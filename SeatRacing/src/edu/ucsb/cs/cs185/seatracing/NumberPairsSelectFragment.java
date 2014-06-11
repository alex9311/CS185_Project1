package edu.ucsb.cs.cs185.seatracing;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;

public class NumberPairsSelectFragment extends Fragment implements OnClickListener {

	private NumberPairsSelectListener mCallback;
	private Button oneRowerButton;
	private Button twoRowersButton;
	private Button threeRowersButton;
	private Button fourRowersButton;
	private CheckBox switchLastBox;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		ViewGroup rootView = (ViewGroup) inflater.inflate(
				R.layout.fragment_select_number_pairs, container, false);

		oneRowerButton = (Button)rootView.findViewById(R.id.button_one_rower);
		oneRowerButton.setOnClickListener(this);
		twoRowersButton = (Button)rootView.findViewById(R.id.button_two_rowers);
		twoRowersButton.setOnClickListener(this);
		threeRowersButton = (Button)rootView.findViewById(R.id.button_three_rowers);
		threeRowersButton.setOnClickListener(this);
		fourRowersButton = (Button)rootView.findViewById(R.id.button_four_rowers);
		fourRowersButton.setOnClickListener(this);
		switchLastBox = (CheckBox)rootView.findViewById(R.id.checkbox_switch_last_pair);
		
		return rootView;
	}

	@Override 
	public void onAttach(Activity activity){
		try{
			mCallback = (NumberPairsSelectListener)activity;
		}catch (ClassCastException cce){
			throw new ClassCastException(activity.toString()+" must implement NumberPairsSelectListener");
		} finally{
			super.onAttach(activity);
		}
	}

	@Override
	public void onClick(View v) {
		if(v!=null && mCallback!=null){
			boolean switchLast = switchLastBox.isChecked();
			if(v == oneRowerButton){
				mCallback.numberPairsSelected(1, switchLast);
			}
			else if(v == twoRowersButton){
				mCallback.numberPairsSelected(2, switchLast);
			}
			else if(v == threeRowersButton){
				mCallback.numberPairsSelected(3, switchLast);
			}
			else if(v == fourRowersButton){
				mCallback.numberPairsSelected(4, switchLast);
			}
		}
	}
	
}
