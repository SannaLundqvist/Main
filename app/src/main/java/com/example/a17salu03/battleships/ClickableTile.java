package com.example.a17salu03.battleships;

import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;

public class ClickableTile extends Tile{
    private static ArrayList<View> clickedViews = new ArrayList<>();
    private View lastClickedTile = null;
    private int lastClickedTileCounter = 0;

    public ClickableTile(int ID, View view){
        super(ID, view);

        tileImage.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(),
                        tileID + " got clicked",
                        Toast.LENGTH_SHORT).show();
                boolean isClicked = false;
                for (View v : clickedViews){
                    if (view.equals(v)){
                        isClicked = true;
                        break;
                    }
                }

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
                       if (isClicked) {
                            imageView.setImageResource(R.drawable.water_tile);
                            Toast.makeText(view.getContext(),
                                    "Clicked",
                                    Toast.LENGTH_SHORT).show();
                            clickedViews.remove(view);
                        } else {

                            Toast.makeText(view.getContext(),
                                    "Not clicked",
                                    Toast.LENGTH_SHORT).show();
                            clickedViews.add(view);
                        }


            }
        });


    }
}
