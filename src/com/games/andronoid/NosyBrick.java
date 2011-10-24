package com.games.andronoid;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.util.DisplayMetrics;

public class NosyBrick extends Brick {
	
	private Context mContext;
	public NosyBrick(Context context, Drawable oBrick, DisplayMetrics metrics)
	{
		super(oBrick, metrics);
		mContext = context;
	}
	
	public void Din()
	{
		MediaPlayer oPlayer = MediaPlayer.create(mContext, R.raw.kick);        
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
