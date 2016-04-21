package com.aevobits.games.minesfieldgame.scene;

import android.content.Intent;

import com.aevobits.games.minesfieldgame.R;
import com.aevobits.games.minesfieldgame.ResourceManager;
import com.aevobits.games.minesfieldgame.entity.ButtonLevel;
import com.google.android.gms.games.Games;

import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.sprite.TiledSprite;
import org.andengine.entity.text.Text;
import org.andengine.entity.text.TextOptions;
import org.andengine.input.touch.TouchEvent;
import org.andengine.util.adt.align.HorizontalAlign;
import org.andengine.util.adt.color.Color;

/**
 * Created by vito on 09/03/16.
 */
public class MainMenuScene extends BaseScene {

    private PlayServicesManager playServicesManager;

    private static final int REQUEST_THE_BEST_LEADERBOARD = 37;

    @Override
    public void createScene() {
        getBackground().setColor(new Color(0.109803922f, 0.717647059f, 0.921568627f));

        float pX = SCREEN_WIDTH / 2;
        float pY = SCREEN_HEIGHT - (SCREEN_HEIGHT / 3);

        Text textTitle = new Text(pX, pY + 170, mResourceManager.candy_shop_min, "Campo Minato", new TextOptions(HorizontalAlign.CENTER), mResourceManager.vbom);
        textTitle.setScale(1.9f);
        attachChild(textTitle);

        ButtonLevel buttonEasyLevel = new ButtonLevel(pX, pY, 1,330, 70, mResourceManager.buttonEasyLevelTextureRegion, mResourceManager.vbom ){
            @Override
            public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
                switch (pSceneTouchEvent.getAction()) {
                    case TouchEvent.ACTION_UP: {
                        mResourceManager.unloadMainManuResources();
                        mResourceManager.loadGameResources();
                        MapManager.getInstance().level = this.getLevel();
                        mSceneManager.setScene(SceneManager.SceneType.SCENE_GAME);
                        return true;
                    }
                    default:{
                        return false;
                    }
                }

            }
        };

        attachChild(buttonEasyLevel);
        registerTouchArea(buttonEasyLevel);

        Text textLevel1 = new Text(pX, pY, mResourceManager.candy_shop_min, "easy", new TextOptions(HorizontalAlign.CENTER), mResourceManager.vbom);
        textLevel1.setScale(1.35f);
        attachChild(textLevel1);

        pY = pY - 80;

