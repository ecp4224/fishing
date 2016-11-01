package com.boxtrotstudio.fishing.core.game.fishing.rods

class BasicPlayerRod : PlayerRod() {
    override fun dispose() {

    }

    override fun name(): String {
        return "Basic"
    }

    override fun description(): String {
        return "A basic player rod"
    }

}