package com.boxtrotstudio.fishing.core.render;

import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Batch;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class Blend {
    private int srcFunc;
    private int dstFunc;

    @NotNull public static final Blend ADDITIVE = new Blend(GL20.GL_SRC_ALPHA, GL20.GL_ONE);
    @NotNull public static final Blend DEFAULT = new Blend(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

    public Blend(int srcFunc, int dstFunc) {
        this.srcFunc = srcFunc;
        this.dstFunc = dstFunc;
    }

    public static Blend fromBatch(Batch batch) {
        return new Blend(batch.getBlendSrcFunc(), batch.getBlendDstFunc());
    }

    public int getSrcFunc() {
        return srcFunc;
    }

    public int getDstFunc() {
        return dstFunc;
    }

    public void apply(Batch batch) {
        batch.setBlendFunction(srcFunc, dstFunc);
    }

    public boolean isDifferent(Batch batch) {
        return srcFunc != batch.getBlendSrcFunc() || dstFunc != batch.getBlendDstFunc();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Blend blend = (Blend) o;

        if (srcFunc != blend.srcFunc) return false;
        return dstFunc == blend.dstFunc;

    }

    @Override
    public int hashCode() {
        int result = srcFunc;
        result = 31 * result + dstFunc;
        return result;
    }

    @Override
    public String toString() {
        return "Blend{" +
                "srcFunc=" + srcFunc +
                ", dstFunc=" + dstFunc +
                '}';
    }
}
