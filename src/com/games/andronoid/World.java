package com.games.andronoid;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Surface;
import android.view.WindowManager;

public class World implements SensorEventListener {
	
	private Ball mBall;
	private Bite mBite;
	private Mosaic mMosaic;
	private Rect mField;
	private Context mContext;
	private Life mLife;
	private Score mScore;
	private boolean mRunning;
		
	private Display mDisplay;
	private SensorManager mSensorManager;
	private Sensor mAccelerometer;
	private Resources mRc;
	
	private GameSettings mSettings;
		
	private float mMetersToPixelsX;
	private float mMetersToPixelsY;
	
	private GameTime mGameTime;
	
	public World(Context oContext, GamePlay oGamePlay, GameSettings settings)
	{
		mGameTime = new GameTime();
		mSettings = settings;
		mContext = oContext;
		mScore = new Score(mSettings.getDifficulty());		
		mLife = new Life(3, oContext);
	
		WindowManager windowManager = (WindowManager)oContext.getSystemService(Context.WINDOW_SERVICE);
        mDisplay = windowManager.getDefaultDisplay();		
        mSensorManager  = (SensorManager)oContext.getSystemService(Context.SENSOR_SERVICE);
        mAccelerometer  = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        DisplayMetrics metrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(metrics);
        mRc = oContext.getResources();
		
		mMetersToPixelsX = metrics.xdpi / 0.0254f;
		mMetersToPixelsY = metrics.ydpi / 0.0254f;	
        
		mBall = new Ball((BitmapDrawable) mRc.getDrawable(R.drawable.ball), mMetersToPixelsX, mMetersToPixelsY, mSettings.getDifficulty());
		mBite = new Bite((BitmapDrawable) mRc.getDrawable(R.drawable.bite), mMetersToPixelsX, mMetersToPixelsY, mSettings.getFriction());
		mMosaic = Parser.CreateMosaic(oContext, mRc, mSettings, mMetersToPixelsX, mMetersToPixelsY);
				
		//bite impacts ball
		if(mSettings.isSoundOn())
			mBite.RegisterObserver(new Sound(oContext, R.raw.impact));
		
		//brick is crashed
		if(mSettings.isSoundOn())
			mMosaic.RegisterObserver(new Sound(oContext, R.raw.kick));
		mMosaic.RegisterObserver(mScore);
		mMosaic.RegisterObserver(oGamePlay);
		
		//life is withdraw
		mLife.RegisterObserver(mScore);
		mLife.RegisterObserver(oGamePlay);
	}
	
	public void Start(){
		
		mRunning = true;
        mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_GAME);
        mGameTime.Start();
	}
	
	public void Stop(){
		
		mRunning = false;
        mSensorManager.unregisterListener(this);
        mGameTime.Stop();
        
        mBite.ClearObservers();
        mMosaic.ClearObservers();
        mLife.ClearObservers();
	}
	
	public boolean isRunning(){
		return mRunning;
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
		
	private void Intersect(Ball ball)
	{
		if(ball.getPlace().bottom > mField.bottom){
			LossBall();
		}
		else if(ball.getPlace().top < mField.top)
			ball.Impact(ImpactType.top, new Rect(0, 0, 1, mField.top - ball.getPlace().top));
		else if(ball.getPlace().left < mField.left)
			ball.Impact(ImpactType.left, new Rect(0, 0, mField.left - ball.getPlace().left, 1));
		else if(ball.getPlace().right > mField.right)
			ball.Impact(ImpactType.right, new Rect(0, 0, ball.getPlace().right - mField.right, 1));
	}
	
	private void LossBall()
	{
		mLife.Withdraw();
		
		float x = mField.exactCenterX();
		mBall = new Ball(mBall, mSettings.getDifficulty());
		mBite.ClearObservers();
		mBite = new Bite(mBite, mSettings.getFriction());
		if(mSettings.isSoundOn())
			mBite.RegisterObserver(new Sound(mContext, R.raw.impact));
		mBite.setOrigin(x-mBite.getPlace().width()/2, mField.height() - mBite.getPlace().height() - 1);//bite on the bottom
		mBall.setOrigin(x - mBall.getPlace().width()/2, mBite.getPlace().top - mBall.getPlace().height() - 1);
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
		mLife.Draw(canvas);
		mScore.Draw(canvas);
		mGameTime.Draw(canvas);
	}

	public Bite getBite() {
		return mBite;
	}

	public void setBounds(int width, int height) {
		mField = new Rect(0, 0, width, height);
		float x = mField.exactCenterX();
		mBite.setOrigin(x-mBite.getPlace().width()/2, height - mBite.getPlace().height() - 1);//bite on the bottom
		mBall.setOrigin(x - mBall.getPlace().width()/2, mBite.getPlace().top - mBall.getPlace().height() - 1);
		mMosaic.setOrigin(x - mMosaic.getPlace().width()/2, 1 + 20);
		
		mLife.setOrigin(0, 1);
		mScore.setOrigin(100, 18);
		mGameTime.setOrigin(160, 18);
	}

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		
	}

	@Override
	public void onSensorChanged(SensorEvent event) {
		
		if(event.sensor.getType() != Sensor.TYPE_ACCELEROMETER)
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
        
        mBite.onSensorChanged(sensorX, event.timestamp, System.nanoTime());		
	}
	
	public int getScore(){
		return mScore.getScore();
	}
	
	public int getLife(){
		return mLife.getLife();
	}
	
	public int getSeconds(){
		return mGameTime.getSeconds();
	}
}
