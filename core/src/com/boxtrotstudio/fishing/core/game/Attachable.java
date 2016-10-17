package com.boxtrotstudio.fishing.core.game;

public interface Attachable {
    void attach(Attachable attach);

    void deattach(Attachable attach);

    float getX();

    float getY();

    void setX(float x);

    void setY(float y);

    void addParent(Attachable parent);

    void removeParent(Attachable parent);

    void setAlpha(float alpha);
}
