package com.example.a17salu03.battleships;

import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.Random;

import static com.example.a17salu03.battleships.Tile.TILE_TYPE_MISS;
import static com.example.a17salu03.battleships.Tile.TILE_TYPE_SIZE_1_SHIPID_1;
import static com.example.a17salu03.battleships.Tile.TILE_TYPE_SIZE_1_SHIPID_1_DAMAGED;
import static com.example.a17salu03.battleships.Tile.TILE_TYPE_SIZE_1_SHIPID_2;
import static com.example.a17salu03.battleships.Tile.TILE_TYPE_SIZE_1_SHIPID_2_DAMAGED;
import static com.example.a17salu03.battleships.Tile.TILE_TYPE_SIZE_1_SHIPID_3;
import static com.example.a17salu03.battleships.Tile.TILE_TYPE_SIZE_1_SHIPID_3_DAMAGED;
import static com.example.a17salu03.battleships.Tile.TILE_TYPE_SIZE_2_SHIPID_4_H_L;
import static com.example.a17salu03.battleships.Tile.TILE_TYPE_SIZE_2_SHIPID_4_H_L_DAMAGED;
import static com.example.a17salu03.battleships.Tile.TILE_TYPE_SIZE_2_SHIPID_4_H_R;
import static com.example.a17salu03.battleships.Tile.TILE_TYPE_SIZE_2_SHIPID_4_H_R_DAMAGED;
import static com.example.a17salu03.battleships.Tile.TILE_TYPE_SIZE_2_SHIPID_4_V_L;
import static com.example.a17salu03.battleships.Tile.TILE_TYPE_SIZE_2_SHIPID_4_V_L_DAMAGED;
import static com.example.a17salu03.battleships.Tile.TILE_TYPE_SIZE_2_SHIPID_4_V_R;
import static com.example.a17salu03.battleships.Tile.TILE_TYPE_SIZE_2_SHIPID_4_V_R_DAMAGED;
import static com.example.a17salu03.battleships.Tile.TILE_TYPE_SIZE_2_SHIPID_5_H_L;
import static com.example.a17salu03.battleships.Tile.TILE_TYPE_SIZE_2_SHIPID_5_H_L_DAMAGED;
import static com.example.a17salu03.battleships.Tile.TILE_TYPE_SIZE_2_SHIPID_5_H_R;
import static com.example.a17salu03.battleships.Tile.TILE_TYPE_SIZE_2_SHIPID_5_H_R_DAMAGED;
import static com.example.a17salu03.battleships.Tile.TILE_TYPE_SIZE_2_SHIPID_5_V_L;
import static com.example.a17salu03.battleships.Tile.TILE_TYPE_SIZE_2_SHIPID_5_V_L_DAMAGED;
import static com.example.a17salu03.battleships.Tile.TILE_TYPE_SIZE_2_SHIPID_5_V_R;
import static com.example.a17salu03.battleships.Tile.TILE_TYPE_SIZE_2_SHIPID_5_V_R_DAMAGED;
import static com.example.a17salu03.battleships.Tile.TILE_TYPE_SIZE_3_SHIPID_6_H_L;
import static com.example.a17salu03.battleships.Tile.TILE_TYPE_SIZE_3_SHIPID_6_H_L_DAMAGED;
import static com.example.a17salu03.battleships.Tile.TILE_TYPE_SIZE_3_SHIPID_6_H_M;
import static com.example.a17salu03.battleships.Tile.TILE_TYPE_SIZE_3_SHIPID_6_H_M_DAMAGED;
import static com.example.a17salu03.battleships.Tile.TILE_TYPE_SIZE_3_SHIPID_6_H_R;
import static com.example.a17salu03.battleships.Tile.TILE_TYPE_SIZE_3_SHIPID_6_H_R_DAMAGED;
import static com.example.a17salu03.battleships.Tile.TILE_TYPE_SIZE_3_SHIPID_6_V_L;
import static com.example.a17salu03.battleships.Tile.TILE_TYPE_SIZE_3_SHIPID_6_V_L_DAMAGED;
import static com.example.a17salu03.battleships.Tile.TILE_TYPE_SIZE_3_SHIPID_6_V_M;
import static com.example.a17salu03.battleships.Tile.TILE_TYPE_SIZE_3_SHIPID_6_V_M_DAMAGED;
import static com.example.a17salu03.battleships.Tile.TILE_TYPE_SIZE_3_SHIPID_6_V_R;
import static com.example.a17salu03.battleships.Tile.TILE_TYPE_SIZE_3_SHIPID_6_V_R_DAMAGED;
import static com.example.a17salu03.battleships.Tile.TILE_TYPE_WATER;

