package jie.android.bigscreen;

import java.io.IOException;

import jie.android.bigscreen.utils.Utils;
import android.app.Activity;
import android.content.ClipData;
import android.content.Intent;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.DragEvent;
import android.view.View;
import android.view.View.DragShadowBuilder;
import android.view.View.OnClickListener;
import android.view.View.OnDragListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.AbsoluteLayout;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class BSImageShowActivity extends Activity {

	private static final String Tag = BSImageShowActivity.class.getSimpleName();
	
	private RelativeLayout layout = null;
	private ImageView iv = null;

    Drawable enterShape = null;;
    Drawable normalShape = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		this.setContentView(R.layout.activity_imageshow);		
		layout = (RelativeLayout) this.findViewById(R.id.layout);
		
		int location = -1;
		String content = null;
		Intent intent = this.getIntent();
		if (intent != null) {
			content = intent.getExtras().getString("content");
		}
		
		iv = new ImageView(this, Utils.getAttributeSet(this, ImageView.class.getSimpleName(), R.layout.show_bsimage));
		try {
			iv.setImageDrawable(Drawable.createFromStream(this.getAssets().open(content), content));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		iv.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				onImageClick(v);
			}
			
		});		
		
		iv.setOnLongClickListener(new OnLongClickListener() {

			@Override
			public boolean onLongClick(View view) {
				return onImageLongClick();
			}
			
		});

		RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) iv.getLayoutParams();
		if (params == null) {
			params = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT,	LayoutParams.MATCH_PARENT);
		}
		params.setMargins(0, 0, 0, 0);
		
//		params.x = 100;
//		params.y = 100;
		
		layout.addView(iv, params);

		
//		AbsoluteLayout.LayoutParams params = ((AbsoluteLayout.LayoutParams) iv.getLayoutParams());
//		params.x = 100;
//		params.y = 100;
//		iv.setLayoutParams(params);				
		
		layout.setOnDragListener(new OnDragListener() {

			@Override
			public boolean onDrag(View view, DragEvent event) {
				return onImageDrag(view, event);
			}
			
		});
		
	    enterShape = getResources().getDrawable(R.drawable.shape_droptarget);
	    normalShape = getResources().getDrawable(R.drawable.shape);
		
	}
	protected void onImageClick(View v) {
		this.finish();
	}
	protected boolean onImageDrag(View view, DragEvent event) {
		
		Log.d(Tag, "event = " + event.getAction());
		switch (event.getAction()) {
//		case DragEvent.ACTION_DRAG_STARTED:
//		    // Do nothing
//		    break;
//		case DragEvent.ACTION_DRAG_ENTERED:
//		    view.setBackgroundDrawable(enterShape);
//		    break;
//		case DragEvent.ACTION_DRAG_EXITED:
//		    view.setBackgroundDrawable(normalShape);
//		    break;
//		case DragEvent.ACTION_DRAG_ENDED:
//			view.setBackgroundDrawable(normalShape);		        
		case DragEvent.ACTION_DROP:
			
			Log.d(Tag, "view = " + view.getClass().getSimpleName() + " x = " + event.getX() + " y = " + event.getY());
			RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) iv.getLayoutParams();
			
//			FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) view.getLayoutParams();
			params.setMargins((int) event.getX() - 50, (int) event.getY() - 50, 0, 0);
			View v = (View) event.getLocalState();
			v.setLayoutParams(params);
			v.setVisibility(View.VISIBLE);
			
			break;
		}
		
		return true;
	}
	
	private class MyShadowBuilder extends DragShadowBuilder {

		public MyShadowBuilder(View arg0) {
			super(arg0);
		}

		@Override
		public void onProvideShadowMetrics(Point shadowSize, Point shadowTouchPoint) {
			Log.d(Tag, "======onProvideShadowMetrics()");
//			Point s = new Point(shadowSize.x + 40, shadowSize.y + 40);
//			shadowSize.y *= 2;
//			shadowTouchPoint.x -= 80;
//			shadowTouchPoint.y -= 80;
			super.onProvideShadowMetrics(shadowSize, shadowTouchPoint);
		}
		
	}
	
	protected boolean onImageLongClick() {
		ClipData data = ClipData.newPlainText("label", "text");
		MyShadowBuilder shadowBuilder = new MyShadowBuilder(iv);
		iv.startDrag(data, shadowBuilder, iv, 0);
		iv.setVisibility(View.INVISIBLE);
		return true;
	}
	
}
