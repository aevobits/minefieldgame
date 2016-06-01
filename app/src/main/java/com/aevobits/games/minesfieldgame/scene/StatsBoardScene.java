package com.aevobits.games.minesfieldgame.scene;

import com.aevobits.games.minesfieldgame.GameActivity;
import com.aevobits.games.minesfieldgame.R;
import com.aevobits.games.minesfieldgame.ResourceManager;

import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.scene.CameraScene;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.entity.text.TextOptions;
import org.andengine.input.touch.TouchEvent;
import org.andengine.util.adt.align.HorizontalAlign;
import org.andengine.util.adt.color.Color;

/**
 * Created by vito on 01/06/16.
 */
public class StatsBoardScene {

    private ResourceManager mResourceManager;
    private CameraScene mStatsBoardScene;
    private SceneManager mSceneManager;

    public StatsBoardScene(){
        this.mResourceManager = ResourceManager.getInstance();
        this.mSceneManager = SceneManager.getInstance();
        this.mStatsBoardScene = new CameraScene(mResourceManager.camera);
        createStatsBoardScene();
    }

    private void createStatsBoardScene(){
        final float overX = GameActivity.CAMERA_WIDTH / 2;
        final float overY = GameActivity.CAMERA_HEIGHT / 2;

        this.mStatsBoardScene.setBackgroundEnabled(false);
        Rectangle backgroundGameOver = new Rectangle(overX, overY,
                GameActivity.CAMERA_WIDTH, GameActivity.CAMERA_HEIGHT, this.mResourceManager.vbom){
            @Override
            public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
                switch (pSceneTouchEvent.getAction()) {
                    case TouchEvent.ACTION_UP: {
                        mSceneManager.setScene(SceneManager.SceneType.SCENE_MENU);
                    }
                    default: {
                        return super.onAreaTouched(pSceneTouchEvent, pTouchAreaLocalX, pTouchAreaLocalY);
                    }
                }
            }
        };
        backgroundGameOver.setColor(new Color(0.004901961f, 0.004901961f, 0.004901961f));
        backgroundGameOver.setAlpha(0.9f);
        this.mStatsBoardScene.registerTouchArea(backgroundGameOver);
        this.mStatsBoardScene.attachChild(backgroundGameOver);

        String statistics = mResourceManager.mActivity.getString(R.string.statistics);
        Text statisticsText = new Text(GameActivity.CAMERA_WIDTH / 2,GameActivity.CAMERA_HEIGHT - 50f, mResourceManager.montserrat, statistics,
                new TextOptions(HorizontalAlign.CENTER), mResourceManager.vbom);
        statisticsText.setScale(1.5f);
        statisticsText.setColor(Color.WHITE);
        this.mStatsBoardScene.attachChild(statisticsText);

        final Sprite statsBoardSprite = new Sprite(overX, overY,430, 560, mResourceManager.statsBoardTextureRegion, mResourceManager.vbom);
        this.mStatsBoardScene.attachChild(statsBoardSprite);


        String easyTitle = mResourceManager.mActivity.getString(R.string.easy);
        Text easyTitleText = new Text(130f,640f, mResourceManager.montserrat, easyTitle, new TextOptions(HorizontalAlign.CENTER), mResourceManager.vbom);
        easyTitleText.setColor(Color.WHITE);
        this.mStatsBoardScene.attachChild(easyTitleText);

        String mediumTitle = mResourceManager.mActivity.getString(R.string.medium);
        Text mediumTitleText = new Text(345f,640f, mResourceManager.montserrat, mediumTitle, new TextOptions(HorizontalAlign.CENTER), mResourceManager.vbom);
        mediumTitleText.setColor(Color.WHITE);
        this.mStatsBoardScene.attachChild(mediumTitleText);

        String hardTitle = mResourceManager.mActivity.getString(R.string.hard);
        Text hardTitleText = new Text(130f,355f, mResourceManager.montserrat, hardTitle, new TextOptions(HorizontalAlign.CENTER), mResourceManager.vbom);
        hardTitleText.setColor(Color.WHITE);
        this.mStatsBoardScene.attachChild(hardTitleText);

        String proTitle = mResourceManager.mActivity.getString(R.string.pro);
        Text proTitleText = new Text(345f,355f, mResourceManager.montserrat, proTitle, new TextOptions(HorizontalAlign.CENTER), mResourceManager.vbom);
        proTitleText.setColor(Color.WHITE);
        this.mStatsBoardScene.attachChild(proTitleText);

    }

    public CameraScene getmStatsBoardScene() {
        return mStatsBoardScene;
    }
}