public class CustomGridViewAdapter extends BaseAdapter {
    private String[] boardState; //0 vatten, 1-3 skepp 1, 4-5 skepp 2, 6 skepp 3
    private boolean isFriendlyBoard;
    private ArrayList<Tile> tiles;
    private View view;
    private ImageView mImageView;


    public CustomGridViewAdapter(String[] boardState, boolean friendly, ArrayList<Tile> tiles, View view) {
        this.boardState = boardState;
        isFriendlyBoard = friendly;
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

    private void setImage(int position) {
        if (isFriendlyBoard) {
            switch (boardState[position]) {
                case TILE_TYPE_WATER:
                    mImageView.setImageResource(R.drawable.water_tile);
                    break;
                case TILE_TYPE_MISS:
                    mImageView.setImageBitmap(BitMapEdit.combineImages(R.drawable.water_tile_miss, view, 0, false));
                    break;
                case TILE_TYPE_SIZE_1_SHIPID_1:
                    mImageView.setImageBitmap(BitMapEdit.combineImages(R.drawable.skepp_1r_w, view, 0, false));
                    break;
                case TILE_TYPE_SIZE_1_SHIPID_1_DAMAGED:
                    mImageView.setImageBitmap(BitMapEdit.darkenBitMap(BitMapEdit.combineImages(R.drawable.skepp_1r_w_broken, view, 0, false)));
                    break;
                case TILE_TYPE_SIZE_1_SHIPID_2:
                    mImageView.setImageBitmap(BitMapEdit.combineImages(R.drawable.skepp_1r_w, view, 0, false));
                    break;
                case TILE_TYPE_SIZE_1_SHIPID_2_DAMAGED:
                    mImageView.setImageBitmap(BitMapEdit.darkenBitMap(BitMapEdit.combineImages(R.drawable.skepp_1r_w_broken, view, 0, false)));
                    break;
                case TILE_TYPE_SIZE_1_SHIPID_3:
                    mImageView.setImageBitmap(BitMapEdit.combineImages(R.drawable.skepp_1r_w, view, 0, false));
                    break;
                case TILE_TYPE_SIZE_1_SHIPID_3_DAMAGED:
                    mImageView.setImageBitmap(BitMapEdit.darkenBitMap(BitMapEdit.combineImages(R.drawable.skepp_1r_w_broken, view, 0, false)));
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
                    mImageView.setImageBitmap(BitMapEdit.darkenBitMap(BitMapEdit.combineImages(R.drawable.skepp_2r_w_r_broken, view, 0, false)));
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
                    mImageView.setImageBitmap(BitMapEdit.darkenBitMap(BitMapEdit.combineImages(R.drawable.skepp_2r_w_r_broken, view, 90, true)));
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
                    mImageView.setImageBitmap(BitMapEdit.darkenBitMap(BitMapEdit.combineImages(R.drawable.skepp_2r_w_r_broken, view, 0, false)));
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
                    mImageView.setImageBitmap(BitMapEdit.darkenBitMap(BitMapEdit.combineImages(R.drawable.skepp_2r_w_r_broken, view, 90, true)));
                    break;
                case TILE_TYPE_SIZE_3_SHIPID_6_H_L:
                    mImageView.setImageBitmap(BitMapEdit.combineImages(R.drawable.skepp_3r_w_l, view, 0, false));
                    break;
                case TILE_TYPE_SIZE_3_SHIPID_6_H_L_DAMAGED:
                    mImageView.setImageBitmap(BitMapEdit.darkenBitMap(BitMapEdit.combineImages(R.drawable.skepp_3r_w_l_broken, view, 0, false)));
                    break;
                case TILE_TYPE_SIZE_3_SHIPID_6_H_M:
                    mImageView.setImageBitmap(BitMapEdit.combineImages(R.drawable.skepp_3r_w_m, view, 0, false));
                    break;
                case TILE_TYPE_SIZE_3_SHIPID_6_H_M_DAMAGED:
                    mImageView.setImageBitmap(BitMapEdit.darkenBitMap(BitMapEdit.combineImages(R.drawable.skepp_3r_w_m_broken, view, 0, false)));
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
                    mImageView.setImageBitmap(BitMapEdit.darkenBitMap(BitMapEdit.combineImages(R.drawable.skepp_3r_w_l_broken, view, 90, false)));
                    break;
                case TILE_TYPE_SIZE_3_SHIPID_6_V_M:
                    mImageView.setImageBitmap(BitMapEdit.combineImages(R.drawable.skepp_3r_w_m, view, 90, false));
                    break;
                case TILE_TYPE_SIZE_3_SHIPID_6_V_M_DAMAGED:
                    mImageView.setImageBitmap(BitMapEdit.darkenBitMap(BitMapEdit.combineImages(R.drawable.skepp_3r_w_m_broken, view, 90, false)));
                    break;
                case TILE_TYPE_SIZE_3_SHIPID_6_V_R:
                    mImageView.setImageBitmap(BitMapEdit.combineImages(R.drawable.skepp_3r_w_r, view, 90, false));
                    break;
                case TILE_TYPE_SIZE_3_SHIPID_6_V_R_DAMAGED:
                    mImageView.setImageBitmap(BitMapEdit.combineImages(R.drawable.skepp_3r_w_r_broken, view, 90, false));
                    break;
            }
        } else if (!isFriendlyBoard) {
            switch (boardState[position]) {
                case TILE_TYPE_WATER:
                    mImageView.setImageResource(R.drawable.water_tile);
                    break;
                case TILE_TYPE_MISS:
                    mImageView.setImageBitmap(BitMapEdit.combineImages(R.drawable.water_tile_miss, view, 0, false));
                    break;
                case TILE_TYPE_SIZE_1_SHIPID_1:
                    mImageView.setImageResource(R.drawable.water_tile);
                    break;
                case TILE_TYPE_SIZE_1_SHIPID_1_DAMAGED:
                    if (isShipDestroyed(boardState[position], position))
                        mImageView.setImageBitmap(BitMapEdit.darkenBitMap(BitMapEdit.combineImages(R.drawable.skepp_1r_w_broken, view, 0, false)));
                    tiles.get(position).setClickDisabled(true);
                    break;
                case TILE_TYPE_SIZE_1_SHIPID_2:
                    mImageView.setImageResource(R.drawable.water_tile);
                    break;
                case TILE_TYPE_SIZE_1_SHIPID_2_DAMAGED:
                    if (isShipDestroyed(boardState[position], position))
                        mImageView.setImageBitmap(BitMapEdit.darkenBitMap(BitMapEdit.combineImages(R.drawable.skepp_1r_w_broken, view, 0, false)));
                    tiles.get(position).setClickDisabled(true);
                    break;
                case TILE_TYPE_SIZE_1_SHIPID_3:
                    mImageView.setImageResource(R.drawable.water_tile);
                    break;
                case TILE_TYPE_SIZE_1_SHIPID_3_DAMAGED:
                    if (isShipDestroyed(boardState[position], position))
                        mImageView.setImageBitmap(BitMapEdit.darkenBitMap(BitMapEdit.combineImages(R.drawable.skepp_1r_w_broken, view, 0, false)));
                    tiles.get(position).setClickDisabled(true);
                    break;
                case TILE_TYPE_SIZE_2_SHIPID_4_H_L:
                    mImageView.setImageResource(R.drawable.water_tile);
                    break;
                case TILE_TYPE_SIZE_2_SHIPID_4_H_L_DAMAGED:
                    if (isShipDestroyed(boardState[position], position))
                        mImageView.setImageBitmap(BitMapEdit.darkenBitMap(BitMapEdit.combineImages(R.drawable.skepp_2r_w_l_broken, view, 0, false)));
                    else
                        mImageView.setImageBitmap(BitMapEdit.combineImages(R.drawable.broken_parts, view, 360, false));
                    tiles.get(position).setClickDisabled(true);
                    break;
                case TILE_TYPE_SIZE_2_SHIPID_4_H_R:
                    mImageView.setImageResource(R.drawable.water_tile);
                    break;
                case TILE_TYPE_SIZE_2_SHIPID_4_H_R_DAMAGED:
                    if (isShipDestroyed(boardState[position], position))
                        mImageView.setImageBitmap(BitMapEdit.darkenBitMap(BitMapEdit.combineImages(R.drawable.skepp_2r_w_r_broken, view, 0, false)));
                    else
                        mImageView.setImageBitmap(BitMapEdit.combineImages(R.drawable.broken_parts, view, 360, false));
                    tiles.get(position).setClickDisabled(true);
                    break;
                case TILE_TYPE_SIZE_2_SHIPID_4_V_L:
                    mImageView.setImageResource(R.drawable.water_tile);
                    break;
                case TILE_TYPE_SIZE_2_SHIPID_4_V_L_DAMAGED:
                    if (isShipDestroyed(boardState[position], position))
                        mImageView.setImageBitmap(BitMapEdit.darkenBitMap(BitMapEdit.combineImages(R.drawable.skepp_2r_w_l_broken, view, 90, true)));
                    else
                        mImageView.setImageBitmap(BitMapEdit.combineImages(R.drawable.broken_parts, view, 360, false));
                    tiles.get(position).setClickDisabled(true);
                    break;
                case TILE_TYPE_SIZE_2_SHIPID_4_V_R:
                    mImageView.setImageResource(R.drawable.water_tile);
                    break;
                case TILE_TYPE_SIZE_2_SHIPID_4_V_R_DAMAGED:
                    if (isShipDestroyed(boardState[position], position))
                        mImageView.setImageBitmap(BitMapEdit.darkenBitMap(BitMapEdit.combineImages(R.drawable.skepp_2r_w_r_broken, view, 90, true)));
                    else
                        mImageView.setImageBitmap(BitMapEdit.combineImages(R.drawable.broken_parts, view, 360, false));
                    tiles.get(position).setClickDisabled(true);
                    break;
                case TILE_TYPE_SIZE_2_SHIPID_5_H_L:
                    mImageView.setImageResource(R.drawable.water_tile);
                    break;
                case TILE_TYPE_SIZE_2_SHIPID_5_H_L_DAMAGED:
                    if (isShipDestroyed(boardState[position], position))
                        mImageView.setImageBitmap(BitMapEdit.darkenBitMap(BitMapEdit.combineImages(R.drawable.skepp_2r_w_l_broken, view, 0, false)));
                    else
                        mImageView.setImageBitmap(BitMapEdit.combineImages(R.drawable.broken_parts, view, 360, false));
                    tiles.get(position).setClickDisabled(true);
                    break;
                case TILE_TYPE_SIZE_2_SHIPID_5_H_R:
                    mImageView.setImageResource(R.drawable.water_tile);
                    break;
                case TILE_TYPE_SIZE_2_SHIPID_5_H_R_DAMAGED:
                    if (isShipDestroyed(boardState[position], position))
                        mImageView.setImageBitmap(BitMapEdit.darkenBitMap(BitMapEdit.combineImages(R.drawable.skepp_2r_w_r_broken, view, 0, false)));
                    else
                        mImageView.setImageBitmap(BitMapEdit.combineImages(R.drawable.broken_parts, view, 360, false));
                    tiles.get(position).setClickDisabled(true);
                    break;
                case TILE_TYPE_SIZE_2_SHIPID_5_V_L:
                    mImageView.setImageResource(R.drawable.water_tile);
                    break;
                case TILE_TYPE_SIZE_2_SHIPID_5_V_L_DAMAGED:
                    if (isShipDestroyed(boardState[position], position))
                        mImageView.setImageBitmap(BitMapEdit.darkenBitMap(BitMapEdit.combineImages(R.drawable.skepp_2r_w_l_broken, view, 0, false)));
                    else
                        mImageView.setImageBitmap(BitMapEdit.combineImages(R.drawable.broken_parts, view, 360, false));
                    tiles.get(position).setClickDisabled(true);
                    break;
                case TILE_TYPE_SIZE_2_SHIPID_5_V_R:
                    mImageView.setImageResource(R.drawable.water_tile);
                    break;
                case TILE_TYPE_SIZE_2_SHIPID_5_V_R_DAMAGED:
                    if (isShipDestroyed(boardState[position], position))
                        mImageView.setImageBitmap(BitMapEdit.darkenBitMap(BitMapEdit.darkenBitMap(BitMapEdit.combineImages(R.drawable.skepp_2r_w_r_broken, view, 90, true))));
                    else
                        mImageView.setImageBitmap(BitMapEdit.combineImages(R.drawable.broken_parts, view, 360, false));
                    tiles.get(position).setClickDisabled(true);
                    break;
                case TILE_TYPE_SIZE_3_SHIPID_6_H_L:
                    mImageView.setImageResource(R.drawable.water_tile);
                    break;
                case TILE_TYPE_SIZE_3_SHIPID_6_H_L_DAMAGED:
                    if (isShipDestroyed(boardState[position], position))
                        mImageView.setImageBitmap(BitMapEdit.darkenBitMap(BitMapEdit.combineImages(R.drawable.skepp_3r_w_l_broken, view, 0, false)));
                    else
                        mImageView.setImageBitmap(BitMapEdit.combineImages(R.drawable.broken_parts, view, 360, false));
                    tiles.get(position).setClickDisabled(true);
                    break;
                case TILE_TYPE_SIZE_3_SHIPID_6_H_M:
                    mImageView.setImageResource(R.drawable.water_tile);
                    break;
                case TILE_TYPE_SIZE_3_SHIPID_6_H_M_DAMAGED:
                    if (isShipDestroyed(boardState[position], position))
                        mImageView.setImageBitmap(BitMapEdit.darkenBitMap(BitMapEdit.combineImages(R.drawable.skepp_3r_w_m_broken, view, 0, false)));
                    else
                        mImageView.setImageBitmap(BitMapEdit.combineImages(R.drawable.broken_parts, view, 360, false));
                    tiles.get(position).setClickDisabled(true);
                    break;
                case TILE_TYPE_SIZE_3_SHIPID_6_H_R:
                    mImageView.setImageResource(R.drawable.water_tile);
                    break;
                case TILE_TYPE_SIZE_3_SHIPID_6_H_R_DAMAGED:
                    if (isShipDestroyed(boardState[position], position))
                        mImageView.setImageBitmap(BitMapEdit.darkenBitMap(BitMapEdit.combineImages(R.drawable.skepp_3r_w_r_broken, view, 0, false)));
                    else
                        mImageView.setImageBitmap(BitMapEdit.combineImages(R.drawable.broken_parts, view, 360, false));
                    tiles.get(position).setClickDisabled(true);
                    break;
                case TILE_TYPE_SIZE_3_SHIPID_6_V_L:
                    mImageView.setImageResource(R.drawable.water_tile);
                    break;
                case TILE_TYPE_SIZE_3_SHIPID_6_V_L_DAMAGED:
                    if (isShipDestroyed(boardState[position], position))
                        mImageView.setImageBitmap(BitMapEdit.darkenBitMap(BitMapEdit.combineImages(R.drawable.skepp_3r_w_l_broken, view, 90, false)));
                    else
                        mImageView.setImageBitmap(BitMapEdit.combineImages(R.drawable.broken_parts, view, 360, false));
                    tiles.get(position).setClickDisabled(true);
                    break;
                case TILE_TYPE_SIZE_3_SHIPID_6_V_M:
                    mImageView.setImageResource(R.drawable.water_tile);
                    break;
                case TILE_TYPE_SIZE_3_SHIPID_6_V_M_DAMAGED:
                    if (isShipDestroyed(boardState[position], position))
                        mImageView.setImageBitmap(BitMapEdit.darkenBitMap(BitMapEdit.combineImages(R.drawable.skepp_3r_w_m_broken, view, 90, false)));
                    else
                        mImageView.setImageBitmap(BitMapEdit.combineImages(R.drawable.broken_parts, view, 360, false));
                    tiles.get(position).setClickDisabled(true);
                    break;
                case TILE_TYPE_SIZE_3_SHIPID_6_V_R:
                    mImageView.setImageResource(R.drawable.water_tile);
                    break;
                case TILE_TYPE_SIZE_3_SHIPID_6_V_R_DAMAGED:
                    if (isShipDestroyed(boardState[position], position))
                        mImageView.setImageBitmap(BitMapEdit.darkenBitMap(BitMapEdit.combineImages(R.drawable.skepp_3r_w_r_broken, view, 90, false)));
                    else
                        mImageView.setImageBitmap(BitMapEdit.combineImages(R.drawable.broken_parts, view, 360, false));
                    tiles.get(position).setClickDisabled(true);
                    break;
            }
        }

    }

    private boolean isShipDestroyed(String value, int position) {
        boolean isDestroyed = false;
        if (value.charAt(0) <= '3') {
            isDestroyed = true;
        } else if (value.charAt(0) == '4' && value.charAt(0) == '5') {
            try {
                if (value.charAt(0) == boardState[position + 1].charAt(0) || value.charAt(0) == boardState[position - 1].charAt(0) || value.charAt(0) == boardState[position + 7].charAt(0) || value.charAt(0) == boardState[position - 7].charAt(0))
                    isDestroyed = true;
            } catch (ArrayIndexOutOfBoundsException e) {
                Log.d("CustomGridViewAdapter", "ArrayOutOfBounds");
            }
        } else if (value.charAt(0) == '6') {
            try {
                String matchingPosition = null;
                if (value.charAt(0) == boardState[position + 1].charAt(0))
                    matchingPosition = "east";
                else if (value.charAt(0) == boardState[position - 1].charAt(0))
                    matchingPosition = "west";
                else if (value.charAt(0) == boardState[position + 7].charAt(0))
                    matchingPosition = "south";
                else if (value.charAt(0) == boardState[position - 7].charAt(0))
                    matchingPosition = "north";

                if (matchingPosition != null) {
                    switch (matchingPosition) {
                        case "east":
                            if (value.charAt(0) == boardState[position + 2].charAt(0))
                                isDestroyed = true;
                            break;
                        case "west":
                            if (value.charAt(0) == boardState[position - 2].charAt(0))
                                isDestroyed = true;
                            break;
                        case "south":
                            if (value.charAt(0) == boardState[position + 14].charAt(0))
                                isDestroyed = true;
                            break;
                        case "north":
                            if (value.charAt(0) == boardState[position - 14].charAt(0))
                                isDestroyed = true;
                            break;
                    }
                }
            } catch (ArrayIndexOutOfBoundsException e) {
                Log.d("CustomGridViewAdapter", "ArrayOutOfBounds");
            }
        }
        return isDestroyed;
    }
}
