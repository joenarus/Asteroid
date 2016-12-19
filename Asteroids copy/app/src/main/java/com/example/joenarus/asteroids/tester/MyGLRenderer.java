package com.example.joenarus.asteroids.tester;

import android.graphics.BitmapFactory;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;
import android.os.SystemClock;
import android.util.Log;

import com.example.joenarus.asteroids.Entities.Entity;
import com.example.joenarus.asteroids.Entities.Player;
import com.example.joenarus.asteroids.GameDataModel;
import com.example.joenarus.asteroids.MyApplication;
import com.example.joenarus.asteroids.R;
import com.example.joenarus.asteroids.Sprite;

import java.util.ArrayList;
import java.util.List;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Created by JoeNarus on 12/18/16.
 */
public class MyGLRenderer implements GLSurfaceView.Renderer {

    private final float[] mProjectionMatrix = new float[16];
    private final float[] mViewMatrix = new float[16];
    Player player;
    public List<Sprite> sprites;

    @Override
    public void onSurfaceCreated(GL10 unused, EGLConfig config) {
        // Set the background frame color
        sprites = new ArrayList<>();
        GLES20.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        player = new Player(9, new Sprite());
        player.sprite.setTexture( BitmapFactory.decodeResource(MyApplication.getContext().getResources(), R.drawable.playersprite));
        // Sprite asteroid = new Sprite();
        // asteroid.setTexture(BitmapFactory.decodeResource(MyApplication.getContext().getResources(), R.drawable.asteroid));
        sprites.add(player.sprite);
        // sprites.add(asteroid);


        // asteroid.prev_x = 2.0f;
    }

    public static int loadShader(int type, String shaderCode){

        int shader = GLES20.glCreateShader(type);

        // add the source code to the shader and compile it
        GLES20.glShaderSource(shader, shaderCode);
        GLES20.glCompileShader(shader);

        if(type == GLES20.GL_VERTEX_SHADER) {
            String vertexShaderCompileLog = GLES20.glGetShaderInfoLog(shader);
            Log.i("VERTEX SHADER", "Output: " + vertexShaderCompileLog);
        }
        else {
            String fragmentShaderCompileLog = GLES20.glGetShaderInfoLog(shader);
            Log.i("FRAGMENT SHADER", "Output: " + fragmentShaderCompileLog);
        }

        return shader;
    }

    @Override
    public void onDrawFrame(GL10 unused) {

        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);
        float[] temp = player.sprite.getmMVPMatrix();
        Matrix.setLookAtM(mViewMatrix, 0, 0, 0, -3, 0f, 0f, 0f, 0f, 1.0f, 0.0f);
        Matrix.multiplyMM(temp, 0, mProjectionMatrix, 0, mViewMatrix, 0);

        for (Sprite e: sprites) {
            e.update();
        }
    }

    @Override
    public void onSurfaceChanged(GL10 unused, int width, int height) {
        GLES20.glViewport(0, 0, width, height);
        float ratio = (float) width / height;
        // this projection matrix is applied to object coordinates
        // in the onDrawFrame() method
        Matrix.frustumM(mProjectionMatrix, 0, -ratio, ratio, -1, 1, 3, 7);
    }

}