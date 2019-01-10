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
  public static int[] myShips = null;
  public static int[] yourShips = null;
  public int turnCounter;

  public SkeletonTurn() {
      myShips = new int[49];
      for (int i = 0 ; i < 49 ; i ++){
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

/*
    String st = new String();
    for (int i = 0 ; i < myShips.length ; i ++){
        st.toCharArray()
    }

    st += retVal.toString();
*/
    String st = retVal.toString();

    Log.d(TAG, "==== PERSISTING\n" + st);

      byte[] one = st.getBytes(Charset.forName("UTF-8");
      byte[] two = Arrays.bymyShips.;
      byte[] combined = new byte[one.length + two.length];

      System.arraycopy(one,0,combined,0         ,one.length);
      System.arraycopy(two,0,combined,one.length,two.length);

      byte[] retArray = st.getBytes(Charset.forName("UTF-8"));
      byte[] shipsByteArray = new byte[49];
      retArray.

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
    String ships = st.substring(0, 48*2 + 2);
    st = st.substring(49);

    try {
      JSONObject obj = new JSONObject(st);

      if (obj.has("data")) {
        retVal.data = obj.getString("data");
      }
      if (obj.has("turnCounter")) {
        retVal.turnCounter = obj.getInt("turnCounter");
      }

    } catch (JSONException e) {
      Log.e("SkeletonTurn", "There was an issue parsing JSON!", e);
    }
    yourShips = stringToIntArray(ships);

    return retVal;
  }
  private static int[] stringToIntArray(String numbers){

        char []list = numbers.toCharArray();
        int[] num = new int[49];

        for (int i = 0; i < numbers.length(); i++) {
            num[i] = Integer.parseInt(String.valueOf(list[i]));
        }
        return  num;
    }
}

