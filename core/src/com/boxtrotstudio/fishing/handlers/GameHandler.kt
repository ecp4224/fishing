package com.boxtrotstudio.fishing.handlers

import com.boxtrotstudio.fishing.Fishing
import com.boxtrotstudio.fishing.core.game.Entity
import com.boxtrotstudio.fishing.core.game.fishing.FishInventory
import com.boxtrotstudio.fishing.core.game.fishing.World
import com.boxtrotstudio.fishing.core.game.fishing.worlds.TestWorld
import com.boxtrotstudio.fishing.core.logic.Handler
import com.boxtrotstudio.fishing.handlers.scenes.LoadingScene
import com.boxtrotstudio.fishing.handlers.scenes.SpriteScene
import com.boxtrotstudio.fishing.utils.Global

class GameHandler() : Handler {
    lateinit var world: World
    lateinit var spriteScene: SpriteScene
    val fishInventory = FishInventory()


    override fun resume() {
        //We need to reload all assets
        val loading = LoadingScene(this)
        spriteScene = SpriteScene()

        loading.setLoadedCallback(Runnable {
            Fishing.getInstance().clearScreen()
            Fishing.getInstance().addScene(spriteScene)
        })

        Fishing.getInstance().addScene(loading)

        Global.GAME = this
    }

    override fun pause() {
        //TODO Save game
        Fishing.getInstance().clearScreen() //We need to clear the screen to save memory
    }

    override fun start() {
        val loading = LoadingScene(this)
        spriteScene = SpriteScene()

        loading.setLoadedCallback(Runnable {
            Fishing.getInstance().clearScreen()
            Fishing.getInstance().addScene(spriteScene)
        })

        Fishing.getInstance().addScene(loading)

        Global.GAME = this
    }

    override fun tick() {

    }

    fun loadSaveState() {
        //TODO Load save state

        world = TestWorld() //TODO Remove test world

        val background = Entity.fromTexture(world.backgroundTexture())
        background.zIndex = -100000

        spriteScene.addEntity(background)

        world.load(this)
    }
}
