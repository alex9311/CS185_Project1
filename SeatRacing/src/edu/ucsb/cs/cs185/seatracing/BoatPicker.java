package edu.ucsb.cs.cs185.seatracing;

import edu.ucsb.cs.cs185.seatracing.model.RacingSet;
import android.content.Context;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class BoatPicker {
	
	static void showDialogue(Context context, RacingSet rs){
		
		LinearLayout layout = new LinearLayout(context);
	    
	    LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
	    p.weight = 1;
	    
	    //boat1
	    RadioGroup rg = new RadioGroup(context);
	    RadioButton rb1 = new RadioButton(context);
	    rb1.setText(rs.getBoat1().name());
	    rb1.setLayoutParams(p);
	    RadioButton rb2 = new RadioButton(context);
	    rb2.setText(rs.getBoat2().name());
	    rb2.setLayoutParams(p);
	    rg.addView(rb1);
	    rg.addView(rb2);
	    
	    layout.addView(rg);
	    
	    
	}

}
