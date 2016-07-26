package com.aevobits.games.minesfield.manager;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;

import com.aevobits.games.minesfield.GameActivity;
import com.google.android.gms.games.Games;
import com.google.android.gms.games.GamesStatusCodes;
import com.google.android.gms.games.snapshot.Snapshot;
import com.google.android.gms.games.snapshot.SnapshotMetadataChange;
import com.google.android.gms.games.snapshot.Snapshots;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by vito on 27/06/16.
 */
public class PlayerDataManager {

    private static PlayerDataManager pdm;
    private GameActivity mActivity;
    public static Map<String, String> mData;

    private static final String CURRENT_SAVE_NAME = "com.aevobits.games.minesfieldgame.playerdata2";
    private static final String TAG = PlayerDataManager.class.getSimpleName();
    private SharedPreferences settings;

    private PlayerDataManager(){
        this.mActivity = ResourceManager.getInstance().mActivity;
        settings = this.mActivity.getSharedPreferences(CURRENT_SAVE_NAME,this.mActivity.MODE_PRIVATE);
        initializeData();
    };

    public static final PlayerDataManager getInstance(){
        if (pdm==null){
            pdm = new PlayerDataManager();
        }

        return pdm;
    }

    public void savePlayerData(String key, String value){

        if (mActivity.getApiClient().isConnected()) {
            new AsyncTask<String, Void, Snapshots.OpenSnapshotResult>() {

                @Override
                protected Snapshots.OpenSnapshotResult doInBackground(String... params) {
                    // Here we get the Snapshot using the open() method
                    return Games.Snapshots.open(mActivity.getApiClient(), CURRENT_SAVE_NAME, true).await();
                }

                @Override
                protected void onPostExecute(Snapshots.OpenSnapshotResult openSnapshotResult) {
                    super.onPostExecute(openSnapshotResult);
                    // Here e have to manage the result that could contain some error
                    int status = openSnapshotResult.getStatus().getStatusCode();
                    switch (status) {
                        case GamesStatusCodes.STATUS_OK:
                        case GamesStatusCodes.STATUS_SNAPSHOT_CONTENTS_UNAVAILABLE:
                            // Everything is ok so we persist the data
                            persist(openSnapshotResult.getSnapshot());
                            break;
                        case GamesStatusCodes.STATUS_SNAPSHOT_CONFLICT:
                            manageConflict(openSnapshotResult);
                            break;
                    }
                }
            }.execute();
        }

    }

    private void persist(final Snapshot snapshot) {
        // We save the data into the opened Snapshot
        snapshot.getSnapshotContents().writeBytes(playerDataMapToBytes(mData));
        Games.Snapshots.commitAndClose(mActivity.getApiClient(), snapshot, SnapshotMetadataChange.EMPTY_CHANGE);
        Log.i(PlayerDataManager.TAG, "Snapshot :" + snapshot);
    }

    private void manageConflict(final Snapshots.OpenSnapshotResult result) {
        // We get the first Snapshot
        Snapshot snapshot = result.getSnapshot();
        // And the conflicting one
        Snapshot conflictSnapshot = result.getConflictingSnapshot();
        // We decide which one to keep as resolving one. To do that we use the information
        // related to the last modified date
        Snapshot resolvedSnapshot = snapshot;
        if (snapshot.getMetadata().getLastModifiedTimestamp() <
                conflictSnapshot.getMetadata().getLastModifiedTimestamp()) {
            resolvedSnapshot = conflictSnapshot;
        }
        // We resolve the conflict
        Snapshots.OpenSnapshotResult resolveResult = Games.Snapshots.resolveConflict(
                mActivity.getApiClient(), result.getConflictId(), resolvedSnapshot).await();
    }

