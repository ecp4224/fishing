/*
 * Copyright (c) 2002-2008 LWJGL Project
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are
 * met:
 *
 * * Redistributions of source code must retain the above copyright
 *   notice, this list of conditions and the following disclaimer.
 *
 * * Redistributions in binary form must reproduce the above copyright
 *   notice, this list of conditions and the following disclaimer in the
 *   documentation and/or other materials provided with the distribution.
 *
 * * Neither the name of 'LWJGL' nor the names of
 *   its contributors may be used to endorse or promote products derived
 *   from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED
 * TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package com.boxtrotstudio.fishing.utils;

import java.nio.FloatBuffer;

/**
 *
 * Holds a 2-tuple vector.
 *
 * Taken from LWJGL (https://github.com/LWJGL/lwjgl/blob/master/src/java/org/lwjgl/util/vector/Vector2f.java)
 * @author cix_foo <cix_foo@users.sourceforge.net>
 * @version $Revision$
 * $Id$
 */
public class Vector2f {

    private static final long serialVersionUID = 1L;
    public static final Vector2f ZERO = new Vector2f(0, 0);

    public float x, y;

    /**
     * Constructor for Vector3f.
     */
    public Vector2f() {
        super();
    }

    /**
     * Constructor
     */
    public Vector2f(Vector2f src) {
        set(src);
    }

    /**
     * Constructor
     */
    public Vector2f(float x, float y) {
        set(x, y);
    }

    public Vector2f(float mag, double angle) {
        set(mag * Math.cos(angle), mag * Math.sin(angle));
    }

    private void set(double x, double y) {
        this.x = (float) x;
        this.y = (float) y;
    }

