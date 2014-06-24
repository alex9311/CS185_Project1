package edu.ucsb.cs.cs185.seatracing.model;

import java.util.ArrayList;
import java.util.List;

import android.os.Parcel;
import android.os.Parcelable;

public class Rower implements Parcelable {
	private String mName;
	private int rowerId;
	private List<Long> results;

	public Rower(String name){
		mName = name;
		results = new ArrayList<Long>();
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
	
	public void addResult(Long result){
		results.add(result);
	}

	public long getFinishTime(int position){
		return results.get(position);
	}

	public int getNumTimes(){
		return results.size();
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(mName);
		dest.writeInt(rowerId);
		dest.writeList(results);
	}

	public static final Parcelable.Creator<Rower> CREATOR = new Parcelable.Creator<Rower>() {
		@Override
		public Rower[] newArray(int size) {
			return new Rower[size];
		}
		
		@Override
		public Rower createFromParcel(Parcel in) {
			return new Rower(in);
		}
	};

	private Rower(Parcel in) {
		mName = in.readString();
		rowerId = in.readInt();
		results = new ArrayList<Long>();
		in.readList(results, Long.class.getClassLoader());
	}
	
	@Override
	public String toString(){
		return "Rower("+mName+")";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((mName == null) ? 0 : mName.hashCode());
		result = prime * result + ((results == null) ? 0 : results.hashCode());
		result = prime * result + rowerId;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Rower other = (Rower) obj;
		if (mName == null) {
			if (other.mName != null)
				return false;
		} else if (!mName.equals(other.mName))
			return false;
		if (results == null) {
			if (other.results != null)
				return false;
		} else if (!results.equals(other.results))
			return false;
		if (rowerId != other.rowerId)
			return false;
		return true;
	}
	
}
