package edu.ucsb.cs.cs185.seatracing;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;
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
	}
}
