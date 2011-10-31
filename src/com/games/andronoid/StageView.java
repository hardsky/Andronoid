package com.games.andronoid;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;

public class StageView extends View {

	private String mMosaicName;
	private String mBackgroundFile;
	private String mMusicFile;
	private String mNextLevel;
	
	private Context mContext;
	
	private String mText;
	private Paint mTextPaint = new Paint();

	private Drawable mLock;
	private Drawable mBack;

	private boolean mLocked;
	
	private String mLevelName;

    public String getLevelName() {
		return mLevelName;
	}

	public void setLevelName(String mLevelName) {
		this.mLevelName = mLevelName;
	}
	
	public StageView(Context context) {
		super(context);
		mContext = context;
	}
	
	public StageView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		mContext = context;
	}

	public StageView(Context context, AttributeSet attrs) {
		super(context, attrs);
		mContext = context;
	}

	public void setLock(boolean locked){
		mLocked = locked;
	}
	
	public boolean isLocked() {
		return mLocked;
	}
	
	public void setText(String text){
		mText = text;
		
		mTextPaint.setColor(Color.WHITE);
		mTextPaint.setAntiAlias(true);
		mTextPaint.setStyle(Style.FILL);
		mTextPaint.setTextAlign(Align.LEFT);
		mTextPaint.setTextSize(20);				
	}
		
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
				
		int padding = 10;
		
	    Path clipPath = new Path();
	    int w = this.getWidth();
	    int h = this.getHeight();
	    clipPath.addRoundRect(new RectF(0 + padding, 0 + padding, w - padding, h - padding), 10.0f, 10.0f, Path.Direction.CW);
	    canvas.clipPath(clipPath);
	    
	    mBack.setDither(true);
	    mBack.setFilterBitmap(true);
	    mBack.setBounds(0, 0, w, h);
	    mBack.draw(canvas);
	    
	    if(mLocked){
			mLock = mContext.getResources().getDrawable(R.drawable.lock);
			mLock.setBounds(0, 0, 30, 30);	    
		    Rect lockBounds = mLock.getBounds();
		    lockBounds.offsetTo((int)(w /2 - lockBounds.width() / 2), (int)(h * 0.3f + padding + 10));
		    mLock.setBounds(lockBounds);
		    mLock.draw(canvas);
	    }
	    
		int textWidth = (int)mTextPaint.measureText(mText);
        canvas.drawText(mText, w /2 - textWidth / 2, h * 0.3f + padding, mTextPaint);
	}
		
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		setMeasuredDimension(120, 100);	
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

	public void setBackgroundName(String sBackgroundFile){
		mBackgroundFile = sBackgroundFile;
		int nId = mContext.getResources().getIdentifier(mBackgroundFile, AppConsts.DRAWABLE_TYPE, AppConsts.PACKAGE_NAME);
		mBack = mContext.getResources().getDrawable(nId);
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
}
