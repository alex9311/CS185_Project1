package edu.ucsb.cs.cs185.seatracing;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import edu.ucsb.cs.cs185.seatracing.model.Boat;
import edu.ucsb.cs.cs185.seatracing.model.BoatResult;
import edu.ucsb.cs.cs185.seatracing.model.RaceResult;
import edu.ucsb.cs.cs185.seatracing.model.RacingSet;
import edu.ucsb.cs.cs185.seatracing.model.Result;
import edu.ucsb.cs.cs185.seatracing.model.Rower;
import edu.ucsb.cs.cs185.seatracing.model.SplitTimer;
import edu.ucsb.cs.cs185.seatracing.view.BoatPicker;
import edu.ucsb.cs.cs185.seatracing.view.MillisecondChronometer;
import edu.ucsb.cs.cs185.seatracing.view.SplitTimerTextView;

public class RunningTimersFragment extends Fragment {

	List<RacingSet> mSets;
	List<Boat> mBoats;
	LinearLayout mTimersContainerView;
	SplitTimer mTimer;
	BoatResult[] results;
	List<SplitTimerTextView> mTimerViews;
	private ResultsFinalizedListener mCallback;

	public int mNumStopped;

	public RunningTimersFragment(){
		mSets = new ArrayList<RacingSet>();
		mTimerViews = new ArrayList<SplitTimerTextView>(8);
		mTimer = new SplitTimer();
		mBoats = new ArrayList<Boat>();
		mNumStopped=0;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment_running_timers, container, false);

		mTimersContainerView = (LinearLayout)rootView.findViewById(R.id.timers_holder_view);

		if(savedInstanceState==null || mSets.size()==0){
			//TODO: handle case of not having mSets set manually, check in args?
			Bundle args = getArguments();
			boolean startImmediately = args.getBoolean("startNow", false);
			results = new BoatResult[mSets.size()*2];
			for(int i=0; i<results.length; ++i){
				results[i] = new BoatResult();
				results[i].time = -1*i;
			}

			mTimerViews.clear();

			//inflate two timers v
			for(int i=0; i<mSets.size(); ++i){
				mBoats.add(mSets.get(i).getBoat1());
				mBoats.add(mSets.get(i).getBoat2());
				for(int j=0; j<2; ++j){
					View timerAndListView = inflater.inflate(R.layout.fragment_timer_result, container, false);

					SplitTimerTextView timerView = (SplitTimerTextView)timerAndListView.findViewById(R.id.frag_timer_chrono);
					TextView boatList = (TextView)timerAndListView.findViewById(R.id.frag_boatlist_label);

					timerView.setOnClickListener(timerClickListener);
					boatList.setOnClickListener(boatlistClickListener);
					//timerView.setId((i*2)+j);
					mTimersContainerView.addView(timerAndListView);
					mTimerViews.add(timerView);
					mTimer.addTimerUpdateListener(timerView);
					boatList.setId((i*2)+j);
				}
			}
			setTimerEditing(false);

			if(startImmediately){
				//find out when start button was first pressed and start from there
				mTimer.start(args.getLong("start_time", SystemClock.elapsedRealtime()));
				//also set that as the start date for all results
				for(BoatResult r : results){
					r.setDate(System.currentTimeMillis());
				}
				mNumStopped = 0;
			}
		}

		return rootView;
	}

	public void stopAllTimers(){
		mTimer.stop();
	}

	/**
	 * Stop's one timer, 
	 * @return index of timer that was stopped
	 */
	public int splitOne(){
		if(numTimersRemaining()>0){			
			mTimer.removeTimerUpdateListener(mTimerViews.get(mNumStopped));
			mNumStopped++;
			if(numTimersRemaining()==0){
				setTimerEditing(true);
			}
			return (mNumStopped-1);
		}
		else return -1;
	}

	public int numTimersRemaining(){
		return mTimerViews.size()-mNumStopped;
	}

	public int numTimersStopped(){
		return mNumStopped;
	}

	public long[] getTimes(){
		int size = mTimerViews.size();
		long[] times = new long[size];
		for(int i=0; i<size; ++i){
			times[i] = mTimerViews.get(i).getTimeElapsed();
		}
		return times;
	}

	public RaceResult getRaceResult(){
		RaceResult race = new RaceResult();

		for(BoatResult res : results){
			if(res.time<=0){
				res.time = mTimerViews.get((int)(-1*res.time)).getTimeElapsed();
			}
			race.addBoatResult(res);
			res.boat.addResult(res);
			for(Rower r : res.boat.getRowers()){
				r.addResult(res.time);
			}
			res.setRowers(new ArrayList<Rower>(Arrays.asList(res.boat.getRowers())));
		}


		return race;
	}

	public void setTimerEditing(boolean enable){
		for(SplitTimerTextView timer : mTimerViews){
			timer.setClickable(enable);
		}
	}

	public void setRacingSets(List<RacingSet> setsIn){
		mSets = setsIn;
		mBoats.clear();
		for(RacingSet rs : mSets){
			mBoats.add(rs.getBoat1());
			mBoats.add(rs.getBoat2());
		}
	}

	public static RunningTimersFragment newInstance(List<RacingSet> sets){
		Bundle args = new Bundle();

		//RacingSet.writeSetsToBundle(args, sets);
		args.putParcelableArrayList("sets", RacingSet.getArrayList(sets));

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
			final SplitTimerTextView timerView = (SplitTimerTextView)v;

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

					//TODO: link to model?
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

	private OnClickListener boatlistClickListener  = new OnClickListener() {
		@Override
		public void onClick(View v) {
			if(! (v instanceof TextView)){
				throw new IllegalArgumentException("Wrong view for boatlistClickListener!");
			}

			View selectBoatView = BoatPicker.getView(RunningTimersFragment.this.getActivity(), mSets, results, v.getId());

			final TextView boatNameView = (TextView)v;
			final RadioGroup rg = (RadioGroup)selectBoatView.findViewById(R.id.boatpicker_radiogroup);

			AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
					RunningTimersFragment.this.getActivity());

			// set prompts.xml to alertdialog builder
			alertDialogBuilder.setView(selectBoatView);

			// set dialog message
			alertDialogBuilder
			.setCancelable(true)
			.setPositiveButton("Confirm",
					new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog,int id) {
					int radioButtonID = rg.getCheckedRadioButtonId();
					RadioButton radioButton = (RadioButton)rg.findViewById(radioButtonID);
					if(radioButton!=null){
						boatNameView.setText(radioButton.getText());
						int index = boatNameView.getId();
						results[index].time = -1*index;
						results[index].boat = (Boat)radioButton.getTag();
						checkBoatSelections();
					}
					else{
						dialog.cancel();
					}
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

	private void checkBoatSelections(){
		if(mCallback!=null){
			//this will pass true if results are onto, false if not
			mCallback.onResultsChanged(allBoatsOrdered(), results);
		}
	}

	private boolean allBoatsOrdered(){
		//TODO: make this less ugly
		boolean[] used = new boolean[mBoats.size()];
		for(BoatResult res : results){
			int index = mBoats.indexOf(res.boat);
			if(res.boat!=null && index < 0){
				throw new IllegalStateException("Boat from results picker not registered!");
			}
			else if(res.boat==null || used[index]){
				return false;
			}
			used[index] = true;
		}
		return true;
	}

	/**
	 * Sets callback for finalizing ordered results. Call with 'null' to remove
	 * @param listener listener to add
	 */
	public void setOnResultsFinalizedListener(ResultsFinalizedListener listener){
		mCallback = listener;
	}


}

