package com.example.a17salu03.battleships;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.LightingColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.util.DisplayMetrics;
import android.view.View;

import java.util.Random;

/**
 * This is a class that is used for different kinds of editing during runtime on bitmaps.
 *
 * @author Mattias Melchior, Sanna Lundqvist
 */
public class BitMapEdit {

    /**
     * Combines two drawables into one bitmap by merging them together. You can also
     * rotate the layerImage and flip it vertically.
     *
     * @param layerImage the top image
     * @param baseImage the bottom image
     * @param view the view
     * @param rotateDegrees the number of degrees the image should be rotated
     * @param doFlip the value for flipping the image vertically or not
     * @return the combined bitmap
     */
    public static Bitmap combineImages(int layerImage, int baseImage, View view, int rotateDegrees, boolean doFlip){
        Bitmap bottomImage = BitmapFactory.decodeResource(view.getResources(), baseImage);
        Bitmap topImage = BitmapFactory.decodeResource(view.getResources(), layerImage);
        if (rotateDegrees > 0){
            topImage = rotateBitmap(topImage, rotateDegrees);
        } else if (rotateDegrees == 360){
            Random random = new Random();
            topImage = rotateBitmap(topImage, random.nextInt(360));
        }
        if (doFlip){
            topImage = flip(topImage);
        }

        Bitmap combined = Bitmap.createBitmap(bottomImage.getWidth(), bottomImage.getHeight(), bottomImage.getConfig());
        Canvas canvas = new Canvas(combined);
        canvas.drawBitmap(bottomImage, 0, 0, null);
        canvas.drawBitmap(topImage, 0, 0, null);

        return combined;

    }

    /**
     * A overloaded combineImages method that uses the most common tile type in this game as the base
     * image and calls the other combineImages with that tile type.
     *
     * @param layerImage the top image
     * @param view the view
     * @param rotateDegrees the number of degrees the image should be rotated
     * @param doFlip the value for flipping the image vertically or not
     * @return the combined bitmap
     */
    public static Bitmap combineImages(int layerImage, View view, int rotateDegrees, boolean doFlip){
        return combineImages(layerImage, R.drawable.water_tile, view, rotateDegrees, doFlip);
    }

    /**
     * Rotates a bitmap.
     *
     * @param bitmap the bitmap
     * @param degrees the number of degrees the bitmap will be rotated
     * @return
     */
    public static Bitmap rotateBitmap(Bitmap bitmap, int degrees) {
        Matrix matrix = new Matrix();
        matrix.preRotate(degrees);

        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
    }

    /**
     * Flips a bitmap on the vertical axis.
     *
     * @param bitmap the bitmap
     * @return the bitmap but flipped
     */
    public static Bitmap flip(Bitmap bitmap) {
        Matrix m = new Matrix();
        m.preScale(-1, 1);

        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), m, false);
    }

    /**
     * Makes the bitmap lose some color making it darker in the progress.
     *
     * @param bitmap the bitmap
     * @return the darkened bitmap
     */
    public static Bitmap darkenBitMap(Bitmap bitmap) {
        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint(Color.WHITE);
        ColorFilter filter = new LightingColorFilter(0xFF7F7F7F, 0x00000000);
        paint.setColorFilter(filter);
        canvas.drawBitmap(bitmap, new Matrix(), paint);

        return bitmap;
    }
}


