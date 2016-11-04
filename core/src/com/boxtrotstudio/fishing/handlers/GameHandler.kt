package com.boxtrotstudio.fishing.handlers

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.InputMultiplexer
import com.boxtrotstudio.fishing.Fishing
import com.boxtrotstudio.fishing.core.game.Entity
import com.boxtrotstudio.fishing.core.game.fishing.*
import com.boxtrotstudio.fishing.core.game.fishing.rods.PlayerRod
import com.boxtrotstudio.fishing.core.game.fishing.worlds.TestWorld
import com.boxtrotstudio.fishing.core.logic.Handler
import com.boxtrotstudio.fishing.handlers.scenes.*
import com.boxtrotstudio.fishing.utils.Global

class GameHandler() : Handler {
    lateinit var world: World

    lateinit var spriteScene: SpriteScene
    lateinit var uiSpriteScene: SpriteScene
    lateinit var inputProcessor: InputMultiplexer
    lateinit var shop: ShopScene
    lateinit var catDialog: CatDialog
    lateinit var bluredScene: BlurredScene
    val fishInventory = FishInventory()

    var lastSave = 0L
    public var tutorial = false
    override fun resume() {
        Fishing.GAME = this

        inputProcessor = InputMultiplexer()

        Gdx.input.inputProcessor = inputProcessor

        //We need to reload all assets
        val loading = LoadingScene(this)
        spriteScene = SpriteScene()
        uiSpriteScene = SpriteScene()

        uiSpriteScene.requestOrder(-100)

        loading.setLoadedCallback(Runnable {
            Fishing.getInstance().removeScene(loading)
            Fishing.getInstance().addScene(spriteScene)
            Fishing.getInstance().addScene(uiSpriteScene)
            val score = ScoreScene()
            score.requestOrder(-3)
            Fishing.getInstance().addScene(score)

            if (Fishing.getPlayer().isNew) {
                Fishing.getPlayer().isNew = false
                tutorial()
            }
        })

        Fishing.getInstance().addScene(loading)

        Global.GAME = this
    }

    override fun pause() {
        Fishing.CURRENTSAVE.save()
        Fishing.getInstance().clearScreen() //We need to clear the screen to save memory
    }

    override fun start() {
        Fishing.GAME = this

        inputProcessor = InputMultiplexer()

        Gdx.input.inputProcessor = inputProcessor

        val loading = LoadingScene(this)
        spriteScene = SpriteScene()
        uiSpriteScene = SpriteScene()

        uiSpriteScene.requestOrder(-100)

        loading.setLoadedCallback(Runnable {
            Fishing.getInstance().removeScene(loading)
            Fishing.getInstance().addScene(spriteScene)
            Fishing.getInstance().addScene(uiSpriteScene)
            val score = ScoreScene()
            score.requestOrder(-3)
            Fishing.getInstance().addScene(score)

            if (Fishing.getPlayer().isNew) {
                Fishing.getPlayer().isNew = false
                tutorial()
            }
        })

        Fishing.getInstance().addScene(loading)

        Global.GAME = this
    }

    override fun tick() {
        if (!tutorial) {
            if (Fishing.getNullablePlayer() != null)
                Fishing.getPlayer().tick()

            if (Fishing.getNullablePlayer() != null && Fishing.getPlayer().rod != null)
                Fishing.getPlayer().rod.tick()
        }

        if (Fishing.CURRENTSAVE != null) {
            if (System.currentTimeMillis() - lastSave >= 5000) {
                lastSave = System.currentTimeMillis()

                Fishing.CURRENTSAVE.save()
            }
        }
    }

    fun loadSaveState() {
        world = TestWorld()
        Fishing.CURRENTSAVE = SaveState.load(0)
        Fishing.CURRENTSAVE.player.load()
        Fishing.CURRENTSAVE.player.addMoney(10000)

        val background = Entity.fromTexture(world.backgroundTexture())
        background.zIndex = -100000

        spriteScene.addEntity(background)

        world.load(this)
    }

    private fun tutorial() {
        tutorial = true
        bluredScene = BlurredScene(spriteScene, 17f)
        Fishing.getInstance().addScene(bluredScene)
        Gdx.app.postRunnable {
            spriteScene.isVisible = false
        }

        catDialog = CatDialog("Welcome to Caturn!")
        Fishing.getInstance().addScene(catDialog)

         catDialog.next(Runnable {
            catDialog.setText("I'm glad you're here!").next(Runnable {
                catDialog.setText("You see, there's a high demand for fish").next(Runnable {
                    catDialog.setText("But I don't know how to catch fish..").next(Runnable {
                        catDialog.setText("I have these directions here...").next(Runnable {
                            catDialog.setText("It says to \"tap the screen\" to catch fish").next(Runnable {
                                catDialog.setText("But I don't know what a \"screen\" is..").next(Runnable {
                                    catDialog.setText("If you know what this \"screen\" is").next(Runnable {
                                        catDialog.setText("you'll be a very rich cat").next(Runnable {
                                            catDialog.setText("err I mean...human is it?").next(Runnable {
                                                catDialog.setText("Anyways, if you decide to partner with me").next(Runnable {
                                                    catDialog.setText("I'll give you all the profits").next(Runnable {
                                                        catDialog.setText("All I ask is for some fish").next(Runnable {
                                                            catDialog.setText("Gotta feed the kids..").next(Runnable {
                                                                catDialog.setText("I'll help by selling the fish").next(Runnable {
                                                                    catDialog.setText("Come to me if you want equipment").next(Runnable {
                                                                        catDialog.setText("I got some connections").next(Runnable {
                                                                            catDialog.setText("Good luck!").next(Runnable {
                                                                                Fishing.getInstance().removeScene(bluredScene)
                                                                                Fishing.getInstance().removeScene(catDialog)
                                                                                spriteScene.isVisible = true
                                                                                tutorial = false
                                                                            })
                                                                        })
                                                                    })
                                                                })
                                                            })
                                                        })
                                                    })
                                                })
                                            })
                                        })
                                    })
                                })
                            })
                        })
                    })
                })
            })
        })
    }

    var isShowingShop = false
    public fun showShop() {
        if (isShowingShop)
            return

        isShowingShop = true

        bluredScene = BlurredScene(spriteScene, 17f)
        Fishing.getInstance().addScene(bluredScene)
        Gdx.app.postRunnable {
            spriteScene.isVisible = false
        }

        catDialog = CatDialog("What should we get?")
        shop = ShopScene()
        shop.requestOrder(-5)
        Fishing.getInstance().addScene(shop)
        Fishing.getInstance().addScene(catDialog)
    }

    public fun hideShop() {
        if (!isShowingShop)
            return

        isShowingShop = false

        Fishing.getInstance().removeScene(bluredScene)
        Fishing.getInstance().removeScene(shop)
        Fishing.getInstance().removeScene(catDialog)
        spriteScene.isVisible = true
    }
}
