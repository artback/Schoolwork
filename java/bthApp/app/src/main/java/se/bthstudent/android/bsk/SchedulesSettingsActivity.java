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
import se.bthstudent.android.bsk.schedulesreader.Schedule;
import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class SchedulesSettingsActivity extends ListActivity implements BSKLOG {
	private static int ADD_ACTIVITY = 1212;
	private SharedPreferences mSharedPreferences = null;
	private ArrayAdapter<Schedule> mSchedulesAdapter;
	private ArrayList<Schedule> mSchedules;
	private ListView mListView;
	private BskData mAppData = null;
	public static final String PREFS_NAME = "BskPreferences";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.schedules_settings);

		// Log.d(TAG, "onCreate...");

		mSharedPreferences = getSharedPreferences(PREFS_NAME, 0);
		mAppData = ((BskData) getApplicationContext());
		if(mSharedPreferences.getInt("schedules", 0) == 0) {
			mSchedules = new ArrayList<Schedule>();
		} else {
			mSchedules = mAppData.getSchedules();
		}
//			Log.d(TAG, "mSchedules size:" +mSchedules.size());
		// Log.d("SLIST", "SlistSettingsActivity.mCalendars.size: "
		// +mAppData.getNrOfCalendars());
		mListView = getListView();
		mSchedulesAdapter = new ArrayAdapter<Schedule>(this, R.layout.schedules_settings_item, mSchedules);
		setListAdapter(mSchedulesAdapter);

		if(mAppData.getNrOfSchedules() > 0) {
			initList();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.schedules_settings_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle item selection
		Intent intent;
		switch (item.getItemId()) {
		case R.id.add_schedule:
			intent = new Intent(SchedulesSettingsActivity.this, SchedulesAddActivity.class);
			startActivityForResult(intent, ADD_ACTIVITY);
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

//		Log.d(TAG, "onActivityForResult...");
//		Log.d(TAG, "nrOfSchedules" +mAppData.getNrOfSchedules());
		if(resultCode == Activity.RESULT_OK) {
			mSchedules = mAppData.getSchedules();
			/*
			ArrayList<Schedule> tempSchedules = mAppData.getSchedules();
//			Log.d(TAG, "Nr of tempSchedules: " +tempSchedules.size());
			mSchedules.clear();
			mSchedules.addAll(tempSchedules);
			
//			Log.d(TAG, "Name: " +mSchedules.get(0).getName());
			mSchedulesAdapter.notifyDataSetChanged();
			*/
			
			mSchedulesAdapter = new ArrayAdapter<Schedule>(this, R.layout.schedules_settings_item, mSchedules);
			setListAdapter(mSchedulesAdapter);
			initList();
		}
	}
	
	private void initList() {
		// Log.d(TAG, "SettingsActivity.initialising...");

		// ListView lv = getListView();
		mListView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				// Intent intent = new Intent(getApplicationContext(),
				// SlistSettingsCalendarEdit.class);
				Intent intent = new Intent(SchedulesSettingsActivity.this, SchedulesSettingsEditActivity.class);
				Bundle bundle = new Bundle();
				bundle.putInt("id", position);
				intent.putExtras(bundle);
				startActivityForResult(intent, 2222);
			}
		});

		mListView.setOnItemLongClickListener(new OnItemLongClickListener() {
			public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
				Editor editor = mSharedPreferences.edit();

				mSchedules.remove(position);
				mAppData.setSchedules(mSchedules);
				for (int i = 0; i < mSchedules.size(); i++) {
					editor.putInt("id" + i, mSchedules.get(i).getId());
					editor.putString("name" + i, mSchedules.get(i).getName());
					editor.putLong("start" + i, mSchedules.get(i).getStartDate());
					editor.putLong("end" + i, mSchedules.get(i).getEndDate());
				}
				editor.putInt("schedules", mSchedules.size());
				editor.commit();
				mAppData.setNrOfSchedules(mSchedules.size());
				mAppData.setModified(true);
				mSchedulesAdapter.notifyDataSetChanged();
				return true;
			}
		});
	}
}