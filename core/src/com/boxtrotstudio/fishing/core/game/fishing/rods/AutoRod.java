package com.boxtrotstudio.fishing.core.game.fishing.rods;

import com.boxtrotstudio.fishing.core.game.fishing.Fish;
import com.boxtrotstudio.fishing.core.game.fishing.FishFactory;
import com.boxtrotstudio.fishing.core.game.fishing.Rod;
import com.boxtrotstudio.fishing.utils.Global;

public abstract class AutoRod implements Rod {
    protected long frequency;
    protected double failRate;
    protected long lastCatch;

    @Override
    public void tick() {
        if (System.currentTimeMillis() - lastCatch >= frequency) {
            lastCatch = System.currentTimeMillis();

            boolean didFail = Global.RANDOM.nextDouble() <= failRate;
            if (!didFail) {
                Fish fish = FishFactory.pickRandomFish(Global.GAME.world);
                Global.GAME.getFishInventory().addFish(fish);
            }
        }
    }
}
