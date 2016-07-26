package com.aevobits.games.minesfield.scene;

import android.app.AlertDialog;
import android.content.DialogInterface;

import com.aevobits.games.minesfield.GameActivity;
import com.aevobits.games.minesfield.R;
import com.aevobits.games.minesfield.manager.PlayerDataManager;
import com.aevobits.games.minesfield.util.Utils;
import com.aevobits.games.minesfield.entity.Tile;

import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.entity.primitive.Gradient;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.scene.CameraScene;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.EntityBackground;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.entity.text.TextOptions;
import org.andengine.input.touch.TouchEvent;
import org.andengine.input.touch.detector.ContinuousHoldDetector;
import org.andengine.input.touch.detector.PinchZoomDetector;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.util.adt.align.HorizontalAlign;
import org.andengine.util.adt.color.Color;

import java.util.Locale;

/**
 * Created by vito on 29/02/16.
 */
public class GameScene extends BaseScene {

    private static final String TAG = GameScene.class.getSimpleName();
    private MapManager mapManager;

    private float tileDimension = 60f;
    private final float PADDING_SIDE = 15f;
    private int rows = 10;
    private int cols = 10;
    private boolean playing = true;
    private Text mTimerHudText;

    private GameScene gameScene;
    private GameOverScene gameOverScene;
    private CameraScene pauseScene;
    private Sprite playButton;

    private TimerHandler timer;
    private IUpdateHandler gameUpdateHandler;

    @Override
    public void createScene() {
        //mActivity.setContentView(R.layout.game_layout);
        this.gameScene = this;
        this.gameScene.setTouchAreaBindingOnActionDownEnabled(true);

        mapManager = MapManager.getInstance();
        mapManager.create(this);
        int level = mapManager.level;
        Integer played = Integer.parseInt(PlayerDataManager.getInstance().getData("GamesPlayed" + level)) + 1;
        PlayerDataManager.getInstance().updateData("GamesPlayed" + level, played.toString());

        this.rows = mapManager.rows;
        this.cols = mapManager.cols;
        createBackground();
        createField();
        fadeIn();
    }

    private void createBackground(){
        Gradient g = new Gradient(SCREEN_WIDTH / 2, SCREEN_HEIGHT / 2, SCREEN_WIDTH, SCREEN_HEIGHT, mResourceManager.vbom);
        g.setGradient(new Color(0.68627451f, 0.866666667f, 0.91372549f), new Color(0.109803922f, 0.717647059f, 0.921568627f), 0, 1);
        this.setBackground(new EntityBackground(g));
        g.setIgnoreUpdate(true);
    }

