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

import se.bthstudent.android.bsk.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class BSKActivity extends Activity implements BSKLOG {
	String mVersion;

	CharSequence[] mNewsFeeds;
	boolean[] mSelectedNewsFeeds;
	String[] mSettingsNewsFeeds;

	CharSequence[] mCalendarFeeds;
	boolean[] mSelectedCalendarFeeds;
	String[] mSettingsCalendarFeeds;

	public boolean enVariabel = false;

	public static final String PREFS_NAME = "BskPreferences";
	SharedPreferences mBskPreferences;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		Log.d(TAG, "START BSK APPLICATION");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		// Get the current version name from the manifest and store for later
		// use.
		try {
			mVersion = getPackageManager().getPackageInfo(getPackageName(), 0).versionName;
		} catch (NameNotFoundException e) {
			mVersion = "";
			e.printStackTrace();
		}

		// TODO Verify that the name of these variables make sense
		Resources res = getResources();
		mSettingsNewsFeeds = res.getStringArray(R.array.newssettings);
		mSettingsCalendarFeeds = res.getStringArray(R.array.calendarsettings);
		// Get the names of resource-defined feeds
		mNewsFeeds = res.getStringArray(R.array.newsfeedtitles);
		mCalendarFeeds = res.getStringArray(R.array.calendarfeedtitles);
		// Get the number of feeds in each category
		mSelectedNewsFeeds = new boolean[mNewsFeeds.length];
		mSelectedCalendarFeeds = new boolean[mCalendarFeeds.length];

		// TODO Make sure this only initiates one of the tabs!
		initNews();
		initCalendars();

		Button mBtnNews = (Button) findViewById(R.id.btnMainNews);
		mBtnNews.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent().setClass(BSKActivity.this, NewsActivity.class);
				startActivity(intent);
			}
		});
		
		Button mBtnCalendar = (Button) findViewById(R.id.btnMainCalendar);
		mBtnCalendar.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent().setClass(BSKActivity.this, CalendarActivity.class);
				startActivity(intent);
			}
		});
		
		Button mBtnContacts = (Button) findViewById(R.id.btnMainContacts);
		mBtnContacts.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent().setClass(BSKActivity.this, StaffActivity.class);
				startActivity(intent);
			}
		});
		
		Button mBtnSchedules = (Button) findViewById(R.id.btnMainSchedules);
		mBtnSchedules.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent().setClass(BSKActivity.this, SchedulesActivity.class);
				startActivity(intent);
			}
		});
		
		Button mBtnMap = (Button) findViewById(R.id.btnMainMap);
		mBtnMap.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent().setClass(BSKActivity.this, MapsActivity.class);
				startActivity(intent);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle option menu item selection
		switch (item.getItemId()) {
		case R.id.settings_news:
			showDialog(0);
			return true;
		case R.id.settings_cal:
			showDialog(1);
			return true;
		case R.id.settings_about:
			showDialog(2);
			return true;
		case R.id.settings_refresh:
			//forceRefresh();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	/**
	 * This forces a new update of the items list on the currently open tab
	 */
	/*
	private void forceRefresh() {
		TabHost tabHost = getTabHost();
		Activity myActivity;
		switch (tabHost.getCurrentTab()) {
		case 0:
			myActivity = this.getCurrentActivity();
			((NewsActivity) myActivity).initTab();
			break;
		case 1:
			myActivity = this.getCurrentActivity();
			((CalendarActivity) myActivity).initTab();
			break;
		case 2:
			myActivity = this.getCurrentActivity();
			((ContactsActivity) myActivity).initTab();
			break;
		default:
			break;
		}
	}
	*/

	/**
	 * Prepares the feeds to be fetched, taking into account any user-stored settings
	 */
	private void initNews() {
		ArrayList<Feed> news = new ArrayList<Feed>();

		// Fetch user-defined feed subscriptions
		mBskPreferences = getSharedPreferences(PREFS_NAME, 0);
		for (int i = 0; i < mSettingsNewsFeeds.length; i++) {
			mSelectedNewsFeeds[i] = mBskPreferences.getBoolean(mSettingsNewsFeeds[i], true);
		}

		// Create a list of feed objects based on user-defined subscriptions
		Resources res = getResources();
		TypedArray newsFeeds = res.obtainTypedArray(R.array.newsfeeds);
		for (int i = 0; i < newsFeeds.length(); i++) {
			if(mSelectedNewsFeeds[i]) {
				TypedArray feed = res.obtainTypedArray(newsFeeds.getResourceId(i, 0));
				news.add(new Feed(feed.getText(0).toString(), feed.getText(1).toString(), feed.getText(2).toString(), feed.getText(3).toString(), feed.getResourceId(4, 0), feed.getColor(5, 0), feed.getText(6).toString()));
			}
		}

		//
		((BskData) getApplication()).setNewsList(news);
	}

	/**
	 * Prepares the calendar feeds to be fetched, taking into account ant user-stored settings
	 */
	private void initCalendars() {
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
	}

	protected AlertDialog onCreateDialog(int id) {
		AlertDialog alertDialog;
		AlertDialog.Builder builder = new AlertDialog.Builder(this);

		switch (id) {
		default:
		case 0:
			enVariabel = false;
			builder.setTitle(this.getString(R.string.settings_news));
			builder.setMultiChoiceItems(mNewsFeeds, mSelectedNewsFeeds, new DialogInterface.OnMultiChoiceClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which, boolean isChecked) {
					enVariabel = true;
					mSelectedNewsFeeds[which] = isChecked;
					Editor editor = mBskPreferences.edit();
					editor.putBoolean(mSettingsNewsFeeds[which], isChecked);
					editor.commit();
				}
			});
			builder.setOnCancelListener(new OnCancelListener() {
				@Override
				public void onCancel(DialogInterface arg0) {
					if(enVariabel) {
						initNews();
						/*
						if(getTabHost().getCurrentTab() == 0) {
							String tabTag = getTabHost().getCurrentTabTag();
							NewsActivity a = (NewsActivity) getLocalActivityManager().getActivity(tabTag);
							a.initTab();
						} else {
							((BskData) getApplication()).setChangedNewsFeeds(true);
						}
						*/
					}
				}

			});
			alertDialog = builder.create();
			break;
		case 1:
			enVariabel = false;
			builder.setTitle(this.getString(R.string.settings_cal));
			builder.setMultiChoiceItems(mCalendarFeeds, mSelectedCalendarFeeds, new DialogInterface.OnMultiChoiceClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which, boolean isChecked) {
					enVariabel = true;
					mSelectedCalendarFeeds[which] = isChecked;
					Editor editor = mBskPreferences.edit();
					editor.putBoolean(mSettingsCalendarFeeds[which], isChecked);
					editor.commit();
				}
			});
			builder.setOnCancelListener(new OnCancelListener() {
				@Override
				public void onCancel(DialogInterface arg0) {
					if(enVariabel) {
						initCalendars();
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
		case 2:
			builder.setTitle(this.getString(R.string.about_title) + " v" + mVersion);
			builder.setMessage(R.string.about);
			alertDialog = builder.create();
			break;
		}
		return alertDialog;
	}
}
