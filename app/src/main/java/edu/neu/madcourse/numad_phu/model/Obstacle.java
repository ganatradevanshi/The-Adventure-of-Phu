package edu.neu.madcourse.numad_phu.model;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

public class Obstacle {

    private Coordinates topLeft;
    private int height;
    private int width;
    private int speedX;
    private int speedY;
    private Bitmap cloudImage;

    Obstacle(Coordinates topLeft, int height, int width, int imageId, Resources resources,
             int speedX, int speedY) {
        this.topLeft = topLeft;
        this.height = height;
        this.width = width;
        BitmapFactory.Options options = new BitmapFactory.Options();
        cloudImage = BitmapFactory.decodeResource(resources, imageId, options);
        cloudImage = Bitmap.createScaledBitmap(cloudImage, width, height, true);
        this.speedX = speedX;
        this.speedY = speedY;
    }

    /**
     * Moves the cloud up using the speed specified while cloud creation
     *
     * @return true iff the cloud is visible in the frame
     */
    public boolean move() {
        topLeft.setY(topLeft.getY() - speedY);
        topLeft.setX(topLeft.getX() + speedX);
        return topLeft.getY() > -height && topLeft.getX() > 0 && topLeft.getX() > -width;
    }

    public boolean isFullyInScreen(int viewHeight) {
        return topLeft.getY() + height <= viewHeight;
    }

    public BoundingRectangle getBoundingRectangle() {
        return new BoundingRectangle(topLeft, new Coordinates(topLeft.getX() + width,
                topLeft.getY() + height));
    }

    public void draw(Canvas canvas) {
        canvas.drawBitmap(cloudImage, topLeft.getX(), topLeft.getY(), null);
    }
}
