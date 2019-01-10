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

public class PlaceShipsActivity extends AppCompatActivity implements GridFragment.OnItemClickedListener {

    private GridFragment playerGrid;
    private ArrayList<Integer> usedTiles = new ArrayList<>();
    private int[] boardState = new int[49];
    private int shipToBePlaced;
    private int selectedShipID;

    private int ship2ID = 1;
    private int ship3ID = 4;
    private int ship4ID = 6;
    private int selectedPosition;
    private boolean[] isShipAtPosition;
    private Button placeBtn = null;
    private Button doneBtn = null;
    private ImageView img_2r = null;
    private ImageView img_3r = null;
    private ImageView img_4r = null;
    private TextView txt_2r = null;
    private TextView txt_3r = null;
    private TextView txt_4r = null;
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
        img_2r = findViewById(R.id.ship_2r_draw);
        img_3r = findViewById(R.id.ship_3r_draw);
        img_4r = findViewById(R.id.ship_4r_draw);
        txt_2r = findViewById(R.id.ship_2r_text);
        txt_3r = findViewById(R.id.ship_3r_text);
        txt_4r = findViewById(R.id.ship_4r_text);

        isShipAtPosition = new boolean[49];

        img_2r.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int shipsLeft0 = Integer.parseInt(txt_2r.getText().toString());
                if(shipsLeft0 > 0){
                    generateShipID(2);

                }else{
                    //Toast.makeText(this, R.string.placed_all_ships, Toast.LENGTH_SHORT);
                }
            }
        });
        img_3r.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int shipsLeft1 = Integer.parseInt(txt_3r.getText().toString());
                if(shipsLeft1 > 0){
                    generateShipID(3);
                }else{
                    //Toast.makeText(this, R.string.placed_all_ships, Toast.LENGTH_SHORT);
                }
            }
        });
        img_4r.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int shipsLeft2 = Integer.parseInt(txt_4r.getText().toString());
                if(shipsLeft2 > 0){
                    generateShipID(4);
                }else{
                    //Toast.makeText(this, R.string.placed_all_ships, Toast.LENGTH_SHORT);
                }
            }
        });
        doneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txt_4r.setText("done!");
                int[] intarray = {7,0,1};

                Intent intent = new Intent();
                intent.putExtra("shipArray", intarray);
                setResult(RESULT_OK, intent);
                finish();

                //skicka tillbaka informationen till StartActivity
                //kanske behövs låsas innan man valt position minst en gång, eller testas
                //isShipAtPosition skickas vidare

            }
        });




    }
    private void generateShipID(int shipSize){
        switch (shipSize){
            case 2:
                selectedShipID = ship2ID;
                break;
            case 3:
                selectedShipID = ship3ID;
                break;
            case 4:
                selectedShipID = ship4ID;
                break;
        }
    }

    public void onPlaceClicked(View view){
       Integer clickedTile = Integer.valueOf(playerGrid.getClickedTile());

        if (clickedTile != null && selectedShipID != 0){
            boolean isTileVacant = true;
            for (int v : usedTiles){
                if (clickedTile == v){
                    isTileVacant = false;
                    break;
                }
            }
            if (isTileVacant){
                boardState[clickedTile] = selectedShipID;

                Toast.makeText(getBaseContext(),
                        boardState[clickedTile] + " got chosen, solklart",
                        Toast.LENGTH_SHORT).show();
                usedTiles.add(clickedTile);


                if (selectedShipID >= 1 && selectedShipID <= 3){
                    int shipsLeft = Integer.parseInt(txt_2r.getText().toString());
                    shipsLeft--;
                    txt_2r.setText(shipsLeft + "");
                } else if (selectedShipID >= 4 && selectedShipID <= 5){
                    int shipsLeft = Integer.parseInt(txt_3r.getText().toString());
                    shipsLeft--;
                    txt_3r.setText(shipsLeft + "");
                } else if (selectedShipID >= 6){
                    int shipsLeft = Integer.parseInt(txt_4r.getText().toString());
                    shipsLeft--;
                    txt_4r.setText(shipsLeft + "");
                }
                selectedShipID = 0;
            }

        }



    }

    @Override
    public void onItemClicked(int position) {
        switch (shipNbr){
            case 0 :
                placeShipAtPosition(position, 2, true);
                return;
            case 1 :
                placeShipAtPosition(position, 3, true);
                return;
            case 2 :
                placeShipAtPosition(position, 4, true);
                break;
        }
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
        while(lenght > 0){
            isShipAtPosition[startPosition + lenght - 1] = true;
            lenght --;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}