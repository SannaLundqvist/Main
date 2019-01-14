package com.example.a17salu03.battleships;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.view.View;

import java.util.Random;

public class BitMapEdit {

    public static Bitmap combineImages(int layerImage, View view){
        Bitmap bottomImage = BitmapFactory.decodeResource(view.getResources(), R.drawable.water_tile);
        Bitmap topImage = BitmapFactory.decodeResource(view.getResources(), layerImage);
        if (layerImage == R.drawable.broken_parts){
            Random random = new Random();
            topImage = rotateBitmap(topImage, random.nextInt(360));
        }

        Bitmap combined = Bitmap.createBitmap(bottomImage.getWidth(), bottomImage.getHeight(), bottomImage.getConfig());
        Canvas canvas = new Canvas(combined);
        canvas.drawBitmap(bottomImage, 0, 0, null);
        canvas.drawBitmap(topImage, 0, 0, null);

        return combined;
    }

    public static Bitmap rotateBitmap(Bitmap original, int degrees) {
        int width = original.getWidth();
        int height = original.getHeight();

        Matrix matrix = new Matrix();
        matrix.preRotate(degrees);

        return Bitmap.createBitmap(original, 0, 0, width, height, matrix, true);
    }


}


