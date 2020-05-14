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
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import se.bthstudent.android.bsk.R;
import se.bthstudent.android.bsk.schedulesreader.Schedule;
import se.bthstudent.android.bsk.schedulesreader.ScheduleAdapter;
import se.bthstudent.android.bsk.schedulesreader.ScheduleEvent;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;

public class SchedulesActivity extends ListActivity implements BSKLOG {
	private static int SETTINGS_ACTIVITY = 1111;
	private static int ADD_ACTIVITY = 1212;

	private ArrayList<ScheduleEvent> mEvents = new ArrayList<ScheduleEvent>();
	private ArrayList<Schedule> mFeeds = null;
	private ScheduleAdapter mAdapter;
	private BskData mAppData;
	private SharedPreferences mSharedPreferences = null;
	public static final String PREFS_NAME = "BskPreferences";
	private FetchSchedule mFetchSchedule;
	private Calendar mCalendar = Calendar.getInstance();
	private ListView mListView;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.schedules_main);
		mAppData = ((BskData) getApplicationContext());

		//jsonHandler = new JsonHandler();

		mSharedPreferences = getSharedPreferences(PREFS_NAME, 0);
		mAppData.setNrOfSchedules(mSharedPreferences.getInt("calendars", 0));
		mFeeds = new ArrayList<Schedule>();
		// If: there are no stored calendars, create two default ones
		// else: initialise and fetch data based on stored calendars
		if(mAppData.getNrOfSchedules() < 1) {
			mAppData.setNrOfSchedules(0);
		} else {
			initStoredList();
		}
		this.mAdapter = new ScheduleAdapter(this, mEvents);
		setListAdapter(this.mAdapter);

		mListView = getListView();
		mListView.setOnItemLongClickListener(new OnItemLongClickListener() {
			public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

				Intent intent = new Intent(SchedulesActivity.this, ContactActivity.class);
				Bundle bundle = new Bundle();
				bundle.putString("contact", mEvents.get(position).getContact());
				intent.putExtras(bundle);
				startActivity(intent);
				return true;
			}
		});

		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				// TODO Auto-generated method stub
				// TODO Auto-generated method stub
				if(!mEvents.get(position).getDescription().matches("weekday") && !mEvents.get(position).getDescription().matches("week")) {
					Intent intent = new Intent(SchedulesActivity.this, RoomActivity.class);
					Bundle bundle = new Bundle();
					bundle.putString("room", mEvents.get(position).getLocation());
					intent.putExtras(bundle);
					startActivity(intent);
				}
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.schedules_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle item selection
		Intent intent;
		switch(item.getItemId()) {
		case R.id.settings:
			intent = new Intent(SchedulesActivity.this, SchedulesSettingsActivity.class);
			startActivityForResult(intent, SETTINGS_ACTIVITY);
			return true;
		case R.id.schedule_help:
			showDialog(R.id.schedule_help);
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if(requestCode == ADD_ACTIVITY && resultCode == Activity.RESULT_OK) {
			initList(false);
			mFeeds = mAppData.getSchedules();
			mAdapter.notifyDataSetChanged();
		} else if(requestCode == SETTINGS_ACTIVITY && resultCode == Activity.RESULT_OK) {
			initList(true);
			mAdapter.notifyDataSetChanged();
		}
	}

	@Override
	public void onResume() {
		super.onResume();

		if(mAppData.isModified() == true && mAppData.getNrOfSchedules() > 0) {
			mFeeds = mAppData.getSchedules();
			mAdapter.notifyDataSetChanged();
			Editor editor = mSharedPreferences.edit();
			editor.putInt("calendars", mAppData.getSchedules().size());
			for(int i = 0; i < mAppData.getSchedules().size(); i++) {
				editor.putInt("id" + i, mFeeds.get(i).getId());
				editor.putString("name" + i, mFeeds.get(i).getName());
				editor.putLong("start" + i, mFeeds.get(i).getStartDate());
				editor.putLong("end" + i, mFeeds.get(i).getEndDate());
			}
			editor.commit();
			mAppData.setModified(false);
			initList(true);
		} else if(mAppData.isModified() == true && mAppData.getNrOfSchedules() < 1) {
			mEvents.clear();
			mAdapter.notifyDataSetChanged();
			mAppData.setModified(false);
		}
	}

	public void onPause() {
		super.onPause();
		Editor editor = mSharedPreferences.edit();
		editor.putInt("calendars", mAppData.getNrOfSchedules());
	}

	private void initList(boolean isPopulated) {
		if(isPopulated) {
			mFetchSchedule = new FetchSchedule();
			mFetchSchedule.execute("");
		} else {
			mEvents.clear();
			mAdapter.notifyDataSetChanged();
		}
	}

	protected Dialog onCreateDialog(int id) {
		AlertDialog alert;
		switch(id) {
		case R.id.schedule_help:
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setTitle(R.string.schedules_title);
			builder.setMessage(R.string.schedules_message).setPositiveButton(R.string.schedules_ok, new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int id) {
				}
			});
			alert = builder.create();
			break;
		default:
			alert = null;
		}
		return alert;
	}

	private void initStoredList() {
		mFeeds.clear();
		for(int i = 0; i < mAppData.getNrOfSchedules(); i++) {
			int id = mSharedPreferences.getInt("id" + i, 0);
			String name = mSharedPreferences.getString("name" + i, "None");
			long start = mSharedPreferences.getLong("start" + i, 0);
			long end = mSharedPreferences.getLong("end" + i, 0);
			Schedule event = new Schedule(id, name, start, end);
			mFeeds.add(event);
		}
		mAppData.setSchedules(mFeeds);

		mFetchSchedule = new FetchSchedule();
		mFetchSchedule.execute("");
	}

	public class FetchSchedule extends AsyncTask<String, Integer, Boolean> implements BSKLOG {
		private ProgressDialog progressDialog = null;
		private ScheduleEvent mEvent;
		private ArrayList<ScheduleEvent> mWeekDays = new ArrayList<ScheduleEvent>();
		private boolean running = true;
		ArrayList<String> mUrls;

		@Override
		protected void onPreExecute() {
			mEvents.clear();
			mWeekDays.clear();
			mUrls = new ArrayList<String>();
			for(int i = 0; i < mFeeds.size(); i++) {
				String start;
				String startYear = String.valueOf(mFeeds.get(i).getDateAsCalendar(mFeeds.get(i).getStartDate()).get(Calendar.YEAR));
				startYear = startYear.substring(2, 4);
				int tempWeek = mFeeds.get(i).getDateAsCalendar(mFeeds.get(i).getStartDate()).get(Calendar.WEEK_OF_YEAR);
				String startWeek = "";
				if(tempWeek < 10) {
					char[] zeros = new char[2];
					Arrays.fill(zeros, '0');
					DecimalFormat df = new DecimalFormat(String.valueOf(zeros));
					startWeek = df.format(tempWeek);
				} else {
					startWeek = String.valueOf(tempWeek);
				}
				start = startYear + startWeek;
				String end;
				String endYear = String.valueOf(mFeeds.get(i).getDateAsCalendar(mFeeds.get(i).getEndDate()).get(Calendar.YEAR));
				endYear = endYear.substring(2, 4);
				tempWeek = mFeeds.get(i).getDateAsCalendar(mFeeds.get(i).getEndDate()).get(Calendar.WEEK_OF_YEAR);
				String endWeek = "";
				if(tempWeek < 10) {
					char[] zeros = new char[2];
					Arrays.fill(zeros, '0');
					DecimalFormat df = new DecimalFormat(String.valueOf(zeros));
					endWeek = df.format(tempWeek);
				} else {
					endWeek = String.valueOf(tempWeek);
				}

				end = endYear + endWeek;
				String url = "http://schema.bth.se/4DACTION/iCal_downloadReservations/timeedit.ics?from=" + start + "&to=" + end + "&id1=" + mFeeds.get(i).getId() + "&branch=1&lang=1";
				mUrls.add(url);
			}
			;
			progressDialog = new ProgressDialog(SchedulesActivity.this);
			progressDialog.setMessage(getResources().getString(R.string.fetch_schedules));
			progressDialog.setCancelable(true);

			progressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
				public void onCancel(DialogInterface dialog) {
					mFetchSchedule.cancel(true);
				}
			});

			progressDialog.show();
		}

		// Unless your AsyncTask is supposed to update something in the
		// UI-thread, DON'T update any visible views here.
		// Instead, do it in onPreExecute, or onPostExecute, depending on what
		// you need.
		@Override
		protected Boolean doInBackground(String... status) {
			int tryCount = 0;
			while(tryCount < 3) {
				try {
					running = true;
					String inputLine;
					for(int i = 0; i < mUrls.size(); i++) {
						Calendar currentDay = Calendar.getInstance();
						Calendar comparingDate = Calendar.getInstance();
						URL url = new URL(mUrls.get(i));
						URLConnection urlConnection = url.openConnection();
						BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
						while((inputLine = in.readLine()) != null) {
							Pattern p = Pattern.compile("(.*?):(.*)");
							Matcher m = p.matcher(inputLine);
							while(m.find()) {

								if(m.group(1).equalsIgnoreCase("begin") && m.group(2).equalsIgnoreCase("vevent")) {
									mEvent = new ScheduleEvent();
								}

								if(m.group(1).equalsIgnoreCase("summary")) {
									mEvent.setContact(m.group(2).substring(0, 3));
									String str = m.group(2).replaceAll("\\\\", "");
									mEvent.setSummary(str);
								}

								if(m.group(1).equalsIgnoreCase("DTSTART;TZID=Europe/Stockholm")) {
									long parsedDate = mEvent.parseDate(m.group(2));
									mEvent.setStartDate(parsedDate);
									comparingDate.setTimeInMillis(mEvent.getStartDate());
								}

								if(m.group(1).equalsIgnoreCase("DTEND;TZID=Europe/Stockholm")) {
									long parsedDate = mEvent.parseDate(m.group(2));
									mEvent.setEndDate(parsedDate);
								}

								if(m.group(1).equalsIgnoreCase("location")) {
									mEvent.setLocation(m.group(2));
								}

								if(m.group(1).equalsIgnoreCase("description")) {
									mEvent.setDescription("event");
								}

								if(m.group(1).equalsIgnoreCase("end") && m.group(2).equalsIgnoreCase("vevent")) {
									mEvents.add(mEvent);
									if(currentDay.get(Calendar.DATE) != comparingDate.get(Calendar.DATE)) {
										mWeekDays.add(addDay(mEvent));
										currentDay.setTimeInMillis(comparingDate.getTimeInMillis());
									}
								}
							}
						}
						in.close();
					}
					tryCount = 3;
				} catch(Exception e) {
					tryCount++;
					running = false;
				}
			}
			return true;
		}

		@Override
		protected void onPostExecute(Boolean asynchDownloadSuccess) {
			Collections.sort(mWeekDays);
			if(running) {
				Calendar weekDayCalendar = Calendar.getInstance();
				for(int i = 0; i < (mWeekDays.size() - 1); i++) {
					weekDayCalendar.setTimeInMillis(mWeekDays.get(i).getStartDate());
					mCalendar.setTimeInMillis(mWeekDays.get(i + 1).getStartDate());

					if(mCalendar.get(Calendar.DATE) == weekDayCalendar.get(Calendar.DATE)) {
						mWeekDays.remove(i);
					}
					mCalendar.setTimeInMillis(mWeekDays.get(i).getStartDate());
				}
				if(running) {
					mEvents.addAll(mWeekDays);
					mEvents.addAll(this.generateWeeks());
					Collections.sort(mEvents);
					for(int i = 0; i < mEvents.size(); i++) {
						//						Log.d(TAG, mEvents.get(i).getDescription());
					}
				}
			}
			mAdapter.notifyDataSetChanged();
			progressDialog.dismiss();
		}

		@Override
		protected void onCancelled() {
			super.onCancelled();
			running = false;
		}

		private ScheduleEvent addDay(ScheduleEvent event) {
			Calendar calendar = Calendar.getInstance();
			calendar.setTimeInMillis(event.getStartDate());
			calendar.set(Calendar.HOUR_OF_DAY, 01);
			DateFormat dateFormat = new SimpleDateFormat("EEEE yyyy-MM-dd");

			ScheduleEvent dayEvent = new ScheduleEvent();
			dayEvent.setStartDate(calendar.getTimeInMillis());
			dayEvent.setDescription("weekday");
			dayEvent.setSummary(dateFormat.format(event.getStartDate()));

			return dayEvent;
		}

		private ArrayList<ScheduleEvent> generateWeeks() {
			ArrayList<ScheduleEvent> weeks = new ArrayList<ScheduleEvent>();

			Calendar weekCalendar = Calendar.getInstance();
			Calendar weekDayCalendar = Calendar.getInstance();

			long currentStart;
			long comparingStart;
			long currentEnd;
			long comparingEnd;
			currentStart = mFeeds.get(0).getStartDate();
			currentEnd = mFeeds.get(0).getEndDate();
			if(mFeeds.size() == 1) {
			} else {
				for(int i = 0; i < (mFeeds.size() - 1); i++) {
					comparingStart = mFeeds.get(i + 1).getStartDate();
					comparingEnd = mFeeds.get(i + 1).getEndDate();

					if(currentStart < comparingStart) {
					} else {
						currentStart = comparingStart;

					}
					if(currentEnd > comparingEnd) {
					} else {
						currentEnd = comparingEnd;
					}
				}
			}
			Calendar startCalendar = Calendar.getInstance();
			startCalendar.setTimeInMillis(currentStart);
			Calendar endCalendar = Calendar.getInstance();
			endCalendar.setTimeInMillis(currentEnd);
			// Note: The order of the set dates seem to have an importance as to
			// how the dividers will show up.
			weekCalendar.set(Calendar.YEAR, startCalendar.get(Calendar.YEAR));
			weekCalendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
			weekCalendar.set(Calendar.HOUR_OF_DAY, 00);
			weekCalendar.set(Calendar.WEEK_OF_YEAR, startCalendar.get(Calendar.WEEK_OF_YEAR));
			//			Log.d(TAG, "startDate: " +weekCalendar.getTime());
			int weekStart = startCalendar.get(Calendar.WEEK_OF_YEAR);
			int weekEnd = endCalendar.get(Calendar.WEEK_OF_YEAR);
			int nrOfWeeks = 0;
			if((weekEnd - weekStart) < 0) {
				nrOfWeeks = mCalendar.getActualMaximum(Calendar.WEEK_OF_YEAR) - weekStart + weekEnd;
			} else if(weekEnd - weekStart == 0) {
				nrOfWeeks = 1;
			} else {
				nrOfWeeks = weekEnd - weekStart;
			}

			// int nrOfWeeks = (int)(dateDiff / def_weeks);
			if(running) {
				for(int i = 0; i < nrOfWeeks; i++) {
					ScheduleEvent weekEvent = new ScheduleEvent();
					weekEvent.setSummary(getResources().getString(R.string.week) + " " + weekCalendar.get(Calendar.WEEK_OF_YEAR));

					weekEvent.setDescription("week");
					weekEvent.setStartDate(weekCalendar.getTimeInMillis());
					weeks.add(weekEvent);

					weekDayCalendar.setTime(weekCalendar.getTime());
					weekCalendar.add(Calendar.DATE, 7);
				}
			}
			return weeks;
		}
	}
}