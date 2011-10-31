package com.games.andronoid;

import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;

public class GameActivity extends Activity {
	
	public final class ParamKeys{
		
		static public final String Mosaic = "com.games.andronoid.mosaic";
		static public final String Background = "com.games.andronoid.background";
		static public final String Music = "com.games.andronoid.music";
		static public final String NextLevel = "com.games.andronoid.next_level";
		
		private ParamKeys(){}
	}
	
	
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
        mWakeLock.acquire();
        
        GameSettings settings = new GameSettings(this);                
        mGameView = new GameView(this, settings);        
        setContentView(mGameView);
        mGameView.startGame();
        
        if(settings.isMusicOn()){
	        mPlayer = createMusicPlayer(settings.getMusicFile());
        }
        else{
        	mPlayer = null;
        }		
    }
    
    @Override
    protected void onPause() {
        super.onPause();

        mGameView.stopGame();
        mWakeLock.release();        
        releaseMusicPlayer();
    }
    
    private MediaPlayer createMusicPlayer(String sMusicFile){
    	
    	MediaPlayer player = MediaPlayer.create(this, R.raw.andronoid);        
    	player.setLooping(true);
    	player.start();
    	
    	return player;
    }
    
    private void releaseMusicPlayer(){
    	
        if(mPlayer != null){
        	mPlayer.stop();
        	mPlayer.release();
        	mPlayer = null;
        }    	
    }    
}