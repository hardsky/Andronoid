package com.games.andronoid;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class StartActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.start_layout);
			    
	    Utility.SetClickListener(findViewById(R.id.btnStart), mStartListener);
	    Utility.SetClickListener(findViewById(R.id.btnRules), mRulesListener);
	    Utility.SetClickListener(findViewById(R.id.btnSettings), mSettingsListener);
	    Utility.SetClickListener(findViewById(R.id.btnExit), mExitListener);
	}
	
	private void StartStageActivity(){
		Intent intent = new Intent(this, StageActivity.class);		
		startActivity(intent);		
	}
	
	private OnClickListener mStartListener = new OnClickListener() {
	    public void onClick(View v) {
	    	StartStageActivity();
	    }
	};
		
	private OnClickListener mRulesListener = new OnClickListener() {
	    public void onClick(View v) {
	      // do something when the button is clicked
	    }
	};
	
	private OnClickListener mSettingsListener = new OnClickListener() {
	    public void onClick(View v) {
	      // do something when the button is clicked
	    }
	};	
	
	private OnClickListener mExitListener = new OnClickListener() {
	    public void onClick(View v) {
	    	finish();
	    }
	};	
	
}
