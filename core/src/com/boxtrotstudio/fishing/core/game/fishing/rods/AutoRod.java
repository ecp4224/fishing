package com.boxtrotstudio.fishing.core.game.fishing.rods;

import com.badlogic.gdx.graphics.Color;
import com.boxtrotstudio.fishing.Fishing;
import com.boxtrotstudio.fishing.core.game.fishing.Fish;
import com.boxtrotstudio.fishing.core.game.fishing.FishFactory;
import com.boxtrotstudio.fishing.core.game.fishing.Rod;
import com.boxtrotstudio.fishing.core.render.ToastText;
import com.boxtrotstudio.fishing.utils.Global;
import com.boxtrotstudio.fishing.utils.Vector2f;

public abstract class AutoRod implements Rod {
    protected long frequency;
    protected double failRate;
    protected int minCatch = 1, maxCatch = 3;
    protected Vector2f toastLocation = new Vector2f(537f, 238f);

    private long lastCatch;

    public abstract void incrementalUpgrade();

    @Override
    public void tick() {
        if (System.currentTimeMillis() - lastCatch >= frequency) {
            lastCatch = System.currentTimeMillis();

            boolean didFail = Global.RANDOM.nextDouble() <= failRate;
            if (!didFail) {
                int count = Global.rand(minCatch, maxCatch);
                if (Global.RANDOM.nextDouble() < 0.05)
                    count *= 2;

                count *= Fishing.GAME.world.catchMultiplier();

                int originalAmount = count;
                while (count > 0) {
                    Fish caughtFish = FishFactory.pickRandomFish();
                    int half = Math.max(count / 2, 2);
                    int addAmount = Global.rand(1, half);
                    Fishing.getPlayer().getFishInventory().addFish(caughtFish, addAmount);
                    count -= addAmount;
                }

                ToastText text = new ToastText(toastLocation, "+" + originalAmount, 1100f);
                text.setToastFontSize(30);
                text.setColor(new Color(90f / 255f, 255f / 255f, 38f / 255f, 1f));
                text.toast();
            }
        }
    }

    public long getFrequency() {
        return frequency;
    }

    public double getFailRate() {
        return failRate;
    }

    public int getMinCatch() {
        return minCatch;
    }

    public int getMaxCatch() {
        return maxCatch;
    }
}
