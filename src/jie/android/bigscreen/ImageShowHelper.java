package jie.android.bigscreen;

import java.io.IOException;

import jie.android.bigscreen.utils.Utils;
import jie.android.bigscreen.view.BSImageView;
import jie.android.bigscreen.view.PlugLayout;
import jie.android.bigscreen.view.SlotScrollView;
import android.app.Activity;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;

public class ImageShowHelper {
	
	private Activity activity = null;
	private int screen_width = -1;
	private int screen_height = -1;
	private SlotScrollView slot = null;
	
	private int imageLoadAmount = 0;
	
	private OnClickListener onClickListener = null;
	private OnLongClickListener onLongClickListener = null;
	
	public ImageShowHelper(Activity activity, int width, int height, SlotScrollView slot) {
		this.activity = activity;
		this.screen_width = width;
		this.screen_height = height;
		this.slot = slot;
	}
	
	public void setOnClickListener(OnClickListener listener) {
		onClickListener = listener;
	}
	
	public void setOnLongClickListener(OnLongClickListener listener) {
		onLongClickListener = listener;
	}
	
	public PlugLayout addPlugLayout(int width) {
		AttributeSet attrs = Utils.getAttributeSet(activity, PlugLayout.class.getName(), R.layout.view_pluglayout);
		PlugLayout plug = new PlugLayout(activity, attrs);
		plug.setOnClickListener(onClickListener);
		plug.setOnLongClickListener(onLongClickListener);
		
		int height = slot.getHeight();
		
		ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(width, height);
		
		slot.addPlugLayout(plug, params);
		
		return plug;
	}
	
	public void addImage(PlugLayout layout, int width, int height) {
		AttributeSet attrs = Utils.getAttributeSet(activity, BSImageView.class.getName(), R.layout.view_bsimage);			
		BSImageView iv = new BSImageView(activity, attrs);
		
		String file = String.format("%d.jpg", ((++ imageLoadAmount) % 5) + 1);
		
		try {
			iv.loadContent(activity.getAssets().open(file), file);
		} catch (IOException e) {
			e.printStackTrace();
		}

		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(width, height);
		params.gravity = Gravity.CENTER;
		
		layout.addChildView(iv, params);//new LinearLayout.LayoutParams(width, height));
	}
	
	
	public void clear() {
		slot.clearAllLayouts();
	}
	
	private void show_img_x1(int width, int col) {
		clear();
		for (int i = 0; i < col; ++ i) {
			PlugLayout layout = addPlugLayout(width);
			addImage(layout, width, width);
		}
	}
	
	private void show_img_x2(int width, int col) {
		clear();
		for (int i = 0; i < col; ++ i) {
			PlugLayout layout = addPlugLayout(width);
			int size = slot.getHeight() / 2;
			size = size > width ? width : size;
			addImage(layout, size, size);
			addImage(layout, size, size);
		}
	}	
	
	public void show_1xslot(int col) {
		show_img_x1(slot.getHeight(), col);
	}
	
	public void show_1x1(int col) {
		show_img_x1(screen_width, col);
	}
	
	public void show_1x4(int col) {
		show_img_x1(screen_width / 4, col);
		clear();
		for (int i = 0; i < col; ++ i) {
			PlugLayout layout = addPlugLayout(screen_width / 4);
			addImage(layout, screen_width / 4, screen_width / 4);
		}		
	}
	
	public void show_2x1(int col) {
		show_img_x2(slot.getHeight(), col);
	}
	
}
