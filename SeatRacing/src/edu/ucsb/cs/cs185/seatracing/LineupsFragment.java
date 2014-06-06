package edu.ucsb.cs.cs185.seatracing;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class LineupsFragment extends Fragment {

	ListView boatAList;
	ListView boatBList;

	ArrayAdapter<String> boatAAdapter;
	ArrayAdapter<String> boatBAdapter;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment_lineups, container, false);          

		boatAList = (ListView) rootView.findViewById(R.id.boat_list_left);
		boatBList = (ListView) rootView.findViewById(R.id.boat_list_right);

		if(boatAList.getAdapter()==null){
			boatAList.setAdapter(new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.mock_lineup_1)));
		}
		//boatAAdapter = (ArrayAdapter<String>)boatAList.getAdapter();

		if(boatBList.getAdapter()==null){
			boatBList.setAdapter(new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.mock_lineup_2)));
		}
		//boatBAdapter = (ArrayAdapter<String>)boatBList.getAdapter();

		return rootView;
	}
}
