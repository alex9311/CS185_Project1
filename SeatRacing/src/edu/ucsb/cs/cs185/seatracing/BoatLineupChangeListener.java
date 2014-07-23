package edu.ucsb.cs.cs185.seatracing;

import edu.ucsb.cs.cs185.seatracing.model.Boat;

public interface BoatLineupChangeListener {
	public void boatLineupChanges(int index, Boat b);
}
