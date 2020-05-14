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

package com.android4dev.navigationview.schedulesreader;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.android4dev.navigationview.BSKLOG;
import com.android4dev.navigationview.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class ScheduleAdapter extends ArrayAdapter<ScheduleEvent> implements BSKLOG {
	private Context mContext;
	
	public ScheduleAdapter(Context context, ArrayList<ScheduleEvent> events) {
		super(context, 0, events);
		this.mContext = context;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = ((Activity)mContext).getLayoutInflater();
		View rowView = null;
		ScheduleEvent event = getItem(position);
		rowView = inflater.inflate(R.layout.schedules_item, null);
		if(event.getDescription().equals("event")) {
			rowView = inflater.inflate(R.layout.schedules_item, null);
			
			TextView summary = (TextView) rowView.findViewById(R.id.calendar_item_summary);
			TextView time = (TextView) rowView.findViewById(R.id.calendar_item_time);
			TextView location = (TextView) rowView.findViewById(R.id.calendar_item_location);
			
			summary.setText(event.getSummary());
			SimpleDateFormat format = new SimpleDateFormat("HH:mm");
			Calendar calendarStart = Calendar.getInstance();
			calendarStart.setTimeInMillis(event.getStartDate());
			Calendar calendarEnd = Calendar.getInstance();
			calendarEnd.setTimeInMillis(event.getEndDate());
			String dateStart = format.format(calendarStart.getTime());
			String dateEnd = format.format(calendarEnd.getTime());
			time.setText(dateStart+ " - " +dateEnd);
			location.setText(event.getLocation());
		} else if(event.getDescription().equals("weekday")) {
			rowView = inflater.inflate(R.layout.list_weekday_divider, null);
			TextView weekDayDivider = (TextView) rowView.findViewById(R.id.weekday_divider_textview);
			weekDayDivider.setText(event.getSummary());
		} else if(event.getDescription().equals("week")) {
			rowView = inflater.inflate(R.layout.list_week_divider, null);
			TextView weekDivider = (TextView) rowView.findViewById(R.id.week_divider_textview);
			weekDivider.setText(event.getSummary());
		}
		return rowView;
	}
}
