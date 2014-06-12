package edu.ucsb.cs.cs185.seatracing;

import java.util.List;

import edu.ucsb.cs.cs185.seatracing.model.RacingSet;
import edu.ucsb.cs.cs185.seatracing.model.Result;
import edu.ucsb.cs.cs185.seatracing.model.Round;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ExtendedResultFragment extends Fragment {
	
	LinearLayout mResultsContainerView;
	Round mRound = new Round(System.currentTimeMillis());
	List<Result> Results;

	
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.extended_result_fragment, container, false);
        
		mResultsContainerView = (LinearLayout)rootView.findViewById(R.id.results_holder_view);

		if(savedInstanceState==null){
			Bundle round_bundle = getActivity().getIntent().getExtras().getBundle("name");
			mRound.loadResultsFromBundle(round_bundle);
			List<Result> results = mRound.getResults();
			int size = results.size();
			Result curr_res = new Result();
			for(int i =0;i<size;i++){
				curr_res = results.get(i);
				View result_row = inflater.inflate(R.layout.extended_result_row, container, false);
				result_row.setId(i);
				mResultsContainerView.addView(result_row);
				TextView race_name = (TextView) mResultsContainerView.findViewById(i).findViewById(R.id.race_label);
				race_name.setText(Integer.toString(curr_res.raceNum()+1));
				TextView rower_name = (TextView) mResultsContainerView.findViewById(i).findViewById(R.id.boat_label);
				rower_name.setText(curr_res.getRower());
				TextView time = (TextView) mResultsContainerView.findViewById(i).findViewById(R.id.time_label);
				int seconds = (int) (curr_res.time()/1000)%60;
				int minutes = (int) (curr_res.time()/60000)%60;
				time.setText(Integer.toString(minutes)+":"+Integer.toString(seconds));
			}
		}	
		

        return rootView;
    }
}
