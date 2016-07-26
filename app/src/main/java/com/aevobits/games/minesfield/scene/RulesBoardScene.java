package com.aevobits.games.minesfield.scene;

import com.aevobits.games.minesfield.GameActivity;
import com.aevobits.games.minesfield.R;
import com.aevobits.games.minesfield.manager.ResourceManager;

import org.andengine.entity.primitive.Rectangle;
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
public class RulesBoardScene extends BaseScene{

    private ResourceManager mResourceManager;
    private SceneManager mSceneManager;

    private static final float AUTOWRAP_WIDTH = 820f;

    public RulesBoardScene(){
        this.mResourceManager = ResourceManager.getInstance();
        this.mSceneManager = SceneManager.getInstance();
        createRulesBoardScene();
    }

    @Override
    public void createScene() {

    }

    @Override
    public void onBackKeyPressed() {

    }

    @Override
    public SceneManager.SceneType getSceneType() {
        return SceneManager.SceneType.SCENE_RULES_BOARD;
    }

    @Override
    public void disposeScene() {

    }

    private void createRulesBoardScene(){

        final float overX = GameActivity.SCREEN_WIDTH / 2;
        final float overY = GameActivity.SCREEN_HEIGHT / 2;

        this.setBackgroundEnabled(false);
        Rectangle backgroundGameOver = new Rectangle(overX, overY,
                GameActivity.SCREEN_WIDTH, GameActivity.SCREEN_HEIGHT, this.mResourceManager.vbom){
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
        this.registerTouchArea(backgroundGameOver);
        this.attachChild(backgroundGameOver);

        String howToPlay = mResourceManager.mActivity.getString(R.string.howToPlay);
        Text howToPlayText = new Text((GameActivity.SCREEN_WIDTH / 2) + 20f,GameActivity.SCREEN_HEIGHT - 62f, mResourceManager.montserrat, howToPlay,
                new TextOptions(HorizontalAlign.CENTER), mResourceManager.vbom);
        howToPlayText.setScale(1.25f);
        howToPlayText.setColor(Color.WHITE);
        this.attachChild(howToPlayText);

        final Sprite rulesBoardSprite = new Sprite(overX, overY + 30f,450, 680, mResourceManager.rulesBoardTextureRegion, mResourceManager.vbom);
        this.attachChild(rulesBoardSprite);

        String rulesDescription = mResourceManager.mActivity.getString(R.string.rules_description);
        Text rulesDescriptionText = new Text(GameActivity.SCREEN_WIDTH / 2, (GameActivity.SCREEN_HEIGHT / 2) + 15f, mResourceManager.montserrat,
                rulesDescription, new TextOptions(AutoWrap.WORDS, AUTOWRAP_WIDTH, HorizontalAlign.LEFT, Text.LEADING_DEFAULT), mResourceManager.vbom);
        if(Locale.getDefault().getLanguage().equals("it")){
            rulesDescriptionText.setScale(0.52f);
        }else {
            rulesDescriptionText.setScale(0.52f);
        }
        rulesDescriptionText.setColor(Color.WHITE);
        this.attachChild(rulesDescriptionText);


    }

    public BaseScene getmRulesBoardScene() {
        return this;
    }
}
