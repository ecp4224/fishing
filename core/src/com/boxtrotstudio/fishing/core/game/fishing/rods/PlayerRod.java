package com.boxtrotstudio.fishing.core.game.fishing.rods;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.input.GestureDetector;
import com.boxtrotstudio.fishing.Fishing;
import com.boxtrotstudio.fishing.core.game.Entity;
import com.boxtrotstudio.fishing.core.game.entities.Hook;
import com.boxtrotstudio.fishing.core.game.fishing.Fish;
import com.boxtrotstudio.fishing.core.game.fishing.FishFactory;
import com.boxtrotstudio.fishing.core.game.fishing.Rod;
import com.boxtrotstudio.fishing.core.render.ToastText;
import com.boxtrotstudio.fishing.utils.Global;
import com.boxtrotstudio.fishing.utils.Vector2f;

public class PlayerRod implements Rod {
    private int nextTapRequirement;
    private int currentTapCount;
    private boolean wasPressed;

    protected int tapMin = 5, tapMax = 15;
    protected int catchMin = 1, catchMax = 3;
    protected double catchCrit = 0.1;
    protected String name = "Player Rod";

    @Override
    public void tick() {
        if (Gdx.input.isTouched() && !wasPressed) {
            currentTapCount++;

            if (currentTapCount >= nextTapRequirement) {
                int catchCount = Global.rand(catchMin, catchMax);
                if (Global.RANDOM.nextDouble() < catchCrit)
                    catchCount *= 2;

                catchCount *= Fishing.GAME.world.catchMultiplier();

                for (int i = 0; i < catchCount; i++) {
                    catchFish();
                }


                //537.25757 : 238.66669
                ToastText text = new ToastText(new Vector2f(537f, 238f), "+" + catchCount, 1100f);
                text.setColor(new Color(90f / 255f, 205f / 255f, 38f / 255f, 1f));
                text.toast();

                currentTapCount = 0;
                nextTapRequirement = Global.rand(tapMin, tapMax);
            }

            wasPressed = true;
        } else if (!Gdx.input.isTouched()) wasPressed = false;


    }

    public int getTapMin() {
        return tapMin;
    }

    public void setTapMin(int tapMin) {
        this.tapMin = tapMin;
    }

    public int getTapMax() {
        return tapMax;
    }

    public void setTapMax(int tapMax) {
        this.tapMax = tapMax;
    }

    public int getCatchMin() {
        return catchMin;
    }

    public void setCatchMin(int catchMin) {
        this.catchMin = catchMin;
    }

    public int getCatchMax() {
        return catchMax;
    }

    public void setCatchMax(int catchMax) {
        this.catchMax = catchMax;
    }

    public double getCatchCrit() {
        return catchCrit;
    }

    public void setCatchCrit(double catchCrit) {
        this.catchCrit = catchCrit;
    }

    @Override
    public void dispose() { }

    protected void catchFish() {
        Fish caughtFish = FishFactory.pickRandomFish();
        Fishing.getPlayer().getFishInventory().addFish(caughtFish);
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String description() {
        return "It's a rod";
    }

    @Override
    public void load() { }
}
