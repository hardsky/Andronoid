package com.games.andronoid;

import android.graphics.Canvas;
import android.graphics.Rect;

import java.util.*;

public class Row{

	private ArrayList<Brick> mBricks;
	private Rect mBounds;
	private ArrayList<IBounds> mBoundHandlers;
	
	public Row()
	{
		mBricks = new ArrayList<Brick>();
		mBoundHandlers = new ArrayList<IBounds>();
		//mBounds needed in getPlace
		mBounds = new Rect(0, 0, 0, 0);
	}
	
	public void AddBoundHandler(IBounds handler)
	{
		mBoundHandlers.add(handler);
	}
	
	public void addBrick(Brick brick) {
		mBricks.add(brick);
		brick.setOrigin(mBounds.right + 1, mBounds.top);
		mBounds.union(brick.getPlace());
		
		for(IBounds handler: mBoundHandlers)
			handler.OnIncrease(brick.getPlace());
	}
	
	public void Draw(Canvas canvas) {
		for(Brick brick: mBricks)
			brick.Draw(canvas);
	}

	public void setOrigin(float left, float top) {
		//mBounds needed in getPlace
		mBounds.offsetTo((int)left, (int)top);
		
		for(Brick brick: mBricks)
		{
			brick.setOrigin(left, top);			
			left += brick.getPlace().width() + 1;			
		}
	}

	public Rect getPlace() {
		return mBounds;
	}

	public boolean Intersect(Ball oBall) {
		if(Rect.intersects(oBall.getPlace(), getPlace()))
		{
			for(Brick brick: mBricks)
			{
				if(brick.Intersect(oBall))
				{
					if(brick instanceof NosyBrick)
						((NosyBrick) brick).Din();
					mBricks.remove(brick);
					
					return true;					
				}
				
			}
		}
		
		return false;
	}
	
	public boolean isEmpty(){
		return mBricks.size() == 0;
	}
}
