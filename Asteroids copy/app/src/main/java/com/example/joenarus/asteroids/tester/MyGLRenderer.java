package com.example.joenarus.asteroids.tester;

import android.graphics.BitmapFactory;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;
import android.os.SystemClock;
import android.util.Log;

import com.example.joenarus.asteroids.MyApplication;
import com.example.joenarus.asteroids.R;
import com.example.joenarus.asteroids.Sprite;

import java.util.ArrayList;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Created by JoeNarus on 12/18/16.
 */
public class MyGLRenderer implements GLSurfaceView.Renderer {

    ArrayList<Sprite> sprites = new ArrayList<Sprite>();
    Sprite player;
    private final float[] mMVPMatrix = new float[16];
    private final float[] mProjectionMatrix = new float[16];
    private final float[] mViewMatrix = new float[16];
    private final float[] mModelMatrix = new float[16];
    private final float[] mScalingMatrix = new float[16];
    private float[] mTempMatrix = new float[16];
    private float[] mRotationMatrix = new float[16];


    public float direction = 1.0f; //1.0f for right, -1.0f for left
    public float x_move = 0.0f;
    public float prev_x = 0.0f;
    public float y_move = 0.0f;
    public float prev_y = 0.0f;
    public float scaleFactorX = 0.3f;
    public float scaleFactorY = 0.3f;

    public volatile float mAngle;

    public float getAngle() {
        return mAngle;
    }

    public void setAngle(float angle) {
        mAngle = angle;

    }



    @Override
    public void onSurfaceCreated(GL10 unused, EGLConfig config) {
        // Set the background frame color
        GLES20.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);

        player = new Sprite();
        player.setTexture( BitmapFactory.decodeResource(MyApplication.getContext().getResources(), R.drawable.playersprite));

        sprites.add(player);


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
        Matrix.setLookAtM(mViewMatrix, 0, 0, 0, -3, 0f, 0f, 0f, 0f, 1.0f, 0.0f);
        Matrix.setIdentityM(mModelMatrix, 0); // initialize to identity matrix
        Matrix.setIdentityM(mScalingMatrix, 0);
        Matrix.setIdentityM(mRotationMatrix, 0);
        // Calculate the projection and view transformation

        prev_x = prev_x - x_move * (float)Math.sin(mAngle/180* Math.PI);
        prev_y = prev_y + y_move * (float)Math.cos(mAngle/180* Math.PI);

        if(prev_x <= (2*-1.0f /scaleFactorX) || prev_x >= (2* 1.0f / scaleFactorX)) {
            prev_x *= -1;
        }

        else if(prev_y >= (1.0f/scaleFactorY) || prev_y <= (-1.0f/scaleFactorY)) {
            prev_y *= -1;
        }


        Matrix.multiplyMM(mMVPMatrix, 0, mProjectionMatrix, 0, mViewMatrix, 0);
        Matrix.scaleM(mModelMatrix,0, scaleFactorX, scaleFactorY, 0f);
        Matrix.translateM(mModelMatrix, 0, prev_x, prev_y, 0); // translation to the left

        Matrix.setRotateM(mRotationMatrix, 0, mAngle, 0, 0, direction);

        mTempMatrix = mModelMatrix.clone();
        Matrix.multiplyMM(mModelMatrix, 0, mTempMatrix, 0, mRotationMatrix, 0);

        mTempMatrix = mMVPMatrix.clone();
        Matrix.multiplyMM(mMVPMatrix, 0, mTempMatrix, 0, mModelMatrix, 0);

        for (Sprite s: sprites) {
            s.draw(mMVPMatrix);
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