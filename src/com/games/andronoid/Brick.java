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
		blue,
		pink,
		red,
		sea,
		yellow
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

	public static Brick Create(Context context, Type type, Resources rc, float metersToPixelsX, float metersToPixelsY, boolean isSoundOn) {
		switch(type)
		{
		case green:
			return isSoundOn ? new NosyBrick(context, rc.getDrawable(R.drawable.green), metersToPixelsX, metersToPixelsY)
			: new Brick(rc.getDrawable(R.drawable.green), metersToPixelsX, metersToPixelsY);
		case blue:
			return isSoundOn ? new NosyBrick(context, rc.getDrawable(R.drawable.blue), metersToPixelsX, metersToPixelsY)
			: new Brick(rc.getDrawable(R.drawable.blue), metersToPixelsX, metersToPixelsY);
		case pink:
			return isSoundOn ? new NosyBrick(context, rc.getDrawable(R.drawable.pink), metersToPixelsX, metersToPixelsY)
			: new Brick(rc.getDrawable(R.drawable.pink), metersToPixelsX, metersToPixelsY);
		case red:
			return isSoundOn ? new NosyBrick(context, rc.getDrawable(R.drawable.red), metersToPixelsX, metersToPixelsY)
			: new Brick(rc.getDrawable(R.drawable.red), metersToPixelsX, metersToPixelsY);
		case sea:
			return isSoundOn ? new NosyBrick(context, rc.getDrawable(R.drawable.sea), metersToPixelsX, metersToPixelsY)
			: new Brick(rc.getDrawable(R.drawable.sea), metersToPixelsX, metersToPixelsY);
		case yellow:
			return isSoundOn ? new NosyBrick(context, rc.getDrawable(R.drawable.yellow), metersToPixelsX, metersToPixelsY)
			: new Brick(rc.getDrawable(R.drawable.yellow), metersToPixelsX, metersToPixelsY);
		case hole:
			return new Hole(metersToPixelsX, metersToPixelsY);
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
