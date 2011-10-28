package com.games.andronoid;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.TextView;

public class StageActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.stage_layout);
				
		LinearLayout ll = (LinearLayout)findViewById(R.id.ll);
		Game gm = Parser.CreateGame(getResources());
		while(!gm.IsEnded())
		{
			Stage st = (Stage)gm.Next();
			while(!st.IsEnded()){
				Level lv = (Level)st.Next();
				
				StageView view = new StageView(this);
				view.setText(getString(R.string.level) + " " + lv.getLevelName());
				view.setBackgroundName(st.getBackgroundImgFile());
				view.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
				view.setTag(lv.getMosaicName() + "," + st.getBackgroundImgFile() + "," + st.getMusic());
				view.setOnClickListener(mClickListener);

				ll.addView(view);				
			}
		}		
	}
	
	private void StartGameActivity(String sMosaicName, String sBackGroundFile, String sMusicFile){
		Intent intent = new Intent(this, GameActivity.class);
		intent.putExtra("com.games.andronoid.mosaic", sMosaicName);
		intent.putExtra("com.games.andronoid.background", sBackGroundFile);
		intent.putExtra("com.games.andronoid.music", " ");
		startActivity(intent);		
	}
	
    private OnClickListener mClickListener = new OnClickListener(){

		@Override
		public void onClick(View v) {
			
			String sData = (String)v.getTag();
			String[] arParams = sData.split(",");			
			StartGameActivity(arParams[0], arParams[1], " ");
		}
    	
    };
	

}
