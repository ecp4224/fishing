package com.boxtrotstudio.fishing.core.game.entities;

import com.boxtrotstudio.fishing.core.game.Entity;
import com.boxtrotstudio.fishing.utils.Global;

public class Cloud extends Entity {
    public float speed;
    private float startX;

    public Cloud() {
        super("sprites/cloud" + (Global.RANDOM.nextInt(2) + 1) + ".png");
        speed = Global.rand(0.5f, 2.5f);

        setScale(speed * 1.5f, speed * 1.5f);

        speed = Math.max(speed / 20f, 0.08f);
    }

    @Override
    public void onLoad() {
        super.onLoad();
        startX = Global.rand(-500f, -100f);
    }

    @Override
    public void tick() {
        setX(getX() + speed);
        if (getX() > 1280 && Global.RANDOM.nextDouble() < 0.8) {
            setX(startX);
        }
    }
}
