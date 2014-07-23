package edu.ucsb.cs.cs185.seatracing;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import edu.ucsb.cs.cs185.seatracing.model.Boat;
import edu.ucsb.cs.cs185.seatracing.model.Rower;

public class BoatRowerNameFragment extends Fragment {

	private BoatLineupChangeListener mCallback;

	int numRowers=-1;
	int boatIndex;
	LinearLayout rowerNameContainer;
	EditText boatNameField;
	Boat mBoat;

	public BoatRowerNameFragment(){
		System.out.println("created new BoatRowerNameFragment");
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		System.out.println("boatRowerNameFrag onCreateView(): tag = "+getTag());

		ViewGroup rootView = (ViewGroup) inflater.inflate(
				R.layout.fragment_boat_rower_name, container, false);

		boatNameField = (EditText)rootView.findViewById(R.id.boat_name_field);
		boatNameField.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				if(s.length()<=0){
					mBoat.setName("Boat "+boatIndex);
				}
				else{
					mBoat.setName(s.toString());
				}
			}	
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
			}
			@Override
			public void afterTextChanged(Editable s) {
				onDataChanged();
			}
		});
		

		rowerNameContainer = (LinearLayout)rootView.findViewById(R.id.rower_name_container);

		if(savedInstanceState==null || numRowers<0){

			//numRowers = getArguments().getInt("numRowers");
			boatIndex = getArguments().getInt("boatIndex");
			
			mBoat = getArguments().getParcelable("boat");
			mBoat.setName("Boat "+boatIndex);
			
			numRowers = mBoat.size();

			((TextView)rootView.findViewById(R.id.boat_name_label)).setText("Boat "+boatIndex);

			rowerNameContainer.removeAllViews();

			for(int i=0; i<numRowers; ++i){
				View rowItem = inflater.inflate(R.layout.fragment_rower_namefield_label, rowerNameContainer, false);
				((TextView)rowItem.findViewById(R.id.rower_namefield)).setHint("Rower "+boatIndex+" - "+(i+1));
				final EditText rowerNamefield = (EditText)rowItem.findViewById(R.id.rower_namefield);
				final int rowerIndex = i;
				rowerNamefield.addTextChangedListener(new TextWatcher() {
					@Override
					public void onTextChanged(CharSequence s, int start, int before, int count) {
						Rower r = mBoat.getRower(rowerIndex);
						if(s.length()<=0){
							r.setName(rowerNamefield.getHint().toString());
						}
						else{
							r.setName(s.toString());
						}
					}
					@Override
					public void beforeTextChanged(CharSequence s, int start, int count,
							int after) {
					}
					@Override
					public void afterTextChanged(Editable s) {
						onDataChanged();
					}
				});
				mBoat.getRower(rowerIndex).setName(rowerNamefield.getHint().toString());

				rowerNamefield.setId(i);
				rowerNameContainer.addView(rowItem);
			}

		}

		onDataChanged();
		return rootView;
	}

	@Override
	public void onAttach(Activity activity){
		try{
			mCallback = (BoatLineupChangeListener)activity;
		}catch (ClassCastException cce){
			throw new ClassCastException(activity.toString()+" must implement BoatLineupChangeListener");
		} finally{
			super.onAttach(activity);
		}
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

	/*
	public String getBoatName(){

		if(boatNameField==null){
			boatNameField = (EditText)getView().findViewById(R.id.boat_name_field);
		}
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

	public static final BoatRowerNameFragment newInstance(int numRowers, int boatIndex){
		BoatRowerNameFragment frag = new BoatRowerNameFragment();
		Bundle bndl = new Bundle(2);
		bndl.putInt("numRowers", numRowers);
		bndl.putInt("boatIndex", boatIndex);
		frag.setArguments(bndl);
		return frag;
	}
	*/
	
	private void onDataChanged(){
		mCallback.boatLineupChanges(boatIndex, mBoat);
	}

	@Override
	public void onDestroyView(){
		System.out.println("destroying BoatRowernameFragment view");
		super.onDestroyView();
	}

}
