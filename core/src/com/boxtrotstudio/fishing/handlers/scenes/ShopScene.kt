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
import com.badlogic.gdx.scenes.scene2d.ui.List
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import com.badlogic.gdx.utils.Align
import com.badlogic.gdx.utils.Array
import com.boxtrotstudio.fishing.Fishing
import com.boxtrotstudio.fishing.core.game.Entity
import com.boxtrotstudio.fishing.core.game.animations.Animation
import com.boxtrotstudio.fishing.core.render.Text
import com.boxtrotstudio.fishing.core.render.ToastText
import com.boxtrotstudio.fishing.core.render.scene.AbstractScene
import com.boxtrotstudio.fishing.utils.Vector2f
import de.tomgrill.gdxdialogs.core.dialogs.GDXButtonDialog
import de.tomgrill.gdxdialogs.core.listener.ButtonClickListener

class ShopScene : AbstractScene() {
    lateinit var stage: Stage
    lateinit var scrollPane: ScrollPane
    private var scrollLocation = 0f
    override fun onInit() {
        stage = Stage(
                Fishing.getInstance().viewport,
                Fishing.getInstance().batch
        )
        Fishing.GAME.inputProcessor.addProcessor(stage)

        val skin = Skin(Gdx.files.internal("sprites/ui/uiskin.json"))
        skin.getFont("light-font").region.texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear)

        //Construct shop items
        val table = Table(skin)

        for (i in Fishing.GAME.world.buyableItems().indices) {
            val item = Fishing.GAME.world.buyableItems()[i]

            val itemRow = Table(skin)
            if (item.image != null) {
                val image = Image(item.image)

                if (item.imageWidth != 0f && item.imageHeight != 0f)
                    itemRow.add(image).width(item.imageWidth).height(item.imageHeight).padRight(15f)
                else
                    itemRow.add(image).padRight(15f)
            }

            val descriptionRow = Table(skin)
            val title = Label(item.name, skin, "bold")
            val description = Label(item.description, skin, "light")
            description.setFontScale(0.8f)
            description.setWrap(true)

            title.setFontScale(1.3f)
            descriptionRow.add(title).fill()
            descriptionRow.row()
            descriptionRow.add(description).fill().width(370f)

            itemRow.add(descriptionRow).fill().padRight(15f)

            val buttonTable = Table(skin)
            if (!item.doesOwn()) {
                val imageButton = ImageButton(TextureRegionDrawable(Entity.fromImage("sprites/buy_up.png")), TextureRegionDrawable(Entity.fromImage("sprites/buy_down.png")))

                imageButton.addListener(object : ClickListener() {
                    override fun clicked(event: InputEvent?, x: Float, y: Float) {
                        if (Fishing.getPlayer().actualMoney >= item.cost) {
                            if (item.isPersistent)
                                Fishing.getPlayer().recordBuyItem(i)

                            item.onBuy()
                            Fishing.getPlayer().subtractMoney(item.cost)

                            val text = ToastText(Vector2f(1175f, 50f), "-${item.cost}", 500f)
                            text.goLeft()
                            text.color = Color.RED
                            text.toast(Fishing.GAME.uiSpriteScene)

                            if (item.doesOwn()) {
                                buttonTable.clear()

                                val text = Label("Already Own", skin, "bold")
                                text.color = Color.GRAY
                                text.setAlignment(Align.center)
                                buttonTable.add(text).fill().padBottom(5f)

                                buttonTable.row()

                                val coinTable = Table(skin)

                                val coinIcon = Image(Texture(Gdx.files.internal("sprites/coin.png")))
                                val sellLabel = Label("" + item.cost, skin, "bold")
                                coinTable.add(coinIcon).width(32f).height(32f).padRight(15f)
                                coinTable.add(sellLabel)

                                buttonTable.add(coinTable).fill()
                            }

                        } /*else {
                            val bDialog = Fishing.DIALOGS.newDialog(GDXButtonDialog::class.java)
                            bDialog.setTitle("Low on Funds!")
                            bDialog.setMessage("You don't have enough coins for ${item.name}!")

                            bDialog.addButton("Ok")

                            bDialog.build().show()
                        }*/
                    }
                })

                imageButton.setScale(1.3f)

                buttonTable.add(imageButton).fill().padBottom(5f)
            } else {
                val text = Label("Already Own", skin, "bold")
                text.color = Color.GRAY
                text.setAlignment(Align.center)
                buttonTable.add(text).fill().padBottom(5f)
            }
            buttonTable.row()

            val coinTable = Table(skin)

            val coinIcon = Image(Texture(Gdx.files.internal("sprites/coin.png")))
            val sellLabel = Label("" + item.cost, skin, "bold")
            coinTable.add(coinIcon).width(32f).height(32f).padRight(15f)
            coinTable.add(sellLabel)

            buttonTable.add(coinTable).fill()

            itemRow.add(buttonTable).fill()

            table.add(itemRow).padTop(30f).padRight(25f).padLeft(25f).padBottom(20f)
            table.row()
        }

        scrollPane = ScrollPane(table, skin)
        scrollPane.setSmoothScrolling(true)
        scrollPane.setScrollingDisabled(true, false)
        scrollPane.setPosition(1280 / 4f, 220f)
        scrollPane.width = 700f
        scrollPane.height = 450f
        scrollPane.isTransform = true

        //Construct fish inventory
        /*val fishTable = Table(skin)

        for (fishHolder in Fishing.player.fishInventory.inventory) {
            val itemRow = Table(skin)
            val fishTexture = Fishing.ASSETS.get<Texture>(fishHolder.fish.texturePath)

            itemRow.add(Image(fishTexture)).width(64f).height(64f).padRight(15f)

            val infoTable = Table(skin)
            val coinIcon = Image(Texture(Gdx.files.internal("sprites/coin.png")))
            val sellLabel = Label("" + fishHolder.fish.sellValue, skin, "bold")
            infoTable.add(coinIcon).width(32f).height(32f).padLeft(32f)
            infoTable.add(sellLabel)
            infoTable.row()

            val countLabel = Label("Count: " + fishHolder.count, skin, "light")
            infoTable.add()
            infoTable.add(countLabel)

            itemRow.add(infoTable)

            fishTable.add(itemRow).padTop(30f).padRight(25f).padLeft(25f).padBottom(20f)
            fishTable.row()
        }

        val scrollPane2 = ScrollPane(fishTable, skin)
        scrollPane2.setSmoothScrolling(true)
        scrollPane2.setScrollingDisabled(true, false)
        scrollPane2.setPosition(900f, 220f)
        scrollPane2.width = 300f
        scrollPane2.height = 450f
        scrollPane2.isTransform = true*/

        val backButton = ImageButton(TextureRegionDrawable(Entity.fromImage("sprites/back_up.png")), TextureRegionDrawable(Entity.fromImage("sprites/back_down.png")))
        backButton.setPosition(40f, 30f)
        backButton.addListener(object : ClickListener() {
            override fun clicked(event: InputEvent?, x: Float, y: Float) {
                Fishing.GAME.hideShop()
            }
        })

        backButton.image.setScale(1.3f)

        stage.addActor(scrollPane)
        //stage.addActor(scrollPane2)
        stage.addActor(backButton)

        //stage.setDebugAll(true)
    }

    override fun render(camera: OrthographicCamera, batch: SpriteBatch) {
        stage.act()
        stage.draw()

        batch.color = Color.WHITE //reset color
    }

    override fun dispose() {
        Fishing.GAME.inputProcessor.removeProcessor(stage)
        stage.dispose()
    }

}