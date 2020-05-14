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
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import se.bthstudent.android.bsk.R;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ProgressBar;

public class SchedulesSettingsEditActivity extends Activity implements BSKLOG {

	private int mCurrentId;
	private EditText mTextId;
	private EditText mName;
	private DatePicker mStartDate;
	private DatePicker mEndDate;
	private String mSearchCode;
	private Button mButtonSearch;
	private Button mButtonSave;

	SharedPreferences mPrefs;
	public static final String PREFS_NAME = "BskPreferences";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.schedules_settings_calendar_edit);
		this.mCurrentId = getIntent().getExtras().getInt("id");
		mPrefs = getSharedPreferences(PREFS_NAME, 0);

		mTextId = (EditText) findViewById(R.id.schedule_edit_id);
		mName = (EditText) findViewById(R.id.schedule_edit_name);
		mStartDate = (DatePicker) findViewById(R.id.schedule_startdate);
		mEndDate = (DatePicker) findViewById(R.id.schedule_enddate);
		mButtonSearch = (Button) findViewById(R.id.schedules_search_button);
		mButtonSave = (Button) findViewById(R.id.schedule_save);

		//Log.d("SLIST", "currentId: " +mPrefs.getInt("id" +mCurrentId, 0));
		mTextId.setText(mPrefs.getInt("id" + mCurrentId, 0) + "");
		if(mTextId.length() > 7) {
			mButtonSearch.setEnabled(false);
		} else {
			mButtonSearch.setEnabled(false);
		}

		mName.setText(mPrefs.getString("name" + mCurrentId, "None"));

		Calendar date = this.parseLong(mPrefs.getLong("start" + mCurrentId, 0));
		int year = date.get(Calendar.YEAR);
		int month = date.get(Calendar.MONTH);
		int day = date.get(Calendar.DAY_OF_MONTH);
		mStartDate.init(year, month, day, null);

		date = this.parseLong(mPrefs.getLong("end" + mCurrentId, 0));
		year = date.get(Calendar.YEAR);
		month = date.get(Calendar.MONTH);
		if(mStartDate == mEndDate) {
			day = date.get(Calendar.DAY_OF_MONTH + 1);
		} else {
			day = date.get(Calendar.DAY_OF_MONTH);
		}
		mEndDate.init(year, month, day, null);

		mButtonSearch.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// http://schema.bth.se/4DACTION/WebShowSimpleSearch/1/1-0?wv_search=ms1401
//				Log.d(TAG, "SchedulesAddActivity.onClick");
				FetchCourseCode fetchCourseCode = new FetchCourseCode();
				fetchCourseCode.execute("");
			}
		});

		mButtonSave.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				// TODO Auto-generated method stub
				int id = Integer.parseInt((mTextId.getText().toString().trim()));
				String name = mName.getText().toString();
				Calendar calendar = new GregorianCalendar(mStartDate.getYear(), mStartDate.getMonth(), mStartDate.getDayOfMonth());
				long start = calendar.getTimeInMillis();
				calendar.set(mEndDate.getYear(), mEndDate.getMonth(), mEndDate.getDayOfMonth());
				long end = calendar.getTimeInMillis();

				BskData appData = ((BskData) getApplicationContext());
				appData.getSchedules().get(mCurrentId).setId(id);
				appData.getSchedules().get(mCurrentId).setName(name);
				appData.getSchedules().get(mCurrentId).setStartDate(start);
				appData.getSchedules().get(mCurrentId).setEndDate(end);

				Editor editor = mPrefs.edit();
				editor.putInt("id" + mCurrentId, id);
				editor.putString("name" + mCurrentId, name);
				editor.putLong("start" + mCurrentId, start);
				editor.putLong("end" + mCurrentId, end);
				editor.commit();

				appData.setModified(true);

				setResult(Activity.RESULT_OK);
				finish();
			}
		});

		mTextId.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub
				if(count > 7) {
					mButtonSearch.setEnabled(false);
				} else {
					mButtonSearch.setEnabled(true);
				}
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
			}
		});
	}

	private Calendar parseLong(long time) {
		Calendar calendar = new GregorianCalendar();
		calendar.setTimeInMillis(time);
		return calendar;
	}

	public class FetchCourseCode extends AsyncTask<String, Integer, Boolean> implements BSKLOG {
		LinearLayout linear = (LinearLayout) findViewById(R.id.linearlayout_schedule_edit);
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		ProgressBar progressBar = new ProgressBar(SchedulesSettingsEditActivity.this, null);
		String foundId = "";

		@Override
		protected void onPreExecute() {
//			Log.d(TAG, "FetchCourseCode onPreExecute()");
			progressBar.setId(5);
			progressBar.setLayoutParams(params);
			progressBar.invalidate();
			mTextId.setEnabled(false);
			mButtonSave.setEnabled(false);
			linear.removeView(mButtonSearch);
			linear.addView(progressBar);
		}

		@Override
		protected Boolean doInBackground(String... arg0) {
			String inputLine;
			URL url;
			mSearchCode = mTextId.getText().toString().trim();
			try {
				url = new URL("http://schema.bth.se/4DACTION/WebShowSimpleSearch/1/1-0?wv_search=" + mSearchCode);
				URLConnection urlConnection = url.openConnection();
				BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
				while((inputLine = in.readLine()) != null) {
					// Log.d("SFIND", "This is the result: " +inputLine);
					// Pattern p = Pattern.compile("\\bwv_obj1=(\\d*)&\\b");
					Pattern p = Pattern.compile("\\b(wv_obj1=){1}(\\d*)(&){1}\\b");
					Matcher m = p.matcher(inputLine);
					while(m.find()) {
						foundId = m.group(2);
						//Log.d("SFIND", "Found this match!: " + m.group());
						//Log.d("SFIND", "Found this match!: " + m.group(2));
					}
				}
			} catch(MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch(IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		protected void onPostExecute(Boolean asynchDownloadSuccess) {
			mTextId.setText(String.valueOf(foundId));
			mTextId.setEnabled(true);
			mButtonSave.setEnabled(true);
			linear.removeView(progressBar);
			linear.addView(mButtonSearch);
		}
	}
}
