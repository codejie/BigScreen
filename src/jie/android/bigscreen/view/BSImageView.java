package jie.android.bigscreen.view;

import java.io.File;
import java.io.InputStream;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

public class BSImageView extends ImageView implements ViewInformation {
	
	public enum ContentLocation {
		NULL, RESOURCE, ASSET, DATA
	}
	
	private ContentLocation location = ContentLocation.NULL;
	private String content = null;
	
	
	public BSImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	public ViewType getType() {
		return ViewType.IMAGE;
	}

	public void loadContent(int resId) {
		location = ContentLocation.RESOURCE;
		content = String.valueOf(resId);
		this.setImageResource(resId);		
	}
	
	public void loadContent(String src) {
		location = ContentLocation.DATA;
		content = src;

		Uri uri = Uri.fromFile(new File(src));
		this.setImageURI(uri);
	}
	
	
	public void loadContent(InputStream is, String name) {
		location = ContentLocation.ASSET;
		content = name;
		
		this.setImageDrawable(Drawable.createFromStream(is, name));
	}
	
	public String getContent() {
		return content;
	}
	
	public ContentLocation getContentLocation() {
		return location;
	}
}
