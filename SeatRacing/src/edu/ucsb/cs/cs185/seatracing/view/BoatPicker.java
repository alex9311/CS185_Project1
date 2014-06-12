package edu.ucsb.cs.cs185.seatracing.view;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import edu.ucsb.cs.cs185.seatracing.R;
import edu.ucsb.cs.cs185.seatracing.model.RacingSet;

public class BoatPicker {
	
	public static View getView(Context context, List<RacingSet> sets){
		
		ScrollView scroller = new ScrollView(context);
		
		LinearLayout layout = new LinearLayout(context);
	    layout.setOrientation(LinearLayout.VERTICAL);
	    LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
	    p.weight = 1;
	    
	    //boat1
	    RadioGroup rg = new RadioGroup(context);
	    rg.setId(R.id.boatpicker_radiogroup);
	    rg.setPadding(15,15,15,15);
	    for(RacingSet rs : sets){
		    RadioButton rb1 = new RadioButton(context);
		    rb1.setPadding(5, 5, 5, 5);
		    rb1.setText(rs.getBoat1().name());
		    rb1.setTextSize(context.getResources().getDimension(R.dimen.boatpick_text_size));
		    rb1.setLayoutParams(p);
		    rb1.setTag(rs.getBoat1());
		    RadioButton rb2 = new RadioButton(context);
		    rb2.setPadding(5, 5, 5, 5);
		    rb2.setText(rs.getBoat2().name());
		    rb2.setTextSize(context.getResources().getDimension(R.dimen.boatpick_text_size));
		    rb2.setLayoutParams(p);
		    rb2.setTag(rs.getBoat2());
		    rg.addView(rb1);
		    rg.addView(rb2);
	    }
	    
	    layout.addView(rg); 
	    scroller.addView(layout);
	    
	    return scroller;
	}
}
