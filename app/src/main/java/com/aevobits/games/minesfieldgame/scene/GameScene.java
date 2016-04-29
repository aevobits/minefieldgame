package com.aevobits.games.minesfieldgame.scene;

import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;

import com.aevobits.games.minesfieldgame.BuildConfig;
import com.aevobits.games.minesfieldgame.GameActivity;
import com.aevobits.games.minesfieldgame.R;
import com.aevobits.games.minesfieldgame.ResourceManager;
import com.aevobits.games.minesfieldgame.Utils;
import com.aevobits.games.minesfieldgame.entity.Tile;
import com.google.android.gms.games.Games;

import org.andengine.engine.camera.hud.HUD;
import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.entity.modifier.FadeInModifier;
import org.andengine.entity.modifier.FadeOutModifier;
import org.andengine.entity.modifier.IEntityModifier;
import org.andengine.entity.modifier.ParallelEntityModifier;
import org.andengine.entity.modifier.ScaleModifier;
import org.andengine.entity.modifier.SequenceEntityModifier;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.scene.CameraScene;
import org.andengine.entity.scene.IOnSceneTouchListener;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.sprite.TiledSprite;
import org.andengine.entity.text.Text;
import org.andengine.entity.text.TextOptions;
import org.andengine.input.touch.TouchEvent;
import org.andengine.input.touch.detector.ContinuousHoldDetector;
import org.andengine.input.touch.detector.HoldDetector;
import org.andengine.util.adt.align.HorizontalAlign;
import org.andengine.util.adt.color.Color;

/**
 * Created by vito on 29/02/16.
 */
public class GameScene extends BaseScene {

    private MapManager mapManager;

    private float tileDimension = 60f;
    private final float PADDING_SIDE = 30f;
    private int rows = 10;
    private int cols = 10;
    private int bombs = 20;
    private Text mTimerHudText;

    private GameOverScene gameOverScene;

    private TimerHandler timer;
    private IUpdateHandler gameUpdateHandler;

    @Override
    public void createScene() {
        if(MapManager.getInstance().level==1){
            rows = 5;
            cols = 5;
            bombs = 5;
        }
        if(MapManager.getInstance().level==2){
            rows = 10;
            cols = 10;
            bombs = 20;
        }
        if(MapManager.getInstance().level==3){
            rows = 10;
            cols = 10;
            bombs = 30;
        }
        if(MapManager.getInstance().level==4){
            rows = 15;
            cols = 12;
            bombs = 50;
        }

        createBackground();
        createField();
        gameOverScene = new GameOverScene(this);
        fadeIn();
    }

    private void createBackground(){
        getBackground().setColor(new Color(0.109803922f, 0.717647059f, 0.921568627f));
    }

    private void createField(){

        tileDimension = (GameActivity.CAMERA_WIDTH - (2 * PADDING_SIDE)) / (this.cols);

        float mInitialX = PADDING_SIDE + (tileDimension * 0.5f);

        final float fieldHeight = (tileDimension * this.rows);
        float mInitialY = (((GameActivity.CAMERA_HEIGHT - fieldHeight) * 0.5f) + fieldHeight) - (tileDimension * 0.5f);

        Rectangle rectangle = new Rectangle(mInitialX + (tileDimension * (this.cols * 0.5f) - tileDimension * 0.5f),
                mInitialY - (tileDimension * (this.rows * 0.5f) - tileDimension * 0.5f),
                (tileDimension * this.cols) + 10f, (tileDimension * this.rows) + 10f, mResourceManager.vbom);
        rectangle.setColor(Color.WHITE);
        attachChild(rectangle);

        mapManager = MapManager.getInstance();
        mapManager.create(rows, cols, bombs, mInitialX, mInitialY, this);

        float tmpY = mInitialY;

        for (int i = 1; i <= this.rows; i++){
            float tmpX = mInitialX;
            for (int j = 1; j <= this.cols; j++) {
                Tile tile = new Tile(tmpX, tmpY, i, j, tileDimension, tileDimension, mResourceManager.tileTextureRegion, mResourceManager.vbom);
                registerTouchArea(tile);
                setTouchAreaBindingOnActionDownEnabled(true);
                attachChild(tile);

                tmpX = tmpX + tileDimension;
            }
            tmpY = tmpY - tileDimension;
        }

        Sprite bombsSprite = new Sprite(82, SCREEN_HEIGHT-50f,128, 64, mResourceManager.bombsTileTextureRegion, mResourceManager.vbom);
        attachChild(bombsSprite);

        mapManager.mBombsHudText = new Text(110f, SCREEN_HEIGHT-50f, mResourceManager.montserrat, "0123456789", new TextOptions(HorizontalAlign.LEFT), mResourceManager.vbom);
        mapManager.mBombsHudText.setText(String.valueOf(mapManager.flagsBombs));
        attachChild(mapManager.mBombsHudText);

        Sprite timerSprite = new Sprite(SCREEN_WIDTH - 110f, SCREEN_HEIGHT-50f,178, 64, mResourceManager.timerGameTileTextureRegion, mResourceManager.vbom);
        attachChild(timerSprite);
        mTimerHudText = new Text(SCREEN_WIDTH - 80f, SCREEN_HEIGHT-50f, mResourceManager.montserrat, "0123456789", new TextOptions(HorizontalAlign.LEFT), mResourceManager.vbom);
        mTimerHudText.setText("0:00");
        attachChild(mTimerHudText);



        Sprite homeSprite = new Sprite(82f, 50f,64, 64, mResourceManager.homeTextureRegion, mResourceManager.vbom){
            @Override
            public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
                if (pSceneTouchEvent.isActionUp()) {
                    backToMenu();
                }
                return true;
            }
        };
        attachChild(homeSprite);
        registerTouchArea(homeSprite);

