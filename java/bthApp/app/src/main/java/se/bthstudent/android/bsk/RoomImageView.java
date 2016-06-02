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

import java.io.FileInputStream;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.ZoomButtonsController;
import android.widget.ZoomButtonsController.OnZoomListener;

public class RoomImageView extends ImageView implements BSKLOG {

	private static final float ZOOM_MIN = 1.0f;
	private static final float ZOOM_MAX = 3.0f;
	private static final float ZOOM_ANIMATION_STEP = 0.15f;

	private BitmapDrawable mBitmap;
	private Rect mBitmapSize;

	private Rect mZoomRect;
	
	private String mFilePath;

	private boolean mIsZooming = false;

	private int mWidth;
	private int mHeight;

	private float mScaleFactor;
	private float mWidthScaleFactor;
	private float mHeightScaleFactor;
	private float mTargetScaleFactor;

	private float mPosX;
	private float mPosY;
	private float mPreviousX;
	private float mPreviousY;
	private float mCenterPointStepX;
	private float mCenterPointStepY;

	private PointF mTargetCenterPoint;

	private ZoomButtonsController mZoomButtonsController;

	public RoomImageView(Context context, String filePath) {
		super(context);
		mFilePath = filePath;
		Log.d(TAG, "filePath: " +filePath);
		init(context);
	}

	public void destroy() {
		mZoomButtonsController.setVisible(false);
	}

