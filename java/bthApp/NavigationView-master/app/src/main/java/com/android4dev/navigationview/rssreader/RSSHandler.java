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

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

import com.android4dev.navigationview.BSKLOG;
import com.android4dev.navigationview.Feed;
import com.android4dev.navigationview.utilities.DateHelper;

public class RSSHandler extends DefaultHandler implements BSKLOG {
	// Feed and Article objects to use for temporary storage
	private RssItem mCurrent;

	// Number of articles added so far
	private int mItemsAdded = 0;

	// Number of articles to download
	// NOTE: should we use this limit?
	private static final int ITEMS_LIMIT = 15;

	private Feed mFeed;
	private ArrayList<RssItem> mList = new ArrayList<RssItem>();

	private boolean mTitleStarted = false;
	private StringBuilder mTitle = new StringBuilder();

	// Current characters being accumulated
	StringBuffer chars = new StringBuffer();

	boolean mBeforeToday = false;

	public RSSHandler(Feed inFeed) {
		this.mFeed = inFeed;
		// If the feed type is "rssnews" we create an object of the type Article
		// If the feed type is "rsscal" we create an object of the type Event
		// If none of the above applies, we create an object of the type RssItem
		if(inFeed.getType().equalsIgnoreCase("rssnews")) {
			mCurrent = new Article();
		} else if(inFeed.getType().equalsIgnoreCase("rsscal")) {
			mCurrent = new Event();
		} else {
			mCurrent = new RssItem();
		}
	}

	/*
	 * This method is called every time a start element is found (an opening XML marker) here we always reset the
	 * characters StringBuffer as we are only currently interested in the the text values stored at leaf nodes
	 * 
	 * (non-Javadoc)
	 * 
	 * @see org.xml.sax.helpers.DefaultHandler#startElement(java.lang.String, java.lang.String, java.lang.String,
	 * org.xml.sax.Attributes)
	 */
	public void startElement(String uri, String localName, String qName, Attributes atts) {
		chars = new StringBuffer();
		if(localName.equals("title")) {
			mTitleStarted = true;
		}
	}

	/*
	 * This method is called every time an end element is found (a closing XML marker) here we check what element is
	 * being closed, if it is a relevant leaf node that we are checking, such as Title, then we get the characters we
	 * have accumulated in the StringBuffer and set the current Article's title to the value
	 * 
	 * If this is closing the "Item", it means it is the end of the article, so we add that to the list and then reset
	 * our Article object for the next one on the stream
	 * 
	 * 
	 * (non-Javadoc)
	 * 
	 * @see org.xml.sax.helpers.DefaultHandler#endElement(java.lang.String, java.lang.String, java.lang.String)
	 */
	public void endElement(String uri, String localName, String qName) throws SAXException {
		if(localName.equalsIgnoreCase("title")) {
			// Unfortunately the SAX-parser takes "<a href>" links into account, which means that the
			// title, if it contains for instance <a href>-tags will affect the final title string
			mTitleStarted = false;
			mCurrent.setTitle(mTitle.toString());
			mTitle.setLength(0);
		} else if(localName.equalsIgnoreCase("description")) {
			mCurrent.setDescription(chars.toString());
			mCurrent.setShortDescription(chars.toString());
		} else if(localName.equalsIgnoreCase("pubdate")) {
			DateHelper dh = new DateHelper();
			Date parsedDate = dh.parseDate(chars.toString());
			if(parsedDate.before(Calendar.getInstance().getTime()) && mCurrent instanceof Event) {
				mBeforeToday = true;
			}
			mCurrent.setPubDate(parsedDate);
		} else if(localName.equalsIgnoreCase("encoded")) {
			mCurrent.setEncodedContent(chars.toString());
		} else if(localName.equalsIgnoreCase("item")) {
			// NOTE: will we use this?
		} else if(localName.equalsIgnoreCase("link")) {
			try {
				mCurrent.setUrl(new URL(chars.toString()));
			} catch (MalformedURLException e) {
				//Log.d(TAG, "RssItem url malformed, "+e.getMessage());
			}
		} else if(localName.equalsIgnoreCase("creator")) {
			mCurrent.setAuthor(chars.toString());
		}

		// Check if looking for article, and if article is complete
		if(localName.equalsIgnoreCase("item") && mBeforeToday == false) {
			mCurrent.setFeed(this.mFeed);

			// Lets check if we've hit our limit on number of articles
			// NOTE: should we use this limit?
			mItemsAdded++;
			if(mItemsAdded <= ITEMS_LIMIT) {
				mList.add(mCurrent);
				mBeforeToday = false;
			}

			if(this.mFeed.getType().equalsIgnoreCase("rssnews")) {
				mCurrent = new Article();
			} else if(this.mFeed.getType().equalsIgnoreCase("rsscal")) {
				mCurrent = new Event();
			} else {
				mCurrent = new RssItem();
			}
		}
	}

	/*
	 * This method is called when characters are found in between XML markers, however, there is no guarantee that this
	 * will be called at the end of the node (from the start to the end of a tag), or that it will be called only once,
	 * so we just accumulate these and then deal with them in endElement() to be sure we have all the text
	 * 
	 * (non-Javadoc)
	 * 
	 * @see org.xml.sax.helpers.DefaultHandler#characters(char[], int, int)
	 */
	public void characters(char ch[], int start, int length) {
		char emdash = 150;
		char minus = 45;
		char rdquo = 148;
		char doublequote = 34;
		for (int i = 0; i < ch.length; i++) {
			// TODO Remove this when the use of non compatible ISO-8859-1 characters stops
			// or when UTF-8 is fully supported
			// Character table chart: http://www.alanwood.net/demos/ansi.html
			if(ch[i] == emdash) {
				ch[i] = minus;
				// For debugging purpose
				// Log.d(TAG, "Found character: " +Integer.toString(ch[i]));
			} else if(ch[i] == rdquo) {
				ch[i] = doublequote;
			}
		}
		chars.append(new String(ch, start, length));
		if(mTitleStarted) {
			mTitle.append(new String(ch, start, length));
		}
	}

	/**
	 * This is the entry point to the parser and creates the feed to be parsed
	 * 
	 * @param feedUrl
	 * @return
	 */
	public Boolean getLatestArticles() {
		boolean success = true;
		// Log.d(TAG, "Starting RSSHandler.getLatestArticles...");
		try {
			SAXParserFactory spf = SAXParserFactory.newInstance();
			SAXParser sp = spf.newSAXParser();
			XMLReader xr = sp.getXMLReader();

			URL url = new URL(this.mFeed.getFeedUrl());

			xr.setContentHandler(this);
			InputSource is = new InputSource(url.openStream());
			is.setEncoding(this.mFeed.getEncoding());

			xr.parse(is);
		} catch (IOException e) {
			// Log.d(TAG, "RSS Handler IO "+e.getMessage() + " >> " + e.toString());
			success = false;
		} catch (SAXException e) {
			// Log.d(TAG, "RSS Handler SAX "+e.toString());
			success = false;
		} catch (ParserConfigurationException e) {
			// Log.d(TAG, "RSS Handler Parser Config "+e.toString());
			success = false;
		}
		return success;
	}

	public ArrayList<RssItem> getList() {
		return this.mList;
	}
}
