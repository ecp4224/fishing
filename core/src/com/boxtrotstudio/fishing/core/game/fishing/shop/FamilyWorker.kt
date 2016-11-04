package com.boxtrotstudio.fishing.core.game.fishing.shop

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import com.boxtrotstudio.fishing.Fishing
import com.boxtrotstudio.fishing.core.game.Entity
import com.boxtrotstudio.fishing.core.game.animations.Animation
import com.boxtrotstudio.fishing.core.game.fishing.ShopItem

class FamilyWorker : ShopItem() {
    init {
        this.name = "Family"
        this.description = "Hire members of my family to help sell the fish. (3 fish every 10 seconds)"

        val cat = Entity.fromImage("sprites/cat.png")
        Animation.fromFile(Gdx.files.internal("sprites/cat.json"), cat)
        cat.setScale(10f)
        Fishing.getInstance().addLogical(cat)

        this.image = TextureRegionDrawable(cat)

        this.cost = 50
    }

    override fun onBuy() {
        Fishing.getPlayer().addWorker()
    }
}
