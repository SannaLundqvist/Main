package com.example.a17salu03.battleships;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.Random;

public class CustomGridViewAdapter extends BaseAdapter {
    private String[] boardState; //0 vatten, 1-3 skepp 1, 4-5 skepp 2, 6 skepp 3
    private boolean isFriendlyBoard;
    private boolean isClickable;
    private ArrayList<Tile> tiles;
    private View view;
    private  ImageView mImageView;
    private int rotateDegrees;

    public static final String TILE_TYPE_WATER = "W";
    public static final String TILE_TYPE_HIT = "HIT";
    public static final String TILE_TYPE_MISS = "MISS";
    public static final String TILE_TYPE_SIZE_1_SHIPID_1 = "1H";
    public static final String TILE_TYPE_SIZE_1_SHIPID_1_DAMAGED = "1HD";
    public static final String TILE_TYPE_SIZE_1_SHIPID_2 = "2H";
    public static final String TILE_TYPE_SIZE_1_SHIPID_2_DAMAGED = "2HD";
    public static final String TILE_TYPE_SIZE_1_SHIPID_3 = "3H";
    public static final String TILE_TYPE_SIZE_1_SHIPID_3_DAMAGED = "3HD";

    public static final String TILE_TYPE_SIZE_2_SHIPID_4_H_L = "4HL";
    public static final String TILE_TYPE_SIZE_2_SHIPID_4_H_L_DAMAGED = "4HLD";
    public static final String TILE_TYPE_SIZE_2_SHIPID_4_H_R = "4HR";
    public static final String TILE_TYPE_SIZE_2_SHIPID_4_H_R_DAMAGED = "4HRD";
    public static final String TILE_TYPE_SIZE_2_SHIPID_4_V_L = "4VL";
    public static final String TILE_TYPE_SIZE_2_SHIPID_4_V_L_DAMAGED = "4VLD";
    public static final String TILE_TYPE_SIZE_2_SHIPID_4_V_R = "4VR";
    public static final String TILE_TYPE_SIZE_2_SHIPID_4_V_R_DAMAGED = "4VRD";
    public static final String TILE_TYPE_SIZE_2_SHIPID_5_H_L = "5HL";
    public static final String TILE_TYPE_SIZE_2_SHIPID_5_H_L_DAMAGED = "5HLD";
    public static final String TILE_TYPE_SIZE_2_SHIPID_5_H_R = "5HR";
    public static final String TILE_TYPE_SIZE_2_SHIPID_5_H_R_DAMAGED = "5HRD";
    public static final String TILE_TYPE_SIZE_2_SHIPID_5_V_L = "5VL";
    public static final String TILE_TYPE_SIZE_2_SHIPID_5_V_L_DAMAGED = "5VLD";
    public static final String TILE_TYPE_SIZE_2_SHIPID_5_V_R = "5VR";
    public static final String TILE_TYPE_SIZE_2_SHIPID_5_V_R_DAMAGED = "5VRD";

    public static final String TILE_TYPE_SIZE_3_SHIPID_6_H_L = "6HL";
    public static final String TILE_TYPE_SIZE_3_SHIPID_6_H_L_DAMAGED = "6HLD";
    public static final String TILE_TYPE_SIZE_3_SHIPID_6_H_M = "6HM";
    public static final String TILE_TYPE_SIZE_3_SHIPID_6_H_M_DAMAGED = "6HMD";
    public static final String TILE_TYPE_SIZE_3_SHIPID_6_H_R = "6HR";
    public static final String TILE_TYPE_SIZE_3_SHIPID_6_H_R_DAMAGED = "6HRD";
    public static final String TILE_TYPE_SIZE_3_SHIPID_6_V_L = "6VL";
    public static final String TILE_TYPE_SIZE_3_SHIPID_6_V_L_DAMAGED = "6VLD";
    public static final String TILE_TYPE_SIZE_3_SHIPID_6_V_M = "6VM";
    public static final String TILE_TYPE_SIZE_3_SHIPID_6_V_M_DAMAGED = "6VMD";
    public static final String TILE_TYPE_SIZE_3_SHIPID_6_V_R = "6VR";
    public static final String TILE_TYPE_SIZE_3_SHIPID_6_V_R_DAMAGED = "6VRD";


