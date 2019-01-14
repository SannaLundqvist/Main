package com.example.a17salu03.battleships;

import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;

public class Tile {
    protected ImageView tileImage;
    protected int tileID;
    private View view;


    public Tile(int ID, View view){
        tileID = ID;
        this.view = view;
        tileImage = new ImageView(view.getContext());

        switch (tileApperance){
            case TILE_TYPE_WATER :
                tileImage.setImageResource(R.drawable.water_tile);
                break;
            case TILE_TYPE_HIT :
                tileImage.setImageResource(R.drawable.water_tile_hit);
                break;
            case TILE_TYPE_SHIP_SMALL :
                tileImage.setImageResource(R.drawable.skepp_2r_w);
                break;
            case TILE_TYPE_SHIP_MEDIUM_L :
                tileImage.setImageResource(R.drawable.skepp_3r_w_l);
                break;
            case TILE_TYPE_SHIP_MEDIUM_R :
                tileImage.setImageResource(R.drawable.skepp_3r_w_r);
                break;
            case TILE_TYPE_SHIP_LARGE_L :
                tileImage.setImageResource(R.drawable.skepp_4r_w_l);
                break;
            case TILE_TYPE_SHIP_LARGE_M :
                    tileImage.setImageResource(R.drawable.skepp_4r_w_m);
                    break;
            case TILE_TYPE_SHIP_LARGE_R :
                tileImage.setImageResource(R.drawable.skepp_4r_w_r);
                break;
        }

        //tileImage.setImageResource(R.drawable.water_tile);
        tiles.add(this);

    }

    public ImageView getTileImage(){
        return tileImage;
    }

    public void setTileImage(ImageView imageView){
        tileImage = imageView;
    }

    public void setClickDisabled(boolean answer){
    }

    public int getTileID(){
        return tileID;
    }



}