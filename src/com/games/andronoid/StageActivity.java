package com.games.andronoid;

import com.google.ads.AdRequest;
import com.google.ads.AdView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;

public class StageActivity extends Activity {
	
	public final class ParamKeys{
		
		static public final String StartLevel = "com.games.andronoid.start_level";
		
		private ParamKeys(){}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.stage_layout);
			
		LevelLock oLvlLocks = new LevelLock(this);
        
		LinearLayout ll = (LinearLayout)findViewById(R.id.ll);
		Game gm = Parser.CreateGame(getResources());
		while(!gm.IsEnded())
		{
			Stage st = (Stage)gm.Next();
			
			while(!st.IsEnded()){
				Level lv = (Level)st.Next();
				
				StageView view = new StageView(this);
				
				view.setText(getString(R.string.level) + " " + lv.getLevelName());
				view.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
				
				view.setLevelName(lv.getLevelName());
				view.setMosaicName(lv.getMosaicName());
				view.setBackgroundName(st.getBackgroundImgFile());
				view.setMusicFile(st.getMusic());
				if(ll.getChildCount() > 0){
					
					StageView prevView = (StageView)ll.getChildAt(ll.getChildCount() - 1);
					prevView.setNextLevel(view.getLevelName());
				}
				
				view.setLock(oLvlLocks.isLocked(lv.getLevelName()));					

				
				view.setOnClickListener(mClickListener);

					
				ll.addView(view);
			}
		}
		
		oLvlLocks.update();

		//if WinActivity started new level
        String sStartLevel = getStartLvl();
        if(sStartLevel != null){
        	        
        	for(int i = 0; i < ll.getChildCount(); i++){
        		
        		StageView stView = (StageView)ll.getChildAt(i);
        		if(stView.getLevelName().equalsIgnoreCase(sStartLevel))
    				StartGameActivity(stView.getMosaicName(), stView.getBackgroundFile(), stView.getMusicFile(), stView.getNextLevel());        			
        	}
        }
        
	    // Look up the AdView as a resource and load a request.
	    AdView adView = (AdView)this.findViewById(R.id.adView);
	    adView.loadAd(new AdRequest());	    
        
	}
	
	private String getStartLvl(){
		
		Intent intent = getIntent();
		Bundle bn = intent.getExtras();
		return bn != null ? bn.getString(ParamKeys.StartLevel): null;
	}
	
	private void StartGameActivity(String sMosaicName, String sBackGroundFile, String sMusicFile, String sNextLevelName){
		
		Intent intent = new Intent(this, GameActivity.class);
		
		intent.putExtra(GameActivity.ParamKeys.Mosaic, sMosaicName);
		intent.putExtra(GameActivity.ParamKeys.Background, sBackGroundFile);
		intent.putExtra(GameActivity.ParamKeys.Music, sMusicFile);
		intent.putExtra(GameActivity.ParamKeys.NextLevel, sNextLevelName);
		
		startActivity(intent);		
	}
	
    private OnClickListener mClickListener = new OnClickListener(){

		@Override
		public void onClick(View v) {						
			StageView stv = (StageView)v;
			if(!stv.isLocked())
				StartGameActivity(stv.getMosaicName(), stv.getBackgroundFile(), stv.getMusicFile(), stv.getNextLevel());			
		}    	
    };
}
