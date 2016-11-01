package com.boxtrotstudio.fishing.core.game.fishing.rods;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector3;
import com.boxtrotstudio.fishing.Fishing;
import com.boxtrotstudio.fishing.core.game.Entity;
import com.boxtrotstudio.fishing.core.game.fishing.Fish;
import com.boxtrotstudio.fishing.core.game.fishing.FishFactory;
import com.boxtrotstudio.fishing.core.game.fishing.Rod;
import com.boxtrotstudio.fishing.core.render.ToastText;
import com.boxtrotstudio.fishing.utils.Global;
import com.boxtrotstudio.fishing.utils.Vector2f;

public abstract class PlayerRod extends GestureDetector.GestureAdapter implements Rod {
    private static final long TIMEOUT = 3000L;

    /*private long lastCatchTime;
    private boolean wasPressed;*/
    private boolean wasCasted;
    private Entity hook;
    @Override
    public void tick() {
        /*   if (Gdx.input.isTouched() && !wasPressed) {
               catchFish();

               wasPressed = true;
           } else if (!Gdx.input.isTouched()) wasPressed = false;

        if (System.currentTimeMillis() - lastCatchTime >= TIMEOUT) {
            lastCatchTime = System.currentTimeMillis();

            catchFish();
        }*/


    }

    @Override
    public void load() {
        Fishing.game.inputProcessor.addProcessor(new GestureDetector(this));
    }

    @Override
    public boolean fling(float velocityX, float velocityY, int button) {
        if (velocityX > 5 && !wasCasted) {
            //wasCasted = true;

            hook = Entity.fromImage("sprites/hook.png");
            hook.setCenter(537f, 400f);
            hook.setVelocity(new Vector2f(velocityX / 500f, velocityY / 1000f));
            hook.hasGravity(true);
            hook.setAlpha(1f);
            Fishing.game.spriteScene.addEntity(hook);
        }

        return super.fling(velocityX, velocityY, button);
    }

    private void catchFish() {
        Fish caughtFish = FishFactory.pickRandomFish();
        Fishing.player.getFishInventory().addFish(caughtFish);

        //537.25757 : 238.66669
        ToastText text = new ToastText(new Vector2f(537f, 238f), "+1", 1100f);
        text.setColor(Color.WHITE);
        text.toast();
    }
}
