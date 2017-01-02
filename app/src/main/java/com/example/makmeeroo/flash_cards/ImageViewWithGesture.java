package com.example.makmeeroo.flash_cards;

import android.content.Context;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.ImageView;

/**
 * Created by vig on 1/1/17.
 */
public class ImageViewWithGesture extends ImageView implements GestureDetector.OnGestureListener {

    public ImageViewWithGesture(Context context) {
        super(context);
    }

//    public ImageViewWithGesture(Context context, AttributeSet attrs) {
//        super(context, attrs);
//    }

    @Override
    public boolean onDown(MotionEvent e) {
        Log.d("flash_cards", " imageviewwithGesture onDown detected");
        return false;
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
