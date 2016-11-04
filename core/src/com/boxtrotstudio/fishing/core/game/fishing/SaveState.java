package com.boxtrotstudio.fishing.core.game.fishing;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.boxtrotstudio.fishing.core.game.fishing.rods.PlayerRod;
import com.boxtrotstudio.fishing.utils.Global;

public class SaveState {
    private Player player;
    private int index;

    public static SaveState newSave(int index) {
        SaveState state = new SaveState();
        state.player = new Player();
        state.player.setRod(new PlayerRod());
        state.player.addWorker();
        state.index = index;

        return state;
    }

    public static SaveState load(int index) {
        FileHandle handle = Gdx.files.external("fishing/fish" + index + ".dat");
        if (!handle.exists())
            return newSave(index);

        String json = handle.readString();

        if (json.equals("")) {
            //TODO Alert the user the save was corrupt
            handle.delete();
            return newSave(index);
        }

        return Global.GSON.fromJson(json, SaveState.class);
    }

    private SaveState() { }

    public void save() {
        player.onSave();

        FileHandle folder = Gdx.files.external("fishing");
        if (!folder.exists())
            folder.mkdirs();

        String json = Global.GSON.toJson(this);

        FileHandle handle = Gdx.files.external("fishing/fish" + index + ".dat");
        handle.writeString(json, false);
    }

    public Player getPlayer() {
        return player;
    }
}
