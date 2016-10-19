package com.boxtrotstudio.fishing.core.game.fishing.worlds;

import com.badlogic.gdx.graphics.Texture;
import com.boxtrotstudio.fishing.Fishing;
import com.boxtrotstudio.fishing.core.game.entities.Cloud;
import com.boxtrotstudio.fishing.core.game.fishing.World;
import com.boxtrotstudio.fishing.handlers.GameHandler;
import com.boxtrotstudio.fishing.utils.Global;

public class TestWorld implements World {
    @Override
    public int worldNumber() {
        return 0;
    }

    @Override
    public Texture backgroundTexture() {
        return Fishing.ASSETS.get("backgrounds/test_world.png");
    }

    @Override
    public void load(GameHandler handler) {
        int count = 20;

        for (int i = 0; i < count; i++) {
            Cloud cloud = new Cloud();
            cloud.setX(Global.rand(-300f, 2000f));
            cloud.setY(720 - (80 + Global.rand(-40f, 60f)));

            handler.spriteScene.addEntity(cloud);
        }
    }
}
