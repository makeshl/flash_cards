package com.example.makmeeroo.flash_cards;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

/**
 * Created by vig on 1/1/17.
 */
public class ImageViewWithGesture extends ImageView implements GestureDetector.OnGestureListener {

    MainActivity pMainActivity;
    private float x1, y1; // coordinates at start of gesture (i.e. at onDown)
    //private int swipeDet = 0; // 0 not a swipe, 1 right, 2 left,
    static final int MIN_DIST = 150; // px
    static final int MAX_TIME = 400; // ms
    static final int SWIPE_NONE = 0;
    static final int SWIPE_FWD = 1;
    static final int SWIPE_BACK = 2;

    public void setMainActivity (MainActivity activity) { // set function
        pMainActivity = activity;
    }

    public ImageViewWithGesture(Context context) {
        super(context);
        init(context);
    }

    public ImageViewWithGesture(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ImageViewWithGesture(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    private void init(Context context) {
        // placeholder for init operations
        setOnTouchListener(new View.OnTouchListener() {
           @Override
           public boolean onTouch(View v, MotionEvent event) {
               // TODO
               int action, pointerCount, i;
               action = event.getAction();
               pointerCount = event.getPointerCount();
               Log.d("flash_cards", " imageviewwithGesture onTouch init called with action " + action + " pointerCount " + pointerCount);

               switch (action) {
                   case MotionEvent.ACTION_DOWN:
                       return onDown(event);
                       //break;
                   case MotionEvent.ACTION_UP:
                       return onUp(v, event);
                        // break;
                   case MotionEvent.ACTION_MOVE:
                       //code here
                       break;

                   default:
                       break;
               }

               return false;
           }
        }
        );
    }

    private boolean onUp(View v, MotionEvent e) {
        float x2 = e.getX();
        float xDiff = x2-x1;
        int swipeDet = SWIPE_NONE;
        float t1 = e.getDownTime();
        float t2 = e.getEventTime();
        float tDiff = t2-t1; // ms
        if (tDiff < MAX_TIME) {
            if (xDiff > MIN_DIST) {
                swipeDet = SWIPE_BACK; // map right swipe to back
                //Toast.makeText(getApplicationContext(),"selection was empty; defaulting to " + SelectionList.get(0),Toast.LENGTH_LONG).show();
            }
            if (xDiff < -MIN_DIST) {
                swipeDet = SWIPE_FWD;
            }
            if (pMainActivity!=null) {
                switch (swipeDet) {
                    case SWIPE_NONE:
                        pMainActivity.stopResume(v);
                        break;
                    case SWIPE_FWD:
                        pMainActivity.slideBack(false);
                        break;
                    case SWIPE_BACK:
                        pMainActivity.slideBack(true);
                        break;
                    default: // do nothing
                        Log.d("imageviewwithGesture ", " unsupported value for swipeDet=" + swipeDet );
                        break;
                }
            }
        }

        Log.d("imageviewwithGesture ", " onUp detected x2=" + x2 + " t2=" + t2);
        Log.d("imageviewwithGesture ", " onUp x2-x1=" + (x2-x1) + " t2-t1=" + (t2-t1));
        Log.d("imageviewwithGesture ", " swipeDet=" + swipeDet );

        return true;
    }

    @Override
    public boolean onDown(MotionEvent e) {

        x1 = e.getX();
        y1 = e.getY();
        float t1 = e.getDownTime();
        Log.d("flash_cards", " imageviewwithGesture onDown detected x1,y1=("+x1+","+y1+") t1="+t1);
        return true;
    }

    @Override
    public void onShowPress(MotionEvent e) {
        Log.d("flash_cards", " imageviewwithGesture ShowPress detected");
    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        Log.d("flash_cards", " imageviewwithGesture SingleTapUp detected");
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        Log.d("flash_cards", " imageviewwithGesture Scroll detected");
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {
        Log.d("flash_cards", " imageviewwithGesture longPress detected");

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        Log.d("flash_cards", " imageviewwithGesture Fling detected");
        return false;
    }
}
