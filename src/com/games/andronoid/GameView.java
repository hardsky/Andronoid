package com.games.andronoid;

import java.io.IOException;

import android.content.Context;
import android.content.res.*;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;

public class GameView extends View{

	private Display mDisplay = null;
	private SensorManager mSensorManager = null;
	private Sensor mAccelerometer = null;
	private Game mGame;
	private Stage mStage;
	private Level mLevel;
	private Resources mRc;
	private World mWorld;
	private DisplayMetrics mMetrics;

	public GameView(Context context) {
		super(context);
		WindowManager windowManager = (WindowManager)context.getSystemService(Context.WINDOW_SERVICE);
        mDisplay = windowManager.getDefaultDisplay();		
        mSensorManager  = (SensorManager)context.getSystemService(Context.SENSOR_SERVICE);
        mAccelerometer  = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mMetrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(mMetrics);
        
        mRc = getResources();
		mGame = Parser.CreateGame(mRc);
		mStage = (Stage) mGame.Next();
		mLevel = (Level) mStage.Next();		
	}


	public void startGame(){
		mWorld = CreateWorld();
		
        //mSensorManager.registerListener(mWorld.getBite(), mAccelerometer, SensorManager.SENSOR_DELAY_UI);
		
        try {
			this.setBackgroundDrawable(new BitmapDrawable(mRc, mRc.getAssets().open(mStage.getBackgroundImgFile())));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private World CreateWorld()
	{		
		Ball oBall = new Ball((BitmapDrawable) mRc.getDrawable(R.drawable.ball), mMetrics);
		Bite oBite = new Bite((BitmapDrawable) mRc.getDrawable(R.drawable.bite), mDisplay, mMetrics, mSensorManager);
		Mosaic oMosaic = Parser.CreateMosaic(mRc, mLevel.getMosaicName());
		return new World(oBall, oBite, oMosaic);
	}
	
	public void stopGame() {
        mWorld.Stop();
	}
	
    @Override
    protected void onDraw(Canvas canvas) {
    	mWorld.Draw(canvas);
        // and make sure to redraw ASAP
        invalidate();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
    	mWorld.setBounds(w, h);
    }
	
}
