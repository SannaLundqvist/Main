package com.example.a17salu03.battleships;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;

/**
 * Keeps the game data.
 *
 * @author Mattias Melchior, Sanna Lundqvist
 */
public class GameData {

    public static final String TAG = "EBTurn";

    public String[] myShips = null;
    public String[] opponentsShips = null;
    public int firePosition;
    public int turnCounter ;

    /**
     * basic constructor
     */
    public GameData() {

    }

    /**
     * converts the game data to a byte array
     * @return the byte array
     */
    public byte[] persist() {
        JSONObject retVal = new JSONObject();

        if (opponentsShips != null) {
            JSONArray opponents = new JSONArray();
            for (int i = 0; i < opponentsShips.length; i++) {
                opponents.put(opponentsShips[i]);
            }
            try {
                retVal.put("opponentsShips", opponents);
            } catch (JSONException e) {
                Log.e("SkeletonTurn", "There was an issue writing JSON!", e);
            }
        }
        if (myShips != null) {
            JSONArray mine = new JSONArray();
            for (int i = 0; i < myShips.length; i++) {
                mine.put(myShips[i]);
            }
            try {
                retVal.put("myShips", mine);
            } catch (JSONException e) {
                Log.e("SkeletonTurn", "There was an issue writing JSON!", e);
            }
        }
        if(firePosition >= 0){
            try {
                retVal.put("firePosition", firePosition);
            } catch (JSONException e) {
                Log.e("SkeletonTurn", "There was an issue writing JSON!", e);
            }
        }
        try {
            retVal.put("turnCounter", turnCounter);
        } catch (JSONException e) {
            Log.e("SkeletonTurn", "There was an issue writing JSON!", e);
        }

        String st = retVal.toString();

        Log.d(TAG, "==== PERSISTING\n" + st);

        return st.getBytes(Charset.forName("UTF-8"));
    }

    /**
     * Fills the game data from the byte array
     * @param byteArray the game data
     * @return a new instance of GameData with the current data
     */
    static public GameData unpersist(byte[] byteArray) {

        if (byteArray == null) {
            Log.d(TAG, "Empty array---possible bug.");
            return new GameData();
        }
        String st = null;
        try {
            st = new String(byteArray, "UTF-8");
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
            return null;
        }
        Log.d(TAG, "====UNPERSIST \n" + st);

        GameData retVal = new GameData();

        try {
            JSONObject obj = new JSONObject(st);

            if (obj.has("turnCounter")) {
                retVal.turnCounter = obj.getInt("turnCounter");
            }
            if (obj.has("myShips")) {
                JSONArray array = obj.getJSONArray("myShips");

                if (array == null)
                    Log.e("SkeletonTurn", "There was an issue getting JSONArray!");

                String[] shipPlacment = new String[array.length()];

                for (int i = 0; i < array.length(); ++i) {
                    shipPlacment[i] = array.optString(i);
                }
                retVal.opponentsShips = shipPlacment;
            }
            if (obj.has("opponentsShips")) {
                JSONArray array = obj.getJSONArray("opponentsShips");

                if (array == null)
                    Log.e("SkeletonTurn", "There was an issue getting JSONArray!");

                String[] shipPlacement = new String[array.length()];

                for (int i = 0; i < array.length(); ++i) {
                    shipPlacement[i] = array.optString(i);
                }
                retVal.myShips = shipPlacement;
            }
            if(obj.has("firePosition")){
                retVal.firePosition = obj.getInt("firePosition");
            }
        } catch (JSONException e) {
            Log.e("SkeletonTurn", "There was an issue parsing JSON!", e);
        }
        return retVal;
    }
}

