package edu.ucsb.cs.cs185.seatracing;

import java.text.DecimalFormat;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import edu.ucsb.cs.cs185.seatracing.db.DatabaseHelper;
import edu.ucsb.cs.cs185.seatracing.model.BoatResult;
import edu.ucsb.cs.cs185.seatracing.model.RaceResult;
import edu.ucsb.cs.cs185.seatracing.model.Result;
import edu.ucsb.cs.cs185.seatracing.model.Round;

public class ExtendedResultFragment extends Fragment {

	LinearLayout mResultsContainerView;
	Round mRound = new Round(System.currentTimeMillis());
	List<Result> Results;


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		ViewGroup rootView = (ViewGroup) inflater.inflate(
				R.layout.fragment_extended_results, container, false);

		mResultsContainerView = (LinearLayout)rootView.findViewById(R.id.ext_results_holder_view);

		if(savedInstanceState==null){	
			//mRound = getActivity().getIntent().getParcelableExtra("round");
			int roundId = getActivity().getIntent().getIntExtra("roundId", -1);
			
			if(roundId < 0){
        		throw new IllegalArgumentException("Invalid/missing roundId argument for results!");
			}
			
			mRound = DatabaseHelper.getInstance(this.getActivity().getApplicationContext()).getResultFilledRound(roundId);

			if(mRound!=null){

				for(int i =0;i<mRound.getResults().size();i++){
					RaceResult thisRaceResult = mRound.getResults().get(i);

					LinearLayout raceGroupView = (LinearLayout)inflater.inflate(R.layout.fragment_result_group, mResultsContainerView,false);

					if(i==0){
						LinearLayout.LayoutParams params = (LinearLayout.LayoutParams)raceGroupView.getLayoutParams();
						params.setMargins(params.leftMargin, params.bottomMargin, params.rightMargin, params.bottomMargin);
						raceGroupView.setLayoutParams(params);
					}

					TextView raceLabel = (TextView) raceGroupView.findViewById(R.id.group_label);
					raceLabel.setText("Race #"+(i+1));


					for(BoatResult thisBoatResult : thisRaceResult.getBoatResults()){

						View resultRowView = inflater.inflate(R.layout.fragment_extended_result_row, raceGroupView, false);

						resultRowView.setId(i);
						raceGroupView.addView(resultRowView);

						TextView boatName = (TextView) resultRowView.findViewById(R.id.boat_label);
						boatName.setText(thisBoatResult.boat.name());

						TextView timeView = (TextView) resultRowView.findViewById(R.id.time_label);
						timeView.setText(formatResultTime(thisBoatResult.time));

					}

					mResultsContainerView.addView(raceGroupView);
				}
			}
		}	


		return rootView;
	}

	private static String formatResultTime(long time){
		DecimalFormat df = new DecimalFormat("00");

		int hours = (int)(time / (3600 * 1000));
		int remaining = (int)(time % (3600 * 1000));

		int minutes = (int)(remaining / (60 * 1000));
		remaining = (int)(remaining % (60 * 1000));

		int seconds = (int)(remaining / 1000);
		remaining = (int)(remaining % (1000));

		int milliseconds = (int)(((int)time % 1000) / 10);

		StringBuilder text = new StringBuilder();

		if (hours > 0) {
			text.append(df.format(hours) + ":");
		}

		text.append(df.format(minutes) + ":");
		text.append(df.format(seconds) + ":");
		text.append(df.format(milliseconds));

		return text.toString();
	}

}
