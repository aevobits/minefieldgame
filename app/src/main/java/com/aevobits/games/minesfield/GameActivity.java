package com.aevobits.games.minesfield;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

import com.aevobits.games.minesfield.scene.BaseScene;
import com.aevobits.games.minesfield.scene.RulesBoardScene;
import com.aevobits.games.minesfield.scene.SceneManager;
import com.aevobits.games.minesfield.scene.StatsBoardScene;
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

import java.io.IOException;

/**
 * Created by vito on 29/02/16.
 */
public class GameActivity extends MineFieldBaseGameActivity {

    private static final String TAG = "GameActivity";

    public static final int CAMERA_WIDTH = 480;
    public static final int CAMERA_HEIGHT = 800;

    private SharedPreferences settings;

    private SceneManager mSceneManager;
    private ResourceManager mResourceManager;

    @Override
    public EngineOptions onCreateEngineOptions() {
        settings = getSharedPreferences("com.aevobits.games.minesfieldgame.prefs", MODE_PRIVATE);
        //resetScore();
        //resetGamesPlayed();
        //resetGamesWon();
        Camera camera = new Camera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);
        IResolutionPolicy resolutionPolicy = new FillResolutionPolicy();
        EngineOptions engineOptions = new EngineOptions(true, ScreenOrientation.PORTRAIT_FIXED, resolutionPolicy, camera);
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
        settingsEditor.putBoolean("Sound", sound);
        settingsEditor.commit();
    }

    public boolean isSound(){
        return settings.getBoolean("Sound", true);
    }

    public void setHiScore(float score, int level){
        SharedPreferences.Editor settingsEditor = settings.edit();
        settingsEditor.putFloat("HiScore" + level, score);
        settingsEditor.commit();
    }

    public float getHiscore(int level){
        return settings.getFloat("HiScore" + level, 0f);
    }

    public void resetScore(){
        SharedPreferences.Editor settingsEditor = settings.edit();
        settingsEditor.putFloat("HiScore1", 0f);
        settingsEditor.putFloat("HiScore2", 0f);
        settingsEditor.putFloat("HiScore3", 0f);
        settingsEditor.putFloat("HiScore4", 0f);
        settingsEditor.commit();
    }

    public void setGamesPlayed(int gamesPlayed, int level){
        SharedPreferences.Editor settingsEditor = settings.edit();
        settingsEditor.putInt("GamesPlayed" + level, gamesPlayed);
        settingsEditor.commit();
    }

    public int getGamesPlayed(int level){
        return settings.getInt("GamesPlayed" + level, 0);
    }

    public void resetGamesPlayed(){
        SharedPreferences.Editor settingsEditor = settings.edit();
        settingsEditor.putInt("GamesPlayed1", 0);
        settingsEditor.putInt("GamesPlayed2", 0);
        settingsEditor.putInt("GamesPlayed3", 0);
        settingsEditor.putInt("GamesPlayed4", 0);
        settingsEditor.commit();
    }

    public void setGamesWon(int gamesWon, int level){
        SharedPreferences.Editor settingsEditor = settings.edit();
        settingsEditor.putInt("GamesWon" + level, gamesWon);
        settingsEditor.commit();
    }

    public int getGamesWon(int level){
        return settings.getInt("GamesWon" + level, 0);
    }

    public void resetGamesWon(){
        SharedPreferences.Editor settingsEditor = settings.edit();
        settingsEditor.putInt("GamesWon1", 0);
        settingsEditor.putInt("GamesWon2", 0);
        settingsEditor.putInt("GamesWon3", 0);
        settingsEditor.putInt("GamesWon4", 0);
        settingsEditor.commit();
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
            if ((baseScene.getChildScene() instanceof RulesBoardScene) ||
                    (baseScene.getChildScene() instanceof StatsBoardScene)){

                baseScene.disposeScene();
                mResourceManager.loadMainManuResources();
                mSceneManager.setScene(SceneManager.SceneType.SCENE_MENU);

            }else {
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
        AdRequest adRequest = new AdRequest.Builder().addTestDevice("043E555F988D1CEBF136C59FD0DD2C9B")
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR).build();
        mAdView.loadAd(adRequest);
    }

}
