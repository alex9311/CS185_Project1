package edu.ucsb.cs.cs185.seatracing;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import edu.ucsb.cs.cs185.seatracing.model.RacingSet;
import edu.ucsb.cs.cs185.seatracing.view.MillisecondChronometer;

public class RunningTimersFragment extends Fragment {

	List<RacingSet> mSets;
	LinearLayout mTimersContainerView;
	List<View> mTimersViews;
	List<MillisecondChronometer> mTimers;
	
	public int mNumStopped;

	public RunningTimersFragment(){
		mSets = new ArrayList<RacingSet>();
		mTimersViews = new ArrayList<View>();
		mTimers = new ArrayList<MillisecondChronometer>();
		mNumStopped=0;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment_running_timers, container, false);

		mTimersContainerView = (LinearLayout)rootView.findViewById(R.id.timers_holder_view);

		if(savedInstanceState==null || mSets.size()==0){
			Bundle args = getArguments();
			boolean startImmediately = args.getBoolean("startNow", false);
			mSets = RacingSet.readSetsFromBundle(args);
			
			//inflate two timers v
			for(int i=0; i<mSets.size(); ++i){
				for(int j=0; j<2; ++j){
					View timerView = inflater.inflate(R.layout.fragment_timer_result, container, false);
					timerView.setId((i*10)+j);
					mTimersViews.add(timerView);
					mTimersContainerView.addView(timerView);
					mTimers.add((MillisecondChronometer)timerView.findViewById(R.id.frag_timer_chrono));
				}
			}
			
			if(startImmediately){
				startAllTimers();
			}
		}

		return rootView;
	}
	
	/**
	 * Starts all included timers. NOTE: This also resets values of all timers.
	 */
	public void startAllTimers(){
		for(MillisecondChronometer timer : mTimers){
			timer.start();
		}

	}
	
	public void stopAllTimers(){
		for(MillisecondChronometer timer : mTimers){
			timer.stop();
		}
	}
	
	/**
	 * Stop's one timer, 
	 * @return index of timer that was stopped
	 */
	public int splitOne(){
		if(numTimersRemaining()>0){
			mTimers.get(mNumStopped).stop();
			mNumStopped++;
			return (mNumStopped-1);
		}
		else return -1;
	}
	
	public int numTimersRemaining(){
		return mTimersViews.size()-mNumStopped;
	}
	
	public int numTimersStopped(){
		return mNumStopped;
	}
	
	public long[] getTimes(){
		long[] times = new long[mTimers.size()];
		for(int i=0; i<mTimers.size(); ++i){
			times[i] = mTimers.get(i).getTimeElapsed();
		}
		return times;
	}

	public static RunningTimersFragment newInstance(List<RacingSet> sets){
		Bundle args = new Bundle();
		
		RacingSet.writeSetsToBundle(args, sets);

		RunningTimersFragment frag = new RunningTimersFragment();
		frag.setArguments(args);

		return frag;
	}

}
