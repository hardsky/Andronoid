package com.games.andronoid;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.view.View;

public class GameView extends View implements IObserver{

	private World mWorld;	
	private GameSettings mSettings;
	private GamePlay mGamePlay;

	public GameView(Context context, GameSettings settings) {
		super(context);
		
		mSettings = settings;	
		mGamePlay = new GamePlay();
	}


	public void startGame(){
		mGamePlay.Reset();
		mGamePlay.RegisterObserver(this);
		mWorld = new World(getContext(), mGamePlay, mSettings);
		
		final int nId = getResources().getIdentifier(mSettings.getBackgroundFile(), AppConsts.DRAWABLE_TYPE, AppConsts.PACKAGE_NAME);
		this.setBackgroundDrawable(getResources().getDrawable(nId));
						
		mWorld.Start();
	}
	
	
	public void stopGame() {
        mWorld.Stop();
	}
	
    @Override
    protected void onDraw(Canvas canvas) {
    	if(mWorld.isRunning()){
	    	mWorld.Draw(canvas);
	        // and make sure to redraw ASAP
	        invalidate();
    	}
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
    	mWorld.setBounds(w, h);
    }

	private void GameOver(){
		stopGame();
		Intent intent = new Intent(getContext(), GameOverActivity.class);
		getContext().startActivity(intent);
	}
	
	private void Win(){
		stopGame();
		Intent intent = new Intent(getContext(), WinActivity.class);
		intent.putExtra(WinActivity.ParamKeys.NextLevel, mSettings.getNextLevel());
		intent.putExtra(WinActivity.ParamKeys.Life, mWorld.getLife());
		intent.putExtra(WinActivity.ParamKeys.Score, mWorld.getScore());
		intent.putExtra(WinActivity.ParamKeys.Time, mWorld.getSeconds());
		getContext().startActivity(intent);
	}

	@Override
	public void update(ISubject subject) {
		GamePlay oGamePlay = (GamePlay)subject;
		if(oGamePlay.isWin())
			Win();
		else if(oGamePlay.isOver())
			GameOver();
	}
}
