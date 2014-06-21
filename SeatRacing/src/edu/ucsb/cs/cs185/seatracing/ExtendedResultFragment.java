package edu.ucsb.cs.cs185.seatracing;

import java.text.DecimalFormat;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
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
				R.layout.extended_result_fragment, container, false);

		mResultsContainerView = (LinearLayout)rootView.findViewById(R.id.results_holder_view);

		if(savedInstanceState==null){	
			mRound = getActivity().getIntent().getParcelableExtra("round");

			for(int i =0;i<mRound.getResults().size();i++){
				RaceResult thisRaceResult = mRound.getResults().get(i);

				for(BoatResult thisBoatResult : thisRaceResult.getBoatResults()){
					View resultRowView = inflater.inflate(R.layout.extended_result_row, container, false);
					resultRowView.setId(i);
					mResultsContainerView.addView(resultRowView);
					
					TextView race_name = (TextView) resultRowView.findViewById(R.id.race_label);
					race_name.setText(Integer.toString(thisRaceResult.getRaceNum()+1));

					TextView rower_name = (TextView) resultRowView.findViewById(R.id.boat_label);
					rower_name.setText(thisBoatResult.boat.name());

					TextView timeView = (TextView) resultRowView.findViewById(R.id.time_label);

					timeView.setText(formatResultTime(thisBoatResult.time));

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
