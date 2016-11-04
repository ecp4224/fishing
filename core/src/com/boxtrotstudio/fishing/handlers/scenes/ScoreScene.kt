package com.boxtrotstudio.fishing.handlers.scenes

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.*
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import com.boxtrotstudio.fishing.Fishing
import com.boxtrotstudio.fishing.core.game.Entity
import com.boxtrotstudio.fishing.core.render.scene.AbstractScene

class ScoreScene : AbstractScene() {
    private var dialog: Label? = null
    private var dialogShadow: Label? = null

    private lateinit var stage: Stage;
    private lateinit var fishCount: Label
    private lateinit var coinCount: Label
    private lateinit var catCount: Label
    private lateinit var shapeRender: ShapeRenderer
    private lateinit var dialogBackground: Sprite

    private val dialogColor = Color(232f/255f,201f/255f,151f/255f,1f)
    override fun onInit() {
        stage = Stage(
                Fishing.getInstance().viewport,
                Fishing.getInstance().batch
        )
        Fishing.GAME.inputProcessor.addProcessor(stage)

        val skin = Skin(Gdx.files.internal("sprites/ui/uiskin.json"))

/*        val dialogTexture = Texture(Fishing.getPixmapRoundedRectangle(250, 100, 10, dialogColor))
        dialogBackground = Sprite(dialogTexture)
        dialogBackground.setCenter(960f, 100f)

        shapeRender = ShapeRenderer()*/

        var table = Table()
        table.width = 400f
        table.height = 100f
        table.x = 1000f
        table.y = 40f
        stage.addActor(table)

        val fishDrawable = TextureRegionDrawable(Entity.fromImage("sprites/fish.png"))
        val fishIcon = ImageButton(fishDrawable)
        fishCount = Label("0", skin)
        fishCount.setFontScale(2f)

        val coinIcon = Image(Texture(Gdx.files.internal("sprites/coin.png")))
        coinCount = Label("0", skin)
        coinCount.setFontScale(2f)

        /*val catDrawable = TextureRegionDrawable(Entity.fromImage("sprites/catHead.png"))
        val catIcon = ImageButton(catDrawable)*/
        val catIcon = Image(Texture(Gdx.files.internal("sprites/catHead.png")))
        catCount = Label("0", skin)
        catCount.setFontScale(2f)

        table.add(catIcon).width(64f / 1.5f).height(64f / 1.5f).padRight(15f)
        table.add(catCount).fill().padBottom(10f)
        table.row()
        table.add(fishIcon).width(64f / 1.5f).height(64f / 1.5f).padRight(15f)
        table.add(fishCount).fill().padBottom(10f)
        table.row()
        table.add(coinIcon).width(64f / 1.5f).height(64f / 1.5f).padRight(15f)
        table.add(coinCount).fill()


        stage.addActor(table)
    }

    private var lastFish = 0L
    private var lastCoin = 0L
    private var lastCat = 0L
    override fun render(camera: OrthographicCamera, batch: SpriteBatch) {
        if (Fishing.getNullablePlayer() == null)
            return

        val displayFish = Fishing.getPlayer().displayFishCount
        val displayMoney = Fishing.getPlayer().displayMoney
        val cats = Fishing.getPlayer().workerCount()

        if (lastFish != displayFish) {
            fishCount.setText(formatText(Fishing.getPlayer().displayFishCount))
            lastFish = displayFish
        }

        if (displayMoney != lastCoin) {
            coinCount.setText(formatText(Fishing.getPlayer().displayMoney))
            lastCoin = displayMoney
        }

        if (lastCat != cats) {
            catCount.setText(formatText(Fishing.getPlayer().workerCount()))
            lastCat = cats
        }

        stage.act()
        stage.draw()
    }

    private fun formatText(size: Long): String {
        return size.toString()
    }

    override fun dispose() {
        Fishing.GAME.inputProcessor.removeProcessor(stage)
        stage.dispose()
    }

}