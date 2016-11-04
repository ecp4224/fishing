package com.boxtrotstudio.fishing.core.game.fishing;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class FishInventory {
    private HashMap<String, FishHolder> fishInventory = new HashMap<>();
    private ArrayList<Fish> fishTypes = new ArrayList<>();
    private long fishCount = 0;

    public void addFish(Fish fish) {
        addFish(fish, 1);
    }

    public void addFish(Fish fish, long amount) {
        if (fishInventory.containsKey(fish.getName())) {
            fishInventory.get(fish.getName()).count += amount;
        } else {
            FishHolder holder = new FishHolder(fish);
            holder.count = amount;
            fishInventory.put(fish.getName(), holder);
            fishTypes.add(fish);
        }

        fishCount += amount;
    }

    public List<FishHolder> getInventory() {
        List<FishHolder> list = new ArrayList<>();
        for (String key : fishInventory.keySet()) {
            list.add(fishInventory.get(key));
        }
        return list;
    }

    public long sell(int amount) {
        if (fishInventory.size() == 0 || fishTypes.size() == 0)
            return 0;
        return sell(fishTypes.get(0), amount);
    }

    public long sell(Fish fish) {
        return sell(fish, 1);
    }

    public long sell(Fish fish, int quanity) {
        if (!fishInventory.containsKey(fish.getName()))
            return 0;

        FishHolder holder = fishInventory.get(fish.getName());
        if (holder.count <= quanity) {
            long sell = (holder.count * fish.getSellValue());
            fishCount -= holder.count;
            holder.count = 0;
            fishInventory.remove(fish.getName());
            fishTypes.remove(fish);
            return sell;
        } else {
            holder.count -= quanity;
            fishCount -= quanity;
        }

        return (quanity * fish.getSellValue());
    }

    public long forceSell(long amount) {
        long totalSell = 0L;
        while (amount > 0) {
            if (fishTypes.size() == 0)
                break;

            Fish fish = fishTypes.get(0);
            FishHolder holder = fishInventory.get(fish.getName());
            if (holder.count <= amount) {
                long sell = (holder.count * fish.getSellValue());
                amount -= holder.count;
                fishCount -= holder.count;
                holder.count = 0;
                fishInventory.remove(fish.getName());
                fishTypes.remove(fish);
                totalSell += sell;
            } else {
                totalSell += (amount * fish.getSellValue());
                holder.count -= amount;
                fishCount -= amount;
                amount = 0;
            }
        }

        return totalSell;
    }

    public long sellAll() {
        long sum = 0;
        for (String key : fishInventory.keySet()) {
            FishHolder holder = fishInventory.get(key);
            sum = sum + (holder.fish.getSellValue() * holder.count);
        }

        fishInventory.clear();
        fishCount = 0;

        return sum;
    }

    public long size() {
        return fishCount;
    }

    public static class FishHolder {
        private Fish fish;
        private long count;

        public FishHolder(Fish fish) {
            this.fish = fish;
            this.count = 1;
        }

        public Fish getFish() {
            return fish;
        }

        public long getCount() {
            return count;
        }
    }
}
