package com.aevobits.games.minesfieldgame.scene;

import com.aevobits.games.minesfieldgame.GameActivity;
import com.aevobits.games.minesfieldgame.R;
import com.aevobits.games.minesfieldgame.ResourceManager;

import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.scene.CameraScene;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.AutoWrap;
import org.andengine.entity.text.Text;
import org.andengine.entity.text.TextOptions;
import org.andengine.input.touch.TouchEvent;
import org.andengine.util.adt.align.HorizontalAlign;
import org.andengine.util.adt.color.Color;

import java.util.Locale;

/**
 * Created by vito on 26/05/16.
 */
public class RulesBoardScene {

    private ResourceManager mResourceManager;
    private CameraScene mRulesBoardScene;
    private MainMenuScene mainMenuScene;
    private SceneManager mSceneManager;

    private static final float AUTOWRAP_WIDTH = 645f;

    public RulesBoardScene(MainMenuScene mainMenuScene){

        this.mainMenuScene = mainMenuScene;
        this.mResourceManager = ResourceManager.getInstance();
        this.mSceneManager = SceneManager.getInstance();
        this.mRulesBoardScene = new CameraScene(mResourceManager.camera);
        createRulesBoardScene();
    }

    private void createRulesBoardScene(){

        final float overX = GameActivity.CAMERA_WIDTH / 2;
        final float overY = GameActivity.CAMERA_HEIGHT / 2;

        this.mRulesBoardScene.setBackgroundEnabled(false);
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
        this.mRulesBoardScene.registerTouchArea(backgroundGameOver);
        this.mRulesBoardScene.attachChild(backgroundGameOver);

        final Sprite rulesBoardSprite = new Sprite(overX, overY,400, 600, mResourceManager.rulesBoardTextureRegion, mResourceManager.vbom);
        mRulesBoardScene.attachChild(rulesBoardSprite);

        String rulesDescription = mResourceManager.mActivity.getString(R.string.rules_description);
        Text rulesDescriptionText = new Text(GameActivity.CAMERA_WIDTH / 2, (GameActivity.CAMERA_HEIGHT / 2) - 25f, mResourceManager.montserrat,
                rulesDescription, new TextOptions(AutoWrap.WORDS, AUTOWRAP_WIDTH, HorizontalAlign.LEFT, Text.LEADING_DEFAULT), mResourceManager.vbom);
        if(Locale.getDefault().getLanguage().equals("it")){
            rulesDescriptionText.setScale(0.52f);
        }else {
            rulesDescriptionText.setScale(0.55f);
        }
        rulesDescriptionText.setColor(Color.WHITE);
        this.mRulesBoardScene.attachChild(rulesDescriptionText);

    }

    public CameraScene getmRulesBoardScene() {
        return mRulesBoardScene;
    }
}
