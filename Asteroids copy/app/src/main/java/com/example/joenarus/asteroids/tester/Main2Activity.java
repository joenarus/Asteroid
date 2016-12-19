package com.example.joenarus.asteroids.tester;

import android.app.Activity;
import android.opengl.GLSurfaceView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import com.example.joenarus.asteroids.GameDataModel;
import com.example.joenarus.asteroids.Sprite;

import java.util.ArrayList;
import java.util.List;

public class Main2Activity extends Activity {
    private GLSurfaceView mGLView;
    List<Sprite> sprites = new ArrayList<Sprite>();
    GameDataModel game;
    int height;
    int width;

    public void getDispVals() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        height = displayMetrics.heightPixels;
        width = displayMetrics.widthPixels;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getDispVals();
        game = GameDataModel.getInstance();
        mGLView = new MyGLSurfaceView(this, height, width);
        setContentView(mGLView);
    }
}
