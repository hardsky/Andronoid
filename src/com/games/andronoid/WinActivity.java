package com.games.andronoid;

import com.google.ads.AdRequest;
import com.google.ads.AdView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

public class WinActivity extends Activity implements OnClickListener {

	public final class ParamKeys{
		
		static public final String NextLevel = "com.games.andronoid.next_level";
		static public final String Life = "life";
		static public final String Score = "score";
		static public final String Time = "time";
		
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
		
        int nLife = intent.getExtras().getInt(ParamKeys.Life);
        int nScore = intent.getExtras().getInt(ParamKeys.Score);
        int nSeconds = intent.getExtras().getInt(ParamKeys.Time);
		
		setContentView(R.layout.game_win_layout);
		
		View txtWin = findViewById(R.id.txtWinNext);
		txtWin.setOnClickListener(this);		
	}

	@Override
	public void onClick(View v) {
		Intent intent = new Intent(this, StageActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		intent.putExtra(StageActivity.ParamKeys.StartLevel, mNextLevel);
		startActivity(intent);
		finish();
	}

}