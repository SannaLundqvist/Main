package com.example.a17salu03.battleships;

import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;

public class Tile {
    protected ImageView tileImage;
    protected int tileID;
    private View view;
    private static ArrayList<Tile> tiles = new ArrayList<Tile>();

    public Tile(){

    }

    public Tile(int ID, View view){
        tileID = ID;
        this.view = view;
        tileImage = new ImageView(view.getContext());
        tileImage.setImageResource(R.drawable.water_tile);
        tiles.add(this);

    }

    public ImageView getTileImage(){
        return tileImage;
    }

    public int getTileID(){
        return tileID;
    }



}
