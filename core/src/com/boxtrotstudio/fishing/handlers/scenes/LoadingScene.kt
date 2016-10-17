package com.boxtrotstudio.fishing.handlers.scenes

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.boxtrotstudio.fishing.Fishing
import com.boxtrotstudio.fishing.core.render.Text
import com.boxtrotstudio.fishing.core.render.scene.AbstractScene

public class LoadingScene : AbstractScene() {
    private lateinit var progressBarBack : Sprite;
    private lateinit var progressBarFront : Sprite;
    private lateinit var progressText : Text
    private var didCall = false
    private var onFinished = Runnable {  }

    override fun onInit() {
        var back = Texture("sprites/progress_back.png")
        var front = Texture("sprites/progress_front.png");

        progressBarBack = Sprite(back)
        progressBarFront = Sprite(front)

        progressBarFront.setCenter(640f, 32f)
        progressBarBack.setCenter(640f, 32f)

        progressBarFront.setOriginCenter()
        progressBarBack.setOriginCenter()

        progressText = Text(36, Color.WHITE, Gdx.files.internal("fonts/TitilliumWeb-Regular.ttf"));
        progressText.x = 640f
        progressText.y = 360f
        progressText.text = "Loading..."
        progressText.load()

        requestOrder(1)
        Fishing.loadGameAssets(Fishing.ASSETS)
    }

    override fun render(camera: OrthographicCamera, batch: SpriteBatch) {
        var temp = Fishing.ASSETS.progress * 720f

        progressBarFront.setSize(temp, 16f)

        batch.begin()

        progressText.draw(batch)
        progressBarBack.draw(batch)
        progressBarFront.draw(batch)

        batch.end()
        if (Fishing.ASSETS.update() && !didCall) {
            onFinished.run()
            didCall = true
        }
    }

    override fun dispose() {

    }


    public fun setText(text: String) {
        progressText.text = text
    }

    public fun setLoadedCallback(callback: Runnable) {
        this.onFinished = callback
    }
}
