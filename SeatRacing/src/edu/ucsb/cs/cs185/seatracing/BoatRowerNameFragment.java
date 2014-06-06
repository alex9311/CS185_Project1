package edu.ucsb.cs.cs185.seatracing;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import edu.ucsb.cs.cs185.seatracing.model.Rower;

public class BoatRowerNameFragment extends Fragment {

	int numRowers;
	char boatIndex;
	ListView rowerNameList;
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

		if(savedInstanceState==null){
			rowers = new Rower[numRowers];
			rowerNameList = (ListView)rootView.findViewById(R.id.rower_name_listview);
			rowerNameList.setAdapter(new RowerNamefieldListAdapter(getActivity(), R.layout.fragment_rower_namefield_label, rowers));
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
	
	public static final BoatRowerNameFragment newInstance(int numRowers, char boatIndex){
		BoatRowerNameFragment frag = new BoatRowerNameFragment();
		Bundle bndl = new Bundle(2);
		bndl.putInt("numRowers", numRowers);
		bndl.putChar("boatIndex", boatIndex);
		frag.setArguments(bndl);
		return frag;
	}

}
