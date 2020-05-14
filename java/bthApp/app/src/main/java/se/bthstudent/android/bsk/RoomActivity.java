/*
 *  This file is distributed under the GPL3 license.
 *  Please see the file LICENSE that should be present in this distribution.
 *
 *  Copyright 2011
 *   Jonas Hellström <jonas@if-then-else.se>
 *  
 *  Maintained by
 *   Jonas Hellström <jonas@if-then-else.se>
 *  since 2011
 */

package se.bthstudent.android.bsk;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.ByteArrayBuffer;
import org.json.JSONArray;
import org.json.JSONObject;

import se.bthstudent.android.bsk.R;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

public class RoomActivity extends Activity implements BSKLOG {
	/** Called when the activity is first created. */
	public static final float ZOOM_MIN = 1;
	public static final float ZOOM_MAX = 3;

	private boolean mZoomEnabled;

	private Context mContext;
	private String mRoomId = "";

	private SharedPreferences mSharedPreferences;
	private List<Room> mIndex;
	private FetchRoomData mFetchRoomData;
	File mSDDirectory = new File(Environment.getExternalStorageDirectory().getPath());
	File mBskDirectory = new File(mSDDirectory.getPath() + "/bsk");

	private RoomLayout mRoomLayout;
	private Room mRoom;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.d(TAG, "RoomActivity.onCreate()");
		mContext = this;

		// Initialise rooms array
		mIndex = new ArrayList<Room>();
		mSharedPreferences = (SharedPreferences) getSharedPreferences("BskPreferences", 0);

