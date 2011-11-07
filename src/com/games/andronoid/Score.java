package com.games.andronoid;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;

public class Score implements IObserver {
	
	private String mDifficulty;
	private int mScore = 0;
	private int mLeft;
	private int mTop;
	
	public Score Clone(){
		Score clone = new Score(mDifficulty);
		clone.mScore = mScore;
		return clone;
	}
	
	public int getScore() {
		return mScore;
	}

	public Score(String sDifficulty){
		mDifficulty = sDifficulty;
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
		String sTxt = String.valueOf(this.getScore());
		canvas.drawText(sTxt, mLeft, mTop, paint);		
	}
	
	@Override
	public void update(ISubject subject) {
		
		int nExchange = 0;
		if(mDifficulty.equalsIgnoreCase(DifficultyType.EASY)){
			nExchange = 100;
		}
		else if(mDifficulty.equalsIgnoreCase(DifficultyType.NORMAL)){
			nExchange = 200;
		}
		else{
			nExchange = 300;
		}
		
		switch(subject.getState())
		{
		case brick:
			mScore += nExchange;
			break;
		case life:
			mScore -= nExchange;
			break;
		}
	}
}
