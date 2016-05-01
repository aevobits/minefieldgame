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

    public void setHiScore(int score){
        SharedPreferences.Editor settingsEditor = settings.edit();
        settingsEditor.putInt(KEY_HISCORE, score);
        settingsEditor.commit();
    }

    public int getHiscore(){
        return settings.getInt(KEY_HISCORE, 0);
    }

    public void resetScore(){
        SharedPreferences.Editor settingsEditor = settings.edit();
        settingsEditor.putInt(KEY_HISCORE, 0);
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
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
    }
}
