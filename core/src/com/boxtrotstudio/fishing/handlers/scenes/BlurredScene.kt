package com.boxtrotstudio.fishing.handlers.scenes

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.Pixmap
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.graphics.glutils.FrameBuffer
import com.badlogic.gdx.graphics.glutils.ShaderProgram
import com.boxtrotstudio.fishing.Fishing
import com.boxtrotstudio.fishing.core.render.scene.AbstractScene
import com.boxtrotstudio.fishing.core.render.scene.Scene
import com.boxtrotstudio.fishing.handlers.scenes.SpriteScene

class BlurredScene(val original: Scene, val radius: Float) : AbstractScene() {
    private lateinit var targetA: FrameBuffer
    private lateinit var targetB: FrameBuffer
    private lateinit var regionA: TextureRegion
    private lateinit var regionB: TextureRegion
    private lateinit var shader: ShaderProgram

    override fun init() {
        targetA = FrameBuffer(Pixmap.Format.RGBA8888, original.width, original.height, false)
        regionA = TextureRegion(targetA.colorBufferTexture, targetA.width, targetA.height)
        regionA.flip(false, true) //Flip the y-axis

        targetB = FrameBuffer(Pixmap.Format.RGBA8888, original.width, original.height, false)
        regionB = TextureRegion(targetB.colorBufferTexture, targetB.width, targetB.height)
        regionB.flip(false, true) //Flip the y-axis

        regionA.texture?.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear)
        regionA.texture?.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear)

        original.init()

        shader = ShaderProgram(Gdx.files.internal("shaders/blur.vert"), Gdx.files.internal("shaders/blur.frag"))

        if (shader.log.length !=0)
            System.out.println(shader.log);

        shader.begin()
        shader.setUniformf("dir", 0f, 0f)
        shader.setUniformf("resolution", original.width.toFloat())
        shader.setUniformf("radius", radius)
        shader.end()
    }

    override fun render(camera: OrthographicCamera, batch: SpriteBatch) {
        targetA.begin()

        Gdx.gl.glClearColor(0f, 0f, 0f, 0f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

        original.render(camera, batch)

        targetA.end()

        batch.shader = shader

        targetB.begin()

        batch.begin()

        shader.setUniformf("dir", 1f, 0f)
        shader.setUniformf("resolution", original.width.toFloat() * 8f)

        batch.draw(regionA, 0f, 0f)

        batch.flush()
        //batch.end()

        targetB.end()

        shader.setUniformf("dir", 0f, 1f)
        shader.setUniformf("resolution", original.height.toFloat() * 8f)
        //batch.begin()

        batch.draw(regionB, 0f, 0f)
        batch.flush()
        batch.end()

        if (original is SpriteScene) {
            Fishing.rayHandler.updateAndRender()
        }

        batch.shader = null
    }

    override fun dispose() {
        targetA.dispose()
        targetB.dispose()
    }
}
