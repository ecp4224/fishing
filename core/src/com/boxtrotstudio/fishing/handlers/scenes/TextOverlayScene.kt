package com.boxtrotstudio.fishing.handlers.scenes

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.boxtrotstudio.fishing.core.render.Text
import com.boxtrotstudio.fishing.core.render.scene.AbstractScene

class TextOverlayScene(val header: String, val subtext: String, var showDots: Boolean) : AbstractScene() {
    private lateinit var headerText: Text
    private lateinit var subText: Text
    var dots = 0
    override fun onInit() {
        headerText = Text(36, Color.WHITE, Gdx.files.internal("fonts/TitilliumWeb-Regular.ttf"));
        headerText.x = 640f
        headerText.y = 360f
        headerText.text = header
        headerText.load()

        subText = Text(28, Color.WHITE, Gdx.files.internal("fonts/TitilliumWeb-Light.ttf"));
        subText.x = 640f
        subText.y = 300f
        subText.text = subtext
        subText.load()

        requestOrder(-2)
    }

    fun setHeaderText(text: String) {
        headerText.text = text
    }


    public fun setSubText(text: String) {
        subText.text = text
    }

    private var lastDot = 0L
    override fun render(camera: OrthographicCamera, batch: SpriteBatch) {
        if (showDots) {
            if (System.currentTimeMillis() - lastDot > 800) {
                dots++;
                if (dots == 4)
                    dots = 0;


                subText.text = subtext
                for (i in 0..dots) {
                    subText.text += "."
                }

                subText.x = 640f

                lastDot = System.currentTimeMillis()
            }
        }

        batch.begin()

        headerText.draw(batch)
        subText.draw(batch)

        batch.end()
    }

    override fun dispose() {

    }
}
