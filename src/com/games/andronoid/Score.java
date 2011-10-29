package com.games.andronoid;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;

public class Score implements IObserver {
	
	private String mDifficulty;
	private int mScore = 0;
	
	public int getScore() {
		return mScore;
	}

	public Score(String sDifficulty){
		mDifficulty = sDifficulty;
	}
	
	public void Draw(Canvas canvas){
		Paint paint = new Paint(); 
		paint.setColor(Color.WHITE); 
		paint.setStyle(Style.FILL); 
		paint.setTextSize(20); 
		canvas.drawText(String.valueOf(this.getScore()), 100, 20, paint);		
	}

	@Override
	public void update(ISubject subject) {
		
		int nExchange = 0;
		if(mDifficulty.equalsIgnoreCase("easy")){
			nExchange = 100;
		}
		else if(mDifficulty.equalsIgnoreCase("normal")){
			nExchange = 200;
		}
		else{
			nExchange = 300;
		}
		
		switch(subject.getScoreType())
		{
		case brick:
			mScore += nExchange;
			break;
		case loss:
			mScore -= nExchange;
			break;
		}
	}
}
