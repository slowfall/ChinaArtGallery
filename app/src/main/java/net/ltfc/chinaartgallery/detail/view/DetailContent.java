package net.ltfc.chinaartgallery.detail.view;

import android.content.Context;
import android.os.Handler;
import android.support.v4.view.GestureDetectorCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebView;

/**
 * Created by zack on 2016/7/18.
 */
public class DetailContent extends WebView implements GestureDetector.OnGestureListener, GestureDetector.OnDoubleTapListener {

    private static final String DEBUG_TAG = "Gestures";
    //    private int baseSystemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
//            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
//            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;
    private int baseSystemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION;
    private int lastSystemUiVis;
    private Runnable navHider = new Runnable() {
        @Override
        public void run() {
            setNavVisibility(false);
        }
    };
    private GestureDetectorCompat detector;

    public DetailContent(Context context, AttributeSet attrs) {
        super(context, attrs);
        detector = new GestureDetectorCompat(context, this);
        detector.setOnDoubleTapListener(this);
        setNavVisibility(true);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        Log.i("DetailContent", "onScrollChanged: l" + l + ", t:" + t + ", oldl:" + oldl + ", oldt:" + oldt);
        // When the user scrolls, we hide navigation elements.
        setNavVisibility(false);
    }

    private void setNavVisibility(boolean visible) {
        int newVis = baseSystemUiVisibility;
        if (!visible) {
            newVis |= View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
                    | View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
                    | View.SYSTEM_UI_FLAG_IMMERSIVE;
//            newVis |= View.SYSTEM_UI_FLAG_LOW_PROFILE | View.SYSTEM_UI_FLAG_FULLSCREEN
//                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE;
        }
        final boolean changed = newVis == getSystemUiVisibility();
        Log.i(DEBUG_TAG, "changed:" + changed + ", visible:" + visible);
        Log.i(DEBUG_TAG, "newVis:" + newVis + ", getSystemUiVisibility:" + getSystemUiVisibility());

        // Set the new desired visibility.
        setSystemUiVisibility(newVis);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.i(DEBUG_TAG, "onTouchEvent");
        this.detector.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

    @Override
    public boolean onDown(MotionEvent event) {
        Log.i(DEBUG_TAG, "onDown: " + event.toString());
        return true;
    }

    @Override
    public boolean onFling(MotionEvent event1, MotionEvent event2,
                           float velocityX, float velocityY) {
        Log.i(DEBUG_TAG, "onFling: " + event1.toString() + event2.toString());
        return true;
    }

    @Override
    public void onLongPress(MotionEvent event) {
        Log.i(DEBUG_TAG, "onLongPress: " + event.toString());
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
                            float distanceY) {
        Log.i(DEBUG_TAG, "onScroll: " + e1.toString() + e2.toString());
        return true;
    }

    @Override
    public void onShowPress(MotionEvent event) {
        Log.i(DEBUG_TAG, "onShowPress: " + event.toString());
    }

    @Override
    public boolean onSingleTapUp(MotionEvent event) {
        Log.i(DEBUG_TAG, "onSingleTapUp: " + event.toString());
        return true;
    }

    @Override
    public boolean onDoubleTap(MotionEvent event) {
        Log.i(DEBUG_TAG, "onDoubleTap: " + event.toString());
        return true;
    }

    @Override
    public boolean onDoubleTapEvent(MotionEvent event) {
        Log.i(DEBUG_TAG, "onDoubleTapEvent: " + event.toString());
        return true;
    }

    @Override
    public boolean onSingleTapConfirmed(MotionEvent event) {
        int curVis = getSystemUiVisibility();
        Log.i(DEBUG_TAG, "onSingleTapConfirmed: " + event.toString());
        Log.i(DEBUG_TAG, "curVis:" + curVis + ", HIDE_NAVIGATION:" + (curVis & View.SYSTEM_UI_FLAG_HIDE_NAVIGATION));
        setNavVisibility((curVis & View.SYSTEM_UI_FLAG_HIDE_NAVIGATION) != 0);
        return true;
    }
}
