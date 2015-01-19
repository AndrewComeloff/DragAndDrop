package com.example.draganddrop;

import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.RelativeLayout;

/**
 * Created by komelov on 19.01.2015.
 * <br>Dragging menu from bottom to top.
 */
public class DragBottomMenuControl implements View.OnTouchListener {

    private final String TAG = getClass().getSimpleName();
    private View mTouchView;
    private View mRootArea;
    private View mContentArea;

    private int yDelta;
    private int viewHeight, viewWidth;
    private int rootHeight;
    private boolean isMove;
    private boolean isOpen;

    private long startTimeDrag;
    private int startPositionDrag;
    private final int MAX_DRAG_TIME = 3000;

    public DragBottomMenuControl(final View touchView, View rootArea, View contentArea) {
        this.mTouchView = touchView;
        this.mRootArea = rootArea;
        this.mContentArea = contentArea;

        touchView.setOnTouchListener(this);

        mRootArea.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            boolean mMeasured;
            @Override
            public void onGlobalLayout() {
                if (!mMeasured) {
                    rootHeight = mRootArea.getHeight();
                    viewHeight = touchView.getHeight();
                    RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, rootHeight);
//                    mContentArea.setLayoutParams(params);
                    mMeasured = true;
                }
            }
        });
    }

    public static abstract interface onOpenListener {
        public abstract void onOpened(boolean isOpen);
    }
    public void setOnOpenListener(onOpenListener listener){
        openListener = listener;
    };
    private onOpenListener openListener;

    public boolean isOpened(){
        return isOpen;
    }
    public void setOpened(boolean smooth){

    }

    @Override
    public boolean onTouch(View view, MotionEvent event) {
        final int X = (int) event.getRawX();
        final int Y = (int) event.getRawY();

        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                RelativeLayout.LayoutParams lParams = (RelativeLayout.LayoutParams) mContentArea.getLayoutParams();
//                _xDelta = X - lParams.leftMargin;
                yDelta = Y - lParams.topMargin;
                startTimeDrag = System.currentTimeMillis();
                startPositionDrag = Y - yDelta;
                isMove = false;
                break;
            case MotionEvent.ACTION_UP:
                if(!isMove){
                    Log.d(TAG, "push dragging view");
                    break;
                }


                animationMove(rootHeight, checkDirectionUp(rootHeight, startPositionDrag, Y - yDelta, startTimeDrag, System.currentTimeMillis()));


//                if((rootHeight - viewHeight)/2 < Y - yDelta) Toast.makeText(MainActivity.this, "down", Toast.LENGTH_SHORT).show();
//                else Toast.makeText(MainActivity.this, "up", Toast.LENGTH_SHORT).show();
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                break;
            case MotionEvent.ACTION_POINTER_UP:
                break;
            case MotionEvent.ACTION_MOVE:
                isMove = true;
                RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) mContentArea.getLayoutParams();
                // drag border
                if(Y - yDelta < 0) layoutParams.topMargin = 0;
                else if(rootHeight - viewHeight < Y - yDelta) layoutParams.topMargin = rootHeight - viewHeight;
                else layoutParams.topMargin = Y - yDelta;

                mContentArea.setLayoutParams(layoutParams);
                break;
        }
        mRootArea.invalidate();
        return true;
    }

    private boolean checkDirectionUp(int rootHeight, int startPosition, int endPosition, long startTime, long endTime) {
        // fast swipe
        if(endTime - startTime < MAX_DRAG_TIME){
            // directions
            if(startPosition > endPosition){ // UP
                Log.d(TAG, "up");
                return true;
            } else { // DOWN
                Log.d(TAG, "down");
                return false;
            }
        } else { // slow swipe
            if(endPosition < rootHeight/2){ // UP
                Log.d(TAG, "up");
                return true;
            } else { // DOWN
                Log.d(TAG, "down");
                return false;
            }
        }
    }

    private void animationMove(int rootHeight, boolean up){
//        TranslateAnimation anim = new TranslateAnimation(0, amountToMoveRight, 0, amountToMoveDown);
//        anim.setDuration(1000);
//
//        anim.setAnimationListener(new TranslateAnimation.AnimationListener() {
//
//            @Override
//            public void onAnimationStart(Animation animation) { }
//
//            @Override
//            public void onAnimationRepeat(Animation animation) { }
//
//            @Override
//            public void onAnimationEnd(Animation animation)
//            {
//                FrameLayout.LayoutParams params = (FrameLayout.LayoutParams)view.getLayoutParams();
//                params.topMargin += amountToMoveDown;
//                params.leftMargin += amountToMoveRight;
//                view.setLayoutParams(params);
//            }
//        });
//
//        view.startAnimation(anim);
    }
}
