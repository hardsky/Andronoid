package com.games.andronoid;

import java.util.*;

import android.graphics.Canvas;
import android.graphics.Rect;

public class Mosaic{
	
	private ArrayList<Row> mRows;
	private Rect mBounds;
	public Mosaic()
	{
		mRows = new ArrayList<Row>();
		//mBounds needed in getPlace
		mBounds = new Rect(0, 0, 0, 0);
	}
	
	public void addRow(Row row)
	{
		row.setOrigin(mBounds.left, mBounds.bottom +1);
		row.AddBoundHandler(new IBounds(){
			public void OnIncrease(Rect added){			
				mBounds.union(added);
			}
		});
		mRows.add(row);
	}
		
	public void Intersect(Ball oBall) {
		Rect oBallBounds = oBall.getPlace();
		if(Rect.intersects(oBallBounds, mBounds))
		{
			for(Row row: mRows)
			{
				if(row.Intersect(oBall))
					break;			
			}
		}
	}

	public void setOrigin(float left, float top) {//temporary, that place this more proper
		mBounds.offsetTo((int)left, (int)top);
		for(Row row: mRows)
		{
			row.setOrigin(left, top);			
			top += row.getPlace().height() + 1;						
		}
	}

	public void Draw(Canvas canvas) {
		for(Row row: mRows)
			row.Draw(canvas);
	}
	
	public Rect getPlace() {
		return mBounds;
	}
}