    private void createField(){

        tileDimension = (SCREEN_WIDTH - (2 * PADDING_SIDE)) / (this.cols);

        float mInitialX = PADDING_SIDE + (tileDimension * 0.5f);

        final float fieldHeight = (tileDimension * this.rows);
        float mInitialY = ((((SCREEN_HEIGHT - fieldHeight) * 0.5f) + fieldHeight) - (tileDimension * 0.5f))+50f;
        mapManager.pXInit = mInitialX;
        mapManager.pYInit = mInitialY;

        Rectangle rectangle = new Rectangle(mInitialX + (tileDimension * (this.cols * 0.5f) - tileDimension * 0.5f),
                mInitialY - (tileDimension * (this.rows * 0.5f) - tileDimension * 0.5f),
                (tileDimension * this.cols) + 10f, (tileDimension * this.rows) + 10f, mResourceManager.vbom);
        rectangle.setColor(new Color(0.650980392f,0.447058824f,0.211764706f));
        rectangle.setIgnoreUpdate(true);
        attachChild(rectangle);

        float tmpY = mInitialY;

        for (int i = 1; i <= this.rows; i++){
            float tmpX = mInitialX;
            for (int j = 1; j <= this.cols; j++) {
                Tile tile = new Tile(tmpX, tmpY, i, j, tileDimension, tileDimension, mResourceManager.tileTextureRegion, mResourceManager.vbom);
                registerTouchArea(tile);
                tile.setIgnoreUpdate(true);
                attachChild(tile);

                tmpX = tmpX + tileDimension;
            }
            tmpY = tmpY - tileDimension;
        }

        pauseScene = new CameraScene(mResourceManager.camera);
        pauseScene.setBackgroundEnabled(false);
        Rectangle backgroundPauseScene = new Rectangle(SCREEN_WIDTH / 2, SCREEN_HEIGHT / 2,
                SCREEN_WIDTH, SCREEN_HEIGHT, vbom);
        backgroundPauseScene.setColor(new Color(0.004901961f, 0.004901961f, 0.004901961f));
        backgroundPauseScene.setAlpha(0.5f);
        Rectangle rectangleGame = new Rectangle(mInitialX + (tileDimension * (this.cols * 0.5f) - tileDimension * 0.5f),
                mInitialY - (tileDimension * (this.rows * 0.5f) - tileDimension * 0.5f),
                (tileDimension * this.cols) + 10f, (tileDimension * this.rows) + 10f, mResourceManager.vbom);
        rectangleGame.setColor(Color.WHITE);
        pauseScene.attachChild(backgroundPauseScene);
        pauseScene.attachChild(rectangleGame);

        playButton = new Sprite(rectangleGame.getX(), rectangleGame.getY(), 120, 120, mResourceManager.playTextureRegion, vbom){
            @Override
            public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
                if (pSceneTouchEvent.isActionUp()) {
                    gameScene.clearChildScene();
                    gameScene.playing = true;

                }
                return true;
            }
        };
        pauseScene.attachChild(playButton);
        pauseScene.registerTouchArea(playButton);

        Sprite bombsSprite = new Sprite(82, SCREEN_HEIGHT-25f,128, 40, mResourceManager.bombsTileTextureRegion, mResourceManager.vbom);
        bombsSprite.setIgnoreUpdate(true);
        attachChild(bombsSprite);

        mapManager.mBombsHudText = new Text(105f, SCREEN_HEIGHT-27f, mResourceManager.bebasneueBold, "0123456789", new TextOptions(HorizontalAlign.LEFT), mResourceManager.vbom);
        mapManager.mBombsHudText.setScale(0.8f);
        mapManager.mBombsHudText.setText(String.valueOf(mapManager.flagsBombs));
        attachChild(mapManager.mBombsHudText);

        Sprite currentLevelSprite = new Sprite((SCREEN_WIDTH / 2) - 10f, SCREEN_HEIGHT-25f,128, 40, mResourceManager.currentLevelTextureRegion, mResourceManager.vbom);
        currentLevelSprite.setIgnoreUpdate(true);
        attachChild(currentLevelSprite);

        Text currentLevelText = new Text((SCREEN_WIDTH / 2) + 7f, SCREEN_HEIGHT-27f, mResourceManager.bebasneueBold, String.valueOf(mapManager.level), new TextOptions(HorizontalAlign.LEFT), mResourceManager.vbom);
        currentLevelText.setIgnoreUpdate(true);
        currentLevelText.setScale(0.8f);
        attachChild(currentLevelText);

        Sprite timerSprite = new Sprite(SCREEN_WIDTH - 104f, SCREEN_HEIGHT-25f,140, 40, mResourceManager.timerGameTileTextureRegion, mResourceManager.vbom);
        timerSprite.setIgnoreUpdate(true);
        attachChild(timerSprite);

        mTimerHudText = new Text(SCREEN_WIDTH - 85f, SCREEN_HEIGHT-27f, mResourceManager.bebasneueBold, "0123456789", new TextOptions(HorizontalAlign.LEFT), mResourceManager.vbom);
        mTimerHudText.setText("0:00");
        mTimerHudText.setScale(0.8f);
        attachChild(mTimerHudText);

        float pY = 110;
        float icon_width = 80;
        float padding = 10;
        float pX = ((SCREEN_WIDTH - ((icon_width + padding) * 3)) / 2) + (icon_width / 2);

