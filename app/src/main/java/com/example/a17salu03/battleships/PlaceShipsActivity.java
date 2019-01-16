package com.example.a17salu03.battleships;

import android.content.DialogInterface;
import android.content.Intent;
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

/**
 *  This is the first activity that starts when you start a new game. It creates a board and places
 *  ships where selected.
 *
 * @author Mattias Melchior, Sanna Lundqvist
 */

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
    private boolean isMusicOn;
    private MediaPlayer backroundMusicPlayer;

    /**
     * Here most things get initialized.
     *
     * @param savedInstanceState the saved data
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_ships);
        isMusicOn = getIntent().getBooleanExtra("isBackroundMusicOn", true);

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
                    selectedShipID = ship1ID;
                    highlightChosenShip(img_1r);
                }
            }
        });
        img_2r.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int shipsLeft1 = Integer.parseInt(txt_2r.getText().toString());
                if(shipsLeft1 > 0){
                    selectedShipID = ship2ID;
                    highlightChosenShip(img_2r);
                }
            }
        });
        img_3r.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int shipsLeft2 = Integer.parseInt(txt_3r.getText().toString());
                if(shipsLeft2 > 0){
                    selectedShipID = ship3ID;
                    highlightChosenShip(img_3r);
                }
            }
        });
        doneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            if (Integer.parseInt(txt_1r.getText().toString()) == 0 && Integer.parseInt(txt_2r.getText().toString()) == 0 && Integer.parseInt(txt_3r.getText().toString()) == 0){
                Intent intent = new Intent();
                intent.putExtra("boardState", boardState);
                setResult(RESULT_OK, intent);
                backroundMusicPlayer.stop();
                finish();
            }

            }
        });

        leaveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                leaveDialog.show();
            }
        });

    }

    /**
     * The music stops when onPause is called.
     */
    @Override
    protected void onPause(){
        super.onPause();
        if(isMusicOn)
            backroundMusicPlayer.stop();
    }

    /**
     * Starts the music player unless turned off in the settings menu.
     */
    @Override
    protected void onResume() {
        super.onResume();
        if(isMusicOn) {
            backroundMusicPlayer = MediaPlayer.create(getBaseContext(), R.raw.battle_music);
            backroundMusicPlayer.start();
        }
    }

    /**
     * Forfeits the current game.
     */
    public void leaveGame(){
        setResult(StartActivity.RESULT_LEAVE);
        finish();
    }

    /**
     * Toggles between a horizontal and a vertical layout for the ship placements.
     *
     * @param view the rotate button
     */
    public void onRotateClicked(View view){
        isHorizontal = !isHorizontal;
        if (isHorizontal){
            rotateBtn.setText(R.string.horizontal);
        } else {
            rotateBtn.setText(R.string.vertical);
        }
    }

    /**
     * When the place button is clicked it checks if the tiles it wants to place a ship on is vacant.
     * If it is, then the placement begins.
     *
     * @param view the placement button
     */
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

    /**
     * This toggles the highlighting feature on the ships you can still choose to place down.
     *
     * @param clickedShip
     */
    private void highlightChosenShip(ImageView clickedShip){
        if (lastClickedShip != null)
            lastClickedShip.setBackgroundColor(0);
        if (clickedShip != null)
            clickedShip.setBackgroundResource(R.color.Color_Selected);
        lastClickedShip = clickedShip;
    }

    /**
     * Calls the recreate method when clicked.
     *
     * @param view the reset button
     */
    public void onResetClicked(View view){
        recreate();
    }

    /**
     * Checks all the occupied tiles and returns false if the position matches one of them.
     * Returns true otherwise.
     *
     * @param position the position on the board that wants to be checked
     * @return tile is vacant if true; tile is occupied otherwise
     */
    private boolean isVacant(int position){
        boolean tileVacancy = true;
        for (int tilePos : usedTiles){
            if (position == tilePos){
                tileVacancy = false;
                break;
            }
        }
        return tileVacancy;
    }

    /**
     * Checks if the ship can be placed near the selected position.
     *
     * @param selectedPosition the selected position
     * @param length the tile size of selected ship
     * @return ship placed if true; ship not place otherwise
     */
    private boolean checkShipsAtPosition(int selectedPosition, int length){
        if (length == 1) {
           placeShipAt(selectedPosition);
           return true;
        } else if (isHorizontal) {
            if (length == 2) {                                     // skepp 2
                if (selectedPosition % 7 == 6 && isVacant(selectedPosition - 1)) {
                    placeShipAt(selectedPosition - 1, selectedPosition);
                    return true;
                } else if (isVacant(selectedPosition + 1)) {
                    placeShipAt(selectedPosition, selectedPosition + 1);
                    return true;
                }
            } else if (length == 3) {                                            // skepp 3
                if (selectedPosition % 7 == 6 && isVacant(selectedPosition - 1) && isVacant(selectedPosition - 2)) {
                    placeShipAt(selectedPosition - 2, selectedPosition - 1, selectedPosition);
                    return true;
                } else if (selectedPosition % 7 == 0 && isVacant(selectedPosition + 1) && isVacant(selectedPosition + 2)) {
                    placeShipAt(selectedPosition, selectedPosition + 1, selectedPosition + 2);
                    return true;
                }else if (isVacant(selectedPosition - 1) && isVacant(selectedPosition + 1)) {
                    placeShipAt(selectedPosition - 1, selectedPosition, selectedPosition + 1);
                    return true;
                }
            }
        } else if (!isHorizontal){
            if (length == 2){
                if (selectedPosition > 41 && isVacant(selectedPosition - 7)){
                    placeShipAt(selectedPosition - 7, selectedPosition);
                    return true;
                } else if (isVacant(selectedPosition + 7)){
                    placeShipAt(selectedPosition, selectedPosition + 7);
                    return  true;
                }

            } else if (length == 3){
                if (selectedPosition < 7 && isVacant(selectedPosition + 7) && isVacant(selectedPosition + 14)){
                    placeShipAt(selectedPosition, selectedPosition + 7, selectedPosition + 14);
                    return true;
                } else if (selectedPosition > 41 && isVacant(selectedPosition - 7) && isVacant(selectedPosition - 14)){
                    placeShipAt(selectedPosition - 14, selectedPosition - 7, selectedPosition);
                    return true;
                } else if (isVacant(selectedPosition - 7) && isVacant( selectedPosition + 7)){
                    placeShipAt(selectedPosition - 7, selectedPosition, selectedPosition + 7);
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Places a small siezd ship of size 1 at the selected position.
     *
     * @param selectedPosition the selected position on the board
     */
    private void placeShipAt(int selectedPosition){
        if (selectedShipID == 1)
            boardState[selectedPosition] = TILE_TYPE_SIZE_1_SHIPID_1;
        else if (selectedShipID == 2)
            boardState[selectedPosition] = TILE_TYPE_SIZE_1_SHIPID_2;
        else if (selectedShipID == 3)
            boardState[selectedPosition] = TILE_TYPE_SIZE_1_SHIPID_3;

        Tile tile = playerGrid.getTileAtPosition(selectedPosition);
        tile.getTileImage().setImageBitmap(BitMapEdit.combineImages(R.drawable.skepp_1r_w, this.getWindow().getDecorView().findViewById(android.R.id.content), 0, false));
        tile.setClickDisabled(true);
        usedTiles.add(selectedPosition);
    }

    /**
     * Places a medium sized ship of size 2 at the start and end position, with the right ship
     * appearance.
     *
     * @param startPosition the left or top position
     * @param endPosition the right or bottom position
     */
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

    /**
     * Places a large sized ship of size 3 between the start and end position, with the right ship
     * appearance.
     *
     * @param startPosition the left or top position
     * @param middlePosition the middle position
     * @param endPosition the right or bottom position
     */
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
}