package com.boxtrotstudio.fishing.core.game.fishing;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetLoaderParameters;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.assets.loaders.SynchronousAssetLoader;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Array;
import com.boxtrotstudio.fishing.utils.ArrayHelper;
import com.boxtrotstudio.fishing.utils.Global;
import com.boxtrotstudio.fishing.utils.PFunction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class FishFactory extends SynchronousAssetLoader<Fish, FishFactory.FishParameters> {
    private static HashMap<Integer, ArrayList<Fish>> fishPool = new HashMap<>();

    public FishFactory(FileHandleResolver resolver) {
        super(resolver);
    }

    @Override
    public Fish load(AssetManager assetManager, String fileName, FileHandle file, FishParameters parameter) {
        String json = file.readString();

        Fish fish = Global.GSON.fromJson(json, Fish.class);

        if (fishPool.containsKey(fish.getWorld())) {
            fishPool.get(fish.getWorld()).add(fish);
        } else {
            ArrayList<Fish> fishes = new ArrayList<>();
            fishes.add(fish);
            fishPool.put(fish.getWorld(), fishes);
        }

        return fish;
    }

    @Override
    public Array<AssetDescriptor> getDependencies(String fileName, FileHandle file, FishParameters parameter) {
        return null;
    }

    public static Fish pickRandomFish(World world) {
        if (!fishPool.containsKey(world.worldNumber()))
            return null;

        ArrayList<Fish> fishes = fishPool.get(world.worldNumber());

        if (fishes.size() == 0)
            return null;

        boolean rareCatch = Global.RANDOM.nextDouble() < 0.05;

        if (rareCatch) {
            return pickRareFish(fishes);
        } else {
            return pickCommonFish(fishes);
        }
    }

    private static Fish pickRareFish(List<Fish> fishes) {
        List<Fish> rareFish = ArrayHelper.filter(fishes, new PFunction<Fish, Boolean>() {
            @Override
            public Boolean run(Fish val) {
                return val.isRare();
            }
        });

        if (rareFish.size() == 0)
            return pickCommonFish(fishes);

        double smallestDistance = Double.MAX_VALUE;
        double randomNumber = Global.RANDOM.nextDouble();
        Fish toReturn = null;
        for (Fish f : rareFish) {
            double distance = Math.abs(f.getProbability() - randomNumber);
            if (toReturn == null) {
                toReturn = f;
                smallestDistance = distance;
            } else if (distance < smallestDistance) {
                toReturn = f;
                smallestDistance = distance;
            }
        }

        return toReturn;
    }

    private static Fish pickCommonFish(List<Fish> fishes) {
        List<Fish> commonFish = ArrayHelper.filter(fishes, new PFunction<Fish, Boolean>() {
            @Override
            public Boolean run(Fish val) {
                return !val.isRare();
            }
        });

        double smallestDistance = Double.MAX_VALUE;
        double randomNumber = Global.RANDOM.nextDouble();
        Fish toReturn = null;
        for (Fish f : commonFish) {
            double distance = Math.abs(f.getProbability() - randomNumber);
            if (toReturn == null) {
                toReturn = f;
                smallestDistance = distance;
            } else if (distance < smallestDistance) {
                toReturn = f;
                smallestDistance = distance;
            }
        }

        return toReturn;
    }

    static class FishParameters extends AssetLoaderParameters<Fish> { }
}
