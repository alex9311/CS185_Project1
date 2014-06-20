package edu.ucsb.cs.cs185.seatracing.view;

import java.text.DecimalFormat;

import edu.ucsb.cs.cs185.seatracing.model.SplitTimerUpdateListener;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

public class SplitTimerTextView extends TextView implements SplitTimerUpdateListener {
	
	long mTimeElapsed = 0;
	
    public SplitTimerTextView(Context context) {
        this (context, null, 0);
    }

    public SplitTimerTextView(Context context, AttributeSet attrs) {
        this (context, attrs, 0);
    }

    public SplitTimerTextView(Context context, AttributeSet attrs, int defStyle) {
        super (context, attrs, defStyle);

        updateTimer(0);
    }


	@Override
	public void updateTimer(long time) {
        DecimalFormat df = new DecimalFormat("00");
        
        int hours = (int)(time / (3600 * 1000));
        int remaining = (int)(time % (3600 * 1000));
        
        int minutes = (int)(remaining / (60 * 1000));
        remaining = (int)(remaining % (60 * 1000));
        
        int seconds = (int)(remaining / 1000);
        remaining = (int)(remaining % (1000));
        
        int milliseconds = (int)(((int)time % 1000) / 10);
        
        String text = "";
        
        if (hours > 0) {
        	text += df.format(hours) + ":";
        }
        
       	text += df.format(minutes) + ":";
       	text += df.format(seconds) + ":";
       	text += df.format(milliseconds);
        
       	mTimeElapsed = time;
        setText(text);
	}
	
	public long getTimeElapsed(){
		return mTimeElapsed;
	}
	
	public void setTimeElapsed(long newElapsed){
		mTimeElapsed = newElapsed;
		updateTimer(mTimeElapsed);
	}

}