    public void loadPlayerData(){

        new AsyncTask<Void, Void, Map<String, String>>() {
            @Override
            protected Map<String, String> doInBackground(Void... params) {
                if (!mActivity.getApiClient().isConnecting() && !mActivity.getApiClient().isConnected()){
                    Log.i(TAG, "Before connect");
                    mActivity.getApiClient().connect();
                    Log.i(TAG, "After connect");
                    return null;
                }else {
                    Log.i(TAG, "Before open:" + mActivity.getApiClient().isConnected());

                    // Open the saved game using its name.
                    Snapshots.OpenSnapshotResult result = Games.Snapshots.open(mActivity.getApiClient(),
                            CURRENT_SAVE_NAME, true).await();
                    // Check the result of the open operation
                    if (result.getStatus().isSuccess()) {
                        Snapshot snapshot = result.getSnapshot();
                        final byte[] mSaveGameData;
                        Map<String, String> data = null;
                        // Read the byte content of the saved game.
                        try {
                            mSaveGameData = snapshot.getSnapshotContents().readFully();
                            if (mSaveGameData.length > 0) {
                                data = playerDataByteToMap(mSaveGameData);
                            }
                        } catch (IOException e) {
                            Log.e(TAG, "Error while reading Snapshot.", e);
                        }
                        return data;
                    } else {
                        Log.e(TAG, "Error while loading: " + result.getStatus().getStatusCode());
                        return null;
                    }
                }

            }

            @Override
            protected void onPostExecute(Map<String, String> data) {
                super.onPostExecute(data);
                if (data != null)
                    mData = data;
            }
        }.execute();

    }

    private byte[] playerDataMapToBytes(Map<String, String> data){

        StringBuilder dataEncoded = new StringBuilder();
        for (String key: data.keySet()){
           dataEncoded.append(key + "#" + data.get(key) + "@") ;
        }
        dataEncoded.deleteCharAt(dataEncoded.length()-1);
        return dataEncoded.toString().getBytes();
    }

    private Map<String, String> playerDataByteToMap(byte[] data){

        String dataDecoded = new String(data);
        String[] dataSplitted = dataDecoded.split("@");
        Map<String, String> returnedData = new HashMap<String, String>();
        for(String singleData:dataSplitted){
            String[] keyValue = singleData.split("#");
            returnedData.put(keyValue[0],keyValue[1]);
        }

        return returnedData;
    }

    public void initializeData(){


        Map<String, String> data = new HashMap<String, String>();

        data.put("MaxLevel", settings.getString("MaxLevel", "1"));
        data.put("HiScore", settings.getString("HiScore", "0"));
        for (int i=1;i<=16;i++){
            data.put("GamesPlayed" + i, settings.getString("GamesPlayed" + i, "0"));
        }
        for (int i=1;i<=16;i++){
            data.put("GamesWon" + i, settings.getString("GamesWon" + i, "0"));
        }

        mData = data;
    }

    public void updateData(String key, String value){
        SharedPreferences.Editor settingsEditor = settings.edit();
        settingsEditor.putString(key, value);
        settingsEditor.commit();
        mData.put(key, value);
        savePlayerData(key, value);
    }

    public String getData(String key){
        return mData.get(key);
    }
/*
    public Integer getGamesPlayed(int level){
        return new Integer(mData.get("GamesPlayed" + level));
    }

    public void updateGamesPlayed(int level){
        Integer played = Integer.parseInt(mData.get("GamesPlayed" + level));
        played++;
        savePlayerData("GamesPlayed" + level, played.toString());
    }

    public Integer getGamesWon(int level){
        return new Integer(mData.get("GamesWon" + level));
    }

    public void updateGamesWon(int level){
        Integer won = Integer.parseInt(mData.get("GamesWon" + level));
        won++;
        savePlayerData("GamesWon" + level, won.toString());
    }

    public Float getHiScore(int level){
        Float hiScore = Float.valueOf(mData.get("HiScore" + level));
        return hiScore;
    }

    public void updateHiScore(int level, String newScore){
        savePlayerData("HiScore" + level, newScore);
    }
*/
}
