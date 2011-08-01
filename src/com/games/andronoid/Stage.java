package com.games.andronoid;
public class Stage extends Part {
    private int mId;
	private String mBGFileName;
	private String mMusicFileName;

	public void setId(int nId) {
		mId = nId;
	}

	public void setBackgroundImgFile(String sImgFile) {
		mBGFileName = sImgFile;
	}

	public String getBackgroundImgFile() {
		return mBGFileName;
	}

	public void setMusic(String sMusicFile) {
		mMusicFileName = sMusicFile;
	}
}
