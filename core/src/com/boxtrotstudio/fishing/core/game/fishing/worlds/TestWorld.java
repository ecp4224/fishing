package com.boxtrotstudio.fishing.core.game.fishing.worlds;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.boxtrotstudio.fishing.Fishing;
import com.boxtrotstudio.fishing.core.game.Entity;
import com.boxtrotstudio.fishing.core.game.animations.Animation;
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

        Entity cat = Entity.fromImage("sprites/cat.png");
        Animation.fromFile(Gdx.files.internal("sprites/cat.json"), cat);
        cat.setCenter(200f, 350f);
        cat.setScale(2f);

        cat.onClick(new Runnable() {

            @Override
            public void run() {
                Fishing.game.showShop();
            }
        });

        handler.spriteScene.addEntity(cat);
    }
}