        Sprite homeSprite = new Sprite(pX, pY,icon_width, icon_width, mResourceManager.homeTextureRegion, mResourceManager.vbom){
            @Override
            public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
                if (pSceneTouchEvent.isActionUp()) {
                    mResourceManager.mActivity.playSound(mResourceManager.soundClick);
                    mActivity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            new AlertDialog.Builder(mActivity)
                                    .setTitle(R.string.quitTitle)
                                    .setMessage(R.string.quitTxt)
                                    .setNegativeButton(R.string.no, null)
                                    .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {

                                        public void onClick(DialogInterface arg0, int arg1) {
                                            backToMenu();
                                        }
                                    }).create().show();
                        }
                    });
                }
                return true;
            }
        };
        registerTouchArea(homeSprite);
        homeSprite.setIgnoreUpdate(true);
        attachChild(homeSprite);

        pX = pX + icon_width + padding;
        Sprite pauseSprite = new Sprite(pX, pY,icon_width, icon_width, mResourceManager.pauseTextureRegion, mResourceManager.vbom){
            @Override
            public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
                switch (pSceneTouchEvent.getAction()) {
                    case TouchEvent.ACTION_UP: {
                        playing = !playing;
                        if (!playing){
                            gameScene.setChildScene(pauseScene, false, true, true);
                        }else {

                        }
                        return true;
                    }
                    default: {
                        return super.onAreaTouched(pSceneTouchEvent, pTouchAreaLocalX, pTouchAreaLocalY);
                    }
                }
            }
        };
        registerTouchArea(pauseSprite);
        pauseSprite.setIgnoreUpdate(true);
        attachChild(pauseSprite);

        pX = pX + icon_width + padding;
        Sprite replayArrowSprite = new Sprite(pX, pY,icon_width, icon_width, mResourceManager.replayTextureRegion, mResourceManager.vbom){
            @Override
            public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
                if (pSceneTouchEvent.isActionUp()) {
                    mResourceManager.mActivity.playSound(mResourceManager.soundClick);
                    mActivity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            new AlertDialog.Builder(mActivity)
                                    .setTitle(R.string.replayTitle)
                                    .setMessage(R.string.replayTxt)
                                    .setNegativeButton(R.string.no, null)
                                    .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {

                                        public void onClick(DialogInterface arg0, int arg1) {
                                            restartGame();
                                        }
                                    }).create().show();
                        }
                    });
                }
                return true;
            }
        };
        registerTouchArea(replayArrowSprite);
        replayArrowSprite.setIgnoreUpdate(true);
        attachChild(replayArrowSprite);

        //mResourceManager.engine.registerUpdateHandler(new FPSLogger());
        gameUpdateHandler = new IUpdateHandler() {

            @Override
            public void onUpdate(float pSecondsElapsed) {

                if(mapManager.getGameOver() && (!mapManager.isWin())){
                    gameOverScene = new GameOverScene(gameScene, false);
                    setChildScene(gameOverScene.getmGameOverScene(), false, true, true);
                }
                if(mapManager.getGameOver() && (mapManager.isWin())){
                    gameOverScene = new GameOverScene(gameScene, true);
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
                if (playing) {
                    mapManager.seconds++;
                    String time = Utils.secondsToString(mapManager.seconds);
                    mTimerHudText.setText(time);
                }
            }
        });
        registerUpdateHandler(timer);

        // for debug purpose only
        mapManager.switchBombs(tileDimension, tileDimension, -1, -1);
    }

    public void restartGame(){
        this.unregisterUpdateHandler(gameUpdateHandler);
        this.unregisterUpdateHandler(timer);
        mapManager.setGameOver(false);
        mSceneManager.setScene(SceneManager.SceneType.SCENE_GAME);
    }

    public void backToMenu(){
        mResourceManager.loadMainMenuResources();
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
        MapManager.getInstance().setGameOver(false);
        this.unregisterUpdateHandler(gameUpdateHandler);
        this.unregisterUpdateHandler(timer);
        mResourceManager.unloadGameResources();
    }
}
