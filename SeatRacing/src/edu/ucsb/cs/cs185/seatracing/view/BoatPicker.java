package edu.ucsb.cs.cs185.seatracing.view;

import java.util.List;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import edu.ucsb.cs.cs185.seatracing.R;
import edu.ucsb.cs.cs185.seatracing.model.Boat;
import edu.ucsb.cs.cs185.seatracing.model.BoatResult;
import edu.ucsb.cs.cs185.seatracing.model.RacingSet;

public class BoatPicker {
	
	public static View getView(Context context, List<RacingSet> sets, BoatResult[] results, int index){
		
		ScrollView scroller = new ScrollView(context);
		
		LinearLayout layout = new LinearLayout(context);
	    layout.setOrientation(LinearLayout.VERTICAL);
	    LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
	    //p.gravity = Gravity.CENTER;
	    //p.weight = 1;
	    
	    RadioGroup rg = new RadioGroup(context);
	    rg.setId(R.id.boatpicker_radiogroup);
	    rg.setPadding(96,48,48,48);
	    
	    for(int i=0; i<sets.size(); ++i){
	    	
	    	RacingSet rs = sets.get(i);
	    	
		    RadioButton rb1 = new RadioButton(context);
		    rb1.setPadding(8, 8, 8, 8);
		    rb1.setText(rs.getBoat1().name());
		    rb1.setTextSize(context.getResources().getDimension(R.dimen.boatpick_text_size));
		    rb1.setLayoutParams(p);
		    rb1.setTag(rs.getBoat1());
		    if(containsBoat(results, rs.getBoat1())){
		    	rb1.setTextColor(context.getResources().getColor(R.color.text_disabled));
		    }
		    
		    RadioButton rb2 = new RadioButton(context);
		    rb2.setPadding(8, 8, 8, 8);
		    rb2.setText(rs.getBoat2().name());
		    rb2.setTextSize(context.getResources().getDimension(R.dimen.boatpick_text_size));
		    rb2.setLayoutParams(p);
		    rb2.setTag(rs.getBoat2());
		    if(containsBoat(results, rs.getBoat2())){
		    	rb2.setTextColor(context.getResources().getColor(R.color.text_disabled));
		    }

		    
		    
		    rg.addView(rb1);
		    rg.addView(rb2);
		    
		    if(results[index].boat == rs.getBoat1()){
		    	rg.check(rb1.getId());
		    }
		    else  if(results[index].boat == rs.getBoat2()){
		    	rg.check(rb2.getId());
		    }
	    }
	    
	    layout.addView(rg); 
	    scroller.addView(layout);
	    
	    return scroller;
	}
	
	private static boolean containsBoat(BoatResult[] results, Boat b){
		for(BoatResult result : results){
			if(result.boat == b){
				return true;
			}
		}
		return false;
	}
}
