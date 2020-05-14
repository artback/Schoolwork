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

package se.bthstudent.android.bsk.vcardreader;

import java.util.ArrayList;
import java.util.List;

import se.bthstudent.android.bsk.BSKLOG;
import se.bthstudent.android.bsk.R;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * This class adds the different views to the list layout based on the fetched and processed data from the contacts
 * feeds. The adapter loads this dynamically when scrolling through the list of items.
 * 
 * @author drlaban
 * 
 */
public class VcardAdapter extends ArrayAdapter<String[]> implements BSKLOG {
	List<String[]> itemList = new ArrayList<String[]>();

	public VcardAdapter(Activity activity, List<String[]> vcard) {
		super(activity, 0, vcard);
	}

	// In order to populate the list items with data, we have to inflate the
	// current layout and get each view we want to populate.
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Activity activity = (Activity) getContext();
		LayoutInflater inflater = activity.getLayoutInflater();
		View rowView = null;
		String[] currentString = getItem(position);
		// If the item to display contains a "header" string, we insert a title divider,
		// else we insert a regular item and populate it with contact data.
		if(currentString[0].equals("header")) {
			rowView = inflater.inflate(R.layout.list_divider, null);
			TextView item = (TextView) rowView.findViewById(R.id.list_divider_text);
			item.setText(currentString[1]);
		} else {
			rowView = inflater.inflate(R.layout.contacts_list_item, null);
			TextView item = (TextView) rowView.findViewById(R.id.contacts_list_item_title);
			item.setText(currentString[1]);
		}
		return rowView;
	}
}