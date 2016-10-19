package com.boxtrotstudio.fishing;

import box2dLight.RayHandler;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.boxtrotstudio.fishing.core.game.fishing.Fish;
import com.boxtrotstudio.fishing.core.game.fishing.FishFactory;
import com.boxtrotstudio.fishing.core.logic.Handler;
import com.boxtrotstudio.fishing.handlers.GameHandler;
import com.boxtrotstudio.fishing.utils.ArrayHelper;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileFilter;

public class Fishing {
    public static final AssetManager ASSETS = new AssetManager();

    private static FishingClient INSTANCE;
    private static Handler DEFAULT = new BlankHandler();
    @NotNull
    public static RayHandler rayHandler;

    public static FishingClient getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new FishingClient(DEFAULT);
        }
        return INSTANCE;
    }

    private static boolean loaded;
    public static void loadGameAssets(AssetManager manager) {
        if (loaded)
            return;

        ASSETS.setLoader(Fish.class, new FishFactory(new InternalFileHandleResolver()));

        //Load all sprites
        FileHandle[] sprites = Gdx.files.internal("sprites").list(new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                return pathname.getName().endsWith("png") ||
                        pathname.getName().endsWith("PNG") ||
                        pathname.getName().endsWith("jpg") ||
                        pathname.getName().endsWith("JPG");
            }
        });

        //Load all sprites
        FileHandle[] menuSprites = Gdx.files.internal("sprites/menu").list(new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                return pathname.getName().endsWith("png") ||
                        pathname.getName().endsWith("PNG") ||
                        pathname.getName().endsWith("jpg") ||
                        pathname.getName().endsWith("JPG");
            }
        });

        //Load all sprites
        FileHandle[] map_files = Gdx.files.internal("maps").list(new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                return pathname.getName().endsWith("png") ||
                        pathname.getName().endsWith("PNG") ||
                        pathname.getName().endsWith("jpg") ||
                        pathname.getName().endsWith("JPG");
            }
        });

        FileHandle[] fish_files = Gdx.files.internal("fish").list(new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                return pathname.getName().endsWith("fish") ||
                        pathname.getName().endsWith("json");
            }
        });

        for (FileHandle file: ArrayHelper.combine(sprites, menuSprites)) {
            manager.load(file.path(), Texture.class);
        }

        for (FileHandle file: map_files) {
            manager.load(file.path(), Texture.class);
        }

        for (FileHandle file: fish_files) {
            manager.load(file.path(), Fish.class);
        }

        FileHandle[] sounds = Gdx.files.internal("sounds").list(new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                return pathname.getName().endsWith("mp3") ||
                        pathname.getName().endsWith("wav") ||
                        pathname.getName().endsWith("ogg");
            }
        });

        for (FileHandle file: sounds) {
            manager.load(file.path(), Sound.class);
        }

        loaded = true;
    }

    public static void setDefaultHandler(Handler defaultHandler) {
        Fishing.DEFAULT = defaultHandler;
    }

    private static class BlankHandler implements Handler {

        @Override
        public void start() { }

        @Override
        public void tick() { }

        @Override
        public void resume() { }

        @Override
        public void pause() { }
    }
}