        ButtonLevel buttonMediumLevel = new ButtonLevel(pX, pY, 2,330, 70, mResourceManager.buttonMediumLevelTextureRegion, mResourceManager.vbom ){
            @Override
            public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
                switch (pSceneTouchEvent.getAction()) {
                    case TouchEvent.ACTION_UP: {
                        mResourceManager.unloadMainManuResources();
                        mResourceManager.loadGameResources();
                        MapManager.getInstance().level = this.getLevel();
                        mSceneManager.setScene(SceneManager.SceneType.SCENE_GAME);
                        return true;
                    }
                    default:{
                        return false;
                    }
                }
            }
        };

        attachChild(buttonMediumLevel);
        registerTouchArea(buttonMediumLevel);

        Text textLevel2 = new Text(pX, pY, mResourceManager.candy_shop_min, "medium", new TextOptions(HorizontalAlign.CENTER), mResourceManager.vbom);
        textLevel2.setScale(1.35f);
        attachChild(textLevel2);

        pY = pY - 80;

        ButtonLevel buttonHardLevel = new ButtonLevel(pX, pY, 3,330, 70, mResourceManager.buttonHardLevelTextureRegion, mResourceManager.vbom ){
            @Override
            public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
                switch (pSceneTouchEvent.getAction()) {
                    case TouchEvent.ACTION_UP: {
                        mResourceManager.unloadMainManuResources();
                        mResourceManager.loadGameResources();
                        MapManager.getInstance().level = this.getLevel();
                        mSceneManager.setScene(SceneManager.SceneType.SCENE_GAME);
                        return true;
                    }
                    default:{
                        return false;
                    }
                }
            }
        };

        attachChild(buttonHardLevel);
        registerTouchArea(buttonHardLevel);

        Text textLevel3 = new Text(pX, pY, mResourceManager.candy_shop_min, "hard", new TextOptions(HorizontalAlign.CENTER), mResourceManager.vbom);
        textLevel3.setScale(1.35f);
        attachChild(textLevel3);


        pY = pY - 80;

        ButtonLevel buttonProLevel = new ButtonLevel(pX, pY, 4,330, 70, mResourceManager.buttonProLevelTextureRegion, mResourceManager.vbom ){
            @Override
            public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
                switch (pSceneTouchEvent.getAction()) {
                    case TouchEvent.ACTION_UP: {
                        mResourceManager.unloadMainManuResources();
                        mResourceManager.loadGameResources();
                        MapManager.getInstance().level = this.getLevel();
                        mSceneManager.setScene(SceneManager.SceneType.SCENE_GAME);
                        return true;
                    }
                    default:{
                        return false;
                    }
                }
            }
        };

        attachChild(buttonProLevel);
        registerTouchArea(buttonProLevel);

        Text textLevel4 = new Text(pX, pY, mResourceManager.candy_shop_min, "pro", new TextOptions(HorizontalAlign.CENTER), mResourceManager.vbom);
        textLevel4.setScale(1.35f);
        attachChild(textLevel4);

        pY = 100;
        float icon_width = 64;
        float padding = 30;
        pX = ((SCREEN_WIDTH - ((icon_width + padding) * 3)) / 2) + (icon_width / 2);

        TiledSprite music = new TiledSprite(pX, pY, icon_width, icon_width, mResourceManager.musicTextureRegion, mResourceManager.vbom) {

            @Override
            public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
                switch (pSceneTouchEvent.getAction()) {
                    case TouchEvent.ACTION_UP: {
                        if(mActivity.isSound()) {
                            setCurrentTileIndex(1);
                            mActivity.setSound(false);

                        }else {
                            setCurrentTileIndex(0);
                            mActivity.setSound(true);

                        }
                        return true;
                    }
                    default: {
                        return super.onAreaTouched(pSceneTouchEvent, pTouchAreaLocalX, pTouchAreaLocalY);
                    }

                }


            }
        };
        if(mActivity.isSound()) {
            music.setCurrentTileIndex(0);
        }else {
            music.setCurrentTileIndex(1);
        }
        attachChild(music);
        registerTouchArea(music);

        pX = pX + icon_width + padding;
        Sprite ranking = new Sprite(pX, pY, icon_width, icon_width, mResourceManager.rankingTextureRegion, mResourceManager.vbom ){
            @Override
            public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
                switch (pSceneTouchEvent.getAction()) {
                    case TouchEvent.ACTION_UP: {
                        //final Intent showTheBestIntent = Games.Leaderboards.getLeaderboardIntent(mActivity.getApiClient(), "CgkIoq7_rKsbEAIQAQ");
                        //mActivity.startActivityForResult(showTheBestIntent, REQUEST_THE_BEST_LEADERBOARD);

                        return true;
                    }
                    default: {
                        return super.onAreaTouched(pSceneTouchEvent, pTouchAreaLocalX, pTouchAreaLocalY);
                    }
                }
            }
        };
        attachChild(ranking);
        registerTouchArea(ranking);

        pX = pX + icon_width + padding;
        Sprite rewards = new Sprite(pX, pY, icon_width, icon_width, mResourceManager.rewardsTextureRegion, mResourceManager.vbom );
        attachChild(rewards);
        registerTouchArea(rewards);

    }

    @Override
    public void onBackKeyPressed() {

    }

    @Override
    public SceneManager.SceneType getSceneType() {
        return SceneManager.SceneType.SCENE_MENU;
    }

    @Override
    public void disposeScene() {

    }
}
