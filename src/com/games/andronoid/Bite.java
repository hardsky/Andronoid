package com.games.andronoid;

import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Surface;

public class Bite extends Graphic implements SensorEventListener {
	private long mLastT = 0;
	private float mLastDeltaT = 0f;
	private float mSensorX = 0f;
	private long mSensorTimeStamp = 0;
	private long mCpuTimeStamp = 0;
	private float mLastPosX = 0;
	private float mAccelX = 0;	
	private Display mDisplay;
	private SensorManager mSensMgr;

	public Bite(BitmapDrawable oBite, Display oDisp, DisplayMetrics metrics, SensorManager sensMgr)
	{
		super(oBite, metrics, 0.01f, 0.002f);
		mDisplay = oDisp;
		mSensMgr = sensMgr;
	}
	
	public void registerAccelerometer()
	{
		Sensor sensAccel = mSensMgr.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		mSensMgr.registerListener(this, sensAccel, SensorManager.SENSOR_DELAY_UI);	
	}
	
	public void unregisterAccelerometer()
	{
		mSensMgr.unregisterListener(this);
	}
	
	public Bite(Bite bite) {
		super(bite);
	}

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onSensorChanged(SensorEvent event) {
            if (event.sensor.getType() != Sensor.TYPE_ACCELEROMETER)
                return;
            /*
             * record the accelerometer data, the event's timestamp as well as
             * the current time. The latter is needed so we can calculate the
             * "present" time during rendering. In this application, we need to
             * take into account how the screen is rotated with respect to the
             * sensors (which always return data in a coordinate space aligned
             * to with the screen in its native orientation).
             */

            switch (mDisplay.getOrientation()) {
                case Surface.ROTATION_0:
                    mSensorX = event.values[0];
				break;
                case Surface.ROTATION_90:
                    mSensorX = -event.values[1];
				break;
                case Surface.ROTATION_180:
                    mSensorX = -event.values[0];
				break;
                case Surface.ROTATION_270:
                    mSensorX = event.values[1];
				break;
            }

            mSensorTimeStamp = event.timestamp;
            mCpuTimeStamp = System.nanoTime();
	}

	//return true, if ball && bite is intersected
	public boolean Intesect(Ball ball) {
		// TODO: more cases
		
		Rect oBallBounds = ball.getPlace();
		Rect oBiteBounds = getPlace();
		Rect oIntersect = new Rect();
		if(oIntersect.setIntersect(oBiteBounds, oBallBounds))
		{
			ball.Impact(ImpactType.bottom, oIntersect);
			return true;
		}
		
		return false;
	}

	public void Impact(ImpactType enType, Rect oIntersect) {
		switch(enType)
		{
			case left:
				mPosX += (oIntersect.width() + 1)/mMetersToPixelsX;
				break;
			case right:
				mPosX -= (oIntersect.width() + 1)/mMetersToPixelsX;
				break;
		}
		
		SetBounds();
	}

	public void setCurrentTime(long nCurrentTimeStamp) {
		final float ax = - mSensorX;
		final long now = mSensorTimeStamp + (nCurrentTimeStamp - mCpuTimeStamp);
		final float dT = (float) (now - mLastT) * (1.0f / 1000000000.0f);
		mLastT = now;
		if (mLastDeltaT != 0)
		{
			final float dTC = dT / mLastDeltaT;
			final float dTdT = dT * dT;
			final float x = mPosX + dTC * (mPosX - mLastPosX) + mAccelX
					* dTdT;
					
			mLastPosX = mPosX;
			mPosX = x;
			mAccelX = ax;
		}
		
		mLastDeltaT = dT;
		
		SetBounds();		
	}
}
