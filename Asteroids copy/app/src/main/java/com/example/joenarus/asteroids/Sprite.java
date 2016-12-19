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

    private static String vertexShaderSource = "" +
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

    private static String fragmentShaderSource = "" +
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

    private float _translateX = 0.0f;
    private float _translateY = 0.0f;
    private float _scaleX = .5f;
    private float _scaleY = .5f;
    private Bitmap _texture = null;
    private int _textureName = -1;
    private FloatBuffer vertexBuffer;
    private FloatBuffer textureBuffer;

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

        ByteBuffer quadByteBuffer = ByteBuffer.allocateDirect(_quadGeometry.length * 4);
        quadByteBuffer.order(ByteOrder.nativeOrder());
        vertexBuffer = quadByteBuffer.asFloatBuffer();
        vertexBuffer.put(_quadGeometry);
        vertexBuffer.rewind();

        ByteBuffer quadTextureCoordinateByteBuffer = ByteBuffer.allocateDirect(_quadtexture.length * 4);
        quadTextureCoordinateByteBuffer.order(ByteOrder.nativeOrder());
        textureBuffer = quadTextureCoordinateByteBuffer.asFloatBuffer();
        textureBuffer.put(_quadtexture);
        textureBuffer.rewind();

        int vertexShader = MyGLRenderer.loadShader(GLES20.GL_VERTEX_SHADER, vertexShaderSource);

        int fragmentShader = MyGLRenderer.loadShader(GLES20.GL_FRAGMENT_SHADER, fragmentShaderSource);

        _program = GLES20.glCreateProgram();
        GLES20.glAttachShader(_program, vertexShader);
        GLES20.glAttachShader(_program, fragmentShader);
        GLES20.glLinkProgram(_program);
    }


    public void draw(float[] mvpMatrix) {
        GLES20.glUseProgram(_program);
        mPositionHandle = GLES20.glGetAttribLocation(_program, "vPosition");
        textureHandle = GLES20.glGetAttribLocation(_program,"textureCoordinate");
        mMVPMatrixHandle = GLES20.glGetUniformLocation(_program, "uMVPMatrix");

        GLES20.glEnableVertexAttribArray(mPositionHandle);
        GLES20.glEnableVertexAttribArray(textureHandle);

        GLES20.glVertexAttribPointer(mPositionHandle, 2,
                GLES20.GL_FLOAT, false,
                0, vertexBuffer);

        GLES20.glVertexAttribPointer(textureHandle, 2,
                GLES20.GL_FLOAT, false,
                0, textureBuffer);

        if(_textureName <= 0) {
            GLES20.glUniform1i(_textureUnitLocation, 0);
            GLES20.glEnable(GLES20.GL_TEXTURE_2D);
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
        GLES20.glDisableVertexAttribArray(mPositionHandle);
        GLES20.glDisableVertexAttribArray(textureHandle);
    }
}
