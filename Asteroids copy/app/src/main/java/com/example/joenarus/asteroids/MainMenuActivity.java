package com.example.joenarus.asteroids;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.joenarus.asteroids.tester.Main2Activity;


public class MainMenuActivity extends AppCompatActivity implements View.OnClickListener {

    Button newGame;
    Button highScores;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        LinearLayout rootLayout = new LinearLayout(this);
        rootLayout.setOrientation(LinearLayout.VERTICAL);
        rootLayout.setBackgroundColor(Color.BLACK);
        TextView Asteroids = new TextView(this);
        Asteroids.setText("ASTEROIDS");
        Asteroids.setTextSize(40f);
        newGame = new Button(this);
        newGame.setText("NEW GAME");
        System.out.println(newGame.getFontFeatureSettings());
        newGame.setOnClickListener(this);
        highScores = new Button(this);
        highScores.setText("HIGHSCORES");
        highScores.setOnClickListener(this);
        rootLayout.addView(newGame, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0,1));
        rootLayout.addView(highScores, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0,1));
        setContentView(rootLayout);
    }

    @Override
    public void onClick(View view) {
        if(view == newGame) {
            Intent showDrawingIntent = new Intent();
            showDrawingIntent.setClass(this, Main2Activity.class);
            showDrawingIntent.putExtra("New Game", 9);
            startActivityForResult(showDrawingIntent, 2);
        }
        if(view == highScores) {
            Intent showDrawingIntent = new Intent();
            showDrawingIntent.setClass(this, HighscoreActivity.class);
            showDrawingIntent.putExtra("Check Scores", 1);
            startActivityForResult(showDrawingIntent, 2);
        }
    }
}
