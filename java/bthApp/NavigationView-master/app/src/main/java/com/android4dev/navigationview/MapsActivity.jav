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
 *  since 2011
 */
package com.android4dev.navigationview;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapView;
import com.google.android.maps.MyLocationOverlay;
import com.google.android.maps.Overlay;
import com.android4dev.navigationview.maps.OverlayItem;
import com.android4dev.navigationview.maps.MapsOverlays;

public class MapsActivity extends MapActivity implements BSKLOG{
	private SharedPreferences mSharedPreferences;
	private MapView mMapView;
	private MapController mMapController;
	private List<Overlay> mMapOverlays;
	private Drawable mDrawable;
	private MapsOverlays mMapsMarkerOverlays;
	private Context mContext;
	private MyLocationOverlay mLocationOverlay;
	private boolean mMyLocationOverlayAdded = false;
	private BskData mAppData; 

	public static final String PREFS_NAME = "BskPreferences";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.map);

		mAppData = (BskData) getApplicationContext();
		
		this.mSharedPreferences = getSharedPreferences(PREFS_NAME, 0);
		this.mContext = this;
		this.mMapView = (MapView) this.findViewById(R.id.bskmap);
		this.mMapView.setBuiltInZoomControls(true);
		this.mMapController = this.mMapView.getController();
		this.mMapController.setCenter(new GeoPoint(mSharedPreferences.getInt("latitude", 56164880), mSharedPreferences.getInt("longitude", 15283012)));
		this.mMapController.setZoom(mSharedPreferences.getInt("zoomlevel", 10));

		mMapOverlays = mMapView.getOverlays();

		GeoPoint geoPoint;
		OverlayItem overlayItem;
		mMapsMarkerOverlays = new MapsOverlays(getResources().getDrawable(R.drawable.marker_bsk), this);
		TypedArray locations = getResources().obtainTypedArray(R.array.locations);
		for (int i = 0; i < locations.length(); i++) {
			TypedArray location = getResources().obtainTypedArray(locations.getResourceId(i, 0));
			mDrawable = this.getResources().getDrawable(location.getResourceId(0, 0));
			mDrawable.setBounds(0, 0, mDrawable.getIntrinsicWidth(), mDrawable.getIntrinsicHeight());
			mMapsMarkerOverlays.setBoundCenterBottom(mDrawable);
			geoPoint = new GeoPoint(location.getInt(1, 0), location.getInt(2, 0));
			overlayItem = new OverlayItem(geoPoint, location.getString(3), location.getString(4));
			overlayItem.setMarker(mDrawable);
			mMapsMarkerOverlays.addOverlay(overlayItem);
		}
		mMapOverlays.add(mMapsMarkerOverlays);

		// LocationManager locationManager = (LocationManager)
		// getSystemService(Context.LOCATION_SERVICE);
		// mLocation =
		// locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
		mLocationOverlay = new MyLocationOverlay(mContext, mMapView);
		mLocationOverlay.enableMyLocation();
		mLocationOverlay.enableCompass();

		Button myLocation = (Button) findViewById(R.id.my_location_button);
		myLocation.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(!mMyLocationOverlayAdded) {
					mMapView.getOverlays().add(mLocationOverlay);
					mMyLocationOverlayAdded = true;
				}
				mLocationOverlay.runOnFirstFix(new Runnable() {
					public void run() {
						mMapView.getController().animateTo(mLocationOverlay.getMyLocation());
						mAppData.setLocationFix(true);
					}
				});
				/*
				if(mLocationOverlay == null) {
					
				} else {
					mMapController.animateTo(mLocationOverlay.getMyLocation());
				}
				*/
			}
		});
	}

	@Override
	public void onResume() {
		super.onResume();
		if(mLocationOverlay != null) {
			if(!mLocationOverlay.isMyLocationEnabled()) {
				mLocationOverlay.enableMyLocation();
			}
		}
	}
	
	@Override
	public void onPause() {
		super.onPause();
		GeoPoint currentCenter = mMapView.getMapCenter();
		Editor editor = mSharedPreferences.edit();
		editor.putInt("latitude", currentCenter.getLatitudeE6());
		editor.putInt("longitude", currentCenter.getLongitudeE6());
		editor.putInt("zoomlevel", mMapView.getZoomLevel());
		editor.commit();
		mLocationOverlay.disableMyLocation();
		mAppData.setLocationFix(false);
	}

	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}

}
