package com.boxtrotstudio.fishing.core.game.fishing.shop

import com.badlogic.gdx.scenes.scene2d.utils.Drawable
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import com.boxtrotstudio.fishing.Fishing
import com.boxtrotstudio.fishing.core.game.Entity
import com.boxtrotstudio.fishing.core.game.fishing.ShopItem
import com.boxtrotstudio.fishing.core.game.fishing.rods.BasicRod

class SmartHook : ShopItem() {
    init {
        name = "Smart Hook"
        cost = 120
        description = "This little fella can help you catch fish! With only a 30% fail rate, Smart Hook" +
        " can catch 1-3 fish every 5 seconds!"

        image = TextureRegionDrawable(Entity.fromImage("sprites/hook.png"))
        imageWidth = 0f
        imageHeight = 0f
    }

    override fun onBuy() {
        Fishing.getPlayer().buyAutoRod(BasicRod())
    }
}