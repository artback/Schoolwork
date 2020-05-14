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

import com.android4dev.navigationview.rssreader.RssItem;

import java.util.List;

public class Feed {
	private String title;
	private String feedUrl;
	private String websiteUrl;
	private String type;
	private int icon;
	private int color;
	private List<RssItem> items;
	private String encoding;

	public Feed(String title, String feedUrl, String websiteUrl, String type, int icon, int color, String encoding) {
		super();
		this.title = title;
		this.feedUrl = feedUrl;
		this.websiteUrl = websiteUrl;
		this.type = type;
		this.icon = icon;
		this.color = color;
		this.encoding = encoding;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getFeedUrl() {
		return feedUrl;
	}

	public void setFeedUrl(String feedUrl) {
		this.feedUrl = feedUrl;
	}

	public String getWebsiteUrl() {
		return websiteUrl;
	}

	public void setWebsiteUrl(String websiteUrl) {
		this.websiteUrl = websiteUrl;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getIcon() {
		return icon;
	}

	public void setIcon(int icon) {
		this.icon = icon;
	}

	public int getColor() {
		return color;
	}

	public void setColor(int color) {
		this.color = color;
	}

	public List<RssItem> getItems() {
		return items;
	}

	public void setItems(List<RssItem> items) {
		this.items = items;
	}

	public String getEncoding() {
		return encoding;
	}

	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}
}
