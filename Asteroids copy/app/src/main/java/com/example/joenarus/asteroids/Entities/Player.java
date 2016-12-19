package com.example.joenarus.asteroids.Entities;

import android.opengl.GLSurfaceView;
import android.view.MotionEvent;

import com.example.joenarus.asteroids.Sprite;
import com.example.joenarus.asteroids.tester.MyGLRenderer;

/**
 * Created by JoeNarus on 12/18/16.
 */
public class Player {

    public static Player getInstance() {
       if(Instance == null)
           Instance = new Player(9);
        return Instance;
    }

    public static Player Instance;
    public static int lives;
    public Sprite ship;
    public int velocityX;
    public int velocityY;
    public float angle; //radians

    public boolean isShielded = false;

    private Player(int _lives) {
        lives = _lives;
        velocityX = 0;
        velocityY = 0;
    }

    public void update(float[] update_matrix) {
        ship.draw(update_matrix);
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
