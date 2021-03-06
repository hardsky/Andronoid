package com.games.andronoid;

public interface ISubject {
	void RegisterObserver(IObserver observer);
	void RemoveObserver(IObserver observer);
	void NotifyObservers();

	int getState();
}
