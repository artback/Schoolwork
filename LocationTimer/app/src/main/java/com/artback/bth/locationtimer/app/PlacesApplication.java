package com.artback.bth.locationtimer.app;

import android.app.Application;
import android.content.Context;
import android.widget.Toast;

import com.artback.bth.locationtimer.Calendar.GoogleCalendarCollection;
import com.artback.bth.locationtimer.db.GeoFenceLocation;
import com.artback.bth.locationtimer.db.MarkMyPlacesDBHelper;
import com.forlong401.log.transaction.log.manager.LogManager;

import java.util.ArrayList;
import java.util.List;


public class PlacesApplication extends Application {
    public static final String CALENDER_INTENT="GeoCalendar";
    public static boolean initializedCalender=false;
    public static ArrayList<String> mIdList= new ArrayList<>();
    public static ArrayList<String> mSummaryList= new ArrayList<>();
    public volatile static ArrayList<Boolean> trueExitList= new ArrayList<>();
    public volatile static ArrayList<Boolean> alreadyExitedList = new ArrayList<>();
    private MarkMyPlacesDBHelper mDbHelper = null;
    public static final int MAX_GEOFENCES = 100 ;

    @Override
    public void onCreate() {
        super.onCreate();
        mDbHelper = MarkMyPlacesDBHelper.getInstance(getApplicationContext());
        LogManager.getManager(getApplicationContext()).registerCrashHandler();
    }
    @Override
    public void onTerminate() {
      super.onTerminate();
      LogManager.getManager(getApplicationContext()).unregisterCrashHandler();
    }
    public static MarkMyPlacesDBHelper getDatabase(Context context){
       return ((PlacesApplication)context.getApplicationContext()).mDbHelper ;
    }
    public static void showGenericToast(Context context,String message){
        Toast.makeText(context,message , Toast.LENGTH_SHORT).show();
    }
    public static void deleteGeoFence(Context context, GeoFenceLocation geoFenceLocation){
       mIdList.remove(mSummaryList.indexOf(geoFenceLocation.getId()));
       mSummaryList.remove(geoFenceLocation.getId());
       PlacesApplication.getDatabase(context).removeMyPlace(geoFenceLocation);
    }
    public static void toGoogleCal( GeoFenceLocation geo, Context context){
            new GoogleCalendarCollection.InsertCalenderTask(context).execute(geo.getId());
    }
    public static void allToGoogleCal(Context context){
        List<GeoFenceLocation> geos =MarkMyPlacesDBHelper.getInstance(context).getMyPlaces();
        String[] array = new String[geos.size()];
        for (int i = 0; i < geos.size(); i++) {
           array[i] = geos.get(i).getId();
        }
        new GoogleCalendarCollection.InsertCalenderTask(context).execute(array);
    }
}
