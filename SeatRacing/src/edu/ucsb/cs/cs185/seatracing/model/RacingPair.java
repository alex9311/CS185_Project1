package edu.ucsb.cs.cs185.seatracing.model;

import android.os.Parcel;
import android.os.Parcelable;


public class RacingPair implements Parcelable{
	
	Boat mBoat1;
	Boat mBoat2;
	int index;
	
	public RacingPair(Boat b1, Boat b2, int seat){
		this.mBoat1 = b1;
		this.mBoat2 = b2;
		this.index = seat;
	}
	
	public Rower getRower1(){
		return mBoat1.getRower(index);
	}
	
	public Rower getRower2(){
		return mBoat2.getRower(index);
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		//mBoat1.writeToParcel(dest, flags);
		//mBoat2.writeToParcel(dest,flags);
	}
	
    public static final Parcelable.Creator<RacingPair> CREATOR = new Parcelable.Creator<RacingPair>() {
    	public RacingPair createFromParcel(Parcel in) {
    		return new RacingPair(in);
    	}

    	public RacingPair[] newArray(int size) {
    		return new RacingPair[size];
    	}
    };

	private RacingPair(Parcel in) {
		//r1 = (Rower) in.readParcelable(Rower.class.getClassLoader());
		//r2 = (Rower) in.readParcelable(Rower.class.getClassLoader());
	}

}
