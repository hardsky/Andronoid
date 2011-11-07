package com.games.andronoid;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;

public class GameStatistic extends View {

	private Score mScore;
	private Life mLife;
	private GameTime mTime;
	
	public void setLife(Life life) {
		this.mLife = life;
	}
	
	public void setScore(Score score) {
		this.mScore = score;
	}	

	public void setTime(GameTime time){
		this.mTime = time;
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

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		
		if(mLife != null && mTime != null && mScore != null){
			
			mLife.setOrigin(1, 1);
			mTime.setOrigin(1, 25);
			mScore.setOrigin(1, 46);
			
			mLife.Draw(canvas);
			mTime.Draw(canvas);
			mScore.Draw(canvas);
		}
	}
}
