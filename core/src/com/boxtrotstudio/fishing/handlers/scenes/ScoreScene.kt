package com.boxtrotstudio.fishing.handlers.scenes

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.Skin
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.boxtrotstudio.fishing.Fishing
import com.boxtrotstudio.fishing.core.render.scene.AbstractScene

class ScoreScene : AbstractScene() {
    private lateinit var stage: Stage;
    private lateinit var fishCount: Label
    private lateinit var coinCount: Label
    override fun onInit() {
        stage = Stage(
                Fishing.getInstance().viewport,
                Fishing.getInstance().batch
        )
        Fishing.game.inputProcessor.addProcessor(stage)

        val skin = Skin(Gdx.files.internal("sprites/ui/uiskin.json"))

        var table = Table()
        table.width = 400f
        table.height = 100f
        table.x = 900f
        table.y = 10f
        stage.addActor(table)

        val fishIcon = Image(Texture(Gdx.files.internal("sprites/fish.png")))
        fishCount = Label("0", skin)
        fishCount.setFontScale(2f)

        val coinIcon = Image(Texture(Gdx.files.internal("sprites/coin.png")))
        coinCount = Label("0", skin)
        coinCount.setFontScale(2f)

        table.add(coinIcon).width(64f / 1.5f).height(64f / 1.5f).padRight(15f)
        table.add(coinCount).fill().padRight(20f)
        table.add(fishIcon).width(64f / 1.5f).height(64f / 1.5f).padRight(15f)
        table.add(fishCount).fill()

        stage.addActor(table)
    }

    override fun render(camera: OrthographicCamera, batch: SpriteBatch) {
        fishCount.setText(formatText(Fishing.player.fishInventory.size()))

        stage.act()
        stage.draw()
    }

    private fun formatText(size: Long): String {
        return size.toString()
    }

    override fun dispose() {
        Fishing.game.inputProcessor.removeProcessor(stage)
        stage.dispose()
    }

}