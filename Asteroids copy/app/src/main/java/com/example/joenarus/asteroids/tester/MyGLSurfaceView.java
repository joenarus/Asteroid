package com.example.joenarus.asteroids.tester;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.opengl.GLSurfaceView;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import com.example.joenarus.asteroids.Entities.Player;
import com.example.joenarus.asteroids.MyApplication;
import com.example.joenarus.asteroids.R;

/**
 * Created by JoeNarus on 12/5/16.
 */
public class MyGLSurfaceView extends GLSurfaceView {

    public class WorkerThread extends Thread{

        private volatile boolean stopped = false;

        @Override
        public void run(){
            super.run();
            while(!stopped){
                if(button_pressed) {
                    mRenderer.setAngle(
                            mRenderer.getAngle() + turn_dir);
                }
            }
        }

        public void stop_run(){
            stopped = true;
        }
    }

    WorkerThread thread;

    private final MyGLRenderer mRenderer;
    private float mPreviousX = 0;
    private float mPreviousY = 0;
    boolean button_pressed = false;
    private int xPixels;
    private int yPixels;
    private float turn_dir = 0;


    public MyGLSurfaceView(Context context) {
        super(context);
        setEGLContextClientVersion(2);

        mRenderer = new MyGLRenderer();

        // Set the Renderer for drawing on the GLSurfaceView
        setRenderer(mRenderer);
    }

    public MyGLSurfaceView(Context context, int x, int y) {
        super(context);
        setEGLContextClientVersion(2);

        mRenderer = new MyGLRenderer();
        xPixels = x;
        yPixels = y;

        // Set the Renderer for drawing on the GLSurfaceView
        setRenderer(mRenderer);
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {

        float x = e.getX();
        float y = e.getY();

        float temp_y = ((y/(xPixels/2)) - 1) * -1; //OpenGL coordinates
        float temp_x = (x/(yPixels/2)) - 1; //OpenGL coordinates
        System.out.println("ANGLE: " + mRenderer.getAngle());
        if(e.getAction() == MotionEvent.ACTION_DOWN) {
                if (temp_y <= -.5f) {
                    if (temp_x >= .4f) {

                        if (temp_x >= .4f && temp_x <= .7f) {
                            turn_dir = -10;
                        }
                        else if (temp_x > .7f) {
                            turn_dir = 10;

                        }
                        mRenderer.setAngle(
                                mRenderer.getAngle() + turn_dir);
                    } else if (temp_x <= -.4f) {
                        if (temp_x <= -.4f && temp_x >= -.7f) {
                            mRenderer.y_move += .01f;
                            mRenderer.x_move += .01f;

                        } else if (temp_x < -.7f) {
                            //FIRE
                        }
                    }

                    requestRender();
                }
        }

        else if(e.getAction() == MotionEvent.ACTION_UP) {

        }

        return super.onTouchEvent(e);

    }

}
