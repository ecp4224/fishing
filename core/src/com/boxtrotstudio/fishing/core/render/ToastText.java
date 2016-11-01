package com.boxtrotstudio.fishing.core.render;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.boxtrotstudio.fishing.Fishing;
import com.boxtrotstudio.fishing.core.game.Entity;
import com.boxtrotstudio.fishing.core.logic.Logical;
import com.boxtrotstudio.fishing.utils.Vector2f;

public class ToastText implements Logical {
    private Text sprite;
    private String text;
    private Color color;
    private int fSize = 28;

    private long start;
    private float duration;
    private final float target;

    private Vector2f startPoint;

    public ToastText(Vector2f startPos, String text, float duration) {
        super();
        this.startPoint = startPos;
        this.text = text;
        this.duration = duration;

       this.target = 25;
    }

    public String getToastString() {
        return text;
    }

    public float getToastDuration() {
        return duration;
    }


    public void toast() {
        sprite = new Text(fSize, color, Gdx.files.internal("fonts/TitilliumWeb-Regular.ttf"));
        sprite.setX(startPoint.x);
        sprite.setY(startPoint.y);
        sprite.setAlpha(1f);
        sprite.setText(text);
        Fishing.game.spriteScene.addEntity(sprite);
        Fishing.getInstance().addLogical(this);
        start = System.currentTimeMillis();
    }

    public int getToastFontSize() {
        return fSize;
    }

    public Color getColor() {
        return color;
    }

    public ToastText setColor(Color color) {
        this.color = color;
        return this;
    }

    public ToastText setToastFontSize(int size) {
        this.fSize = size;
        sprite = new Text(fSize, color, Gdx.files.local("fonts/TitilliumWeb-ExtraLight.ttf"));
        return this;
    }

    boolean fadeOut = false;
    float alpha = 0f;
    @Override
    public void tick() {
        if (!fadeOut) {
            sprite.setX(startPoint.getX());

            //float targetAlpha = Entity.ease(0f, 1f, (duration - 200), (System.currentTimeMillis() - (start + 100L)));
            float target = Entity.ease(0f, this.target, duration, (System.currentTimeMillis() - start));
            sprite.setY(startPoint.getY() + target);
            sprite.setAlpha(1f);
            if (target == this.target) {
                fadeOut = true;
                sprite.setAlpha(1f);
                start = System.currentTimeMillis(); //Reuse the start var
            }
        } else {
            alpha = Entity.ease(1f, 0f, 350f, (System.currentTimeMillis() - start));
            sprite.setAlpha(alpha);
        }
    }

    @Override
    public void dispose() {

    }
}
