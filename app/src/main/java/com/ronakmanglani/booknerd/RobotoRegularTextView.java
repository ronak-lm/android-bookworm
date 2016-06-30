package com.ronakmanglani.booknerd;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

public class RobotoRegularTextView extends TextView {

    public RobotoRegularTextView(Context context) {
        super(context);
        applyCustomFont();
    }

    public RobotoRegularTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        applyCustomFont();
    }

    public RobotoRegularTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        applyCustomFont();
    }

    private void applyCustomFont() {
        Typeface customFont = FontCacheUtil.getTypeface(FontCacheUtil.ROBOTO_REGULAR);
        setTypeface(customFont);
    }
}