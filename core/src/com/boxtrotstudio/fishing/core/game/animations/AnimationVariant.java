package com.boxtrotstudio.fishing.core.game.animations;

public class AnimationVariant {
    private int x = -1;
    private int y = -1;
    private int width = -1;
    private int height = -1;
    private int framecount = -1;
    private int speed = -1;
    private String name;

    public static AnimationVariant fromAnimation(Animation animation) {
        AnimationVariant variant = new AnimationVariant();
        variant.x = animation.getX();
        variant.y = animation.getY();
        variant.width = animation.getWidth();
        variant.height = animation.getHeight();
        variant.framecount = animation.getFramecount();
        variant.speed = animation.getSpeed();
        variant.name = "";

        return variant;
    }

    private AnimationVariant() { }

    public void applyTo(Animation animation) {
        if (x != -1) {
            animation.setX(x);
        }
        if (y != -1) {
            animation.setY(y);
        }
        if (width != -1) {
            animation.setWidth(width);
        }
        if (height != -1) {
            animation.setHeight(height);
        }
        if (framecount != -1) {
            animation.setFramecount(framecount);
        }
        if (speed != -1) {
            animation.setSpeed(speed);
        }
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getFramecount() {
        return framecount;
    }

    public int getSpeed() {
        return speed;
    }

    public String getName() {
        return name;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setFramecount(int framecount) {
        this.framecount = framecount;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public void setName(String name) {
        this.name = name;
    }
}
