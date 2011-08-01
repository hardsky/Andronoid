package com.games.andronoid;

import android.graphics.*;
import android.graphics.drawable.BitmapDrawable;
import android.util.DisplayMetrics;

public class Ball extends Graphic {

	private float mVX;
	private float mVY;
	private float mLastPosX;
	private float mLastPosY;
	private long mLastT;
	private float mLastDeltaT;
	private float mRadius = 0.002f;

	public Ball(BitmapDrawable oBall, DisplayMetrics metrics) {
		super(oBall, metrics, 0.002f, 0.002f);
		mVX = 0.02f;
		mVY = 0.02f;
	}

	public Ball(Ball lostBall) {
		super(lostBall);
		mVX = 0.02f;
		mVY = 0.02f;
	}

	/*
	 * private void Init() { mVX = 0.02f; mVY = 0.02f; mLastPosX = 0; mLastPosY
	 * = 0; //mLastT = 0; mLastDeltaT = 0; mPosX = 0; mPosY = 0; }
	 * 
	 * @Override public void setOrigin(float left, float top) {
	 * super.setOrigin(left, top); Init(); }
	 */
	public void Impact(ImpactType enType, Rect oIntersect) {
		switch (enType) {
		case left:
			mVX *= -1;
			mPosX += (oIntersect.width() + 1) / mMetersToPixelsX;
			break;
		case right:
			mVX *= -1;
			mPosX -= (oIntersect.width() + 1) / mMetersToPixelsX;
			break;
		case top:
			mVY *= -1;
			mPosY -= (oIntersect.height() + 1) / mMetersToPixelsY;
			break;
		case bottom:
			mVY *= -1;
			mPosY += (oIntersect.height() + 1) / mMetersToPixelsY;
			break;
		}
		SetBounds();
	}

	public void setCurrentTime(long nCurrentTimeStamp) {
		final long now = nCurrentTimeStamp;
		final float dT = (float) (now - mLastT) * (1.0f / 1000000000.0f);
		mLastT = now;
		if (mLastDeltaT != 0) {
			final float dTC = dT / mLastDeltaT;
			final float x = mPosX /* + dTC * (mPosX - mLastPosX) */+ mVX * dT;
			final float y = mPosY /* + dTC * (mPosY - mLastPosY) */+ mVY * dT;

			mLastPosX = mPosX;
			mPosX = x;

			mLastPosY = mPosY;
			mPosY = y;
		}

		mLastDeltaT = dT;

		SetBounds();
	}

	public boolean Intersect(Rect rt) {
		int ballY = mBounds.centerY();
		int ballX = mBounds.centerX();

		int ballLeft = mBounds.left;
		int ballRight = mBounds.right;
		int ballTop = mBounds.top;
		int ballBottom = mBounds.bottom;

		if (rt.contains(ballLeft, ballY))
			Impact(ImpactType.left, rt);
		else if (rt.contains(ballRight, ballY))
			Impact(ImpactType.right, rt);
		else if (rt.contains(ballX, ballTop))
			Impact(ImpactType.top, rt);
		else if (rt.contains(ballX, ballBottom))
			Impact(ImpactType.bottom, rt);
		else// determine reflection from corner
		{
			float ballCenter = PointF.length(ballX, ballY);

			float leftTop = PointF.length(rt.left, rt.top);
			float leftBottom = PointF.length(rt.left, rt.bottom);
			float rigthBottom = PointF.length(rt.right, rt.bottom);
			float rightTop = PointF.length(rt.right, rt.top);

			float nRadius = mBounds.width();

			boolean isLeftTop = Math.abs(ballCenter - leftTop) <= nRadius;
			boolean isLeftBottom = Math.abs(ballCenter - leftBottom) <= nRadius;
			boolean isRigthBottom = Math.abs(ballCenter - rigthBottom) <= nRadius;
			boolean isRigthTop = Math.abs(ballCenter - rightTop) <= nRadius;

			float cos = 0;
			float sin = 0;

			float katetX = 0;
			float katetY = 0;

			float gipotenusa = 1;

			if (isLeftTop) {
				katetX = ballX - rt.left;
				katetY = rt.top - ballY;
			} else if (isLeftBottom) {
				katetX = ballX - rt.left;
				katetY = rt.bottom - ballY;
			} else if (isRigthBottom) {
				katetX = ballX - rt.right;
				katetY = rt.bottom - ballY;
			} else if (isRigthTop) {
				katetX = ballX - rt.right;
				katetY = rt.top - ballY;
			} else
				// there is no impact
				return false;

			gipotenusa = (float) Math.sqrt(Math.pow(katetX, 2)
					+ Math.pow(katetY, 2));

			cos = katetX / gipotenusa;
			sin = katetY / gipotenusa;

			float VX = (mVX * cos - mVY * sin) * -1;
			float VY = mVX * sin + mVY * cos;

			mVX = VY * sin + VX * cos;
			mVY = VY * cos - VX * sin;
		}

		return true;
	}

}
