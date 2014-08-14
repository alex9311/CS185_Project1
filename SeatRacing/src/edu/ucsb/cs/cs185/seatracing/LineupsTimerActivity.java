package edu.ucsb.cs.cs185.seatracing;

import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import edu.ucsb.cs.cs185.seatracing.db.DatabaseHelper;
import edu.ucsb.cs.cs185.seatracing.model.Boat;
import edu.ucsb.cs.cs185.seatracing.model.BoatResult;
import edu.ucsb.cs.cs185.seatracing.model.RaceResult;
import edu.ucsb.cs.cs185.seatracing.model.RacingSet;
import edu.ucsb.cs.cs185.seatracing.model.Result;
import edu.ucsb.cs.cs185.seatracing.model.Round;
import edu.ucsb.cs.cs185.seatracing.model.Rower;

public class LineupsTimerActivity extends FragmentActivity implements AddNewSetListener, OnClickListener, ResultsFinalizedListener {

	private enum LineupTimerState{
		INIT,
		LINEUPS, //only creating lineups
		RACING,
		ORDERING,
		RESULT,
		DONE
	}

	private Handler mHandler = new Handler();
	private Button timerButton;
	private TextView stateView;


	private int numPairs=-1;
	private LineupTimerState state;
	private Round mCurrentRound;

	private LineupsPagerContainerFragment lineupsFrag;
	private RunningTimersFragment timersFrag;
	
