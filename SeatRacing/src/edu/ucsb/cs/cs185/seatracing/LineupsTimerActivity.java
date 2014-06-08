package edu.ucsb.cs.cs185.seatracing;

import java.util.Timer;
import java.util.TimerTask;

import edu.ucsb.cs.cs185.seatracing.animation.DefaultPageTransformer;
import edu.ucsb.cs.cs185.seatracing.animation.ZoomOutPageTransformer;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;

public class LineupsTimerActivity extends FragmentActivity implements AddNewSetListener {

	private enum LineupTimerState{
		LINEUPS,
		RACING,
		RESULT,
		SWITCHING
	}
	
	private Handler mHandler = new Handler();

	private LineupTimerState state;
	private LineupsPagerAdapter mLineupsPagerAdapter;
	private LineupsPagerContainerFragment lineupsFrag;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_lineups_timer);
		if(savedInstanceState==null){
			emplaceLineupsPagerContainerFragment();
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

	private void emplaceLineupsPagerContainerFragment(){
		if(state != LineupTimerState.LINEUPS){
			if(lineupsFrag==null){
				lineupsFrag = new LineupsPagerContainerFragment();
			}
			getSupportFragmentManager().beginTransaction()
				.replace(R.id.lineups_timer_container, lineupsFrag)
				.commit();

			state = LineupTimerState.LINEUPS;
		}
	}

	@Override
	public void addNewRacingSet() {
		Intent newRacingSetIntent = new Intent(this,BoatsetCreateActivity.class);
		startActivityForResult(newRacingSetIntent, BoatsetCreateActivity.NEW_LINEUP);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(resultCode == RESULT_OK){
			if(requestCode == BoatsetCreateActivity.NEW_LINEUP){
				if(state==LineupTimerState.LINEUPS){
					//TODO: pull lineup out of result intent into model object
					if(! data.hasExtra("lineup")){
						throw new IllegalStateException("Got lineups result with no lineup.");
					}
					lineupsFrag.getAdapter().addNewSet(data.getBundleExtra("lineup"));
					//lineupsFrag.getPager().setCurrentItem(lineupsFrag.getAdapter().getCount(), false);
					
					//lineupsFrag.getPager().setCurrentItem(lineupsFrag.getAdapter().getCount(),true);
					mHandler.postDelayed(new Runnable() {
						@Override
						public void run() {
							lineupsFrag.getPager().setCurrentItem(lineupsFrag.getAdapter().getCount()-1, true);
						}
					}, 500);
				}
			}
		}
	}

}
