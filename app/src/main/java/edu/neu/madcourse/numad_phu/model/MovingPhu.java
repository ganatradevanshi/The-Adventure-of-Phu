package edu.neu.madcourse.numad_phu.model;

import android.content.res.Resources;

import static edu.neu.madcourse.numad_phu.utils.Constants.PHU_SPEEDX;
import static edu.neu.madcourse.numad_phu.utils.Constants.PHU_SPEEDY;

public class MovingPhu extends Phu {

    private int height;
    private int width;
    private int viewWidth;
    private int viewHeight;

    public MovingPhu(int phuX, int phuY, int viewWidth, int viewHeight, Resources resources, int width, int height) {
        super(phuX, phuY, resources, width, height);
        this.viewHeight = viewHeight;
        this.viewWidth = viewWidth;
        this.height = height;
        this.width = width;
    }

    private void constrainCoordinates() {
        if (topLeft.getX() <= 0) {
            topLeft.setX(0);
        } else if (topLeft.getX() > (viewWidth - width)) {
            topLeft.setX(viewWidth - width);
        }

        if (topLeft.getY() <= 0) {
            topLeft.setY(0);
        } else if (topLeft.getY() > (viewHeight - height)) {
            topLeft.setY(viewHeight - height);
        }
    }

    public void moveHorizontally(int value) {
        movePhu((width / 2 - value) / PHU_SPEEDX, 0);
    }

    public void moveVertically(int value) {
        movePhu(0, (height / 2 - value) / PHU_SPEEDY);
    }

    public void movePhu(double x, double y) {
        topLeft.setX(topLeft.getX() - (int)(x * PHU_SPEEDX));
        topLeft.setY(topLeft.getY() - (int)(y * PHU_SPEEDY));
        constrainCoordinates();
    }

    public void updateViewSize(int viewWidth, int viewHeight) {
        this.viewWidth = viewWidth;
        this.viewHeight = viewHeight;
    }

    public boolean hasCollided(BoundingRectangle boundingRectangle) {
        return check(boundingRectangle.getTopLeft().getX(),
                boundingRectangle.getBottomRight().getX(), topLeft.getX(),
                topLeft.getX() + width)
                && check(boundingRectangle.getTopLeft().getY(),
                boundingRectangle.getBottomRight().getY(), topLeft.getY(),
                topLeft.getY() + height);
    }

    private boolean check(int start1, int end1, int start2, int end2) {
        if(start1 <= start2) {
            return end1 >= start2;
        } else return start1 <= end2;
    }

    public Coordinates getCoordinates() {
        return topLeft;
    }

}
