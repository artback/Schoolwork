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

package se.bthstudent.android.bsk.rssreader;

import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import se.bthstudent.android.bsk.BSKLOG;
import se.bthstudent.android.bsk.Feed;

public class RssItem implements BSKLOG, Comparable<RssItem> {

	private long articleId;
	private long feedId;
	private String title;
	private String description;
	private String shortDescription;
	private Date pubDate;
	private Date endDate;
	private URL url;
	private String encodedContent;
	private int color;
	private Feed feed;
	private String author;

	/**
	 * @return the articleId
	 */
	public long getArticleId() {
		return articleId;
	}

	/**
	 * @param articleId the articleId to set
	 */
	public void setArticleId(long articleId) {
		this.articleId = articleId;
	}

	/**
	 * @return the feedId
	 */
	public long getFeedId() {
		return feedId;
	}

	/**
	 * @param feedId the feedId to set
	 */
	public void setFeedId(long feedId) {
		this.feedId = feedId;
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return the url
	 */
	public URL getUrl() {
		return url;
	}

	/**
	 * @param url the URL to set
	 */
	public void setUrl(URL url) {
		this.url = url;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		description = description.trim();
		this.description = description;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	public void setShortDescription(String description) {
		description = description.trim();
		String shortDescription = description;
		if(description.length() > 100) {
			shortDescription = description.substring(0, 100) + "...";
		}
		this.shortDescription = shortDescription;
	}

	public String getShortDescription() {
		return shortDescription;
	}

	/**
	 * @param pubDate the pubDate to set
	 */
	public void setPubDate(Date pubDate) {
		this.pubDate = pubDate;
	}

	/**
	 * @return the pubDate
	 */
	public Date getPubDate() {
		return pubDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	/**
	 * @return the pubDate
	 */
	public Date getEndDate() {
		return endDate;
	}

	public String getFormattedPubDate() {
		DateFormat date = new SimpleDateFormat("EEE, dd/MM yyyy, HH:mm");
		Calendar calendar = Calendar.getInstance();
		String readableDate = date.format(pubDate);
		calendar.setTime(this.getPubDate());

		// This is done to provide an array of days for translating the day of week numerical
		// to a string.
		// String[] weekDays = {"", "Söndag", "Måndag", "Tisdag", "Onsdag", "Torsdag", "Fredag", "Lördag"};

		// readableDate = calendar.get(Calendar.DAY_OF_WEEK)+ ", " +readableDate;
		return readableDate;
	}

	/**
	 * @param encodedContent the encodedContent to set
	 */
	public void setEncodedContent(String encodedContent) {
		this.encodedContent = encodedContent;
	}

	/**
	 * @return the encodedContent
	 */
	public String getEncodedContent() {
		return encodedContent;
	}

	public int getColor() {
		return color;
	}

	public void setColor(int color) {
		this.color = color;
	}

	public Feed getFeed() {
		return feed;
	}

	public void setFeed(Feed feed) {
		this.feed = feed;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	@Override
	public int compareTo(RssItem item) {
		return item.getPubDate().compareTo(getPubDate());
	}
}
