package com.example.a17salu03.battleships;


import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

public class BoardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board);

     //   View fragmentContainer = findViewById(R.id.fragment_container);

        GridFragment playerGrid = new GridFragment();
        FragmentTransaction playerft = getSupportFragmentManager().beginTransaction();
        playerft.replace(R.id.fragment_container_player, playerGrid);
        playerft.addToBackStack(null);
        playerft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        playerft.commit();


/*
        GridFragment OpponentGrid = new GridFragment();
        FragmentTransaction opponentft = getSupportFragmentManager().beginTransaction();
        opponentft.replace(R.id.fragment_container_opponent, OpponentGrid);
        opponentft.addToBackStack(null);
        opponentft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        opponentft.commit(); */
    }

    public void onBigClick(View view){
        ImageView imageView = (ImageView) view;
        imageView.setBackgroundResource(R.drawable.red_border);
    }
}
