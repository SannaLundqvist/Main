package com.example.a17salu03.battleships;


import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import static com.example.a17salu03.battleships.Tile.TILE_TYPE_MISS;
import static com.example.a17salu03.battleships.Tile.TILE_TYPE_WATER;

public class BoardActivity extends AppCompatActivity implements MediaPlayer.OnSeekCompleteListener {
    private String[] myShips;
    private String[] opponentsShips;
    private Button fireBtn = null;
    private Button leaveBtn = null;
    private GridFragment opponentGrid;
    private GridFragment playerGrid;
    private int position;
    private boolean isBackroundMusicOn;
    private boolean isEffectMusicOn;
    private MediaPlayer backroundMusicPlayer;
    private int musicDuration;
    private SharedPreferences prefs;

    public static final String SHIP_TILE_IS_HIT = "D";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board);

        Intent intent = getIntent();
        myShips = intent.getStringArrayExtra("myShips");
        opponentsShips = intent.getStringArrayExtra("opponentsShips");
        isBackroundMusicOn = intent.getBooleanExtra("isBackroundMusicOn", true);
        isEffectMusicOn = intent.getBooleanExtra("isEffectMusicOn", true);;

        shipsRemaining();
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

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(R.layout.dialog_leave);

        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        musicDuration = prefs.getInt("musicDuration", 0);

        builder.setPositiveButton(R.string.leave, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                leaveGame();
            }
        });
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface leaveDialog, int id) {
                leaveDialog.dismiss();
            }
        });

// Set other dialog properties

// Create the AlertDialog
        final AlertDialog dialog = builder.create();

        leaveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();

            }
        });
    }
    public void leaveGame(){
        setResult(StartActivity.RESULT_LEAVE);
        finish();
    }
    @Override
    protected void onPause(){
        super.onPause();
        if(isBackroundMusicOn){
            backroundMusicPlayer.stop();
            musicDuration = backroundMusicPlayer.getCurrentPosition();
            prefs.edit().putInt("musicDuration", musicDuration).apply();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(isBackroundMusicOn){

            backroundMusicPlayer = MediaPlayer.create(getBaseContext(), R.raw.battle_music);
            backroundMusicPlayer.setOnSeekCompleteListener(this);
            backroundMusicPlayer.seekTo(musicDuration);
        }
    }

    public void onFireClick(View view) {
        MediaPlayer fireMusicPlayer = MediaPlayer.create(getBaseContext(), R.raw.shot);
        int clickedTile = opponentGrid.getClickedTile();
        if (!(clickedTile == -1)) {
            if(isEffectMusicOn)
                fireMusicPlayer.start();
            boolean hasWon;
            if (isHit(clickedTile)) {
                Toast.makeText(BoardActivity.this, "Hit!", Toast.LENGTH_LONG).show();
                hasWon = checkIfWon();
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

    private void shipsRemaining() {
        int friendlyShip_small_Remaining = PlaceShipsActivity.NUMBER_OF_SMALL_TILES;
        int friendlyShip_medium_Remaining = PlaceShipsActivity.NUMBER_OF_MEDIUM_TILES;
        int friendlyShip_large_Remaining = PlaceShipsActivity.NUMBER_OF_LARGE_TILES;

        String friendlyShipIDs = null;
        TextView ship_small_friendly_remaining = findViewById(R.id.ship_small_friendly_remaining);
        TextView ship_medium_friendly_remaining = findViewById(R.id.ship_medium_friendly_remaining);
        TextView ship_large_friendly_remaining = findViewById(R.id.ship_large_friendly_remaining);
        StringBuilder friendlySB = new StringBuilder();
        for (String string : myShips) {
            if (string.contains(SHIP_TILE_IS_HIT)) {
                friendlyShipIDs = friendlySB.append(string.charAt(0)).toString();
            }
        }
        if (friendlyShipIDs != null && !friendlyShipIDs.isEmpty()) {
            for (int i = 0; i < friendlyShipIDs.length(); i++) {
                if (friendlyShipIDs.charAt(i) == '1' || friendlyShipIDs.charAt(i) == '2' || friendlyShipIDs.charAt(i) == '3') {
                    friendlyShip_small_Remaining--;
                } else if (friendlyShipIDs.charAt(i) == '4' || friendlyShipIDs.charAt(i) == '5') {
                    friendlyShip_medium_Remaining--;
                } else if (friendlyShipIDs.charAt(i) == '6') {
                    friendlyShip_large_Remaining--;
                }
            }
        }
        ship_small_friendly_remaining.setText("x" + friendlyShip_small_Remaining);
        ship_medium_friendly_remaining.setText("x" + friendlyShip_medium_Remaining);
        ship_large_friendly_remaining.setText("x" + friendlyShip_large_Remaining);

        int opponentShip_small_Remaining = PlaceShipsActivity.NUMBER_OF_SMALL_TILES;
        int opponentShip_medium_Remaining = PlaceShipsActivity.NUMBER_OF_MEDIUM_TILES;
        int opponentShip_large_Remaining = PlaceShipsActivity.NUMBER_OF_LARGE_TILES;

        String opponentShipIDs = null;
        TextView ship_small_opponent_remaining = findViewById(R.id.ship_small_opponent_remaining);
        TextView ship_medium_opponent_remaining = findViewById(R.id.ship_medium_opponent_remaining);
        TextView ship_large_opponent_remaining = findViewById(R.id.ship_large_opponent_remaining);
        StringBuilder opponentSB = new StringBuilder();
        for (String string : opponentsShips) {
            if (string.contains(SHIP_TILE_IS_HIT)) {
                opponentShipIDs = opponentSB.append(string.charAt(0)).toString();
            }
        }
        if (opponentShipIDs != null && !opponentShipIDs.isEmpty()) {
            for (int i = 0; i < opponentShipIDs.length(); i++) {
                if (opponentShipIDs.charAt(i) == '1' || opponentShipIDs.charAt(i) == '2' || opponentShipIDs.charAt(i) == '3') {
                    opponentShip_small_Remaining--;
                } else if (opponentShipIDs.charAt(i) == '4' || opponentShipIDs.charAt(i) == '5') {
                    opponentShip_medium_Remaining--;
                } else if (opponentShipIDs.charAt(i) == '6') {
                    opponentShip_large_Remaining--;
                }
            }
        }
        ship_small_opponent_remaining.setText("x" + opponentShip_small_Remaining);
        ship_medium_opponent_remaining.setText("x" + opponentShip_medium_Remaining);
        ship_large_opponent_remaining.setText("x" + opponentShip_large_Remaining);
    }

    private boolean checkIfWon() {
        int shipRemaining = PlaceShipsActivity.NUMBER_OF_SHIP_TILES;
        for (int i = 0; i < opponentsShips.length; i++) {
            if (opponentsShips[i].contains(SHIP_TILE_IS_HIT)){
                shipRemaining -= 1;
            }
        }
        return (shipRemaining == 0);
    }

    private boolean isHit(int position) {
        boolean isHit = false;
        if (!opponentsShips[position].equals(TILE_TYPE_WATER)){
            opponentsShips[position] = opponentsShips[position] + SHIP_TILE_IS_HIT;
            isHit = true;
        } else {
            opponentsShips[position] = TILE_TYPE_MISS;
        }
        return isHit;
    }

    @Override
    public void onSeekComplete(MediaPlayer mp) {
        backroundMusicPlayer.start();
    }
}