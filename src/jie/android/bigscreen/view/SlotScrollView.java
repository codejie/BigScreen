package jie.android.bigscreen.view;

import jie.android.bigscreen.R;
import jie.android.bigscreen.utils.Utils;
import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;
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
		this.addView(layout, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, 1.0f));
	}

	public void addPlugLayout(PlugLayout plug, int index, ViewGroup.LayoutParams params) {
		layout.addView(plug, index, params);
	}
	
	public void addPlugLayout(PlugLayout plug, ViewGroup.LayoutParams params) {
		addPlugLayout(plug, -1, params);
	}
	
	public void addPlugLayout(PlugLayout plug) {
		layout.addView(plug);
	}

	public void clearAllLayouts() {
		layout.removeAllViews();
	}
	
	public void refresh() {
		this.removeAllViews();
		initLayout();
//		layout.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
	}
}
