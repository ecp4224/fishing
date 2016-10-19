package com.boxtrotstudio.fishing.handlers.scenes

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.boxtrotstudio.fishing.Fishing
import com.boxtrotstudio.fishing.core.game.Entity
import com.boxtrotstudio.fishing.core.render.Text
import com.boxtrotstudio.fishing.core.render.scene.AbstractScene
import com.boxtrotstudio.fishing.handlers.GameHandler
import com.boxtrotstudio.fishing.utils.AsyncTask
import com.boxtrotstudio.fishing.utils.CancelToken
import com.boxtrotstudio.fishing.utils.Future

public class LoadingScene(val handler: GameHandler) : AbstractScene() {
    private lateinit var progressBarFront : Sprite
    private lateinit var logo: Sprite
    private var startTime = 0L
    private var stage2 = 0
    private var didCall = false
    private var onFinished = Runnable {  }

    override fun onInit() {
        val front = Texture("sprites/progress_front.png")
        progressBarFront = Sprite(front)
        progressBarFront.setPosition(1f, 0f)

        val logoTexture = Texture("sprites/boxtrotlogo.png")
        logo = Sprite(logoTexture)
        //640, 360
        logo.setCenter(900f, 360f)
        logo.setOriginCenter()

        //rgb(96,62,40)
        Fishing.getInstance().backColor = Color(96f/255f, 62f/255f, 40f/255f, 1f)

        requestOrder(1)
        Fishing.loadGameAssets(Fishing.ASSETS)

        startTime = System.currentTimeMillis()
    }

    override fun render(camera: OrthographicCamera, batch: SpriteBatch) {
        if (finishedLoading() && !didCall) {
            stage2 = 1
            startTime = System.currentTimeMillis()
            didCall = true
        }

        if (stage2 == 1) {
            val pos = Entity.ease(900f, 640f, 900f, (System.currentTimeMillis() - startTime).toFloat())
            val r = Entity.ease(96f / 255f, 1f, 900f, (System.currentTimeMillis() - startTime).toFloat())
            val g = Entity.ease(62f / 255f, 1f, 900f, (System.currentTimeMillis() - startTime).toFloat())
            val b = Entity.ease(40f / 255f, 1f, 900f, (System.currentTimeMillis() - startTime).toFloat())

            Fishing.getInstance().backColor = Color(r, g, b, 1f)
            logo.setCenter(pos, 360f)

            if (r == 1f) {
                stage2 = 2
                startTime = System.currentTimeMillis()
            }
        }

        if (stage2 == 2 && System.currentTimeMillis() - startTime >= 3000) {
            stage2 = 3
            onFinished.run()
        }

        batch.begin()
        logo.draw(batch)
        batch.end()
    }

    override fun dispose() {

    }

    var task : Future<Boolean>? = null
    fun finishedLoading() : Boolean {
        if (Fishing.ASSETS.update()) {
            if (task == null) {
                task = AsyncTask.runTask {
                    handler.loadSaveState()
                }
            } else if ((task as Future<Boolean>).isFinished) {
                return true
            }
        }
        return false
    }

    public fun setLoadedCallback(callback: Runnable) {
        this.onFinished = callback
    }
}
