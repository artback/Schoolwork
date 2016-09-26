package com.artback.bth.timeplanner;

import java.util.HashMap;

import android.text.format.DateUtils;

public class LocationStore {
	private static final long GEOFENCE_EXPIRATION_IN_HOURS = 12;
	public static final long GEOFENCE_EXPIRATION_IN_MILLISECONDS = GEOFENCE_EXPIRATION_IN_HOURS
			* DateUtils.HOUR_IN_MILLIS;
	protected HashMap<String, Geofence> geofences = new HashMap<String, Geofence>();
	private static LocationStore instance = new LocationStore();

	public static LocationStore getInstance() {
		return instance;
	}

	private LocationStore() {
		geofences.put("The Shire", new Geofence("The Shire", 51.663398, -0.209118,
				100, GEOFENCE_EXPIRATION_IN_MILLISECONDS,
				com.google.android.gms.location.Geofence.GEOFENCE_TRANSITION_ENTER
						| com.google.android.gms.location.Geofence.GEOFENCE_TRANSITION_DWELL
						| com.google.android.gms.location.Geofence.GEOFENCE_TRANSITION_EXIT));
	}

	public HashMap<String, Geofence> getSimpleGeofences() {
		return this.geofences;
	}
}