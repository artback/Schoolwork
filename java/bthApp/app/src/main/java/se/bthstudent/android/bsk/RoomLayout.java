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

import java.io.File;


import android.content.Context;
import android.os.Environment;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class RoomLayout extends RelativeLayout implements OnClickListener, BSKLOG {
	public final RoomImageView mRoomImageView;
	public String mBitmapPath;
	File mSDDirectory = new File(Environment.getExternalStorageDirectory().getPath());
	File mBskDirectory = new File(mSDDirectory.getPath() + "/bsk");
	
	public RoomLayout(Context context, Room room) {
		super(context);
		mRoomImageView = new RoomImageView(context, mBskDirectory + "/" +room.getImage());
		this.addView(mRoomImageView);
		TextView textView = new TextView(context);
		textView.setText(context.getString(R.string.room_requested)+ " " +room.getName());
		textView.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
		textView.setGravity(Gravity.CENTER_HORIZONTAL|Gravity.RIGHT);
		textView.setPadding(5, 5, 5, 5);
		this.addView(textView);
	}
	
	public RoomLayout(Context context) {
		super(context);
		mRoomImageView = new RoomImageView(context, mBskDirectory + "/broken_image.png");
		this.addView(mRoomImageView);
	}
	
	public void destroy() {
		mRoomImageView.destroy();
	}

	@Override
	public void onClick(View v) {
	}

}
