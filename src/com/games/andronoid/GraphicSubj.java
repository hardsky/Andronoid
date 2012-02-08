package com.games.andronoid;

import android.graphics.drawable.Drawable;

public class GraphicSubj extends Graphic implements ISubject {

	SimpleSubj mISubjectImpl = new SimpleSubj();

	protected GraphicSubj()
	{
		super();
	}
	
	public GraphicSubj(Graphic prev)
	{
		super(prev);
	}
	
	public GraphicSubj(Drawable oDrawble, float metersToPixelsX, float metersToPixelsY, float widthMeters, float heightMeters)	
	{
		super(oDrawble, metersToPixelsX, metersToPixelsY, widthMeters, heightMeters);
	}
	
	@Override
	public void RegisterObserver(IObserver observer) {
		mISubjectImpl.RegisterObserver(observer);
	}

	@Override
	public void RemoveObserver(IObserver observer) {
		mISubjectImpl.RemoveObserver(observer);
	}

	@Override
	public void NotifyObservers() {
		mISubjectImpl.NotifyObservers();
	}

	@Override
	public int getState() {
		throw new UnsupportedOperationException();
	}
	
	public void ClearObservers(){
		mISubjectImpl.ClearObservers();
	}
	
}
