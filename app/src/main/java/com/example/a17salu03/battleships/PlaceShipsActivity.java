package com.example.a17salu03.battleships;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import static com.example.a17salu03.battleships.Tile.TILE_TYPE_SIZE_1_SHIPID_1;
import static com.example.a17salu03.battleships.Tile.TILE_TYPE_SIZE_1_SHIPID_2;
import static com.example.a17salu03.battleships.Tile.TILE_TYPE_SIZE_1_SHIPID_3;
import static com.example.a17salu03.battleships.Tile.TILE_TYPE_SIZE_2_SHIPID_4_H_L;
import static com.example.a17salu03.battleships.Tile.TILE_TYPE_SIZE_2_SHIPID_4_H_R;
import static com.example.a17salu03.battleships.Tile.TILE_TYPE_SIZE_2_SHIPID_4_V_L;
import static com.example.a17salu03.battleships.Tile.TILE_TYPE_SIZE_2_SHIPID_4_V_R;
import static com.example.a17salu03.battleships.Tile.TILE_TYPE_SIZE_2_SHIPID_5_H_L;
import static com.example.a17salu03.battleships.Tile.TILE_TYPE_SIZE_2_SHIPID_5_H_R;
import static com.example.a17salu03.battleships.Tile.TILE_TYPE_SIZE_2_SHIPID_5_V_L;
import static com.example.a17salu03.battleships.Tile.TILE_TYPE_SIZE_2_SHIPID_5_V_R;
import static com.example.a17salu03.battleships.Tile.TILE_TYPE_SIZE_3_SHIPID_6_H_L;
import static com.example.a17salu03.battleships.Tile.TILE_TYPE_SIZE_3_SHIPID_6_H_M;
import static com.example.a17salu03.battleships.Tile.TILE_TYPE_SIZE_3_SHIPID_6_H_R;
import static com.example.a17salu03.battleships.Tile.TILE_TYPE_SIZE_3_SHIPID_6_V_L;
import static com.example.a17salu03.battleships.Tile.TILE_TYPE_SIZE_3_SHIPID_6_V_M;
import static com.example.a17salu03.battleships.Tile.TILE_TYPE_SIZE_3_SHIPID_6_V_R;
import static com.example.a17salu03.battleships.Tile.TILE_TYPE_WATER;

public class PlaceShipsActivity extends AppCompatActivity {

    private GridFragment playerGrid;
    private ArrayList<Integer> usedTiles = new ArrayList<>();
    private String[] boardState = new String[49];
    private int selectedShipID;
    private boolean isHorizontal = true;
    private int ship1ID = 1;
    private int ship2ID = 4;
    private int ship3ID = 6;
    private Button rotateBtn;
    private ImageView img_1r = null;
    private ImageView img_2r = null;
    private ImageView img_3r = null;
    private TextView txt_1r = null;
    private TextView txt_2r = null;
    private TextView txt_3r = null;

