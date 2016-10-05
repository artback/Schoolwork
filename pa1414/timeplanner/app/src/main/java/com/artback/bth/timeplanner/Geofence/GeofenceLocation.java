package com.artback.bth.timeplanner.Geofence;


import android.content.Context;

import com.artback.bth.timeplanner.extendedcalendarview.CalendarProvider;
import com.google.android.gms.location.Geofence;

import java.util.Date;

public class GeofenceLocation {
	private final String id;
	private final double latitude;
	private final double longitude;
	private final float radius;
	private long expirationDuration;
	private int transitionType;
	private int loiteringDelay = 60000;

	public GeofenceLocation(String geofenceId, double latitude, double longitude,
							float radius, int transition) {
		this.id = geofenceId;
		this.latitude = latitude;
		this.longitude = longitude;
		this.radius = radius;
		this.transitionType = transition;
	}

	public String getId() {
		return id;
	}

	public double getLatitude() {
		return latitude;

	}

	public double getLongitude() {
		return longitude;
	}

	public float getRadius() {
		return radius;
	}

	public int getTransitionType() {
		return transitionType;
	}

	public com.google.android.gms.location.Geofence toGeofence() {
		com.google.android.gms.location.Geofence g = new com.google.android.gms.location.Geofence.Builder()
				.setRequestId(getId())
				.setTransitionTypes(transitionType)
				.setExpirationDuration(Geofence.NEVER_EXPIRE)
				.setCircularRegion(getLatitude(), getLongitude(), getRadius())
				.setLoiteringDelay(loiteringDelay).build();
		return g;
	}
}
