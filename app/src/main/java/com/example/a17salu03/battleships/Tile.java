package com.example.a17salu03.battleships;

import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * The tile class containing an ImageView and an ID.
 *
 * @author Mattias Melchior, Sanna Lundqvist
 */
public class Tile {
    public static final String TILE_TYPE_WATER = "W";
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


    protected ImageView tileImage;
    protected int tileID;

    /**
     * The constructor for Tile.
     *
     * @param ID the ID
     * @param view the view
     */
    public Tile(int ID, View view){
        tileID = ID;
        tileImage = new ImageView(view.getContext());
    }

    /**
     * Gets the imageview
     *
     * @return the imageview
     */
    public ImageView getTileImage(){
        return tileImage;
    }

    /**
     * Does nothing here, it's for ClickedTile.
     *
     * @param answer the answer
     */
    public void setClickDisabled(boolean answer){
    }

}