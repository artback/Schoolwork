package com.artback.bth.timeplanner;

import java.util.HashMap;

import android.text.format.DateUtils;

import com.google.android.gms.location.Geofence;

public class LocationStore {
	private static final long GEOFENCE_EXPIRATION_IN_HOURS = 12;
	public static final long GEOFENCE_EXPIRATION_IN_MILLISECONDS = GEOFENCE_EXPIRATION_IN_HOURS
			* DateUtils.HOUR_IN_MILLIS;
	protected HashMap<String, Location> geofences = new HashMap<String, Location>();
	private static LocationStore instance = new LocationStore();

	public static LocationStore getInstance() {
		return instance;
	}

	private LocationStore() {
		geofences.put("The Shire", new Location("The Shire", 51.663398, -0.209118,
				100, GEOFENCE_EXPIRATION_IN_MILLISECONDS,
				Geofence.GEOFENCE_TRANSITION_ENTER
						| Geofence.GEOFENCE_TRANSITION_DWELL
						| Geofence.GEOFENCE_TRANSITION_EXIT));
	}

	public HashMap<String, Location> getSimpleGeofences() {
		return this.geofences;
	}
}