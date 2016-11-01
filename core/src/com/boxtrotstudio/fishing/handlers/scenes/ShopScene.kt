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
import com.badlogic.gdx.scenes.scene2d.ui.List
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane
import com.badlogic.gdx.scenes.scene2d.ui.Skin
import com.badlogic.gdx.utils.Array
import com.boxtrotstudio.fishing.Fishing
import com.boxtrotstudio.fishing.core.game.Entity
import com.boxtrotstudio.fishing.core.game.animations.Animation
import com.boxtrotstudio.fishing.core.render.scene.AbstractScene

class ShopScene : AbstractScene() {
    lateinit var cat: Entity
    lateinit var stage: Stage
    lateinit var list: List<String>
    lateinit var dialogBackground: Sprite
    lateinit var shapeRender: ShapeRenderer
    val dialogColor = Color(232f/255f,201f/255f,151f/255f,1f)
    override fun onInit() {
        stage = Stage(
                Fishing.getInstance().viewport,
                Fishing.getInstance().batch
        )
        Fishing.game.inputProcessor.addProcessor(stage)

        val skin = Skin(Gdx.files.internal("sprites/ui/uiskin.json"))
        val dialogTexture = Texture(Fishing.getPixmapRoundedRectangle(500, 150, 10, dialogColor))
        dialogBackground = Sprite(dialogTexture)
        dialogBackground.setCenter((1280f/2f) + 70, 100f)

        shapeRender = ShapeRenderer()

        cat = Entity.fromImage("sprites/cat.png")
        Animation.fromFile(Gdx.files.internal("sprites/cat.json"), cat)
        cat.scale(14f)
        cat.setCenter(900f, -35f)

        list = List<String>(skin)
        list.setItems("Test 1", "Test 2", "Test 3")

        val scrollPane = ScrollPane(list, skin)
        scrollPane.setSmoothScrolling(true)
        scrollPane.setPosition(1280f / 2f, 720f / 2f)
        scrollPane.width = 100f
        scrollPane.height = 100f
        scrollPane.isTransform = true

        val dialog = Label("What would you like to buy?", skin)
        dialog.setPosition((1280f/2f) - 130f, 100f)
        dialog.color = Color.WHITE
        dialog.setFontScale(2f)

        val dialogShadow = Label("What would you like to buy?", skin)
        dialogShadow.setPosition(((1280f/2f) - 130f) - 4f, 100f - 4f)
        dialogShadow.color = Color(0f, 0f, 0f, 0.6f)
        dialogShadow.setFontScale(2f)

        stage.addActor(scrollPane)
        stage.addActor(dialogShadow)
        stage.addActor(dialog)

        //stage.setDebugAll(true)
    }

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
    }

    override fun dispose() {
        Fishing.game.inputProcessor.removeProcessor(stage)
        stage.dispose()
    }

}