package com.example.joenarus.asteroids;

import android.app.Activity;

import com.example.joenarus.asteroids.Entities.Asteroid;
import com.example.joenarus.asteroids.Entities.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by JoeNarus on 12/2/16.
 */
public class GameDataModel {

    public static GameDataModel Instance;
    Activity gameAct;
    public int score;
    public int lives;
    Player player;

    public List<Asteroid> asteroids;

    public static GameDataModel getInstance() {
        if(Instance == null) {
            Instance = new GameDataModel();
        }
        return Instance;
    }

    private GameDataModel() {
        asteroids = new ArrayList<Asteroid>();
        score = 0;
        player = Player.getInstance();
    }

    public void setGameAct(Activity activity) {
        gameAct = activity;
    }

    public void GameOver() {

    }

    public void newRound(int asteroids) {

    }

    public void loseLife() {
        if(lives == 0) {
            GameOver();

        }
        else
            lives--;
    }

    public boolean saveProgress() {
        //TODO: WRITE JSON SAVE OF GAME DATA.


        return true;
    }

}
