package com.example.joenarus.asteroids.Entities;

import com.example.joenarus.asteroids.Sprite;

/**
 * Created by JoeNarus on 12/2/16.
 */
public class Asteroid extends Entity {

    public boolean shieldMineral;
    public float radius;

    public Asteroid(float x, float y, Sprite as) {
        super(x,y, as);

    }
}
