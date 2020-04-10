package com.example.snake;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.SurfaceHolder;
import android.view.View;

import androidx.core.view.MotionEventCompat;

import static android.content.ContentValues.TAG;


public class GameView extends SurfaceView implements SurfaceHolder.Callback {
    private MainThread thread;
    private Body head;
    private Object DEBUG_TAG;

    private boolean screenTouched = false;
    private float initX, initY, endX, endY;

    public GameView(Context context){
        super(context);

        head = new Body();

        getHolder().addCallback(this);
        thread = new MainThread(getHolder(), this);
        setFocusable(true);
        this.setKeepScreenOn(true);

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder){
        thread.setRunning(true);
        thread.start();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        boolean retry = true;
        while (retry) {
            try {
                thread.setRunning(false);
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            retry = false;
        }
    }
    public void update(){
        head.Move();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                if(!screenTouched){
                    screenTouched = true;
                    initX = event.getX();
                    initY = event.getY();
                    Log.d(TAG, "Recieved a touch at: ("+initX+", "+initY+")");
                }
                return true;
            case MotionEvent.ACTION_UP:
                screenTouched = false;
                endX = event.getX();
                endY = event.getY();
                Log.d(TAG, "End touch at: ("+endX+", "+endY+")");
                return false;
        }
        return super.onTouchEvent(event);
    }

    @Override
    public void draw(Canvas canvas){
        super.draw(canvas);
        if(canvas != null){
            canvas.drawColor(Color.WHITE);
            Paint paint = new Paint();
            paint.setColor(Color.rgb(250, 0 , 0));
            canvas.drawRect(head.getRect(), paint);
        }
    }
}
