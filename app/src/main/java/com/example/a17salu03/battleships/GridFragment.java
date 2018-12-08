package com.example.a17salu03.battleships;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

public class GridFragment extends Fragment implements
        AdapterView.OnItemClickListener {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
     //   setContentView(R.layout.activity_grid);
    //    ImageView selection = findViewById(R.id.selection);
    //    GridView grid = findViewById(R.id.grid);
        // grid.setAdapter(new ArrayAdapter<Integer>(this, R.layout.cell,
        // items));
    //    grid.setAdapter(new CustomGridAdapter(this, 70));
    //    grid.setOnItemClickListener(this);

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
            grid.setAdapter(new CustomGridAdapter(this, 64));
            grid.setOnItemClickListener(this);
        }
    }


    @Override
    public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {

        Toast.makeText(getContext(),
                "Clicked position is " + arg2,
                Toast.LENGTH_SHORT).show();
        //Selection.setImageResource(items[arg2]);
    }

    // Here is your custom Adapter

    public class CustomGridAdapter extends BaseAdapter {
        private Context mContext;
        // Keep all Images in array
        public Integer[] mThumbIds = new Integer[64];

        // Constructor
        public CustomGridAdapter(Fragment gridActivity, int size) {
            this.mContext = gridActivity.getActivity();
            for (int i = 0; i < size; i++){
                this.mThumbIds[i] = R.drawable.water_tile;
            }


        }

        @Override
        public int getCount() {
            return mThumbIds.length;
        }

        @Override
        public Object getItem(int position) {
            return mThumbIds[position];
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ImageView imageView = new ImageView(mContext);
            imageView.setImageResource(mThumbIds[position]);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setLayoutParams(new GridView.LayoutParams(105, 105));
            return imageView;
        }

    }

}