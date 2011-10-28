package com.games.andronoid;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.Path.Direction;
import android.graphics.Picture;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.MeasureSpec;

public class StageView extends View {

	private String mText;

	private Context mContext;

	private int mWidth;

	private int mHeight;

	private Paint mTextPaint = new Paint();

	private int mAscent;

	private Drawable mLock; 


	public StageView(Context context) {
		super(context);
		mContext = context;
		// TODO Auto-generated constructor stub
		setWillNotDraw(false);
	}
	
    public StageView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}

	public StageView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	public void setText(String text){
		mText = text;
		
		mTextPaint.setColor(Color.WHITE);
		mTextPaint.setAntiAlias(true);
		mTextPaint.setStyle(Style.FILL);
		mTextPaint.setTextAlign(Align.LEFT);
		mTextPaint.setTextSize(20);
		
		String sN = mContext.getResources().getResourceName(R.drawable.lock);
		String sP = mContext.getResources().getResourcePackageName(R.drawable.lock);
		String sT = mContext.getResources().getResourceTypeName(R.drawable.lock);
		String sE = mContext.getResources().getResourceEntryName(R.drawable.lock);

		int nI = mContext.getResources().getIdentifier(sE, sT, sP);
		
		mLock = mContext.getResources().getDrawable(R.drawable.lock);
		mLock.setBounds(0, 0, 30, 30);	
		
		//this.setBackgroundResource(R.drawable.back_7);
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);
		
		/*
		Paint paint = new Paint();
		paint.setColor(Color.GREEN);
		paint.setStyle(Style.STROKE);
		paint.setAlpha(255);
		
		Path path = new Path();
		path.addRoundRect(new RectF(getPaddingLeft() + 10, getPaddingTop() + 10, mWidth - getPaddingRight() - 10, mHeight - getPaddingBottom() - 10), 5, 5, Direction.CW);
		canvas.drawPath(path, paint);
		
		mLock.setAlpha(255);
		Rect lockBounds = mLock.getBounds();
		lockBounds.offsetTo(45, 45);
		mLock.setBounds(lockBounds);
		
		mLock.draw(canvas);
		*/
		
		/*
		Path textPath = new Path();
		textPaint.getTextPath(mText, 0, mText.length()-1, 0, 30, textPath);
		textPath.addRoundRect(new RectF(0, 0, 80, 70), 5, 5, Direction.CW);
		canvas.drawPath(textPath, textPaint);
		*/
		
		int padding = 10;
		
	    Path clipPath = new Path();
	    int w = this.getWidth();
	    int h = this.getHeight();
	    clipPath.addRoundRect(new RectF(0 + padding, 0 + padding, w - padding, h - padding), 10.0f, 10.0f, Path.Direction.CW);
	    canvas.clipPath(clipPath);
	    
	    Drawable bg = getResources().getDrawable(R.drawable.back_7);
	    bg.setDither(true);
	    bg.setFilterBitmap(true);
	    bg.setBounds(0, 0, w, h);
	    bg.draw(canvas);
	    
	    Rect lockBounds = mLock.getBounds();
	    lockBounds.offsetTo((int)(w /2 - lockBounds.width() / 2), (int)(h * 0.3f + padding + 10));
	    mLock.setBounds(lockBounds);
	    mLock.draw(canvas);
	    
		int textWidth = (int)mTextPaint.measureText(mText);
        canvas.drawText(mText, w /2 - textWidth / 2, h * 0.3f + padding, mTextPaint);
	}
	
    @Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		mWidth = w;
		mHeight = h;
		// TODO Auto-generated method stub
		//super.onSizeChanged(w, h, oldw, oldh);
	}

    /**
     * Determines the width of this view
     * @param measureSpec A measureSpec packed into an int
     * @return The width of the view, honoring constraints from measureSpec
     */
    private int measureWidth(int measureSpec) {
        int result = 0;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);

        if (specMode == MeasureSpec.EXACTLY) {
            // We were told how big to be
            result = specSize;
        } else {
            // Measure the text
            result = (int) mTextPaint.measureText(mText) + getPaddingLeft()
                    + getPaddingRight();
            if (specMode == MeasureSpec.AT_MOST) {
                // Respect AT_MOST value if that was what is called for by measureSpec
                result = Math.min(result, specSize);
            }
        }

        return result;
    }

    /**
     * Determines the height of this view
     * @param measureSpec A measureSpec packed into an int
     * @return The height of the view, honoring constraints from measureSpec
     */
    private int measureHeight(int measureSpec) {
        int result = 0;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);

        mAscent = (int) mTextPaint.ascent();
        if (specMode == MeasureSpec.EXACTLY) {
            // We were told how big to be
            result = specSize;
        } else {
            // Measure the text (beware: ascent is a negative number)
            result = (int) (-mAscent + mTextPaint.descent()) + getPaddingTop()
                    + getPaddingBottom() + mLock.getBounds().height() + 10;
            if (specMode == MeasureSpec.AT_MOST) {
                // Respect AT_MOST value if that was what is called for by measureSpec
                result = Math.min(result, specSize);
            }
        }
        return result;
    }
	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//		setMeasuredDimension(measureWidth(widthMeasureSpec), measureHeight(heightMeasureSpec));	
		setMeasuredDimension(120, 100);	
	}
	
}
