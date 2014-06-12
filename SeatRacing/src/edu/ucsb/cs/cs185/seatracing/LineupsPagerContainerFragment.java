package edu.ucsb.cs.cs185.seatracing;

import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import edu.ucsb.cs.cs185.seatracing.model.RacingSet;

public class LineupsPagerContainerFragment extends Fragment {

	private ViewPager mPager;
	private LineupsPagerAdapter mPagerAdapter;
	private int highlightedSeat = -1;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment_lineups_pager_container, container, false);          

		mPager = (ViewPager)rootView.findViewById(R.id.lineups_pager);

		if(mPagerAdapter==null){
			mPagerAdapter = new LineupsPagerAdapter(getActivity().getSupportFragmentManager());
		}

		if(getArguments() != null && getArguments().getInt("numSets", 0)>0){
			mPagerAdapter.setRacingSets(RacingSet.readSetsFromBundle(getArguments()));
		}

		if(getArguments()!=null){
			highlightedSeat = getArguments().getInt("highlightedSeat", -1);
			mPagerAdapter.setHighlightedSeat(highlightedSeat);
			mPagerAdapter.setEditable(getArguments().getBoolean("editable",true));
		}

		mPager.setAdapter(mPagerAdapter);

		return rootView;
	}

	public void setAdapter(LineupsPagerAdapter adapter){
		mPagerAdapter = adapter;
	}

	public LineupsPagerAdapter getAdapter(){
		return this.mPagerAdapter;
	}

	public ViewPager getPager(){
		return this.mPager;
	}

	public List<RacingSet> getRacingSets(){
		return mPagerAdapter.getRacingSets();
	}

}
