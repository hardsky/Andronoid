package com.games.andronoid;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;

public class GameActivity extends Activity {
	
    private PowerManager mPowerManager;
    private WakeLock mWakeLock;
    private GameView mGameView;
    
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        mPowerManager = (PowerManager) getSystemService(POWER_SERVICE);
        mWakeLock = mPowerManager.newWakeLock(PowerManager.SCREEN_BRIGHT_WAKE_LOCK, getClass()
                .getName());
        
        Intent inte = getIntent();
        Bundle bn = inte.getExtras();
        
        String sMosaicName = (String)bn.get("com.games.andronoid.mosaic");
        String sBackGroundFile = (String)bn.get("com.games.andronoid.background");
        String sMusicFile = (String)bn.get("com.games.andronoid.music");
				        

        mGameView = new GameView(this, sMosaicName, sBackGroundFile, sMusicFile);
        setContentView(mGameView);
        
    }
    
    @Override
    protected void onResume() {
        super.onResume();
        /*
         * when the activity is resumed, we acquire a wake-lock so that the
         * screen stays on, since the user will likely not be fiddling with the
         * screen or buttons.
         */
        mWakeLock.acquire();

		mGameView.startGame();
    }

    @Override
    protected void onPause() {
        super.onPause();
        /*
         * When the activity is paused, we make sure to stop the simulation,
         * release our sensor resources and wake locks
         */

        // Stop the simulation
        mGameView.stopGame();

        // and release our wake-lock
        mWakeLock.release();
    }
    
}