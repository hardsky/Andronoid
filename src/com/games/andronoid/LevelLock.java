package com.games.andronoid;

import java.util.ArrayList;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;

public class LevelLock {

	private final String mKey = "locked_levels";
	
	private SharedPreferences mSettings;
	private String[] mLocked;
	
	private boolean mFirstLvlProcessed = false;
	private ArrayList<String> mNewLocks = new ArrayList<String>();
	
	public LevelLock(Context context){
		mSettings = PreferenceManager.getDefaultSharedPreferences(context);
        String sLockedLevels = mSettings.getString(mKey, "");
        if(sLockedLevels.length() > 0)
        	mLocked = sLockedLevels.split(",");
	}
		
	public boolean isLocked(String sLevelName){
		
		//have settings with previously settled locks
		if(mLocked != null && mLocked.length > 0){
			for(String s:mLocked)
				if(s.equalsIgnoreCase(sLevelName))
					return true;
		}
		else{
			//did not set locks before
			mNewLocks.add(sLevelName);//here collect lvl names for update locks in settings
			boolean lock = mFirstLvlProcessed;
			mFirstLvlProcessed = true;
			return lock;			
		}
		
		return false;
	}
	
	public void unlockLevel(String sLevelName){
		String sLocked = "";
		for(String s:mLocked){
			if(s.equalsIgnoreCase(sLevelName))
				continue;
			
			sLocked += s + ",";
		}
		if(sLocked.length()>0){
			sLocked = sLocked.substring(0, sLocked.length() - 1);
			
			Editor ed = mSettings.edit();
			ed.putString(mKey, sLocked);
			ed.commit();			
		}		
	}
	
	public void update() {
		if(mNewLocks.size() > 0)
			lockLevels(mNewLocks);
	}
	
	private void lockLevels(ArrayList<String> arLevels){
		if(arLevels.size() > 0){			
			String sNewLockedLevels = "";
			for(String s: arLevels)
				sNewLockedLevels += s + ",";
			
			String sTrimed = sNewLockedLevels.substring(0, sNewLockedLevels.length() - 1);
			Editor ed = mSettings.edit();
			ed.putString(mKey, sTrimed);
			ed.commit();
		}		
	}	
}
