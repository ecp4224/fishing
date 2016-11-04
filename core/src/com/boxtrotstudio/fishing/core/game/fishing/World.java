package com.boxtrotstudio.fishing.core.game.fishing;

import com.badlogic.gdx.graphics.Texture;
import com.boxtrotstudio.fishing.handlers.GameHandler;

public interface World {
    int worldNumber();

    Texture backgroundTexture();

    void load(GameHandler handler);

    ShopItem[] buyableItems();

    float catchMultiplier();

    void increaseMultiplierBy(float add);

    void decreaseMultiplierBy(float remove);

    void doubleMultiplier();

    void halfMultiplier();

    void resetMultiplier();
}
