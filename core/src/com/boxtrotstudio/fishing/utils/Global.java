package com.boxtrotstudio.fishing.utils;

import com.boxtrotstudio.fishing.handlers.GameHandler;
import com.google.gson.Gson;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;

public final class Global {

    public static final Gson GSON = new Gson();
    public static final Random RANDOM = new Random();
    public static GameHandler GAME;
}
