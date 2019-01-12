package com.example.a17salu03.battleships;


import android.content.Intent;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class BoardActivity extends AppCompatActivity implements GridFragment.OnItemClickedListener {
    private int[] opponentsShips;
    private Button fireBtn = null;
    private GridFragment opponentGrid;
    private boolean hasWon;
    private GridFragment playerGrid;
    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board);

        opponentsShips = getIntent().getIntArrayExtra("opponentsShips");


        fireBtn = findViewById(R.id.fire);

        playerGrid = new GridFragment();
        FragmentTransaction playerft = getSupportFragmentManager().beginTransaction();
        playerft.replace(R.id.fragment_container_player, playerGrid);
        playerft.addToBackStack(null);
        playerft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        playerft.commit();

        opponentGrid = new GridFragment();
        opponentGrid.setClickableTiles(true);
        FragmentTransaction opponentft = getSupportFragmentManager().beginTransaction();
        opponentft.replace(R.id.fragment_container_opponent, opponentGrid);
        opponentft.addToBackStack(null);
        opponentft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        opponentft.commit();
    }

    public void onFireClick(View view) {

        int clickedTile = opponentGrid.getClickedTile();
        if (clickedTile >= 0) {
            if (isHit(clickedTile)) {
                opponentsShips[clickedTile] = opponentsShips[clickedTile] + 10;
                Toast.makeText(this, "Hit!", Toast.LENGTH_LONG).show();
                hasWon = checkIfWon();
            } else {
                Toast.makeText(this, "You missed...", Toast.LENGTH_SHORT).show();
                hasWon = false;
            }

            Intent intent = new Intent();
            intent.putExtra("opponentsShips", opponentsShips);
            intent.putExtra("hasWon", hasWon);
            setResult(RESULT_OK, intent);
            finish();
        }
    }

        public void onBigClick (View view){
            ImageView imageView = (ImageView) view;
            imageView.setBackgroundResource(R.drawable.red_border);
            Toast.makeText(getApplicationContext(), "onItemClick", Toast.LENGTH_LONG).show();
        }

        @Override
        public void onItemClicked ( int position){
            this.position = position;
            Toast.makeText(getBaseContext(), "fakePosition: " + position, Toast.LENGTH_LONG).show();
        }
        private boolean checkIfWon () {
            for (int i = 0; i < opponentsShips.length; i++) {
                if ((opponentsShips[i] > 0) && (opponentsShips[i] < 10))
                    return false;
            }
            return true;
        }
        private boolean isHit ( int tile){
            boolean theHit = ((opponentsShips[tile] > 0) && (opponentsShips[tile] < 10));
            return theHit;
        }
}