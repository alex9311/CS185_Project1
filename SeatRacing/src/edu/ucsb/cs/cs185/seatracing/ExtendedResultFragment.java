package edu.ucsb.cs.cs185.seatracing;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

public class ExtendedResultFragment extends Fragment {
	
	LinearLayout mResultsContainerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.extended_result_fragment, container, false);
        
		mResultsContainerView = (LinearLayout)rootView.findViewById(R.id.results_holder_view);

		//TODO:iterate through results found in the round 
		///object passed by the intent that called this activity
		View result_row = inflater.inflate(R.layout.extended_result_row, container, false);
		mResultsContainerView.addView(result_row);
		

        return rootView;
    }
}
