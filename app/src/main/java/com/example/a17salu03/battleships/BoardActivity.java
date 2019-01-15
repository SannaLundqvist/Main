package com.example.a17salu03.battleships;


import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class BoardActivity extends AppCompatActivity implements GridFragment.OnItemClickedListener {
    private String[] myShips;
    private String[] opponentsShips;
    private Button fireBtn = null;
    private Button leaveBtn = null;
    private GridFragment opponentGrid;
    private boolean hasWon;
    private GridFragment playerGrid;
    private int position;


    public static final String TILE_TYPE_WATER = "W";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board);

        myShips = new String[49];
        for (int i = 0; i < myShips.length; i++){
            myShips[i] = TILE_TYPE_WATER;
        }

    /*    int [] myShips = {0, 0,	0, 0, 0, 0, 0,
                        0, 6, 0, 0, 0, 0, 0,
                        0, 6, 0, 0, 1, 0, 0,
                        0, 6, 0, 0, 0, 2, 0,
                        0, 0, 0, 4, 4, 10, 0,
                        0, 0, 3, 0, 5, 0, 0,
                        10, 0, 0, 0, 5, 0, 0}; */
        opponentsShips = getIntent().getStringArrayExtra("opponentsShips");

        fireBtn = findViewById(R.id.fire);
        leaveBtn = findViewById(R.id.leave);

        playerGrid = new GridFragment();
        playerGrid.setMyBoard(myShips);
        FragmentTransaction playerft = getSupportFragmentManager().beginTransaction();
        playerft.replace(R.id.fragment_container_player, playerGrid);
        playerft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        playerft.commit();

        opponentGrid = new GridFragment();
        opponentGrid.setClickableTiles(true);
        opponentGrid.setOpponentsBoard(opponentsShips);
        FragmentTransaction opponentft = getSupportFragmentManager().beginTransaction();
        opponentft.replace(R.id.fragment_container_opponent, opponentGrid);
        opponentft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        opponentft.commit();

        leaveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(StartActivity.RESULT_LEAVE);
                finish();
            }
        });
    }

    public void onFireClick(View view) {
        //MediaPlayer mediaPlayer = MediaPlayer.create(getBaseContext(), R.raw.shot);
        int clickedTile = opponentGrid.getClickedTile();
        if (clickedTile >= 0) {
           // mediaPlayer.start();
            if (isHit(clickedTile)) {
                opponentsShips[clickedTile] = opponentsShips[clickedTile] + 10;
                Toast.makeText(BoardActivity.this, "Hit!", Toast.LENGTH_LONG).show();
                hasWon = checkIfWon();
            } else {
                opponentsShips[clickedTile] = opponentsShips[clickedTile] + 10;
                Toast.makeText(BoardActivity.this, "You missed...", Toast.LENGTH_SHORT).show();
                hasWon = false;
            }

            Intent intent = new Intent();
            intent.putExtra("opponentsShips", opponentsShips);
            intent.putExtra("hasWon", hasWon);
            setResult(RESULT_OK, intent);
            finish();
        }
    }

    public void onBigClick(View view) {
        ImageView imageView = (ImageView) view;
        imageView.setBackgroundResource(R.drawable.red_border);
        Toast.makeText(getApplicationContext(), "onItemClick", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onItemClicked(int position) {
        this.position = position;
        Toast.makeText(getBaseContext(), "fakePosition: " + position, Toast.LENGTH_LONG).show();
    }

    private boolean checkIfWon() {
        for (int i = 0; i < opponentsShips.length; i++) {
            //         if ((opponentsShips[i] > 0) && (opponentsShips[i] < 10))
            return false;

        }
        return true;
    }

    private boolean isHit(int tile) {
        boolean isHit = false;



 //       boolean theHit = ((opponentsShips[tile] > 0) && (opponentsShips[tile] < 10));
        return isHit;
    }
}