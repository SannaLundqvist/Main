package com.example.a17salu03.battleships;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.GridView;

import java.util.ArrayList;

import static com.example.a17salu03.battleships.Tile.TILE_TYPE_WATER;

public class GridFragment extends Fragment{

    private ArrayList<Tile> tiles = new ArrayList<>();
    private int tileID = 0;
    private int clickedTile = 50;
    private View thisView;
    private String[] ships;
    private String[] board;
    private String[] opponentsBoard;
    public Integer layoutBorder = R.drawable.layout_border;
    private GridView gridView;
    private boolean isFriendlyBoard = true;
    private String[] gridArray;
    
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

            gridArray = new String[49];
            if (opponentsBoard != null) {
                System.arraycopy(opponentsBoard, 0, gridArray, 0, opponentsBoard.length);
            } else if (board != null) {
                System.arraycopy(board, 0, gridArray, 0, board.length);
            } else {
                for (int i = 0; i < 49; i++) {
                    gridArray[i] = TILE_TYPE_WATER;
                }
            }
            Tile tile;
            for (int position = 0; position < 49; position++) {


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


    public void setMyBoard(String[] board) {
        this.board = board;
        isFriendlyBoard = true;
    }

    public void setOpponentsBoard(String[] board) {
        this.board = board;
        isFriendlyBoard = false;
    }

    public String[] getGridArray(){
        return gridArray;
    }

    private boolean between(int small, int large, int value) {
        return (value >= small) && (value <= large);
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

    public String getTile(int tileID) {
        return board[tileID];
    }

    public String[] getBoard() {
        return board;
    }

    public interface OnItemClickedListener {
        public void onItemClicked(int position);
    }

    public void setShipArray(String[] ships) {
        this.ships = ships;
    }}