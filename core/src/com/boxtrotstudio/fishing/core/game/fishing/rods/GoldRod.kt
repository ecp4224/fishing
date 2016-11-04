package com.boxtrotstudio.fishing.core.game.fishing.rods

import com.badlogic.gdx.graphics.Color

class GoldRod : BasicRod() {
    override fun getName(): String {
        return "Rose Gold Rod"
    }

    override fun description(): String {
        return "Catches a fish every 10 seconds, with a 20% fail rate"
    }

    override fun load() {
        frequency = 10000
        failRate = 0.2
        minCatch = 3
        maxCatch = 8

        spawnHook(Color.GOLDENROD)
    }

    override fun incrementalUpgrade() {
        minCatch += 3
        maxCatch += 8

        spawnHook(Color.GOLDENROD)
    }
}