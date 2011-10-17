package com.games.andronoid;

public class Level extends Part {
	
	private String mMosaicName;
	private String mLevelName;

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
