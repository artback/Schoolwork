package com.artback.bth.timeplanner.Geofence;

import java.util.HashMap;

import com.google.android.gms.location.Geofence;
//TODO: rebuild class for sqlite savings

public class GeofenceLocationProvider {
	private HashMap<String, GeofenceLocation> geofences = new HashMap<String, GeofenceLocation>();
	private static GeofenceLocationProvider instance = new GeofenceLocationProvider();
	public static GeofenceLocationProvider getInstance() {
		return instance;
	}

	private GeofenceLocationProvider() {
		geofences.put("Ericsson", new GeofenceLocation("Ericsson", 56.164500 , 15.593149,
				100,
				com.google.android.gms.location.Geofence.GEOFENCE_TRANSITION_ENTER
						| com.google.android.gms.location.Geofence.GEOFENCE_TRANSITION_EXIT));
		geofences.put("Fujitsu", new GeofenceLocation("Fujitsu", 56.167982 , 15.587184,
				100,
				com.google.android.gms.location.Geofence.GEOFENCE_TRANSITION_ENTER
						| com.google.android.gms.location.Geofence.GEOFENCE_TRANSITION_EXIT));
		geofences.put("Telenor", new GeofenceLocation("Telenor", 56.1843281, 15.5917499,
				200, Geofence.GEOFENCE_TRANSITION_ENTER
						| com.google.android.gms.location.Geofence.GEOFENCE_TRANSITION_EXIT));
		geofences.put("BTH", new GeofenceLocation("BTH", 56.182302,15.590573,
				200, com.google.android.gms.location.Geofence.GEOFENCE_TRANSITION_ENTER
						| com.google.android.gms.location.Geofence.GEOFENCE_TRANSITION_EXIT));
		geofences.put("HOME", new GeofenceLocation("HOME", 56.680852,16.236984,
				500, com.google.android.gms.location.Geofence.GEOFENCE_TRANSITION_ENTER
				| com.google.android.gms.location.Geofence.GEOFENCE_TRANSITION_EXIT));

	}

	public HashMap<String, GeofenceLocation> getGeofencesLocations() {
		return this.geofences;
	}
}