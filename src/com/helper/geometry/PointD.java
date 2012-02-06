package com.helper.geometry;

import java.awt.Point;

import com.helper.MathHelper;

@SuppressWarnings("unused")
public class PointD {
    private final static String tag = "PointD";

    public static PointD create(final Object obj) {
        if (obj instanceof PointD) {
            return (PointD) obj;
        }

        return new PointD(0, 0);
    }

    public double x;

    public double y;

    /**
     * Basic contructor.
     */
    public PointD() {
        set(0, 0);
    }

    /**
     * Creates a new PointD and initialise it with the given values.
     * 
     * @param x
     *            X position
     * @param y
     *            Y position
     */
    public PointD(final double x, final double y) {
        set(x, y);
    }

    /**
     * Creates a copy of the given PointD.
     * 
     * @param p
     *            The PointD to copy.
     */
    public PointD(final PointD p) {
        set(p);
    }

    /**
     * Creates a copy of the given PointI.
     * 
     * @param p
     *            The PointI to copy.
     */
    public PointD(final PointI p) {
        set(p);
    }

    /**
     * Return the point's argument (in the complex meaning) as degrees.
     * 
     * @return The angle, in degrees. 0 degree = top orientation.
     */
    public double arg() {
        return 180 / Math.PI * Math.atan2(x, -y);
    }

    public double arg(final PointD p) {
        return p.arg() - arg();
    }

    @Override
    public PointD clone() {
        return new PointD(this);
    }

    /**
     * Adds the value to the current point, and returns the current point.<br>
     * This method alters the current point !
     * 
     * @param x
     *            X value to add.
     * @param y
     *            Y value to add.
     * @return The current point.
     */
    public PointD doAdd(final double x, final double y) {
        this.x += x;
        this.y += y;
        return this;
    }

    /**
     * Adds the value to the current point, and returns the current point.<br>
     * This method alters the current point !
     * 
     * @param point
     *            The point to add to this one.
     * @return The current point.
     */
    public PointD doAdd(final PointD point) {
        return doAdd(point.x, point.y);
    }

    public PointD doDivide(final double d) {
        return doDivide(d, d);
    }

    public PointD doDivide(final double x, final double y) {
        this.x /= x;
        this.y /= y;
        return this;
    }

    public PointD doDivide(final PointD point) {
        return doDivide(point.x, point.y);
    }

    public PointD doInvert() {
        x = (x == 0) ? 0 : 1 / x;
        y = (y == 0) ? 0 : 1 / y;
        return this;
    }

    public PointD doMinus() {
        x = -x;
        y = -y;
        return this;
    }

    private PointD doMinus(final double x, final double y) {
        this.x -= x;
        this.y -= y;
        return this;
    }

    public PointD doMinus(final PointD point) {
        return doMinus(point.x, point.y);
    }

    public PointD doMultiply(final double d) {
        x *= d;
        y *= d;
        return this;
    }

    public PointD doMultiply(final double x, final double y) {
        this.x *= x;
        this.y *= y;
        return this;
    }

    public PointD doMultiply(final PointD point) {
        return doMultiply(point.x, point.y);
    }

    public PointD doRotate(final double angle) {
        if (angle < 0)
            return doRotate(-angle);
        if (angle > 360)
            return doRotate(angle - 360);

        if (angle == 0)
            return this;

        if (angle == 90) {
            set(-y, x);
            return this;
        }
        if (angle == 180) {
            set(-x, -y);
            return this;
        }
        if (angle == 270) {
            set(y, -x);
            return this;
        }

        double a = MathHelper.radians(angle);
        set(x * Math.cos(a) - y * Math.sin(a),
                y * Math.cos(a) + x * Math.sin(a));
        return this;
    }

    @Override
    public boolean equals(final Object o) {
        if (o instanceof PointD) {
            PointD p = (PointD) o;
            return p.x == x && p.y == y;
        }
        return false;
    }

    /**
     * Returns one of the coordinates of the point.
     * 
     * @param dim
     *            The dimension to get.
     * @return The X value if <b>dim = 0</b>, the Y value if <b>dim = 1</b>.
     */
    public double get(final int dim) {
        return (dim == 0) ? x : y;
    }

    public PointD getAdd(final double x, final double y) {
        return clone().doAdd(x, y);
    }

    /**
     * Returns the addition of this point and the given point.<br />
     * Does NOT alter the current point.
     * 
     * @param point
     *            The point to add to this one.
     * @return The addition of the two points.
     */
    public PointD getAdd(final PointD point) {
        return clone().doAdd(point);
    }

    public double getCrossProduct(PointD p) {
        return x * p.y - y * p.x;
    }

    public double getDotProduct(final PointD p) {
        return p.x * x + p.y * y;
    }

    public PointD getInvert() {
        return clone().doInvert();
    }

    public PointD getMinus() {
        return clone().doMinus();
    }

    public PointD getMinus(final PointD point) {
        return clone().doMinus(point);
    }

    public PointD getMultiply(final double d) {
        return clone().doMultiply(d);
    }

    public PointD getMultiply(final PointD point) {
        return clone().doMultiply(point);
    }

    public PointD getNormalize() {
        return getNormalize(1);
    }

    public PointD getNormalize(final double d) {
        return clone().normalize(d);
    }

    public PointD getRotate(final double angle) {
        return clone().doRotate(angle);
    }

    @Override
    public int hashCode() {
        throw new UnsupportedOperationException();
    }

    public double length() {
        return Math.sqrt(square_length());
    }

    public double length(final double x, final double y) {
        return Math.sqrt(square_length(x, y));
    }

    public double length(final PointD p) {
        return length(p.x, p.y);
    }

    public PointD normalize() {
        return normalize(1);
    }

    public PointD normalize(final double d) {
        if (x == 0 && y == 0) {
            return this;
        }
        return doMultiply(d / length());
    }

    public PointI round() {
        return new PointI((int) Math.round(x), (int) Math.round(y));
    }

    public void roundTo(final PointI p) {
        p.set((int) Math.round(x), (int) Math.round(y));
    }

    public PointD set(final double nx, final double ny) {
        x = nx;
        y = ny;
        return this;
    }

    public void set(final int dim, final double value) {
        if (dim == 0) {
            x = value;
        } else {
            y = value;
        }

    }

    public PointD set(final Point p) {
        x = p.x;
        y = p.y;
        return this;
    }

    public PointD set(final PointD p) {
        x = p.x;
        y = p.y;
        return this;
    }

    public PointD set(final PointI p) {
        x = p.x;
        y = p.y;
        return this;
    }

    public double square_length() {
        return square_length(0, 0);
    }

    public double square_length(final double x, final double y) {
        double dx = this.x - x;
        double dy = this.y - y;
        return dx * dx + dy * dy;
    }

    public double square_length(final PointD p) {
        return square_length(p.x, p.y);
    }

    @Override
    public String toString() {
        return "[" + x + ":" + y + "]";
    }
}
