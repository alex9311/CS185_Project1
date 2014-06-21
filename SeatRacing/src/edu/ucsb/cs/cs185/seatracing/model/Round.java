package edu.ucsb.cs.cs185.seatracing.model;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

public class Round implements Parcelable{
	
	private static final int[] switches = {
		0, 1, 0, 2, 0, 1, 0, 3, 0, 1, 0, 2, 0, 1, 0
	};
	
	private long dateCreated;
	private int mNumRowers;
	private int mNumRaces;
	private int mNumBoats;
	private boolean switchingLast;
	private int id;
	
	private int currentRace;
	
	private List<RacingSet> mRacingSets;
	private List<RaceResult> mResults;
	
	public Round(long dateCreatedIn){
		dateCreated = dateCreatedIn;
		currentRace=0;
	}
	
	public void setRacingSets(List<RacingSet> sets){
		mRacingSets = sets;
		mNumBoats = sets.size()*2;
		mNumRowers = mNumBoats*sets.get(0).getBoat1().size();
		if(switchingLast){
			mNumRaces = (int)Math.pow(2, mNumRowers);
		}
		else{
			mNumRaces = (int)Math.pow(2, (mNumRowers/mNumBoats)-1);
		}
		mResults = new ArrayList<RaceResult>(mNumRaces);
	}
	
	public List<RacingSet> getRacingSets(){
		return this.mRacingSets;
	}
	
	public void setResults(List<RaceResult> results){
		this.mResults = results;
	}
	
	public List<RaceResult> getResults(){
		return mResults;
	}
	
	public void setID(int id){
		this.id = id;
	}
	
	public int getID(){
		return this.id;
	}

		
	public long dateCreated(){
		return this.dateCreated;
	}
	
	public int getCurrentRace(){
		return currentRace;
	}
	
	public void setCurrentRace(int race){
		currentRace = race;
	}
	
	public void setSwitchingLast(boolean sl){
		switchingLast = sl;
	}
	
	public boolean switchingLast(){
		return switchingLast;
	}
	
	public int getNumRaces(){
		return this.mNumRaces;
	}
	
	public boolean hasSwitch(){
		return (currentRace+1)!=mNumRaces;
	}
	
	/**
	 * 
	 * @param raceNum  Num of the race run just BEFORE this switch (0 index, 14 max)
	 * @param switchLast Whether the last pair is switching in this race.
	 * @return Which seat to switch next (0 index)
	 */
	public static int getSwitchIndex(int raceNum, boolean switchLast){
		return switches[raceNum];
	}
	
	
	public void writeToBundle(Bundle b){
		
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeLong(dateCreated);
		dest.writeInt(mNumRowers);
		dest.writeInt(mNumRaces);
		dest.writeInt(mNumBoats);
		dest.writeInt(switchingLast ? 1 : 0);
		dest.writeInt(id);
		dest.writeInt(currentRace);
		dest.writeList(mRacingSets);
		dest.writeList(mResults);
	}
	
	public static final Parcelable.Creator<Round> CREATOR
		= new Parcelable.Creator<Round>() {
			
			@Override
			public Round[] newArray(int size) {
				return new Round[size];
			}
			
			@Override
			public Round createFromParcel(Parcel source) {
				return new Round(source);
			}
		};
		
		private Round(Parcel in){
			this.mNumRowers = in.readInt();
			this.mNumRaces = in.readInt();
			this.mNumBoats = in.readInt();
			this.switchingLast = (in.readInt() == 1);
			this.id = in.readInt();
			this.currentRace = in.readInt();
			in.readList(this.mRacingSets, RacingSet.class.getClassLoader());
			in.readList(this.mResults, RaceResult.class.getClassLoader());
		}
}
