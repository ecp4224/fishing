package com.boxtrotstudio.fishing.core.game.fishing;

import com.boxtrotstudio.fishing.Fishing;
import com.boxtrotstudio.fishing.core.game.fishing.rods.AutoRod;
import com.boxtrotstudio.fishing.core.game.fishing.rods.PlayerRod;
import com.boxtrotstudio.fishing.utils.Global;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;

public class Player {
    private static final long MONEY_UPDATE_SPEED = 1L;

    private FishInventory fishInventory = new FishInventory();
    private PlayerRod rod;
    private ArrayList<Integer> purchasedItems = new ArrayList<>();

    private long workerCount;
    private long actualMoney;
    private long lastSave;
    private double idlePercent = 0.3;
    public boolean isNew = true;

    private transient ArrayList<Worker> workers = new ArrayList<>();
    private transient HashMap<Type, AutoRod> autoRods = new HashMap<>();
    private transient long displayMoney;
    private transient long displayFishCount;
    private transient long lastUpdate;

    public long getDisplayMoney() {
        return displayMoney;
    }

    public long getDisplayFishCount() {
        return displayFishCount;
    }

    public long getActualMoney() {
        return actualMoney;
    }

    public void subtractMoney(long amount) {
        this.actualMoney -= amount;
    }

    public FishInventory getFishInventory() {
        return fishInventory;
    }

    public PlayerRod getRod() {
        return rod;
    }

    public void setRod(PlayerRod rod) {
        this.rod = rod;
        this.rod.load();
    }

    public void tick() {
        if (System.currentTimeMillis() - lastUpdate >= MONEY_UPDATE_SPEED) {
            lastUpdate = System.currentTimeMillis();

            if (displayFishCount > fishInventory.size())
                displayFishCount--;
            else if (displayFishCount < fishInventory.size())
                displayFishCount++;

            if (Math.abs(displayMoney - actualMoney) > 5)
                displayMoney += ((actualMoney - displayMoney) / 2);
            else
                displayMoney = actualMoney;
        }

        for (Worker worker : workers) {
            worker.tick();
        }

        for (AutoRod rod : autoRods.values()) {
            rod.tick();
        }
    }

    private volatile boolean isLoading = false;
    public void load() {
        isLoading = true;

        rod.load();

        for (Integer i : purchasedItems) {
            Fishing.GAME.world.buyableItems()[i].onBuy();
        }
        isLoading = false;

        displayMoney = actualMoney;
        displayFishCount = fishInventory.size();

        if (workers.size() == 0)
            addWorker(); //fail safe in-case we get to this point somehow

        if (lastSave != 0L)
            calculateIdle(System.currentTimeMillis() - lastSave);
    }

    public void addWorker(Worker worker) {
        if (this.workers.contains(worker)) {
            Worker current = this.workers.get(this.workers.indexOf(worker));
            current.setSellAmount(current.getSellAmount() + worker.getSellAmount());
        } else {
            this.workers.add(worker);
        }
        workerCount++;
    }

    public double getIdlePercent() {
        return idlePercent;
    }

    public void setIdlePercent(double idlePercent) {
        this.idlePercent = idlePercent;
    }

    public void addWorker() {
        addWorker(new Worker());
    }

    public void buyAutoRod(AutoRod rod) {
        if (this.autoRods.containsKey(rod.getClass())) {
            this.autoRods.get(rod.getClass()).incrementalUpgrade();
        } else {
            this.autoRods.put(rod.getClass(), rod);
            rod.load();
        }
    }

    void onSave() {
        lastSave = System.currentTimeMillis();
    }

    public void recordBuyItem(int index) {
        if (isLoading)
            return;

        this.purchasedItems.add(index);
    }

    public double getCatchPerSecond() {
        double totalPerSecond = 0L;

        for (AutoRod rod : autoRods.values()) {
            double meanCatch = rod.getMinCatch() + ((rod.getMaxCatch() - rod.getMinCatch()) / 2);
            double seconds = rod.getFrequency() / 1000.0;

            double perSecond = meanCatch / seconds;

            totalPerSecond += perSecond;
        }

        return totalPerSecond;
    }

    public double getSellPerSecond() {
        double totalPerSecond = 0L;

        for (Worker worker : workers) {
            double sell = worker.getSellAmount();
            double seconds = worker.getSellRate() / 1000.0;

            double perSecond = sell / seconds;

            totalPerSecond += perSecond;
        }

        return Math.round(totalPerSecond * 100.0) / 100.0;
    }

    public void calculateIdle(long duration) {
        long sell = (long) Math.floor(getSellPerSecond() * idlePercent * (duration / 1000.0));
        long fish = (long) Math.floor(getCatchPerSecond() * idlePercent * (duration / 1000L));

        //Catch fish first
        while (fish > 0) {
            Fish caughtFish = FishFactory.pickRandomFish();
            long half = Math.max(fish / 2, 2);
            long addAmount = Global.rand(1, half);
            Fishing.getPlayer().getFishInventory().addFish(caughtFish, addAmount);
            fish -= addAmount;
        }

        //Then sell
        long amount = fishInventory.forceSell(sell);
        addMoney(amount);
    }

    public void addMoney(long amount) {
        this.actualMoney += amount;
    }

    public long workerCount() {
        return workerCount;
    }
}
