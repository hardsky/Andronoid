package com.games.andronoid;

import android.content.res.Resources;
import android.graphics.*;
import android.util.DisplayMetrics;
import android.graphics.drawable.Drawable;

public class Brick extends Graphic {

	public enum Type
	{
		green,
		hole
	}
	
	protected Brick()
	{
		
	}
	
	public Brick(Drawable oBrick, DisplayMetrics metrics)
	{
		super(oBrick, metrics, 0.01f, 0.005f);
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

	public static Brick Create(Type type, Resources rc) {
		DisplayMetrics metrics = rc.getDisplayMetrics();
		switch(type)
		{
		case green:
			return new Brick(rc.getDrawable(R.drawable.brick), metrics);
		case hole:
			return new Hole(metrics);
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
