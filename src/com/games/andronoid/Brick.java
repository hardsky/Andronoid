package com.games.andronoid;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.*;
import android.graphics.drawable.Drawable;

public class Brick extends Graphic {

	public enum Type
	{
		green,
		hole,
		nosy
	}
	
	protected Brick()
	{
		
	}
	
	public Brick(Drawable oBrick, float metersToPixelsX, float metersToPixelsY)
	{
		super(oBrick, metersToPixelsX, metersToPixelsY, 0.01f, 0.005f);
	}
	
	@Override
	public void setOrigin(float left, float top)
	{
		super.setOrigin(left, top);
		mDrawble.setBounds(mBounds);		
	}
	
	@Override
	public void Draw(Canvas canvas)
	{
		mDrawble.draw(canvas);
	}

	public static Brick Create(Context context, Type type, Resources rc, float metersToPixelsX, float metersToPixelsY) {
		switch(type)
		{
		case green:
			return new Brick(rc.getDrawable(R.drawable.green), metersToPixelsX, metersToPixelsY);
		case hole:
			return new Hole(metersToPixelsX, metersToPixelsY);
		case nosy:
			return new NosyBrick(context, rc.getDrawable(R.drawable.green), metersToPixelsX, metersToPixelsY);
		}
		return null;
	}

	public boolean Intersect(Ball oBall) {
		Rect rt = new Rect();
		if(rt.setIntersect(mBounds, oBall.getPlace()))
		{
			return oBall.Intersect(rt);
		}
		return false;
	}	
}
