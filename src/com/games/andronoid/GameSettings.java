package com.games.andronoid;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;

public final class GameSettings {

	private String mMosaicName;
	private String mBackgroundFile;
	private String mMusicFile;
	private String mNextLevel;
	private int mFriction;
	private int mMass;
	private boolean mMusicOn;
	private String mDifficulty;

	public GameSettings(GameActivity activity){
		
        Intent inte = activity.getIntent();
        Bundle bn = inte.getExtras();
        
        setMosaicName(bn.getString(GameActivity.ParamKeys.Mosaic));
        setBackgroundFile(bn.getString(GameActivity.ParamKeys.Background));
        setMusicFile(bn.getString(GameActivity.ParamKeys.Music));
        setNextLevel(bn.getString(GameActivity.ParamKeys.NextLevel));
        
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(activity);
        
        setFriction(settings.getInt(PreferenceKeys.FRICTION, 30));
        setMass(settings.getInt(PreferenceKeys.MASS, 50));
        setMusicOn(settings.getBoolean(PreferenceKeys.MUSIC, true));
        setDifficulty(settings.getString(PreferenceKeys.DIFFICALTY, DifficaltyType.NORMAL)); 
		
	}

	public String getMosaicName() {
		return mMosaicName;
	}

	public void setMosaicName(String mMosaicName) {
		this.mMosaicName = mMosaicName;
	}

	public String getBackgroundFile() {
		return mBackgroundFile;
	}

	public void setBackgroundFile(String mBackgroundFile) {
		this.mBackgroundFile = mBackgroundFile;
	}

	public String getMusicFile() {
		return mMusicFile;
	}

	public void setMusicFile(String mMusicFile) {
		this.mMusicFile = mMusicFile;
	}

	public String getNextLevel() {
		return mNextLevel;
	}

	public void setNextLevel(String mNextLevel) {
		this.mNextLevel = mNextLevel;
	}

	public int getFriction() {
		return mFriction;
	}

	public void setFriction(int mFriction) {
		this.mFriction = mFriction;
	}

	public int getMass() {
		return mMass;
	}

	public void setMass(int mMass) {
		this.mMass = mMass;
	}

	public boolean isMusicOn() {
		return mMusicOn;
	}

	public void setMusicOn(boolean mMusicOn) {
		this.mMusicOn = mMusicOn;
	}

	public String getDifficulty() {
		return mDifficulty;
	}

	public void setDifficulty(String mDifficulty) {
		this.mDifficulty = mDifficulty;
	}
}
