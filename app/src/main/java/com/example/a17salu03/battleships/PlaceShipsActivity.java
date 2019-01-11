package com.example.a17salu03.battleships;


import android.content.Intent;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class PlaceShipsActivity extends AppCompatActivity {

    private GridFragment playerGrid;
    private ArrayList<Integer> usedTiles = new ArrayList<>();
    private int[] boardState = new int[49];
    private int shipToBePlaced;
    private int selectedShipID;

    private int ship1ID = 1;
    private int ship2ID = 4;
    private int ship3ID = 6;
    private int selectedPosition;
    private boolean[] isShipAtPosition;
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
        playerft.addToBackStack(null);
        playerft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        playerft.commit();

        for (int i = 0; i < boardState.length; i++){
            boardState[i] = 0;
        }

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

                }else{
                    //Toast.makeText(this, R.string.placed_all_ships, Toast.LENGTH_SHORT);
                }
            }
        });
        img_2r.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int shipsLeft1 = Integer.parseInt(txt_2r.getText().toString());
                if(shipsLeft1 > 0){
                    generateShipID(2);
                }else{
                    //Toast.makeText(this, R.string.placed_all_ships, Toast.LENGTH_SHORT);
                }
            }
        });
        img_3r.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int shipsLeft2 = Integer.parseInt(txt_3r.getText().toString());
                if(shipsLeft2 > 0){
                    generateShipID(3);
                }else{
                    //Toast.makeText(this, R.string.placed_all_ships, Toast.LENGTH_SHORT);
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

    public void onPlaceClicked(View view){
       Integer clickedTile = Integer.valueOf(playerGrid.getClickedTile());

        if (clickedTile != null && selectedShipID != 0){
            if (isVacant(clickedTile)){
                boardState[clickedTile] = selectedShipID;

                Toast.makeText(getBaseContext(),
                        boardState[clickedTile] + " got chosen, solklart",
                        Toast.LENGTH_SHORT).show();
                usedTiles.add(clickedTile);


                if (selectedShipID >= 1 && selectedShipID <= 3){
                    int shipsLeft = Integer.parseInt(txt_1r.getText().toString());
                    ship1ID++;
                    shipsLeft--;
                    txt_1r.setText(shipsLeft + "");
                    placeShipAtPosition(clickedTile, 1, true);
                } else if (selectedShipID >= 4 && selectedShipID <= 5){
                    int shipsLeft = Integer.parseInt(txt_2r.getText().toString());
                    ship2ID++;
                    shipsLeft--;
                    txt_2r.setText(shipsLeft + "");
                    placeShipAtPosition(clickedTile, 2, true);
                } else if (selectedShipID >= 6){
                    int shipsLeft = Integer.parseInt(txt_3r.getText().toString());
                    ship3ID++;
                    shipsLeft--;
                    txt_3r.setText(shipsLeft + "");
                    placeShipAtPosition(clickedTile, 3, true);
                }
                selectedShipID = 0;
            }

        }

    }

    private boolean isVacant(int position){
        boolean isTileVacant = true;
        for (int v : usedTiles){
            if (position == v){
                isTileVacant = false;
                break;
            }
        }
        return isTileVacant;
    }

    /**
     * Hjälpmetod för att visa vart skeppen finns
     * justs nu går det att lägga två skepp ovanpå varadra och
     * om det hamnar utanför den högra begrensningen kommer den att
     * fortsätta under.
     * @param startPosition
     * @param lenght
     * @param isHorizontal finns som tillägg för framtiden, används ej
     */
    private void placeShipAtPosition(int startPosition, int lenght, boolean isHorizontal){
        if (lenght == 1){
            boardState[startPosition] = selectedShipID;
        } else if (lenght == 2){
            if (startPosition % 7 == 6 && isVacant(startPosition - 1)){
                boardState[startPosition - 1] = selectedShipID;
                boardState[startPosition] = selectedShipID;
            } else if ( isVacant(startPosition + 1)){
                boardState[startPosition] = selectedShipID;
                boardState[startPosition + 1] = selectedShipID;
            }
        } else if (lenght == 3){
            if (startPosition % 7 == 6 && isVacant(startPosition - 1) && isVacant(startPosition - 2)){
                boardState[startPosition - 2] = selectedShipID;
                boardState[startPosition - 1] = selectedShipID;
                boardState[startPosition] = selectedShipID;
            }
            if (isVacant(startPosition - 1) && isVacant(startPosition + 1)){
                boardState[startPosition - 1] = selectedShipID;
                boardState[startPosition] = selectedShipID;
                boardState[startPosition + 1] = selectedShipID;
            }
        }

      /*  while(lenght > 0){
            isShipAtPosition[startPosition + lenght - 1] = true;
            lenght --;
        }*/
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}