package com.boxtrotstudio.fishing.core.game.fishing.worlds;

import com.badlogic.gdx.graphics.Texture;
import com.boxtrotstudio.fishing.core.game.fishing.World;
import com.boxtrotstudio.fishing.handlers.GameHandler;

public class TestWorld implements World {
    @Override
    public int worldNumber() {
        return 0;
    }

    @Override
    public Texture backgroundTexture() {
        return null;
    }

    @Override
    public void load(GameHandler handler) {

    }


}