		// Check if there exists a "bsk" folder on the SD-card or else create it
		File readDir = new File(mSDDirectory.getPath() + "/bsk");
		if(!mBskDirectory.exists()) {
			if(!readDir.mkdirs()) {
				//				Log.d(TAG, "Could not create SD-card folder, exiting!");
				Log.d(TAG, "Could not create bsk folder!");
			} else {
				launchRoomLayout();
			}
		} else {
			launchRoomLayout();
		}

	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		if(mZoomEnabled) {
			mRoomLayout.destroy();
			mZoomEnabled = false;
		}
		saveIndex();
	}

	private void launchRoomLayout() {
		Bundle extras = getIntent().getExtras();

		mRoomId = extras.getString("room");
		Pattern pattern = Pattern.compile("([A-Z]{1}[0-9]{4})|([A-Z]{1}[0-9]{3}[A-Z]{1})|([A-Z]{1}[0-9]{3})");
		Matcher matcher = pattern.matcher(mRoomId);
		if(matcher.find()) {
			mRoomId = matcher.group();
		}

		Log.d(TAG, "mRoomId: " + mRoomId);

		mFetchRoomData = new FetchRoomData();
		mFetchRoomData.execute();
	}

	private void saveIndex() {
		FileOutputStream stream = null;
		try {
			stream = RoomActivity.this.openFileOutput("building.index", Context.MODE_PRIVATE);
			ObjectOutputStream outputStream = new ObjectOutputStream(stream);
			outputStream.writeObject(mIndex);
			outputStream.flush();
			stream.getFD().sync();
			stream.close();
		} catch(Exception e) {
			Log.d(TAG, "Could not save a persistent index...");
			e.printStackTrace();
		}
	}

	public class FetchRoomData extends AsyncTask<String, Integer, Boolean> {
		ProgressDialog progressDialog;
		boolean indexSuccess = true;
		boolean imageSuccess = true;
		boolean imageExistsInIndex = true;
		String request;
		String imageUrl;
		String image;
		String file;

		@Override
		protected void onPreExecute() {
			progressDialog = new ProgressDialog(RoomActivity.this);
			progressDialog.setMessage(getString(R.string.room_info_download_progress));
			progressDialog.setCancelable(true);
			progressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
				@Override
				public void onCancel(DialogInterface dialog) {
					cancel(true);
				}
			});
			progressDialog.show();
		}

		@Override
		protected Boolean doInBackground(String... status) {
			if(!getPersistedIndex()) {
				Log.d(TAG, "Index does not exist locally");
				if(!getIndex()) {
					Log.d(TAG, "Index download failed");
					indexSuccess = false;
				}
			} else {
				NetworkInfo info = ((ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
				if(info == null || !info.isConnected()) {
					Log.d(TAG, "No network connection! Let's see if the room exists in the index and whether the image has been downloaded..");
				} else {
					if(!isIndexUpToDate()) {
						Log.d(TAG, "Index is out of date");
						if(!getUpdatedIndex()) {
							Log.d(TAG, "Index download failed");
							indexSuccess = false;
						}
					} else {
						if(!getPersistedIndex()) {
							indexSuccess = false;
							Log.d(TAG, "Could not open persisted index");
						}
					}
				}
			}

			if(!roomInIndex()) {
				imageExistsInIndex = false;
				if(!brokenImageExists()) {
					Log.d(TAG, "Broken image does not exist on SD card");
					if(!getBrokenImage()) {
						Log.d(TAG, "Broken image download failed");
						imageSuccess = false;
					} else {
					}
				} else {
				}
			} else {
				Log.d(TAG, "The room exists in the index");
				if(!imageExists()) {
					Log.d(TAG, "The image of the room does not exist on SD card OR it has been updated");
					if(!getARoom()) {
						Log.d(TAG, "Could not get a room...");
						imageSuccess = false;
					} else {
						Log.d(TAG, "Got a room!");
					}
				} else {
				}
			}

			return null;
		}

		protected void onPostExecute(Boolean asyncDownloadSuccess) {
			Log.d(TAG, "indexSuccess: " + indexSuccess + " imageExistsInIndex: " + imageExistsInIndex + " imageSuccess: " + imageSuccess);
			if(indexSuccess && imageExistsInIndex && imageSuccess) {
				// Setting image
				mRoomLayout = new RoomLayout(mContext, mRoom);
				setContentView(mRoomLayout);
				mZoomEnabled = true;
			} else if(indexSuccess && !imageExistsInIndex && imageSuccess) {
				// Setting broken image
				mRoomLayout = new RoomLayout(mContext);
				setContentView(mRoomLayout);
				mZoomEnabled = true;
			} else {
				// Setting default something-went-horribly-wrong layout
				setContentView(R.layout.rooms);
				TextView textView = (TextView) findViewById(R.id.textViewRoomError);
				ImageView imageView = (ImageView) findViewById(R.id.imageViewRoom);
				textView.setVisibility(TextView.VISIBLE);
				imageView.setVisibility(ImageView.VISIBLE);
			}
			progressDialog.dismiss();
		}

		private boolean getUpdatedIndex() {
			String httpResponse = readRequest("http://projects.sandladan.nu/buildingnavigator/buildingindex.php?req=index");

			int tryCount = 0;
			boolean success = true;
			while(tryCount < 3) {
				try {
					JSONObject jsonObject = new JSONObject(httpResponse);
					JSONArray rooms = jsonObject.getJSONArray("rooms");
					if(rooms.length() > mIndex.size()) {
						Log.d(TAG, "Index count is smaller than updated index count so we have to get the entire index...");
						success = getIndex();
					} else {
						for(int i = 0; i < rooms.length(); i++) {
							if(Integer.parseInt(rooms.getJSONObject(i).getString("updated")) > mIndex.get(i).getTimestamp()) {
								mIndex.get(i).setTimestamp(Integer.parseInt(rooms.getJSONObject(i).getString("updated")));
								mIndex.get(i).setUpdated(true);
								Log.d(TAG, "Updated room: " + mIndex.get(i).getName() + " updated: " + mIndex.get(i).isUpdated());
							}
						}
						//					editor.putBoolean("getIndex", false);
						//					editor.commit();
						tryCount = 3;
						success = true;
					}
				} catch(Exception e) {
					e.printStackTrace();
					tryCount++;
					success = false;
					Log.d(TAG, "Something is going wrong while downloading. Is there an exisiting network connection?");
				}
			}
			return success;
		}

		private boolean getIndex() {
			String httpResponse = readRequest("http://projects.sandladan.nu/buildingnavigator/buildingindex.php?req=index");

			int tryCount = 0;
			boolean success = true;
			while(tryCount < 3) {
				try {
					JSONObject jsonObject = new JSONObject(httpResponse);
					JSONArray rooms = jsonObject.getJSONArray("rooms");
					if(mIndex.size() > 0) {
						mIndex.clear();
					}
					for(int i = 0; i < rooms.length(); i++) {
						mIndex.add(new Room(rooms.getJSONObject(i).getString("id"), rooms.getJSONObject(i).getString("name"), rooms.getJSONObject(i).getString("image"), Integer.parseInt(rooms.getJSONObject(i).getString("x_coord")), Integer.parseInt(rooms.getJSONObject(i).getString("y_coord")), Integer.parseInt(rooms.getJSONObject(i).getString("updated")), false));
					}
					//					editor.putBoolean("getIndex", false);
					//					editor.commit();
					tryCount = 3;
					success = true;
				} catch(Exception e) {
					e.printStackTrace();
					tryCount++;
					success = false;
					Log.d(TAG, "Something is going wrong while downloading. Is there an exisiting network connection?");
					e.printStackTrace();
				}
			}
			return success;
		}

		private boolean isIndexUpToDate() {
			boolean result = false;
			int lastUpdate = 0;
			String httpResponse = readRequest("http://projects.sandladan.nu/buildingnavigator/buildingindex.php?req=last_updated");

			try {
				JSONObject jsonObject = new JSONObject(httpResponse);
				if(jsonObject.get("res").toString().matches("success")) {
					lastUpdate = Integer.parseInt(jsonObject.get("last").toString());
					if(mSharedPreferences.getInt("last_update", 0) != lastUpdate) {
						Editor editor = mSharedPreferences.edit();
						editor.putInt("last_update", lastUpdate);
						editor.commit();
					} else {
						result = true;
					}
				}
			} catch(Exception e) {
				//				Log.d("BuildingTest", "Error in isIndexOutOfDate(): " + e);
			}
			return result;
		}

		/*
		 * If the index exists since earlier, load up the serialized list of rooms Here is a conscious suppression of
		 * the Unchecked warning. Refering to this article:
		 * http://stackoverflow.com/questions/5201555/unchecked-cast-warning-how-to-avoid-this
		 */
		@SuppressWarnings("unchecked")
		private boolean getPersistedIndex() {
			boolean result = false;

			FileInputStream stream = null;
			try {
				stream = RoomActivity.this.openFileInput("building.index");
				ObjectInputStream dIn = new ObjectInputStream(stream);
				mIndex = (ArrayList<Room>) dIn.readObject();
				result = true;
			} catch(Exception e) {
				Log.d(TAG, "" + e);
			} finally {
				try {
					stream.close();
				} catch(Exception e) {
					Log.d(TAG, "" + e);
				}
			}
			return result;
		}

		private boolean roomInIndex() {
			boolean result = false;

			for(int i = 0; i < mIndex.size(); i++) {
				//				Log.d("BuildingTest", mIndex.get(i).getName());
				if(mIndex.get(i).getName().matches(mRoomId)) {
					mRoom = mIndex.get(i);
					result = true;
				}
			}
			return result;
		}

		private boolean brokenImageExists() {
			File imgFile;
			boolean result = false;

			imgFile = new File(mSDDirectory.getPath() + "/bsk/broken_image.png");
			if(imgFile.exists()) {
				result = true;
			}
			return result;
		}

		private boolean getBrokenImage() {
			boolean result = false;

			if(getImageToSD("http://projects.sandladan.nu/buildingnavigator/", "broken_image.png")) {
				result = true;
			}
			return result;
		}

		private boolean imageExists() {
			File imgFile;
			boolean result = false;

			imgFile = new File(mSDDirectory.getPath() + "/bsk/" + mRoom.getImage());
			Log.d(TAG, "room: " + mRoom.getName() + " updated: " + mRoom.isUpdated());
			if(imgFile.exists() && !mRoom.isUpdated()) {
				result = true;
			}
			return result;
		}

		private boolean getARoom() {
			boolean result = false;

			if(getImageToSD("http://projects.sandladan.nu/buildingnavigator/", mRoom.getImage())) {
				mRoom.setUpdated(false);
				result = true;
			}
			return result;
		}

		public String readRequest(String request) {
			StringBuilder stringBuilder = new StringBuilder();
			HttpClient httpClient = new DefaultHttpClient();
			HttpGet httpGet = new HttpGet(request);

			try {
				HttpResponse response = httpClient.execute(httpGet);
				StatusLine statusLine = response.getStatusLine();
				int statusCode = statusLine.getStatusCode();
				if(statusCode == 200) {
					HttpEntity httpEntity = response.getEntity();
					InputStream content = httpEntity.getContent();
					BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(content));
					String line;
					while((line = bufferedReader.readLine()) != null) {
						stringBuilder.append(line);
					}
				} else {
					//					Log.e(RoomActivity.class.toString(), "Failed to download file!");
				}
			} catch(Exception e) {
			}

			return stringBuilder.toString();
		}

		public boolean getImageToSD(String imageUrl, String image) {
			try {
				URL url = new URL(imageUrl + image);
				File file = new File(mBskDirectory, image);
				URLConnection urlConnection = url.openConnection();
				InputStream is = urlConnection.getInputStream();
				BufferedInputStream bis = new BufferedInputStream(is);
				ByteArrayBuffer baf = new ByteArrayBuffer(5000);
				int current = 0;
				while((current = bis.read()) != -1) {
					baf.append((byte) current);
				}
				FileOutputStream fos = new FileOutputStream(file);
				fos.write(baf.toByteArray());
				fos.flush();
				fos.close();
				return true;
			} catch(Exception e) {
				//				Log.d("BuildingTest", "Error when downloading image: " + e);
				return false;
			}
		}
	}
}