        Sprite bestScoreSprite = new Sprite(SCREEN_WIDTH / 2, 65f,160, 96, mResourceManager.bestScoreTextureRegion, mResourceManager.vbom);
        attachChild(bestScoreSprite);

        Text bestScoreText = new Text(SCREEN_WIDTH / 2, 85f, mResourceManager.montserrat, "BEST", new TextOptions(HorizontalAlign.CENTER), mResourceManager.vbom);
        bestScoreText.setScale(1.1f);
        bestScoreText.setColor(Color.BLACK);
        attachChild(bestScoreText);

        String bestScoreString = "" + mResourceManager.mActivity.getHiscore();
        Text bestScore = new Text(SCREEN_WIDTH / 2, 45f, mResourceManager.montserrat, bestScoreString, new TextOptions(HorizontalAlign.CENTER), mResourceManager.vbom);
        bestScore.setScale(0.9f);
        bestScore.setColor(Color.BLACK);
        attachChild(bestScore);

        Sprite replayArrowSprite = new Sprite((SCREEN_WIDTH)-82f, 50f,64, 64, mResourceManager.replayTextureRegion, mResourceManager.vbom){
            @Override
            public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
                if (pSceneTouchEvent.isActionUp()) {
                    restartGame();
                }
                return true;
            }
        };
        attachChild(replayArrowSprite);
        registerTouchArea(replayArrowSprite);

        //mResourceManager.engine.registerUpdateHandler(new FPSLogger());
        gameUpdateHandler = new IUpdateHandler() {

            @Override
            public void onUpdate(float pSecondsElapsed) {

                if(mapManager.getGameOver() && (!mapManager.isWin())){
                    String text = "Hai Perso!";
                    gameOverScene.setGameOverText(text);
                    setChildScene(gameOverScene.getmGameOverScene(), false, true, true);
                }
                if(mapManager.getGameOver() && (mapManager.isWin())){
                    String text = "Hai Vinto!";
                    gameOverScene.setGameOverText(text);
                    String gameOverScore = "Il tuo punteggio è: " + mapManager.newScore;
                    gameOverScene.setGameOverTextScore(gameOverScore);
                    gameOverScene.setGameOverTextScoreVisible(true);
                    setChildScene(gameOverScene.getmGameOverScene(), false, true, true);
                }
            }

            @Override
            public void reset() {

            }
        };
        registerUpdateHandler(gameUpdateHandler);

        timer = new TimerHandler(1f, true, new ITimerCallback() {

            @Override
            public void onTimePassed(TimerHandler pTimerHandler) {
                mapManager.seconds++;
                if(mapManager.seconds==11){
                    unregisterUpdateHandler(gameUpdateHandler);
                    String text = "Tempo Scaduto!";
                    gameOverScene.setGameOverText(text);
                    gameOverScene.setGameOverTextScale(0.8f);
                    setChildScene(gameOverScene.getmGameOverScene(), false, true, true);
                }else{
                    String time = Utils.secondsToString(mapManager.seconds);
                    mTimerHudText.setText(time);
                }
            }
        });
        registerUpdateHandler(timer);

        // for debug purpose only
        mapManager.switchBombs(tileDimension, tileDimension);
    }

    public void restartGame(){
        this.unregisterUpdateHandler(gameUpdateHandler);
        this.unregisterUpdateHandler(timer);
        mapManager.setGameOver(false);
        mSceneManager.setScene(SceneManager.SceneType.SCENE_GAME);
    }

    public void backToMenu(){
        mResourceManager.loadMainManuResources();
        this.unregisterUpdateHandler(gameUpdateHandler);
        this.unregisterUpdateHandler(timer);
        mapManager.setGameOver(false);
        mSceneManager.setScene(SceneManager.SceneType.SCENE_MENU);
        mResourceManager.unloadGameResources();
    }

    @Override
    public void onBackKeyPressed() {
        mSceneManager.setScene(SceneManager.SceneType.SCENE_MENU);
    }



    @Override
    public SceneManager.SceneType getSceneType() {
        return SceneManager.SceneType.SCENE_GAME;
    }

    @Override
    public void disposeScene() {
        this.unregisterUpdateHandler(gameUpdateHandler);
        this.unregisterUpdateHandler(timer);
    }
}
