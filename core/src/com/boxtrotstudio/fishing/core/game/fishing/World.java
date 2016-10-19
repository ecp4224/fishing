package com.boxtrotstudio.fishing.core.game.fishing;

import com.badlogic.gdx.graphics.Texture;
import com.boxtrotstudio.fishing.handlers.GameHandler;

public interface World {
    int worldNumber();

    Texture backgroundTexture();

    void load(GameHandler handler);
}
