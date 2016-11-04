package com.boxtrotstudio.fishing.core.game.fishing.worlds;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.boxtrotstudio.fishing.Fishing;
import com.boxtrotstudio.fishing.core.game.Entity;
import com.boxtrotstudio.fishing.core.game.animations.Animation;
import com.boxtrotstudio.fishing.core.game.entities.Cloud;
import com.boxtrotstudio.fishing.core.game.fishing.ShopItem;
import com.boxtrotstudio.fishing.core.game.fishing.World;
import com.boxtrotstudio.fishing.core.game.fishing.shop.*;
import com.boxtrotstudio.fishing.handlers.GameHandler;
import com.boxtrotstudio.fishing.utils.Global;

public class TestWorld extends BaseWorld {
    public Entity water;

    @Override
    public int worldNumber() {
        return 0;
    }

    @Override
    public Texture backgroundTexture() {
        return Fishing.ASSETS.get("backgrounds/backdrop.png");
    }

    @Override
    public void load(GameHandler handler) {
        super.load(handler);

        Entity dock = Entity.fromImage("backgrounds/dock.png");
        water = Entity.fromImage("backgrounds/water.png");

        dock.setZIndex(-3);
        water.setZIndex(-5);

        handler.spriteScene.addEntity(dock);
        handler.spriteScene.addEntity(water);


        int count = 20;

        for (int i = 0; i < count; i++) {
            Cloud cloud = new Cloud();
            cloud.setX(Global.rand(-300f, 2000f));
            cloud.setY(720 - (80 + Global.rand(-40f, 60f)));

            handler.spriteScene.addEntity(cloud);
        }

        Entity cat = Entity.fromImage("sprites/cat.png");
        Animation.fromFile(Gdx.files.internal("sprites/cat.json"), cat);
        cat.setCenter(200f, 348f);
        cat.setScale(2f);

        cat.onClick(new Runnable() {

            @Override
            public void run() {
                Fishing.GAME.showShop();
            }
        });

        handler.spriteScene.addEntity(cat);


        Entity fisherman = Entity.fromImage("sprites/fisherman_side.png");
        fisherman.setCenter(490f, 400f);
        fisherman.setScale(0.125f * 1.5f);
        fisherman.setZIndex(-4);

        handler.spriteScene.addEntity(fisherman);
    }

    @Override
    public ShopItem[] buyableItems() {
        return new ShopItem[] {
            new FamilyWorker(),
                new SmartHook(),
                new CollegeCat(),
                new RoseGoldHook(),
                new ProfessionalFishingRod(),
                new Fiagra()
        };
    }
}
