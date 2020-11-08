package edu.neu.madcourse.numad_phu.model;

public class BoundingRectangle {
    private Coordinates topLeft;
    private Coordinates bottomRight;

    public BoundingRectangle(Coordinates topLeft, Coordinates bottomRight) {
        this.topLeft = topLeft;
        this.bottomRight = bottomRight;
    }

    public Coordinates getTopLeft() {
        return topLeft;
    }

    public void setTopLeft(Coordinates topLeft) {
        this.topLeft = topLeft;
    }

    public Coordinates getBottomRight() {
        return bottomRight;
    }

    public void setBottomRight(Coordinates bottomRight) {
        this.bottomRight = bottomRight;
    }
}
