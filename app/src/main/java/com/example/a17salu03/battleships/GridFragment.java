package com.example.a17salu03.battleships;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import java.util.ArrayList;

import static com.example.a17salu03.battleships.Tile.TILE_TYPE_WATER;

/**
 * This fragment contains the grid view and some methods to interact with it.
 *
 * @author Mattias Melchior, Sanna Lundqvist
 */
public class GridFragment extends Fragment {

    private ArrayList<Tile> tiles = new ArrayList<>();
    private final int INVALID = -1;
    private int clickedTile = INVALID;
    private String[] board;
    public static final int TOTAL_NBR_OF_TILES = 49;
    private boolean friendlyBoard = true;
    private boolean clickableTiles = false;

    /**
     * Inflates the layout.
     *
     * @param inflater the inflater
     * @param container the container
     * @param savedInstanceState the saved instance state
     * @return the inflated view
     */

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_grid, container, false);
    }

    /**
     * If no board has been sent in, then it's the PlaceShipActivity that wants a GridFragment.
     * That activity only wants a clean board with water, so that's what's gonna be created.
     * If a board has been inserted that data is sent to the CustomGridViewAdapter.
     */
    @Override
    public void onStart() {
        super.onStart();
        View view = getView();
        if (view != null) {
            GridView gridView = view.findViewById(R.id.grid);
            if (board == null) {
                board = new String[TOTAL_NBR_OF_TILES];
                for (int i = 0; i < TOTAL_NBR_OF_TILES; i++) {
                    board[i] = TILE_TYPE_WATER;
                }
            }

            Tile tile;
            for (int position = 0; position < TOTAL_NBR_OF_TILES; position++) {

                if (clickableTiles) {
                    tile = new ClickableTile(position, getView(), this);
                } else {
                    tile = new Tile(position, getView());
                }
                tiles.add(tile);
            }

            CustomGridViewAdapter gridAdapter = new CustomGridViewAdapter(board, friendlyBoard, tiles, view);
            gridView.setAdapter(gridAdapter);

        }
    }


    /**
     * Sets the players board and sets that the board is friendly.
     *
     * @param board the friendly board
     */
    public void setMyBoard(String[] board) {
        this.board = board;
        friendlyBoard = true;
    }

    /**
     * Sets the opponents board and sets that the board is the opponents.
     *
     * @param board
     */
    public void setOpponentsBoard(String[] board) {
        this.board = board;
        friendlyBoard = false;
    }

    /**
     * Gets the tile at position.
     *
     * @param position the position
     * @return the tile at the position
     */
    public Tile getTileAtPosition(int position) {
        return tiles.get(position);
    }

    /**
     * Gets the last clicked tile.
     *
     * @return the clicked tile
     */
    public int getClickedTile() {
        return clickedTile;
    }

    /**
     * Sets the clicked tile.
     *
     * @param tileID the clicked tile ID
     */
    public void setClickedTile(int tileID) {
        clickedTile = tileID;
    }

    /**
     * Sets the tiles to clickable depending on the answer.
     *
     * @param answer the answer
     */
    public void isClickableTiles(boolean answer) {
        clickableTiles = answer;
    }

}
