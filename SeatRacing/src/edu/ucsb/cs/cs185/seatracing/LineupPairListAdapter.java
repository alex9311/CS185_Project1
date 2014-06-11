package edu.ucsb.cs.cs185.seatracing;

import edu.ucsb.cs.cs185.seatracing.model.RacingPair;
import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class LineupPairListAdapter extends ArrayAdapter<RacingPair> {

	Context mContext;
	RacingPair[] mData;
	int highlightedSeat = -1;
	
	public LineupPairListAdapter(Context context, RacingPair[] data){
		super(context, R.layout.fragment_lineup_pair_list_item);
		
		this.mData = data;
		this.mContext = context;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent){
		if(convertView == null){
			convertView = ((Activity)mContext).getLayoutInflater()
					.inflate(R.layout.fragment_lineup_pair_list_item, parent,false);
		}
		
		((TextView) convertView.findViewById(R.id.left_rower_field)).setText(mData[position].getRower1().name());;
		((TextView) convertView.findViewById(R.id.right_rower_field)).setText(mData[position].getRower2().name());
		
		if(position==highlightedSeat){
			convertView.setBackgroundResource(R.color.switch_seat_background);
		}
		
		return convertView;
	}
	
	@Override
	public int getCount(){
		return mData.length;
	}
	
	/**
	 * Highlight a selected index, to be used for showing switches. 
	 * @param index Index to highlight. Passing -1 will reset highlight;
	 */
	public void setHighlightedSeat(int index){
		highlightedSeat = index;
		notifyDataSetChanged();
	}
	
}
