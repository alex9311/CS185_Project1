package edu.ucsb.cs.cs185.seatracing.model;

import java.util.ArrayList;
import java.util.List;

import android.os.Parcel;
import android.os.Parcelable;

public class Rower implements Parcelable {
	private String mName;
	private int rowerId;
	private List<Long> finishTimes;

	public Rower(String name){
		mName = name;
		finishTimes = new ArrayList<Long>();
	}

	public String name(){
		return this.mName;
	}

	public void setName(String s){
		this.mName=s;
	}
	
	public int id(){
		return this.rowerId;
	}
	
	public void setId(int i){
		this.rowerId=i;
	}

	public void addfinishTime(long time){
		finishTimes.add(time);
	}

	public long getFinishTime(int position){
		return finishTimes.get(position);
	}

	public int getNumTimes(){
		return finishTimes.size();
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(mName);
		//dest.writeList(finishTimes);
	}

	public static final Parcelable.Creator<Rower> CREATOR = new Parcelable.Creator<Rower>() {
		public Rower createFromParcel(Parcel in) {
			return new Rower(in);
		}

		public Rower[] newArray(int size) {
			return new Rower[size];
		}
	};

	private Rower(Parcel in) {
		mName = in.readString();
		//in.readList(finishTimes, Long.class.getClassLoader());
	}
	
	@Override
	public String toString(){
		return "Rower("+mName+")";
	}
}
