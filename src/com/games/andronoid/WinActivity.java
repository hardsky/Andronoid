package com.games.andronoid;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

public class WinActivity extends Activity implements OnClickListener {

	public final class ParamKeys{
		
		static public final String NextLevel = "com.games.andronoid.next_level";
		
		private ParamKeys(){}
	}

	private String mNextLevel;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		Intent intent = getIntent();
        mNextLevel = intent.getExtras().getString(ParamKeys.NextLevel);
        LevelLock oLock = new LevelLock(this);
        oLock.unlockLevel(mNextLevel);
		
		
		setContentView(R.layout.game_win_layout);
		
		View txtWin = findViewById(R.id.txtWinNext);
		txtWin.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		Intent intent = new Intent(this, StageActivity.class);
		intent.putExtra(StageActivity.ParamKeys.StartLevel, mNextLevel);
		startActivity(intent);		
	}

}
