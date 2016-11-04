package com.boxtrotstudio.fishing.core.game.fishing.worlds;

import com.boxtrotstudio.fishing.core.game.fishing.World;
import com.boxtrotstudio.fishing.handlers.GameHandler;

public abstract class BaseWorld implements World {
    protected float baseMultiplier = 1f;

    private float multiplier;

    @Override
    public void load(GameHandler handler) {
        multiplier = baseMultiplier;
    }

    @Override
    public float catchMultiplier() {
        return multiplier;
    }

    @Override
    public void increaseMultiplierBy(float add) {
        multiplier += add;
    }

    @Override
    public void decreaseMultiplierBy(float remove) {
        multiplier -= remove;
    }

    @Override
    public void doubleMultiplier() {
        multiplier *= 2f;
    }

    @Override
    public void halfMultiplier() {
        multiplier /= 2f;
    }

    @Override
    public void resetMultiplier() {
        multiplier = baseMultiplier;
    }
}
