package com.example.a17salu03.battleships;


import android.content.Intent;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class PlaceShipsActivity extends AppCompatActivity {

    public static final String TILE_TYPE_WATER = "W";
    public static final String TILE_TYPE_SIZE_1_SHIPID_1 = "1H";
    public static final String TILE_TYPE_SIZE_1_SHIPID_2 = "2H";
    public static final String TILE_TYPE_SIZE_1_SHIPID_3 = "3H";

    public static final String TILE_TYPE_SIZE_2_SHIPID_4_H_L = "4HL";
    public static final String TILE_TYPE_SIZE_2_SHIPID_4_H_R = "4HR";
    public static final String TILE_TYPE_SIZE_2_SHIPID_4_V_L = "4VL";
    public static final String TILE_TYPE_SIZE_2_SHIPID_4_V_R = "4VR";
    public static final String TILE_TYPE_SIZE_2_SHIPID_5_H_L = "5HL";
    public static final String TILE_TYPE_SIZE_2_SHIPID_5_H_R = "5HR";
    public static final String TILE_TYPE_SIZE_2_SHIPID_5_V_L = "5VL";
    public static final String TILE_TYPE_SIZE_2_SHIPID_5_V_R = "5VR";

    public static final String TILE_TYPE_SIZE_3_SHIPID_6_H_L = "6HL";
    public static final String TILE_TYPE_SIZE_3_SHIPID_6_H_M = "6HM";
    public static final String TILE_TYPE_SIZE_3_SHIPID_6_H_R = "6HR";
    public static final String TILE_TYPE_SIZE_3_SHIPID_6_V_L = "6VL";
    public static final String TILE_TYPE_SIZE_3_SHIPID_6_V_M = "6VM";
    public static final String TILE_TYPE_SIZE_3_SHIPID_6_V_R = "6VR";

    public static final int GROUP_SMALL = 1;
    public static final int GROUP_MEDIUM_LEFT_VERTICAL = 2;
    public static final int GROUP_MEDIUM_LEFT_HORISONTAL = 3;
    public static final int GROUP_MEDIUM_RIGHT_VERTICAL = 4;
    public static final int GROUP_MEDIUM_RIGHT_HORISONAL = 5;
    public static final int GROUP_LARGE_LEFT_VERTICAL = 6;
    public static final int GROUP_LARGE_MIDDLE_VERTICAL = 7;
    public static final int GROUP_LARGE_RIGHT_VERTICAL = 8;
    public static final int GROUP_LARGE_LEFT_HORISONTAL = 9;
    public static final int GROUP_LARGE_MIDDLE_HORISONTAL = 10;
    public static final int GROUP_LARGE_RIGHT_HORISONTAL = 11;

    private GridFragment playerGrid;
    private ArrayList<Integer> usedTiles = new ArrayList<>();
    private int[] boardState = new int[49];  //0 vatten, 1-3 skepp 1, 4-5 skepp 2, 6 skepp 3
    private int shipToBePlaced;
    private int selectedShipID;
    private boolean isHorizontal = true;
    private int ship1ID = 1;
    private int ship2ID = 4;
    private int ship3ID = 6;
    private int selectedPosition;
    private boolean[] isShipAtPosition;
    private Button rotateBtn;
    private Button placeBtn = null;
    private Button doneBtn = null;
    private ImageView img_1r = null;
    private ImageView img_2r = null;
    private ImageView img_3r = null;
    private TextView txt_1r = null;
    private TextView txt_2r = null;
    private TextView txt_3r = null;
    //används för att ta reda på vilket skepp som ska placeras (än så
    //länge i en förvald ordning... bör vara enkelt att få i spelarvald ordning
    private int shipNbr = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_ships);

        playerGrid = new GridFragment();
        playerGrid.setClickableTiles(true);
        FragmentTransaction playerft = getSupportFragmentManager().beginTransaction();
        playerft.replace(R.id.fragment_container_player, playerGrid);
        playerft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        playerft.commit();

        for (int i = 0; i < boardState.length; i++){
            boardState[i] = 0;
        }

        rotateBtn = findViewById(R.id.rotate);
        placeBtn = findViewById(R.id.place);
        doneBtn = findViewById(R.id.done);
        img_1r = findViewById(R.id.ship_1r_draw);
        img_2r = findViewById(R.id.ship_2r_draw);
        img_3r = findViewById(R.id.ship_3r_draw);
        txt_1r = findViewById(R.id.ship_1r_text);
        txt_2r = findViewById(R.id.ship_2r_text);
        txt_3r = findViewById(R.id.ship_3r_text);


        isShipAtPosition = new boolean[49];

        img_1r.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int shipsLeft0 = Integer.parseInt(txt_1r.getText().toString());
                if(shipsLeft0 > 0){
                    generateShipID(1);
                }
            }
        });
        img_2r.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int shipsLeft1 = Integer.parseInt(txt_2r.getText().toString());
                if(shipsLeft1 > 0){
                    generateShipID(2);
                }
            }
        });
        img_3r.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int shipsLeft2 = Integer.parseInt(txt_3r.getText().toString());
                if(shipsLeft2 > 0){
                    generateShipID(3);
                }
            }
        });
        doneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent();
                intent.putExtra("boardState", boardState);
                setResult(RESULT_OK, intent);
                finish();

            }
        });

    }

    @Override
    protected void onPause(){
        super.onPause();
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
            rotateBtn.setText("Horizontal");
        } else {
            rotateBtn.setText("Vertical");
        }
    }

    public void onPlaceClicked(View view){
       Integer clickedTile = Integer.valueOf(playerGrid.getClickedTile());

        if (clickedTile != null && selectedShipID != 0){
            if (isVacant(clickedTile)){


                if (selectedShipID >= 1 && selectedShipID <= 3 && checkShipsAtPosition(clickedTile, 1)){
                    int shipsLeft = Integer.parseInt(txt_1r.getText().toString());
                    ship1ID++;
                    shipsLeft--;
                    txt_1r.setText(shipsLeft + "");

                } else if (selectedShipID >= 4 && selectedShipID <= 5 && checkShipsAtPosition(clickedTile, 2)){
                    int shipsLeft = Integer.parseInt(txt_2r.getText().toString());
                    ship2ID++;
                    shipsLeft--;
                    txt_2r.setText(shipsLeft + "");
                } else if (selectedShipID >= 6 && checkShipsAtPosition(clickedTile, 3)){
                    int shipsLeft = Integer.parseInt(txt_3r.getText().toString());
                    ship3ID++;
                    shipsLeft--;
                    txt_3r.setText(shipsLeft + "");
                }
                selectedShipID = 0;
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
            boardState[startPosition] = selectedShipID;
            Tile tile = playerGrid.getTileAtPosition(startPosition);
            //       tile.setClickedImage(R.drawable.skepp_1r);
            tile.getTileImage().setVisibility(View.INVISIBLE);

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
        Tile tile;
        for (int i = startPosition; i <= endPosition; i++){
            if (i == startPosition || i == endPosition){        //Vertical fail safe
                boardState[i] = selectedShipID;
                tile = playerGrid.getTileAtPosition(i);
                tile.getTileImage().setVisibility(View.INVISIBLE);
                usedTiles.add(i);
            }
        }
    }

    private void placeShipAt(int startPosition, int middlePosition, int endPosition){
        Tile tile;
        for (int i = startPosition; i <= endPosition; i++){
            if (i == startPosition || i == middlePosition || i == endPosition){     //Vertical fail safe
                boardState[i] = selectedShipID;
                tile = playerGrid.getTileAtPosition(i);
                tile.getTileImage().setVisibility(View.INVISIBLE);
                usedTiles.add(i);
            }
        }
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}