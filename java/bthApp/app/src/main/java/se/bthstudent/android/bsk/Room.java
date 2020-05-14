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

package se.bthstudent.android.bsk;

import java.io.Serializable;

public class Room implements Serializable {
	private static final long serialVersionUID = 1L;
	private String id;
	private String image;
	private String name;
	private int x;
	private int y;
	private int timestamp;
	private boolean updated;

	public Room(String id, String name, String image, int x, int y, int timestamp, boolean updated) {
		setId(id);
		setName(name);
		setImage(image);
		setX(x);
		setY(y);
		setTimestamp(timestamp);
		setUpdated(updated);
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(int timestamp) {
		this.timestamp = timestamp;
	}

	public boolean isUpdated() {
		return updated;
	}

	public void setUpdated(boolean updated) {
		this.updated = updated;
	}

}
