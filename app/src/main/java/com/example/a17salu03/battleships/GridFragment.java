package com.example.a17salu03.battleships;


import android.content.Context;

import android.os.Bundle;
import android.support.v4.app.Fragment;
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


    private int tileID = 0;
    private int clickedTile;
    private View thisView;
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
                    tile = new ClickableTile(tileID, view, this);
                } else{
                    tile = new Tile(tileID, view);
                }

                tileID++;
                ImageView imageView = tile.getTileImage();
                GridLayout.LayoutParams param = new GridLayout.LayoutParams();
           //     param.height = GridLayout.LayoutParams.WRAP_CONTENT;
           //     param.width = GridLayout.LayoutParams.WRAP_CONTENT;
                param.rightMargin = 5;
                param.topMargin = 5;
                param.columnSpec = GridLayout.spec(c);
                param.rowSpec = GridLayout.spec(r);
                imageView.setLayoutParams(param);
                gridLayout.addView(imageView);

        /*        imageView.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View view){
                        OnItemClickedListener onItemClickedListener = (OnItemClickedListener) getActivity();
                        onItemClickedListener.onItemClicked(2);
            /*            boolean isClicked = false;
                        for (View v : clickedViews){
                            if (view.equals(v)){
                                isClicked = true;
                                break;
                            }
                        }*/

             /*           ImageView imageView = (ImageView) view;
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
            /*            if (isClicked) {
                            imageView.setImageResource(R.drawable.water_tile);
                            Toast.makeText(getContext(),
                                    "Clicked",
                                    Toast.LENGTH_SHORT).show();
                            clickedViews.remove(view);
                        } else {

                            Toast.makeText(getContext(),
                                    "Not clicked",
                                    Toast.LENGTH_SHORT).show();
                            clickedViews.add(view);
                        }*/

/*

                    }
                }); */
            }
        }
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


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

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