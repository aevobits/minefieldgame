package com.aevobits.games.minesfieldgame.scene;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.aevobits.games.minesfieldgame.GameActivity;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.games.Games;
import com.google.android.gms.plus.Plus;

/**
 * Created by vito on 18/03/16.
 */
public class PlayServicesManager
        implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private static final PlayServicesManager INSTANCE = new PlayServicesManager();

    private static final String DIALOG_ERROR = "dialog_error";
    public static final int REQUEST_RESOLVE_ERROR = 1001;
    final String TAG = "PlayServicesManager";
    private GameActivity activity;
    private boolean mAutoStartSignInFlow = true;
    private GoogleApiClient mGoogleApiClient;
    private boolean mResolvingConnectionFailure = false;
    private boolean mResolvingError = false;
    private boolean mSignInClicked = false;

    private PlayServicesManager(){}

    public static PlayServicesManager getInstance(){
        return INSTANCE;
    }

    private boolean isSignedIn()
    {
        return (this.mGoogleApiClient != null) && (this.mGoogleApiClient.isConnected());
    }

    public void init(GameActivity paramActivity)
    {
        Log.d("PlayServicesManager", "onConnected(): init");
        this.activity = paramActivity;
        this.mGoogleApiClient = new GoogleApiClient.Builder(this.activity)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(Plus.API).addScope(Plus.SCOPE_PLUS_LOGIN)
                .addApi(Games.API).addScope(Games.SCOPE_GAMES)
                .build();
        Log.d("PlayServicesManager", "onConnected(): end");
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        Log.d("PlayServicesManager", "onConnected()");
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.d("PlayServicesManager", "onConnectionSuspended(): attempting to connect");
        this.mGoogleApiClient.connect();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    public void onStart(){
        Log.d("PlayServicesManager", "onStart(): connecting");
        this.mGoogleApiClient.connect();
        Log.d("PlayServicesManager", "onStart(): connect has been invoked");
    }

    public void onStop(){
        Log.d("PlayServicesManager", "onStop(): disconnecting");
        if (this.mGoogleApiClient.isConnected()) {
            this.mGoogleApiClient.disconnect();
        }
    }
}
