package edu.ucsb.cs.cs185.seatracing.model;

import android.os.Parcel;
import android.os.Parcelable;


public class RacingPair implements Parcelable{
	
	Rower r1;
	Rower r2;
	
	public RacingPair(Rower r1, Rower r2){
		this.r1 = r1;
		this.r2 = r2;
	}
	
	public Rower getRower1(){
		return r1;
	}
	
	public Rower getRower2(){
		return r2;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		r1.writeToParcel(dest, flags);
		r2.writeToParcel(dest,flags);
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
		r1 = (Rower) in.readParcelable(Rower.class.getClassLoader());
		r2 = (Rower) in.readParcelable(Rower.class.getClassLoader());
	}

}