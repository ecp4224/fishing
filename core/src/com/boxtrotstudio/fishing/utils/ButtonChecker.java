package com.boxtrotstudio.fishing.utils;

import com.badlogic.gdx.Gdx;

import java.util.HashMap;

public class ButtonChecker {
    private static HashMap<Integer, Boolean> states = new HashMap<Integer, Boolean>();

    public static void checkKey(int key, Runnable onPressed, Runnable onRelease) {
        if (!states.containsKey(key)) {
            boolean pressed = Gdx.input.isKeyPressed(key);
            states.put(key, pressed);

            if (pressed)
                onPressed.run();
            return;
        }

        Boolean state = states.get(key);
        boolean pressed = Gdx.input.isKeyPressed(key);

        states.put(key, !state);

        if (pressed && !state && onPressed != null) {
            onPressed.run();
        } else if (!pressed & state && onRelease != null) {
            onRelease.run();
        }
    }
}
