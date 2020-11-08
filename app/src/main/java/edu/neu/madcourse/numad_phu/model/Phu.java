package edu.neu.madcourse.numad_phu.model;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

import edu.neu.madcourse.numad_phu.R;

public class Phu {
    Coordinates topLeft;
    private static final int PHU_IMAGE_ID = R.drawable.phunew;
    private Bitmap phuImage;

    public Phu(int phuX, int phuY, Resources resources, int width, int height) {
        this.topLeft = new Coordinates(phuX, phuY);
        BitmapFactory.Options options = new BitmapFactory.Options();
        this.phuImage = BitmapFactory.decodeResource(resources, PHU_IMAGE_ID, options);
        this.phuImage = Bitmap.createScaledBitmap(phuImage, width, height, true);
    }

    public void drawPhu(Canvas canvas) {
        canvas.drawBitmap(phuImage, topLeft.getX(), topLeft.getY(), null);
    }

}
