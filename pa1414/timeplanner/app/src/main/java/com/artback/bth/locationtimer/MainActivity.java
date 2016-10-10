package com.artback.bth.locationtimer;
import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.artback.bth.locationtimer.Geofence.GeofenceLocation;
import com.artback.bth.locationtimer.Geofence.GeolocationService;
import com.artback.bth.locationtimer.Geofence.GeofenceLocationProvider;
import com.github.stephenbaidu.placepicker.PlacePicker;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends Activity {
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
        PermissionListener permissionlistener = new PermissionListener() {
            @Override
            public void onPermissionGranted() {
            }

            @Override
            public void onPermissionDenied(ArrayList<String> deniedPermissions) {
                Toast.makeText(MainActivity.this, "Permission Denied\n" + deniedPermissions.toString(), Toast.LENGTH_SHORT).show();
            }


        };
        new TedPermission(this)
                .setPermissionListener(permissionlistener)
                .setDeniedMessage("If you reject permission,you can not use this service\n\nPlease turn on permissions at [Setting] > [Permission]")
                .setPermissions(Manifest.permission.ACCESS_NETWORK_STATE,
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .check();

        return 0;
    }
    private void init(){
        RecyclerView locationView;
        RecyclerView.Adapter locAdapter;
        RecyclerView.LayoutManager locationLayoutManager;

        locationView = (RecyclerView) findViewById(R.id.location_list);
        locationView.addOnItemTouchListener(
                new RecyclerItemClickListener(this, locationView ,new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        // do whatever
                    }

                    @Override public void onLongItemClick(View view, int position) {
                        // do whatever
                    }
                })
        );
        locationView.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        locationView.setLayoutManager(llm);

        locationLayoutManager= new LinearLayoutManager(this);
        locationView.setLayoutManager(locationLayoutManager);
        final List<GeofenceLocation> myGeofenceSet = new ArrayList<>
                (GeofenceLocationProvider.getInstance().getGeofencesLocations().values());
        locAdapter = new locationAdapter(myGeofenceSet);

        //start geolocation
        startService(new Intent(this, GeolocationService.class));

        locationView.setAdapter(locAdapter);
    }
    public void openaddpage(View view){
        // Create an intent with `PlacePicker.class`
        Intent intent = new Intent(MainActivity.this, PlacePicker.class);

        // Set your server api key (required)
        intent.putExtra(PlacePicker.PARAM_API_KEY,  "AIzaSyB6I5KhMEFArn94e3llopUWb11Y8jcrqsM" );

        // Set extra query in a one line like below
        intent.putExtra(PlacePicker.PARAM_EXTRA_QUERY, "&components=country:gh&types=(cities)");

        // Then start the intent for result
        startActivityForResult(intent, PlacePicker.REQUEST_CODE_PLACE);
    }
    @Override
    protected void onStart() {
        super.onStart();
    }

}
