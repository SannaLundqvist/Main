package com.example.a17salu03.battleships;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

public class GridActivity extends Activity implements
        AdapterView.OnItemClickListener {

    private ImageView selection;
    private static final Integer[] items = { R.drawable.water_tile,
            R.drawable.water_tile, R.drawable.water_tile,
            R.drawable.water_tile, R.drawable.water_tile,
            R.drawable.water_tile, R.drawable.water_tile,
            R.drawable.water_tile, R.drawable.water_tile,
            R.drawable.water_tile, R.drawable.water_tile,
            R.drawable.water_tile, R.drawable.water_tile,
            R.drawable.water_tile, R.drawable.water_tile,
            R.drawable.water_tile, R.drawable.water_tile,
            R.drawable.water_tile, R.drawable.water_tile,
            R.drawable.water_tile, R.drawable.water_tile,
            R.drawable.water_tile, R.drawable.water_tile,
            R.drawable.water_tile, R.drawable.water_tile,
            R.drawable.water_tile, R.drawable.water_tile,
            R.drawable.water_tile, R.drawable.water_tile,
            R.drawable.water_tile, R.drawable.water_tile,
            R.drawable.water_tile, R.drawable.water_tile,  
            R.drawable.water_tile, R.drawable.water_tile,
            R.drawable.water_tile, R.drawable.water_tile };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid);
        ImageView selection = findViewById(R.id.selection);
        GridView grid = findViewById(R.id.grid);
        // grid.setAdapter(new ArrayAdapter<Integer>(this, R.layout.cell,
        // items));
        grid.setAdapter(new CustomGridAdapter(this, 50));
        grid.setOnItemClickListener(this);

    }

    // @Override
    // public boolean onCreateOptionsMenu(Menu menu) {
    // // Inflate the menu; this adds items to the action bar if it is present.
    // getMenuInflater().inflate(R.menu.grid, menu);
    // return true;
    // }

    @Override
    public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
        // TODO Auto-generated method stub
        Toast.makeText(GridActivity.this, "Clicked position is" + arg2,
                Toast.LENGTH_LONG).show();
        //Selection.setImageResource(items[arg2]);
    }

    // Here is your custom Adapter

    public class CustomGridAdapter extends BaseAdapter {
        private Activity mContext;

        // Keep all Images in array
        public Integer[] mThumbIds;

        // Constructor
        public CustomGridAdapter(GridActivity gridActivity, int size) {
            this.mContext = gridActivity;

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
            imageView.setLayoutParams(new GridView.LayoutParams(70, 70));
            return imageView;
        }

    }

}