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

public class BitMapEdit {

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

    public static Bitmap combineImages(int layerImage, View view, int rotateDegrees, boolean doFlip){
        return combineImages(layerImage, R.drawable.water_tile, view, rotateDegrees, doFlip);
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

    public static Bitmap darkenBitMap(Bitmap bitmap) {
        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint(Color.WHITE);
        ColorFilter filter = new LightingColorFilter(0xFF7F7F7F, 0x00000000);
        paint.setColorFilter(filter);
        canvas.drawBitmap(bitmap, new Matrix(), paint);

        return bitmap;
    }
}


