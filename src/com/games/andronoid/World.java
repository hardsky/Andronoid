package com.games.andronoid;

import android.graphics.Rect;
import android.graphics.Canvas;

public class World {
	
	private Ball mBall;
	private Bite mBite;
	private Mosaic mMosaic;
	private Rect mField;

	public World(Ball ball, Bite bite, Mosaic mosaic)
	{
		mBall = ball;
		mBite = bite;
		mMosaic = mosaic;	
		
		mBite.registerAccelerometer();
	}
	
	public void Stop()
	{
		mBite.unregisterAccelerometer();		
	}
		
	private void ComputePhysics()
	{
		long nCurrentTimeStamp = System.nanoTime();
		mBall.setCurrentTime(nCurrentTimeStamp);
		mBite.setCurrentTime(nCurrentTimeStamp);
		
		if(!mBite.Intesect(mBall))
			mMosaic.Intersect(mBall);				
		
		Intersect(mBall);
		Intersect(mBite);
	}
	
	public void Intersect(Ball ball)
	{
		if(ball.getPlace().bottom > mField.bottom)
			LossBall();
		else if(ball.getPlace().top < mField.top)
			ball.Impact(ImpactType.top, new Rect(0, 0, 1, mField.top - ball.getPlace().top));
		else if(ball.getPlace().left < mField.left)
			ball.Impact(ImpactType.left, new Rect(0, 0, mField.left - ball.getPlace().left, 1));
		else if(ball.getPlace().right > mField.right)
			ball.Impact(ImpactType.right, new Rect(0, 0, ball.getPlace().right - mField.right, 1));
	}
	
	private void LossBall()
	{
		float x = mField.exactCenterX();
		mBall = new Ball(mBall);
		mBite.unregisterAccelerometer();
		mBite = new Bite(mBite);
		mBite.registerAccelerometer();
		mBite.setOrigin(x-mBite.getPlace().width()/2, mField.height() - mBite.getPlace().height() - 1);//bite on the bottom
		mBall.setOrigin(x - mBall.getPlace().width()/2, mBite.getPlace().top - mBall.getPlace().height() - 1);
		//TODO: attach game play
	}
	private void Intersect(Bite bite) {
		if(bite.getPlace().left < mField.left)
			bite.Impact(ImpactType.left, new Rect(0, 0, mField.left - bite.getPlace().left, 1));
		else if(bite.getPlace().right > mField.right)
			bite.Impact(ImpactType.right, new Rect(0, 0, bite.getPlace().right - mField.right, 1));
		
	}

	public void Draw(Canvas canvas)
	{
		ComputePhysics();
		
		mBall.Draw(canvas);
		mBite.Draw(canvas);
		mMosaic.Draw(canvas);
	}

	public Bite getBite() {
		return mBite;
	}

	public void setBounds(int width, int height) {
		mField = new Rect(0, 0, width, height);
		float x = mField.exactCenterX();
		mBite.setOrigin(x-mBite.getPlace().width()/2, height - mBite.getPlace().height() - 1);//bite on the bottom
		mBall.setOrigin(x - mBall.getPlace().width()/2, mBite.getPlace().top - mBall.getPlace().height() - 1);
		mMosaic.setOrigin(x - mMosaic.getPlace().width()/2, 1);		
	}

}
