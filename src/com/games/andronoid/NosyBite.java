package com.games.andronoid;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.util.DisplayMetrics;

public class NosyBite extends Bite {

	private Context mContext;
	
	public NosyBite(Context context, BitmapDrawable oBite, DisplayMetrics metrics, int nFriction) {
		super(oBite, metrics, nFriction);
		
		mContext = context;
	}

	public NosyBite(Context context, Bite bite, int nFriction) {
		super(bite, nFriction);
		
		mContext = context;		
	}
	
	@Override
	public boolean Intesect(Ball ball) {
		if(super.Intesect(ball)){
			Din();
			return true;
		}
		
		return false;
	}

	private void Din()
	{
		MediaPlayer oPlayer = MediaPlayer.create(mContext, R.raw.impact);        
		oPlayer.setLooping(false);
		oPlayer.setOnCompletionListener(new OnCompletionListener(){

			@Override
			public void onCompletion(MediaPlayer pl) {
				// TODO Auto-generated method stub
				pl.release();
			}
			
		});
		oPlayer.start();		
	}
	
}
