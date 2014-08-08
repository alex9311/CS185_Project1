package edu.ucsb.cs.cs185.seatracing.model;

import java.util.ArrayList;
import java.util.List;

import android.os.Parcel;
import android.os.Parcelable;

public class BoatResult implements Parcelable{
	public Boat boat;
	public long time;
	public long date;
	private List<Rower> rowers;
	
	public BoatResult(){
		this(null, 0, null);
	}
	public BoatResult(Boat b, long t, List<Rower> rowersIn){
		boat = b;
		time = t;
		rowers = rowersIn;
	}
	
	public void setRowers(List<Rower> rowersIn){
		rowers = rowersIn;
	}
	
	public void setDate(long date){
		this.date = date;
	}
	
	public List<Result> getResults(){
		List<Result> ret = new ArrayList<Result>(rowers.size());
		
		for(int i=0; i<rowers.size(); ++i){
			Rower rower = rowers.get(i);
			Result res = new Result();
			
			res.setBoat(boat.getID());
			res.setTime(time);
			res.setRower(rower.id());
			res.setDate(date);
			res.setSeat(i);
			
			ret.add(res);
		}
		
		
		return ret;
	}
	
	public List<Rower> getRowers(){
		return this.rowers;
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
