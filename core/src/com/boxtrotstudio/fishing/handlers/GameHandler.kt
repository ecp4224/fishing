package com.boxtrotstudio.fishing.handlers

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.InputMultiplexer
import com.boxtrotstudio.fishing.Fishing
import com.boxtrotstudio.fishing.core.game.Entity
import com.boxtrotstudio.fishing.core.game.fishing.Fish
import com.boxtrotstudio.fishing.core.game.fishing.FishInventory
import com.boxtrotstudio.fishing.core.game.fishing.Player
import com.boxtrotstudio.fishing.core.game.fishing.World
import com.boxtrotstudio.fishing.core.game.fishing.rods.BasicPlayerRod
import com.boxtrotstudio.fishing.core.game.fishing.worlds.TestWorld
import com.boxtrotstudio.fishing.core.logic.Handler
import com.boxtrotstudio.fishing.handlers.scenes.*
import com.boxtrotstudio.fishing.utils.Global

class GameHandler() : Handler {
    lateinit var world: World
    lateinit var spriteScene: SpriteScene
    lateinit var inputProcessor: InputMultiplexer
    lateinit var shop: ShopScene
    lateinit var bluredScene: BlurredScene
    val fishInventory = FishInventory()


    override fun resume() {
        Fishing.game = this

        inputProcessor = InputMultiplexer()

        Gdx.input.inputProcessor = inputProcessor

        //We need to reload all assets
        val loading = LoadingScene(this)
        spriteScene = SpriteScene()

        loading.setLoadedCallback(Runnable {
            Fishing.getInstance().removeScene(loading)
            Fishing.getInstance().addScene(spriteScene)
            val score = ScoreScene()
            score.requestOrder(3)
            Fishing.getInstance().addScene(score)
        })

        Fishing.getInstance().addScene(loading)

        Global.GAME = this
    }

    override fun pause() {
        //TODO Save game
        Fishing.getInstance().clearScreen() //We need to clear the screen to save memory
    }

    override fun start() {
        Fishing.game = this

        inputProcessor = InputMultiplexer()

        Gdx.input.inputProcessor = inputProcessor

        val loading = LoadingScene(this)
        spriteScene = SpriteScene()

        loading.setLoadedCallback(Runnable {
            Fishing.getInstance().removeScene(loading)
            Fishing.getInstance().addScene(spriteScene)
            val score = ScoreScene()
            score.requestOrder(-3)
            Fishing.getInstance().addScene(score)
        })

        Fishing.getInstance().addScene(loading)

        Global.GAME = this
    }

    override fun tick() {
        if (Fishing.player != null && Fishing.player.rod != null)
            Fishing.player.rod.tick()
    }

    fun loadSaveState() {
        //TODO Load save state

        world = TestWorld() //TODO Remove test world
        Fishing.player = Player() //TODO Load player from file maybe?
        Fishing.player.rod = BasicPlayerRod()
        Fishing.player.rod.load()

        val background = Entity.fromTexture(world.backgroundTexture())
        background.zIndex = -100000

        spriteScene.addEntity(background)

        world.load(this)
    }

    public fun showShop() {

        bluredScene = BlurredScene(spriteScene, 17f)
        Fishing.getInstance().addScene(bluredScene)
        Gdx.app.postRunnable {
            spriteScene.isVisible = false
        }

        shop = ShopScene()
        shop.requestOrder(-5)
        Fishing.getInstance().addScene(shop)
    }

    public fun hideShop() {
        Fishing.getInstance().removeScene(bluredScene)
        Fishing.getInstance().removeScene(shop)
        spriteScene.isVisible = true
    }
}
