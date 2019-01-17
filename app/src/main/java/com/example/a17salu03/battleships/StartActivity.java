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

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.games.Games;
import com.google.android.gms.games.GamesCallbackStatusCodes;
import com.google.android.gms.games.GamesClient;
import com.google.android.gms.games.GamesClientStatusCodes;
import com.google.android.gms.games.InvitationsClient;
import com.google.android.gms.games.Player;
import com.google.android.gms.games.TurnBasedMultiplayerClient;
import com.google.android.gms.games.multiplayer.Invitation;
import com.google.android.gms.games.multiplayer.InvitationCallback;
import com.google.android.gms.games.multiplayer.Multiplayer;
import com.google.android.gms.games.multiplayer.realtime.RoomConfig;
import com.google.android.gms.games.multiplayer.turnbased.TurnBasedMatch;
import com.google.android.gms.games.multiplayer.turnbased.TurnBasedMatchConfig;
import com.google.android.gms.games.multiplayer.turnbased.TurnBasedMatchUpdateCallback;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import java.util.ArrayList;


/**
 * Keeps track of the connection to Google and starts the game.
 *
 * @author Mattias Melchior, Sanna Lundqvist
 */
public class StartActivity extends Activity implements
        View.OnClickListener, MediaPlayer.OnSeekCompleteListener {

    public static final String TAG = "StartActivity";
    private GoogleSignInClient mGoogleSignInClient = null;
    private TurnBasedMultiplayerClient mTurnBasedMultiplayerClient = null;
    private InvitationsClient mInvitationsClient = null;

    private AlertDialog mAlertDialog;
    private boolean isBackgroundMusicOn;
    private boolean isEffectMusicOn;
    Switch backgroundMusicSwitch = null;
    Switch effectMusicSwitch = null;
    Switch winSwitch = null;
    private int musicDuration;

    private static final int RC_SIGN_IN = 9001;
    final static int RC_SELECT_PLAYERS = 10000;
    final static int RC_LOOK_AT_MATCHES = 10001;
    public static final int PLACED_SHIPS = 2001;
    public static final int SHOOTING = 2002;
    public static final int RESULT_LEAVE = 999;

    private SharedPreferences prefs;
    public boolean isDoingTurn = false;

    public TurnBasedMatch mMatch;
    public GameData mTurnData;

    private MediaPlayer backgroundMusicPlayer;
    private MediaPlayer effectMusicPlayer;


    private String mDisplayName;
    private String mPlayerId;

    /**
     * Fetches the saved data  and set up sign-in
     * @param savedInstanceState the saved game data
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        isBackgroundMusicOn = prefs.getBoolean("backgroundMusic", true);
        isEffectMusicOn = prefs.getBoolean("effectMusic", true);
        musicDuration = prefs.getInt("musicDuration", 0);

        GoogleSignInOptions gso = new GoogleSignInOptions
                .Builder(GoogleSignInOptions.DEFAULT_GAMES_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        findViewById(R.id.sign_out_button).setOnClickListener(this);
        findViewById(R.id.sign_in_button).setOnClickListener(this);

    }

    /**
     * Keeps restarts the music and signs in
     */
    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume()");

        if(isBackgroundMusicOn) {
            backgroundMusicPlayer = MediaPlayer.create(getBaseContext(), R.raw.relaxing);
            backgroundMusicPlayer.setOnSeekCompleteListener(this);
            backgroundMusicPlayer.seekTo(musicDuration);
        }
        // Since the state of the signed in user can change when the activity is not active
        // it is recommended to try and sign in silently from when the app resumes.
        signInSilently();
    }

    /**
     * Saves the important data, turns off the music and unregister Google clients
     */
    @Override
    protected void onPause() {
        super.onPause();
        if(isBackgroundMusicOn){
            backgroundMusicPlayer.stop();
            musicDuration = backgroundMusicPlayer.getCurrentPosition();
            prefs.edit().putInt("musicDuration", musicDuration).apply();
        }


        prefs.edit().putBoolean("backgroundMusic", isBackgroundMusicOn).apply();
        prefs.edit().putBoolean("effectMusic", isEffectMusicOn).apply();

        if (mInvitationsClient != null) {
            mInvitationsClient.unregisterInvitationCallback(mInvitationCallback);
        }
        if (mTurnBasedMultiplayerClient != null) {
            mTurnBasedMultiplayerClient.unregisterTurnBasedMatchUpdateCallback(mMatchUpdateCallback);
        }
    }

    /**
     * Starts when you have connected to Google. Sets the Google driven
     * components and updates the View
     * @param googleSignInAccount the signed in account
     */
    private void onConnected(GoogleSignInAccount googleSignInAccount) {
        Log.d(TAG, "onConnected(): connected to Google APIs");

        mTurnBasedMultiplayerClient = Games.getTurnBasedMultiplayerClient(this, googleSignInAccount);
        mInvitationsClient = Games.getInvitationsClient(this, googleSignInAccount);

        Games.getPlayersClient(this, googleSignInAccount)
                .getCurrentPlayer()
                .addOnSuccessListener(
                        new OnSuccessListener<Player>() {
                            @Override
                            public void onSuccess(Player player) {
                                mDisplayName = player.getDisplayName();
                                mPlayerId = player.getPlayerId();

                                setViewVisibility();
                            }
                        }
                )
                .addOnFailureListener(createFailureListener("There was a problem getting the player!"));

        Log.d(TAG, "onConnected(): Connection successful");

        GamesClient gamesClient = Games.getGamesClient(this, googleSignInAccount);
        gamesClient.getActivationHint()
                .addOnSuccessListener(new OnSuccessListener<Bundle>() {
                    @Override
                    public void onSuccess(Bundle hint) {
                        if (hint != null) {
                            TurnBasedMatch match = hint.getParcelable(Multiplayer.EXTRA_TURN_BASED_MATCH);

                            if (match != null) {
                                updateMatch(match);
                            }
                        }
                    }
                })
                .addOnFailureListener(createFailureListener(
                        "There was a problem getting the activation hint!"));

        setViewVisibility();

        mInvitationsClient.registerInvitationCallback(mInvitationCallback);
        mTurnBasedMultiplayerClient.registerTurnBasedMatchUpdateCallback(mMatchUpdateCallback);
    }

    /**
     * Sets the new visibility and drops the components dependent
     * on logged in
     */
    private void onDisconnected() {

        Log.d(TAG, "onDisconnected()");

        mTurnBasedMultiplayerClient = null;
        mInvitationsClient = null;

        setViewVisibility();
    }

    /**
     * Reports failure to the user by an AlertDialog
     * @param string the text for the dialog
     * @return a FailureListener prepared with the text
     */
    private OnFailureListener createFailureListener(final String string) {
        return new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                handleException(e, string);
            }
        };
    }

    /**
     * Starts an ActivityForResult for looking at matches
     * @param view the current view
     */
    public void onCheckGamesClicked(View view) {
        mTurnBasedMultiplayerClient.getInboxIntent()
                .addOnSuccessListener(new OnSuccessListener<Intent>() {
                    @Override
                    public void onSuccess(Intent intent) {
                        startActivityForResult(intent, RC_LOOK_AT_MATCHES);

                    }
                })
                .addOnFailureListener(createFailureListener(getString(R.string.error_get_inbox_intent)));
    }

    /**
     * Starts an ActivityForResult for choosing the players to the game
     * @param view the current view
     */
    public void onStartMatchClicked(View view) {
        mTurnBasedMultiplayerClient.getSelectOpponentsIntent(1, 1, true)
                .addOnSuccessListener(new OnSuccessListener<Intent>() {
                    @Override
                    public void onSuccess(Intent intent) {
                        startActivityForResult(intent, RC_SELECT_PLAYERS);
                    }
                })
                .addOnFailureListener(createFailureListener(
                        getString(R.string.error_get_select_opponents)));
    }

    /**
     * Starts an ActivityForResult for a game against random player
     * @param view the current view
     */
    public void onQuickMatchClicked(View view) {

        Bundle autoMatchCriteria = RoomConfig.createAutoMatchCriteria(1, 1, 0);

        TurnBasedMatchConfig turnBasedMatchConfig = TurnBasedMatchConfig.builder()
                .setAutoMatchCriteria(autoMatchCriteria).build();

        showSpinner();

        mTurnBasedMultiplayerClient.createMatch(turnBasedMatchConfig)
                .addOnSuccessListener(new OnSuccessListener<TurnBasedMatch>() {
                    @Override
                    public void onSuccess(TurnBasedMatch turnBasedMatch) {
                        onInitiateMatch(turnBasedMatch);
                    }
                })
                .addOnFailureListener(createFailureListener("There was a problem creating a match!"));
    }

    /**
     * Let you leave the game during your turn
     */
    private void leaveGame(){

        showSpinner();
        String nextParticipantId = getNextParticipantId();

        mTurnBasedMultiplayerClient.leaveMatchDuringTurn(mMatch.getMatchId(), nextParticipantId)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        onLeaveMatch();
                    }
                })
                .addOnFailureListener(createFailureListener("There was a problem leaving the match!"));
                
        Toast.makeText(this, "you left the game", Toast.LENGTH_SHORT).show();

        setViewVisibility();

    }

    /**
     * Finishes the game - with current player as a winner.
     */
    public void gameWon() {
        showSpinner();

        mTurnBasedMultiplayerClient.finishMatch(mMatch.getMatchId())
                .addOnSuccessListener(new OnSuccessListener<TurnBasedMatch>() {
                    @Override
                    public void onSuccess(TurnBasedMatch turnBasedMatch) {
                        onUpdateMatch(turnBasedMatch);
                    }
                })
                .addOnFailureListener(createFailureListener("There was a problem finishing the match!"));

        isDoingTurn = false;
        setViewVisibility();
    }

    /**
     * Sends your updated game data to the opponent.
     */
    public void takeTurn() {
        showSpinner();

        String nextParticipantId = getNextParticipantId();
        mTurnData.turnCounter += 1;

        mTurnBasedMultiplayerClient.takeTurn(mMatch.getMatchId(),
                mTurnData.persist(), nextParticipantId)
                .addOnSuccessListener(new OnSuccessListener<TurnBasedMatch>() {
                    @Override
                    public void onSuccess(TurnBasedMatch turnBasedMatch) {
                        onUpdateMatch(turnBasedMatch);
                    }
                })
                .addOnFailureListener(createFailureListener("There was a problem taking a turn!"));

        mTurnData = null;
    }

    /**
     * Sends your placed ships to the opponent
     * @param ships the ships placed at the board
     */
    public void takeTurnPlaceShips(String[] ships) {
        showSpinner();

        String nextParticipantId = getNextParticipantId();

        mTurnData.turnCounter += 1;
        Toast.makeText(this, "turnCOunt" + mTurnData.turnCounter, Toast.LENGTH_LONG).show();
        mTurnData.myShips = ships;
        mTurnBasedMultiplayerClient.takeTurn(mMatch.getMatchId(),
                mTurnData.persist(), nextParticipantId)
                .addOnSuccessListener(new OnSuccessListener<TurnBasedMatch>() {
                    @Override
                    public void onSuccess(TurnBasedMatch turnBasedMatch) {
                        onUpdateMatch(turnBasedMatch);
                    }
                })
                .addOnFailureListener(createFailureListener("There was a problem taking a turn!"));

        mTurnData = null;

    }

    /**
     * If you are signed in the layout for start game etc. shows up
     * If not signed in the layout for signing in shows up
     */
    public void setViewVisibility() {
        boolean isSignedIn = mTurnBasedMultiplayerClient != null;

        if (!isSignedIn) {
            findViewById(R.id.login_layout).setVisibility(View.VISIBLE);
            findViewById(R.id.sign_in_button).setVisibility(View.VISIBLE);
            findViewById(R.id.matchup_layout).setVisibility(View.GONE);

            if (mAlertDialog != null) {
                mAlertDialog.dismiss();
            }
            return;
        }
            ((TextView) findViewById(R.id.name_field)).setText(mDisplayName);
            findViewById(R.id.login_layout).setVisibility(View.GONE);

            findViewById(R.id.matchup_layout).setVisibility(View.VISIBLE);


    }

    /**
     * Sends the player to the right Activity dependent on
     * what turnCount is at
     */
    public void setGameplayUI() {
        isDoingTurn = true;

        if(mTurnData.turnCounter < 2){
            Intent intent = new Intent(StartActivity.this, PlaceShipsActivity.class);
            intent.putExtra("isBackgroundMusicOn", isBackgroundMusicOn);
            startActivityForResult(intent, PLACED_SHIPS);

        }else{
            Intent intent = new Intent(StartActivity.this, BoardActivity.class);
            intent.putExtra("opponentsShips", mTurnData.opponentsShips);
            intent.putExtra("myShips", mTurnData.myShips);
            intent.putExtra("isBackgroundMusicOn", isBackgroundMusicOn);
            intent.putExtra("isEffectMusicOn", isEffectMusicOn);
            startActivityForResult(intent, SHOOTING);
        }
        setViewVisibility();
        dismissSpinner();
    }

    /**
     * Shows a loading circle on the screen
     */
    public void showSpinner() {
        findViewById(R.id.progressLayout).setVisibility(View.VISIBLE);
    }

    /**
     * Takes away the loading circle
     */
    public void dismissSpinner() {
        findViewById(R.id.progressLayout).setVisibility(View.GONE);
    }

    /**
     * Shows the warning
     * @param title the title for the warning
     * @param message the message for the warning
     */
    public void showWarning(String title, String message) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle(title).setMessage(message);

        alertDialogBuilder.setCancelable(false).setPositiveButton("OK",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                    }
                });

        mAlertDialog = alertDialogBuilder.create();
        mAlertDialog.show();
    }

    /**
     * Asks the player for a rematch.
     * If yes - rematch
     * If no - nothing
     */
    public void askForRematch() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

        alertDialogBuilder.setMessage("Do you want a rematch?");

        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("Sure, rematch!",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                rematch();
                            }
                        })
                .setNegativeButton("No.",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                            }
                        });

        alertDialogBuilder.show();
    }

    /**
     * Start a sign in activity.
     */
    public void startSignInIntent() {

        startActivityForResult(mGoogleSignInClient.getSignInIntent(), RC_SIGN_IN);
    }

    /**
     * Try to sign in without displaying dialogs to the user.
     */
    public void signInSilently() {
        Log.d(TAG, "signInSilently()");

        mGoogleSignInClient.silentSignIn().addOnCompleteListener(this,
                new OnCompleteListener<GoogleSignInAccount>() {
                    @Override
                    public void onComplete(@NonNull Task<GoogleSignInAccount> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "signInSilently(): success");
                            onConnected(task.getResult());
                        } else {
                            Log.d(TAG, "signInSilently(): failure", task.getException());
                            onDisconnected();
                        }
                    }
                });
    }

    /**
     * Signs the user out
     */
    public void signOut() {
        Log.d(TAG, "signOut()");

        mGoogleSignInClient.signOut().addOnCompleteListener(this,
                new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        if (task.isSuccessful()) {
                            Log.d(TAG, "signOut(): success");
                        } else {
                            handleException(task.getException(), "signOut() failed!");
                        }

                        onDisconnected();
                    }
                });
    }

    /**
     * Handles exceptions
     *
     * @param exception The exception to evaluate.  Will try to display a more descriptive reason for
     *                  the exception.
     * @param details   Will display alongside the exception if you wish to provide more details for
     *                  why the exception happened
     */
    private void handleException(Exception exception, String details) {
        int status = 0;

        if (exception instanceof TurnBasedMultiplayerClient.MatchOutOfDateApiException) {
            TurnBasedMultiplayerClient.MatchOutOfDateApiException matchOutOfDateApiException =
                    (TurnBasedMultiplayerClient.MatchOutOfDateApiException) exception;

            new AlertDialog.Builder(this)
                    .setMessage("Match was out of date, updating with latest match data...")
                    .setNeutralButton(android.R.string.ok, null)
                    .show();

            TurnBasedMatch match = matchOutOfDateApiException.getMatch();
            updateMatch(match);

            return;
        }

        if (exception instanceof ApiException) {
            ApiException apiException = (ApiException) exception;
            status = apiException.getStatusCode();
        }

        if (!checkStatusCode(status)) {
            return;
        }

        String message = getString(R.string.status_exception_error, details, status, exception);

        new AlertDialog.Builder(this)
                .setMessage(message)
                .setNeutralButton(android.R.string.ok, null)
                .show();
    }

    private void logBadActivityResult(int requestCode, int resultCode, String message) {
        Log.i(TAG, "Bad activity result(" + resultCode + ") for request (" + requestCode + "): "
                + message);
    }


    /**
     * The function is called if a startActivityForResult is successful
     * @param requestCode the action that has been done
     * @param resultCode what was the result of the request
     * @param intent might contain data from the action
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {

        if (requestCode == RC_SIGN_IN) {

            Task<GoogleSignInAccount> task =
                    GoogleSignIn.getSignedInAccountFromIntent(intent);

            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                onConnected(account);
            } catch (ApiException apiException) {
                String message = apiException.getMessage();
                if (message == null || message.isEmpty()) {
                    message = getString(R.string.signin_other_error);
                }

                onDisconnected();

                new AlertDialog.Builder(this)
                        .setMessage(message)
                        .setNeutralButton(android.R.string.ok, null)
                        .show();
            }
        } else if (requestCode == RC_LOOK_AT_MATCHES) {

            if (resultCode != Activity.RESULT_OK) {
                logBadActivityResult(requestCode, resultCode,
                        "User cancelled returning from the 'Select Match' dialog.");
                return;
            }

            TurnBasedMatch match = intent
                    .getParcelableExtra(Multiplayer.EXTRA_TURN_BASED_MATCH);

            if (match != null) {
                updateMatch(match);
            }

            Log.d(TAG, "Match = " + match);
        } else if (requestCode == RC_SELECT_PLAYERS) {
            if (resultCode != Activity.RESULT_OK) {
                logBadActivityResult(requestCode, resultCode,
                        "User cancelled returning from 'Select players to Invite' dialog");
                return;
            }

            ArrayList<String> invitees = intent
                    .getStringArrayListExtra(Games.EXTRA_PLAYER_IDS);

            Bundle autoMatchCriteria;

            int minAutoMatchPlayers = intent.getIntExtra(Multiplayer.EXTRA_MIN_AUTOMATCH_PLAYERS, 0);
            int maxAutoMatchPlayers = intent.getIntExtra(Multiplayer.EXTRA_MAX_AUTOMATCH_PLAYERS, 0);

            if (minAutoMatchPlayers > 0) {
                autoMatchCriteria = RoomConfig.createAutoMatchCriteria(minAutoMatchPlayers,
                        maxAutoMatchPlayers, 0);
            } else {
                autoMatchCriteria = null;
            }

            TurnBasedMatchConfig tbmc = TurnBasedMatchConfig.builder()
                    .addInvitedPlayers(invitees)
                    .setAutoMatchCriteria(autoMatchCriteria).build();

            mTurnBasedMultiplayerClient.createMatch(tbmc)
                    .addOnSuccessListener(new OnSuccessListener<TurnBasedMatch>() {
                        @Override
                        public void onSuccess(TurnBasedMatch turnBasedMatch) {
                            onInitiateMatch(turnBasedMatch);
                        }
                    })
                    .addOnFailureListener(createFailureListener("There was a problem creating a match!"));
            showSpinner();

        }else if (requestCode == PLACED_SHIPS) {
            super.onActivityResult(requestCode, requestCode, intent);
            if(resultCode == RESULT_OK){
                takeTurnPlaceShips (intent.getStringArrayExtra("boardState"));
            }
            else if(resultCode == RESULT_LEAVE){
                leaveGame();
            }else
                Toast.makeText(getBaseContext(), "There was a problem placing your ships", Toast.LENGTH_SHORT).show();
        }else if (requestCode == SHOOTING) {

            super.onActivityResult(requestCode, resultCode, intent);
            if (resultCode == RESULT_OK) {

                if(intent.getBooleanExtra("hasWon", true)){
                    gameWon();
                }
                else{
                    mTurnData.myShips = intent.getStringArrayExtra("myShips");
                    mTurnData.opponentsShips = intent.getStringArrayExtra("opponentsShips");
                    takeTurn();
                }
            }  else if(resultCode == RESULT_LEAVE){
                leaveGame();
            }else{
                Toast.makeText(getBaseContext(), "There was a problem shooting", Toast.LENGTH_SHORT).show();
            }
        }
    }

    /**
     * Initializes the match
     * @param match a valid match object
     */
    public void startMatch(TurnBasedMatch match) {
        mTurnData = new GameData();

        mMatch = match;

        String myParticipantId = mMatch.getParticipantId(mPlayerId);

        showSpinner();

        mTurnBasedMultiplayerClient.takeTurn(match.getMatchId(),
                mTurnData.persist(), myParticipantId)
                .addOnSuccessListener(new OnSuccessListener<TurnBasedMatch>() {
                    @Override
                    public void onSuccess(TurnBasedMatch turnBasedMatch) {
                        updateMatch(turnBasedMatch);
                    }
                })
                .addOnFailureListener(createFailureListener("There was a problem taking a turn!"));
    }

    public void rematch() {
        showSpinner();
        mTurnBasedMultiplayerClient.rematch(mMatch.getMatchId())
                .addOnSuccessListener(new OnSuccessListener<TurnBasedMatch>() {
                    @Override
                    public void onSuccess(TurnBasedMatch turnBasedMatch) {
                        onInitiateMatch(turnBasedMatch);
                    }
                })
                .addOnFailureListener(createFailureListener("There was a problem starting a rematch!"));
        mMatch = null;
        isDoingTurn = false;
    }

    /**
     * Get the next participant. Works for multiple players
     *
     * @return participantId of next player, or null if automatching
     */

    public String getNextParticipantId() {

        String myParticipantId = mMatch.getParticipantId(mPlayerId);

        ArrayList<String> participantIds = mMatch.getParticipantIds();

        int desiredIndex = -1;

        for (int i = 0; i < participantIds.size(); i++) {
            if (participantIds.get(i).equals(myParticipantId)) {
                desiredIndex = i + 1;
            }
        }

        if (desiredIndex < participantIds.size()) {
            return participantIds.get(desiredIndex);
        }

        if (mMatch.getAvailableAutoMatchSlots() <= 0) {
            return participantIds.get(0);
        } else {
            return null;
        }
    }

    /**
     * Updates the game dependent on the state
     * @param match the match for update
     */
    public void updateMatch(TurnBasedMatch match) {
        mMatch = match;

        int status = match.getStatus();
        int turnStatus = match.getTurnStatus();

        switch (status) {
            case TurnBasedMatch.MATCH_STATUS_CANCELED:
                showWarning("Canceled!", "This game was canceled!");
                setViewVisibility();
                return;
            case TurnBasedMatch.MATCH_STATUS_EXPIRED:
                showWarning("Expired!", "This game is expired.  So sad!");
                setViewVisibility();
                return;
            case TurnBasedMatch.MATCH_STATUS_AUTO_MATCHING:
                showWarning("Waiting for auto-match...",
                        "We're still waiting for an automatch partner.");
                setViewVisibility();
                return;
            case TurnBasedMatch.MATCH_STATUS_COMPLETE:
                showWarning("Complete!",
                        "This game is over; someone finished it, and so did you!  " +
                                "There is nothing to be done.");
                setViewVisibility();
                return;
        }

        switch (turnStatus) {
            case TurnBasedMatch.MATCH_TURN_STATUS_MY_TURN:
                mTurnData = GameData.unpersist(mMatch.getData());
                setGameplayUI();
                return;
            case TurnBasedMatch.MATCH_TURN_STATUS_THEIR_TURN:
                showWarning("Alas...", "It's not your turn.");
                break;
            case TurnBasedMatch.MATCH_TURN_STATUS_INVITED:
                showWarning("Good inititative!",
                        "Still waiting for invitations.\n\nBe patient!");
        }

        mTurnData = null;
        setViewVisibility();
    }

    /**
     * If the game is active it updates the match and starts it
     * @param match the match
     */
    private void onInitiateMatch(TurnBasedMatch match) {
        dismissSpinner();

        if (match.getData() != null) {
            updateMatch(match);
            return;
        }
        startMatch(match);
    }

    /**
     * shows dialog confirming Leave
     */
    private void onLeaveMatch() {
        dismissSpinner();

        isDoingTurn = false;
        showWarning("Left", "You've left this match.");
    }

    /**
     * Updates the match
     * @param match the match for update
     */
    public void onUpdateMatch(TurnBasedMatch match) {
        dismissSpinner();

        if (match.canRematch()) {
            askForRematch();
        }
        isDoingTurn = (match.getTurnStatus() == TurnBasedMatch.MATCH_TURN_STATUS_MY_TURN);

        if (isDoingTurn) {
            updateMatch(match);
            return;
        }
        setViewVisibility();
    }


    private InvitationCallback mInvitationCallback = new InvitationCallback() {

        /**
         * Gives notice to the user that an invation was recived
         * @param invitation the invatation
         */
        @Override
        public void onInvitationReceived(@NonNull Invitation invitation) {
            Toast.makeText(
                    StartActivity.this,
                    "An invitation has arrived from "
                            + invitation.getInviter().getDisplayName(), Toast.LENGTH_SHORT)
                    .show();
        }

        /**
         * Confirmes to the user that an invitaion was removed
         * @param invitationId the id of the invatation
         */
        @Override
        public void onInvitationRemoved(@NonNull String invitationId) {
            Toast.makeText(StartActivity.this, "An invitation was removed.", Toast.LENGTH_SHORT)
                    .show();
        }
    };

    private TurnBasedMatchUpdateCallback mMatchUpdateCallback = new TurnBasedMatchUpdateCallback() {
        /**
         * Gives the user notice on a match was updated
         * @param turnBasedMatch the match
         */
        @Override
        public void onTurnBasedMatchReceived(@NonNull TurnBasedMatch turnBasedMatch) {
            Toast.makeText(StartActivity.this, "A match was updated.", Toast.LENGTH_LONG).show();
        }

        /**
         * Gives the user notice on a match was removed
         * @param matchId the id of the match
         */
        @Override
        public void onTurnBasedMatchRemoved(@NonNull String matchId) {
            Toast.makeText(StartActivity.this, "A match was removed.", Toast.LENGTH_SHORT).show();
        }
    };

    /**
     * Shows an error message
     * @param stringId the string rescorce id
     */
    public void showErrorMessage(int stringId) {
        showWarning("Warning", getResources().getString(stringId));
    }

    /**
     * Checks error status code
     * @param statusCode the status code for the error
     * @return true if no error false in case of error
     */
    private boolean checkStatusCode(int statusCode) {
        switch (statusCode) {
            case GamesCallbackStatusCodes.OK:
                return true;

            case GamesClientStatusCodes.MULTIPLAYER_ERROR_NOT_TRUSTED_TESTER:
                showErrorMessage(R.string.status_multiplayer_error_not_trusted_tester);
                break;
            case GamesClientStatusCodes.MATCH_ERROR_ALREADY_REMATCHED:
                showErrorMessage(R.string.match_error_already_rematched);
                break;
            case GamesClientStatusCodes.NETWORK_ERROR_OPERATION_FAILED:
                showErrorMessage(R.string.network_error_operation_failed);
                break;
            case GamesClientStatusCodes.INTERNAL_ERROR:
                showErrorMessage(R.string.internal_error);
                break;
            case GamesClientStatusCodes.MATCH_ERROR_INACTIVE_MATCH:
                showErrorMessage(R.string.match_error_inactive_match);
                break;
            case GamesClientStatusCodes.MATCH_ERROR_LOCALLY_MODIFIED:
                showErrorMessage(R.string.match_error_locally_modified);
                break;
            default:
                showErrorMessage(R.string.status_exception_error);
                Log.d(TAG, "Did not have warning or string to deal with: "
                        + statusCode);
        }

        return false;
    }

    /**
     * Opens an AlertDialog with settings
     * @param view the current view
     */
    public void onSettingsClicked(View view) {

        LayoutInflater factory = LayoutInflater.from(this);
        final View textEntryView = factory.inflate(R.layout.dialog_settings, null);
        final AlertDialog alertDialog = new AlertDialog.Builder(this)
                .setView(textEntryView)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int btn) {
                        try {
                            isBackgroundMusicOn = backgroundMusicSwitch.isChecked();
                            isEffectMusicOn = effectMusicSwitch.isChecked();
                            if (isBackgroundMusicOn){
                                backgroundMusicPlayer = MediaPlayer.create(getBaseContext(), R.raw.relaxing);
                                backgroundMusicPlayer.start();
                            }
                            else
                                backgroundMusicPlayer.stop();
                            if(winSwitch.isChecked())
                                Toast.makeText(getBaseContext(), "You fool, one can not win by cheating", Toast.LENGTH_SHORT).show();
                        } catch (Exception e) {
                        }
                    }
                })
                .create();

        alertDialog.show();
        backgroundMusicSwitch = (Switch) alertDialog.findViewById(R.id.backgroundMusicSwitch);
        backgroundMusicSwitch.setChecked(isBackgroundMusicOn);
        effectMusicSwitch = (Switch) alertDialog.findViewById(R.id.effectMusicSwitch);
        effectMusicSwitch.setChecked(isEffectMusicOn);
        winSwitch = (Switch) alertDialog.findViewById(R.id.winSwitch);
    }

    /**
     * Checks what was clicked and thakes action
     * @param v the view
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sign_in_button:
                mMatch = null;
                findViewById(R.id.sign_in_button).setVisibility(View.GONE);
                startSignInIntent();
                break;
            case R.id.sign_out_button:
                signOut();
                setViewVisibility();
                break;
        }
    }

    @Override
    public void onSeekComplete(MediaPlayer mp) {
        backgroundMusicPlayer.start();
    }
}