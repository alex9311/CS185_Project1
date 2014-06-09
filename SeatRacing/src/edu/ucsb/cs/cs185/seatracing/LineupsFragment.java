package edu.ucsb.cs.cs185.seatracing;

import android.os.Bundle;
import android.os.Parcel;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import edu.ucsb.cs.cs185.seatracing.model.RacingSet;

public class LineupsFragment extends Fragment {

	TextView boatAName;
	TextView boatBName;
	ListView lineupPairList;

	ArrayAdapter<String> boatAAdapter;
	ArrayAdapter<String> boatBAdapter;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment_lineups, container, false);   
		
		boatAName = (TextView) rootView.findViewById(R.id.boat_name_left);
		boatBName = (TextView) rootView.findViewById(R.id.boat_name_right);
		
		
		RacingSet rs = new RacingSet(getArguments());
		
		boatAName.setText(rs.getBoat1().name());
		boatBName.setText(rs.getBoat2().name());
		
		lineupPairList = (ListView) rootView.findViewById(R.id.lineup_pairs_list);

		if(lineupPairList.getAdapter()==null){
			lineupPairList.setAdapter(new LineupPairListAdapter(getActivity(), rs.getRacingPairs()));
		}
		
		//load arguments
		return rootView;
	}
	
	public static LineupsFragment newInstance(RacingSet set){
		LineupsFragment frag = new LineupsFragment();
		Bundle bndl = new Bundle();
		bndl.putParcelable(null, set);
		frag.setArguments(bndl);
		return frag;
	}
	
}
