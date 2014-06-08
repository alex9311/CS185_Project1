package edu.ucsb.cs.cs185.seatracing;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;
import edu.ucsb.cs.cs185.seatracing.model.Rower;

public class BoatRowerNameFragment extends Fragment {

	int numRowers;
	char boatIndex;
	ListView rowerNameList;
	EditText boatNameField;
	Rower[] rowers;

	public BoatRowerNameFragment(){
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		numRowers = getArguments().getInt("numRowers");
		boatIndex = getArguments().getChar("boatIndex");

		ViewGroup rootView = (ViewGroup) inflater.inflate(
				R.layout.fragment_boat_rower_name, container, false);

		boatNameField = (EditText)rootView.findViewById(R.id.boat_name_field);

		if(savedInstanceState==null){
			rowers = new Rower[numRowers];
			for(int i=0; i<numRowers; ++i){
				rowers[i] = new Rower();
			}
			rowerNameList = (ListView)rootView.findViewById(R.id.rower_name_listview);
			RowerNamefieldListAdapter adapter = new RowerNamefieldListAdapter(getActivity(), R.layout.fragment_rower_namefield_label, rowers);
			adapter.setBoatIndex(boatIndex);
			rowerNameList.setAdapter(adapter);
		}

		return rootView;
	}

	public String getRowerName(int position){
		if(rowers[position]!=null){
			return rowers[position].name();
		}
		else{
			return null;
		}
	}

	public String getBoatName(){
		if(boatNameField!=null){
			String entered = boatNameField.getText().toString();
			if(entered.matches("")){
				return (String)boatNameField.getHint();
			}
			else{
				return entered;
			}
		}
		else return null;
	}

	public static final BoatRowerNameFragment newInstance(int numRowers, char boatIndex){
		BoatRowerNameFragment frag = new BoatRowerNameFragment();
		Bundle bndl = new Bundle(2);
		bndl.putInt("numRowers", numRowers);
		bndl.putChar("boatIndex", boatIndex);
		frag.setArguments(bndl);
		return frag;
	}

}
