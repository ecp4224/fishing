package com.boxtrotstudio.fishing.handlers.scenes

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.scenes.scene2d.Stage
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

    override fun onInit() {
        stage = Stage(
                Fishing.getInstance().viewport,
                Fishing.getInstance().batch
        )
        Fishing.game.inputProcessor.addProcessor(stage)

        val skin = Skin(Gdx.files.internal("sprites/ui/uiskin.json"))

        cat = Entity.fromImage("sprites/cat.png")
        Animation.fromFile(Gdx.files.internal("sprites/cat.json"), cat)
        cat.scale(6f)
        cat.setCenter(20f, 20f)

        list = List<String>(skin)
        list.setItems("Test 1", "Test 2", "Test 3")

        val scrollPane = ScrollPane(list, skin)
        scrollPane.setSmoothScrolling(true)
        scrollPane.setPosition(1280f / 2f, 720f / 2f)
        scrollPane.isTransform = true
        scrollPane.setScale(0.5f)

        stage.addActor(scrollPane)
    }

    override fun render(camera: OrthographicCamera, batch: SpriteBatch) {
        batch.begin()
        cat.draw(batch)
        batch.end()

        stage.act()
        stage.draw()
    }

    override fun dispose() {
        Fishing.game.inputProcessor.removeProcessor(stage)
        stage.dispose()
    }

}