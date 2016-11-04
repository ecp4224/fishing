package com.boxtrotstudio.fishing.core.render;

import com.badlogic.gdx.Gdx;
import com.boxtrotstudio.fishing.Fishing;
import com.boxtrotstudio.fishing.core.game.Entity;
import com.boxtrotstudio.fishing.core.logic.Logical;
import com.boxtrotstudio.fishing.handlers.scenes.SpriteScene;
import com.boxtrotstudio.fishing.utils.Vector2f;

public class ToastEntity implements Logical {
    protected Entity sprite;

    private long start;
    private float duration;
    private final float target;

    private Vector2f startPoint;

    public ToastEntity(Vector2f startPos, Entity target, float duration) {
        super();
        this.startPoint = startPos;
        this.sprite = target;
        this.duration = duration;

        this.target = 25;
    }

    public float getToastDuration() {
        return duration;
    }

    public void toast(SpriteScene scene) {
        sprite.setX(startPoint.x);
        sprite.setY(startPoint.y);
        sprite.setAlpha(1f);
        scene.addEntity(sprite);
        Gdx.app.postRunnable(new Runnable() {
            @Override
            public void run() {
                Fishing.getInstance().addLogical(ToastEntity.this);
                start = System.currentTimeMillis();
            }
        });
    }

    public void toast() {
        toast(Fishing.GAME.spriteScene);
    }

    boolean fadeOut = false;
    float alpha = 0f;
    @Override
    public void tick() {
        if (!fadeOut) {
            sprite.setX(startPoint.getX());

            float targetAlpha = Entity.ease(0.1f, 1f, duration, (System.currentTimeMillis() - start));
            float target = Entity.ease(0f, this.target, duration, (System.currentTimeMillis() - start));
            sprite.setY(startPoint.getY() + target);
            sprite.setAlpha(targetAlpha);
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
