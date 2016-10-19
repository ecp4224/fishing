package com.boxtrotstudio.fishing.utils;

import com.boxtrotstudio.fishing.handlers.GameHandler;
import com.google.gson.Gson;

import java.util.Random;

public final class Global {

    public static final Gson GSON = new Gson();
    public static final Random RANDOM = new Random();
    public static GameHandler GAME;

    public static float rand(float min, float max) {
        return RANDOM.nextFloat() * (max - min) + min;
    }
}
