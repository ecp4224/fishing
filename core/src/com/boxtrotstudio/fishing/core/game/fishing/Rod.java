package com.boxtrotstudio.fishing.core.game.fishing;

import com.boxtrotstudio.fishing.core.logic.Logical;

public interface Rod extends Logical {
    String getName();

    String description();

    void load();
}
