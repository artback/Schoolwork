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

import com.android4dev.navigationview.R;

import java.util.List;

/**
 * This class adds the different views to the list layout based on the fetched and processed data from the news feeds.
 * The adapter loads this dynamically when scrolling through the list of items.
 * 
 * @author drlaban
 * 
 */
public class NewsListAdapter extends ArrayAdapter<RssItem> {
	public NewsListAdapter(Activity activity, List<RssItem> article) {
		super(activity, 0, article);
	}

	// In order to populate the list items with data, we have to inflate the
	// current layout and get each view we want to populate.
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Activity activity = (Activity) getContext();
		LayoutInflater inflater = activity.getLayoutInflater();
		View rowView = null;

		rowView = inflater.inflate(R.layout.news_list_item, null);
		RssItem currentArticle = getItem(position);

		TextView item_title = (TextView) rowView.findViewById(R.id.news_list_item_title);
		TextView item_text = (TextView) rowView.findViewById(R.id.news_list_item_text);
		TextView item_date = (TextView) rowView.findViewById(R.id.news_list_item_date);
		FrameLayout item_color = (FrameLayout) rowView.findViewById(R.id.news_list_item_color);

		item_color.setBackgroundColor(currentArticle.getFeed().getColor());
		item_title.setText(currentArticle.getTitle());
		item_text.setText(currentArticle.getShortDescription());
		item_date.setText(currentArticle.getFormattedPubDate());

		return rowView;
	}
}
