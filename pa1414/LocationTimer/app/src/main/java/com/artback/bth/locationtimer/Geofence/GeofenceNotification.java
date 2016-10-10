package com.artback.bth.locationtimer.Geofence;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.artback.bth.timeplanner.R;

public class GeofenceNotification {
	public static final int NOTIFICATION_ID = 20;

	protected Context context;

	protected NotificationManager notificationManager;
	protected Notification notification;

	public GeofenceNotification(Context context) {
		this.context = context;

		this.notificationManager = (NotificationManager) context
				.getSystemService(Context.NOTIFICATION_SERVICE);
	}

	protected void buildNotificaction(GeofenceLocation loc,
			int transitionType) {

		String notificationText = "";
		Object[] notificationTextParams = new Object[] { loc.getId() };

		switch (transitionType) {
		case com.google.android.gms.location.Geofence.GEOFENCE_TRANSITION_DWELL:
			notificationText = String.format(
					context.getString(R.string.geofence_enter),
					notificationTextParams);

			break;

		case com.google.android.gms.location.Geofence.GEOFENCE_TRANSITION_ENTER:
			notificationText = String.format(
					context.getString(R.string.geofence_enter),
					notificationTextParams);
			break;

		case com.google.android.gms.location.Geofence.GEOFENCE_TRANSITION_EXIT:
			notificationText = String.format(
					context.getString(R.string.geofence_exit),
					notificationTextParams);
			break;
		}
		Log.d(this.getClass().getName(), notificationText);

		NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(
				context)
				.setSmallIcon(R.drawable.ic_notification)
				.setContentTitle(context.getString(R.string.app_name))
				.setContentText(notificationText).setAutoCancel(true);

		notification = notificationBuilder.build();
		notification.defaults |= Notification.DEFAULT_LIGHTS;
		notification.defaults |= Notification.DEFAULT_SOUND;
		notification.defaults |= Notification.DEFAULT_VIBRATE;
	}

	public void displayNotification(GeofenceLocation loc,
			int transitionType) {
		buildNotificaction(loc , transitionType);

		notificationManager.notify(NOTIFICATION_ID, notification);
	}
}
