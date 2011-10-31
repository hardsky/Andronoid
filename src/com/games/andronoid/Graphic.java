package com.games.andronoid;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.*;

public class Graphic {
	protected Drawable mDrawble;
	protected float mMetersToPixelsX;
	protected float mMetersToPixelsY;
	protected Rect mBounds;
	protected float mXOrigin;
	protected float mYOrigin;
	protected float mPosX;
	protected float mPosY;

	protected Graphic()
	{
		
	}
	
	public Graphic(Graphic prev)
	{
		mDrawble = prev.getDrawable();
		mMetersToPixelsX = prev.getXMTP();
		mMetersToPixelsY = prev.getYMTP();
		mBounds = mDrawble.copyBounds();
	}
		
	private float getYMTP() {
		return mMetersToPixelsY;
	}

	private float getXMTP() {
		return mMetersToPixelsX;
	}

	private Drawable getDrawable() {
		return mDrawble;
	}

	public Graphic(Drawable oDrawble, float metersToPixelsX, float metersToPixelsY, float widthMeters, float heightMeters)	
	{
		mDrawble = oDrawble;
		
		mMetersToPixelsX = metersToPixelsX;
		mMetersToPixelsY = metersToPixelsY;	
		
		mDrawble.setBounds(0, 0, (int)(widthMeters * mMetersToPixelsX), (int)(heightMeters * mMetersToPixelsY));
		mBounds = mDrawble.copyBounds();		
	}
	
	public void setOrigin(float left, float top) {
		mXOrigin = left;
		mYOrigin = top;
		
		mBounds.offsetTo((int)left, (int)top);
	}
	
	protected void SetBounds()
	{
		final float x = mXOrigin + mPosX * mMetersToPixelsX;
		final float y = mYOrigin - mPosY * mMetersToPixelsY;
		mBounds.offsetTo((int) x, (int)y);	
	}
	
	public void Draw(Canvas canvas)
	{
		mDrawble.setBounds(mBounds);
		mDrawble.draw(canvas);
	}

	public Rect getPlace() {
		return mBounds;
	}
	
}
