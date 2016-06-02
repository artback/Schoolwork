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

package com.android4dev.navigationview;

import android.app.Application;

import com.android4dev.navigationview.rssreader.RssItem;
import com.android4dev.navigationview.schedulesreader.Schedule;

import java.util.ArrayList;

/**
 * This object contains data for the purpose of being available through the entire app.
 *
 * @author drlaban
 *
 */
public class BskData extends Application implements BSKLOG{
    private ArrayList<Feed> newsList = new ArrayList<Feed>();
    private ArrayList<Feed> calendarList = new ArrayList<Feed>();
    private ArrayList<Schedule> schedules;
    private RssItem currentArticle;
    private boolean modified;
    private int nrOfSchedules;
    private boolean changedNewsFeeds;
    private boolean changedCalendarFeeds;
    private boolean locationFix;

    @Override
    public void onCreate() {
        // reinitialize variable
    }

    public ArrayList<Feed> getNewsList() {
        return newsList;
    }

    public void setNewsList(ArrayList<Feed> newsList) {
        this.newsList = newsList;
    }

    public ArrayList<Feed> getCalendarList() {
        return calendarList;
    }

    public void setCalendarList(ArrayList<Feed> calendarList) {
        this.calendarList = calendarList;
    }

    public ArrayList<Schedule> getSchedules() {
        return schedules;
    }

    public void setSchedules(ArrayList<Schedule> schedules) {
        this.schedules = schedules;
    }

    public boolean isModified() {
        return modified;
    }

    public void setModified(boolean modified) {
        this.modified = modified;
    }

    public int getNrOfSchedules() {
        return nrOfSchedules;
    }

    public void setNrOfSchedules(int nrOfSchedules) {
        this.nrOfSchedules = nrOfSchedules;
    }

    public RssItem getCurrentArticle() {
        return currentArticle;
    }

    public void setCurrentArticle(RssItem currentArticle) {
        this.currentArticle = currentArticle;
    }

    public boolean isChangedNewsFeeds() {
        return changedNewsFeeds;
    }

    public void setChangedNewsFeeds(boolean changedNewsFeeds) {
        this.changedNewsFeeds = changedNewsFeeds;
    }

    public boolean isChangedCalendarFeeds() {
        return changedCalendarFeeds;
    }

    public void setChangedCalendarFeeds(boolean changedCalendarFeeds) {
        this.changedCalendarFeeds = changedCalendarFeeds;
    }

    public boolean isLocationFix() {
        return locationFix;
    }

    public void setLocationFix(boolean locationFix) {
        this.locationFix = locationFix;
    }
}