package com.games.andronoid;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

public class StartActivity extends Activity implements OnClickListener {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.start_layout);
			    
	    Utility.SetClickListener(findViewById(R.id.btnStart), this);
	    Utility.SetClickListener(findViewById(R.id.btnRules), this);
	    Utility.SetClickListener(findViewById(R.id.btnSettings), this);
	    Utility.SetClickListener(findViewById(R.id.btnExit), this);
	}
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId())
		{
			case R.id.btnStart:
			{
				Intent intent = new Intent(this, StageActivity.class);		
				startActivity(intent);		
				break;
			}
			case R.id.btnRules:
			{
				Intent intent = new Intent(this, RulesActivity.class);		
				startActivity(intent);		
				break;
			}
			case R.id.btnSettings:
			{
				Intent intent = new Intent(this, SettingsActivity.class);		
				startActivity(intent);		
				break;
			}
			case R.id.btnExit:
			{
				finish();
				break;
			}
		}
	}	
	
}
