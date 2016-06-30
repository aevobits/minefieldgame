package com.aevobits.games.minesfield.scene;

import com.aevobits.games.minesfield.GameActivity;
import com.aevobits.games.minesfield.ResourceManager;
import com.aevobits.games.minesfield.util.Utils;

import org.andengine.entity.Entity;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.scene.CameraScene;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.entity.text.TextOptions;
import org.andengine.input.touch.TouchEvent;
import org.andengine.util.adt.align.HorizontalAlign;
import org.andengine.util.adt.color.Color;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vito on 22/04/16.
 */
public class GameOverScene {

    private ResourceManager mResourceManager;
    private CameraScene mGameOverScene;
    private GameScene gameScene;

    private Text gameOverText;
    private Text gameOverTextScore;

    public GameOverScene(GameScene gameScene){
        this.gameScene = gameScene;
        this.mResourceManager = ResourceManager.getInstance();
        //create CameraScene for game over
        mGameOverScene = new CameraScene(mResourceManager.camera);
        createGameOverScene();
    }

    private void createGameOverScene(){
        List<Entity> entityList = new ArrayList<Entity>();

        final float overX = GameActivity.CAMERA_WIDTH / 2;
        final float overY = GameActivity.CAMERA_HEIGHT / 2;

        this.mGameOverScene.setBackgroundEnabled(false);
        Rectangle backgroundGameOver = new Rectangle(overX, overY,
                GameActivity.CAMERA_WIDTH, GameActivity.CAMERA_HEIGHT, this.mResourceManager.vbom);
        backgroundGameOver.setColor(new Color(0.004901961f, 0.004901961f, 0.004901961f));
        backgroundGameOver.setAlpha(0.5f);
        this.mGameOverScene.attachChild(backgroundGameOver);

        final Sprite gameOverTextSprite = new Sprite(overX, overY,350, 250, mResourceManager.gameOverTextTextureRegion, mResourceManager.vbom);
        mGameOverScene.attachChild(gameOverTextSprite);
        entityList.add(gameOverTextSprite);

        gameOverText = new Text(overX, overY + 50, mResourceManager.montserrat, "Hai Vinto!/Hai Perso!", new TextOptions(HorizontalAlign.CENTER), mResourceManager.vbom);
        gameOverText.setScale(1.3f);
        gameOverText.setColor(Color.BLACK);
        mGameOverScene.attachChild(gameOverText);
        entityList.add(gameOverText);

        gameOverTextScore = new Text(overX, overY, mResourceManager.montserrat, "punteggio: " + String.format(mResourceManager.locale,"%.02f", 123456789.123456789f), new TextOptions(HorizontalAlign.CENTER), mResourceManager.vbom);
        gameOverTextScore.setScale(0.8f);
        gameOverTextScore.setColor(Color.BLACK);
        gameOverTextScore.setVisible(false);
        mGameOverScene.attachChild(gameOverTextScore);
        entityList.add(gameOverTextScore);

        Text gameOverTextQuestion = new Text(overX, overY - 50, mResourceManager.montserrat, "Vuoi giocare di nuovo?", new TextOptions(HorizontalAlign.CENTER), mResourceManager.vbom);
        gameOverTextQuestion.setScale(0.8f);
        gameOverTextQuestion.setColor(Color.BLACK);
        mGameOverScene.attachChild(gameOverTextQuestion);
        entityList.add(gameOverTextQuestion);

        final Sprite gameOverYesSprite = new Sprite(overX - (gameOverTextSprite.getWidth()/4), overY - (gameOverTextSprite.getHeight()/2),130, 80, mResourceManager.gameOverYesTextureRegion, mResourceManager.vbom) {

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
        entityList.add(gameOverYesSprite);

        Text gameOverYesText = new Text(overX - (gameOverTextSprite.getWidth()/4), overY - (gameOverTextSprite.getHeight()/2), mResourceManager.montserrat, "SI", new TextOptions(HorizontalAlign.CENTER), mResourceManager.vbom);
        mGameOverScene.attachChild(gameOverYesText);
        entityList.add(gameOverYesText);

        final Sprite gameOverNoSprite = new Sprite(overX + (gameOverTextSprite.getWidth()/4), overY - (gameOverTextSprite.getHeight()/2),130, 80, mResourceManager.gameOverNoTextureRegion, mResourceManager.vbom) {

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
        entityList.add(gameOverNoSprite);

        Text gameOverNoText = new Text(overX + (gameOverTextSprite.getWidth()/4), overY - (gameOverTextSprite.getHeight()/2), mResourceManager.montserrat, "NO", new TextOptions(HorizontalAlign.CENTER), mResourceManager.vbom);
        mGameOverScene.attachChild(gameOverNoText);
        entityList.add(gameOverNoText);

        Utils.fadeInEntityList(entityList);
    }

    public CameraScene getmGameOverScene(){

        return mGameOverScene;
    }

    public void setGameOverText(String text){
        this.gameOverText.setText(text);
    }

    public void setGameOverTextScale(float scale){
        this.gameOverText.setScale(scale);
    }

    public void setGameOverTextScore(String text){
        this.gameOverTextScore.setText(text);
    }

    public void setGameOverTextScoreVisible(boolean visible){
        this.gameOverTextScore.setVisible(visible);
    }
}
