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

package com.android4dev.navigationview;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.android4dev.navigationview.icalreader.IcalHandler;
import com.android4dev.navigationview.rssreader.Event;
import com.android4dev.navigationview.rssreader.EventListAdapter;
import com.android4dev.navigationview.rssreader.RSSHandler;
import com.android4dev.navigationview.rssreader.RssItem;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

public class CalendarActivity extends ListActivity implements BSKLOG {
	public FetchCalendar mFetchCalendar = null;
	private EventListAdapter adapter;
	private List<RssItem> allEvents = new ArrayList<RssItem>();
	CharSequence[] mCalendarFeeds;
	boolean[] mSelectedCalendarFeeds;
	String[] mSettingsCalendarFeeds;
	public static final String PREFS_NAME = "BskPreferences";
	SharedPreferences mBskPreferences;
	public boolean mVariabel = false;

	// Initialize adapter and call function to populate list
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		Resources res = getResources();
		mSettingsCalendarFeeds = res.getStringArray(R.array.calendarsettings);
		mCalendarFeeds = res.getStringArray(R.array.calendarfeedtitles);
		mSelectedCalendarFeeds = new boolean[mCalendarFeeds.length];
		
		adapter = new EventListAdapter(this, allEvents);
		setListAdapter(adapter);

		initTab();
	}

	// When this activity resumes, look for any changes in Calendar user-defined
	// subscriptions and update list accordingly.
	public void onResume() {
		super.onResume();
		if(((BskData) getApplication()).isChangedCalendarFeeds()) {
			initTab();
			((BskData) getApplication()).setChangedCalendarFeeds(false);
		}
	}

	// Instantiate internal FetchCalendar object and start call new thread execution.
	public void initTab() {
		
		ArrayList<Feed> calendars = new ArrayList<Feed>();
		Resources res = getResources();
		mBskPreferences = getSharedPreferences(PREFS_NAME, 0);
		
		for (int i = 0; i < mSettingsCalendarFeeds.length; i++) {
			mSelectedCalendarFeeds[i] = mBskPreferences.getBoolean(mSettingsCalendarFeeds[i], true);
		}

		TypedArray calendarFeeds = res.obtainTypedArray(R.array.calendarfeeds);

		for (int i = 0; i < calendarFeeds.length(); i++) {
			if(mSelectedCalendarFeeds[i]) {
				TypedArray calendar = res.obtainTypedArray(calendarFeeds.getResourceId(i, 0));
				calendars.add(new Feed(calendar.getText(0).toString(), calendar.getText(1).toString(), calendar.getText(2).toString(), calendar.getText(3).toString(), calendar.getResourceId(4, 0), calendar.getColor(5, 0), calendar.getText(6).toString()));
			}
		}

		((BskData) getApplication()).setCalendarList(calendars);
		
		mFetchCalendar = new FetchCalendar();
		mFetchCalendar.execute();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.calendar_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle option menu item selection
		switch (item.getItemId()) {
		case R.id.settings_cal:
			showDialog(0);
			return true;
		case R.id.settings_refresh:
			initTab();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
	
	/**
	 * An AsyncTask for fetching and adding calendar events of the user-defined calendar feeds. Note: Cancelling an
	 * AsyncTask might not cancel the currently active process/thread immediately. doInBackground will run its course if
	 * no defined procedure to stop the code from executing exists.
	 *
	 * Unless your AsyncTask is supposed to update something in the UI-thread, DON'T update any visible views in doInBackGround.
	 * Instead, do it in onPreExecute, or onPostExecute, depending on what you need.
	 *  
	 * @author drlaban
	 */
	public class FetchCalendar extends AsyncTask<String, Integer, Boolean> {
		private ProgressDialog progressDialog = null;
		private volatile boolean running = false;

		@Override
		protected void onPreExecute() {
			progressDialog = new ProgressDialog(CalendarActivity.this);
			progressDialog.setMessage(CalendarActivity.this.getString(R.string.fetch_calendar));
			progressDialog.setCancelable(true);

			progressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
				@Override
				public void onCancel(DialogInterface dialog) {
					cancel(true);
				}
			});
			running = true;
			progressDialog.show();
		}

		@Override
		protected Boolean doInBackground(String... status) {
			// TODO See if there's another way of stopping an AsyncTask more effectively.
			// Killing it is not an option, we should really use the isCancelled() method
			// for this. But we would rather not have to wait until an entire feed is
			// processed before we can stop it.
			while (running && !isCancelled()) {
				// Log.d(TAG, "Started CalendarActivity AsyncTask...");
				// Get an array with the feeds to be processed
				ArrayList<Feed> feeds = ((BskData) getApplication()).getCalendarList();
				// Ensure that there is no residual information from previous
				// fetches
				allEvents.clear();
				// Set the maximum number of retries for fetching and parsing
				int maxTries = 5;

				// Start fetch and parse sequence
				for (int i = 0; i < feeds.size(); i++) {
					// Get a new feed object to process
					Feed currentFeed = feeds.get(i);
					// Prepare an empty RssItem list for later
					List<RssItem> events = null;
					// If the type is "rsscalendar", load and process a feed
					// through the RSSHandler
					// or default to a feed through the iCalHandler
					// TODO Show a default message if nothing could be fetched
					if(currentFeed.getType().equalsIgnoreCase("rsscal")) {
						// Debug logging
						// Log.d(TAG, "Processing an RSS calendar feed: "
						// +currentFeed.getFeedUrl());
						// Create a new RSSHandler object with the current feed
						// provided
						RSSHandler rh = new RSSHandler(currentFeed);
						// Prepare values for determining if the fetch and process went well
						Boolean fetchedEvents = false;
						int tryCount = 0;
						while (fetchedEvents == false && tryCount < maxTries && !isCancelled()) {
							fetchedEvents = rh.getLatestArticles();
							tryCount++;
						}
						// Add the processed calendar items to the events list
						events = rh.getList();
					} else if(!isCancelled()) {
						// Create a new IcalHandler object with the current feed provided
						IcalHandler ih = new IcalHandler(currentFeed);
						// Add the processed calendar items to the events list
						events = ih.getList();
					}

					// feeds.get(i).setItems(events);
					// Add the currently fetched and processed events list items
					// to the complete list of events
					allEvents.addAll(events);
				}
				// Add a list of generated weeks to the complete list of events
				if(!isCancelled()) {
					allEvents.addAll(generateWeeks());
				}
				// Sort the complete list of events based on start date
				Collections.sort(allEvents);
				running = false;
				// Log.d(TAG, "In the end of doInBackground...");
			}
			return null;
		}

		@Override
		protected void onPostExecute(Boolean asynchDownloadSuccess) {
			// Log.d(TAG, "onPostExecuting CalendarActivity AsyncTask...");
			running = false;
			adapter.notifyDataSetChanged();
			progressDialog.dismiss();
		}

		@Override
		protected void onCancelled() {
			// TODO Make sure the thread really exits, otherwise duplicates of weeks will occur
			super.onCancelled();
			// Log.d(TAG, "Cancel called");
			running = false;
		}
	}

	private ArrayList<RssItem> generateWeeks() {
		ArrayList<RssItem> weeks = new ArrayList<RssItem>();

		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);

		// Generate 5 dividers, one for each week, starting today
		for (int i = 0; i < 5; i++) {
			Event e = new Event();
			e.setPubDate(calendar.getTime());
			e.setTitle(getResources().getString(R.string.week) + " " + calendar.get(Calendar.WEEK_OF_YEAR));
			weeks.add(e);
			calendar.add(Calendar.DATE, 7);
		}

		// Generate the "Future..." event
		Event e = new Event();
		e.setPubDate(calendar.getTime());
		e.setTitle(getResources().getString(R.string.future));
		weeks.add(e);

		return weeks;

	}
	
	protected AlertDialog onCreateDialog(int id) {
		AlertDialog alertDialog;
		AlertDialog.Builder builder = new AlertDialog.Builder(this);

		switch (id) {
		default:
		case 0:
			mVariabel = false;
			builder.setTitle(this.getString(R.string.settings_cal));
			builder.setMultiChoiceItems(mCalendarFeeds, mSelectedCalendarFeeds, new DialogInterface.OnMultiChoiceClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which, boolean isChecked) {
					mVariabel = true;
					mSelectedCalendarFeeds[which] = isChecked;
					Editor editor = mBskPreferences.edit();
					editor.putBoolean(mSettingsCalendarFeeds[which], isChecked);
					editor.commit();
				}
			});
			builder.setOnCancelListener(new OnCancelListener() {
				@Override
				public void onCancel(DialogInterface arg0) {
					if(mVariabel) {
						initTab();
						/*
						if(getTabHost().getCurrentTab() == 1) {
							String tabTag = getTabHost().getCurrentTabTag();
							CalendarActivity a = (CalendarActivity) getLocalActivityManager().getActivity(tabTag);
							a.initTab();
						} else {
							((BskData) getApplication()).setChangedCalendarFeeds(true);
						}
						*/
					}
				}

			});
			alertDialog = builder.create();
			break;
		}
		return alertDialog;
	}
	
}