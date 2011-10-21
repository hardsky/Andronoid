package com.games.andronoid;

import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.PreferenceActivity;

public class SettingsActivity extends PreferenceActivity implements OnPreferenceChangeListener//, OnSeekBarChangeListener
{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.preferences);
		
		ListPreference difficulty = (ListPreference)this.findPreference("difficulty");
		difficulty.setSummary(difficulty.getEntry());
		difficulty.setOnPreferenceChangeListener(this);
		
		CheckBoxPreference music = (CheckBoxPreference)this.findPreference("music");
		music.setOnPreferenceChangeListener(this);
	}
	
	@Override
	public boolean onPreferenceChange(Preference preference, Object newValue) {
		if(newValue instanceof Boolean)
			return true;
		
		preference.setSummary(newValue.toString());
		return true;
	}
}
