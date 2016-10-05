package com.artback.bth.timeplanner;
import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.artback.bth.timeplanner.Geofence.GeofenceLocation;
import com.artback.bth.timeplanner.Geofence.GeofenceLocationProvider;
import com.artback.bth.timeplanner.Geofence.GeolocationService;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends Activity {
    private static final int REQUEST_STORAGE  =1;
    private static final int REQUEST_NETWORK =2;
    private static final int REQUEST_LOCATION =3;

    private RecyclerView locationView;
    private RecyclerView.Adapter locAdapter;
    private RecyclerView.LayoutManager locationLayoutManager;
    static public boolean geofencesAlreadyRegistered = false;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_page);
        checkPermissons();
        init();
    }
    private int checkPermissons(){
        String[] permisson = new String[3];
        int nrOFpermissons=0;
        if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)!=
                PackageManager.PERMISSION_GRANTED ) {
            permisson[nrOFpermissons] = Manifest.permission.WRITE_EXTERNAL_STORAGE;
            nrOFpermissons++;
        }
        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION)!=
                PackageManager.PERMISSION_GRANTED ) {
            permisson[nrOFpermissons] = Manifest.permission.ACCESS_FINE_LOCATION;
            nrOFpermissons++;
        }
        if (checkSelfPermission(Manifest.permission.ACCESS_NETWORK_STATE)!=
                PackageManager.PERMISSION_GRANTED ) {
            permisson[nrOFpermissons] = Manifest.permission.ACCESS_NETWORK_STATE;
            nrOFpermissons++;
        }
        requestPermissions(permisson,REQUEST_LOCATION);
        return 0;
    }
    private void init(final Context context){
        locationView = (RecyclerView) findViewById(R.id.location_list);
        locationView.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        locationView.setLayoutManager(llm);

        locationLayoutManager= new LinearLayoutManager(this);
        locationView.setLayoutManager(locationLayoutManager);
        List<GeofenceLocation> myGeofenceSet = new ArrayList<GeofenceLocation>
                (GeofenceLocationProvider.getInstance().getGeofencesLocations().values());
        locAdapter = new locationAdapter(myGeofenceSet);

        //start geolocation
        startService(new Intent(this, GeolocationService.class));

        locationView.addOnItemTouchListener(
                new RecyclerItemClickListener(context, locationView ,new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        Intent intent = new Intent(context, AddLocationActivity.class);
                        intent.setAction(Intent.ACTION_SEND);
                        intent.putExtra(Intent.EXTRA, "This is my text to send.");
                        startActivity(intent);
                    }

                    @Override public void onLongItemClick(View view, int position) {
                        // do whatever
                    }
                })
        );


        locationView.setAdapter(locAdapter);
    }
    public void openaddpage(View view){
        Intent intent = new Intent(this, AddLocationActivity.class);
        startActivity(intent);
    }
    @Override
    protected void onStart() {
        super.onStart();
    }

}
