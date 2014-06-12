package edu.ucsb.cs.cs185.seatracing;

import java.util.List;

import edu.ucsb.cs.cs185.seatracing.model.RacingSet;
import edu.ucsb.cs.cs185.seatracing.model.Result;
import edu.ucsb.cs.cs185.seatracing.model.Round;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

public class ExtendedResultFragment extends Fragment {
	
	LinearLayout mResultsContainerView;
	Round mRound;
	List<Result> Results;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.extended_result_fragment, container, false);
        
		mResultsContainerView = (LinearLayout)rootView.findViewById(R.id.results_holder_view);

		if(savedInstanceState==null){
			Bundle round_bundle = getActivity().getIntent().getExtras();
			mRound.loadFromBundle(round_bundle);
			//TODO: iterate through round to get results and add them to extended_result_rows
			View result_row = inflater.inflate(R.layout.extended_result_row, container, false);
			mResultsContainerView.addView(result_row);
		}	

        return rootView;
    }
}
