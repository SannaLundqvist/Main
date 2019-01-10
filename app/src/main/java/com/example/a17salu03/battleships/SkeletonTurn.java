/*
 * Copyright (C) 2013 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.a17salu03.battleships;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Basic turn data. It's just a blank data string and a turn number counter.
 *
 * @author wolff
 */
public class SkeletonTurn {

  public static final String TAG = "EBTurn";

  public String data = "";
  public int[] myShips = null;
  public int[] opponentsShips = null;
  public int turnCounter;

  public SkeletonTurn() {
      myShips = new int[49];
      for (int i = 0 ; i < myShips.length ; i ++){
          if(i % 2 == 0)
              myShips[i] = 0;
          else
              myShips[i] = 1;
      }
  }

  // This is the byte array we will write out to the TBMP API.
  public byte[] persist() {
    JSONObject retVal = new JSONObject();

    try {
          retVal.put("data", data);
          retVal.put("turnCounter", turnCounter);

      } catch (JSONException e) {
          Log.e("SkeletonTurn", "There was an issue writing JSON!", e);
      }
      if(turnCounter < 3){
          JSONArray ships = new JSONArray();
          for (int i = 0 ; i < myShips.length ; i ++){
              ships.put(myShips[i]);
          }
          try {
              retVal.put("ships", ships);
          }catch (JSONException e){
              Log.e("SkeletonTurn", "There was an issue writing JSON!", e);
          }
      }
    String st = retVal.toString();

    Log.d(TAG, "==== PERSISTING\n" + st);

    return st.getBytes(Charset.forName("UTF-8"));
  }

  // Creates a new instance of SkeletonTurn.
  static public SkeletonTurn unpersist(byte[] byteArray) {

    if (byteArray == null) {
      Log.d(TAG, "Empty array---possible bug.");
      return new SkeletonTurn();
    }

    String st = null;
    try {
      st = new String(byteArray, "UTF-8");
    } catch (UnsupportedEncodingException e1) {
      e1.printStackTrace();
      return null;
    }

    Log.d(TAG, "====UNPERSIST \n" + st);

    SkeletonTurn retVal = new SkeletonTurn();

    try {
      JSONObject obj = new JSONObject(st);

      if (obj.has("data")) {
        retVal.data = obj.getString("data");
      }
      if (obj.has("turnCounter")) {
        retVal.turnCounter = obj.getInt("turnCounter");
      }
      if(obj.has("ships")){
          JSONArray array = obj.getJSONArray("ships");

          if (array == null)
              Log.e("SkeletonTurn", "There was an issue getting JSONArray!");

          int[] numbers = new int[array.length()];

          for (int i = 0; i < array.length(); ++i) {
              numbers[i] = array.optInt(i);
          }
          retVal.opponentsShips = numbers;
      }

    } catch (JSONException e) {
      Log.e("SkeletonTurn", "There was an issue parsing JSON!", e);
    }

    return retVal;
  }
}

