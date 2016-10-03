package com.artback.bth.timeplanner;

import java.util.HashMap;

import android.text.format.DateUtils;

public class GeofenceLocationStore {
	private static final long GEOFENCE_EXPIRATION_IN_HOURS = 12;
	public static final long GEOFENCE_EXPIRATION_IN_MILLISECONDS = GEOFENCE_EXPIRATION_IN_HOURS
			* DateUtils.HOUR_IN_MILLIS;
	protected HashMap<String, GeofenceLocation> geofences = new HashMap<String, GeofenceLocation>();
	private static GeofenceLocationStore instance = new GeofenceLocationStore();
	public static GeofenceLocationStore getInstance() {
		return instance;
	}

	private GeofenceLocationStore() {
		geofences.put("Ericsson", new GeofenceLocation("Ericsson", 56.164500 , 15.593149,
				100, GEOFENCE_EXPIRATION_IN_MILLISECONDS,
				com.google.android.gms.location.Geofence.GEOFENCE_TRANSITION_DWELL
						| com.google.android.gms.location.Geofence.GEOFENCE_TRANSITION_EXIT));
		geofences.put("Fijutsi", new GeofenceLocation("fijutsi", 56.167982 , 15.587184,
				100, GEOFENCE_EXPIRATION_IN_MILLISECONDS,
				com.google.android.gms.location.Geofence.GEOFENCE_TRANSITION_DWELL
						| com.google.android.gms.location.Geofence.GEOFENCE_TRANSITION_EXIT));
		geofences.put("Telenor", new GeofenceLocation("Telenor", 56.184311, 15.592057,
				100, GEOFENCE_EXPIRATION_IN_MILLISECONDS,
				com.google.android.gms.location.Geofence.GEOFENCE_TRANSITION_DWELL
						| com.google.android.gms.location.Geofence.GEOFENCE_TRANSITION_EXIT));

	}

	public HashMap<String, GeofenceLocation> getSimpleGeofences() {
		return this.geofences;
	}
}