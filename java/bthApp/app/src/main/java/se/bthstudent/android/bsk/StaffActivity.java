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

import java.util.ArrayList;
import java.util.List;

import se.bthstudent.android.bsk.R;
import se.bthstudent.android.bsk.vcardreader.VcardAdapter;
import se.bthstudent.android.bsk.vcardreader.VcardHandler;
import se.bthstudent.android.bsk.vcardreader.VcardItem;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

public class StaffActivity extends ListActivity implements BSKLOG {

	private VcardAdapter adapter;
	private List<VcardItem> mVcard = new ArrayList<VcardItem>();
	private VcardHandler mVcardHandler = null;
	private ArrayList<String[]> mValues;
	private List<String[]> mList;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		mList = new ArrayList<String[]>();
		adapter = new VcardAdapter(this, mList);
		setListAdapter(adapter);

		initTab();
	}

	public void initTab() {
		FetchContacts fetchContacts = new FetchContacts(this);
		fetchContacts.execute("");
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);

		if(mValues.get(position)[0] == null) {
		} else if(mValues.get(position)[0].equals("phone")) {
			if(!mValues.get(position)[1].equals(this.getString(R.string.not_specified))) {
				String url = "tel:" + mValues.get(position)[1];
				Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse(url));
				startActivity(intent);
			}
		} else if(mValues.get(position)[0].equals("email")) {
			Intent intent = new Intent(android.content.Intent.ACTION_SEND);
			intent.setType("plain/text");
			intent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[] { mValues.get(position)[1] });
			startActivity(Intent.createChooser(intent, "Send mail..."));
		} else if(mValues.get(position)[0].equals("url")) {
			Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(mValues.get(position)[1]));
			startActivity(intent);
		}
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
		protected Boolean doInBackground(String... status) {
			while (running && !isCancelled()) {
				mList.clear();
				mVcard.clear();
				mVcardHandler = new VcardHandler(extContext);

				String[] vCards = { "http://www.bthstudent.se/meta.php?p=vcard&id=1", "http://www.bthstudent.se/meta.php?p=vcard&id=2" };

				for (int i = 0; i < vCards.length; i++) {
					boolean fetchedVcards = false;
					while (fetchedVcards == false && !isCancelled()) {
						fetchedVcards = mVcardHandler.getVcardValues(vCards[i], i);
					}
				}

				mVcard.addAll(mVcardHandler.getVcardList());
				// Log.d(TAG, "I have this number of values: " +mVcard.size());
				mValues = new ArrayList<String[]>();

				for (int i = 0; i < mVcard.size(); i++) {
					switch (mVcard.get(i).getPosition()) {
					default:
						break;
					case 1:
						mList.add(new String[] { "header", getResources().getString(R.string.chairman) });
						break;
					case 2:
						mList.add(new String[] { "header", getResources().getString(R.string.vice_president) });
						break;
					}
					// mList.add(new String[] {"header", mVcard.get(i).getTitle()});
					mValues.add(new String[] { null, null });
					mList.add(new String[] { "item", mVcard.get(i).getName() });
					mValues.add(new String[] { null, null });
					mList.add(new String[] { "item", mVcard.get(i).getOrganization() });
					mValues.add(new String[] { null, null });
					mList.add(new String[] { "item", mVcard.get(i).getWorkphone() });
					mValues.add(new String[] { "phone", mVcard.get(i).getWorkphone() });
					mList.add(new String[] { "item", mVcard.get(i).getMobilephone() });
					mValues.add(new String[] { "phone", mVcard.get(i).getMobilephone() });
					mList.add(new String[] { "item", mVcard.get(i).getAddress() });
					mValues.add(new String[] { null, null });
					mList.add(new String[] { "item", mVcard.get(i).getUrl() });
					mValues.add(new String[] { "url", mVcard.get(i).getUrl() });
					mList.add(new String[] { "item", mVcard.get(i).getEmail() });
					mValues.add(new String[] { "email", mVcard.get(i).getEmail() });
				}
				running = false;
			}
			return null;
		}

		@Override
		protected void onPostExecute(Boolean asynchDownloadSuccess) {
			running = false;
			adapter.notifyDataSetChanged();
			progressDialog.dismiss();
			// Log.d(TAG, "mValues.size: " +mValues.size());
		}

		@Override
		protected void onCancelled() {
			super.onCancelled();
			running = false;
		}
	}
}