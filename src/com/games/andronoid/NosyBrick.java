package com.games.andronoid;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;

public class NosyBrick extends Brick {
	
	private Context mContext;
	
	public NosyBrick(Context context, Drawable oBrick, float metersToPixelsX, float metersToPixelsY){
		
		super(oBrick, metersToPixelsX, metersToPixelsY);
		mContext = context;
	}
	
	public void Din(){
		
		MediaPlayer oPlayer = MediaPlayer.create(mContext, R.raw.kick);        
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
