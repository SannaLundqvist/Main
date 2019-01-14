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