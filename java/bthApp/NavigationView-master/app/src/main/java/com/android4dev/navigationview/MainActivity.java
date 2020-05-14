package com.android4dev.navigationview;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.android4dev.navigationview.rssreader.NewsListAdapter;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    //Defining Variables
    String mVersion;
    CharSequence[] mNewsFeeds;
    boolean[] mSelectedNewsFeeds;
    String[] mSettingsNewsFeeds;

    CharSequence[] mCalendarFeeds;
    boolean[] mSelectedCalendarFeeds;
    String[] mSettingsCalendarFeeds;

    public boolean enVariabel = false;
    private Toolbar toolbar;
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;
    public static final String PREFS_NAME = "BskPreferences";
    SharedPreferences mBskPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        try {
            mVersion = getPackageManager().getPackageInfo(getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            mVersion = "";
            e.printStackTrace();
        }
        // TODO Verify that the name of these variables make sense
        Resources res = getResources();
        mSettingsNewsFeeds = res.getStringArray(R.array.newssettings);
        mSettingsCalendarFeeds = res.getStringArray(R.array.calendarsettings);
        // Get the names of resource-defined feeds
        mNewsFeeds = res.getStringArray(R.array.newsfeedtitles);
        mCalendarFeeds = res.getStringArray(R.array.calendarfeedtitles);
        // Get the number of feeds in each category
        mSelectedNewsFeeds = new boolean[mNewsFeeds.length];
        mSelectedCalendarFeeds = new boolean[mCalendarFeeds.length];

        // TODO Make sure this only initiates one of the tabs!
        initnews();
        initCalendars();

        // Initializing Toolbar and setting it as the actionbar
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Initializing NavigationView
        navigationView = (NavigationView) findViewById(R.id.navigation_view);

        //Setting Navigation View Item Selected Listener to handle the item click of the navigation menu
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            // This method will trigger on item Click of navigation menu
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {


                //Checking if the item is in checked state or not, if not make it in checked state
                if (menuItem.isChecked()) menuItem.setChecked(false);
                else menuItem.setChecked(true);

                //Closing drawer on item click
                drawerLayout.closeDrawers();

                //Check to see which item was being clicked and perform appropriate action
                switch (menuItem.getItemId()) {
                    //Replacing the main content with ContentFragment Which is our News View;
                    case R.id.news:
                        NewsListAdapter news = new NewsListAdapter();
                        android.support.v4.app.FragmentTransaction Newslistadapter2 = getSupportFragmentManager().beginTransaction();
                        Newslistadapter2.replace(R.id.frame, news);
                        Newslistadapter2.commit();
                    case R.id.calender:
                        Toast.makeText(getApplicationContext(), "@string/calender", Toast.LENGTH_SHORT).show();
                        return true;
                    case R.id.administration:
                        administration admin = new administration();
                        android.support.v4.app.FragmentTransaction administration2 = getSupportFragmentManager().beginTransaction();
                        administration2.replace(R.id.frame, admin);
                        administration2.commit();
                        return true;
                    case R.id.timetable:
                        Toast.makeText(getApplicationContext(), "Schema", Toast.LENGTH_SHORT).show();
                        return true;
                    case R.id.map:
                        Toast.makeText(getApplicationContext(), "Karta", Toast.LENGTH_SHORT).show();
                        return true;
                    default:
                        Toast.makeText(getApplicationContext(), "Somethings Wrong", Toast.LENGTH_SHORT).show();
                        return true;

                }
            }
        });

        // Initializing Drawer Layout and ActionBarToggle
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.openDrawer, R.string.closeDrawer) {

            @Override
            public void onDrawerClosed(View drawerView) {
                // Code here will be triggered once the drawer closes as we dont want anything to happen so we leave this blank
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                // Code here will be triggered once the drawer open as we dont want anything to happen so we leave this blank

                super.onDrawerOpened(drawerView);
            }
        };

        //Setting the actionbarToggle to drawer layout
        drawerLayout.setDrawerListener(actionBarDrawerToggle);

        //calling sync state is necessay or else your hamburger icon wont show up
        actionBarDrawerToggle.syncState();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void initnews() {
        ArrayList<Feed> news = new ArrayList<Feed>();

        // Fetch user-defined feed subscriptions
        mBskPreferences = getSharedPreferences(PREFS_NAME, 0);
        for (int i = 0; i < mSettingsNewsFeeds.length; i++) {
            mSelectedNewsFeeds[i] = mBskPreferences.getBoolean(mSettingsNewsFeeds[i], true);
        }

        // Create a list of feed objects based on user-defined subscriptions
        Resources res = getResources();
        TypedArray newsFeeds = res.obtainTypedArray(R.array.newsfeeds);
        for (int i = 0; i < newsFeeds.length(); i++) {
            if (mSelectedNewsFeeds[i]) {
                TypedArray feed = res.obtainTypedArray(newsFeeds.getResourceId(i, 0));
                news.add(new Feed(feed.getText(0).toString(), feed.getText(1).toString(), feed.getText(2).toString(), feed.getText(3).toString(), feed.getResourceId(4, 0), feed.getColor(5, 0), feed.getText(6).toString()));
            }
        }

        //
        ((BskData) getApplication()).setNewsList(news);
    }

    private void initCalendars() {
        ArrayList<Feed> calendars = new ArrayList<Feed>();
        Resources res = getResources();
        mBskPreferences = getSharedPreferences(PREFS_NAME, 0);

        for (int i = 0; i < mSettingsCalendarFeeds.length; i++) {
            mSelectedCalendarFeeds[i] = mBskPreferences.getBoolean(mSettingsCalendarFeeds[i], true);
        }

        TypedArray calendarFeeds = res.obtainTypedArray(R.array.calendarfeeds);

        for (int i = 0; i < calendarFeeds.length(); i++) {
            if(mSelectedCalendarFeeds[i]) {
                TypedArray calendar = res.obtainTypedArray(calendarFeeds.getResourceId(i, 0));
                calendars.add(new Feed(calendar.getText(0).toString(), calendar.getText(1).toString(), calendar.getText(2).toString(), calendar.getText(3).toString(), calendar.getResourceId(4, 0), calendar.getColor(5, 0), calendar.getText(6).toString()));
            }
        }

        ((BskData) getApplication()).setCalendarList(calendars);
    }
}
