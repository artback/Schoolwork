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
 *  since 2011
 */

package se.bthstudent.android.bsk;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import se.bthstudent.android.bsk.R;
import se.bthstudent.android.bsk.schedulesreader.Schedule;
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
import android.widget.Toast;

public class SchedulesAddActivity extends Activity implements BSKLOG {

	private EditText mTextId;
	private EditText mName;
	private DatePicker mStartDate;
	private DatePicker mEndDate;
	private int mNrOfSchedules;
	private String mSearchCode;
	private Button mButtonSearch;
	private Button mButtonSave;

	SharedPreferences mPrefs;
	BskData mAppData = null;
	public static final String PREFS_NAME = "BskPreferences";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.schedules_add);

		mAppData = ((BskData) getApplicationContext());

		mTextId = (EditText) findViewById(R.id.schedule_edit_id);
		mName = (EditText) findViewById(R.id.schedule_edit_name);
		mStartDate = (DatePicker) findViewById(R.id.schedule_startdate);
		mEndDate = (DatePicker) findViewById(R.id.schedule_enddate);
		mButtonSearch = (Button) findViewById(R.id.schedules_search_button);
		mButtonSave = (Button) findViewById(R.id.schedule_save);

		mPrefs = getSharedPreferences(PREFS_NAME, 0);
		mNrOfSchedules = mAppData.getNrOfSchedules();

		mButtonSearch.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
//				Log.d(TAG, "SchedulesAddActivity.onClick");
				FetchCourseCode fetchCourseCode = new FetchCourseCode();
				fetchCourseCode.execute("");
			}
		});

		mButtonSave.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Editor editor = mPrefs.edit();
				try {
					Integer.parseInt(mTextId.getText().toString());
					if(mTextId.getText().length() < 1 || mName.getText().length() < 1) {
						if(mTextId.getText().length() < 1) {
							Toast toast = Toast.makeText(SchedulesAddActivity.this, getResources().getString(R.string.schedules_add_blank_id), Toast.LENGTH_SHORT);
							toast.show();
						}
						if(mName.getText().length() < 1) {
							Toast toast = Toast.makeText(SchedulesAddActivity.this, getResources().getString(R.string.schedules_add_blank_name), Toast.LENGTH_SHORT);
							toast.show();
						}
					} else {
						int id = Integer.parseInt(mTextId.getText().toString().trim());
						String name = mName.getText().toString();
						Calendar calendarStart = new GregorianCalendar(mStartDate.getYear(), mStartDate.getMonth(), mStartDate.getDayOfMonth());
						long start = calendarStart.getTimeInMillis();
						Calendar calendarEnd = new GregorianCalendar(mEndDate.getYear(), mEndDate.getMonth(), mEndDate.getDayOfMonth());
						long end = calendarEnd.getTimeInMillis();

						if((end - start) < 0) {
							Toast toast = Toast.makeText(SchedulesAddActivity.this, getResources().getText(R.string.end_before_start), Toast.LENGTH_SHORT);
							toast.show();
						} else {
							ArrayList<Schedule> schedules;
							if(mNrOfSchedules < 1) {
								schedules = new ArrayList<Schedule>();
								mNrOfSchedules = 1;
							} else {
								schedules = mAppData.getSchedules();
								mNrOfSchedules++;
							}

							Schedule schedule = new Schedule(id, name, start, end);
							schedules.add(schedule);

							mAppData.setSchedules(schedules);
							mAppData.setNrOfSchedules(mNrOfSchedules);
							editor.putInt("schedules", mNrOfSchedules);
							editor.commit();
							mAppData.setModified(true);

							setResult(Activity.RESULT_OK);
							finish();
						}
					}
				} catch(Exception e) {
					Toast toast = Toast.makeText(SchedulesAddActivity.this, getResources().getString(R.string.schedules_add_only_integers), Toast.LENGTH_SHORT);
					toast.show();
				}
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

	public class FetchCourseCode extends AsyncTask<String, Integer, Boolean> implements BSKLOG {
		LinearLayout linear = (LinearLayout) findViewById(R.id.linearlayout_schedules_add);
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		ProgressBar progressBar = new ProgressBar(SchedulesAddActivity.this, null);
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
			// TODO Auto-generated method stub
			// http://schema.bth.se/4DACTION/WebShowSimpleSearch/1/1-0?wv_search=ms1401
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