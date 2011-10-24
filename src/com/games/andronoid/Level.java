package com.games.andronoid;

public class Level extends Part {
	
	private String mMosaicName;
	private String mLevelName;
	private int mLives = 3;
	private int mScore = 0;

	public int getLives() {
		return mLives;
	}

	public void setLives(int mLives) {
		this.mLives = mLives;
	}

	public int getScore() {
		return mScore;
	}

	public void setScore(int mScore) {
		this.mScore = mScore;
	}

	public String getLevelName() {
		return mLevelName;
	}

	public void setLevelName(String mLevelName) {
		this.mLevelName = mLevelName;
	}

	public void setMosaicName(String sMosaicName) {
		mMosaicName = sMosaicName;
	};
	
	public String getMosaicName()
	{
		return mMosaicName;
	}
}
