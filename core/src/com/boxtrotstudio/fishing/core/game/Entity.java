package com.boxtrotstudio.fishing.core.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.boxtrotstudio.fishing.Fishing;
import com.boxtrotstudio.fishing.core.game.animations.Animation;
import com.boxtrotstudio.fishing.core.game.animations.AnimationType;
import com.boxtrotstudio.fishing.core.logic.Logical;
import com.boxtrotstudio.fishing.core.render.Blend;
import com.boxtrotstudio.fishing.core.render.Drawable;
import com.boxtrotstudio.fishing.handlers.scenes.SpriteScene;
import com.boxtrotstudio.fishing.utils.Direction;
import com.boxtrotstudio.fishing.utils.Vector2f;
import com.boxtrotstudio.fishing.utils.annotations.InternalOnly;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class Entity extends Sprite implements Drawable, com.badlogic.gdx.scenes.scene2d.utils.Drawable, Logical, Attachable, Comparable<Entity> {
    private int z;
    private boolean hasLoaded = false;

    private String path;

    private Animation animation;
    private List<Animation> animations = new ArrayList<>();

    private Blend blend = Blend.DEFAULT;

    private ArrayList<Attachable> children = new ArrayList<Attachable>();
    private ArrayList<Attachable> parents = new ArrayList<Attachable>();

    private final Object child_lock = new Object();
    protected boolean lightable = true;
    private SpriteScene scene;

    private float leftWidth, rightWidth, topHeight, bottomHeight, minWidth, minHeight;

    private boolean isVisible = true;
    private Skin[] skins;
    private Vector2f velocity;
    private boolean hasGravity;
    private Runnable onClick;
    private float rotationVelocity;
    private float rotationAcceleration;

    private Color targetColor = Color.WHITE;
    private Color oldColor = Color.WHITE;
    private long colorStartTime;
    private long colorFadeDuration;
    private boolean isEaseColor;

    public static Entity fromTexture(Texture texture) {
        Sprite sprite = new Sprite(texture);
        Entity e = new Entity(sprite);
        e.path = texture.getClass().getCanonicalName();
        return e;
    }

    public static Entity fromImage(String path) {
        Texture texture = Fishing.ASSETS.get(path, Texture.class);
        Sprite sprite = new Sprite(texture);
        Entity e = new Entity(sprite);
        e.path = path;
        return e;
    }

    public static Entity fromImage(String path, short id) {
        Texture texture = Fishing.ASSETS.get(path, Texture.class);
        Sprite sprite = new Sprite(texture);
        Entity e = new Entity(sprite);
        e.path = path;
        return e;
    }

    public static Entity fromImage(FileHandle file) {
        Texture texture = new Texture(file);
        Sprite sprite = new Sprite(texture);
        Entity e = new Entity(sprite);
        e.path = file.name();
        return e;
    }

    public static Entity fromImage(FileHandle file, short id) {
        Texture texture = new Texture(file);
        Sprite sprite = new Sprite(texture);
        Entity e = new Entity(sprite);
        e.path = file.path();
        return e;
    }

    public Entity(Sprite sprite) {
        super(sprite);
        this.path = sprite.toString();

        setOriginCenter();
    }

    public Entity(String path) {
        super(Fishing.ASSETS.get(path, Texture.class));

        this.path = path;

        setOriginCenter();
    }

    public float getZ() {
        return z;
    }

    public void setZ(int z) {
        this.z = z;
    }

    @Override
    public Blend blendMode() {
        return blend;
    }

    @Override
    public boolean hasLighting() {
        return lightable;
    }

    @Override
    public int getZIndex() {
        return z;
    }

    public boolean isVisible() {
        return isVisible && getAlpha() > 0f;
    }

    public void setVisible(boolean visible) {
        isVisible = visible;
    }

    @Deprecated
    public void setHasLighting(boolean val) {
        boolean update = this.lightable != val;

        this.lightable = val;

        if (hasLoaded && update) {
            //We need to reload this sprite now
            scene.removeEntity(this);
            Gdx.app.postRunnable(new Runnable() {
                @Override
                public void run() {
                    scene.addEntity(Entity.this);
                }
            });
        }
    }

    public void setBlend(Blend blend) {
        this.blend = blend;
    }

    public float getCenterX() {
        return getX() + (getWidth() / 2f);
    }

    public float getCenterY() {
        return getY() + (getWidth() / 2f);
    }

    public float getAlpha() { return getColor().a; }

    public String getPath() {
        return path;
    }

    @InternalOnly
    public final void load() {
        onLoad();
        if (!hasLoaded)
            throw new IllegalStateException("super.onLoad() was not invoked!");
    }

    protected void onLoad() {
        hasLoaded = true;
    }

    @InternalOnly
    public final void unload() {
        onUnload();
        if (hasLoaded)
            throw new IllegalStateException("super.onUnload() was not invoked!");
    }

    protected void onUnload() {
        hasLoaded = false;
    }

    @Override
    public void draw(SpriteBatch batch) {
        super.draw(batch);
    }

    @Override
    public void setX(float x) {
        if (children.size() > 0) {
            float dif = getX() - x;
            super.setX(x);

            synchronized (child_lock) {
                for (Attachable c : children) {
                    c.setX(c.getX() - dif);
                }
            }
        } else {
            super.setX(x);
        }
    }

    @Override
    public void setAlpha(float alpha) {
        if (children.size() > 0) {
            super.setAlpha(alpha);

            synchronized (child_lock) {
                for (Attachable c : children) {
                    c.setAlpha(alpha);
                }
            }
        } else {
            super.setAlpha(alpha);
        }
    }

    @Override
    public void setY(float y) {
        if (children.size() > 0) {
            float dif = getY() - y;
            super.setY(y);

            synchronized (child_lock) {
                for (Attachable c : children) {
                    c.setY(c.getY() - dif);
                }
            }
        } else {
            super.setY(y);
        }
    }

    public void attach(Attachable e) {
        synchronized (child_lock) {
            children.add(e);
        }
        e.addParent(this);
    }

    public void deattach(Attachable e) {
        synchronized (child_lock) {
            children.remove(e);
        }
        e.removeParent(this);
    }

    @Override
    public void addParent(Attachable e) {
        parents.add(e);
    }

    @Override
    public void removeParent(Attachable e) {
        parents.remove(e);
    }

    private boolean touchDown;
    @Override
    public void tick() {
        if (onClick != null && !Fishing.GAME.getTutorial()) {
            if (Gdx.input.isTouched()) {
                Vector3 pos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
                pos.x += 80f;
                Fishing.getInstance().camera.unproject(pos);

                touchDown = contains(pos);
            } else if (touchDown) {
                onClick.run();
                touchDown = false;
            }
        }

        if (velocity != null) {
            if (hasGravity) {
                velocity.y -= 0.3;
            }

            setX(getX() + velocity.x);
            setY(getY() + velocity.y);
        }

        rotationVelocity += rotationAcceleration;
        if (getRotation() > 0) {
            rotate(rotationVelocity);
        } else if (getRotation() != 0f) {
            setRotation(0f);
        }

        if (isFadingOut) {
            float alpha = ease(1f, 0f, fadeDuration, System.currentTimeMillis() - fadeStart);

            setAlpha(alpha);

            if (alpha == 0f) {
                isFadingOut = false;
                if (isFadeOutDespawn) {
                    scene.removeEntity(this);
                }
            }
        }

        if (isEaseColor) {
            float r = ease(oldColor.r, targetColor.r, colorFadeDuration, System.currentTimeMillis() - colorStartTime);
            float g = ease(oldColor.g, targetColor.g, colorFadeDuration, System.currentTimeMillis() - colorStartTime);
            float b = ease(oldColor.b, targetColor.b, colorFadeDuration, System.currentTimeMillis() - colorStartTime);
            float a = ease(oldColor.a, targetColor.a, colorFadeDuration, System.currentTimeMillis() - colorStartTime);

            setColor(new Color(r, g, b, a));

            if (getColor().equals(targetColor)) {
                isEaseColor = false;
                targetColor = Color.WHITE;
            }
        }

        if (animation != null) {
            animation.tick();
            setRegion(animation.getTextureRegion());
        }
    }

    @Override
    public void dispose() { }


    @Override
    public SpriteScene getParentScene() {
        return scene;
    }

    @Override
    public void setParentScene(SpriteScene scene) {
        this.scene = scene;
    }

    public void setZIndex(int z) {
        this.z = z;

        if (hasLoaded) {
            //We need to reload this sprite now
            scene.removeEntity(this);
            Gdx.app.postRunnable(new Runnable() {
                @Override
                public void run() {
                    scene.addEntity(Entity.this);
                }
            });
        }
    }

    public boolean hasAnimations() {
        return animations.size() > 0;
    }

    public Animation getAnimation(AnimationType type, Direction direction) {
        for (Animation animation : animations) {
            if (animation.getType() == type && animation.getDirection() == direction) {
                return animation;
            }
        }
        return null;
    }

    //Code taken from: https://code.google.com/p/replicaisland/source/browse/trunk/src/com/replica/replicaisland/Lerp.java?r=5
    //Because I'm a no good dirty scrub
    public static float ease(float start, float target, float duration, float timeSinceStart) {
        float value = start;
        if (timeSinceStart > 0.0f && timeSinceStart < duration) {
            final float range = target - start;
            final float percent = timeSinceStart / (duration / 2.0f);
            if (percent < 1.0f) {
                value = start + ((range / 2.0f) * percent * percent * percent);
            } else {
                final float shiftedPercent = percent - 2.0f;
                value = start + ((range / 2.0f) *
                        ((shiftedPercent * shiftedPercent * shiftedPercent) + 2.0f));
            }
        } else if (timeSinceStart >= duration) {
            value = target;
        }
        return value;
    }

    public Vector2f getPosition() {
        return new Vector2f(getCenterX(), getCenterY());
    }

    public Animation getCurrentAnimation() {
        return animation;
    }

    @Override
    public int compareTo(@NotNull Entity o) {
        return z - o.z;
    }

    @Override
    public String toString() {
        return path;
    }

    public void setCurrentAnimation(Animation currentAnimation) {
        this.animation = currentAnimation;
        setSize(this.animation.getWidth(), this.animation.getHeight());
    }

    public void easeToColor(Color target, long duration) {
        this.isEaseColor = true;
        this.targetColor = target;
        this.oldColor = getColor();
        this.colorFadeDuration = duration;
        this.colorStartTime = System.currentTimeMillis();
    }

    public Color getTargetColor() {
        return targetColor;
    }

    private boolean isFadingOut;
    private boolean isFadeOutDespawn;
    private long fadeDuration;
    private long fadeStart;
    public void fadeOutAndDespawn(long arg) {
        this.fadeDuration = arg;
        this.isFadingOut = true;
        this.isFadeOutDespawn = true;
        fadeStart = System.currentTimeMillis();
    }

    public void fadeOut(long arg) {
        this.fadeDuration = arg;
        this.isFadingOut = true;
        fadeStart = System.currentTimeMillis();
    }

    public void attachAnimations(Animation... animations) {
        for (Animation animation : animations) {
            animation.attach(this);
            this.animations.add(animation);
        }

        setCurrentAnimation(this.animations.get(0));
    }

    public boolean contains(Vector3 pos) {
        return contains(pos.x, pos.y);
    }

    public boolean contains(Vector2f pos) {
        return contains(pos.x, pos.y);
    }

    public boolean contains(float x, float y) {
        return (x >= getX() && x <= getX() + (getWidth() * getScaleX() * 2f) && y >= getY() && y <= getY() + (getHeight() * 2f * getScaleY()));
    }

    public void attachSkins(Skin[] skins) {
        this.skins = skins;
    }

    public void setVelocity(Vector2f velocity) {
        this.velocity = velocity;
    }

    public Vector2f getVelocity() {
        return velocity;
    }

    public void hasGravity(boolean b) {
        this.hasGravity = b;
    }

    public boolean hasGravity() {
        return hasGravity;
    }

    public void onClick(Runnable runnable) {
        this.onClick = runnable;
    }

    @Override
    public void draw(Batch batch, float x, float y, float width, float height) {
        float oldx = this.getPosition().x;
        float oldy = this.getPosition().y;
        float oldWidth = this.getWidth();
        float oldHeight = this.getHeight();

        setX(x);
        setY(y);
        setSize(width, height);

        super.draw(batch);

        setX(oldx);
        setY(oldy);
        setSize(oldWidth, oldHeight);
    }

    @Override
    public float getLeftWidth() {
        return leftWidth;
    }

    @Override
    public void setLeftWidth(float leftWidth) {
        this.leftWidth = leftWidth;
    }

    @Override
    public float getRightWidth() {
        return rightWidth;
    }

    @Override
    public void setRightWidth(float rightWidth) {
        this.rightWidth = rightWidth;
    }

    @Override
    public float getTopHeight() {
        return topHeight;
    }

    @Override
    public void setTopHeight(float topHeight) {
        this.topHeight = topHeight;
    }

    @Override
    public float getBottomHeight() {
        return bottomHeight;
    }

    @Override
    public void setBottomHeight(float bottomHeight) {
        this.bottomHeight = bottomHeight;
    }

    @Override
    public float getMinWidth() {
        return minWidth;
    }

    @Override
    public void setMinWidth(float minWidth) {
        this.minWidth = minWidth;
    }

    @Override
    public float getMinHeight() {
        return minHeight;
    }

    @Override
    public void setMinHeight(float minHeight) {
        this.minHeight = minHeight;
    }

    public void setRotationVelocity(float rotationVelocity) {
        this.rotationVelocity = rotationVelocity;
    }

    public float getRotationVelocity() {
        return rotationVelocity;
    }

    public void setRotationAcceleration(float rotationAcceleration) {
        this.rotationAcceleration = rotationAcceleration;
    }

    public float getRotationAcceleration() {
        return rotationAcceleration;
    }
}
