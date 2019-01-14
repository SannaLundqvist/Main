package com.example.a17salu03.battleships;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.Random;

public class CustomGridViewAdapter extends BaseAdapter {
    private int[] boardState; //0 vatten, 1-3 skepp 1, 4-5 skepp 2, 6 skepp 3
    private boolean isFriendlyBoard;
    private boolean isClickable;
    private ArrayList<Tile> tiles;
    private View view;
    private ImageView mImageView;
    private int rotateDegrees;

    public CustomGridViewAdapter(int[] boardState, boolean isFriendly, boolean isClickable, ArrayList<Tile> tiles, View view) {
        this.boardState = boardState;
        isFriendlyBoard = isFriendly;
        this.isClickable = isClickable;
        this.tiles = tiles;
        this.view = view;

    }

    public int getCount() {
        return boardState.length;
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            mImageView = tiles.get(position).getTileImage();
            mImageView.setAdjustViewBounds(true);
            mImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            mImageView.setPadding(5, 5, 5, 5);
        } else {
            mImageView = (ImageView) convertView;
        }
        //bild

        tiles.get(position).getTileImage().setImageBitmap(combineImages(R.drawable.skepp_1r_w2_broken));
        return mImageView;
    }
    private void setDrawable(Tile tile, String shipId){
        switch (shipId){
            case :

        }
    }

    private Bitmap combineImages(int layerImage) {
        Bitmap bottomImage = BitmapFactory.decodeResource(view.getResources(), R.drawable.water_tile);
        Bitmap topImage = BitmapFactory.decodeResource(view.getResources(), layerImage);
        if (layerImage == R.drawable.broken_parts) {
            Random random = new Random();
            topImage = rotateBitmap(topImage, random.nextInt(360));
        }

        Bitmap combined = Bitmap.createBitmap(bottomImage.getWidth(), bottomImage.getHeight(), bottomImage.getConfig());
        Canvas canvas = new Canvas(combined);
        canvas.drawBitmap(bottomImage, 0, 0, null);
        canvas.drawBitmap(topImage, 0, 0, null);

        return combined;
    }

    private Bitmap rotateBitmap(Bitmap original, int degrees) {
        int width = original.getWidth();
        int height = original.getHeight();

        Matrix matrix = new Matrix();
        matrix.preRotate(degrees);

        return Bitmap.createBitmap(original, 0, 0, width, height, matrix, true);
    }
}
