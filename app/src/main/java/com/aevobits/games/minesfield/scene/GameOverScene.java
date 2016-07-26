package com.aevobits.games.minesfield.scene;

import com.aevobits.games.minesfield.GameActivity;
import com.aevobits.games.minesfield.R;
import com.aevobits.games.minesfield.manager.ResourceManager;
import com.aevobits.games.minesfield.util.Utils;

import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.scene.CameraScene;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.entity.text.TextOptions;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.util.adt.align.HorizontalAlign;
import org.andengine.util.adt.color.Color;

import java.util.Locale;

/**
 * Created by vito on 22/04/16.
 */
public class GameOverScene {

    private ResourceManager mResourceManager;
    private CameraScene mGameOverScene;
    private GameScene gameScene;
    private GameActivity mActivity;
    private MapManager mapManager;
    private boolean won;

    public GameOverScene(GameScene gameScene, boolean won){
        this.gameScene = gameScene;
        this.won = won;
        this.mResourceManager = ResourceManager.getInstance();
        this.mActivity = this.mResourceManager.mActivity;
        this.mapManager = MapManager.getInstance();
        //create CameraScene for game over
        mGameOverScene = new CameraScene(mResourceManager.camera);
        createGameOverScene();
    }

    private void createGameOverScene(){

        final float overX = GameActivity.SCREEN_WIDTH / 2;
        final float overY = GameActivity.SCREEN_HEIGHT / 2;

        this.mGameOverScene.setBackgroundEnabled(false);

        Rectangle backgroundGameOver = new Rectangle(overX, overY,
                GameActivity.SCREEN_WIDTH, GameActivity.SCREEN_HEIGHT, this.mResourceManager.vbom);
        backgroundGameOver.setColor(new Color(0.004901961f, 0.004901961f, 0.004901961f));
        backgroundGameOver.setAlpha(0.5f);
        this.mGameOverScene.attachChild(backgroundGameOver);

        ITextureRegion goTexture;
        if (this.won){
            goTexture = mResourceManager.gameOverTextTextureRegion;
        }else {
            goTexture = mResourceManager.gameOverFailedTextureRegion;
        }

        final Sprite gameOverTextSprite = new Sprite(overX, overY + 50f,360, 238, goTexture, mResourceManager.vbom);
        mGameOverScene.attachChild(gameOverTextSprite);

        String levelText;
        if (this.won){
            levelText = mActivity.getString(R.string.goCompleted);
        }else {
            levelText = mActivity.getString(R.string.goFailed);
        }
        Text gameOverText = new Text(overX, -50f, mResourceManager.bebasneue, levelText , new TextOptions(HorizontalAlign.CENTER), mResourceManager.vbom);
        mGameOverScene.attachChild(gameOverText);

        Text gameOverTextTitleScore = null;
        Text gameOverTextScore = null;
        Text gameOverLoseTextScore = null;
        if (this.won) {
            gameOverTextTitleScore = new Text(overX, overY + 30f, mResourceManager.bebasneue, mActivity.getString(R.string.goHiScore), new TextOptions(HorizontalAlign.CENTER), mResourceManager.vbom);
            gameOverTextTitleScore.setScale(1.4f);
            gameOverTextTitleScore.setColor(Color.BLACK);
            mGameOverScene.attachChild(gameOverTextTitleScore);

            Locale current = mActivity.getResources().getConfiguration().locale;
            String gameOverScore = String.format(current, "%.02f", mapManager.newScore);
            gameOverTextScore = new Text(overX, overY - 22f, mResourceManager.bebasneueBold, gameOverScore, new TextOptions(HorizontalAlign.CENTER), mResourceManager.vbom);
            gameOverTextScore.setScale(0.9f);
            mGameOverScene.attachChild(gameOverTextScore);
        }else {
            gameOverLoseTextScore = new Text(overX, overY + 30f, mResourceManager.bebasneue, mActivity.getString(R.string.goLose), new TextOptions(HorizontalAlign.CENTER), mResourceManager.vbom);
            gameOverLoseTextScore.setScale(2f);
            gameOverLoseTextScore.setColor(Color.BLACK);
            mGameOverScene.attachChild(gameOverLoseTextScore);
        }

        Sprite gameOverQuestionSprite = new Sprite(mActivity.SCREEN_WIDTH / 2, 110f, 600f, 80, mResourceManager.gameOverQuestionTextureRegion, mResourceManager.vbom);
        gameOverQuestionSprite.setVisible(false);
        mGameOverScene.attachChild(gameOverQuestionSprite);

        final Sprite gameOverYesSprite = new Sprite(-130f, 120,80, 80, mResourceManager.buttonTextureRegion, mResourceManager.vbom) {

            @Override
            public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
                if (pSceneTouchEvent.isActionUp()) {
                    mResourceManager.mActivity.stopSound(mResourceManager.soundExplosion);
                    gameScene.restartGame();
                }
                return true;
            }
        };
        mGameOverScene.registerTouchArea(gameOverYesSprite);
        mGameOverScene.attachChild(gameOverYesSprite);

        Text gameOverYesText = new Text(-130f, 100f, mResourceManager.bebasneue, mActivity.getString(R.string.goYes), new TextOptions(HorizontalAlign.CENTER), mResourceManager.vbom);
        mGameOverScene.attachChild(gameOverYesText);

        final Sprite gameOverNoSprite = new Sprite(mActivity.SCREEN_WIDTH + 130f, 120f,80, 80, mResourceManager.buttonTextureRegion, mResourceManager.vbom) {

            @Override
            public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
                if (pSceneTouchEvent.isActionUp()) {
                    mResourceManager.mActivity.stopSound(mResourceManager.soundExplosion);
                    gameScene.backToMenu();
                }
                return true;
            }
        };
        mGameOverScene.registerTouchArea(gameOverNoSprite);
        mGameOverScene.attachChild(gameOverNoSprite);

        Text gameOverNoText = new Text(mActivity.SCREEN_WIDTH + 130f, 110f, mResourceManager.bebasneue, mActivity.getString(R.string.goNo), new TextOptions(HorizontalAlign.CENTER), mResourceManager.vbom);
        mGameOverScene.attachChild(gameOverNoText);

        Text gameOverTextQuestion = new Text(overX, 105f, mResourceManager.bebasneue, mActivity.getString(R.string.goNewGame), new TextOptions(HorizontalAlign.CENTER), mResourceManager.vbom);
        if(Locale.getDefault().getLanguage().equals("it")){
            gameOverTextQuestion.setScale(0.95f);
        }else {
            gameOverTextQuestion.setScale(0.9f);
        }
        //gameOverTextQuestion.setColor(Color.BLACK);
        gameOverTextQuestion.setVisible(false);
        mGameOverScene.attachChild(gameOverTextQuestion);

        Utils.gameOverEffect(gameOverYesSprite, gameOverYesText, gameOverNoSprite, gameOverNoText, gameOverTextSprite,
                    gameOverTextQuestion, gameOverQuestionSprite, gameOverText, gameOverTextScore, gameOverTextTitleScore, gameOverLoseTextScore);

    }

    public CameraScene getmGameOverScene(){

        return mGameOverScene;
    }
}
