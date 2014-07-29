package edu.ucsb.cs.cs185.seatracing;

import edu.ucsb.cs.cs185.seatracing.model.BoatResult;

public interface ResultsFinalizedListener {
	public void onResultsChanged(boolean complete, BoatResult[] results);
}
