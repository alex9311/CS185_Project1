package edu.ucsb.cs.cs185.seatracing.animation;

import android.support.v4.view.ViewPager.PageTransformer;
import android.view.View;

public class DefaultPageTransformer implements PageTransformer {

	@Override
	public void transformPage(View page, float position) {
		page.setTranslationX(position);
	}

}
