package com.example.a17salu03.battleships;

import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

public class Tile {
    private ImageView tileImage;
    private int tileID;
    private View view;

    public Tile(int ID, View view){
        tileID = ID;
        this.view = view;
        tileImage = new ImageView(view.getContext());
        tileImage.setImageResource(R.drawable.water_tile);

        tileImage.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(),
                        tileID + " got clicked",
                        Toast.LENGTH_SHORT).show();

            }
        });
        //     imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);


    }

    public ImageView getTileImage(){
        return tileImage;
    }

    public int getTileID(){
        return tileID;
    }



}
