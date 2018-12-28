package com.example.a17salu03.battleships;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class PlaceShipsActivity extends AppCompatActivity implements GridFragment.OnItemClickedListener {

    private int selectedPosition;
    private Button doneBtn = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_ships);

        doneBtn = findViewById(R.id.done);

        doneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //skicka tillbaka informationen till StartActivity
                //kanske behövs låsas innan man valt position minst en gång, eller testas

            }
        });

        GridFragment playerGrid = new GridFragment();
        FragmentTransaction playerft = getSupportFragmentManager().beginTransaction();
        playerft.replace(R.id.fragment_container_player, playerGrid);
        playerft.addToBackStack(null);
        playerft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        playerft.commit();


    }

    @Override
    public void onItemClicked(int position) {
        selectedPosition = position;
    }
}
