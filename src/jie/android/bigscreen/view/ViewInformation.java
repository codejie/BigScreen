package jie.android.bigscreen.view;

import android.view.View;

public interface ViewInformation {
	public enum ViewType {
		IMAGE, VIDEO
	}
	
	public ViewType getType();
}
