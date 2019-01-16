package com.example.a17salu03.battleships;

import android.view.View;
import android.widget.ImageView;

/**
 * The Tile class but this one is clickable.
 *
 * @author Mattias Melchior, Sanna Lundqvist
 */
public class ClickableTile extends Tile{
    private static View lastClickedTile = null;
    private static int lastClickedTileCounter = 0;
    private boolean clickDisabled = false;

    /**
     * This constructor sets a onClickListener on the imageview that changes appearance if clicked on
     * and if another is clicked the appearance is set to the default, also works if the same is
     * clicked twice.
     *
     * @param ID the ID
     * @param view the view
     * @param gridFragment the GridFragment
     */
    public ClickableTile(int ID, View view, final GridFragment gridFragment){
        super(ID, view);


        tileImage.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if (!clickDisabled){
                    gridFragment.setClickedTile(tileID);

                    ImageView imageView = (ImageView) view;

                    if (lastClickedTile == null) {
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
                }
            }
        });
    }

    /**
     * Sets the click mechanics to disabled or able.
     *
     * @param answer the answer
     */
    @Override
    public void setClickDisabled(boolean answer){
        clickDisabled = answer;
        lastClickedTile = null;
    }

}
