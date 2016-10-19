package com.boxtrotstudio.fishing.core.game.fishing;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class FishInventory {
    private HashMap<Fish, FishHolder> fishInventory = new HashMap<>();

    public void addFish(Fish fish) {
        if (fishInventory.containsKey(fish)) {
            fishInventory.get(fish).count++;
        } else {
            FishHolder holder = new FishHolder(fish);
            fishInventory.put(fish, holder);
        }
    }

    public List<FishHolder> getInventory() {
        List<FishHolder> list = new ArrayList<>();
        for (Fish key : fishInventory.keySet()) {
            list.add(fishInventory.get(key));
        }
        return list;
    }

    public int sell(Fish fish) {
        return sell(fish, 1);
    }

    public int sell(Fish fish, int quanity) {
        if (!fishInventory.containsKey(fish))
            return 0;

        FishHolder holder = fishInventory.get(fish);
        if (holder.count <= quanity) {
            int sell = (holder.count * fish.getSellValue());
            holder.count = 0;
            fishInventory.remove(fish);
            return sell;
        } else {
            holder.count -= quanity;
        }

        return (quanity * fish.getSellValue());
    }

    public int sellAll() {
        int sum = 0;
        for (Fish key : fishInventory.keySet()) {
            FishHolder holder = fishInventory.get(key);
            sum = sum + (key.getSellValue() * holder.count);
        }

        fishInventory.clear();

        return sum;
    }

    public static class FishHolder {
        private Fish fish;
        private int count;

        public FishHolder(Fish fish) {
            this.fish = fish;
            this.count = 1;
        }

        public Fish getFish() {
            return fish;
        }

        public int getCount() {
            return count;
        }
    }
}
