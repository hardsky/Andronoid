package com.games.andronoid;

import java.util.Timer;
import java.util.TimerTask;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;

public class GameTime {

	public final class UpdateTask extends TimerTask{

		private GameTime mGt;
		
		public UpdateTask(GameTime gt){
			mGt = gt;
		}


		@Override
		public void run() {
			mGt.setCurrentTime(System.currentTimeMillis());
		}		
	}
	
	private long mStartedTime;
	private long mCurrentTime;
	private Timer mUpdaterShedule;
	private int mLeft;
	private int mTop;

	public GameTime Clone(){
		GameTime clone = new GameTime();
		clone.mStartedTime = mStartedTime;
		clone.mCurrentTime = mCurrentTime;
		clone.mUpdaterShedule = new Timer();
		
		return clone;
	}
	
	public GameTime(){
		
		mUpdaterShedule = new Timer();		
	}
	
	public void setCurrentTime(long currentTimeMillis) {
		
		mCurrentTime = currentTimeMillis;
	}
	
	public void Start(){
		
		mStartedTime = System.currentTimeMillis();
		mCurrentTime = mStartedTime;
		mUpdaterShedule.schedule(new UpdateTask(this), 0, 300);
	}
	
	public void Stop(){
		mUpdaterShedule.cancel();
		mCurrentTime = System.currentTimeMillis();
	}
	
	@Override
	public String toString(){
		
		long nElapsed = mCurrentTime - mStartedTime;
		int nMinutes = (int)(nElapsed / 60000f);
		int nSeconds = (int)(nElapsed / 1000 - nMinutes * 60);
		
		return String.format("%02d:%02d", nMinutes, nSeconds);
	}
	
	public void setOrigin(int left, int top){
			
		mLeft = left;
		mTop = top;
	}
	
	public void Draw(Canvas canvas){
		Paint paint = new Paint(); 
		paint.setColor(Color.WHITE); 
		paint.setStyle(Style.FILL); 
		paint.setTextSize(20); 
		canvas.drawText(toString(), mLeft, mTop, paint);		
	}	
}
