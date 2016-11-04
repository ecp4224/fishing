package com.boxtrotstudio.fishing.utils;

import com.boxtrotstudio.fishing.handlers.GameHandler;
import com.google.gson.Gson;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public final class Global {

    public static boolean SIMULATED = false;
    public static final Gson GSON = new Gson();
    public static final Random RANDOM = new Random();
    public static GameHandler GAME;

    public static long rand(long min, long max) {
        return ThreadLocalRandom.current().nextLong(min, max);
    }

    public static float rand(float min, float max) {
        return RANDOM.nextFloat() * (max - min) + min;
    }

    public static int rand(int min, int max) {
        return ThreadLocalRandom.current().nextInt(min, max);
    }
}
