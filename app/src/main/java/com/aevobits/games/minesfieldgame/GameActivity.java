package com.aevobits.games.minesfieldgame;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

import com.aevobits.games.minesfieldgame.scene.BaseScene;
import com.aevobits.games.minesfieldgame.scene.GameScene;
import com.aevobits.games.minesfieldgame.scene.MapManager;
import com.aevobits.games.minesfieldgame.scene.SceneManager;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.example.games.basegameutils.MineFieldBaseGameActivity;

import org.andengine.audio.sound.Sound;
import org.andengine.engine.camera.Camera;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.FillResolutionPolicy;
import org.andengine.engine.options.resolutionpolicy.IResolutionPolicy;
import org.andengine.entity.scene.Scene;
import org.andengine.ui.activity.BaseGameActivity;

import java.io.IOException;

/**
 * Created by vito on 29/02/16.
 */
public class GameActivity extends MineFieldBaseGameActivity {

    private static final String TAG = "GameActivity";

    public static final int CAMERA_WIDTH = 480;
    public static final int CAMERA_HEIGHT = 800;

    public static final String KEY_SOUND = "Sound";
    public static final String KEY_HISCORE = "HiScore";
    public static final String KEY_GAMES_PLAYED_1 = "GamesPlayed1";
    public static final String KEY_GAMES_PLAYED_2 = "GamesPlayed2";
    public static final String KEY_GAMES_PLAYED_3 = "GamesPlayed3";
    public static final String KEY_GAMES_PLAYED_4 = "GamesPlayed4";
    public static final String KEY_GAMES_WON_1 = "GamesWon1";
    public static final String KEY_GAMES_WON_2 = "GamesWon2";
    public static final String KEY_GAMES_WON_3 = "GamesWon3";
    public static final String KEY_GAMES_WON_4 = "GamesWon4";

    private SharedPreferences settings;

    private SceneManager mSceneManager;
    private ResourceManager mResourceManager;

    @Override
    public EngineOptions onCreateEngineOptions() {
        //getGameHelper().setConnectOnStart(false);
        settings = getSharedPreferences("minesfieldgame_prefs", MODE_PRIVATE);
        //resetScore();
        Camera camera = new Camera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);
        IResolutionPolicy resolutionPolicy = new FillResolutionPolicy();
        EngineOptions engineOptions = new EngineOptions(true, ScreenOrientation.PORTRAIT_FIXED, resolutionPolicy, camera);
        engineOptions.getRenderOptions().setDithering(true);
        engineOptions.getAudioOptions().setNeedsMusic(true).setNeedsSound(true);

        return engineOptions;
    }

    @Override
    public void onCreateResources(OnCreateResourcesCallback pOnCreateResourcesCallback) throws IOException {
       //beginUserInitiatedSignIn();
        mResourceManager = ResourceManager.getInstance();
        mResourceManager.create(this, getEngine(), getEngine().getCamera(), getVertexBufferObjectManager());
        mResourceManager.loadSplashResources();
        mSceneManager = SceneManager.getInstance();

        pOnCreateResourcesCallback.onCreateResourcesFinished();

    }

    @Override
    public void onCreateScene(OnCreateSceneCallback pOnCreateSceneCallback) throws IOException {

        this.mEngine.registerUpdateHandler(new TimerHandler(3f, new ITimerCallback() {
            public void onTimePassed(final TimerHandler pTimerHandler) {
                mEngine.unregisterUpdateHandler(pTimerHandler);
                mResourceManager.loadMainManuResources();
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
        settingsEditor.putBoolean(KEY_SOUND, sound);
        settingsEditor.commit();
    }

    public boolean isSound(){
        return settings.getBoolean(KEY_SOUND, true);
    }

    public void setHiScore(float score){
        SharedPreferences.Editor settingsEditor = settings.edit();
        settingsEditor.putFloat(KEY_HISCORE, score);
        settingsEditor.commit();
    }

    public float getHiscore(){
        return settings.getFloat(KEY_HISCORE, 0f);
    }

    public void resetScore(){
        SharedPreferences.Editor settingsEditor = settings.edit();
        settingsEditor.putFloat(KEY_HISCORE, 0f);
        settingsEditor.commit();
    }

    public void setGamesPlayed(int gamesPlayed, int level){
        SharedPreferences.Editor settingsEditor = settings.edit();
        switch (level){
            case 1:{
                settingsEditor.putInt(KEY_GAMES_PLAYED_1, gamesPlayed);
                break;
            }
            case 2:{
                settingsEditor.putInt(KEY_GAMES_PLAYED_2, gamesPlayed);
                break;
            }
            case 3:{
                settingsEditor.putInt(KEY_GAMES_PLAYED_3, gamesPlayed);
                break;
            }
            case 4:{
                settingsEditor.putInt(KEY_GAMES_PLAYED_4, gamesPlayed);
                break;
            }
        }

        settingsEditor.commit();
    }

    public int getGamesPlayed(int level){
        int gamesPlayed = 0;
        switch (level){
            case 1:{
                gamesPlayed = settings.getInt(KEY_GAMES_PLAYED_1, 0);
                break;
            }
            case 2:{
                gamesPlayed = settings.getInt(KEY_GAMES_PLAYED_2, 0);
                break;
            }
            case 3:{
                gamesPlayed = settings.getInt(KEY_GAMES_PLAYED_3, 0);
                break;
            }
            case 4:{
                gamesPlayed = settings.getInt(KEY_GAMES_PLAYED_4, 0);
                break;
            }
        }

        return gamesPlayed;
    }

    public void setGamesWon(int gamesWon, int level){
        SharedPreferences.Editor settingsEditor = settings.edit();
        switch (level){
            case 1:{
                settingsEditor.putInt(KEY_GAMES_WON_1, gamesWon);
                break;
            }
            case 2:{
                settingsEditor.putInt(KEY_GAMES_WON_2, gamesWon);
                break;
            }
            case 3:{
                settingsEditor.putInt(KEY_GAMES_WON_3, gamesWon);
                break;
            }
            case 4:{
                settingsEditor.putInt(KEY_GAMES_WON_4, gamesWon);
                break;
            }
        }

        settingsEditor.commit();
    }

    public int getGamesWon(int level){
        int gamesWon = 0;
        switch (level){
            case 1:{
                gamesWon = settings.getInt(KEY_GAMES_WON_1, 0);
                break;
            }
            case 2:{
                gamesWon = settings.getInt(KEY_GAMES_WON_2, 0);
                break;
            }
            case 3:{
                gamesWon = settings.getInt(KEY_GAMES_WON_3, 0);
                break;
            }
            case 4:{
                gamesWon = settings.getInt(KEY_GAMES_WON_4, 0);
                break;
            }
        }

        return gamesWon;
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

    }

    @Override
    public void onSignInSucceeded() {

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
                            mResourceManager.loadMainManuResources();
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
        AdView mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().addTestDevice("043E555F988D1CEBF136C59FD0DD2C9B").build();
        mAdView.loadAd(adRequest);
    }
}
