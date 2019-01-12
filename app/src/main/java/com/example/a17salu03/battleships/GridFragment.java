package com.example.a17salu03.battleships;


import android.content.Context;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;

import android.widget.GridLayout;

import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class GridFragment extends Fragment implements
        AdapterView.OnItemClickListener {

    private int[] board = {0, 0 ,0 ,0 ,1, 0, 0 ,0 ,1, 0 ,0 ,2};

    private ArrayList<Tile> tiles = new ArrayList<>();
    private int tileID = 0;
    private int clickedTile;
    private View thisView;
    private int[] ships;
    public Integer layoutBorder = R.drawable.layout_border;

    private boolean isClickableTiles = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_grid, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        View view = getView();
        if (view != null) {
            thisView = view;
            tileID = 0;
            GridLayout gridLayout = thisView.findViewById(R.id.grid);

            gridLayout.removeAllViews();

            int column = 7;
            int row = 7;
            int total = column * row;
            gridLayout.setColumnCount(column);
            gridLayout.setRowCount(row);
            for(int i = 0, c = 0, r = 0; i < total; i++, c++)
            {
                if(c == column)
                {
                    c = 0;
                    r++;
                }

                Tile tile;
                if (isClickableTiles){

                    if(ships != null)
                        tile = new ClickableTile(tileID, view, this, getTileAppenence(i));
                    else
                        tile = new ClickableTile(tileID, view, this, Tile.TILE_TYPE_WATER);
                } else{
                    if(ships != null)
                        tile = new Tile(tileID, view, getTileAppenence(i));
                    else
                        tile = new Tile(tileID, view, Tile.TILE_TYPE_WATER);
                }
                tiles.add(tile);

// https://stackoverflow.com/questions/37174955/fit-image-into-grid-view
                tileID++;

                ImageView imageView = tile.getTileImage();
                imageView.setAdjustViewBounds(true);
                imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                GridLayout.LayoutParams param = new GridLayout.LayoutParams();

                param.height = GridLayout.LayoutParams.WRAP_CONTENT;
                param.width = GridLayout.LayoutParams.WRAP_CONTENT;

                param.rightMargin = 5;
                param.topMargin = 5;
                param.columnSpec = GridLayout.spec(c);
                param.rowSpec = GridLayout.spec(r);
                imageView.setLayoutParams(param);
                gridLayout.addView(imageView);

            }
        }
    }
    private int getTileAppenence(int pos){
        int ship = ships[pos];
        if((ship >= 0) && (ship < 9)){
            return Tile.TILE_TYPE_WATER;
        }else if((ship > 10) && (ship <= 13)){
            return  Tile.TILE_TYPE_SHIP_SMALL;
        }else if(between(14, 15, ship)) {
            if (!(pos % 7 == 0)) {
                if (between(14, 15, ships[pos - 1])) {
                    return Tile.TILE_TYPE_SHIP_MEDIUM_R;
                }
            }
            if (!(pos % 7 == 6))
                if (between(14, 15, ships[pos + 1])) {
                    return Tile.TILE_TYPE_SHIP_MEDIUM_L;
                }
            return Tile.TILE_TYPE_HIT;
        }
        else{
            if(!between(0, 1,(pos + 2) % 7)) {
                try {
                    if ((ships[pos + 2] > 15) && (ships[pos + 1] > 15)) {
                        return Tile.TILE_TYPE_SHIP_LARGE_R;
                    }
                }catch (IndexOutOfBoundsException e){
                    Log.e("GridFragment", e.toString());
                }
            }
            if(((pos + 1) % 7 == 6) || ((pos - 1) % 7 == 0)){
                try {
                    if ((ships[pos + 1] > 15) && (ships[pos - 1] > 15)) {
                        return Tile.TILE_TYPE_SHIP_LARGE_M;
                    }
                }catch (IndexOutOfBoundsException e){
                    Log.e("GridFragment", e.toString());
                }
            }
            if(!between(5, 6, (pos - 2) % 7 )){
                try {
                    if ((ships[pos - 1] > 15) && (ships[pos - 2] > 15))
                        return Tile.TILE_TYPE_SHIP_LARGE_L;
                }catch (IndexOutOfBoundsException e){
                    Log.e("GridFragment", e.toString());
                }
            }
            return Tile.TILE_TYPE_HIT;
        }
    }
    private boolean between(int small, int large, int value){
        return (value >= small) && (value <= large);
    }
    public Tile getTileAtPosition(int position){
        return tiles.get(position);
    }

    public int getClickedTile(){
        return clickedTile;
    }

    public void setClickedTile(int tileID) {
        clickedTile = tileID;
    }

    public void setClickableTiles(boolean answer){
        isClickableTiles = answer;
    }

    public int getTile(int tileID){
        return board[tileID];
    }

    public int[] getBoard(){
        return board;
    }

    public interface OnItemClickedListener{
        public void onItemClicked(int position);
    }
    public void setShipArray(int[] ships){
        this.ships = ships;
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        clickedTile = position;

        //     String selectedItem = (String) parent.getItemAtPosition(position);

        //    ImageView iv = (ImageView) parent.getItemAtPosition(position);
        //      ImageView iv = (ImageView) parent.getSelectedItem();
        //      iv.setImageResource(R.drawable.ic_launcher_background); // <- här ska det vara tänkt att bilden skall bytas ut

   /*     View view = cga.getView(position, null, null);
        ImageView imageView = (ImageView) view;
        imageView.setImageResource(R.drawable.x); */
    /*    Toast.makeText(getContext(),
                "Clicked position is " + position,
                Toast.LENGTH_SHORT).show(); */
        //Selection.setImageResource(items[arg2]);
    }

    // Here is your custom Adapter

    public class CustomGridAdapter extends BaseAdapter {
        private Context mContext;
        private int size = 49;
        // Keep all Images in array
        //     public Integer[] mThumbIds = new Integer[64];
        private Integer waterImage = R.drawable.water_tile;

        // Constructor
        public CustomGridAdapter(Fragment gridFragment) {
            this.mContext = gridFragment.getActivity();
            //    for (int i = 0; i < size; i++){
            //        this.mThumbIds[i] = R.drawable.water_tile;
            //    }


        }

        @Override
        public int getCount() {
            return size;
        }

        @Override
        public Object getItem(int position) {
            return waterImage;
        }

        public void setItem(int position) {

        }

        private Integer generateID(int position){
            String tile = "R.id.tile" + position;
/*
            View v = getView();
            if (v != null){
               v = v.findViewById(R.id.tile2);
            Integer e = getView().findViewWithTag("R.id.tile"); */
            //  generatedID++;
            return 5;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ImageView imageView = new ImageView(mContext);
            //     imageView.setId(generateID(position));
            //     Log.v("rhh", imageView.getId() + "");
            imageView.setImageResource(waterImage);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            //        imageView.setLayoutParams(new GridView.LayoutParams(105, 105));  // <- här säts det till en statisk storlek, activity_grid hör också till detta problem
            return imageView;
        }

    }

}