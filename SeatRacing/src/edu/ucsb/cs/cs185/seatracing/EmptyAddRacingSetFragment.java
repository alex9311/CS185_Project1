package edu.ucsb.cs.cs185.seatracing;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

public class EmptyAddRacingSetFragment extends Fragment {

	private Button addSetButton;
	private AddNewSetListener mCallback;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	        Bundle savedInstanceState) {

	        View rootView = inflater.inflate(R.layout.fragment_empty_add_racingset, container, false);          

	        addSetButton = (Button)rootView.findViewById(R.id.add_racingset_button);
	        addSetButton.setOnClickListener(new OnClickListener() {		
				@Override
				public void onClick(View v) {
					mCallback.addNewRacingSet();
				}
			});
	        
	        return rootView;
	}
	
	@Override 
	public void onAttach(Activity activity){
		try{
			mCallback = (AddNewSetListener)activity;
		}catch (ClassCastException cce){
			throw new ClassCastException(activity.toString()+" must implement AddNewSetListener");
		} finally{
			super.onAttach(activity);
		}
	}

}
