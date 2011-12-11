package com.games.andronoid;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.util.AttributeSet;
import android.view.View;

public class GameStatistic extends View {

	private int mScore;
	private Life mLife;
	private int mSeconds;
	
	public void setLife(int nLife) {		
		this.mLife = new Life(nLife, getContext());
	}
	
	public void setScore(int nScore) {
		mScore = nScore;
	}	

	public void setTime(int nSeconds){
		mSeconds = nSeconds;
	}

	public GameStatistic(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public GameStatistic(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	public GameStatistic(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}

	private void DrawScore(Canvas canvas){
		Paint paint = new Paint(); 
		paint.setColor(Color.WHITE); 
		paint.setStyle(Style.FILL); 
		paint.setTextSize(20);
		String sTxt = String.valueOf(mScore);
		canvas.drawText(sTxt, 1, 43, paint);		
	}
	
	private void DrawTime(Canvas canvas){
		Paint paint = new Paint(); 
		paint.setColor(Color.WHITE); 
		paint.setStyle(Style.FILL); 
		paint.setTextSize(20);
		String sTxt = String.valueOf(mSeconds);
		canvas.drawText(sTxt, 1, 64, paint);				
	}
	
	private void DrawLabel(Canvas canvas){
		Paint paint = new Paint(); 
		paint.setColor(Color.WHITE); 
		paint.setStyle(Style.FILL); 
		paint.setTextSize(20);
		String sTxt = "Score:";
		canvas.drawText(sTxt, 1, 1, paint);		
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		
		if(mLife != null){
			
			mLife.setOrigin(1, 22);
			//mTime.setOrigin(1, 25);
			//mScore.setOrigin(1, 46);
			
			mLife.Draw(canvas);
			//mTime.Draw(canvas);
			//mScore.Draw(canvas);
			DrawScore(canvas);
			DrawTime(canvas);
		}
	}
}
