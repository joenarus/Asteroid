package com.example.joenarus.asteroids.Entities;

import com.example.joenarus.asteroids.Sprite;

/**
 * Created by JoeNarus on 12/19/16.
 */
public class Entity {
    public float xPos; //centerX
    public float yPos; //centerY
    public Sprite sprite;
    public float velocityX;
    public float velocityY;

    public Entity(float x, float y, Sprite sprite1) {
        xPos = x;
        yPos = y;
        sprite = sprite1;
    }

    public void update(float[] update_matrix) {
        sprite.draw(update_matrix);
    }

}
