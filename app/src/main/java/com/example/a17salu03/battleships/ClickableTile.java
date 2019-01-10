package com.example.a17salu03.battleships;

import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;

public class ClickableTile extends Tile{
    private static View lastClickedTile = null;
    private static int lastClickedTileCounter = 0;

    public ClickableTile(int ID, View view, final GridFragment gridFragment){
        super(ID, view);


        tileImage.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                gridFragment.setClickedTile(tileID);
                Toast.makeText(view.getContext(),
                          tileID + " got clicked",
                        Toast.LENGTH_SHORT).show();

                ImageView imageView = (ImageView) view;
                if (lastClickedTile == null) {
                    imageView.setImageResource(R.drawable.water_tile_border);
                } else if (!lastClickedTile.equals(view))    {
                    ImageView lastClickedImage = (ImageView) lastClickedTile;
                    imageView.setImageResource(R.drawable.water_tile_border);
                    lastClickedImage.setImageResource(R.drawable.water_tile);
                    lastClickedTileCounter = 0;
                } else if (lastClickedTile.equals(view)){
                    if ((lastClickedTileCounter % 2) == 0) {
                        imageView.setImageResource(R.drawable.water_tile);
                        lastClickedTileCounter++;
                    } else {
                        imageView.setImageResource(R.drawable.water_tile_border);
                        lastClickedTileCounter++;
                    }

                }
                lastClickedTile = view;

            }
        });


    }
}
