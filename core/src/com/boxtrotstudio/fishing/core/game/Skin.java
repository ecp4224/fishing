package com.boxtrotstudio.fishing.core.game;

import com.boxtrotstudio.fishing.core.game.animations.Animation;
import com.boxtrotstudio.fishing.core.game.animations.AnimationVariant;

public class Skin {
    private String name = "DEFAULT";
    private String texture_file = "";
    private String variant_name = "DEFAULT";
    private String character_name = "";

    private Skin() { }

    public String getName() {
        return name;
    }

    public String getTexturFile() {
        return texture_file;
    }

    public String getVarianName() {
        return variant_name;
    }

    public String getCharacterName() {
        if (character_name == null || character_name.equals(""))
            return name;

        return character_name;
    }

    public void applyTo(Animation... animations) {
        for (Animation animation : animations) {
            AnimationVariant variant = animation.getVariant(variant_name);
            if (variant != null) {
                animation.applyVariant(variant);
            }
        }
    }
}
