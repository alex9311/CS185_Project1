package edu.ucsb.cs.cs185.seatracing;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import edu.ucsb.cs.cs185.seatracing.HistoricResultsAdapter.ResultType;
import edu.ucsb.cs.cs185.seatracing.db.DatabaseHelper;

public class HistoricResultsActivity extends Activity {
	
	DatabaseHelper db;
	ListView resultsList;
	HistoricResultsAdapter adapter;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_historic_results);
		
		if(db==null){ 
			db = DatabaseHelper.getInstance(getApplicationContext());
		}
		if(adapter==null){
			adapter = new HistoricResultsAdapter(getApplicationContext(), db.getHistoricResultsCursor(), 0);
		}
		
		resultsList = (ListView)findViewById(R.id.results_history_list);
		resultsList.setAdapter(adapter);
		
		//add listener
		
		resultsList.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				int round_id = (Integer)view.getTag(R.id.history_result_round_id);
				ResultType type = (ResultType)view.getTag(R.id.history_result_type);
				
				switch(type){
				case NOT_STARTED:
					//TODO: go to lineups stage
					break;
				case STARTED:
					//TODO: go to switch stage
					break;
				case FINISHED:
					Intent intent = new Intent(HistoricResultsActivity.this, ResultsActivity.class);
					intent.putExtra("roundId", round_id);
					startActivity(intent);
					break;
				}
			}
		});
	}
}
