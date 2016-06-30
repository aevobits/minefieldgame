package com.aevobits.games.minesfield.util;

import android.os.AsyncTask;
import android.util.Log;

import com.aevobits.games.minesfield.GameActivity;
import com.google.android.gms.games.Games;
import com.google.android.gms.games.snapshot.Snapshot;
import com.google.android.gms.games.snapshot.SnapshotMetadataChange;
import com.google.android.gms.games.snapshot.Snapshots;

import java.io.IOException;

/**
 * Created by vito on 27/06/16.
 */
public class PlayerDataManagement {

    private static PlayerDataManagement pdm;
    private GameActivity mActivity;
    private byte[] mSaveGameData;

    private static final String CURRENT_SAVE_NAME = "com.aevobits.games.minesfieldgame.playerdata";
    private static final String TAG = PlayerDataManagement.class.getSimpleName();

    private PlayerDataManagement(GameActivity activity){
        this.mActivity = activity;
    };

    public static final PlayerDataManagement getInstance(GameActivity activity){
        if (pdm==null){
            pdm = new PlayerDataManagement(activity);
        }

        return pdm;
    }

    public void savePlayerData(final String key, final Object data){

        // Open the saved game using its name.
        Snapshots.OpenSnapshotResult result = Games.Snapshots.open(mActivity.getApiClient(),
                CURRENT_SAVE_NAME + "." + key, true).await();

        // Check the result of the open operation
        if (result.getStatus().isSuccess()) {
            Snapshot snapshot = result.getSnapshot();
            // Set the data payload for the snapshot
            snapshot.getSnapshotContents().writeBytes(playerDataToBytes(data));
            // Commit the operation
            Games.Snapshots.commitAndClose(mActivity.getApiClient(), snapshot, SnapshotMetadataChange.EMPTY_CHANGE);

        } else {
            Log.e(TAG, "Error while loading: " + result.getStatus().getStatusCode());
        }
    }

    public String loadPlayerData(final String key){

        AsyncTask<Void, Void, Integer> task = new AsyncTask<Void, Void, Integer>() {
            @Override
            protected Integer doInBackground(Void... params) {
                // Open the saved game using its name.
                Snapshots.OpenSnapshotResult result = Games.Snapshots.open(mActivity.getApiClient(),
                        CURRENT_SAVE_NAME + "." + key, true).await();

                // Check the result of the open operation
                if (result.getStatus().isSuccess()) {
                    Snapshot snapshot = result.getSnapshot();
                    // Read the byte content of the saved game.
                    try {
                        mSaveGameData = snapshot.getSnapshotContents().readFully();
                        Log.e(TAG, "saved game data:" + mSaveGameData);
                    } catch (IOException e) {
                        Log.e(TAG, "Error while reading Snapshot.", e);
                    }
                } else {
                    Log.e(TAG, "Error while loading: " + result.getStatus().getStatusCode());
                }

                return result.getStatus().getStatusCode();
            }
        };
        task.execute();

        return "1";//mSaveGameData.toString();
    }

    public byte[] playerDataToBytes(Object data){

        return data.toString().getBytes();
    }

    public String playerDataToData(byte[] data){

        return data.toString();
    }

}
