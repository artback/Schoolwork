package com.artback.bth.timeplanner.Geofence;

import java.util.List;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import com.artback.bth.timeplanner.MainActivity;
import com.artback.bth.timeplanner.extendedcalendarview.Event;
import com.google.android.gms.location.GeofencingEvent;

public class GeofenceReceiver extends IntentService {
	public static final int NOTIFICATION_ID = 1;

	public GeofenceReceiver() {
		super("GeofenceReceiver");
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		
		GeofencingEvent geoEvent = GeofencingEvent.fromIntent(intent);
		if (geoEvent.hasError()) {
			Log.d(MainActivity.TAG, "Error GeofenceReceiver.onHandleIntent");
		} else {
			Log.d(MainActivity.TAG, "GeofenceReceiver : Transition -> "
					+ geoEvent.getGeofenceTransition());

			int transitionType = geoEvent.getGeofenceTransition();

			if (transitionType == com.google.android.gms.location.Geofence.GEOFENCE_TRANSITION_ENTER
					|| transitionType == com.google.android.gms.location.Geofence.GEOFENCE_TRANSITION_DWELL
					|| transitionType == com.google.android.gms.location.Geofence.GEOFENCE_TRANSITION_EXIT) {
				List<com.google.android.gms.location.Geofence> triggerList = geoEvent.getTriggeringGeofences();

				for (com.google.android.gms.location.Geofence geofence : triggerList) {
					GeofenceLocation loc = GeofenceLocationProvider.getInstance()
							.getSimpleGeofences().get(geofence.getRequestId());

					String transitionName = "";
					switch (transitionType) {
					case com.google.android.gms.location.Geofence.GEOFENCE_TRANSITION_DWELL:
						transitionName = "dwell";
						break;

					case com.google.android.gms.location.Geofence.GEOFENCE_TRANSITION_ENTER:
						transitionName = "enter";
						Event.startNewEvent(this,loc.getId());
						break;

					case com.google.android.gms.location.Geofence.GEOFENCE_TRANSITION_EXIT:
						transitionName = "exit";
						Event.endEvent(this,loc.getId());
						break;
					}

					GeofenceNotification geofenceNotification = new GeofenceNotification(
							this);
					geofenceNotification
							.displayNotification(loc, transitionType);
				}
			}
		}
	}
}
