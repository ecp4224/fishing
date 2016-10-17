package com.boxtrotstudio.fishing.core.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.boxtrotstudio.fishing.core.render.Text;

public class TextEntity extends Entity {
    private boolean dirty = true;
    private final Text text;

    private TextureRegion textureRegion;
    private FrameBuffer frameBuffer;

    public TextEntity(Text text) {
        super(new Sprite());
        this.text = text;
    }

    @Override
    public void draw(SpriteBatch batch) {
        if (text.getFont() == null)
            return;

        if (dirty) {
            generateTexture(batch);
            super.setTexture(textureRegion.getTexture());
            dirty = false;
        }

        super.draw(batch);
    }

    private void generateTexture(SpriteBatch batch) {
        if (frameBuffer == null) {
            frameBuffer = new FrameBuffer(Pixmap.Format.RGBA8888, text.getWidth(), text.getHeight(), false);
        }
        if (textureRegion == null) {
            textureRegion = new TextureRegion(frameBuffer.getColorBufferTexture(), frameBuffer.getWidth(), frameBuffer.getHeight());
        }

        frameBuffer.begin();

        Gdx.gl.glClearColor(0f, 0f, 0f, 0f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();

        text.draw(batch);

        batch.end();

        frameBuffer.end();
    }

    public void invalidate() {
        dirty = true;
    }
}
