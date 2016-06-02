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

import se.bthstudent.android.bsk.BSKLOG;

public class VcardItem implements BSKLOG {
	private int position;
	private String name;
	private String organization;
	private String title;
	private String workphone;
	private String mobilephone;
	private String address;
	private String url;
	private String email;

	public VcardItem() {
	}

	public VcardItem(String url, int id) {
		this.setUrl(url);
		this.setPosition(id);
	}

	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}

	/**
	 * Gets the name for this instance.
	 * 
	 * @return The name.
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * Sets the name for this instance.
	 * 
	 * @param name
	 *            The name.
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Gets the organization for this instance.
	 * 
	 * @return The organization.
	 */
	public String getOrganization() {
		return this.organization;
	}

	/**
	 * Sets the organization for this instance.
	 * 
	 * @param organization
	 *            The organization.
	 */
	public void setOrganization(String organization) {
		this.organization = organization;
	}

	/**
	 * Gets the title for this instance.
	 * 
	 * @return The title.
	 */
	public String getTitle() {
		return this.title;
	}

	/**
	 * Sets the title for this instance.
	 * 
	 * @param title
	 *            The title.
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * Gets the workphone for this instance.
	 * 
	 * @return The workphone.
	 */
	public String getWorkphone() {
		return this.workphone;
	}

	/**
	 * Sets the workphone for this instance.
	 * 
	 * @param workphone
	 *            The workphone.
	 */
	public void setWorkphone(String workphone) {
		this.workphone = workphone;
	}

	/**
	 * Gets the mobilephone for this instance.
	 * 
	 * @return The mobilephone.
	 */
	public String getMobilephone() {
		return this.mobilephone;
	}

	/**
	 * Sets the mobilephone for this instance.
	 * 
	 * @param mobilephone
	 *            The mobilephone.
	 */
	public void setMobilephone(String mobilephone) {
		this.mobilephone = mobilephone;
	}

	/**
	 * Gets the address for this instance.
	 * 
	 * @return The address.
	 */
	public String getAddress() {
		return this.address;
	}

	/**
	 * Sets the address for this instance.
	 * 
	 * @param address
	 *            The address.
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * Gets the url for this instance.
	 * 
	 * @return The url.
	 */
	public String getUrl() {
		return this.url;
	}

	/**
	 * Sets the url for this instance.
	 * 
	 * @param url
	 *            The url.
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * Gets the email for this instance.
	 * 
	 * @return The email.
	 */
	public String getEmail() {
		return this.email;
	}

	/**
	 * Sets the email for this instance.
	 * 
	 * @param email
	 *            The email.
	 */
	public void setEmail(String email) {
		this.email = email;
	}
}
