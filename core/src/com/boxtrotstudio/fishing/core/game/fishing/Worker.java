package com.boxtrotstudio.fishing.core.game.fishing;

import com.badlogic.gdx.graphics.Color;
import com.boxtrotstudio.fishing.Fishing;
import com.boxtrotstudio.fishing.core.render.ToastText;
import com.boxtrotstudio.fishing.utils.Global;
import com.boxtrotstudio.fishing.utils.Vector2f;

public final class Worker {
    protected String name = "Family";
    protected int sellAmount = 3, sellRate = 10000;
    private long lastSellTime;

    public void tick() {
        if (System.currentTimeMillis() - lastSellTime >= sellRate) {
            lastSellTime = System.currentTimeMillis();

            int toSell = Global.rand(sellAmount / 2, sellAmount);
            if (toSell == 0)
                toSell = sellAmount;

            if (Global.RANDOM.nextDouble() < 0.05)
                toSell *= 2;

            long amount = Fishing.getPlayer().getFishInventory().forceSell(toSell);
            if (amount == 0)
                return;

            Fishing.getPlayer().addMoney(amount);
            ToastText text = new ToastText(new Vector2f(1175f, 50f), "+" + amount , 500f);
            text.goLeft();
            text.setColor(Color.GOLD);
            text.toast(Fishing.GAME.uiSpriteScene);
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSellAmount() {
        return sellAmount;
    }

    public void setSellAmount(int sellAmount) {
        this.sellAmount = sellAmount;
    }

    public int getSellRate() {
        return sellRate;
    }

    public void setSellRate(int sellRate) {
        this.sellRate = sellRate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Worker worker = (Worker) o;

        return name != null ? name.equals(worker.name) : worker.name == null;
    }

    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }
}
