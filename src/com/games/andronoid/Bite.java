package com.games.andronoid;

import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.util.DisplayMetrics;

public class Bite extends Graphic implements ISensorListener {
	private long mLastT = 0;
	private float mLastDeltaT = 0f;
	private float mSensorX = 0f;
	private long mSensorTimeStamp = 0;
	private long mCpuTimeStamp = 0;
	private float mLastPosX = 0;
	private float mAccelX = 0;
	private float mFriction = 0.3f;

	public Bite(BitmapDrawable oBite, DisplayMetrics metrics)
	{
		super(oBite, metrics, 0.01f, 0.002f);
	}
		
	public Bite(Bite bite) {
		super(bite);
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
			final float x = mPosX + (1 - mFriction)*dTC * (mPosX - mLastPosX) + mAccelX
					* dTdT;
					
			mLastPosX = mPosX;
			mPosX = x;
			mAccelX = ax;
		}
		
		mLastDeltaT = dT;
		
		SetBounds();		
	}

	@Override
	public void onSensorChanged(float fSensorX, long eventTime, long cpuTime) {
		mSensorX = fSensorX;
        mSensorTimeStamp = eventTime;
        mCpuTimeStamp = cpuTime;		
	}
}
