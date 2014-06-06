package edu.ucsb.cs.cs185.seatracing;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class FrontPageActivity extends Activity {
	
	Button newRaceButton;
	Button loadRaceButton;
	Button raceHistoryButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_front_page);
		
		newRaceButton = (Button)findViewById(R.id.button_new_race);
		newRaceButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				//go to lineups activity with flag set for new sets
				Intent intent = new Intent(FrontPageActivity.this,LineupsActivity.class);
				intent.putExtra("new", true);
				startActivity(intent);
			}
		});
		
		
		loadRaceButton = (Button)findViewById(R.id.button_load_race);
		loadRaceButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO for now do nothing
			}
		});
		
		raceHistoryButton = (Button)findViewById(R.id.button_race_history);
		raceHistoryButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO for now do nothing
			}
		});
	}
}
