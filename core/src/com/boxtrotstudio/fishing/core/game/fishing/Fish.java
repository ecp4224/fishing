package com.boxtrotstudio.fishing.core.game.fishing;

import com.badlogic.gdx.graphics.Texture;
import com.boxtrotstudio.fishing.Fishing;

public class Fish implements Cloneable {
    private String name;
    private String texturePath;
    private int skillLevel;
    private int world;
    private double probability;
    private boolean isRare;
    private int sellValue;

    private Fish() { }

    public String getName() {
        return name;
    }

    public String getTexturePath() {
        return texturePath;
    }

    public Texture getTexture() {
        return Fishing.ASSETS.get(texturePath);
    }

    public int getSkillLevel() {
        return skillLevel;
    }

    public int getWorld() {
        return world;
    }

    public double getProbability() {
        return probability;
    }

    public boolean isRare() {
        return isRare;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Fish fish = (Fish) o;

        if (skillLevel != fish.skillLevel) return false;
        if (world != fish.world) return false;
        if (Double.compare(fish.probability, probability) != 0) return false;
        if (isRare != fish.isRare) return false;
        if (name != null ? !name.equals(fish.name) : fish.name != null) return false;
        return texturePath != null ? texturePath.equals(fish.texturePath) : fish.texturePath == null;

    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = name != null ? name.hashCode() : 0;
        result = 31 * result + (texturePath != null ? texturePath.hashCode() : 0);
        result = 31 * result + skillLevel;
        result = 31 * result + world;
        temp = Double.doubleToLongBits(probability);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (isRare ? 1 : 0);
        return result;
    }

    public Fish clone() {
        Fish f = new Fish();
        f.name = name;
        f.texturePath = texturePath;
        f.skillLevel = skillLevel;
        f.world = world;
        f.probability = probability;
        f.isRare = isRare;

        return f;
    }

    public int getSellValue() {
        return sellValue;
    }
}
