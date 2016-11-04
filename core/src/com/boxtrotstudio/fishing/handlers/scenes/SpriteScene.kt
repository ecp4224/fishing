package com.boxtrotstudio.fishing.handlers.scenes

import box2dLight.RayHandler
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Matrix4
import com.badlogic.gdx.physics.box2d.Box2D
import com.boxtrotstudio.fishing.Fishing
import com.boxtrotstudio.fishing.core.logic.Logical
import com.boxtrotstudio.fishing.core.render.Blend
import com.boxtrotstudio.fishing.core.render.Drawable
import com.boxtrotstudio.fishing.core.render.scene.AbstractScene
import java.util.*

public class SpriteScene : AbstractScene() {
    private var sprites: HashMap<Blend, ArrayList<Drawable>> = HashMap();
    private var uiSprites: HashMap<Blend, ArrayList<Drawable>> = HashMap();
    private var isSpriteLooping: Boolean = false
    private var spritesToAdd: ArrayList<Drawable> = ArrayList()
    private var spritesToRemove: ArrayList<Drawable> = ArrayList()
    private var normalProjection = Matrix4()
    private var dirty = false

    override fun init() {
        Box2D.init()
        if (Fishing.rayHandler == null)
            Fishing.rayHandler = RayHandler(Fishing.getInstance().world)

        RayHandler.setGammaCorrection(true)
        RayHandler.isDiffuse = true
        Fishing.rayHandler.setAmbientLight(1f, 1f, 1f, 1f)
        Fishing.rayHandler.setBlurNum(3)

        normalProjection.setToOrtho2D(0f, 0f, Gdx.graphics.width.toFloat(), Gdx.graphics.height.toFloat());
    }

    override fun render(camera: OrthographicCamera, batch: SpriteBatch) {
        isSpriteLooping = true

        //Render all light sprites
        batch.begin()
        try {
            if (sprites.containsKey(Blend.DEFAULT)) {
                Blend.DEFAULT.apply(batch)
                val array = sprites[Blend.DEFAULT]
                for (sprite in array!!) {
                    if (!sprite.isVisible)
                        continue;

                    sprite.draw(batch)
                }
            }

            if (sprites.containsKey(Blend.ADDITIVE)) {
                Blend.ADDITIVE.apply(batch)
                val array = sprites[Blend.ADDITIVE]
                for (sprite in array!!) {
                    if (!sprite.isVisible)
                        continue;

                    sprite.draw(batch)
                }
            }

            Blend.DEFAULT.apply(batch)
        } catch (t: Throwable) {
            t.printStackTrace()
        }

        batch.end()

        Blend.ADDITIVE.apply(batch)
        Fishing.rayHandler.setCombinedMatrix(camera)
        Fishing.rayHandler.updateAndRender()
        Blend.DEFAULT.apply(batch)

        //Render UI sprites
        //val oldMatrix = batch.projectionMatrix
        /*batch.projectionMatrix = normalProjection;*/
        batch.begin();

        try {
            for (blend in uiSprites.keys) {
                if (blend.isDifferent(batch)) {
                    blend.apply(batch)
                }

                val array = uiSprites.get(blend) ?: continue
                for (ui in array) {
                    if (!ui.isVisible)
                        continue;

                    ui.draw(batch)
                }
            }
        } catch (t: Throwable) {
            t.printStackTrace()
        }

        batch.end()

        //batch.projectionMatrix = oldMatrix

        if (dirty) {
            sortSprites()
            dirty = false
        }

        isSpriteLooping = false

        updateSprites()
    }

    override fun dispose() {
    }

    fun updateSprites() {
        dirty = spritesToAdd.size > 0 || spritesToRemove.size > 0

        if (spritesToAdd.size > 0) {
            for (i in spritesToAdd.indices) {
                val toAdd = spritesToAdd[i]

                val map = if (toAdd.hasLighting()) sprites else uiSprites

                if (map.containsKey(toAdd.blendMode()))
                    map.get(toAdd.blendMode())?.add(toAdd)
                else {
                    val temp = ArrayList<Drawable>()
                    temp.add(toAdd)
                    map.put(toAdd.blendMode(), temp)
                }
            }

            //sprites.addAll(spritesToAdd)

            spritesToAdd.clear()
        }

        if (spritesToRemove.size > 0) {
            for (i in spritesToRemove.indices) {
                val toRemove = spritesToRemove[i]

                if (sprites.containsKey(toRemove.blendMode())) {
                    sprites.get(toRemove.blendMode())?.remove(toRemove)
                }
                if (uiSprites.containsKey(toRemove.blendMode())) {
                    uiSprites.get(toRemove.blendMode())?.remove(toRemove)
                }
            }

            //sprites.removeAll(spritesToRemove)

            spritesToRemove.clear()
        }
    }

    public fun sortSprites() {
        for (b in sprites.keys) {
            Collections.sort(sprites[b], { o1, o2 -> o1.zIndex - o2.zIndex })
            //Collections.sort(sprites, { o1, o2 -> o1.zIndex - o2.zIndex })
        }
    }

    public fun addEntity(entity: Drawable) {
        if (isSpriteLooping)
            spritesToAdd.add(entity)
        else {
            val map = if (entity.hasLighting()) sprites else uiSprites

            if (map.containsKey(entity.blendMode()))
                map.get(entity.blendMode())?.add(entity)
            else {
                val temp = ArrayList<Drawable>()
                temp.add(entity)
                map.put(entity.blendMode(), temp)
            }
            //sprites.add(entity)

            if (entity.hasLighting()) {
                dirty = true
            }
        }

        entity.parentScene = this
        entity.load()

        if (entity is Logical) {
            Fishing.getInstance().addLogical(entity)
        }
    }

    public fun removeEntity(entity: Drawable) {
        if (isSpriteLooping)
            spritesToRemove.add(entity)
        else {
            if (sprites.containsKey(entity.blendMode())) {
                sprites.get(entity.blendMode())?.remove(entity)
            }
            if (uiSprites.containsKey(entity.blendMode())) {
                uiSprites.get(entity.blendMode())?.remove(entity)
            }
            //sprites.remove(entity)
        }

        if (entity.hasLighting())
            dirty = true

        Gdx.app.postRunnable { entity.unload() }

        if (entity is Logical) {
            Fishing.getInstance().removeLogical(entity)
        }
    }

    fun clear() {
        sprites.clear()
        spritesToAdd.clear()
        spritesToRemove.clear()
        uiSprites.clear()
    }
}
