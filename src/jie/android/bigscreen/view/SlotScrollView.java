package jie.android.bigscreen.view;

import jie.android.bigscreen.R;
import jie.android.bigscreen.utils.Utils;
import android.content.Context;
import android.content.res.XmlResourceParser;
import android.util.AttributeSet;
import android.util.Xml;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

public class SlotScrollView extends HorizontalScrollView {

	private LinearLayout layout = null;	
	
	public SlotScrollView(Context context, AttributeSet attrs) {
		super(context, attrs);
		
		initLayout();
	}

	private void initLayout() {
		AttributeSet attrs = Utils.getAttributeSet(this.getContext(), LinearLayout.class.getSimpleName(), R.layout.view_slotscrollview_layout);		
		layout = new LinearLayout(this.getContext(), attrs);
		this.addView(layout);
	}

	public void addPlugLayout(PlugLayout plug, int index, LayoutParams params) {
		layout.addView(plug, index, params);
	}
	
	public void addPlugLayout(PlugLayout plug, LayoutParams params) {
		addPlugLayout(plug, -1, params);
	}
	
	public void addPlugLayout(PlugLayout plug) {
		layout.addView(plug);
	}
}
