package edu.ucsb.cs.cs185.seatracing.model;

import java.util.ArrayList;
import java.util.List;

import android.os.Parcel;
import android.os.Parcelable;

public class BoatResult implements Parcelable{
	public Boat boat;
	public long time;
	private List<Rower> rowers;
	
	public BoatResult(){
		this(null, 0);
	}
	public BoatResult(Boat b, long t){
		boat = b;
		time = t;
		rowers = new ArrayList<Rower>();
	}
	@Override
	public int describeContents() {
		return 0;
	}
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeParcelable(boat, flags);
		dest.writeLong(time);
		dest.writeList(rowers);
	}
	
	public static final Parcelable.Creator<BoatResult> CREATOR
		= new Parcelable.Creator<BoatResult>() {
			@Override
			public BoatResult[] newArray(int size) {
				return new BoatResult[size];
			}
			
			@Override
			public BoatResult createFromParcel(Parcel source) {
				return new BoatResult(source);
			}
		};
		
	private BoatResult(Parcel in){
		boat = in.readParcelable(Boat.class.getClassLoader());
		time = in.readLong();
		rowers = new ArrayList<Rower>();
		in.readList(rowers, Rower.class.getClassLoader());
	}
	
}
