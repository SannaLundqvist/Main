package com.example.a17salu03.battleships;

import android.content.ClipData;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

public class ImageGridAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<Tile> itemList;

    public ImageGridAdapter(Context context, ArrayList<Tile> itemList){
        this.context = context;
        this.itemList = itemList;
    }

    @Override
    public int getCount() {
        return itemList.size();
    }

    @Override
    public Object getItem(int position) {
        return itemList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
 /*       if (convertView == null){
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            convertView = inflater.inflate(R.layout.grid_view_image, parent, false);
        }
        final ImageView itemView = (ImageView) convertView.findViewById(R.id.itemView);
        itemView.setImageDrawable("YOUR DRAWABLE");
        return convertView; */
    return convertView;
    }
}