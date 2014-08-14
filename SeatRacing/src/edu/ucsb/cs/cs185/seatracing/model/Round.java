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
			mNumRaces = (int)Math.pow(2, mNumRowers/mNumBoats);
		}
		else{
			mNumRaces = (int)Math.pow(2, (mNumRowers/mNumBoats)-1);
		}
		mResults = new ArrayList<RaceResult>(mNumRaces);
	}
	
	public List<RacingSet> getRacingSets(){
		return this.mRacingSets;
	}
	
	public void addResult(RaceResult result){
		result.setRaceNum(getCurrentRace());
		this.mResults.add(result);
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
	
	/**
	 * This will perform scheduled switches from current race to indicated race number
	 * @param race race number to switch until
	 */
	public void switchToRaceNum(int race){
		if(race<currentRace){
			throw new IllegalArgumentException("Cannot switch to race "+race+", already at race "+currentRace);
		}
		
		for(int i=currentRace; i<race; ++i){
			int switchToMake = Round.getSwitchIndex(getCurrentRace(), switchingLast());
			for(RacingSet rs : mRacingSets){
				Boat.switchRowers(rs.getBoat1(), rs.getBoat2(), switchToMake);
			}
		}
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
	
	public static boolean switchingLast(int numRaces, int boatSize){
		return Math.pow(2, boatSize) >= (numRaces-0.00001); //using fp error pad here
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
			this.dateCreated = in.readLong();
			this.mNumRowers = in.readInt();
			this.mNumRaces = in.readInt();
			this.mNumBoats = in.readInt();
			this.switchingLast = (in.readInt() == 1);
			this.id = in.readInt();
			this.currentRace = in.readInt();
			this.mRacingSets = new ArrayList<RacingSet>();
			this.mResults = new ArrayList<RaceResult>();
			in.readList(this.mRacingSets, RacingSet.class.getClassLoader());
			in.readList(this.mResults, RaceResult.class.getClassLoader());
		}
}
