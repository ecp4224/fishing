package com.boxtrotstudio.fishing.core.logic;

public interface Handler {
    void start();

    void tick();

    void resume();

    void pause();
}
