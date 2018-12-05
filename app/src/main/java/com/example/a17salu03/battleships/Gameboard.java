package com.example.a17salu03.battleships;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.GridView;

import java.net.URL;
import java.util.ArrayList;

public class Gameboard extends AppCompatActivity{

    private GridView imageGrid;
    private ArrayList<Bitmap> bitmapList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gameboard);

        this.imageGrid = findViewById(R.id.gridview);
        this.bitmapList = new ArrayList<Bitmap>();

        try {
            for(int i = 0; i < 10; i++) {
                this.bitmapList.add(urlImageToBitmap("http://placehold.it/150x150"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        this.imageGrid.setAdapter(new ImageAdapter(this, this.bitmapList));
    }

    private Bitmap urlImageToBitmap(String imageUrl) throws Exception {
        Bitmap result = null;
        URL url = new URL(imageUrl);
        if (url != null){
            result = BitmapFactory.decodeStream(url.openConnection().getInputStream());
        }
        return result;
    }

}
