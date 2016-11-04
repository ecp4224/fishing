package com.boxtrotstudio.fishing.core.game.fishing.shop

import com.boxtrotstudio.fishing.Fishing
import com.boxtrotstudio.fishing.core.game.fishing.ShopItem
import com.boxtrotstudio.fishing.core.game.fishing.rods.PlayerRod

class ProfessionalFishingRod : ShopItem() {
    init {
        name = "FishingRod +"
        description = "Catch 10 fish in 10-20 taps!"
        cost = 300
    }

    override fun doesOwn() : Boolean {
        return Fishing.getPlayer().rod.name.equals("FishingRod +")
    }

    override fun onBuy() {
        val newRod = PlayerRod()
        newRod.catchCrit = 0.15
        newRod.catchMin = 8
        newRod.catchMax = 11
        newRod.tapMin = 10
        newRod.tapMax = 20
        newRod.name = "FishingRod +"

        Fishing.getPlayer().rod = newRod
    }

}