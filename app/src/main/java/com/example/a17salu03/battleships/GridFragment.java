package com.example.a17salu03.battleships;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.GridView;

import java.util.ArrayList;

public class GridFragment extends Fragment{

    private ArrayList<Tile> tiles = new ArrayList<>();
    private int tileID = 0;
    private int clickedTile;
    private View thisView;
    private int[] ships;
    private int[] board;
    private int[] opponentsBoard;
    public Integer layoutBorder = R.drawable.layout_border;
    private GridView gridView;
    private boolean isFriendlyBoard = true;


    private boolean isClickableTiles = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_grid, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        View view = getView();
        if (view != null) {
            thisView = view;

            gridView = view.findViewById(R.id.grid);

            int[] gridArray = new int[49];
            if (opponentsBoard != null) {
                System.arraycopy(opponentsBoard, 0, gridArray, 0, opponentsBoard.length);
            } else if (board != null) {
                System.arraycopy(board, 0, gridArray, 0, board.length);
            } else {
                for (int i = 0; i < 49; i++) {
                    gridArray[i] = 0;
                }
            }
            Tile tile;
            for (int position = 0; position < 49; position++) {

                    if(ships != null)
                        tile = new Tile(tileID, view, getTileAppenence(i));
                    else
                        tile = new Tile(tileID, view, Tile.TILE_TYPE_WATER);
                if (isClickableTiles) {
                    tile = new ClickableTile(position, getView(), this);
                } else {
                    tile = new Tile(position, getView());
                }
                tiles.add(tile);
            }


            CustomGridViewAdapter gridAdapter = new CustomGridViewAdapter(gridArray, isFriendlyBoard, isClickableTiles, tiles, view);
            gridView.setAdapter(gridAdapter);



        }
    }
    private int getTileAppenence(int pos){
        int ship = ships[pos];
        if((ship >= 0) && (ship < 9)){
            return Tile.TILE_TYPE_WATER;
        }else if((ship > 10) && (ship <= 13)){
            return  Tile.TILE_TYPE_SHIP_SMALL;
        }else if(between(14, 15, ship)) {
            if (!(pos % 7 == 0)) {
                if (between(14, 15, ships[pos - 1])) {
                    return Tile.TILE_TYPE_SHIP_MEDIUM_R;
                }
            }
            if (!(pos % 7 == 6))
                if (between(14, 15, ships[pos + 1])) {
                    return Tile.TILE_TYPE_SHIP_MEDIUM_L;
                }
            return Tile.TILE_TYPE_HIT;
        }
        else{
            if(!between(0, 1,(pos + 2) % 7)) {
                try {
                    if ((ships[pos + 2] > 15) && (ships[pos + 1] > 15)) {
                        return Tile.TILE_TYPE_SHIP_LARGE_L;
                    }
                }catch (IndexOutOfBoundsException e){
                    Log.e("GridFragment", e.toString());
                }
            }
            if(!(((pos + 1) % 7) == 6) || (((pos - 1) % 7) == 0)){
                try {
                    if ((ships[pos + 1] > 15) && (ships[pos - 1] > 15)) {
                        return Tile.TILE_TYPE_SHIP_LARGE_M;
                    }
                }catch (IndexOutOfBoundsException e){
                    Log.e("GridFragment", e.toString());
                }
            }
            if(!between(5, 6, (pos - 2) % 7 )){
                try {
                    if ((ships[pos - 1] > 15) && (ships[pos - 2] > 15))
                        return Tile.TILE_TYPE_SHIP_LARGE_R;
                }catch (IndexOutOfBoundsException e){
                    Log.e("GridFragment", e.toString());
                }
            }
            return Tile.TILE_TYPE_HIT;
        }


    public void setMyBoard(int[] board) {
        this.board = board;
        isFriendlyBoard = true;
    }

    public void setOpponentsBoard(int[] board) {
        this.board = board;
        isFriendlyBoard = false;
    }


    private boolean between(int small, int large, int value) {
        boolean returnB = ((value >= small) && (value <= large));
            return returnB;
    }

    public Tile getTileAtPosition(int position) {
        return tiles.get(position);
    }

    public int getClickedTile() {
        return clickedTile;
    }

    public void setClickedTile(int tileID) {
        clickedTile = tileID;
    }

    public void setClickableTiles(boolean answer) {
        isClickableTiles = answer;
    }

    public int getTile(int tileID) {
        return board[tileID];
    }

    public int[] getBoard() {
        return board;
    }

    public interface OnItemClickedListener {
        public void onItemClicked(int position);
    }

    public void setShipArray(int[] ships) {
        this.ships = ships;
    }}