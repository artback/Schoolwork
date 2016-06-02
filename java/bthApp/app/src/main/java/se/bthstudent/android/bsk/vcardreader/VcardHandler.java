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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import org.xml.sax.helpers.DefaultHandler;

import se.bthstudent.android.bsk.BSKLOG;
import se.bthstudent.android.bsk.R;
import a_vcard.android.syncml.pim.PropertyNode;
import a_vcard.android.syncml.pim.VDataBuilder;
import a_vcard.android.syncml.pim.VNode;
import a_vcard.android.syncml.pim.vcard.VCardException;
import a_vcard.android.syncml.pim.vcard.VCardParser;
import android.content.Context;

/**
 * This class contains functionality for reading, parsing and processing data from a Vcard-feed.
 * 
 * @author drlaban
 * 
 */
public class VcardHandler extends DefaultHandler implements BSKLOG {
	private ArrayList<VcardItem> mVcardList = new ArrayList<VcardItem>();

	private String mVcard;
	VcardItem mCurrentVcard = null;
	private int mAttempts;
	private boolean mParseFailed = false;
	private Context mExtContext;

	public VcardHandler(Context context) {
		mAttempts = 0;
		this.mExtContext = context;
	}

	public ArrayList<VcardItem> getVcardList() {
		return this.mVcardList;
	}

	/**
	 * This method takes an inputstream and puts everything it gets into a string.
	 * 
	 * @param is Inputstream to process
	 * @return Returns an appended string based on what was fetched from the inputstream.
	 * @throws UnsupportedEncodingException
	 */
	private static String convertStreamToString(InputStream is) throws UnsupportedEncodingException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(is, "ISO-8859-1"));
		StringBuilder sb = new StringBuilder();
		String line = null;
		try {
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return sb.toString();
	}

	/**
	 * This method takes a URL and an ID to fetch and process contact data
	 * 
	 * @param url The URL to fetch data from.
	 * @param id The id corresponding the contact card to connect the fetched data to.
	 * @return
	 */
	public boolean getVcardValues(String url, int id) {
		boolean succeeded = true;
		if(mAttempts < 5) {
			if(mParseFailed == false && mCurrentVcard == null) {
				mCurrentVcard = new VcardItem(url, id + 1);
			}
			URLConnection feedUrl;
			try {
				feedUrl = new URL(url).openConnection();
				InputStream in = feedUrl.getInputStream();
				mVcard = convertStreamToString(in);
			} catch (MalformedURLException e) {
				// Log.d(TAG,"MALFORMED URL EXCEPTION");
			} catch (IOException e) {
				e.printStackTrace();
			}

			// TODO Clean this section up!
			VCardParser parser = new VCardParser();
			VDataBuilder builder = new VDataBuilder();
			try {
				boolean parsed = parser.parse(mVcard, "ISO-8859-1", builder);
				// TODO Remove later!! This is only here because the parsed
				// variable would be considered "unused" otherwise
				if(parsed == true) {
					parsed = true;
					mParseFailed = false;
				}
			} catch (VCardException e) {
				e.printStackTrace();
				mAttempts++;
				mParseFailed = true;
				succeeded = false;
				getVcardValues(url, mCurrentVcard.getPosition());
				// Log.d(TAG, "VcardException");
			} catch (IOException e) {
				mAttempts++;
				mParseFailed = true;
				succeeded = false;
				getVcardValues(url, mCurrentVcard.getPosition());
				// Log.d(TAG, "IOException");
			} catch (Exception e) {
				mAttempts++;
				mParseFailed = true;
				succeeded = false;
				getVcardValues(url, mCurrentVcard.getPosition());
				// Log.d(TAG, "Other exception");
			}

			if(mParseFailed != true) {
				// get all parsed contacts
				List<VNode> pimContacts = builder.vNodeList;

				for (VNode contact : pimContacts) {
					ArrayList<PropertyNode> props = contact.propList;

					// contact name/full name - FN property
					boolean workphoneIsSet = false;
					for (PropertyNode prop : props) {
						if("FN".equals(prop.propName)) {
							mCurrentVcard.setName(this.nullCheck(prop.propValue));
						} else if("ORG".equals(prop.propName)) {
							mCurrentVcard.setOrganization(this.nullCheck(prop.propValue));
						} else if("TITLE".equals(prop.propName)) {
							mCurrentVcard.setTitle(this.nullCheck(prop.propValue));
						} else if("TEL".equals(prop.propName)) {
							if(!workphoneIsSet) {
								mCurrentVcard.setWorkphone(this.nullCheck(prop.propValue));
								// Log.d("VCARDTAG", "current.workphone: "
								// +current.getWorkphone());
								workphoneIsSet = true;
							} else {
								mCurrentVcard.setMobilephone(this.nullCheck(prop.propValue));
								// Log.d("VCARDTAG", "current.mobilephone: "
								// +current.getMobilephone());
							}
						} else if("ADR".equals(prop.propName)) {
							mCurrentVcard.setAddress(this.nullCheck(prop.propValue.replace(";", " ").trim()));
						} else if("URL".equals(prop.propName)) {
							mCurrentVcard.setUrl(this.nullCheck(prop.propValue));
						} else if("EMAIL".equals(prop.propName)) {
							mCurrentVcard.setEmail(this.nullCheck(prop.propValue));
						}
					}
				}
				this.mVcardList.add(mCurrentVcard);
				mCurrentVcard = null;
				succeeded = true;
			}
		}
		return succeeded;
	}

	/**
	 * If a value is empty, we set a default value, provided in resources.
	 * 
	 * @param value The value to check.
	 * @return A default string if the incoming string is empty.
	 */
	private String nullCheck(String value) {
		if(value.equals("") || value == null) {
			value = this.mExtContext.getApplicationContext().getResources().getString(R.string.not_specified);
		}
		return value;
	}
}
