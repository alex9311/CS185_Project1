package edu.ucsb.cs.cs185.seatracing;

import android.os.Bundle;
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
	ListView boatAList;
	ListView boatBList;

	ArrayAdapter<String> boatAAdapter;
	ArrayAdapter<String> boatBAdapter;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment_lineups, container, false);   
		
		Bundle args = getArguments();

		boatAName = (TextView) rootView.findViewById(R.id.boat_name_left);
		boatBName = (TextView) rootView.findViewById(R.id.boat_name_right);
		
		boatAList = (ListView) rootView.findViewById(R.id.boat_list_left);
		boatBList = (ListView) rootView.findViewById(R.id.boat_list_right);
		
		RacingSet rs = new RacingSet(getArguments());
		
		boatAName.setText(rs.getBoat1().name());
		boatBName.setText(rs.getBoat2().name());

		if(boatAList.getAdapter()==null){
			boatAList.setAdapter(new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1, rs.getBoat1().getRowerNames()));
		}

		if(boatBList.getAdapter()==null){
			boatBList.setAdapter(new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1, rs.getBoat2().getRowerNames()));
		}
		
		
		//load arguments
		return rootView;
	}
	
	public static LineupsFragment newInstance(RacingSet set){
		LineupsFragment frag = new LineupsFragment();
		Bundle bndl = new Bundle();
		set.writeToBundle(bndl);
		frag.setArguments(bndl);
		return frag;
	}
	
}
