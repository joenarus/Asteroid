package com.example.joenarus.asteroids.Entities;

import android.opengl.GLSurfaceView;
import android.view.MotionEvent;

import com.example.joenarus.asteroids.Sprite;
import com.example.joenarus.asteroids.tester.MyGLRenderer;

/**
 * Created by JoeNarus on 12/18/16.
 */
public class Player extends Entity {

    public static int lives;
    public int velocityX;
    public int velocityY;
    public float angle; //radians

    public boolean isShielded = false;

    public Player(int _lives, Sprite ship) {
        super(0,0, ship);
        lives = _lives;
        velocityX = 0;
        velocityY = 0;
    }

    public void shoot() {

    }

    public void rotateRight(MyGLRenderer myGLRenderer) {
      // myGLRenderer.direction = 1.0f;
    }

    public void rotateLeft(MyGLRenderer myGLRenderer) {
        //myGLRenderer.direction = -1.0f;
    }



}
