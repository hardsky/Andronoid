package com.games.andronoid;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

public class RulesActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.rules_layout);
		
	    Utility.SetClickListener(findViewById(R.id.btnBack), mBackListener);
	}
	
	private OnClickListener mBackListener = new OnClickListener() {
	    public void onClick(View v) {
	    	finish();
	    }
	};		
}
