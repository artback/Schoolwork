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

public class Schedule implements BSKLOG {
	private int id;
	private String name;
	private long startDate;
	private long endDate;
	private String summary;
	private String location;
	private String description;
	
	public Schedule() {}
	
	public Schedule(int id, String name, String startDate, String endDate) {
		this.id = id;
		this.name = name;
		this.startDate = this.parseDate(startDate);
		this.endDate = this.parseDate(endDate);
	}
	
	public Schedule(int id, String name, long startDate, long endDate) {
		this.id = id;
		this.name = name;
		this.startDate = startDate;
		this.endDate = endDate;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
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
			// Log.d(TAG, "Unparseable date");
			e.printStackTrace();
		}
		return calendar.getTimeInMillis();
	}
	
	public String toString() {
		return this.getName();
	}
}
