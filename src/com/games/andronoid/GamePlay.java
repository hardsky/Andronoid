package com.games.andronoid;

public class GamePlay extends SimpleSubj implements IObserver {
	
	public enum Event{
		win,
		over,
		none
	}

	Event mEvent = Event.none;
	

	public boolean isWin(){
		return mEvent == Event.win;
	}
	
	public boolean isOver(){
		return mEvent == Event.over;
	}
	
	@Override
	public void update(ISubject subject) {
		if(subject instanceof Mosaic){
			Mosaic oMosaic = (Mosaic)subject;
			if(oMosaic.isEmpty()){
				mEvent = Event.win;
				NotifyObservers();
			}
		}
		else if(subject instanceof Life){
			Life oLife = (Life)subject;
			if(oLife.isEmpty()){
				mEvent = Event.over;
				NotifyObservers();
			}
		}
		
	}
	
	public void Reset(){
		mEvent = Event.none;
		ClearObservers();
	}
}
