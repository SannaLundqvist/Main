package com.example.a17salu03.battleships;


import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import static com.example.a17salu03.battleships.Tile.TILE_TYPE_MISS;
import static com.example.a17salu03.battleships.Tile.TILE_TYPE_WATER;

public class BoardActivity extends AppCompatActivity implements GridFragment.OnItemClickedListener {
    private String[] myShips;
    private String[] opponentsShips;
    private Button fireBtn = null;
    private GridFragment opponentGrid;
    private boolean hasWon;
    private GridFragment playerGrid;
    private int position;
    private MediaPlayer mediaPlayer;
    private int friendlyShip_small_Remaining;
    private int friendlyShip_medium_Remaining;
    private int friendlyShip_large_Remaining;

    private int opponentShip_small_Remaining;
    private int opponentShip_medium_Remaining;
    private int opponentShip_large_Remaining;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board);
        mediaPlayer = MediaPlayer.create(getBaseContext(), R.raw.battle_music);
        mediaPlayer.start();



/*
        myShips = new String[49];
        for (int i = 0; i < myShips.length; i++){
            myShips[i] = TILE_TYPE_WATER;
        }

    /*    String [] myShips = {"W", "W", "W", "W", "W", "W", "W",
                "W", "W", "W", "W", "W", "W", "W",
                "W", "W", "W", "W", "W", "W", "W",
                "W", "W", "W", "W", "W", "W", "W",
                "W", "W", "W", "W", "W", "W", "W",
                "W", "W", "W", "W", "W", "W", "W",
                "W", "W", "W", "W", "W", "W", "W"};

        String [] opponentsShips = {"W", "W", "W", "W", "W", "W", "W",
                "W", "W", "W", "W", "W", "W", "W",
                "W", "W", "W", "W", "W", "W", "W",
                "W", "W", "W", "W", "W", "W", "W",
                "W", "W", "W", "W", "W", "W", "W",
                "W", "W", "W", "W", "W", "W", "W",
                "W", "W", "W", "W", "W", "W", "W"}; */

        myShips = getIntent().getStringArrayExtra("myShips");
        opponentsShips = getIntent().getStringArrayExtra("opponentsShips");
    //    shipsRemaining();
        fireBtn = findViewById(R.id.fire);

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
    }
    @Override
    protected void onPause(){
        super.onPause();
        mediaPlayer.stop();
    }

    public void onFireClick(View view) {
        MediaPlayer mediaPlayer = MediaPlayer.create(getBaseContext(), R.raw.shot);
        int clickedTile = opponentGrid.getClickedTile();
        if (!(clickedTile == 50)) {
            mediaPlayer.start();
            if (isHit(clickedTile)) {
                Toast.makeText(BoardActivity.this, "Hit!", Toast.LENGTH_LONG).show();
                hasWon = checkIfWon(clickedTile);
            } else {
                Toast.makeText(BoardActivity.this, "You missed...", Toast.LENGTH_LONG).show();
                hasWon = false;
            }

            Intent intent = new Intent();
            intent.putExtra("opponentsShips", opponentsShips);
            intent.putExtra("myShips", myShips);
            intent.putExtra("hasWon", hasWon);
            setResult(RESULT_OK, intent);
            finish();
        }
    }

    public void onBigClick(View view) {
        ImageView imageView = (ImageView) view;
        imageView.setBackgroundResource(R.drawable.red_borderrr);
        Toast.makeText(getApplicationContext(), "onItemClick", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onItemClicked(int position) {
        this.position = position;
        Toast.makeText(getBaseContext(), "fakePosition: " + position, Toast.LENGTH_LONG).show();
    }

    private void shipsRemaining() {
        String shipIDs = null;
        StringBuilder sb = new StringBuilder();
        TextView ship_1_remaining = findViewById(R.id.ship_1_remaining);

        for (String string : myShips) {
            if (string.contains("D")) {
                shipIDs = sb.append(string.charAt(0)).toString();
            }
        }
        if (shipIDs != null && !shipIDs.isEmpty()) {
            for (int i = 0; i < shipIDs.length(); i++) {
                if (shipIDs.charAt(i) == 1 || shipIDs.charAt(i) == 2 || shipIDs.charAt(i) == 3) {
                    friendlyShip_small_Remaining--;
                } else if (shipIDs.charAt(i) == 4 || shipIDs.charAt(i) == 5) {
                    friendlyShip_medium_Remaining--;
                } else if (shipIDs.charAt(i) == 6) {
                    friendlyShip_large_Remaining--;
                }
            }
        }

        ship_1_remaining.setText(friendlyShip_small_Remaining);

        for (String string : opponentsShips) {
            if (string.contains("D")) {
                shipIDs = sb.append(string.charAt(0)).toString();
            }
        }
        if (shipIDs != null && !shipIDs.isEmpty()){
            for (int i = 0; i < shipIDs.length(); i++) {
                if (shipIDs.charAt(i) == 1 || shipIDs.charAt(i) == 2 || shipIDs.charAt(i) == 3) {
                    opponentShip_large_Remaining--;
                } else if (shipIDs.charAt(i) == 4 || shipIDs.charAt(i) == 5) {
                    opponentShip_medium_Remaining--;
                } else if (shipIDs.charAt(i) == 6) {
                    opponentShip_large_Remaining--;
                }
            }
        }

    }

    private boolean checkIfWon(int clickedTile) {
        int shipRemaining = 10;
        for (int i = 0; i < opponentsShips.length; i++) {
            if (opponentsShips[clickedTile].contains("D")){
                shipRemaining -= 1;
            }
        }
        return (shipRemaining == 0);
    }

    private boolean isHit(int position) {
        boolean isHit = false;
        if (!opponentsShips[position].equals(TILE_TYPE_WATER)){
            opponentsShips[position] = opponentsShips[position] + "D";
            isHit = true;
        } else {
            opponentsShips[position] = TILE_TYPE_MISS;
        }
        return isHit;
    }
}