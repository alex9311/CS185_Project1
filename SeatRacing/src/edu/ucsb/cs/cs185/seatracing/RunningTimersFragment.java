package edu.ucsb.cs.cs185.seatracing;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
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
					View timer = timerView.findViewById(R.id.frag_timer_chrono);
					timer.setOnClickListener(timerClickListener);
					View boatList = timerView.findViewById(R.id.frag_boatlist_label);
					//do shit
					timerView.setId((i*10)+j);
					mTimersViews.add(timerView);
					mTimersContainerView.addView(timerView);
					mTimers.add((MillisecondChronometer)timerView.findViewById(R.id.frag_timer_chrono));
				}
			}
			setTimerEditing(false);

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
			if(numTimersRemaining()==0){
				setTimerEditing(true);
			}
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
	
	public void setTimerEditing(boolean enable){
			for(MillisecondChronometer timer : mTimers){
				timer.setClickable(enable);
			}
	}

	public static RunningTimersFragment newInstance(List<RacingSet> sets){
		Bundle args = new Bundle();

		RacingSet.writeSetsToBundle(args, sets);

		RunningTimersFragment frag = new RunningTimersFragment();
		frag.setArguments(args);

		return frag;
	}

	private OnClickListener timerClickListener = new OnClickListener(){
		@Override
		public void onClick(View v) {
			if(! (v instanceof MillisecondChronometer)){
				throw new IllegalArgumentException("Wrong view for timerClickListener!");
			}
			final MillisecondChronometer timerView = (MillisecondChronometer)v;
			
			long timeBefore = timerView.getTimeElapsed();
			
			/*
			 * From http://www.mkyong.com/android/android-prompt-user-input-dialog-example/
			 */
			LayoutInflater li = LayoutInflater.from(RunningTimersFragment.this.getActivity());
			View adjustView = li.inflate(R.layout.fragment_dialog_adjust_finish_time, null);
			
			final NumberPicker minutesWheel = (NumberPicker)adjustView.findViewById(R.id.minutes_wheel);
			minutesWheel.setMaxValue(59);
			final NumberPicker secondsWheel = (NumberPicker)adjustView.findViewById(R.id.seconds_wheel);
			secondsWheel.setMaxValue(59);
			final NumberPicker millisecondsWheel = (NumberPicker)adjustView.findViewById(R.id.milliseconds_wheel);
			millisecondsWheel.setMaxValue(99);
		
	        DecimalFormat df = new DecimalFormat("00");
	        
	        int remaining = (int)(timeBefore % (3600 * 1000));
	        
	        int minutes = (int)(remaining / (60 * 1000));
	        remaining = (int)(remaining % (60 * 1000));
	        minutesWheel.setValue(minutes);
	        
	        int seconds = (int)(remaining / 1000);
	        remaining = (int)(remaining % (1000));
	        secondsWheel.setValue(seconds);
	        
	        int milliseconds = (int)(((int)timeBefore % 1000) / 10);
	        millisecondsWheel.setValue(milliseconds);

			AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
					RunningTimersFragment.this.getActivity());

			// set prompts.xml to alertdialog builder
			alertDialogBuilder.setView(adjustView);

			// set dialog message
			alertDialogBuilder
			.setCancelable(true)
			.setPositiveButton("Confirm",
					new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog,int id) {
					long newTime = 0;
					//set minutes
					newTime+=minutesWheel.getValue()*1000*60;
					
					//set seconds
					newTime+=secondsWheel.getValue()*1000;
					
					//set "ms"
					newTime+=millisecondsWheel.getValue()*10;
					
					timerView.setTimeElapsed(newTime);
				}
			})
			.setNegativeButton("Cancel",
					new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog,int id) {
					dialog.cancel();
				}
			});

			// create alert dialog
			AlertDialog alertDialog = alertDialogBuilder.create();
			// show it
			alertDialog.show();
		}
	};

}
