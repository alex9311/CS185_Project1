package edu.ucsb.cs.cs185.seatracing.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

public class Boat implements Parcelable {

	private int size;
	private String name;
	private int id;
	private List<BoatResult> results;
	private Rower[] rowers;

	/*
	public Boat(int id, Bundle lineup){
		loadFromBundle(id, lineup);
		id = (int)System.currentTimeMillis();
		results = new ArrayList<BoatResult>();
	}
	*/

	public Boat(String name, int size){
		this.size=size;
		this.name = name;
		this.id = (int)System.currentTimeMillis();
		
		results = new ArrayList<BoatResult>();
	}
	
	public void addResult(BoatResult result){
		results.add(result);
	}

	public void setRowers(String... rowers){
		if(rowers.length != this.size){
			throw new IllegalArgumentException("Cannot fill "+this.size+" size boat with "+rowers.length+" rowers!");
		}

		Rower[] rowerObjs = new Rower[this.size];
		for(int i=0; i<this.size; ++i){
			rowerObjs[i] = new Rower(rowers[i]);
		}
		setRowers(rowerObjs);
	}

	public void setRowers(Rower[] rowers){
		this.rowers = rowers;
	}
	
	public void setID(int newID){
		this.id = newID;
	}
	
	public int getID(){
		return this.id;
	}

	public String[] getRowerNames(){
		String[] names = new String[this.size];
		for(int i=0; i<this.size; ++i){
			names[i] = rowers[i].name();
		}
		return names;
	}
	
	public Rower getRower(int position){
		return rowers[position];
	}
	
	public Rower[] getRowers(){
		return rowers;
	}

	public String name(){
		return this.name;
	}


	public int size(){
		return this.size;
	}

	/**
	 * 
	 * @param b1 One boat to switch from
	 * @param b2 Another boat to switch from
	 * @param position Position in the boats to switch (0-indexed)
	 */
	public static void switchRowers(Boat b1, Boat b2, int position){
		if(b1.size() != b2.size()){
			throw new IllegalArgumentException("Tried to switch in boats of different sizes!");
		}
		else if(position >= b1.size()){
			throw new IndexOutOfBoundsException("Tried to switch rower "+position+" in boats of size "+b1.size());
		}

		Rower temp = b1.rowers[position];
		b1.rowers[position] = b2.rowers[position];
		b2.rowers[position] = temp;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(size);
		dest.writeString(name);
		dest.writeInt(id);
		dest.writeArray(rowers);
	}
	
    public static final Parcelable.Creator<Boat> CREATOR = new Parcelable.Creator<Boat>() {
    	public Boat createFromParcel(Parcel in) {
    		return new Boat(in);
    	}

    	public Boat[] newArray(int size) {
    		return new Boat[size];
    	}
    };

	private Boat(Parcel in) {
		this(null, 0);
		size = in.readInt();
		name = in.readString();
		id = in.readInt();
		Object[] temp = in.readArray(Rower.class.getClassLoader());
		rowers = new Rower[temp.length];
		for(int i=0; i<temp.length; ++i){
			rowers[i]=(Rower)temp[i];
		}
	}
	
	@Override
	public String toString(){
		StringBuilder sb = new StringBuilder();
		
		sb.append("Boat: "+this.name+": [");
		for(Rower r : rowers){
			sb.append(r.toString()+", ");
		}
		sb.append(" ]");
		
		return sb.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + Arrays.hashCode(rowers);
		result = prime * result + size;
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
		Boat other = (Boat) obj;
		if (id != other.id)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (!Arrays.equals(rowers, other.rowers))
			return false;
		if (size != other.size)
			return false;
		return true;
	}

}
