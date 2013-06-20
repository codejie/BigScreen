package jie.android.bigscreen;

import jie.android.bigscreen.view.BSImageView;
import jie.android.bigscreen.view.SlotScrollView;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Point;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class MainActivity extends Activity implements OnClickListener {

	
	private int SCREEN_HEIGHT	=	-1;
	private int SCREEN_WIDTH	=	-1;
	
	private LinearLayout frame = null;
	private LinearLayout dock = null;
	private RelativeLayout desktop = null;
	private SlotScrollView slotScrollView = null;
	
	private ImageButton back = null;
	private ImageButton remove = null;
	
	private ImageShowHelper imageShowHelper = null;
	
	private OnClickListener onClickListener = new OnClickListener() {

		@Override
		public void onClick(View view) {
			onImageClick(view);
		}
		
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		getScreenInfo();
		
		setContentView(R.layout.activity_main);		
		
		frame = (LinearLayout) this.findViewById(R.id.frame);
		desktop = (RelativeLayout) this.findViewById(R.id.desktop);
		dock = (LinearLayout) this.findViewById(R.id.dock);				
		
		slotScrollView = (SlotScrollView) this.findViewById(R.id.slotScrollView1);
		
		imageShowHelper = new ImageShowHelper(this, SCREEN_WIDTH, SCREEN_HEIGHT, slotScrollView);
		imageShowHelper.setOnClickListener(onClickListener);
		
		((Button) this.findViewById(R.id.button8)).setOnClickListener(this);
		((Button) this.findViewById(R.id.button7)).setOnClickListener(this);
		((Button) this.findViewById(R.id.button6)).setOnClickListener(this);
		((Button) this.findViewById(R.id.button5)).setOnClickListener(this);
		((Button) this.findViewById(R.id.button4)).setOnClickListener(this);
		((Button) this.findViewById(R.id.button3)).setOnClickListener(this);
		
		((Button) this.findViewById(R.id.button2)).setOnClickListener(this);
		
		((ImageButton)this.findViewById(R.id.back)).setOnClickListener(this);
//		((ImageButton)this.findViewById(R.id.remove)).setOnClickListener(this);
	}

	private void getScreenInfo() {
		Point point = new Point();
		this.getWindowManager().getDefaultDisplay().getSize(point);
		SCREEN_HEIGHT = point.y;
		SCREEN_WIDTH = point.x;
	}

	protected void onImageClick(View view) {
		
		Intent intent = new Intent(this, BSImageShowActivity.class);
		
		if (view instanceof BSImageView) {
			intent.putExtra("location", ((BSImageView)view).getContentLocation().ordinal());
			intent.putExtra("content", ((BSImageView)view).getContent());			
		} else {
			return;
		}
		
		this.startActivity(intent);
		this.overridePendingTransition(R.anim.zoom_enter, R.anim.zoom_exit);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		//getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.button8:
			imageShowHelper.show_1x1(12);
			break;
		case R.id.button7:
			imageShowHelper.show_1xslot(12);
			break;
		case R.id.button6:
			imageShowHelper.show_1x4(12);
			break;
		case R.id.button5:
			imageShowHelper.show_2x1(12);
			break;
		case R.id.button4:
			imageShowHelper.show_2x4(12);
			break;
		case R.id.button3:
			imageShowHelper.show_2x4_wrap(12);
			break;
		case R.id.button2:
			onClickMeClick();
			break;
		case R.id.back:
			onBackClick();
			break;
		}
	}

	private void onBackClick() {
		desktop.setVisibility(View.GONE);
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, 1.0f);
		frame.setLayoutParams(params);
//		imageShowHelper = new ImageShowHelper(this, SCREEN_WIDTH, SCREEN_HEIGHT, slotScrollView);
//		imageShowHelper.setOnClickListener(onClickListener);
		
//		slotScrollView.refresh();	
		
		imageShowHelper.show_1x1(12);
		dock.setVisibility(View.VISIBLE);
	}

	private void onClickMeClick() {
		dock.setVisibility(View.GONE);		
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 110, 0.0f);
		frame.setLayoutParams(params);
		imageShowHelper.show_1xsmall(12);
		params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, 1.0f);
		desktop.setLayoutParams(params);
		desktop.setVisibility(View.VISIBLE);
	}

}
