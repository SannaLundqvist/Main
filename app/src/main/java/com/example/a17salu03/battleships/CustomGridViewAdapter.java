package com.example.a17salu03.battleships;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

public class CustomGridViewAdapter extends BaseAdapter {
    private Context mContext;
    private int[] boardState; //0 vatten, 1-3 skepp 1, 4-5 skepp 2, 6 skepp 3
    private GridFragment gridFrag;
    private boolean isClickableTiles;

    public CustomGridViewAdapter(Context c, int[] boardState, GridFragment gridFrag, Boolean isClickableTiles) {
        mContext = c;
        this.boardState = boardState;
        this.gridFrag = gridFrag;
        this.isClickableTiles = isClickableTiles;
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
        ImageView mImageView;

        Tile tile;
        if (isClickableTiles) {
            tile = new ClickableTile(position, gridFrag.getView(), gridFrag);
        } else {
            tile = new Tile(position, gridFrag.getView());
        }

        if (convertView == null) {
            mImageView = tile.getTileImage();
            mImageView.setAdjustViewBounds(true);
            mImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            mImageView.setPadding(5, 5, 5, 5);
        } else {
            mImageView = (ImageView) convertView;
        }
        if (boardState[position] == 0) {
            mImageView.setImageResource(R.drawable.water_tile);
        } else if (boardState[position] >= 1 && boardState[position] <= 3) {
            mImageView.setImageResource(R.drawable.skepp_1r);

        } else if (boardState[position] >= 4 && boardState[position] <= 5) {
            if (boardState[position + 7] == boardState[position] || (boardState[position + 7] + 10) == (boardState[position])){
                mImageView.setImageResource(R.drawable.skepp_2r);
                mImageView.setRotation(90);
            } else if (boardState[position - 7] == boardState[position] || (boardState[position - 7] + 10) == (boardState[position])){
                mImageView.setImageResource(R.drawable.skepp_2r);
                mImageView.setRotation(90);
            } else if (boardState[position + 1] == boardState[position] || (boardState[position + 1] + 10) == (boardState[position])){
                mImageView.setImageResource(R.drawable.skepp_2r);
            } else if (boardState[position - 1] == boardState[position] || (boardState[position - 1] + 10) == (boardState[position])){
                mImageView.setImageResource(R.drawable.skepp_2r);
            }

        }else if (boardState[position] == 6 ) {
            if (boardState[position - 7] == boardState[position] || (boardState[position - 7] + 10) == (boardState[position])){
                if (boardState[position - 14] == boardState[position] || (boardState[position - 14] + 10) == (boardState[position])){
                    mImageView.setImageResource(R.drawable.skepp_3r);
                } else if (boardState[position + 7] == boardState[position] || (boardState[position + 7] + 10) == (boardState[position])){
                    mImageView.setImageResource(R.drawable.skepp_3r);
                }
            } else if (boardState[position] + 7 == boardState[position] || (boardState[position + 7] + 10) == (boardState[position])) {
                if (boardState[position] + 14 == boardState[position] || (boardState[position + 14] + 10) == (boardState[position])){
                    mImageView.setImageResource(R.drawable.skepp_3r);
                } else if (boardState[position] - 7 == boardState[position] || (boardState[position - 7] + 10) == (boardState[position])){
                    mImageView.setImageResource(R.drawable.skepp_3r);
                }
            } else if (boardState[position + 1] == boardState[position] || (boardState[position + 1] + 10) == (boardState[position])){
                if (boardState[position + 2] == boardState[position] || (boardState[position + 2] + 10) == (boardState[position])){
                    mImageView.setImageResource(R.drawable.skepp_3r);
                } else if (boardState[position - 1] == boardState[position] || (boardState[position - 1] + 10) == (boardState[position])){
                    mImageView.setImageResource(R.drawable.skepp_3r);
                }
                mImageView.setImageResource(R.drawable.skepp_3r);
            } else if (boardState[position - 1] == boardState[position] || (boardState[position - 1] + 10) == (boardState[position])){
                if (boardState[position - 2] == boardState[position] || (boardState[position - 2] + 10) == (boardState[position])){
                    mImageView.setImageResource(R.drawable.skepp_3r);
                } else if (boardState[position + 1] == boardState[position] || (boardState[position + 1] + 10) == (boardState[position])){
                    mImageView.setImageResource(R.drawable.skepp_3r);
                }
            }
        } else {
            mImageView.setImageResource(R.drawable.water_tile_border);
        }

        return mImageView;
    }
}
