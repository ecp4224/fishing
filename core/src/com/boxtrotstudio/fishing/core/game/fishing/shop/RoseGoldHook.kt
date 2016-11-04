package com.boxtrotstudio.fishing.core.game.fishing.shop

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.utils.Drawable
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import com.boxtrotstudio.fishing.Fishing
import com.boxtrotstudio.fishing.core.game.Entity
import com.boxtrotstudio.fishing.core.game.fishing.ShopItem
import com.boxtrotstudio.fishing.core.game.fishing.rods.GoldRod

class RoseGoldHook : ShopItem() {
    init {
        name = "Rose Gold Hook"
        description = "Not only does this hook look beautiful, it can catch 3-8 fish every 10 seconds!"

        val hook = Entity.fromImage("sprites/hook.png")
        var texture = TextureRegionDrawable(hook)
        var drawable = texture.tint(Color.GOLDENROD)

        image = drawable
        imageWidth = 0f
        imageHeight = 0f

        cost = 250
    }

    override fun onBuy() {
        Fishing.getPlayer().buyAutoRod(GoldRod())
    }

}