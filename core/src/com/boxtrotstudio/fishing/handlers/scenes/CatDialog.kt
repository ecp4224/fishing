package com.boxtrotstudio.fishing.handlers.scenes

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.Skin
import com.boxtrotstudio.fishing.Fishing
import com.boxtrotstudio.fishing.core.game.Entity
import com.boxtrotstudio.fishing.core.game.animations.Animation
import com.boxtrotstudio.fishing.core.render.scene.AbstractScene
import com.boxtrotstudio.fishing.core.render.scene.Scene

class CatDialog(private var text: String) : AbstractScene() {
    private lateinit var dialog: Label
    private lateinit var dialogShadow: Label
    private lateinit var dialogBackground: Sprite
    private lateinit var shapeRender: ShapeRenderer
    private lateinit var cat: Entity
    private lateinit var stage: Stage
    private var onNext: Runnable? = null
    private lateinit var skin: Skin

    private val dialogColor = Color(232f/255f,201f/255f,151f/255f,1f)
    override fun onInit() {
        requestOrder(-5)

        stage = Stage(
                Fishing.getInstance().viewport,
                Fishing.getInstance().batch
        )
        Fishing.GAME.inputProcessor.addProcessor(stage)

        skin = Skin(Gdx.files.internal("sprites/ui/uiskin.json"))
        val dialogTexture = Texture(Fishing.getPixmapRoundedRectangle(500, 150, 10, dialogColor))
        dialogBackground = Sprite(dialogTexture)
        dialogBackground.setCenter((1280f/2f) + 70, 100f)

        shapeRender = ShapeRenderer()


        cat = Entity.fromImage("sprites/cat.png")
        Animation.fromFile(Gdx.files.internal("sprites/cat.json"), cat)
        cat.scale(14f)
        cat.setCenter(900f, -35f)

        val div = if (text.length > 25) 2.5f else 2f
        val mult = if (text.length > 25) 0.8f else 1f

        dialog = Label(text, skin, "bold")
        dialog.setPosition(dialogBackground.x + (dialogBackground.width / 2f) - (dialog.width / div), 100f)
        dialog.color = Color.WHITE
        dialog.setFontScale(mult)

        dialogShadow = Label(text, skin, "bold")
        dialogShadow.setPosition((dialogBackground.x + (dialogBackground.width / 2f) - (dialogShadow.width / div)) - 4f, 100f - 4f)
        dialogShadow.color = Color(0f, 0f, 0f, 0.4f)
        dialogShadow.setFontScale(mult)

        stage.addActor(dialogShadow)
        stage.addActor(dialog)
    }

    private var wasPressed = false
    override fun render(camera: OrthographicCamera, batch: SpriteBatch) {
        cat.tick()
        batch.begin()
        cat.draw(batch)
        dialogBackground.draw(batch)
        batch.end()

        shapeRender.projectionMatrix = camera.combined
        shapeRender.begin(ShapeRenderer.ShapeType.Filled)
        shapeRender.triangle(460f, 10f, 480f, 40f, 490f, 30f, dialogColor, dialogColor, dialogColor)
        shapeRender.end()

        stage.act()
        stage.draw()

        batch.color = Color.WHITE //reset color

        if (Gdx.input.isTouched && !wasPressed) {
            wasPressed = true
            if (onNext != null) {
                val notNullValue = onNext as Runnable
                notNullValue.run()
            }
        } else if (!Gdx.input.isTouched) wasPressed = false
    }

    override fun dispose() {
        Fishing.GAME.inputProcessor.removeProcessor(stage)
        stage.dispose()
        wasInit = false
    }

    fun setText(text: String) : CatDialog {
        this.text = text

        Fishing.getInstance().removeScene(this)
        Fishing.getInstance().addScene(this)

        return this
    }

    fun next(runnable: Runnable) : Scene {
        this.onNext = runnable
        if (this.onNext != null)
            showNextText()

        return this
    }

    private fun showNextText() {
        if (!wasInit) {
            Gdx.app.postRunnable {
                showNextText()
            }
            return
        }

        val next = Label("..Tap anywhere to continue..", skin, "bold")
        next.setPosition(dialogBackground.x + (dialogBackground.width / 2f) - 100f, 50f)
        next.color = Color.GRAY
        next.setFontScale(0.5f)

        stage.addActor(next)
    }
}