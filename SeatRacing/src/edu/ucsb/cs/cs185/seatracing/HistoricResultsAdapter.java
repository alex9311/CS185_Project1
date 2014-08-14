package edu.ucsb.cs.cs185.seatracing;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class HistoricResultsAdapter extends CursorAdapter {
	
	public static enum ResultType {
		NOT_STARTED, STARTED, FINISHED
	};
	
	DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm", Locale.US);

	public HistoricResultsAdapter(Context context, Cursor c, int flags) {
		super(context, c, flags);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void bindView(View recycleView, Context context, Cursor cursor) {
		// TODO Auto-generated method stub
		
		TextView nameLabel = (TextView)recycleView.findViewById(R.id.name_label);
		TextView summaryLabel = (TextView)recycleView.findViewById(R.id.summary_label);
		TextView progressLabel = (TextView)recycleView.findViewById(R.id.progress_label);
		
		int round_id = Integer.parseInt(cursor.getString(0));
		long date = Long.parseLong(cursor.getString(1));
		String numRaces = cursor.getString(2);
		int num_races = numRaces == null ? -1 : Integer.parseInt(numRaces);
		int num_results = Integer.parseInt(cursor.getString(3));
		int num_boats = Integer.parseInt(cursor.getString(4));
		int num_rowers = Integer.parseInt(cursor.getString(5));
		
		if(num_boats <= 0){
			nameLabel.setText("Round "+round_id+": empty");
			//TODO: link to lineups, allow adding sets
		}
		else{
			nameLabel.setText("Round "+round_id+": "+num_boats/2+" set"+ (num_boats/2>1 ? "s" : "")+" with "+num_rowers/num_boats+"s");
			
			//TODO: if complete, set to link to results, else go to lineups, allow adding sets
		}
		summaryLabel.setText(dateFormat.format(new Date(date)));
		
		progressLabel.setText(num_results+"/"+(num_races<=0 ? "?" : ""+num_races));
		
		if(num_results!=0 && num_results == num_races){
			progressLabel.setTextColor(context.getResources().getColor(R.color.history_progress_finished));
			//set tag or something here
			recycleView.setTag(R.id.history_result_type, ResultType.FINISHED);
		}
		else if(num_results>0 && num_results < num_races){
			progressLabel.setTextColor(context.getResources().getColor(R.color.history_progress_started));
			recycleView.setTag(R.id.history_result_type, ResultType.STARTED);
		}
		else{
			progressLabel.setTextColor(context.getResources().getColor(R.color.history_progress_notstarted));
			recycleView.setTag(R.id.history_result_type, ResultType.NOT_STARTED);
		}
		
		
		recycleView.setTag(R.id.history_result_round_id,round_id);
	}

	@Override
	public View newView(Context context, Cursor cursor, ViewGroup parent) {
		// TODO Auto-generated method stub
		LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		View v = inflater.inflate(R.layout.fragment_historic_result_listitem, parent, false);
		
		bindView(v, context, cursor);
		
		return v;
	}


}
