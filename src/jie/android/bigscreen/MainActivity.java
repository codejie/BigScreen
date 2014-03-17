package jie.android.bigscreen;

import java.io.IOException;
import java.io.InputStream;

import jie.android.bigscreen.view.BSImageView;
import jie.android.bigscreen.view.SlotScrollView;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.content.ClipData;
import android.content.Intent;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.DragEvent;
import android.view.Menu;
import android.view.View;
import android.view.View.DragShadowBuilder;
import android.view.View.OnClickListener;
import android.view.View.OnDragListener;
import android.view.View.OnLongClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class MainActivity extends Activity implements OnClickListener {

	private static final String Tag = MainActivity.class.getSimpleName();
	
	private static final int DESKTOP_CLOSE	=	1;
	private static final int DESKTOP_OPEN		=	2;
	
	private int SCREEN_HEIGHT	=	-1;
	private int SCREEN_WIDTH	=	-1;
	
	private LinearLayout frame = null;
	private LinearLayout dock = null;
	private RelativeLayout desktop = null;
	private LinearLayout desktopFrame = null;
	private SlotScrollView slotScrollView = null;
	
	private ImageButton back = null;
	private ImageButton remove = null;
	
	private ImageShowHelper imageShowHelper = null;

	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case DESKTOP_CLOSE:
				imageShowHelper.show_2x1(12);
				imageShowHelper.setOnLongClickListener(null);
				break;
			case DESKTOP_OPEN:
				imageShowHelper.show_1xsmall(12);
				imageShowHelper.setOnLongClickListener(onLongClickListener);
				break;
			default:
				break;
			}
		}		
	};
	
	private OnClickListener onClickListener = new OnClickListener() {
		@Override
		public void onClick(View view) {
			onImageClick(view);
		}		
	};
	
	private OnLongClickListener onLongClickListener = new OnLongClickListener() {

		@Override
		public boolean onLongClick(View view) {
			return onImageLongClick(view);
		}
		
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		getScreenInfo();
		
		setContentView(R.layout.activity_main);		
		
		frame = (LinearLayout) this.findViewById(R.id.frame);
		desktop = (RelativeLayout) this.findViewById(R.id.desktop);
		desktopFrame = (LinearLayout) this.findViewById(R.id.desktop_frame);
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
		
		
		desktop.setOnDragListener(new OnDragListener() {

			@Override
			public boolean onDrag(View view, DragEvent event) {
				return onDesktopDrag(view, event);
			}			
		});
		
		((ImageView)this.findViewById(R.id.remove)).setOnDragListener(new OnDragListener() {

			@Override
			public boolean onDrag(View view, DragEvent event) {
				return onRemoveDrag(view, event);
			}			
		});
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

	protected boolean onImageLongClick(View view) {
		Intent intent = new Intent();
		intent.putExtra("src", 0);
		intent.putExtra("content", ((BSImageView)view).getContent());		
		ClipData data = ClipData.newIntent("intent", intent);// .newPlainText("content", ((BSImageView)view).getContent());
		DragShadowBuilder builder = new DragShadowBuilder(view);
		view.startDrag(data, builder, null, 0);
		
		return true;
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
		this.runOnUiThread(new Runnable() {
			@Override
			public void run() {
				desktopFrame.setVisibility(View.GONE);
				dock.setVisibility(View.VISIBLE);		
				LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, 1.0f);
				frame.setLayoutParams(params);
				
				handler.sendMessage(Message.obtain(handler, DESKTOP_CLOSE));
			}
		});
	}

	private void onClickMeClick() {
		this.runOnUiThread(new Runnable() {
			@Override
			public void run() {
				dock.setVisibility(View.GONE);
				LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 110, 0.0f);
				frame.setLayoutParams(params);
				params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, 1.0f);
				desktopFrame.setLayoutParams(params);
				desktopFrame.setVisibility(View.VISIBLE);
				
				handler.sendMessage(Message.obtain(handler, DESKTOP_OPEN));
			}
			
		});
	}

	protected boolean onRemoveDrag(View view, DragEvent event) {
		switch (event.getAction()) {
		case DragEvent.ACTION_DRAG_STARTED:
//		    // Do nothing
		    break;
		case DragEvent.ACTION_DRAG_ENTERED:
			view.setBackgroundDrawable(this.getResources().getDrawable(R.drawable.shape_enter));
		    break;
		case DragEvent.ACTION_DRAG_EXITED:
			view.setBackgroundDrawable(this.getResources().getDrawable(R.drawable.shape_exit));
		    break;
		case DragEvent.ACTION_DRAG_ENDED:
			view.setBackgroundDrawable(this.getResources().getDrawable(R.drawable.shape_exit));
			break;
		case DragEvent.ACTION_DROP:
			
			Log.d(Tag, "view = " + view.getClass().getSimpleName() + " x = " + event.getX() + " y = " + event.getY());
			
			Intent intent = event.getClipData().getItemAt(0).getIntent();
			if (intent == null) {
				return false;
			}
			
			int src = intent.getIntExtra("src", 0);
			if(src == 1) {
				ImageView iv = (ImageView) event.getLocalState();
				desktop.removeView(iv);
			}
			break;
		default:
			return false;
		}
		
		return true;
	}

	protected boolean onDesktopDrag(View view, DragEvent event) {
		switch (event.getAction()) {
		case DragEvent.ACTION_DRAG_STARTED:
		    break;
		case DragEvent.ACTION_DRAG_ENTERED:
		    break;
		case DragEvent.ACTION_DRAG_EXITED:
		    break;
		case DragEvent.ACTION_DRAG_ENDED:
			break;		
		case DragEvent.ACTION_DROP:
			
			Intent intent = event.getClipData().getItemAt(0).getIntent();
			if (intent == null) {
				return false;
			}
			
			int src = intent.getIntExtra("src", 0);
			if (src == 0) {
				String file = intent.getExtras().getString("content");
				makeDesktopImage(file, (int) event.getX(), (int) event.getY());
			} else if(src == 1) {
				ImageView iv = (ImageView) event.getLocalState();
				moveDesktopImage(iv, (int) event.getX(), (int) event.getY());
			}
			break;
		default:
			return false;		
		}
		
		return true;
	}

	private void moveDesktopImage(ImageView iv, int x, int y) {
//		params.setMargins((int) event.getX() - 50, (int) event.getY() - 50, 0, 0);
		RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) iv.getLayoutParams();
		params.setMargins(x, y, 0, 0);
		iv.setLayoutParams(params);
		
		iv.setVisibility(View.VISIBLE);
	}

	private void makeDesktopImage(String file, int x, int y) {
		try {
			InputStream is = this.getAssets().open(file);
						
			ImageView iv = new ImageView(this);			
			iv.setImageDrawable(Drawable.createFromStream(is, file));
			RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
			params.setMargins(x, y, 0, 0);
			
			iv.setOnLongClickListener(new OnLongClickListener() {

				@Override
				public boolean onLongClick(View view) {
					Intent intent = new Intent();
					intent.putExtra("src", 1);
					ClipData data = ClipData.newIntent("intent", intent);// .newPlainText("content", ((BSImageView)view).getContent());
					DragShadowBuilder builder = new DragShadowBuilder(view);
					view.startDrag(data, builder, view, 0);
					view.setVisibility(View.GONE);
					return true;
				}
				
			});
			
			desktop.addView(iv, params);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
