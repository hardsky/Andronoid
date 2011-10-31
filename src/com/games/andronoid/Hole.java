package com.games.andronoid;

import android.graphics.Canvas;
import android.graphics.Rect;

public class Hole extends Brick {

	public Hole(float metersToPixelsX, float metersToPixelsY) {
		mMetersToPixelsX = metersToPixelsX;
		mMetersToPixelsY = metersToPixelsY;	
		
		mBounds = new Rect(0, 0, (int)(0.01f * mMetersToPixelsX), (int)(0.005f * mMetersToPixelsY));
	}
	
	@Override
	public void setOrigin(float left, float top)
	{
		mXOrigin = left;
		mYOrigin = top;
		
		mBounds.offsetTo((int)left, (int)top);		
	}
	
	@Override
	public void Draw(Canvas canvas)
	{
	}
	
	@Override
	public boolean Intersect(Ball oBall) {
		return false;
	}	

}
