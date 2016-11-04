package com.boxtrotstudio.fishing.core.game.fishing;

import com.badlogic.gdx.scenes.scene2d.utils.Drawable;

public abstract class ShopItem {
    protected String name;
    protected long cost;
    protected Drawable image;
    protected String description;
    protected float imageWidth = 50f;
    protected float imageHeight = 50f;
    protected boolean persistent = true;

    public abstract void onBuy();

    public long getCost() {
        return cost;
    }

    public Drawable getImage() {
        return image;
    }

    public String getDescription() {
        return description;
    }

    public String getName() {
        return name;
    }

    public float getImageWidth() {
        return imageWidth;
    }

    public float getImageHeight() {
        return imageHeight;
    }

    public boolean doesOwn() {
        return false;
    }

    public boolean isPersistent() {
        return persistent;
    }
}
