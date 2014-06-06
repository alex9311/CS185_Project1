package edu.ucsb.cs.cs185.seatracing;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class LineupsPagerContainerFragment extends Fragment {
	
	private ViewPager mPager;
	private LineupsPagerAdapter mPagerAdapter;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	        Bundle savedInstanceState) {

	        View rootView = inflater.inflate(R.layout.fragment_lineups_pager_container, container, false);          

	        mPager = (ViewPager)rootView.findViewById(R.id.lineups_pager);
	        
	        mPagerAdapter = new LineupsPagerAdapter(getActivity().getSupportFragmentManager());
	        
	        mPager.setAdapter(mPagerAdapter);
	        		
	        return rootView;
	}
	
	public LineupsPagerAdapter getAdapter(){
		return this.mPagerAdapter;
	}
}
