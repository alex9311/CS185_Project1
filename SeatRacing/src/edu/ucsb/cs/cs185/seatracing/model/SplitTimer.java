package edu.ucsb.cs.cs185.seatracing.model;

import java.util.ArrayList;
import java.util.List;

import android.os.Handler;
import android.os.SystemClock;

public class SplitTimer {
	
	private static Handler mHandler = new Handler();
	private long mMillisecondUpdateInterval = 10;
	private List<SplitTimerUpdateListener> mListeners;
	private long mBase;
	private long mFinal;
	
	public SplitTimer(){
		mListeners = new ArrayList<SplitTimerUpdateListener>();
	}
	
	public void setUpdateInterval(long ms){
		mMillisecondUpdateInterval = ms;
	}

	public void addTimerUpdateListener(SplitTimerUpdateListener listener){
		mListeners.add(listener);
	}
	
	public void removeTimerUpdateListener(SplitTimerUpdateListener listener){
		mListeners.remove(listener);
	}
	
	public void start(){
		mBase = SystemClock.elapsedRealtime();
		mHandler.postDelayed(new Runnable() {
			@Override
			public void run() {
				callbackListeners(SystemClock.elapsedRealtime()-mBase);
				mHandler.postDelayed(this, mMillisecondUpdateInterval);
			}

		}, mMillisecondUpdateInterval);
	}
	
	public void stop(){
		mHandler.removeCallbacksAndMessages(null);
		mFinal = SystemClock.elapsedRealtime();
		callbackListeners(mFinal-mBase);
	}
	
	private void callbackListeners(long timeElapsed){
		for(SplitTimerUpdateListener listener : mListeners){
			listener.updateTimer(timeElapsed);
		}
	}

}