    /* (non-Javadoc)
     * @see org.lwjgl.util.vector.WritableVector2f#set(float, float)
     */
    public void set(float x, float y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Load from another Vector2f
     * @param src The source vector
     * @return this
     */
    public Vector2f set(Vector2f src) {
        x = src.getX();
        y = src.getY();
        return this;
    }

    /**
     * Normalise this vector
     * @return this
     */
    public final Vector2f normalise() {
        float len = length();
        if (len != 0.0f) {
            float l = 1.0f / len;
            return scale(l);
        } else
            throw new IllegalStateException("Zero length vector");
    }

    public final Vector2f clip(float xMin, float xMax, float yMin, float yMax) {
        if (x < xMin) {
            x = xMin;
        } else if (x > xMax) {
            x = xMax;
        }

        if (y < yMin) {
            y = yMin;
        } else if (y > yMax) {
            y = yMax;
        }

        return this;
    }

    /**
     * @return the length of the vector
     */
    public final float length() {
        return (float) Math.sqrt(lengthSquared());
    }

    /**
     * @return the length squared of the vector
     */
    public float lengthSquared() {
        return x * x + y * y;
    }

    /**
     * Translate a vector
     * @param x The translation in x
     * @param y the translation in y
     * @return this
     */
    public Vector2f translate(float x, float y) {
        this.x += x;
        this.y += y;
        return this;
    }

    /**
     * Negate a vector
     * @return this
     */
    public Vector2f negate() {
        x = -x;
        y = -y;
        return this;
    }

    /**
     * Negate a vector and place the result in a destination vector.
     * @param dest The destination vector or null if a new vector is to be created
     * @return the negated vector
     */
    public Vector2f negate(Vector2f dest) {
        if (dest == null)
            dest = new Vector2f();
        dest.x = -x;
        dest.y = -y;
        return dest;
    }


    /**
     * Normalise this vector and place the result in another vector.
     * @param dest The destination vector, or null if a new vector is to be created
     * @return the normalised vector
     */
    public Vector2f normalise(Vector2f dest) {
        float l = length();

        if (dest == null)
            dest = new Vector2f(x / l, y / l);
        else
            dest.set(x / l, y / l);

        return dest;
    }

    /**
     * The dot product of two vectors is calculated as
     * v1.x * v2.x + v1.y * v2.y + v1.z * v2.z
     * @param left The LHS vector
     * @param right The RHS vector
     * @return left dot right
     */
    public static float dot(Vector2f left, Vector2f right) {
        return left.x * right.x + left.y * right.y;
    }



    /**
     * Calculate the angle between two vectors, in radians
     * @param a A vector
     * @param b The other vector
     * @return the angle between the two vectors, in radians
     */
    public static float angle(Vector2f a, Vector2f b) {
        float dls = dot(a, b) / (a.length() * b.length());
        if (dls < -1f)
            dls = -1f;
        else if (dls > 1.0f)
            dls = 1.0f;
        return (float)Math.acos(dls);
    }

    public Vector2f add(Vector2f toAdd) {
        return Vector2f.add(this, toAdd, null);
    }

    public Vector2f sub(Vector2f toSub) {
        return Vector2f.sub(this, toSub, null);
    }

    /**
     * Add a vector to another vector and place the result in a destination
     * vector.
     * @param left The LHS vector
     * @param right The RHS vector
     * @param dest The destination vector, or null if a new vector is to be created
     * @return the sum of left and right in dest
     */
    public static Vector2f add(Vector2f left, Vector2f right, Vector2f dest) {
        if (dest == null)
            return new Vector2f(left.x + right.x, left.y + right.y);
        else {
            dest.set(left.x + right.x, left.y + right.y);
            return dest;
        }
    }

    /**
     * Subtract a vector from another vector and place the result in a destination
     * vector.
     * @param left The LHS vector
     * @param right The RHS vector
     * @param dest The destination vector, or null if a new vector is to be created
     * @return left minus right in dest
     */
    public static Vector2f sub(Vector2f left, Vector2f right, Vector2f dest) {
        if (dest == null)
            return new Vector2f(left.x - right.x, left.y - right.y);
        else {
            dest.set(left.x - right.x, left.y - right.y);
            return dest;
        }
    }

    /**
     * Store this vector in a FloatBuffer
     * @param buf The buffer to store it in, at the current position
     * @return this
     */
    public Vector2f store(FloatBuffer buf) {
        buf.put(x);
        buf.put(y);
        return this;
    }

    /**
     * Load this vector from a FloatBuffer
     * @param buf The buffer to load it from, at the current position
     * @return this
     */
    public Vector2f load(FloatBuffer buf) {
        x = buf.get();
        y = buf.get();
        return this;
    }

    /* (non-Javadoc)
     * @see org.lwjgl.vector.Vector#scale(float)
     */
    public Vector2f scale(float scale) {

        x *= scale;
        y *= scale;

        return this;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    public String toString() {
        StringBuilder sb = new StringBuilder(64);

        sb.append("Vector2f[");
        sb.append(x);
        sb.append(", ");
        sb.append(y);
        sb.append(']');
        return sb.toString();
    }

    /**
     * @return x
     */
    public final float getX() {
        return x;
    }

    /**
     * @return y
     */
    public final float getY() {
        return y;
    }

    /**
     * Set X
     * @param x
     */
    public final void setX(float x) {
        this.x = x;
    }

    /**
     * Set Y
     * @param y
     */
    public final void setY(float y) {
        this.y = y;
    }

    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        Vector2f other = (Vector2f)obj;

        if (x == other.x && y == other.y) return true;

        return false;
    }

    public Vector2f cloneVector() {
        return new Vector2f(x, y);
    }

    public static double distance(Vector2f position, Vector2f position2) {
        return Math.sqrt(((position2.getX() - position.getX()) * (position2.getX() - position.getX())) + ((position2.getY() - position.getY()) * (position2.getY() - position.getY())));
    }

    public Vector2f invert() {
        this.x *= -1;
        this.y *= -1;

        return this;
    }

    public Vector2f rotate(double radiusAdd) {
        float tempX = this.x;
        float tempY = this.y;

        this.x = (float) (tempX * Math.cos(radiusAdd) - tempY * Math.sin(radiusAdd));
        this.y = (float) (tempX * Math.sin(radiusAdd) + tempY * Math.cos(radiusAdd));

        return this;
    }

    public Direction getDirection() {
        double d = Math.toDegrees(Math.atan2(y, x));

        return Direction.fromDegrees(d);
    }
}