	private DatabaseHelper db;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_lineups_timer);

		if(db==null){
			db = DatabaseHelper.getInstance(getApplicationContext());
		}
		
		//TODO: check for loading round in progress
		
		if(mCurrentRound==null){
			mCurrentRound = new Round(System.currentTimeMillis());
			int id = db.addRound(mCurrentRound);
			if(id<0){
				throw new RuntimeException("SQLite error inserting Round into DB!");
			}
		}

		timerButton = (Button)findViewById(R.id.button_main_timer);
		timerButton.setOnClickListener(this);

		stateView = (TextView)findViewById(R.id.state_label_view);

		if(savedInstanceState==null){
			emplaceLineupsPagerContainerFragment(true);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.lineups_timer, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}


	@Override
	public void onClick(View v) {
		switch (v.getId()){
		case R.id.button_main_timer:
			switch(state){
			case LINEUPS:
				emplaceRunningTimerContainerFragment();
				setState(LineupTimerState.RACING);
				timerButton.setText(R.string.timer_split_button);
				break;
			case RACING:
				if (timersFrag.numTimersRemaining()>1){
					timersFrag.splitOne();
				}
				else{
					//go to ordering state
					timersFrag.splitOne();
					setState(LineupTimerState.ORDERING);
					timerButton.setText(R.string.timer_done_button);
					timerButton.setEnabled(false);
				}
				break;
			case ORDERING:
				//this should not happen
				throw new IllegalStateException("Got finish click in ordering state! Not ready to finish.");
			case RESULT:
				writeResults(mCurrentRound);
				System.out.println("Before switch: "+mCurrentRound.getRacingSets().get(0));
				if(mCurrentRound.hasSwitch()){
					showSwitchDialog(mCurrentRound);
					performSwitches(mCurrentRound);
				}

				System.out.println("After switch: "+mCurrentRound.getRacingSets().get(0));
				emplaceLineupsPagerContainerFragment(false);

				System.out.println("Finished race "+(mCurrentRound.getCurrentRace()+1)+" of "+mCurrentRound.getNumRaces());
				if(mCurrentRound.getCurrentRace()+1 == mCurrentRound.getNumRaces()){
					setState(LineupTimerState.DONE);
					timerButton.setText(R.string.timer_finish_button);
				}
				else{
					mCurrentRound.setCurrentRace(mCurrentRound.getCurrentRace()+1);
					setState(LineupTimerState.LINEUPS);
					timerButton.setText(R.string.timer_start_button);
				}
				break;
			case DONE:
				switchToResultsActivity(mCurrentRound);
				break;
			default:
				break;
			}
			break;
		default:
			break;
		}
	}

	private void writeResults(Round round) {
		RaceResult raceresult = timersFrag.getRaceResult();
		raceresult.setRaceNum(mCurrentRound.getCurrentRace());
		round.addResult(raceresult);
		
		//TODO: put results in DB
		List<Result> results = new ArrayList<Result>();
		for(BoatResult br : raceresult.getBoatResults()){
			results.addAll(br.getResults());
		}
		
		for(Result r : results){
			r.setRound(mCurrentRound.getID());
			r.setRaceNum(mCurrentRound.getCurrentRace());
		}
		
		db.batchAddResults(results);
	}


	private void setState(LineupTimerState newState){
		state = newState;
		switch (newState) {
		case INIT:
			stateView.setText(R.string.state_label_init);
			break;
		case LINEUPS:
			if(mCurrentRound.getCurrentRace()>0){
				stateView.setText(R.string.state_label_switching);
			}
			else{
				stateView.setText(R.string.state_label_lineup);
			}
			break;
		case RACING:
			stateView.setText(getResources().getString(R.string.state_label_racing)+(mCurrentRound.getCurrentRace()+1));
			break;
		case ORDERING:
			stateView.setText(getResources().getString(R.string.state_label_ordering));
			break;
		case RESULT:
			stateView.setText(getResources().getString(R.string.state_label_result)+(mCurrentRound.getCurrentRace()+1));
			break;
		case DONE:
			stateView.setText(R.string.state_label_done);
			break;
		}
	}

	private void emplaceRunningTimerContainerFragment(){
		long startTime = SystemClock.elapsedRealtime();
		if(state != LineupTimerState.RACING){
			if(timersFrag==null){
				timersFrag = new RunningTimersFragment();
				timersFrag.setRacingSets(mCurrentRound.getRacingSets());
			}

			Bundle args = timersFrag.getArguments();
			if(args==null){
				args = new Bundle();
			}

			//RacingSet.writeSetsToBundle(args, mCurrentRound.getRacingSets());
			args.putParcelableArrayList("sets", RacingSet.getArrayList(mCurrentRound.getRacingSets()));
			args.putLong("start_time", startTime);
			args.putBoolean("startNow", true);

			timersFrag.setArguments(args);
			timersFrag.setOnResultsFinalizedListener(this);

			getSupportFragmentManager().beginTransaction()
			.replace(R.id.lineups_timer_container,timersFrag)
			.commit();
		}
	}

	private void emplaceLineupsPagerContainerFragment(boolean editable){
		if(state != LineupTimerState.LINEUPS){
			int highlightedSeat = -1;
			if(lineupsFrag!=null && lineupsFrag.getArguments()!=null && mCurrentRound.hasSwitch()){
				highlightedSeat = lineupsFrag.getArguments().getInt("highlightedSeat",-1);
			}
			lineupsFrag = new LineupsPagerContainerFragment();
			if(mCurrentRound.getRacingSets()!=null){
				System.out.println("Sent to lineupsFragment: "+mCurrentRound.getRacingSets().get(0));
				Bundle bndl = new Bundle();
				bndl.putParcelableArrayList("sets", RacingSet.getArrayList(mCurrentRound.getRacingSets()));
				bndl.putBoolean("editable", editable);
				bndl.putInt("highlightedSeat", highlightedSeat);
				lineupsFrag.setArguments(bndl);
			}
			getSupportFragmentManager().beginTransaction()
			.replace(R.id.lineups_timer_container, lineupsFrag)
			.commit();

			setState(LineupTimerState.LINEUPS);
		}
	}

	private void switchToResultsActivity(Round round){
		Intent intent = new Intent(this, ResultsActivity.class);
		intent.putExtra("roundId", mCurrentRound.getID());
		//intent.putExtra("round", mCurrentRound);
		startActivity(intent);
	}


	/**
	 * Taken from http://stackoverflow.com/questions/5810084/android-alertdialog-single-button
	 * @param round
	 */
	private void showSwitchDialog(Round round){

		int switchToMake = Round.getSwitchIndex(round.getCurrentRace(), round.switchingLast());
		StringBuilder alertMessage = new StringBuilder();

		alertMessage.append("Switch at "+(switchToMake+1)+":\n");
		for(RacingSet rs : mCurrentRound.getRacingSets()){
			Boat b1 = rs.getBoat1();
			Boat b2 = rs.getBoat2();
			Rower r1 = b1.getRower(switchToMake);
			Rower r2 = b2.getRower(switchToMake);
			alertMessage.append("\n\t"+r1.name()+" and "+r2.name()+"\n");
			alertMessage.append("\t  ("+b1.name()+" and "+b2.name()+")\n");
		}

		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage(alertMessage.toString())
		.setCancelable(false)
		.setPositiveButton("OK", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				//do NOTHING BWAHAHAHAHA
			}
		});
		AlertDialog alert = builder.create();
		alert.show();
	}

	private void performSwitches(Round round){
		int switchToMake = Round.getSwitchIndex(round.getCurrentRace(), round.switchingLast());

		System.out.println("Switching rowers at "+switchToMake);


		for(RacingSet rs : round.getRacingSets()){
			Boat.switchRowers(rs.getBoat1(), rs.getBoat2(), switchToMake);
		}
		Bundle args = lineupsFrag.getArguments();
		if(args==null){
			args = new Bundle();
		}
		args.putInt("highlightedSeat", switchToMake);

		lineupsFrag.setArguments(args);
		if(lineupsFrag.getView()!=null){
			lineupsFrag.getView().invalidate();
		}
	}

	@Override
	public void addNewRacingSet() {
		Intent newRacingSetIntent = new Intent(this,BoatsetCreateActivity.class);
		if(numPairs>0){
			newRacingSetIntent.putExtra("numPairs", numPairs);
		}
		startActivityForResult(newRacingSetIntent, BoatsetCreateActivity.NEW_LINEUP);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(resultCode == RESULT_OK){
			if(requestCode == BoatsetCreateActivity.NEW_LINEUP){
				if(state==LineupTimerState.LINEUPS){
					if(data==null || (! data.hasExtra("racingset"))){
						throw new IllegalStateException("Got lineups result with no lineup.");
					}
					RacingSet rs = data.getParcelableExtra("racingset");

					lineupsFrag.getAdapter().addNewSet(rs);
					lineupsFrag.getPager().setCurrentItem(lineupsFrag.getAdapter().getCount()-1, false);

					if(data.hasExtra("switchLast")){
						mCurrentRound.setSwitchingLast(data.getBooleanExtra("switchLast", false));
					}
					mCurrentRound.setRacingSets(lineupsFrag.getAdapter().getRacingSets());
					
					if(numPairs<0){
						numPairs=rs.getBoat1().size();
						db.setRoundSize(mCurrentRound);
					}
					
					//add boats to db
					db.addBoat(rs.getBoat1());
					db.addBoat(rs.getBoat2());
					
					//add rowers to db
					db.batchAddRowers(rs.getBoat1().getRowers());
					db.batchAddRowers(rs.getBoat2().getRowers());
										
					//add lineups
					db.addLineups(mCurrentRound.getID(), mCurrentRound.getRacingSets().size()-1, rs);

					//lineupsFrag.getPager().setCurrentItem(lineupsFrag.getAdapter().getCount(),true);
					mHandler.postDelayed(new Runnable() {
						@Override
						public void run() {
							lineupsFrag.getPager().setCurrentItem(lineupsFrag.getAdapter().getCount()-2, true);
						}
					}, 500);
				}
			}
		}
	}

	@Override
	public void onResultsChanged(boolean complete, BoatResult[] boatresults) {
		if(state != LineupTimerState.ORDERING && state!=LineupTimerState.RESULT){
			throw new IllegalStateException("Got results ordering when not expected");
		}
		
		if(complete){
			timerButton.setEnabled(true);
			setState(LineupTimerState.RESULT);
		}
		else{
			timerButton.setEnabled(false);
			setState(LineupTimerState.ORDERING);
		}
	}


}
