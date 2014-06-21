package edu.ucsb.cs.cs185.seatracing.model;

import java.util.ArrayList;
import java.util.List;

import android.os.Parcel;
import android.os.Parcelable;

public class RaceResult implements Parcelable{
	private List<BoatResult> boatResults;
	
	public RaceResult(){
		boatResults = new ArrayList<BoatResult>();
	}
	
	public void addBoatResult(BoatResult b){
		boatResults.add(b);
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeList(boatResults);
	}
	
	public static final Parcelable.Creator<RaceResult> CREATOR
		= new Parcelable.Creator<RaceResult>() {
			@Override
			public RaceResult[] newArray(int size) {
				return new RaceResult[size];
			}
			
			@Override
			public RaceResult createFromParcel(Parcel source) {
				return new RaceResult(source);
			}
		};
		
		private RaceResult(Parcel in){
			in.readList(this.boatResults, BoatResult.class.getClassLoader());
		}
}
