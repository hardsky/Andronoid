package com.games.andronoid;

import java.util.*;

import android.graphics.Canvas;
import android.graphics.Rect;

public class Mosaic implements ISubject{
	
	private ArrayList<Row> mRows;
	private Rect mBounds;
	private ArrayList<IObserver> mObservers;
	
	public Mosaic()
	{
		mRows = new ArrayList<Row>();
		mObservers = new ArrayList<IObserver>();
		
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
				if(row.Intersect(oBall)){
					NotifyObservers();
					break;			
				}
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

	@Override
	public void RegisterObserver(IObserver observer) {
		mObservers.add(observer);
	}

	@Override
	public void RemoveObserver(IObserver observer) {
		mObservers.remove(observer);
	}

	@Override
	public void NotifyObservers() {
		for(IObserver ob: mObservers){
			ob.update(this);
		}
	}

	@Override
	public ScoreType getScoreType() {
		return ScoreType.brick;
	}

	@Override
	public boolean isEmpty() {
		for(Row row: mRows)
			if(row.isEmpty())
				return true;
		
		return false;
	}
}