    private ImageView lastClickedShip;
    private MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_ships);
        mediaPlayer = MediaPlayer.create(getBaseContext(), R.raw.battle_music);
        mediaPlayer.start();

        playerGrid = new GridFragment();
        playerGrid.setClickableTiles(true);
        FragmentTransaction playerft = getSupportFragmentManager().beginTransaction();
        playerft.replace(R.id.fragment_container_player, playerGrid);
        playerft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        playerft.commit();


        for (int i = 0; i < boardState.length; i++){
            boardState[i] = TILE_TYPE_WATER;
        }

        rotateBtn = findViewById(R.id.rotate);
        Button doneBtn = findViewById(R.id.done);
        Button leaveBtn = findViewById(R.id.leave);
        img_1r = findViewById(R.id.ship_1r_draw);
        img_2r = findViewById(R.id.ship_2r_draw);
        img_3r = findViewById(R.id.ship_3r_draw);
        txt_1r = findViewById(R.id.ship_1r_text);
        txt_2r = findViewById(R.id.ship_2r_text);
        txt_3r = findViewById(R.id.ship_3r_text);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(R.layout.dialog_leave);

        builder.setPositiveButton(R.string.leave, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                leaveGame();
            }
        });
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });
        final AlertDialog leaveDialog = builder.create();

        img_1r.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int shipsLeft0 = Integer.parseInt(txt_1r.getText().toString());
                if(shipsLeft0 > 0){
                    generateShipID(1);
                    highlightChosenShip(img_1r);
                }
            }
        });
        img_2r.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int shipsLeft1 = Integer.parseInt(txt_2r.getText().toString());
                if(shipsLeft1 > 0){
                    generateShipID(2);
                    highlightChosenShip(img_2r);
                }
            }
        });
        img_3r.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int shipsLeft2 = Integer.parseInt(txt_3r.getText().toString());
                if(shipsLeft2 > 0){
                    generateShipID(3);
                    highlightChosenShip(img_3r);
                }
            }
        });
        doneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent();
                intent.putExtra("boardState", boardState);
                setResult(RESULT_OK, intent);
                mediaPlayer.stop();
                finish();
            }
        });

        leaveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                leaveDialog.show();
            }
        });

    }

    public void leaveGame(){
        setResult(StartActivity.RESULT_LEAVE);
        finish();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onPause(){
        super.onPause();
        mediaPlayer.stop();
    }

    private void generateShipID(int shipSize){
        switch (shipSize){
            case 1:
                selectedShipID = ship1ID;
                break;
            case 2:
                selectedShipID = ship2ID;
                break;
            case 3:
                selectedShipID = ship3ID;
                break;
        }
    }

    public void onRotateClicked(View view){
        isHorizontal = !isHorizontal;
        if (isHorizontal){
            rotateBtn.setText(R.string.horizontal);
        } else {
            rotateBtn.setText(R.string.vertical);
        }
    }

    private void highlightChosenShip(ImageView clickedShip){
        if (lastClickedShip != null)
            lastClickedShip.setBackgroundColor(0);
        if (clickedShip != null)
            clickedShip.setBackgroundResource(R.color.Color_Selected);
        lastClickedShip = clickedShip;
    }

    public void onPlaceClicked(View view){
       int clickedTile = playerGrid.getClickedTile();

        if (!(clickedTile == -1) && selectedShipID != 0){
            if (isVacant(clickedTile)){
                int shipsLeft = -1;
                if (selectedShipID >= 1 && selectedShipID <= 3 && checkShipsAtPosition(clickedTile, 1)){
                    shipsLeft = Integer.parseInt(txt_1r.getText().toString());
                    ship1ID++;
                    shipsLeft--;
                    txt_1r.setText(String.valueOf(shipsLeft));
                } else if (selectedShipID >= 4 && selectedShipID <= 5 && checkShipsAtPosition(clickedTile, 2)){
                    shipsLeft = Integer.parseInt(txt_2r.getText().toString());
                    ship2ID++;
                    shipsLeft--;
                    txt_2r.setText(String.valueOf(shipsLeft));
                } else if (selectedShipID >= 6 && checkShipsAtPosition(clickedTile, 3)){
                    shipsLeft = Integer.parseInt(txt_3r.getText().toString());
                    ship3ID++;
                    shipsLeft--;
                    txt_3r.setText(String.valueOf(shipsLeft));
                }
                if (shipsLeft == 0){
                    selectedShipID = 0;
                    highlightChosenShip(null);
                }
            }

        }

    }

    private boolean isVacant(int position){
        boolean isTileVacant = true;
        for (int tilePos : usedTiles){
            if (position == tilePos){
                isTileVacant = false;
                break;
            }
        }
        return isTileVacant;
    }

    private boolean checkShipsAtPosition(int startPosition, int lenght){
        if (lenght == 1) {                                           // skepp  1
            if (selectedShipID == 1)
                boardState[startPosition] = TILE_TYPE_SIZE_1_SHIPID_1;
            else if (selectedShipID == 2)
                boardState[startPosition] = TILE_TYPE_SIZE_1_SHIPID_2;
            else if (selectedShipID == 3)
                boardState[startPosition] = TILE_TYPE_SIZE_1_SHIPID_3;

            Tile tile = playerGrid.getTileAtPosition(startPosition);
            tile.getTileImage().setImageBitmap(BitMapEdit.combineImages(R.drawable.skepp_1r_w, this.getWindow().getDecorView().findViewById(android.R.id.content), 0, false));
            tile.setClickDisabled(true);
            usedTiles.add(startPosition);
            return true;
        } else if (isHorizontal) {
            if (lenght == 2) {                                     // skepp 2
                if (startPosition % 7 == 6 && isVacant(startPosition - 1)) {
                    placeShipAt(startPosition - 1, startPosition);
                    return true;
                } else if (isVacant(startPosition + 1)) {
                    placeShipAt(startPosition, startPosition + 1);
                    return true;
                }
            } else if (lenght == 3) {                                            // skepp 3
                if (startPosition % 7 == 6 && isVacant(startPosition - 1) && isVacant(startPosition - 2)) {
                    placeShipAt(startPosition - 2, startPosition - 1, startPosition);
                    return true;
                } else if (startPosition % 7 == 0 && isVacant(startPosition + 1) && isVacant(startPosition + 2)) {
                    placeShipAt(startPosition, startPosition + 1, startPosition + 2);
                    return true;
                }else if (isVacant(startPosition - 1) && isVacant(startPosition + 1)) {
                    placeShipAt(startPosition - 1, startPosition, startPosition + 1);
                    return true;
                }
            }
        } else if (!isHorizontal){
            if (lenght == 2){
                if (startPosition > 41 && isVacant(startPosition - 7)){
                    placeShipAt(startPosition - 7, startPosition);
                    return true;
                } else if (isVacant(startPosition + 7)){
                    placeShipAt(startPosition, startPosition + 7);
                    return  true;
                }

            } else if (lenght == 3){
                if (startPosition < 7 && isVacant(startPosition + 7) && isVacant(startPosition + 14)){
                    placeShipAt(startPosition, startPosition + 7, startPosition + 14);
                    return true;
                } else if (startPosition > 41 && isVacant(startPosition - 7) && isVacant(startPosition - 14)){
                    placeShipAt(startPosition - 14, startPosition - 7, startPosition);
                    return true;
                } else if (isVacant(startPosition - 7) && isVacant( startPosition + 7)){
                    placeShipAt(startPosition - 7, startPosition, startPosition + 7);
                    return true;
                }
            }
        }
        return false;
    }

    private void placeShipAt(int startPosition, int endPosition){
        Tile startTile = playerGrid.getTileAtPosition(startPosition);
        Tile endTile = playerGrid.getTileAtPosition(endPosition);
        if (selectedShipID == 4) {
            if (isHorizontal){
                boardState[startPosition] = TILE_TYPE_SIZE_2_SHIPID_4_H_L;
                boardState[endPosition] = TILE_TYPE_SIZE_2_SHIPID_4_H_R;
            } else {
                boardState[startPosition] = TILE_TYPE_SIZE_2_SHIPID_4_V_L;
                boardState[endPosition] = TILE_TYPE_SIZE_2_SHIPID_4_V_R;
            }

        } else if (selectedShipID == 5) {
            if (isHorizontal){
                boardState[startPosition] = TILE_TYPE_SIZE_2_SHIPID_5_H_L;
                boardState[endPosition] = TILE_TYPE_SIZE_2_SHIPID_5_H_R;
            } else {
                boardState[startPosition] = TILE_TYPE_SIZE_2_SHIPID_5_V_L;
                boardState[endPosition] = TILE_TYPE_SIZE_2_SHIPID_5_V_R;
            }
        }
        int rotateDegrees = 0;
        boolean flipImage = false;
        if (!isHorizontal){
           rotateDegrees = 90;
           flipImage = true;
        }
        startTile.getTileImage().setImageBitmap(BitMapEdit.combineImages(R.drawable.skepp_2r_w_l, this.getWindow().getDecorView().findViewById(android.R.id.content), rotateDegrees, flipImage));
        endTile.getTileImage().setImageBitmap(BitMapEdit.combineImages(R.drawable.skepp_2r_w_r, this.getWindow().getDecorView().findViewById(android.R.id.content), rotateDegrees, flipImage));

        startTile.setClickDisabled(true);
        endTile.setClickDisabled(true);
        usedTiles.add(startPosition);
        usedTiles.add(endPosition);

    }

    private void placeShipAt(int startPosition, int middlePosition, int endPosition){
        Tile startTile = playerGrid.getTileAtPosition(startPosition);
        Tile middleTile = playerGrid.getTileAtPosition(middlePosition);
        Tile endTile = playerGrid.getTileAtPosition(endPosition);

        if (isHorizontal){
            boardState[startPosition] = TILE_TYPE_SIZE_3_SHIPID_6_H_L;
            boardState[middlePosition] = TILE_TYPE_SIZE_3_SHIPID_6_H_M;
            boardState[endPosition] = TILE_TYPE_SIZE_3_SHIPID_6_H_R;
        } else {
            boardState[startPosition] = TILE_TYPE_SIZE_3_SHIPID_6_V_L;
            boardState[middlePosition] = TILE_TYPE_SIZE_3_SHIPID_6_V_M;
            boardState[endPosition] = TILE_TYPE_SIZE_3_SHIPID_6_V_R;
        }

        int rotateDegrees = 0;
        boolean flipImage = false;
        if (!isHorizontal){
            rotateDegrees = 90;
            flipImage = true;
        }
        startTile.getTileImage().setImageBitmap(BitMapEdit.combineImages(R.drawable.skepp_3r_w_l, this.getWindow().getDecorView().findViewById(android.R.id.content), rotateDegrees, flipImage));
        middleTile.getTileImage().setImageBitmap(BitMapEdit.combineImages(R.drawable.skepp_3r_w_m, this.getWindow().getDecorView().findViewById(android.R.id.content), rotateDegrees, flipImage));
        endTile.getTileImage().setImageBitmap(BitMapEdit.combineImages(R.drawable.skepp_3r_w_r, this.getWindow().getDecorView().findViewById(android.R.id.content), rotateDegrees, flipImage));

        startTile.setClickDisabled(true);
        middleTile.setClickDisabled(true);
        endTile.setClickDisabled(true);
        usedTiles.add(startPosition);
        usedTiles.add(middlePosition);
        usedTiles.add(endPosition);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}