    public CustomGridViewAdapter(String[] boardState, boolean isFriendly, boolean isClickable, ArrayList<Tile> tiles, View view) {
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

        setImage(position);

        return mImageView;
    }

    private void setImage(int position){
        if (isFriendlyBoard) {
            switch (boardState[position]){
                case TILE_TYPE_WATER:
                    mImageView.setImageResource(R.drawable.water_tile);
                    break;
                case TILE_TYPE_MISS:
                    mImageView.setImageBitmap(BitMapEdit.combineImages(R.drawable.water_tile, view, 0, false));
                    break;
                case TILE_TYPE_SIZE_1_SHIPID_1:
                    mImageView.setImageBitmap(BitMapEdit.combineImages(R.drawable.skepp_1r_w, view, 0, false));
                    break;
                case TILE_TYPE_SIZE_1_SHIPID_1_DAMAGED:
                    mImageView.setImageBitmap(BitMapEdit.combineImages(R.drawable.skepp_1r_w_broken, view, 0, false));
                    break;
                case TILE_TYPE_SIZE_1_SHIPID_2:
                    mImageView.setImageBitmap(BitMapEdit.combineImages(R.drawable.skepp_1r_w, view, 0, false));
                    break;
                case TILE_TYPE_SIZE_1_SHIPID_2_DAMAGED:
                    mImageView.setImageBitmap(BitMapEdit.combineImages(R.drawable.skepp_1r_w_broken, view, 0, false));
                    break;
                case TILE_TYPE_SIZE_1_SHIPID_3:
                    mImageView.setImageBitmap(BitMapEdit.combineImages(R.drawable.skepp_1r_w, view, 0, false));
                    break;
                case TILE_TYPE_SIZE_1_SHIPID_3_DAMAGED:
                    mImageView.setImageBitmap(BitMapEdit.combineImages(R.drawable.skepp_1r_w_broken, view, 0, false));
                    break;
                case TILE_TYPE_SIZE_2_SHIPID_4_H_L:
                    mImageView.setImageBitmap(BitMapEdit.combineImages(R.drawable.skepp_2r_w_l, view, 0, false));
                    break;
                case TILE_TYPE_SIZE_2_SHIPID_4_H_L_DAMAGED:
                    mImageView.setImageBitmap(BitMapEdit.combineImages(R.drawable.skepp_2r_w_l_broken, view, 0, false));
                    break;
                case TILE_TYPE_SIZE_2_SHIPID_4_H_R:
                    mImageView.setImageBitmap(BitMapEdit.combineImages(R.drawable.skepp_2r_w_r, view, 0, false));
                    break;
                case TILE_TYPE_SIZE_2_SHIPID_4_H_R_DAMAGED:
                    mImageView.setImageBitmap(BitMapEdit.combineImages(R.drawable.skepp_2r_w_r_broken, view, 0, false));
                    break;
                case TILE_TYPE_SIZE_2_SHIPID_4_V_L:
                    mImageView.setImageBitmap(BitMapEdit.combineImages(R.drawable.skepp_2r_w_l, view, 90, true));
                    break;
                case TILE_TYPE_SIZE_2_SHIPID_4_V_L_DAMAGED:
                    mImageView.setImageBitmap(BitMapEdit.combineImages(R.drawable.skepp_2r_w_l_broken, view, 90, true));
                    break;
                case TILE_TYPE_SIZE_2_SHIPID_4_V_R:
                    mImageView.setImageBitmap(BitMapEdit.combineImages(R.drawable.skepp_2r_w_r, view, 90, true));
                    break;
                case TILE_TYPE_SIZE_2_SHIPID_4_V_R_DAMAGED:
                    mImageView.setImageBitmap(BitMapEdit.combineImages(R.drawable.skepp_2r_w_r_broken, view, 90, true));
                    break;
                case TILE_TYPE_SIZE_2_SHIPID_5_H_L:
                    mImageView.setImageBitmap(BitMapEdit.combineImages(R.drawable.skepp_2r_w_l, view, 0, false));
                    break;
                case TILE_TYPE_SIZE_2_SHIPID_5_H_L_DAMAGED:
                    mImageView.setImageBitmap(BitMapEdit.combineImages(R.drawable.skepp_2r_w_l_broken, view, 0, false));
                    break;
                case TILE_TYPE_SIZE_2_SHIPID_5_H_R:
                    mImageView.setImageBitmap(BitMapEdit.combineImages(R.drawable.skepp_2r_w_r, view, 0, false));
                    break;
                case TILE_TYPE_SIZE_2_SHIPID_5_H_R_DAMAGED:
                    mImageView.setImageBitmap(BitMapEdit.combineImages(R.drawable.skepp_2r_w_r_broken, view, 0, false));
                    break;
                case TILE_TYPE_SIZE_2_SHIPID_5_V_L:
                    mImageView.setImageBitmap(BitMapEdit.combineImages(R.drawable.skepp_2r_w_l, view, 90, true));
                    break;
                case TILE_TYPE_SIZE_2_SHIPID_5_V_L_DAMAGED:
                    mImageView.setImageBitmap(BitMapEdit.combineImages(R.drawable.skepp_2r_w_l_broken, view, 90, true));
                    break;
                case TILE_TYPE_SIZE_2_SHIPID_5_V_R:
                    mImageView.setImageBitmap(BitMapEdit.combineImages(R.drawable.skepp_2r_w_r, view, 90, true));
                    break;
                case TILE_TYPE_SIZE_2_SHIPID_5_V_R_DAMAGED:
                    mImageView.setImageBitmap(BitMapEdit.combineImages(R.drawable.skepp_2r_w_r_broken, view, 90, true));
                    break;
                case TILE_TYPE_SIZE_3_SHIPID_6_H_L:
                    mImageView.setImageBitmap(BitMapEdit.combineImages(R.drawable.skepp_3r_w_l, view, 0, false));
                    break;
                case TILE_TYPE_SIZE_3_SHIPID_6_H_L_DAMAGED:
                    mImageView.setImageBitmap(BitMapEdit.combineImages(R.drawable.skepp_3r_w_l_broken, view, 0, false));
                    break;
                case TILE_TYPE_SIZE_3_SHIPID_6_H_M:
                    mImageView.setImageBitmap(BitMapEdit.combineImages(R.drawable.skepp_3r_w_m, view, 0, false));
                    break;
                case TILE_TYPE_SIZE_3_SHIPID_6_H_M_DAMAGED:
                    mImageView.setImageBitmap(BitMapEdit.combineImages(R.drawable.skepp_3r_w_m_broken, view, 0, false));
                    break;
                case TILE_TYPE_SIZE_3_SHIPID_6_H_R:
                    mImageView.setImageBitmap(BitMapEdit.combineImages(R.drawable.skepp_3r_w_r, view, 0, false));
                    break;
                case TILE_TYPE_SIZE_3_SHIPID_6_H_R_DAMAGED:
                    mImageView.setImageBitmap(BitMapEdit.combineImages(R.drawable.skepp_3r_w_r_broken, view, 0, false));
                    break;
                case TILE_TYPE_SIZE_3_SHIPID_6_V_L:
                    mImageView.setImageBitmap(BitMapEdit.combineImages(R.drawable.skepp_3r_w_l, view, 90, false));
                    break;
                case TILE_TYPE_SIZE_3_SHIPID_6_V_L_DAMAGED:
                    mImageView.setImageBitmap(BitMapEdit.combineImages(R.drawable.skepp_3r_w_l_broken, view, 90, false));
                    break;
                case TILE_TYPE_SIZE_3_SHIPID_6_V_M:
                    mImageView.setImageBitmap(BitMapEdit.combineImages(R.drawable.skepp_3r_w_m, view, 90, false));
                    break;
                case TILE_TYPE_SIZE_3_SHIPID_6_V_M_DAMAGED:
                    mImageView.setImageBitmap(BitMapEdit.combineImages(R.drawable.skepp_3r_w_m_broken, view, 90, false));
                    break;
                case TILE_TYPE_SIZE_3_SHIPID_6_V_R:
                    mImageView.setImageBitmap(BitMapEdit.combineImages(R.drawable.skepp_3r_w_r, view, 90, false));
                    break;
                case TILE_TYPE_SIZE_3_SHIPID_6_V_R_DAMAGED:
                    mImageView.setImageBitmap(BitMapEdit.combineImages(R.drawable.skepp_3r_w_r_broken, view, 90, false));
                    break;
            }
        } else if (!isFriendlyBoard){
            switch (boardState[position]){
                case TILE_TYPE_WATER:
                    mImageView.setImageResource(R.drawable.water_tile);
                    break;
                case TILE_TYPE_MISS:
                    mImageView.setImageBitmap(BitMapEdit.combineImages(R.drawable.water_tile, view, 0, false));
                    break;
                case TILE_TYPE_SIZE_1_SHIPID_1:
                    mImageView.setImageResource(R.drawable.water_tile);
                    break;
                case TILE_TYPE_SIZE_1_SHIPID_1_DAMAGED:
                    if (isShipDestroyed(boardState[position], position)){

                    }
                    mImageView.setImageBitmap(BitMapEdit.combineImages(R.drawable.skepp_1r_w_broken, view, 0, false));
                    break;
                case TILE_TYPE_SIZE_1_SHIPID_2:
                    mImageView.setImageResource(R.drawable.water_tile);
                    break;
                case TILE_TYPE_SIZE_1_SHIPID_2_DAMAGED:
                    mImageView.setImageBitmap(BitMapEdit.combineImages(R.drawable.skepp_1r_w_broken, view, 0, false));
                    break;
                case TILE_TYPE_SIZE_1_SHIPID_3:
                    mImageView.setImageResource(R.drawable.water_tile);
                    break;
                case TILE_TYPE_SIZE_1_SHIPID_3_DAMAGED:
                    mImageView.setImageBitmap(BitMapEdit.combineImages(R.drawable.skepp_1r_w_broken, view, 0, false));
                    break;
                case TILE_TYPE_SIZE_2_SHIPID_4_H_L:
                    mImageView.setImageResource(R.drawable.water_tile);
                    break;
                case TILE_TYPE_SIZE_2_SHIPID_4_H_L_DAMAGED:
                    mImageView.setImageBitmap(BitMapEdit.combineImages(R.drawable.skepp_2r_w_l_broken, view, 0, false));
                    break;
                case TILE_TYPE_SIZE_2_SHIPID_4_H_R:
                    mImageView.setImageResource(R.drawable.water_tile);
                    break;
                case TILE_TYPE_SIZE_2_SHIPID_4_H_R_DAMAGED:
                    mImageView.setImageBitmap(BitMapEdit.combineImages(R.drawable.skepp_2r_w_r_broken, view, 0, false));
                    break;
                case TILE_TYPE_SIZE_2_SHIPID_4_V_L:
                    mImageView.setImageResource(R.drawable.water_tile);
                    break;
                case TILE_TYPE_SIZE_2_SHIPID_4_V_L_DAMAGED:
                    mImageView.setImageBitmap(BitMapEdit.combineImages(R.drawable.skepp_2r_w_l_broken, view, 90, true));
                    break;
                case TILE_TYPE_SIZE_2_SHIPID_4_V_R:
                    mImageView.setImageResource(R.drawable.water_tile);
                    break;
                case TILE_TYPE_SIZE_2_SHIPID_4_V_R_DAMAGED:
                    mImageView.setImageBitmap(BitMapEdit.combineImages(R.drawable.skepp_2r_w_r_broken, view, 90, true));
                    break;
                case TILE_TYPE_SIZE_2_SHIPID_5_H_L:
                    mImageView.setImageResource(R.drawable.water_tile);
                    break;
                case TILE_TYPE_SIZE_2_SHIPID_5_H_L_DAMAGED:
                    mImageView.setImageBitmap(BitMapEdit.combineImages(R.drawable.skepp_2r_w_l_broken, view, 0, false));
                    break;
                case TILE_TYPE_SIZE_2_SHIPID_5_H_R:
                    mImageView.setImageResource(R.drawable.water_tile);
                    break;
                case TILE_TYPE_SIZE_2_SHIPID_5_H_R_DAMAGED:
                    mImageView.setImageBitmap(BitMapEdit.combineImages(R.drawable.skepp_2r_w_r_broken, view, 0, false));
                    break;
                case TILE_TYPE_SIZE_2_SHIPID_5_V_L:
                    mImageView.setImageResource(R.drawable.water_tile);
                    break;
                case TILE_TYPE_SIZE_2_SHIPID_5_V_L_DAMAGED:
                    mImageView.setImageBitmap(BitMapEdit.combineImages(R.drawable.skepp_2r_w_l_broken, view, 90, true));
                    break;
                case TILE_TYPE_SIZE_2_SHIPID_5_V_R:
                    mImageView.setImageResource(R.drawable.water_tile);
                    break;
                case TILE_TYPE_SIZE_2_SHIPID_5_V_R_DAMAGED:
                    mImageView.setImageBitmap(BitMapEdit.combineImages(R.drawable.skepp_2r_w_r_broken, view, 90, true));
                    break;
                case TILE_TYPE_SIZE_3_SHIPID_6_H_L:
                    mImageView.setImageResource(R.drawable.water_tile);
                    break;
                case TILE_TYPE_SIZE_3_SHIPID_6_H_L_DAMAGED:
                    mImageView.setImageBitmap(BitMapEdit.combineImages(R.drawable.skepp_3r_w_l_broken, view, 0, false));
                    break;
                case TILE_TYPE_SIZE_3_SHIPID_6_H_M:
                    mImageView.setImageResource(R.drawable.water_tile);
                    break;
                case TILE_TYPE_SIZE_3_SHIPID_6_H_M_DAMAGED:
                    mImageView.setImageBitmap(BitMapEdit.combineImages(R.drawable.skepp_3r_w_m_broken, view, 0, false));
                    break;
                case TILE_TYPE_SIZE_3_SHIPID_6_H_R:
                    mImageView.setImageResource(R.drawable.water_tile);
                    break;
                case TILE_TYPE_SIZE_3_SHIPID_6_H_R_DAMAGED:
                    mImageView.setImageBitmap(BitMapEdit.combineImages(R.drawable.skepp_3r_w_r_broken, view, 0, false));
                    break;
                case TILE_TYPE_SIZE_3_SHIPID_6_V_L:
                    mImageView.setImageResource(R.drawable.water_tile);
                    break;
                case TILE_TYPE_SIZE_3_SHIPID_6_V_L_DAMAGED:
                    mImageView.setImageBitmap(BitMapEdit.combineImages(R.drawable.skepp_3r_w_l_broken, view, 90, false));
                    break;
                case TILE_TYPE_SIZE_3_SHIPID_6_V_M:
                    mImageView.setImageResource(R.drawable.water_tile);
                    break;
                case TILE_TYPE_SIZE_3_SHIPID_6_V_M_DAMAGED:
                    mImageView.setImageBitmap(BitMapEdit.combineImages(R.drawable.skepp_3r_w_m_broken, view, 90, false));
                    break;
                case TILE_TYPE_SIZE_3_SHIPID_6_V_R:
                    mImageView.setImageResource(R.drawable.water_tile);
                    break;
                case TILE_TYPE_SIZE_3_SHIPID_6_V_R_DAMAGED:
                    mImageView.setImageBitmap(BitMapEdit.combineImages(R.drawable.skepp_3r_w_r_broken, view, 90, false));
                    break;
            }
        }

    }

    private boolean isShipDestroyed(String value, int position) {
        boolean isDestroyed = false;
        if (value.charAt(0) <= '3'){
            isDestroyed = true;
        } else if (value.charAt(0) == '4' && value.charAt(0) == '5'){
            if (value.charAt(0) == boardState[position + 1].charAt(0) || value.charAt(0) == boardState[position - 1].charAt(0) || value.charAt(0) == boardState[position + 7].charAt(0) || value.charAt(0) == boardState[position - 7].charAt(0)){

            }
        } else if (value.charAt(0) == '6'){

        }
        return isDestroyed;
    }


}
