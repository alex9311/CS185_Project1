package edu.ucsb.cs.cs185.seatracing;

import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;
import edu.ucsb.cs.cs185.seatracing.model.Rower;

public class RowerNamefieldListAdapter extends ArrayAdapter<Rower> {

	final Rower[] data;
	Map<View, TextWatcher> listeners;
	Context mContext;
	int rowResourceId;
	char boatIndex='A';
	
	public RowerNamefieldListAdapter(Context context, int resource,
			Rower[] objects) {
		super(context, resource, objects);
		data = objects;
		listeners = new HashMap<View, TextWatcher>();
		rowResourceId = resource;
		mContext = context;
	}
	
	public View getView(int position, View convertView, ViewGroup parent){
		
		if(convertView == null){
			convertView = ((Activity)mContext).getLayoutInflater()
					.inflate(rowResourceId, parent,false);
		}
		
		if(data[position]==null){
			data[position] = new Rower();
		}
		final Rower r = data[position];

		
		TextView tv = (TextView)convertView.findViewById(R.id.rower_namefield_label);
		EditText et = (EditText)convertView.findViewById(R.id.rower_namefield);
		
		if(listeners.containsKey(convertView)){
			//need to shuffle listeners
			et.removeTextChangedListener(listeners.get(convertView));
			TextWatcher tw = new TextWatcher() {
				@Override
				public void onTextChanged(CharSequence s, int start, int before, int count) {
					r.setName(new String((String)s));
				}
				
				@Override
				public void beforeTextChanged(CharSequence s, int start, int count,
						int after) {
					//ununsed
				}
				
				@Override
				public void afterTextChanged(Editable s) {
					//ununsed
				}
			};
			et.addTextChangedListener(tw);
			listeners.put(convertView, tw);
		}
		
		tv.setText(mContext.getResources().getString(R.string.rower_label_default)+" "+position);
		et.setHint("Rower "+boatIndex+" - "+position);

		
		return convertView;
	}
	
	public void setBoatIndex(char in){
		boatIndex=in;
	}

}
