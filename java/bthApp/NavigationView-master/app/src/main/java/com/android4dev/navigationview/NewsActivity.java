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
 *   Emma Ekberg <emma.ekberg@gmail.com>
 *  since 2011
 */

package com.android4dev.navigationview;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import com.android4dev.navigationview.rssreader.NewsListAdapter;
import com.android4dev.navigationview.rssreader.RSSHandler;
import com.android4dev.navigationview.rssreader.RssItem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class NewsActivity extends ListActivity implements BSKLOG {
	private NewsListAdapter mAdapter;
	private List<RssItem> mAllArticles = new ArrayList<RssItem>();
	public FetchNews mFetchNews = null;
	CharSequence[] mNewsFeeds;
	boolean[] mSelectedNewsFeeds;
	String[] mSettingsNewsFeeds;
	public static final String PREFS_NAME = "BskPreferences";
	SharedPreferences mBskPreferences;
	public boolean mVariabel = false;
	
	// Initialize adapter and call function to populate list
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		Resources res = getResources();
		mSettingsNewsFeeds = res.getStringArray(R.array.newssettings);
		mNewsFeeds = res.getStringArray(R.array.newsfeedtitles);
		mSelectedNewsFeeds = new boolean[mNewsFeeds.length];
		
		mAdapter = new NewsListAdapter(this, mAllArticles);
		setListAdapter(mAdapter);

		initTab();
	}

	// When this activity resumes, look for any changes in Calendar user-defined
	// subscriptions and update list accordingly.
	public void onResume() {
		super.onResume();
		if(((BskData) getApplication()).isChangedNewsFeeds()) {
			initTab();
			((BskData) getApplication()).setChangedNewsFeeds(false);
		}
	}

	// When an item in the list is tapped, open the full article
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		((BskData) getApplication()).setCurrentArticle(mAllArticles.get(position));
		Intent intent = new Intent(this, ArticleActivity.class);
		startActivity(intent);
	}

	// Instantiate internal FetchCalendar object and start new thread execution.
	public void initTab() {
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
			if(mSelectedNewsFeeds[i]) {
				TypedArray feed = res.obtainTypedArray(newsFeeds.getResourceId(i, 0));
				news.add(new Feed(feed.getText(0).toString(), feed.getText(1).toString(), feed.getText(2).toString(), feed.getText(3).toString(), feed.getResourceId(4, 0), feed.getColor(5, 0), feed.getText(6).toString()));
			}
		}

		//
		((BskData) getApplication()).setNewsList(news);
		
		mFetchNews = new FetchNews();
		mFetchNews.execute();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.news_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle option menu item selection
		switch (item.getItemId()) {
		case R.id.settings_news:
			showDialog(0);
			return true;
		case R.id.settings_refresh:
			initTab();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
	
	/**
	 * An AsyncTask for fetching and adding news articles of the user-defined news feeds. Note: Cancelling an AsyncTask
	 * might not cancel the currently active process/thread immediately. doInBackground will run its course if no
	 * defined procedure to stop the code from executing exists.
	 * 
	 * @author drlaban
	 * 
	 */
	public class FetchNews extends AsyncTask<String, Integer, Boolean> implements BSKLOG {
		private ProgressDialog progressDialog = null;
		private volatile boolean running = true;

		@Override
		protected void onPreExecute() {
			progressDialog = new ProgressDialog(NewsActivity.this);
			progressDialog.setCancelable(true);

			progressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
				@Override
				public void onCancel(DialogInterface dialog) {
					cancel(true);
				}
			});
			running = true;
			progressDialog.show();
		}

		@Override
		protected Boolean doInBackground(String... status) {
			// TODO See if there's another way of stopping an AsyncTask more ffectively.
			// Killing it is not an option, we should really use the isCancelled() method
			// for this. But we would rather not have to wait until an entire feed is
			// processed before we can stop it.
			while (running && !isCancelled()) {
				// Get an array with the feeds to be processed
				ArrayList<Feed> feeds = ((BskData) getApplication()).getNewsList();
				// Ensure that there is no residual information from previous
				// fetches
				mAllArticles.clear();
				// Set the maximum number of retries for fetching and parsing
				int maxTries = 5;

				// Start fetch and parse sequence
				for (int i = 0; i < feeds.size(); i++) {
					// Get a new feed object to process
					Feed currentFeed = feeds.get(i);
					// Instantiate a new handler which will do the grunt work
					RSSHandler rh = new RSSHandler(currentFeed);
					// Prepare a variable that keeps track of whether the fetching
					// succeeded or not.
					boolean fetchedArticles = false;
					// Reset the tryCount
					int tryCount = 0;
					// If the fetching fails for any reason, retry until the maximum
					// number of reties is reached, or the AsyncTask thread is cancelled
					while (fetchedArticles == false && tryCount < maxTries && !isCancelled()) {
						fetchedArticles = rh.getLatestArticles();

						// for debugging purposes
						// Log.d(TAG,
						// "fetch articles "+currentFeed.getTitle()+" "+fetchedArticles);

						tryCount++;
					}
					// TODO Present a message to the user if the download was unsuccessful.
					List<RssItem> articles = rh.getList();

					// Populate the current feed with the fetched information.
					feeds.get(i).setItems(articles);
					// TODO This is really a pot-shot workaround for proper thread handling.
					if(!isCancelled()) {
						mAllArticles.addAll(articles);
					}
				}
				// Sort the complete list of events based on start date
				Collections.sort(mAllArticles);
				running = false;
			}
			return null;
		}

		@Override
		protected void onPostExecute(Boolean asynchDownloadSuccess) {
			running = false;
			mAdapter.notifyDataSetChanged();
			progressDialog.dismiss();
		}

		@Override
		protected void onCancelled() {
			// TODO Make sure the thread really exits, otherwise duplicates of weeks will occur
			super.onCancelled();
			running = false;
		}
	}
	
	protected AlertDialog onCreateDialog(int id) {
		AlertDialog alertDialog;
		AlertDialog.Builder builder = new AlertDialog.Builder(this);

		switch (id) {
		default:
		case 0:
			mVariabel = false;
			builder.setTitle(this.getString(R.string.settings_news));
			builder.setMultiChoiceItems(mNewsFeeds, mSelectedNewsFeeds, new DialogInterface.OnMultiChoiceClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which, boolean isChecked) {
					mVariabel = true;
					mSelectedNewsFeeds[which] = isChecked;
					Editor editor = mBskPreferences.edit();
					editor.putBoolean(mSettingsNewsFeeds[which], isChecked);
					editor.commit();
				}
			});
			builder.setOnCancelListener(new OnCancelListener() {
				@Override
				public void onCancel(DialogInterface arg0) {
					if(mVariabel) {
						initTab();
						/*
						if(getTabHost().getCurrentTab() == 0) {
							String tabTag = getTabHost().getCurrentTabTag();
							NewsActivity a = (NewsActivity) getLocalActivityManager().getActivity(tabTag);
							a.initTab();
						} else {
							((BskData) getApplication()).setChangedNewsFeeds(true);
						}
						*/
					}
				}

			});
			alertDialog = builder.create();
			break;
		}
		return alertDialog;
	}
}