package com.boxtrotstudio.fishing.handlers

import com.boxtrotstudio.fishing.Fishing
import com.boxtrotstudio.fishing.core.logic.Handler
import com.boxtrotstudio.fishing.handlers.scenes.LoadingScene

class GameHandler() : Handler {
    override fun resume() {

    }

    override fun pause() {

    }

    override fun start() {
        val loading = LoadingScene()

        loading.setLoadedCallback(Runnable {
            Fishing.getInstance().clearScreen()

        })

        Fishing.getInstance().addScene(loading)
    }

    override fun tick() {

    }

}
