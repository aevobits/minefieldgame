package com.aevobits.games.minesfield;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Toast;

import com.aevobits.games.minesfield.manager.PlayerDataManager;
import com.aevobits.games.minesfield.manager.ResourceManager;
import com.aevobits.games.minesfield.scene.BaseScene;
import com.aevobits.games.minesfield.scene.SceneManager;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.firebase.analytics.FirebaseAnalytics;

import org.andengine.audio.sound.Sound;
import org.andengine.engine.camera.Camera;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.FillResolutionPolicy;
import org.andengine.engine.options.resolutionpolicy.IResolutionPolicy;
import org.andengine.entity.scene.Scene;

import java.io.IOException;

/**
 * Created by vito on 29/02/16.
 */
public class GameActivity extends MultiplayerActivity{

    private static final String TAG = "GameActivity";

    public static int SCREEN_WIDTH = 480;
    public static int SCREEN_HEIGHT = 800;

    private SharedPreferences settings;

    private SceneManager mSceneManager;
    private ResourceManager mResourceManager;
    private Camera mCamera;

    private FirebaseAnalytics mFirebaseAnalytics;

    @Override
    public EngineOptions onCreateEngineOptions() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        WindowManager windowmanager = (WindowManager) getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
        windowmanager.getDefaultDisplay().getMetrics(displayMetrics);
        //SCREEN_WIDTH = displayMetrics.widthPixels;
        //SCREEN_HEIGHT = displayMetrics.heightPixels;

        settings = getSharedPreferences("com.aevobits.games.minesfieldgame.prefs", MODE_PRIVATE);
        mCamera = new Camera(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
        IResolutionPolicy resolutionPolicy = new FillResolutionPolicy();
        EngineOptions engineOptions = new EngineOptions(true, ScreenOrientation.PORTRAIT_FIXED, resolutionPolicy, mCamera);
        engineOptions.getRenderOptions().setDithering(true);
        engineOptions.getAudioOptions().setNeedsMusic(true).setNeedsSound(true);

        return engineOptions;
    }

    @Override
    public void onCreateResources(OnCreateResourcesCallback pOnCreateResourcesCallback) throws IOException {
        mResourceManager = ResourceManager.getInstance();
        mResourceManager.create(this, getEngine(), getEngine().getCamera(), getVertexBufferObjectManager());
        mResourceManager.loadSplashResources();
        mSceneManager = SceneManager.getInstance();

        pOnCreateResourcesCallback.onCreateResourcesFinished();

    }

    @Override
    public void onCreateScene(OnCreateSceneCallback pOnCreateSceneCallback) throws IOException {
        PlayerDataManager.getInstance();
        this.mEngine.registerUpdateHandler(new TimerHandler(3f, new ITimerCallback() {
            public void onTimePassed(final TimerHandler pTimerHandler) {
                mEngine.unregisterUpdateHandler(pTimerHandler);
                mResourceManager.loadMainMenuResources();
                mSceneManager.setScene(SceneManager.SceneType.SCENE_MENU);
                mResourceManager.unloadSplashResources();
            }
        }));
        BaseScene scene = mSceneManager.createSplashScene();
        mSceneManager.setCurrentScene(scene);
        pOnCreateSceneCallback.onCreateSceneFinished(scene);

    }

    @Override
    public void onPopulateScene(Scene pScene, OnPopulateSceneCallback pOnPopulateSceneCallback) throws IOException {
        pOnPopulateSceneCallback.onPopulateSceneFinished();

    }

    public void setSound(boolean sound){
        SharedPreferences.Editor settingsEditor = settings.edit();
        settingsEditor.putBoolean("Sound", sound);
        settingsEditor.commit();
    }

    public boolean isSound(){
        return settings.getBoolean("Sound", true);
    }

    public void playSound(Sound soundToPlay){
        if(isSound()){
            soundToPlay.play();
        }
    }

    public void stopSound(Sound soundToPlay){
        if(isSound()){
            soundToPlay.stop();
        }
    }
/*
    public boolean isPlaying(Sound soundToCheck){
        if (soundToCheck.)
    }
*/
    public final Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            if(msg.arg1 == 1) {
                CharSequence text = getString(R.string.bombs_flag_toast, msg.arg2);
                int duration = Toast.LENGTH_SHORT;
                Toast.makeText(mResourceManager.mActivity.getApplicationContext(), text, duration).show();
            }
        }
    };

    @Override
    public void onSignInFailed() {
        PlayerDataManager.getInstance();
        Log.i(TAG, "Sign in failed!!");
    }

    @Override
    public void onSignInSucceeded() {
        PlayerDataManager.getInstance().loadPlayerData();
    }



    @Override
    public void onBackPressed() {
        final BaseScene baseScene = ((BaseScene) this.getEngine().getScene());
        if(baseScene.getSceneType() == SceneManager.SceneType.SCENE_GAME){
            new AlertDialog.Builder(this)
                    .setTitle(R.string.quitTitle)
                    .setMessage(R.string.quitTxt)
                    .setNegativeButton(R.string.no, null)
                    .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface arg0, int arg1) {
                            baseScene.disposeScene();
                            mResourceManager.loadMainMenuResources();
                            mSceneManager.setScene(SceneManager.SceneType.SCENE_MENU);
                        }
                    }).create().show();
        }else if(baseScene.getSceneType() == SceneManager.SceneType.SCENE_MENU){
                     new AlertDialog.Builder(this)
                        .setTitle(R.string.exitTitle)
                        .setMessage(R.string.exitTxt)
                        .setNegativeButton(R.string.no, null)
                        .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface arg0, int arg1) {
                                finish();
                            }
                        }).create().show();
        }else if(baseScene.getSceneType() == SceneManager.SceneType.SCENE_LEVEL_MENU){
            mSceneManager.setScene(SceneManager.SceneType.SCENE_MENU);
        }


    }

    @Override
    protected int getLayoutID() {
        return R.layout.game_layout;
    }

    @Override
    protected int getRenderSurfaceViewID() {
        return R.id.SurfaceViewId;
    }

    @Override
    protected void onSetContentView() {
        super.onSetContentView();
        /*
        AdView mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice("043E555F988D1CEBF136C59FD0DD2C9B")
                //.addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();
        mAdView.loadAd(adRequest);
        */
        MobileAds.initialize(getApplicationContext(), getString(R.string.banner_ad_unit_id_test));
        AdView mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)        // All emulators
                .addTestDevice("AC98C820A50B4AD8A2106EDE96FB87D4")  // An example device ID
                .build();
        mAdView.loadAd(adRequest);
    }
/*
    @Override
    protected void onCreate(Bundle b) {
        super.onCreate(b);
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        String versionCode = String.valueOf(BuildConfig.VERSION_CODE);
        String versionName = BuildConfig.VERSION_NAME;
        mFirebaseAnalytics.setUserProperty("version_code", versionCode);
        mFirebaseAnalytics.setUserProperty("versione_name", versionName);
        mFirebaseAnalytics.logEvent(TAG, b);

    }
*/
    @Override
    protected synchronized void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        System.exit(0);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        System.exit(0);
    }
}
