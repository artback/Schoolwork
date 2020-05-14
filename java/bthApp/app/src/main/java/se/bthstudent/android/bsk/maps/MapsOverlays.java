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

package se.bthstudent.android.bsk.maps;

import java.util.ArrayList;

import se.bthstudent.android.bsk.BSKLOG;
import android.app.AlertDialog;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.location.Location;

import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.OverlayItem;

public class MapsOverlays extends ItemizedOverlay<OverlayItem> implements BSKLOG {

	private ArrayList<OverlayItem> mOverlays = new ArrayList<OverlayItem>();
	private Context mContext;
	Location mLocation;
	boolean mIsLocated = false;
	
	public MapsOverlays(Drawable defaultMarker, Context context) {
		super(boundCenterBottom(defaultMarker));
		mContext = context;
		// TODO Auto-generated constructor stub
	}

	@Override
	protected boolean onTap(int index) {
		OverlayItem item = mOverlays.get(index);
		AlertDialog.Builder dialog = new AlertDialog.Builder(mContext);
		dialog.setTitle(item.getTitle());
		dialog.setMessage(item.getSnippet());

		/*
		 * Criteria criteria = new Criteria();
		 * criteria.setAccuracy(Criteria.ACCURACY_FINE); String provider =
		 * locationManager.getBestProvider(criteria, true); mLocation =
		 * locationManager.getLastKnownLocation(provider); //GeoPoint(((int)
		 * mLocation.getLatitude()*1000000),((int)
		 * mLocation.getLongitude()*1000000));
		 */
		//double distance;
		Location locationA = new Location("point A");
		locationA.setLatitude((item.getPoint().getLatitudeE6() / 1000000.0));
		locationA.setLongitude((item.getPoint().getLongitudeE6() / 1000000.0));
		// Log.d(TAG, "Location a:" +locationA.getLatitude());
		// Log.d(TAG, "mLocation:" +mLocation.getLatitude());
		// TODO This calculation is not dynamic enough to provide a stable determination of distance.
		// Rethink the option and reimplement.
		//dialog.setMessage(item.getSnippet());
		if(mLocation != null) {
			//distance = mLocation.distanceTo(locationA);
//			dialog.setMessage(item.getSnippet() + "\n\n Distance from you: " + (int) (distance / 1000) + " km");
		}
		dialog.show();
		return true;
	}

	public void addOverlay(OverlayItem overlay) {
		mOverlays.add(overlay);
		populate();
	}

	@Override
	protected OverlayItem createItem(int i) {
		return mOverlays.get(i);
	}

	@Override
	public int size() {
		return mOverlays.size();
	}

	public void setBoundCenterBottom(Drawable marker) {
		boundCenterBottom(marker);
	}
}
