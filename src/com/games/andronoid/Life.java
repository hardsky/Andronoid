package com.games.andronoid;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;

public class Life {
	
	private int mCount;
	private ArrayList<Drawable> mLives; 
	private Context mContext;
	private Score mScore;
	
	public Life(int nCount, Context context){
		mContext = context;
		Resources rc = context.getResources();
		mLives= new ArrayList<Drawable>(nCount);
		mCount = nCount;
		for(int i = 0; i < mCount; i++){
			mLives.add(rc.getDrawable(R.drawable.heart));
		}
		
		for(Drawable heart: mLives)
			heart.setBounds(0, 0, 20, 20);
	}
	
	public void Withdraw(){
		if(mLives.size() != 0)
			mLives.remove(mLives.size() - 1);
		
		if(mLives.size() == 0){
			Intent intent = new Intent(mContext, GameOverActivity.class);
			mContext.startActivity(intent);
		}
			
		mScore.Excite(ScoreType.loss);
	}
	
	public void Draw(Canvas canvas){
		for(Drawable heart: mLives)
			heart.draw(canvas);
	}
	
	public void setOrigin(){
		int x = 0;
		int nSpace = 5;
		Rect bounds;
		for(Drawable heart: mLives){
			bounds = heart.copyBounds();
			x += heart.getBounds().width();
			x += nSpace;
			bounds.offsetTo(x, 1);
			heart.setBounds(bounds);
		}		
	}

	public void setScoreHandler(Score score) {
		mScore = score;
	}
			
}
