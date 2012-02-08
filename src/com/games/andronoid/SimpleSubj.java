package com.games.andronoid;

import java.util.ArrayList;

public class SimpleSubj implements ISubject {

	ArrayList<IObserver> mObservers = new ArrayList<IObserver>();
	
	@Override
	public void RegisterObserver(IObserver observer) {
		mObservers.add(observer);
	}

	@Override
	public void RemoveObserver(IObserver observer) {
		mObservers.remove(observer);
	}

	@Override
	public void NotifyObservers() {
		for(IObserver obs: mObservers)
			obs.update(this);
	}

	@Override
	public int getState() {
		// TODO Auto-generated method stub
		return -1;
	}
	
	public void ClearObservers(){
		mObservers.clear();
	}

}
