package com.boxtrotstudio.fishing.core.render.scene;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import org.jetbrains.annotations.NotNull;

public interface Scene {
    void init();

    void render(@NotNull OrthographicCamera camera, @NotNull SpriteBatch batch);

    void dispose();

    boolean isVisible();

    String getName();

    void setVisible(boolean val);

    void setName(String name);

    int requestedOrder();

    int getWidth();

    int getHeight();

    void replaceWith(Scene scene);

    void softReplace(Scene scene);
}
