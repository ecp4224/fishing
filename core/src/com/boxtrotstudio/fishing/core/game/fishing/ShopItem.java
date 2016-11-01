package com.boxtrotstudio.fishing.core.game.fishing;

import com.badlogic.gdx.graphics.Texture;

public abstract class ShopItem {
    private String name;
    private int cost;
    private Texture image;
    private String description;


    public String getName() {
        return name;
    }
}
