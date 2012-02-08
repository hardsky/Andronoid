package com.games.andronoid;

import android.content.Context;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;

public class Sound implements IObserver {
	private Context mContext;
	private int mSoundId;
	
	public Sound(Context oContext, int nSoundId){
		mContext = oContext;
		mSoundId = nSoundId;
	}

	@Override
	public void update(ISubject subject) {
		MediaPlayer oPlayer = MediaPlayer.create(mContext, mSoundId);        
		oPlayer.setLooping(false);
		
		oPlayer.setOnCompletionListener(new OnCompletionListener(){

			@Override
			public void onCompletion(MediaPlayer pl) {
				
				if(pl != null)
					pl.release();
			}
			
		});
		
		oPlayer.start();		
	}
}
