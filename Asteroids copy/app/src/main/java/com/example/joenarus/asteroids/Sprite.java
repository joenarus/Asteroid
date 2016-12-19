package com.example.joenarus.asteroids;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLES20;
import android.opengl.GLUtils;
import android.opengl.Matrix;
import android.os.SystemClock;
import android.util.Log;

import com.example.joenarus.asteroids.tester.MyGLRenderer;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

/**
 * Created by JoeNarus on 12/5/16.
 */
public class Sprite {


    static private int _program = -1;
    static private int _textureUnitLocation = -1;
    static private int mMVPMatrixHandle = -1;
    static private int mPositionHandle = -1;
    static private int textureHandle = -1;



    static final private float[] _quadGeometry = new float[] {
            -.25f,-.25f,
            -.25f, .25f,
            .25f, -.25f,
            .25f, .25f,
    };

    static final private float[] _quadtexture = new float[] {
            0.0f, 1.0f,
            0.0f, 0.0f,
            1.0f, 1.0f,
            1.0f, 0.0f,
    };




    public float[] getmMVPMatrix() {
        return mMVPMatrix;
    }
    private final static int POSITION_ARRAY = 1;
    private final static int TEXTURE_COORDINATE_ARRAY = 0;
    private final float[] mMVPMatrix = new float[16];
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

    private float _translateX = 0.0f;
    private float _translateY = 0.0f;
    private float _scaleX = .5f;
    private float _scaleY = .5f;
    private Bitmap _texture = null;
    private int _textureName = -1;



    public volatile float mAngle;

    public float getAngle() {
        return mAngle;
    }

    public void setAngle(float angle) {
        mAngle = angle;

    }

    public Bitmap getTexture() {
        return _texture;
    }

    public void setTexture(Bitmap bitmap) {
        _texture = bitmap;
    }

    public float getCenterX() {
        return _translateX;
    }

    public void setCenterX(float x) {
        _translateX = x;
    }

    public float getCenterY() {
        return _translateY;
    }

    public void setCenterY(float y) {
        _translateY = y;
    }

    public float getWidth() {
        return _scaleX;
    }

    public void setWidth(float width) {
        _scaleX = width;
    }

    public float getHeight() {
        return _scaleY;
    }

    public void setHeight(float height) {
        _scaleY = height;
    }

    public Sprite() {

    }


    static private void setup() {
        String vertexShaderSource = "" +
                "\n" +
                " uniform mat4 uMVPMatrix; \n" +
                " attribute vec2 textureCoordinate; \n" +
                " attribute vec4 vPosition; \n" +
                " varying highp vec2 textureCoordinateInterpolated; \n" +
                " \n" +
                " void main() { \n" +
                " \n" +
                //"   gl_Position = vec4(position.x * scale.x + translate.x, position.y * scale.y + translate.y, 1, 1); \n" +
                "   gl_Position = uMVPMatrix * vPosition; \n " +
                "   textureCoordinateInterpolated = textureCoordinate; \n" +
                " } \n" +
                "\n" +
                "\n" +
                "\n";

        String fragmentShaderSource = "" +
                "\n" +
                " varying highp vec2 textureCoordinateInterpolated; \n" +
                " uniform sampler2D textureUnit; \n" +
                "\n" +
                " void main() { \n" +
                "   gl_FragColor = texture2D(textureUnit, textureCoordinateInterpolated); \n" +
                " } \n" +
                "\n" +
                "\n" +
                "\n";
        int vertexShader = MyGLRenderer.loadShader(GLES20.GL_VERTEX_SHADER, vertexShaderSource);
        int fragmentShader = MyGLRenderer.loadShader(GLES20.GL_FRAGMENT_SHADER, fragmentShaderSource);

        _program = GLES20.glCreateProgram();
        GLES20.glAttachShader(_program, vertexShader);
        GLES20.glAttachShader(_program, fragmentShader);
        GLES20.glBindAttribLocation(_program, POSITION_ARRAY, "vPosition");
        GLES20.glBindAttribLocation(_program, TEXTURE_COORDINATE_ARRAY, "textureCoordinate");
        GLES20.glLinkProgram(_program);
        GLES20.glUseProgram(_program);
       // mPositionHandle = GLES20.glGetAttribLocation(_program, "vPosition");
        //textureHandle = GLES20.glGetAttribLocation(_program,"textureCoordinate");

        GLES20.glEnableVertexAttribArray(0);
        GLES20.glEnableVertexAttribArray(1);

        mMVPMatrixHandle = GLES20.glGetUniformLocation(_program, "uMVPMatrix");
        _textureUnitLocation = GLES20.glGetUniformLocation(_program, "textureUnit");


        FloatBuffer vertexBuffer;
        FloatBuffer textureBuffer;

        ByteBuffer quadByteBuffer = ByteBuffer.allocateDirect(_quadGeometry.length * 4);
        quadByteBuffer.order(ByteOrder.nativeOrder());
        vertexBuffer = quadByteBuffer.asFloatBuffer();
        vertexBuffer.put(_quadGeometry);
        vertexBuffer.rewind();

        GLES20.glVertexAttribPointer(POSITION_ARRAY, 2,
                GLES20.GL_FLOAT, false,
                0, vertexBuffer);

        ByteBuffer quadTextureCoordinateByteBuffer = ByteBuffer.allocateDirect(_quadtexture.length * 4);
        quadTextureCoordinateByteBuffer.order(ByteOrder.nativeOrder());
        textureBuffer = quadTextureCoordinateByteBuffer.asFloatBuffer();
        textureBuffer.put(_quadtexture);
        textureBuffer.rewind();

        GLES20.glVertexAttribPointer(TEXTURE_COORDINATE_ARRAY, 2,
                GLES20.GL_FLOAT, false,
                0, textureBuffer);

        GLES20.glUniform1i(_textureUnitLocation, 0);

        GLES20.glEnable(GLES20.GL_TEXTURE_2D);
    }

    public void update() {
        if(_program <= 0) {
            setup();
        }

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

        Matrix.scaleM(mModelMatrix,0, scaleFactorX, scaleFactorY, 0f);
        Matrix.translateM(mModelMatrix, 0, prev_x, prev_y, 0); // translation to the left

        Matrix.setRotateM(mRotationMatrix, 0, mAngle, 0, 0, direction);

        mTempMatrix = mModelMatrix.clone();
        Matrix.multiplyMM(mModelMatrix, 0, mTempMatrix, 0, mRotationMatrix, 0);

        mTempMatrix = mMVPMatrix.clone();
        Matrix.multiplyMM(mMVPMatrix, 0, mTempMatrix, 0, mModelMatrix, 0);

        draw(mMVPMatrix);
    }


    public void draw(float[] mvpMatrix) {

        if(_textureName <= 0) {
            int[] textName = new int[1];
            textName[0] = -1;
            GLES20.glGenTextures(1,textName, 0);
            _textureName = textName[0];
            GLES20.glBindTexture(GLES20.GL_TEXTURE_2D,_textureName);
            GLUtils.texImage2D(GLES20.GL_TEXTURE_2D, 0, _texture, 0);
            GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_LINEAR);
            GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_LINEAR);
        }

        GLES20.glUniformMatrix4fv(mMVPMatrixHandle, 1, false, mvpMatrix, 0);
        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_STRIP, 0, 4);
    }
}
