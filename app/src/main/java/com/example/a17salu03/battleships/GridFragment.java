package com.example.a17salu03.battleships;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class GridFragment extends Fragment implements
        AdapterView.OnItemClickListener {

    private CustomGridAdapter cga;
    private int generatedID = 0;

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
            GridView grid = view.findViewById(R.id.grid);
            cga = new CustomGridAdapter(this, 64);
            grid.setAdapter(cga);
            grid.setOnItemClickListener(this);
        }
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

   //     String selectedItem = (String) parent.getItemAtPosition(position);

    //    ImageView iv = (ImageView) parent.getItemAtPosition(position);
        ImageView iv = (ImageView) parent.getSelectedItem();
        iv.setImageResource(R.drawable.ic_launcher_background); // <- här ska det vara tänkt att bilden skall bytas ut

   /*     View view = cga.getView(position, null, null);
        ImageView imageView = (ImageView) view;
        imageView.setImageResource(R.drawable.x); */
        Toast.makeText(getContext(),
                "Clicked position is " + position,
                Toast.LENGTH_SHORT).show();
        //Selection.setImageResource(items[arg2]);
    }

    // Here is your custom Adapter

    public class CustomGridAdapter extends BaseAdapter {
        private Context mContext;
        private int size;
        // Keep all Images in array
   //     public Integer[] mThumbIds = new Integer[64];
        private Integer waterImage = R.drawable.water_tile;

        // Constructor
        public CustomGridAdapter(Fragment gridActivity, int size) {
            this.mContext = gridActivity.getActivity();
            this.size = size;
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
            imageView.setLayoutParams(new GridView.LayoutParams(105, 105));  // <- här säts det till en statisk storlek, activity_grid hör också till detta problem
            return imageView;
        }

    }

}