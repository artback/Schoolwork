package com.artback.bth.timeplanner.Geofence;

import java.util.HashMap;
import java.util.Map;

import android.Manifest;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.location.Location;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.widget.Toast;


import com.artback.bth.timeplanner.MainActivity;
import com.artback.bth.timeplanner.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.GeofenceStatusCodes;
import com.google.android.gms.location.GeofencingRequest;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

public class GeolocationService extends Service implements ConnectionCallbacks,
		OnConnectionFailedListener, LocationListener, ResultCallback<Status> {
	public static final long UPDATE_INTERVAL_IN_MILLISECONDS = 10000;
	public static final long FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS = UPDATE_INTERVAL_IN_MILLISECONDS / 5;
	private static final int REQUEST_LOCATION =3;
	protected GoogleApiClient mGoogleApiClient;
	protected LocationRequest mLocationRequest;

	private PendingIntent mPendingIntent;

	@Override
	public void onStart(Intent intent, int startId) {
		buildGoogleApiClient();

		mGoogleApiClient.connect();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();

		if (mGoogleApiClient.isConnected()) {
			mGoogleApiClient.disconnect();
		}

	}

	protected void registerGeofences() {
		if (MainActivity.geofencesAlreadyRegistered) {
			return;
		}


		HashMap<String, GeofenceLocation> geofences = GeofenceLocationProvider
				.getInstance().getGeofencesLocations();

		GeofencingRequest.Builder geofencingRequestBuilder = new GeofencingRequest.Builder();
		for (Map.Entry<String, GeofenceLocation> item : geofences.entrySet()) {
			GeofenceLocation loc = item.getValue();

			geofencingRequestBuilder.addGeofence(loc.toGeofence());
		}

		geofencingRequestBuilder.setInitialTrigger(GeofencingRequest.INITIAL_TRIGGER_ENTER);
		GeofencingRequest geofencingRequest = geofencingRequestBuilder.build();
		mPendingIntent = requestPendingIntent();

		if (ActivityCompat.checkSelfPermission(this,
				Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
			if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION)!=
					PackageManager.PERMISSION_GRANTED ) {
				ActivityCompat.requestPermissions(new String{Manifest.permission.ACCESS_FINE_LOCATION},REQUEST_LOCATION);
			}
			// TODO: Consider calling
			//    ActivityCompat#requestPermissions
			// here to request the missing permissions, and then overriding
			//   public void onRequestPermissionsResult(int requestCode, String[] permissions,
			//                                          int[] grantResults)
			// to handle the case where the user grants the permission. See the documentation
			// for ActivityCompat#requestPermissions for more details.
			return;
		}

		LocationServices.GeofencingApi.addGeofences(mGoogleApiClient,
				geofencingRequest, mPendingIntent).setResultCallback(this);
		MainActivity.geofencesAlreadyRegistered = true;
		Log.d(this.getClass().getName(), "Registering Geofences");
	}

	private PendingIntent requestPendingIntent() {

		if (null != mPendingIntent) {

			return mPendingIntent;
		} else {

			Intent intent = new Intent(this, GeofenceReceiver.class);
			return PendingIntent.getService(this, 0, intent,
					PendingIntent.FLAG_UPDATE_CURRENT);

		}
	}

	public void broadcastLocationFound(Location location) {
		Intent intent = new Intent("com.artback.bth.timeplanner.geolocation.service");
		intent.putExtra("latitude", location.getLatitude());
		intent.putExtra("longitude", location.getLongitude());
		intent.putExtra("done", 1);
		sendBroadcast(intent);
	}

	protected void startLocationUpdates() {
		if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
				!= PackageManager.PERMISSION_GRANTED &&
				ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
			// TODO: Consider calling
			//    ActivityCompat#requestPermissions
			// here to request the missing permissions, and then overriding
			//   public void onRequestPermissionsResult(int requestCode, String[] permissions,
			//                                          int[] grantResults)
			// to handle the case where the user grants the permission. See the documentation
			// for ActivityCompat#requestPermissions for more details.
			return;
		}
		LocationServices.FusedLocationApi.requestLocationUpdates(
				mGoogleApiClient, mLocationRequest, this);
	}

	protected void stopLocationUpdates() {
		LocationServices.FusedLocationApi.removeLocationUpdates(
				mGoogleApiClient, this);
	}

	@Override
	public void onConnected(Bundle connectionHint) {
		Log.i(this.getClass().getName(), "Connected to GoogleApiClient");
		startLocationUpdates();
	}

	@Override
	public void onLocationChanged(Location location) {
		Log.d(this.getClass().getName(),
				"new location : " + location.getLatitude() + ", "
						+ location.getLongitude() + ". "
						+ location.getAccuracy());
		broadcastLocationFound(location);

		if (!MainActivity.geofencesAlreadyRegistered) {
			registerGeofences();
		}
	}

	@Override
	public void onConnectionSuspended(int cause) {
		Log.i(this.getClass().getName(), "Connection suspended");
		mGoogleApiClient.connect();
	}

	@Override
	public void onConnectionFailed(ConnectionResult result) {
		Log.i(this.getClass().getName(),
				"Connection failed: ConnectionResult.getErrorCode() = "
						+ result.getErrorCode());
	}

	protected synchronized void buildGoogleApiClient() {
		Log.i(this.getClass().getName(), "Building GoogleApiClient");
		mGoogleApiClient = new GoogleApiClient.Builder(this)
				.addConnectionCallbacks(this)
				.addOnConnectionFailedListener(this)
				.addApi(LocationServices.API).build();
		createLocationRequest();
	}

	protected void createLocationRequest() {
		mLocationRequest = new LocationRequest();
		mLocationRequest.setInterval(UPDATE_INTERVAL_IN_MILLISECONDS);
		mLocationRequest
				.setFastestInterval(FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS);
		mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
	}

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	public void onResult(Status status) {
		if (status.isSuccess()) {
			Log.d(this.getClass().getName(), String.valueOf(R.string.geofences_added));
		} else {
			MainActivity.geofencesAlreadyRegistered = false;
			String errorMessage = getErrorString(this, status.getStatusCode());
			Toast.makeText(getApplicationContext(), errorMessage,
					Toast.LENGTH_LONG).show();
		}
	}

	public static String getErrorString(Context context, int errorCode) {
		Resources mResources = context.getResources();
		switch (errorCode) {
		case GeofenceStatusCodes.GEOFENCE_NOT_AVAILABLE:
			return mResources.getString(R.string.geofence_not_available);
		case GeofenceStatusCodes.GEOFENCE_TOO_MANY_GEOFENCES:
			return mResources.getString(R.string.geofence_too_many_geofences);
		case GeofenceStatusCodes.GEOFENCE_TOO_MANY_PENDING_INTENTS:
			return mResources
					.getString(R.string.geofence_too_many_pending_intents);
		default:
			return mResources.getString(R.string.unknown_geofence_error);
		}
	}

}
