package com.boxtrotstudio.fishing.core.game.fishing.rods

class BasicRod : AutoRod() {
    override fun description(): String {
        return "Catches a fish every 5 seconds, with a 30% fail rate"
    }

    override fun dispose() {

    }

    override fun name(): String {
        return "Basic Rod"
    }

    override fun load() {
        frequency = 5000
        failRate = 0.3


        //TODO Load texture
    }
}