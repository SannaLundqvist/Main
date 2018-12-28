package com.example.a17salu03.battleships;


import android.app.Activity;
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

    private CustomGridAdapter cga;
    private int generatedID = 0;
    private View thisView;
    public Integer layoutBorder = R.drawable.layout_border;
    private static List<View> clickedViews = new ArrayList<>();
    private View lastClickedTile = null;
    private int lastClickedTileCounter = 0;

    //För att kunna skicka tillbaka vid vald ruta med gemensam metod.
    private OnItemClickedListener mCallback = null;

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
                ImageView imageView = new ImageView(this.getActivity());
                imageView.setImageResource(R.drawable.water_tile);

           //     imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                GridLayout.LayoutParams param = new GridLayout.LayoutParams();
           //     param.height = GridLayout.LayoutParams.WRAP_CONTENT;
           //     param.width = GridLayout.LayoutParams.WRAP_CONTENT;
                param.rightMargin = 5;
                param.topMargin = 5;
                param.columnSpec = GridLayout.spec(c);
                param.rowSpec = GridLayout.spec(r);
                imageView.setLayoutParams(param);
                gridLayout.addView(imageView);

                imageView.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View view){
            /*            boolean isClicked = false;
                        for (View v : clickedViews){
                            if (view.equals(v)){
                                isClicked = true;
                                break;
                            }
                        }*/
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



                    }
                });
            }
        }
    }





    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //skicka tillbaka till den activity som äger dig
        mCallback.onItemClicked(position);


        //     String selectedItem = (String) parent.getItemAtPosition(position);

        //    ImageView iv = (ImageView) parent.getItemAtPosition(position);
  //      ImageView iv = (ImageView) parent.getSelectedItem();
  //      iv.setImageResource(R.drawable.ic_launcher_background); // <- här ska det vara tänkt att bilden skall bytas ut

   /*     View view = cga.getView(position, null, null);
        ImageView imageView = (ImageView) view;
        imageView.setImageResource(R.drawable.x); */
        Toast.makeText(getContext(),
                "Clicked position is " + position,
                Toast.LENGTH_SHORT).show();
        //Selection.setImageResource(items[arg2]);
    }

    //måste köras från activitien för att kunna skicka tillbaka informationen till rätt activity
    public void setOnItemClickedListener(Activity activity){
        mCallback = (OnItemClickedListener) activity;
    }

    //Interface som avtivityn som håller i dig måste implementera för att kunna skicka information mellan Frag -> Acti
    public interface OnItemClickedListener{
        public void onItemClicked(int position);
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