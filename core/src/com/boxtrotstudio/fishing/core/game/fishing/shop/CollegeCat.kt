package com.boxtrotstudio.fishing.core.game.fishing.shop

import com.boxtrotstudio.fishing.Fishing
import com.boxtrotstudio.fishing.core.game.fishing.ShopItem
import com.boxtrotstudio.fishing.core.game.fishing.Worker

class CollegeCat : ShopItem() {
    init {
        name = "College Cat"
        description = "Looking for a part time job, I can sell 2 fish in 4 seconds!"
        cost = 150

    }

    override fun onBuy() {
        val worker = Worker()
        worker.name = "College Cat"
        worker.sellAmount = 2
        worker.sellRate = 4000

        Fishing.getPlayer().addWorker(worker)
    }

}