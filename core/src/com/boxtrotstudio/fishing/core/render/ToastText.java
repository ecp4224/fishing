package com.boxtrotstudio.fishing.core.render;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.boxtrotstudio.fishing.Fishing;
import com.boxtrotstudio.fishing.core.game.Entity;
import com.boxtrotstudio.fishing.core.logic.Logical;
import com.boxtrotstudio.fishing.handlers.scenes.SpriteScene;
import com.boxtrotstudio.fishing.utils.Vector2f;

public class ToastText implements Logical {
    private Text sprite;
    private Text shadowSprite;
    private boolean shadow = false;
    private boolean left = false;
    private String text;
    private Color color;
    private int fSize = 28;

    private long start;
    private float duration;
    private float target;

    private Vector2f startPoint;

    public ToastText(Vector2f startPos, String text, float duration) {
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

    public void toast(SpriteScene scene) {
        if (shadow) {
            shadowSprite = new Text(fSize, new Color(0f, 0f, 0f, 0.4f), Gdx.files.internal("fonts/TitilliumWeb-Bold.ttf"));
            shadowSprite.setX(startPoint.x - 2f);
            shadowSprite.setY(startPoint.y - 2f);
            shadowSprite.setText(text);
            scene.addEntity(shadowSprite);
        }

        sprite = new Text(fSize, color, Gdx.files.internal("fonts/TitilliumWeb-Bold.ttf"));
        sprite.setX(startPoint.x);
        sprite.setY(startPoint.y);
        sprite.setAlpha(1f);
        sprite.setText(text);
        scene.addEntity(sprite);

        Gdx.app.postRunnable(new Runnable() {
            @Override
            public void run() {
                Fishing.getInstance().addLogical(ToastText.this);
                start = System.currentTimeMillis();
            }
        });
    }

    public void toast() {
        toast(Fishing.GAME.spriteScene);
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
        sprite = new Text(fSize, color, Gdx.files.local("fonts/TitilliumWeb-Bold.ttf"));
        if (shadow)
            shadowSprite = new Text(fSize, new Color(0f, 0f, 0f, 0.4f), Gdx.files.local("fonts/TitilliumWeb-Bold.ttf"));
        return this;
    }

    boolean fadeOut = false;
    float alpha = 0f;
    @Override
    public void tick() {
        if (!fadeOut) {
            if (!left)
                sprite.setX(startPoint.getX());
            else
                sprite.setY(startPoint.getY());

            float targetAlpha = Entity.ease(0.1f, 1f, duration, (System.currentTimeMillis() - start));
            float target = Entity.ease(0f, this.target, duration, (System.currentTimeMillis() - start));

            if (!left)
                sprite.setY(startPoint.getY() + target);
            else
                sprite.setX(startPoint.getX() - target);

            sprite.setAlpha(targetAlpha);
            if (target == this.target) {
                fadeOut = true;
                sprite.setAlpha(1f);
                if (shadow)
                    shadowSprite.setAlpha(0.4f);

                start = System.currentTimeMillis(); //Reuse the start var
            } else if (shadow) {
                if (!left)
                    shadowSprite.setX(startPoint.getX() - 2f);
                else
                    shadowSprite.setY(startPoint.getY() - 2f);

                if (!left)
                    shadowSprite.setY(startPoint.getY() - 2f + target);
                else
                    shadowSprite.setX(startPoint.getX() - 2f - target);

                targetAlpha = Entity.ease(0.1f, 0.4f, duration, (System.currentTimeMillis() - start));
                shadowSprite.setAlpha(targetAlpha);
            }
        } else {
            alpha = Entity.ease(1f, 0f, 350f, (System.currentTimeMillis() - start));
            sprite.setAlpha(alpha);
            if (shadow) {
                alpha = Entity.ease(0.4f, 0f, 350f, (System.currentTimeMillis() - start));
                shadowSprite.setAlpha(alpha);
            }

            if (alpha == 0f) {
                Fishing.getInstance().removeLogical(this);
            }
        }
    }

    @Override
    public void dispose() {
        sprite.getParentScene().removeEntity(sprite);
        if (shadow)
            shadowSprite.getParentScene().removeEntity(shadowSprite);
    }

    public void enableShadow() {
        shadow = true;
    }

    public void goLeft() {
        left = true;
        target *= 3f;
    }
}
