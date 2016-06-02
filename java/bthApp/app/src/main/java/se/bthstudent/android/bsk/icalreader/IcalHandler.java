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

package se.bthstudent.android.bsk.icalreader;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import se.bthstudent.android.bsk.BSKLOG;
import se.bthstudent.android.bsk.Feed;
import se.bthstudent.android.bsk.rssreader.Event;
import se.bthstudent.android.bsk.rssreader.RssItem;
import se.bthstudent.android.bsk.utilities.DateHelper;

/**
 * This class purpose is to process incoming feed objects, collect data based on the feed's URL and return a list
 * containing event objects.
 * 
 * @author drlaban
 * 
 */
public class IcalHandler implements BSKLOG {
	private RssItem mCurrent;
	private Feed mFeed;
	private ArrayList<RssItem> mList = new ArrayList<RssItem>();

	// Set feed object and call parsing process
	public IcalHandler(Feed inFeed) {
		this.mFeed = inFeed;
		ParseIcalString();
	}

	public void ParseIcalString() {
		try {
			// Prepare URL to fetch information from and collect data
			URL url = new URL(this.mFeed.getFeedUrl());
			URLConnection urlConnection = url.openConnection();
			BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));

			// Prepare variables and start looping through URL-collected data,
			// line-for-line
			String inputLine;
			boolean startBeforeToday = false;
			boolean endBeforeToday = false;
			while ((inputLine = in.readLine()) != null) {
				// Using a pattern, we sort out which keys we get from the iCal
				// data and store the value from a key we are interested in.
				Pattern p = Pattern.compile("(.*?):(.*)");
				Matcher m = p.matcher(inputLine);
				while (m.find()) {
					// Log.d(TAG, "m.group(1): " +m.group(1));
					// If the key contains "vevent", create a new Feed-object to
					// populate.
					if(m.group(1).equalsIgnoreCase("begin") && m.group(2).equalsIgnoreCase("vevent")) {
						// Log.d(TAG, "Creating new event...");
						mCurrent = new Event();
						mCurrent.setFeed(this.mFeed);
					}

					if(m.group(1).equalsIgnoreCase("summary")) {
						// Log.d(TAG, "Found a SUMMARY key. Setting to title: "
						// +m.group(2));
						mCurrent.setTitle(m.group(2));
					}

					// If the key contains "dtstart", parse the date to create
					// a storable Date object to the feed with the parsed date.
					if(m.group(1).equalsIgnoreCase("dtstart")) {
						// Log.d(TAG, "Found a DTSTART key. Converting date: "
						// +m.group(2));

						DateHelper dh = new DateHelper();
						Date parsedDate = dh.parseDate(m.group(2));
						if(parsedDate.before(Calendar.getInstance().getTime())) {
							startBeforeToday = true;
						}
						mCurrent.setPubDate(parsedDate);
					}

					// If the key contains "dtend", parse the date to create
					// a storable Date object to the feed with the parsed date.
					if(m.group(1).equalsIgnoreCase("dtend")) {
						// Log.d(TAG, "Found a DTEND key. Converting date: "
						// +m.group(2));

						// TODO Reuse the datehelper from the if-statement process
						// above
						DateHelper dh = new DateHelper();
						Date parsedDate = dh.parseDate(m.group(2));
						if(parsedDate.before(Calendar.getInstance().getTime())) {
							endBeforeToday = true;
						}
						mCurrent.setEndDate(parsedDate);
					}

					// If the key contains "end", add the up to now processed
					// feed object to an arraylist.
					if(m.group(1).equalsIgnoreCase("end") && m.group(2).equalsIgnoreCase("vevent") && (startBeforeToday == false || endBeforeToday == false)) {
						this.mList.add(mCurrent);
						startBeforeToday = endBeforeToday = false;
					}
				}
			}
			in.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	// Returns a list of feed objects
	public ArrayList<RssItem> getList() {
		return this.mList;
	}

	// Parses an incoming string in a number of different formats, in order to
	// return a Date object.
	Date tryParse(String dateString) {
		String[] dateFormatStrings = { "yyyyMMdd'T'HHmmss'Z'", "yyyyMMdd'T'HHmmss" };
		Date d = null;
		for (String dateFormatString : dateFormatStrings) {
			try {
				d = new SimpleDateFormat(dateFormatString).parse(dateString);
			} catch (ParseException e) {
				e.printStackTrace();
				// Log.d(TAG, "Unparseable date string: " + e);
			}
		}
		return d;
	}

}
