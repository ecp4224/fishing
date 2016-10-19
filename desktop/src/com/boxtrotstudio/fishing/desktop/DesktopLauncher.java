package com.boxtrotstudio.fishing.desktop;


import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.boxtrotstudio.fishing.Fishing;
import com.boxtrotstudio.fishing.core.logic.Handler;
import com.boxtrotstudio.fishing.handlers.GameHandler;

public class DesktopLauncher {

    public static void main(String[] args) {
        startGame(new GameHandler());
    }

    private static void startGame(Handler handler) {
        Fishing.setDefaultHandler(handler);


        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        //Graphics.DisplayMode dm = LwjglApplicationConfiguration.getDesktopDisplayMode();
        config.title = "Fishing";

        config.width = 1280;
        config.height = 720;

        new LwjglApplication(Fishing.getInstance(), config);
    }
}
