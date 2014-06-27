package edu.ucsb.cs.cs185.seatracing;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import edu.ucsb.cs.cs185.seatracing.model.Rower;

public class BoatRowerNameFragment extends Fragment {

	int numRowers=-1;
	char boatIndex;
	LinearLayout rowerNameContainer;
	EditText boatNameField;

	public BoatRowerNameFragment(){
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		ViewGroup rootView = (ViewGroup) inflater.inflate(
				R.layout.fragment_boat_rower_name, container, false);

		boatNameField = (EditText)rootView.findViewById(R.id.boat_name_field);

		if(savedInstanceState==null || numRowers<0){
			
			numRowers = getArguments().getInt("numRowers");
			boatIndex = getArguments().getChar("boatIndex");
			
			((TextView)rootView.findViewById(R.id.boat_name_label)).setText("Boat "+boatIndex);

			rowerNameContainer = (LinearLayout)rootView.findViewById(R.id.rower_name_container);
			rowerNameContainer.removeAllViews();
			
			for(int i=0; i<numRowers; ++i){
				View rowItem = inflater.inflate(R.layout.fragment_rower_namefield_label, rowerNameContainer, false);
				((TextView)rowItem.findViewById(R.id.rower_namefield)).setHint("Rower "+boatIndex+" - "+(i+1));
				rowItem.findViewById(R.id.rower_namefield).setId(i);
				rowerNameContainer.addView(rowItem);
			}
			
		}

		return rootView;
	}

	public String getRowerName(int position){
		if(position<0 || position>=numRowers){
			return null;
		}
		else{
			TextView tv = (TextView)(rowerNameContainer.getChildAt(position).findViewById(position));
			if(tv.getText() == null || tv.getText().toString() == null || "".equals(tv.getText().toString())){
				return "Rower "+boatIndex+" - "+(position+1);
			}
			else{
				return tv.getText().toString();
			}
		}
	}

	public String getBoatName(){
		if(boatNameField!=null){
			String entered = boatNameField.getText().toString();
			if(entered.matches("")){
				return "Boat "+boatIndex;
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
