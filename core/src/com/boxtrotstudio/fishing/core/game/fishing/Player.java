package com.boxtrotstudio.fishing.core.game.fishing;

import com.boxtrotstudio.fishing.Fishing;
import com.boxtrotstudio.fishing.core.game.fishing.rods.PlayerRod;

public class Player {
    private FishInventory fishInventory = new FishInventory();
    private PlayerRod rod;
    private long money;

    public long getMoney() {
        return money;
    }

    public FishInventory getFishInventory() {
        return fishInventory;
    }

    public PlayerRod getRod() {
        return rod;
    }

    public void setRod(PlayerRod rod) {
        this.rod = rod;
    }
}
