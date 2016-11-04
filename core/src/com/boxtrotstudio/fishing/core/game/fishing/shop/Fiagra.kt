package com.boxtrotstudio.fishing.core.game.fishing.shop

import com.badlogic.gdx.graphics.Color
import com.boxtrotstudio.fishing.Fishing
import com.boxtrotstudio.fishing.core.game.fishing.ShopItem
import com.boxtrotstudio.fishing.core.game.fishing.worlds.TestWorld

class Fiagra : ShopItem() {
    init {
        name = "Fiagra"
        description = "x2 fish for 1 minute! (Only while in game)"
        cost = 1500
        persistent = false
    }

    override fun doesOwn() : Boolean {
        val testWorld = Fishing.GAME.world as TestWorld

        return testWorld.water.color.equals(Color.PURPLE) || testWorld.water.targetColor.equals(Color.PURPLE)
    }

    override fun onBuy() {
        val testWorld = Fishing.GAME.world as TestWorld

        testWorld.doubleMultiplier()
        testWorld.water.easeToColor(Color.PURPLE, 2000)
        Thread(Runnable {
            Thread.sleep(60000)
            Fishing.GAME.world.halfMultiplier()
            testWorld.water.easeToColor(Color.WHITE, 2000)
        }).start()
    }
}