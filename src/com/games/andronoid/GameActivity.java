package com.games.andronoid;

import java.io.FileDescriptor;
import java.io.IOException;

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
        /*
         * when the activity is resumed, we acquire a wake-lock so that the
         * screen stays on, since the user will likely not be fiddling with the
         * screen or buttons.
         */
        mWakeLock.acquire();
        /*
        MediaPlayer mediaPlayer = new MediaPlayer(); //MediaPlayer.create(this,getAssets().open("track.mp3"));
        try {
        	FileDescriptor fd = new FileDescriptor();
        	
			mediaPlayer.setDataSource(fd);//.setDataSource("track.mp3");
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
        
        mPlayer = MediaPlayer.create(this, R.raw.kick);        
        mPlayer.setLooping(true);
        mPlayer.start();
		mGameView.startGame();
    }

    @Override
    protected void onPause() {
        super.onPause();
        /*
         * When the activity is paused, we make sure to stop the simulation,
         * release our sensor resources and wake locks
         */

        mPlayer.stop();
        // Stop the simulation
        mGameView.stopGame();

        // and release our wake-lock
        mWakeLock.release();
    }
    
}