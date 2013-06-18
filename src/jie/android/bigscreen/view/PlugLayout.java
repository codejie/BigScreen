package jie.android.bigscreen.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

public class PlugLayout extends LinearLayout {

	private int viewCount = 0;
	
	public PlugLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.setOrientation(LinearLayout.VERTICAL);
	}
	
	public boolean addChildView(View view) {
		
		addView(view, viewCount);
		++ viewCount;
		return true;
	}

}
