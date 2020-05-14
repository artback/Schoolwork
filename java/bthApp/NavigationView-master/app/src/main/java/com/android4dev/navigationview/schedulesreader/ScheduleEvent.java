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

import com.android4dev.navigationview.BSKLOG;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class ScheduleEvent implements Comparable<ScheduleEvent>, BSKLOG {
	private long startDate;
	private long endDate;
	private String summary;
	private String contact;
	private String location;
	private String description;
	
	public ScheduleEvent() {}

	public long getStartDate() {
		return startDate;
	}
	public void setStartDate(long startDate) {
		this.startDate = startDate;
	}
	public long getEndDate() {
		return endDate;
	}
	public void setEndDate(long endDate) {
		this.endDate = endDate;
	}
	
	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public Calendar getDateAsCalendar(long millis) {
		Calendar calendar = new GregorianCalendar();
		calendar.setTimeInMillis(millis);
		return calendar;
	}
	
	public long parseDate(String date) {
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd'T'HHmmss");
		Calendar calendar = new GregorianCalendar();
		try {
			calendar.setTime(df.parse(date));
		} catch (ParseException e) {
			// Log.d("SLIST", "Unparseable date");
			e.printStackTrace();
		}
		return calendar.getTimeInMillis();
	}

	public int compareTo(ScheduleEvent item) {
		Long a = new Long(startDate);
		Long b = new Long(item.startDate);
		return a.compareTo(b);
	}
}
