package com.example.joenarus.asteroids;

import android.content.pm.ActivityInfo;
import android.graphics.BitmapFactory;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class MainActivity extends AppCompatActivity implements GLSurfaceView.Renderer, View.OnTouchListener {

    List<Sprite> sprites = new ArrayList<Sprite>();

    GLSurfaceView surfaceView;

    GameDataModel game;

    Date _gameLoopLastRunDate = null;
    float angle = 0.0f;

    private final float[] mMVPMatrix = new float[16];
    private final float[] mProjectionMatrix = new float[16];
    private final float[] mViewMatrix = new float[16];
    private float[] mRotationMatrix = new float[16];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        if(getIntent().hasExtra("New Game")) {
        }

//        game = new GameDataModel();
//        game.setGameAct(this);

        surfaceView = new GLSurfaceView(this);
        surfaceView.setEGLContextClientVersion(2);
        surfaceView.setEGLConfigChooser(8,8,8,8,0,0);
        surfaceView.setOnTouchListener(this);
        surfaceView.setRenderer(this);

        if(_gameLoopLastRunDate == null) {
            _gameLoopLastRunDate = new Date();
        }
        Date now = new Date();
        float elapsedTime = (float)(now.getTime() - _gameLoopLastRunDate.getTime())/1000.0f;
        _gameLoopLastRunDate = now;

        angle = 0.009f * (elapsedTime);

        Sprite player = new Sprite();
        player.setWidth(.2f);
        player.setHeight(.25f);
        player.setTexture( BitmapFactory.decodeResource(getResources(),R.drawable.texture));
        player.setCenterX(player.getCenterX() + .01f);
        player.setCenterY(player.getCenterY() + .01f);

        sprites.add(player);

        setContentView(surfaceView);


//  + player.VelocityY * elapsedTime
//        asteroid.setCenterX(asteroid.getCenterX() + asteroid.VelocityX * elapsedTime);
//        asteroid.setCenterY(asteroid.getCenterY() + asteroid.VelocityY * elapsedTime);

//        float betweenVectorX = player.getCenterX() - asteroid.getCenterX();
//        float betweenVectorY = player.getCenterY() - asteroid.getCenterY();
//        float betweenVectorLength = (float)Math.sqrt(betweenVectorX * betweenVectorX + betweenVectorY * betweenVectorY);
//
//        boolean collided = betweenVectorLength < (player.getWidth()/2 + player.getWidth()/2);
    }

    @Override
    public void onSurfaceCreated(GL10 gl10, EGLConfig eglConfig) {
        GLES20.glClearColor(0.0f,0.0f, 0.0f, 1.0f);


    }

    @Override
    public void onSurfaceChanged(GL10 gl10, int i, int i1) {
        GLES20.glViewport(0, 0, i, i1);

        float ratio = (float) i / i1;

        // this projection matrix is applied to object coordinates
        // in the onDrawFrame() method
        Matrix.frustumM(mProjectionMatrix, 0, -ratio, ratio, -1, 1, 3, 7);
    }

    @Override
    public void onDrawFrame(GL10 gl10) {
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);
        float[] scratch = new float[16];
        long time = SystemClock.uptimeMillis() % 4000L;
        float angle = 0.090f * ((int) time);
        Matrix.setRotateM(mRotationMatrix, 0, angle, 0, 0, -1.0f);

        Matrix.multiplyMM(scratch, 0, mMVPMatrix, 0, mRotationMatrix, 0);

        Sprite a = sprites.get(0);
        a.setCenterX((a.getCenterX() + 0.001f));

        for(Sprite s : sprites) {
            s.draw(scratch);
        }
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        System.out.println(motionEvent.getX());
        return false;
    }
}
