package com.xian.patientappv1;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by PTTsiuoLIVIA on 3/30/16.
 */
public class NonSwipeableViewpager extends ViewPager{
    public NonSwipeableViewpager(Context context) {
        super(context);
    }

    public NonSwipeableViewpager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        // Never allow swiping to switch between pages
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // Never allow swiping to switch between pages
        return false;
    }
}
