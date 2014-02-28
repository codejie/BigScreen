package jie.android.bigscreen.utils;

import java.io.IOException;

import org.xmlpull.v1.XmlPullParserException;

import android.content.Context;
import android.content.res.XmlResourceParser;
import android.util.AttributeSet;
import android.util.Xml;

public final class Utils {
	
	public static AttributeSet getAttributeSet(Context context, final String className, int resId) {

		XmlResourceParser p = context.getResources().getXml(resId);
		int state = XmlResourceParser.START_DOCUMENT;
		do {
			try {
				state = p.next();

				if (state == XmlResourceParser.START_TAG) {
					if (p.getName().equals(className)) {
						return Xml.asAttributeSet(p);
					}
				}

			} catch (XmlPullParserException e) {
				e.printStackTrace();
				break;
			} catch (IOException e) {
				e.printStackTrace();
				break;	
			}
		} while (state != XmlResourceParser.END_DOCUMENT);

		return null;
	}
}
