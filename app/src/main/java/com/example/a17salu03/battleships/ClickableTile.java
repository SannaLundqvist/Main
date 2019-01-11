package com.example.a17salu03.battleships;

import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

public class ClickableTile extends Tile{
    private static View lastClickedTile = null;
    private static int lastClickedTileID;
    private static int lastClickedTileCounter = 0;
    private static boolean[] isClickDisabled;


    private View view;

    public ClickableTile(int ID, View view, final GridFragment gridFragment){
        super(ID, view);
        this.view = view;

        if (isClickDisabled == null){
            isClickDisabled = new boolean[49];
            Arrays.fill(isClickDisabled, false);
        }

        tileImage.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                    gridFragment.setClickedTile(tileID);

                    ImageView imageView = (ImageView) view;
                    if (lastClickedTile == null || (!isClickDisabled[lastClickedTileID])) {
                        imageView.setImageResource(R.drawable.water_tile_border);
                    } else if (!lastClickedTile.equals(view)) {
                                ImageView lastClickedImage = (ImageView) lastClickedTile;
                                imageView.setImageResource(R.drawable.water_tile_border);
                                lastClickedImage.setImageResource(R.drawable.water_tile);
                                lastClickedTileCounter = 0;
                            } else if (lastClickedTile.equals(view)) {
                        if ((lastClickedTileCounter % 2) == 0) {
                            imageView.setImageResource(R.drawable.water_tile);
                            lastClickedTileCounter++;
                        } else {
                            imageView.setImageResource(R.drawable.water_tile_border);
                            lastClickedTileCounter++;


                        }
                    }
                    lastClickedTile = view;
                    lastClickedTileID = tileID;
            }

        });


    }

    @Override
    public void setTileImage(int imageView){
        tileImage.setImageResource(imageView);
        isClickDisabled[tileID] = true;
    }
}
