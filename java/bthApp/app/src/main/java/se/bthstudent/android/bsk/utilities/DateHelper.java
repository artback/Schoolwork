/*
 *  This file is distributed under the GPL3 license.
 *  Please see the file LICENSE that should be present in this distribution.
 *
 *  Copyright 2011
 *   Jonas Hellström <jonas@if-then-else.se>
 *  
 *  Maintained by
 *   Jonas Hellström <jonas@if-then-else.se>
 *  since 2011
 */

package se.bthstudent.android.bsk.utilities;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import se.bthstudent.android.bsk.BSKLOG;

/**
 * This is a helper class to provide supporting methods of handling dates.
 * 
 * @author drlaban
 * 
 */
public class DateHelper implements BSKLOG {
	public DateHelper() {
	}

	/**
	 * This function takes a string and tries to parse it in a variety of formats. If a format is matched, a Date-object
	 * is returned. The Date object's date is set based on the date found in the argument.
	 * 
	 * @param dateString The date to parse.
	 * @return A Date object with the parsed date set.
	 */
	public Date parseDate(String dateString) {
		String[] dateFormatStrings = { "yyyyMMdd'T'HHmmss'Z'", "yyyyMMdd'T'HHmmss", "EEE, dd MMM yyyy HH:mm:ss Z" };
		Date d = null;
		for (String dateFormatString : dateFormatStrings) {
			try {
				// Log.d(TAG, "Parsing date: " +dateString);
				d = new SimpleDateFormat(dateFormatString, Locale.US).parse(dateString);
				// Log.d(TAG, "Parsing date succeeded");
			} catch (ParseException e) {
				// e.printStackTrace();
				// Log.d(TAG, "Unparseable date string: " +e);
			}
		}
		return d;
	}
}
