package com.games.andronoid;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.DisplayMetrics;

public class Hole extends Brick {

	public Hole(DisplayMetrics metrics) {
		mMetersToPixelsX = metrics.xdpi / 0.0254f;
		mMetersToPixelsY = metrics.ydpi / 0.0254f;	
		
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
