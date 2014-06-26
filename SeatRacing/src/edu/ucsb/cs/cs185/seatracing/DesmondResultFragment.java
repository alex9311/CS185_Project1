package edu.ucsb.cs.cs185.seatracing;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import edu.ucsb.cs.cs185.seatracing.model.RacingPair;
import edu.ucsb.cs.cs185.seatracing.model.RacingSet;
import edu.ucsb.cs.cs185.seatracing.model.Round;
import edu.ucsb.cs.cs185.seatracing.model.Rower;

public class DesmondResultFragment extends Fragment {

	private LinearLayout mResultsContainerView;
	private Round mRound;
	
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
    	
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_desmond_results, container, false);
        
        mResultsContainerView = (LinearLayout)rootView.findViewById(R.id.des_results_holder_view);
        
        if(savedInstanceState==null){
        	mRound = getActivity().getIntent().getParcelableExtra("round");
        	
        	for(int i=0; i<mRound.getRacingSets().size(); ++i){
        		
        		RacingSet rs = mRound.getRacingSets().get(i);
        		
        		//makegroup view for set?
        		LinearLayout setResultGroupView = (LinearLayout)inflater.inflate(R.layout.fragment_result_group, mResultsContainerView, false);
        		
				if(i==0){
					LinearLayout.LayoutParams params = (LinearLayout.LayoutParams)setResultGroupView.getLayoutParams();
					params.setMargins(params.leftMargin, params.bottomMargin, params.rightMargin, params.bottomMargin);
					setResultGroupView.setLayoutParams(params);
				}
				
				TextView groupLabel = (TextView)setResultGroupView.findViewById(R.id.group_label);
				groupLabel.setText(rs.getBoat1().name()+" / "+rs.getBoat2().name());
				
        		
        		for(RacingPair rp : rs.getRacingPairs()){
        			long diff = rp.getTimeDifferential();
        			Rower winner, loser;
        			if(diff>0){
        				winner = rp.getRower1();
        				loser = rp.getRower2();
        			}
        			else{
        				winner = rp.getRower2();
        				loser = rp.getRower1();
        			}
        			diff = Math.abs(diff);
        			
        			View resultRowView = inflater.inflate(R.layout.fragment_desmond_results_row, setResultGroupView, false);
        			
        			TextView winName = (TextView)resultRowView.findViewById(R.id.win_name);
        			winName.setText(winner.name());
        			
        			TextView lostName = (TextView)resultRowView.findViewById(R.id.lose_name);
        			lostName.setText(loser.name());
        			
        			TextView diffTimeView = (TextView)resultRowView.findViewById(R.id.time_diff);
        			int seconds = (int)diff/1000;
        			int centiseconds = (int)(((int)diff % 1000) / 100);
        			diffTimeView.setText(seconds+"."+centiseconds);
        			
        			
        			setResultGroupView.addView(resultRowView);
        		}
        		
        		mResultsContainerView.addView(setResultGroupView);
        	}
        	        	
        }

        return rootView;
    }
}
