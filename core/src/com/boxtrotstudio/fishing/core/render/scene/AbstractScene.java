package com.boxtrotstudio.fishing.core.render.scene;

import com.boxtrotstudio.fishing.Fishing;

public abstract class AbstractScene implements Scene {
    private boolean visible = true;
    private String name = "AbstractScene";
    private int order = 0;

    protected int width, height;

    protected boolean wasInit = false;

    public AbstractScene() {
        width = (int) Fishing.getInstance().viewport.getWorldWidth();
        height = (int) Fishing.getInstance().viewport.getWorldHeight();
        /*width = Gdx.graphics.getWidth();
        height = Gdx.graphics.getHeight();*/
    }

    @Override
    public void init() {
        if (wasInit) {
            System.out.println("Already loaded " + getClass().getSimpleName());
            return;
        }
        onInit();
        wasInit = true;
    }

    protected void onInit() { }

    @Override
    public boolean isVisible() {
        return visible;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    @Override
    public int requestedOrder() {
        return order;
    }

    public void requestOrder(int order) {
        this.order = order;
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }

    @Override
    public void replaceWith(Scene scene) {
        Fishing.getInstance().removeScene(this);
        Fishing.getInstance().addScene(scene);
    }

    @Override
    public void softReplace(Scene scene) {
        scene.setVisible(true);
        setVisible(false);
    }
}
