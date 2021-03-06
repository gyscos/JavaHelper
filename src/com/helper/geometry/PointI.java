package com.helper.geometry;

public class PointI {
    public int x;
    public int y;

    public PointI() {
        this(0, 0);
    }

    public PointI(final int x, final int y) {
        set(x, y);
    }

    public PointI(final PointD p) {
        p.roundTo(this);
    }

    public PointI(final PointI p) {
        set(p);
    }

    @Override
    public PointI clone() {
        return new PointI(this);
    }

    /**
     * Clone this point into p
     * 
     * @param p
     *            Point to store the new data
     */
    public PointI clone(final PointI p) {
        p.x = x;
        p.y = y;
        return this;
    }

    /**
     * Adds the specified point to this one.
     * 
     * @param p
     *            Point to add
     */
    public PointI doAdd(final PointI p) {
        if (p == null)
            return this;

        x += p.x;
        y += p.y;

        return this;
    }

    public PointI doMinus(PointI p) {
        if (p == null)
            return this;

        x -= p.x;
        y -= p.y;

        return this;
    }

    public PointD getNormalize(double length) {
        return new PointD(this).normalize(length);
    }

    public double length() {
        return Math.sqrt(square_length());
    }

    public void minus() {
        x = -x;
        y = -y;
    }

    public void minus(final PointI p) {
        if (p == null)
            return;

        x -= p.x;
        y -= p.y;
    }

    public void set(final int x, final int y) {
        this.x = x;
        this.y = y;
    }

    public void set(final PointI p) {
        if (p == null)
            return;

        x = p.x;
        y = p.y;
    }

    public int square_length() {
        return x * x + y * y;
    }

    public PointD toPointD() {
        return new PointD(x, y);
    }
}
