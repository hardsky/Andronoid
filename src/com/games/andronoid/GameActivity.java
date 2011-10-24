package com.games.andronoid;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.preference.PreferenceManager;

public class GameActivity extends Activity {
	
    private PowerManager mPowerManager;
    private WakeLock mWakeLock;
    private GameView mGameView;
    private MediaPlayer mPlayer;
    
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
                
    }
    
    @Override
    protected void onResume() {
        super.onResume();
        mPowerManager = (PowerManager) getSystemService(POWER_SERVICE);
        mWakeLock = mPowerManager.newWakeLock(PowerManager.SCREEN_BRIGHT_WAKE_LOCK, getClass()
                .getName());
        
        Intent inte = getIntent();
        Bundle bn = inte.getExtras();
        
        String sMosaicName = (String)bn.get("com.games.andronoid.mosaic");
        String sBackGroundFile = (String)bn.get("com.games.andronoid.background");
        String sMusicFile = (String)bn.get("com.games.andronoid.music");
        
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(this);
        
        int nFriction = settings.getInt("friction", 30);
        int nMass = settings.getInt("mass", 50);
        boolean bMusic = settings.getBoolean("music", true);
        String sDifficulty = settings.getString("difficulty", "normal"); 
        
        mGameView = new GameView(this, sMosaicName, sBackGroundFile, sMusicFile, nFriction, nMass, bMusic, sDifficulty);
        setContentView(mGameView);
        mWakeLock.acquire();
        if(bMusic){
	        mPlayer = MediaPlayer.create(this, R.raw.andronoid);        
	        mPlayer.setLooping(true);
	        mPlayer.start();
        }
        else{
        	mPlayer = null;
        }
		mGameView.startGame();
    }

    @Override
    protected void onPause() {
        super.onPause();
        /*
         * When the activity is paused, we make sure to stop the simulation,
         * release our sensor resources and wake locks
         */

        if(mPlayer != null)
        	mPlayer.stop();
        
        // Stop the simulation
        mGameView.stopGame();

        // and release our wake-lock
        mWakeLock.release();
    }
    
}