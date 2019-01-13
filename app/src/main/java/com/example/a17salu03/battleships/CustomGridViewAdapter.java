package com.example.a17salu03.battleships;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import java.util.ArrayList;

public class CustomGridViewAdapter extends BaseAdapter {
    private int[] boardState; //0 vatten, 1-3 skepp 1, 4-5 skepp 2, 6 skepp 3
    private boolean isFriendlyBoard;
    private boolean isClickable;
    private ArrayList<Tile> tiles;
    private  ImageView mImageView;

    public CustomGridViewAdapter(int[] boardState, boolean isFriendly, boolean isClickable, ArrayList<Tile> tiles) {
        this.boardState = boardState;
        isFriendlyBoard = isFriendly;
        this.isClickable = isClickable;
        this.tiles = tiles;

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

        if (isFriendlyBoard) {
            if (boardState[position] == 0) {
                mImageView.setImageResource(R.drawable.water_tile);
            } else if (boardState[position] >= 1 && boardState[position] <= 3) {
                mImageView.setImageResource(R.drawable.skepp_1r);

            } else if (boardState[position] == 4 || boardState[position] == 5) {
                showShipSizeMedium(position);

            } else if (boardState[position] == 6) {
                showShipSizeLarge(position);
            } else {
                mImageView.setImageResource(R.drawable.water_tile);
            }
        } else if (!isFriendlyBoard) {
            if (boardState[position] < 10){
                mImageView.setImageResource(R.drawable.water_tile);
            }
            if (boardState[position] >= 11 && boardState[position] <= 13) {
                mImageView.setImageResource(R.drawable.skepp_1r);
            } else if (boardState[position] == 14 || boardState[position] == 15) {
                if (isShipDestroyed(position)) {
                   showShipSizeMedium(position);
                } else {
                    mImageView.setImageResource(R.drawable.skepp_2r);           // inte sönder
                }
            } else if (boardState[position] == 16) {
                if (isShipDestroyed(position))
                    showShipSizeLarge(position);
                else {
                    mImageView.setImageResource(R.drawable.skepp_3r);
                }
            }
        }

        if (isClickable){
            if (boardState[position] > 10){
                tiles.get(position).setClickDisabled(true);
            }
        }


        return mImageView;
    }

    private boolean isShipDestroyed(int position) {
        for (int i = 0; i < boardState.length; i++) {
            if (boardState[position] == boardState[i] - 10) {
                return false;
            }
        }
        return true;
    }

    private void showShipSizeMedium(int position){
        if (position < 42 && (boardState[position + 7] == boardState[position] || (boardState[position + 7] - 10) == (boardState[position]))) {
            mImageView.setImageResource(R.drawable.skepp_2r);
            mImageView.setRotation(90);
        } else if (position > 7 && (boardState[position - 7] == boardState[position] || (boardState[position - 7] - 10) == (boardState[position]))) {
            mImageView.setImageResource(R.drawable.skepp_2r);
            mImageView.setRotation(90);
        } else if (position != 48 && (boardState[position + 1] == boardState[position] || (boardState[position + 1] - 10) == (boardState[position]))) {
            mImageView.setImageResource(R.drawable.skepp_2r);
        } else if (position != 0 && (boardState[position - 1] == boardState[position] || (boardState[position - 1] - 10) == (boardState[position]))) {
            mImageView.setImageResource(R.drawable.skepp_2r);
        }
    }

    private void showShipSizeLarge(int position){
        if (position < 42 && (boardState[position + 7] == boardState[position] || (boardState[position + 7] - 10) == (boardState[position]))) {
            if (position < 35 && (boardState[position + 14] == boardState[position] || (boardState[position + 14] - 10) == (boardState[position]))) {
                mImageView.setImageResource(R.drawable.skepp_3r);
            } else if (position > 7 && (boardState[position - 7] == boardState[position] || (boardState[position - 7] - 10) == (boardState[position]))) {
                mImageView.setImageResource(R.drawable.skepp_3r);
            }
        } else if (position > 7 && (boardState[position - 7] == boardState[position] || (boardState[position - 7] - 10) == (boardState[position]))) {
            if (position > 14 && (boardState[position - 14] == boardState[position] || (boardState[position - 14] - 10) == (boardState[position]))) {
                mImageView.setImageResource(R.drawable.skepp_3r);
            } else if (position < 42 && (boardState[position + 7] == boardState[position] || (boardState[position + 7] - 10) == (boardState[position]))) {
                mImageView.setImageResource(R.drawable.skepp_3r);
            }
        } else if (position != 48 && (boardState[position + 1] == boardState[position] || (boardState[position + 1] - 10) == (boardState[position]))) {
            if (position != 47 && (boardState[position + 2] == boardState[position] || (boardState[position + 2] - 10) == (boardState[position]))) {
                mImageView.setImageResource(R.drawable.skepp_3r);
            } else if (position != 0 && (boardState[position - 1] == boardState[position] || (boardState[position - 1] - 10) == (boardState[position]))) {
                mImageView.setImageResource(R.drawable.skepp_3r);
            }
        } else if (position != 0 && (boardState[position - 1] == boardState[position] || (boardState[position - 1] - 10) == (boardState[position]))) {
            if (position != 1 && (boardState[position - 2] == boardState[position] || (boardState[position - 2] - 10) == (boardState[position]))) {
                mImageView.setImageResource(R.drawable.skepp_3r);
            } else if (position != 48 && (boardState[position + 1] == boardState[position] || (boardState[position + 1] - 10) == (boardState[position]))) {
                mImageView.setImageResource(R.drawable.skepp_3r);
            }
        }
    }
}
