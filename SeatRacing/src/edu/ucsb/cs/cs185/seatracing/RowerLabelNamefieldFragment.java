package edu.ucsb.cs.cs185.seatracing;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

public class RowerLabelNamefieldFragment extends Fragment {

	int seatIndex;
	int boatIndex;
	EditText rowerNameField;

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		ViewGroup rootView = (ViewGroup) inflater.inflate(
				R.layout.fragment_rower_namefield_label, container, false);

		seatIndex = getArguments().getInt("seatIndex");
		boatIndex = getArguments().getChar("boatIndex");
		
		TextView tv = (TextView)rootView.findViewById(R.id.rower_namefield_label);
		rowerNameField = (EditText)rootView.findViewById(R.id.rower_namefield);

		tv.setText(getResources().getString(R.string.rower_label_default)+" "+(seatIndex+1));
		rowerNameField.setHint("Rower "+boatIndex+" - "+seatIndex);


		return rootView;
	}
	
	public String getRowerName(){
		String entered = rowerNameField.getText().toString();
		if(entered.matches("")){
			return (String)rowerNameField.getHint();
		}
		else{
			return entered;
		}
	}
	
	public static final RowerLabelNamefieldFragment newInstance(int rowerIndex, char boatIndex){
		Bundle bndl = new Bundle(2);
		bndl.putInt("seatIndex", rowerIndex);
		bndl.putChar("boatIndex", boatIndex);
		RowerLabelNamefieldFragment frag = new RowerLabelNamefieldFragment();
		frag.setArguments(bndl);
		
		return frag;
	}
}
