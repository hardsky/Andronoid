package com.games.andronoid;

import java.util.*;

import android.graphics.Canvas;
import android.graphics.Rect;

public class Mosaic{
	
	private ArrayList<Row> mRows;
	private Rect mBounds;
	
	public Mosaic(){
		
		mRows = new ArrayList<Row>();			
		mBounds = new Rect(0, 0, 0, 0);//mBounds needed in getPlace
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
		
	public boolean Intersect(Ball oBall) {
		
		Rect oBallBounds = oBall.getPlace();
		if(Rect.intersects(oBallBounds, mBounds))
			for(Row row: mRows)
				if(row.Intersect(oBall)){
				
					if(row.isEmpty())
						mRows.remove(row);
					
					return true;
				}
		
		return false;
	}

	public void setOrigin(float left, float top) {//temporary, that place this more proper
		
		mBounds.offsetTo((int)left, (int)top);
		for(Row row: mRows){
			
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

	public boolean isEmpty() {

		return mRows.isEmpty();
	}
}
