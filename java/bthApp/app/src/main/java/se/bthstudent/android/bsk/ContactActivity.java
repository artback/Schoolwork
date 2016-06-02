/*
 *  This file is distributed under the GPL3 license.
 *  Please see the file LICENSE that should be present in this distribution.
 *
 *  Copyright 2011
 *   Jonas Hellström <jonas@if-then-else.se>
 *   Emma Ekberg <emma.ekberg@gmail.com>
 *  
 *  Maintained by
 *   Jonas Hellström <jonas@if-then-else.se>
 *   Emma Ekberg <emma.ekberg@gmail.com>
 *  since 2011
 */

package se.bthstudent.android.bsk;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import se.bthstudent.android.bsk.R;
import se.bthstudent.android.bsk.jsonreader.JsonAdapter;
import se.bthstudent.android.bsk.jsonreader.JsonContact;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

public class ContactActivity extends ListActivity implements BSKLOG {
	private JsonAdapter mJsonAdapter;
	private Map<String, JsonContact> mContacts;
	private JsonContact mCurrentContact;
	private List<String[]> mList = new ArrayList<String[]>();
	private String mResponse = "";
	private String mCurrentAcronym;
	public FetchContacts mFetchContacts = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		mCurrentAcronym = getIntent().getExtras().getString("contact");
		mJsonAdapter = new JsonAdapter(this, mList);
		setListAdapter(mJsonAdapter);

	private void populateList() {
		mCurrentContact = mContacts.get(mCurrentAcronym);

		mList.add(new String[]{"header", getResources().getString(R.string.name)});
		mList.add(new String[]{"item", nullCheck(mCurrentContact.getFirstname() + " " + mCurrentContact.getLastname())});
		mList.add(new String[]{"header", getResources().getString(R.string.acronym)});
		mList.add(new String[]{"item", nullCheck(mCurrentContact.getAcronym())});
		mList.add(new String[]{"header", getResources().getString(R.string.location)});
		mList.add(new String[]{"item", nullCheck(getResources().getString(R.string.room) + " " + mCurrentContact.getRoom() + ", " + mCurrentContact.getEmploymentLocation())});
		mList.add(new String[]{"header", getResources().getString(R.string.phone)});
		mList.add(new String[]{"item", nullCheck(mCurrentContact.getPhone())});
		mList.add(new String[]{"header", getResources().getString(R.string.cellphone)});
		mList.add(new String[]{"item", nullCheck(mCurrentContact.getCellphone())});
		mList.add(new String[]{"header", getResources().getString(R.string.mail)});
		mList.add(new String[]{"item", nullCheck(mCurrentContact.getMail())});
		mList.add(new String[]{"header", getResources().getString(R.string.institution)});
		mList.add(new String[]{"item", nullCheck(mCurrentContact.getInstitution())});
		mList.add(new String[]{"header", getResources().getString(R.string.homepage)});
		mList.add(new String[]{"item", nullCheck(mCurrentContact.getHomepage())});
		mList.add(new String[]{"header", getResources().getString(R.string.employment)});
		mList.add(new String[]{"item", nullCheck(mCurrentContact.getEmploymentType())});

		mJsonAdapter.notifyDataSetChanged();
	}
}
	public String nullCheck(String incoming) {
		if(incoming.length() == 0) {
			return getResources().getString(R.string.not_specified);
		}
		return incoming;
	}

	public class FetchContacts extends AsyncTask<String, Integer, Boolean> implements BSKLOG {
		private volatile boolean running = true;
		private ProgressDialog progressDialog = null;
		public Context extContext;

		public FetchContacts(Context context) {
			this.extContext = context;
		}

		@Override
		protected void onPreExecute() {
			progressDialog = new ProgressDialog(extContext);
			progressDialog.setMessage(extContext.getString(R.string.fetch_contacts));
			progressDialog.setCancelable(true);
			progressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
				@Override
				public void onCancel(DialogInterface dialog) {
					cancel(true);
				}
			});

			progressDialog.show();
		}




		@Override
		protected void onPostExecute(Boolean asynchDownloadSuccess) {
			populateList();

			running = false;
			progressDialog.dismiss();
		}

		@Override
		protected void onCancelled() {
			super.onCancelled();
			running = false;
		}
	}
}
