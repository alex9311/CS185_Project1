package edu.ucsb.cs.cs185.seatracing;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class LineupPairListItemFragment extends Fragment {

	private TextView leftRowerLabel;
	private TextView rightRowerLabel;
	
	public LineupPairListItemFragment() {
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		ViewGroup rootView = (ViewGroup) inflater.inflate(
				R.layout.fragment_lineup_pair_list_item, container, false);

		leftRowerLabel = (TextView) rootView.findViewById(R.id.left_rower_field);
		rightRowerLabel = (TextView) rootView.findViewById(R.id.right_rower_field);
		
		Bundle args = getArguments();
		
		leftRowerLabel.setText(args.getString("rower1Name"));
		rightRowerLabel.setText(args.getString("rower2Name"));
		
		return rootView;
	}
	
	
	public static LineupPairListItemFragment newInstance(String rower1, String rower2){
		LineupPairListItemFragment frag = new LineupPairListItemFragment();
		Bundle bndl = new Bundle();
		bndl.putString("rower1Name", rower1);
		bndl.putString("rower2Name", rower2);
		frag.setArguments(bndl);
		return frag;
	}
	
}
