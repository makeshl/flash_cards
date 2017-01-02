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

    @Override
    public boolean onDown(MotionEvent e) {
        Log.d("flash_cards", " imageviewwithGesture onDown detected");
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
