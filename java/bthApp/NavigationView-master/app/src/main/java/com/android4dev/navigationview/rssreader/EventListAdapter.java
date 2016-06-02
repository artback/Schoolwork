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

package com.android4dev.navigationview.rssreader;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.android4dev.navigationview.BSKLOG;
import com.android4dev.navigationview.R;

import java.util.List;

/**
 * This class adds the different views to the list layout based on the fetched and processed data from the calendar
 * feeds. The adapter loads this dynamically when scrolling through the list of items.
 * 
 * @author drlaban
 * 
 */
public class EventListAdapter extends ArrayAdapter<RssItem> implements BSKLOG {
	public EventListAdapter(Activity activity, List<RssItem> event) {
		super(activity, 0, event);
	}

	// In order to populate the list items with data, we have to inflate the
	// current layout and get each view we want to populate.
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Activity activity = (Activity) getContext();
		LayoutInflater inflater = activity.getLayoutInflater();
		View rowView = null;
		Event currentEvent = (Event) getItem(position);
		// If the item to display is null, we insert a week number divider,
		// else we insert a regular item and populate it with data.
		if(currentEvent.getFeed() == null) {
			rowView = inflater.inflate(R.layout.list_divider, null);
			TextView item_weeknumber = (TextView) rowView.findViewById(R.id.list_divider_text);
			item_weeknumber.setText(currentEvent.getTitle());
		} else {
			rowView = inflater.inflate(R.layout.calendar_list_item, null);

			TextView item_title = (TextView) rowView.findViewById(R.id.calendar_list_item_title);
			TextView item_date = (TextView) rowView.findViewById(R.id.calendar_list_item_date);
			FrameLayout item_color = (FrameLayout) rowView.findViewById(R.id.calendar_list_item_color);

			item_color.setBackgroundColor(currentEvent.getFeed().getColor());
			item_title.setText(currentEvent.getTitle());
			item_date.setText(currentEvent.getFormattedPubDate());
		}
		return rowView;
	}
}