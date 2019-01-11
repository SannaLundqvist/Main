package com.example.a17salu03.battleships;


import android.content.Intent;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class BoardActivity extends AppCompatActivity implements GridFragment.OnItemClickedListener{
    private int[] opponentsShips;
    private Button fireBtn = null;
    private GridFragment playerGrid;
    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board);

        opponentsShips =  getIntent().getIntArrayExtra("opponentsShips");


        fireBtn = findViewById(R.id.fire);

     //   View fragmentContainer = findViewById(R.id.fragment_container);

        playerGrid = new GridFragment();
        FragmentTransaction playerft = getSupportFragmentManager().beginTransaction();
        playerft.replace(R.id.fragment_container_player, playerGrid);
        playerft.addToBackStack(null);
        playerft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        playerft.commit();

        GridFragment opponentGrid = new GridFragment();
        opponentGrid.setClickableTiles(true);
        FragmentTransaction opponentft = getSupportFragmentManager().beginTransaction();
        opponentft.replace(R.id.fragment_container_opponent, opponentGrid);
        opponentft.addToBackStack(null);
        opponentft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        opponentft.commit();
    }

    public void onFireClick(View view){
        int clickedTile = playerGrid.getClickedTile();
        if(clickedTile >= 0){
            if((opponentsShips[clickedTile] > 0) && (opponentsShips[clickedTile] < 10)){
                opponentsShips[clickedTile] = opponentsShips[clickedTile] + 10;
                //hitCounter ++;
            }
            Intent intent = new Intent();
            intent.putExtra("opponentsShips", opponentsShips);
            setResult(RESULT_OK, intent);
            finish();
        }else{
            Toast.makeText(getBaseContext(), "Choose a block to shoot at", Toast.LENGTH_SHORT).show();
        }
    }

    public void onBigClick(View view){
        ImageView imageView = (ImageView) view;
        imageView.setBackgroundResource(R.drawable.red_border);
        Toast.makeText(getApplicationContext(), "onItemClick", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onItemClicked(int position) {
        this.position = position;
        Toast.makeText(getBaseContext(), "fakePosition: " + position, Toast.LENGTH_LONG).show();
    }
}
