package com.example.a17salu03.battleships;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.util.DisplayMetrics;
import android.view.View;

import java.util.Random;

public class BitMapEdit {

    public static Bitmap combineImages(int layerImage, View view, int rotateDegrees, boolean doFlip){
        Bitmap bottomImage = BitmapFactory.decodeResource(view.getResources(), R.drawable.water_tile);
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

    public static Bitmap rotateBitmap(Bitmap bitmap, int degrees) {
        Matrix matrix = new Matrix();
        matrix.preRotate(degrees);

        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
    }

    public static Bitmap flip(Bitmap bitmap) {
        Matrix m = new Matrix();
        m.preScale(-1, 1);

        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), m, false);
    }

}


