package com.games.andronoid;

import java.io.IOException;

import android.content.Context;
import android.content.res.*;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Surface;
import android.view.View;
import android.view.WindowManager;

public class GameView extends View implements SensorEventListener{

	private Display mDisplay = null;
	private SensorManager mSensorManager = null;
	private Sensor mAccelerometer = null;
	private Stage mStage;
	private Resources mRc;
	private World mWorld;
	private DisplayMetrics mMetrics;
	
	private String mMosaicName;
	private String mBackGroundFile;
	private String mMusicFile;
	
	private int mFriction;
	private int mMass;
	private boolean mEnableMusic;
	private String mDifficulty;

	public GameView(Context context, String sMosaicName, String sBackGroundFile, String sMusicFile, int nFriction, int nMass, boolean bMusic, String sDifficulty) {
		super(context);
		mMosaicName = sMosaicName;
		mBackGroundFile = sBackGroundFile;
		mMusicFile = sMusicFile;
		
		mFriction = nFriction;
		mMass = nMass;
		mEnableMusic = bMusic;
		mDifficulty = sDifficulty;
		
		WindowManager windowManager = (WindowManager)context.getSystemService(Context.WINDOW_SERVICE);
        mDisplay = windowManager.getDefaultDisplay();		
        mSensorManager  = (SensorManager)context.getSystemService(Context.SENSOR_SERVICE);
        mAccelerometer  = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mMetrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(mMetrics);
        
        mRc = getResources();
	}


	public void startGame(){
		mWorld = CreateWorld();
		
        mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_UI);
		
        try {
			this.setBackgroundDrawable(new BitmapDrawable(mRc, mRc.getAssets().open(mBackGroundFile)));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private World CreateWorld()
	{		
		Ball oBall = new Ball((BitmapDrawable) mRc.getDrawable(R.drawable.ball), mMetrics, mDifficulty);
		Bite oBite = new Bite((BitmapDrawable) mRc.getDrawable(R.drawable.bite), mMetrics, mFriction);
		Mosaic oMosaic = Parser.CreateMosaic(mRc, mMosaicName);
		return new World(oBall, oBite, oMosaic, mDifficulty, mFriction);
	}
	
	public void stopGame() {
        mWorld.Stop();
        mSensorManager.unregisterListener(this);
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


	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void onSensorChanged(SensorEvent event) {
        if (mWorld == null || event.sensor.getType() != Sensor.TYPE_ACCELEROMETER)
            return;
        /*
         * record the accelerometer data, the event's timestamp as well as
         * the current time. The latter is needed so we can calculate the
         * "present" time during rendering. In this application, we need to
         * take into account how the screen is rotated with respect to the
         * sensors (which always return data in a coordinate space aligned
         * to with the screen in its native orientation).
         */

        float sensorX = 0;
        switch (mDisplay.getOrientation()) {
            case Surface.ROTATION_0:
            	sensorX = event.values[0];
			break;
            case Surface.ROTATION_90:
            	sensorX = -event.values[1];
			break;
            case Surface.ROTATION_180:
            	sensorX = -event.values[0];
			break;
            case Surface.ROTATION_270:
            	sensorX = event.values[1];
			break;
        }

        mWorld.onSensorChanged(sensorX, event.timestamp, System.nanoTime());
	}
	
}