	public void init(Context context) {
		setBackgroundColor(0xFF6C9B67);

		mScaleFactor = ZOOM_MIN;

		setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));

		mZoomButtonsController = new ZoomButtonsController(this);
		mZoomButtonsController.getZoomControls();

		mZoomButtonsController.setZoomSpeed(80);
		mZoomButtonsController.setOnZoomListener(new OnZoomListener() {

			@Override
			public void onZoom(boolean zoomIn) {
				// TODO Auto-generated method stub
				if(zoomIn) {
					if(mScaleFactor < ZOOM_MAX) {
						steppingZoom(mScaleFactor * 1.25f, new PointF((getWidth() / 2), (getHeight() / 2)));
					}
				} else {
					if(mScaleFactor > ZOOM_MIN) {
						steppingZoom(mScaleFactor / 1.25f, new PointF((getWidth() / 2), (getHeight() / 2)));
					}
				}
			}

			@Override
			public void onVisibilityChanged(boolean visible) {
			}
		});

	}

	public void initLayout() {
		loadImage();
	}

	public void loadImage() {
		Log.d(TAG, "Loading image...");
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = false;
		//options.inSampleSize = 2;
		options.inInputShareable = true;
		options.inPurgeable = true;
		options.inScaled = false;

		System.gc();

		try {
			FileInputStream inFile = new FileInputStream(mFilePath);
			mBitmap = new BitmapDrawable(BitmapFactory.decodeStream(inFile, null, options));
		} catch(Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		if(mWidth != 0 && mHeight != 0) {
			mBitmap.setBounds(0,0,mWidth,mHeight);
			mBitmapSize = mBitmap.getBounds();
			Log.d(TAG, "mBitmapSize: " +mBitmapSize.top);
			Log.d(TAG, "mBitmapSize: " +mBitmapSize.right);
			Log.d(TAG, "mBitmapSize: " +mBitmapSize.bottom);
			Log.d(TAG, "mBitmapSize: " +mBitmapSize.left);
			
		}

		if(mBitmap != null && mZoomRect == null && mBitmapSize != null) {
			mZoomRect = new Rect(mBitmapSize);
			mWidthScaleFactor = (float) mBitmapSize.width() / (float) mWidth;
			mHeightScaleFactor = (float) mBitmapSize.height() / (float) mHeight;
			mScaleFactor = (float) mBitmapSize.width() / (float) mZoomRect.width();

			mPosX = -((mScaleFactor * (float) mZoomRect.left * (float) mWidth) / mBitmapSize.width());
			mPosY = -((mScaleFactor * (float) mZoomRect.top * (float) mHeight) / mBitmapSize.height());
		}

		Log.d(TAG, "Width: " + mBitmapSize.width() + " Height: " + mBitmapSize.height());

		calcMidPoint();
		invalidate();
	}

	public Rect getZoomRect() {
		final float widthZoom = mWidthScaleFactor / mScaleFactor;
		final float heightZoom = mHeightScaleFactor / mScaleFactor;
		mZoomRect.left = -Math.round(mPosX * widthZoom);
		mZoomRect.right = -Math.round((float) mZoomRect.left + ((float) mWidth * widthZoom));
		mZoomRect.top = -Math.round(mPosY * heightZoom);
		mZoomRect.bottom = -Math.round((float) mZoomRect.top + ((float) mWidth * heightZoom));

		return mZoomRect;
	}

	private void steppingZoom(float targetScaleFactor, PointF centerPoint) {
		if(targetScaleFactor > ZOOM_MAX) {
			mTargetScaleFactor = ZOOM_MAX;
		} else if(targetScaleFactor < ZOOM_MIN) {
			mTargetScaleFactor = ZOOM_MIN;
		} else {
			mTargetScaleFactor = targetScaleFactor;
		}

		float dx = centerPoint.x - getWidth() / 2;
		float dy = centerPoint.y - getHeight() / 2;

		float nrOfSteps = 1.0f;

		mCenterPointStepX = (dx / (nrOfSteps));
		mCenterPointStepY = (dy / (nrOfSteps));

		if(mScaleFactor < targetScaleFactor) {

			mTargetCenterPoint = centerPoint;
			zoomIn();

		} else if(mScaleFactor > targetScaleFactor) {
			mTargetCenterPoint = new PointF((getWidth() / 2), (getHeight() / 2));
			zoomOut();
		}
	}

	private void zoomIn() {
		float zoomStep = 0;
		if(mScaleFactor < mTargetScaleFactor) {
			zoomStep += ZOOM_ANIMATION_STEP;
			if(mScaleFactor + zoomStep > mTargetScaleFactor) {
				zoomStep = mTargetScaleFactor - mScaleFactor;
			}

			zoom(zoomStep, new PointF(getWidth() / 2 + mCenterPointStepX * mScaleFactor, getHeight() / 2 + mCenterPointStepY * mScaleFactor));

		} else {
			mIsZooming = false;
			invalidate();
		}
	}

	private void zoomOut() {
		float zoomStep = 0;
		if(mScaleFactor > mTargetScaleFactor) {
			zoomStep -= ZOOM_ANIMATION_STEP;
			if(mScaleFactor + zoomStep < mTargetScaleFactor) {
				zoomStep = mTargetScaleFactor - mScaleFactor;
			}

			zoom(zoomStep, mTargetCenterPoint);
			zoom(zoomStep, new PointF(getWidth() / 2 + mCenterPointStepX * mScaleFactor, getHeight() / 2 + mCenterPointStepY * mScaleFactor));

		} else {
			mIsZooming = false;
			invalidate();
		}
	}

	public void zoom(float scaleFactor, PointF centerPoint) {
		float imagePosX = -mPosX + centerPoint.x;
		float imagePosY = -mPosY + centerPoint.y;

		float previousScaleFactor = mScaleFactor;
		mScaleFactor += scaleFactor;
		float scaleDiff = mScaleFactor;

		if(mScaleFactor > ZOOM_MAX || mScaleFactor < ZOOM_MIN) {
			if(mScaleFactor > ZOOM_MAX) {
				mScaleFactor = ZOOM_MAX;
			} else {
				mScaleFactor = ZOOM_MIN;
			}
		}

		scaleDiff = mScaleFactor / previousScaleFactor;
		mPosX = -(imagePosX * scaleDiff - getWidth() / 2);
		mPosY = -(imagePosY * scaleDiff - getHeight() / 2);

		checkBounds();

		invalidate();
	}

	private boolean checkBounds() {
		boolean ret = true;

		
		if(mPosX > 0) {
			Log.d(TAG, "mPosX > 0");
			mPosX = 0;
			ret = false;
		}

		if(mPosY > 0) {
			Log.d(TAG, "mPos Y > 0");
			mPosY = 0;
			ret = false;
		}

		final float scaledWidth = (float) mWidth * mScaleFactor;
		Log.d(TAG, "scaledWidth: " +scaledWidth);
		if(mPosX + scaledWidth < mWidth) {
			Log.d(TAG, "mPosX + sclaedWidth < mWidth");
			mPosX = mWidth - scaledWidth;
			ret = false;
		}

		final float scaledHeight = (float) mHeight * mScaleFactor;
		Log.d(TAG, "scaledHeight: " +scaledHeight);
		if(mPosY + scaledHeight < mHeight) {
			Log.d(TAG, "mPosY + scaledHeight < mHeight");
			mPosY = mHeight - scaledHeight;
			ret = false;
		}

		return ret;
	}

	private void calcMidPoint() {
		float x = mWidth / 2.0f;
		float y = mHeight / 2.0f;
		Log.d(TAG, "Width: " + x);
		Log.d(TAG, "Height: " + y);
	}

	/*
	private int[] calculateImageScale(int imageWidth, int imageHeight) {
		Log.d(TAG, "calculateImageScale");
		float x = 0;
		float y = 0;
		float viewWidth = mWidth;
		float viewHeight = mHeight;
		
		
		Log.d(TAG, "viewWidth: " +viewWidth+ " viewHeight: " +viewHeight);
		Log.d(TAG, "imageWidth: " +imageWidth+ " imageHeight: " +imageHeight);
		Log.d(TAG, "mHeight: " +mHeight);
		Log.d(TAG, "mWidth: " +mWidth);
		
		float scale;
		Log.d(TAG, "orientation: " +getResources().getConfiguration().orientation);
		if(getResources().getConfiguration().orientation == 1) {
			scale = viewWidth / imageWidth;
		} else {
			scale = viewHeight / imageHeight;
		}
		Log.d(TAG, "Scale: " +scale);
		
		float scaledWidth = imageWidth * scale;
		
		float scaledHeight = imageHeight * scale;
		
		mHeight = (int) scaledHeight;
		
		if(getResources().getConfiguration().orientation == 1) {
			y = (scaledHeight / 2);
		} else {
			x = (scaledWidth / 2);
		}
		
		Log.d(TAG, "scaledWidth: " +scaledWidth+ " scaledHeight: " +scaledHeight);
		Log.d(TAG, "fromTop: " +y+ " fromLeft: " +x);
		
		//mWidth = (int) scaledWidth;
		//mHeight = (int) scaledHeight;
		
		int sizes[] = { (int) -x, (int) -y, (int) (scaledWidth), (int) (scaledHeight) }; 
		return sizes;
	}
	 */
	
	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		if(mIsZooming) {
			//Zoom animation in progress, touch input not accepted.
			return true;
		}

		mZoomButtonsController.setVisible(true);

		final int action = ev.getAction();

		switch(action & MotionEvent.ACTION_MASK) {

		case MotionEvent.ACTION_DOWN: {
			final float x = ev.getX();
			final float y = ev.getY();

			mPreviousX = x;
			mPreviousY = y;

			break;
		}
		case MotionEvent.ACTION_MOVE: {
			final float x = ev.getX(0);
			final float y = ev.getY(0);
			final float dx = x - mPreviousX;
			final float dy = y - mPreviousY;

			mPosX += dx;
			mPosY += dy;

			checkBounds();

			invalidate();

			mPreviousX = x;
			mPreviousY = y;
		}
		}

		return true;
	}

	@Override
	protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
		super.onLayout(changed, left, top, right, bottom);
		if(getVisibility() == View.VISIBLE) {
			if(changed) {
				mWidth = right - left;
				mHeight = bottom - top;
				if(mWidth > 0 && mHeight > 0) {
					initLayout();
				}
			}
		}
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		if(mBitmap != null) {
			canvas.save();
			canvas.translate(mPosX, mPosY);
			canvas.scale(mScaleFactor, mScaleFactor);
			mBitmap.draw(canvas);
		}
	}
}
