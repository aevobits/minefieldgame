package com.aevobits.games.minesfieldgame.scene;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;

import com.aevobits.games.minesfieldgame.R;
import com.aevobits.games.minesfieldgame.ResourceManager;
import com.aevobits.games.minesfieldgame.RulesActivity;
import com.aevobits.games.minesfieldgame.Utils;
import com.aevobits.games.minesfieldgame.entity.ButtonLevel;
import com.google.android.gms.games.Games;

import org.andengine.entity.scene.CameraScene;
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

    private static final int REQUEST_THE_BEST_LEADERBOARD = 37;
    private RulesBoardScene mRulesBoardScene;
    private StatsBoardScene mStatsBoardScene;

    @Override
    public void createScene() {
        mRulesBoardScene = new RulesBoardScene();
        mStatsBoardScene = new StatsBoardScene();
        getBackground().setColor(new Color(0.109803922f, 0.717647059f, 0.921568627f));

        float pX = SCREEN_WIDTH / 2;
        float pY = SCREEN_HEIGHT - (SCREEN_HEIGHT / 3);

        Text textTitle = new Text(pX, pY + 170, mResourceManager.candy_shop_min, "Campo Minato", new TextOptions(HorizontalAlign.CENTER), mResourceManager.vbom);
        textTitle.setScale(1.9f);
        attachChild(textTitle);

        ButtonLevel buttonEasyLevel = new ButtonLevel(pX, pY, 1,330, 70, mResourceManager.buttonLevelTextureRegion, mResourceManager.vbom ){
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

        ButtonLevel buttonMediumLevel = new ButtonLevel(pX, pY, 2,330, 70, mResourceManager.buttonLevelTextureRegion, mResourceManager.vbom ){
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

        ButtonLevel buttonHardLevel = new ButtonLevel(pX, pY, 3,330, 70, mResourceManager.buttonLevelTextureRegion, mResourceManager.vbom ){
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

        ButtonLevel buttonProLevel = new ButtonLevel(pX, pY, 4,330, 70, mResourceManager.buttonLevelTextureRegion, mResourceManager.vbom ){
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
        float padding = 10;
        pX = ((SCREEN_WIDTH - ((icon_width + padding) * 5)) / 2) + (icon_width / 2);

        TiledSprite music = new TiledSprite(pX, pY, icon_width, icon_width, mResourceManager.musicTextureRegion, mResourceManager.vbom) {

            @Override
            public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
                switch (pSceneTouchEvent.getAction()) {
                    case TouchEvent.ACTION_UP: {
                        Utils.clickUpEffect(this);
                        if(mActivity.isSound()) {
                            setCurrentTileIndex(1);
                            mActivity.setSound(false);

                        }else {
                            setCurrentTileIndex(0);
                            mActivity.setSound(true);
                            mResourceManager.mActivity.playSound(mResourceManager.soundClick);
                        }
                        return true;
                    }
                    case TouchEvent.ACTION_DOWN: {
                        Utils.clickDownEffect(this);
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
                        Utils.clickUpEffect(this);
                        mResourceManager.mActivity.playSound(mResourceManager.soundClick);
                        //final Intent showTheBestIntent = Games.Leaderboards.getLeaderboardIntent(mActivity.getApiClient(), "CgkIoq7_rKsbEAIQAQ");
                        //mActivity.startActivityForResult(showTheBestIntent, REQUEST_THE_BEST_LEADERBOARD);

                        return true;
                    }
                    case TouchEvent.ACTION_DOWN: {
                        Utils.clickDownEffect(this);
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
        Sprite rewards = new Sprite(pX, pY, icon_width, icon_width, mResourceManager.rewardsTextureRegion, mResourceManager.vbom ){
            @Override
            public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
                switch (pSceneTouchEvent.getAction()) {
                    case TouchEvent.ACTION_UP: {
                        Utils.clickUpEffect(this);
                        mResourceManager.mActivity.playSound(mResourceManager.soundClick);
                        return true;
                    }
                    case TouchEvent.ACTION_DOWN: {
                        Utils.clickDownEffect(this);
                    }
                    default: {
                        return super.onAreaTouched(pSceneTouchEvent, pTouchAreaLocalX, pTouchAreaLocalY);
                    }
                }
            }
        };
        attachChild(rewards);
        registerTouchArea(rewards);

        pX = pX + icon_width + padding;
        Sprite sharing = new Sprite(pX, pY, icon_width, icon_width, mResourceManager.sharingTextureRegion, mResourceManager.vbom ){
            @Override
            public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
                switch (pSceneTouchEvent.getAction()) {
                    case TouchEvent.ACTION_UP: {
                        Utils.clickUpEffect(this);
                        mResourceManager.mActivity.playSound(mResourceManager.soundClick);
                        Intent sendIntent = new Intent();
                        sendIntent.setAction(Intent.ACTION_SEND);
                        sendIntent.putExtra(Intent.EXTRA_SUBJECT,mActivity.getString(R.string.share_extra_subject));
                        sendIntent.putExtra(Intent.EXTRA_TEXT, mActivity.getString(R.string.shate_extra_text));
                        sendIntent.setType("text/plain");
                        mActivity.startActivity(Intent.createChooser(sendIntent, mActivity.getString(R.string.share_chooser_text)));

                        return true;
                    }
                    case TouchEvent.ACTION_DOWN: {
                        Utils.clickDownEffect(this);
                    }
                    default: {
                        return super.onAreaTouched(pSceneTouchEvent, pTouchAreaLocalX, pTouchAreaLocalY);
                    }
                }
            }
        };
        attachChild(sharing);
        registerTouchArea(sharing);

        pX = pX + icon_width + padding;
        final float finalPX = pX;
        final float finalPY = pY;

        final Sprite stats = new Sprite(pX, pY, 40, 40, mResourceManager.statsTextureRegion, mResourceManager.vbom ){
            @Override
            public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
                switch (pSceneTouchEvent.getAction()) {
                    case TouchEvent.ACTION_UP: {
                        mResourceManager.mActivity.playSound(mResourceManager.soundClick);
                        setChildScene(mStatsBoardScene.getmStatsBoardScene(),false,false, true);
                        return true;
                    }
                    default: {
                        return true;
                    }
                }
            }
        };
        attachChild(stats);
        //registerTouchArea(stats);

        final Sprite rate = new Sprite(pX, pY, 40, 40, mResourceManager.rateTextureRegion, mResourceManager.vbom ){
            @Override
            public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
                switch (pSceneTouchEvent.getAction()) {
                    case TouchEvent.ACTION_UP: {
                        mResourceManager.mActivity.playSound(mResourceManager.soundClick);
                        final String appPackageName = mActivity.getPackageName(); // getPackageName() from Context or Activity  object
                        try {
                            mActivity.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
                        } catch (android.content.ActivityNotFoundException anfe) {
                            mActivity.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps?hl=it")));
                            //mActivity.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=" + appPackageName)));
                        }

                        return true;
                    }
                    default: {
                        return true;
                        //return super.onAreaTouched(pSceneTouchEvent, pTouchAreaLocalX, pTouchAreaLocalY);
                    }
                }
            }
        };
        attachChild(rate);
        //rate.setZIndex(-1);
        //registerTouchArea(rate);

        final Sprite rules = new Sprite(pX, pY, 40, 40, mResourceManager.rulesTextureRegion, mResourceManager.vbom ){
            @Override
            public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
                switch (pSceneTouchEvent.getAction()) {
                    case TouchEvent.ACTION_UP: {
                        mResourceManager.mActivity.playSound(mResourceManager.soundClick);
                        /*
                        Intent localIntent = new Intent(mActivity, RulesActivity.class);
                        mActivity.startActivity(localIntent);
                        */
                        setChildScene(mRulesBoardScene.getmRulesBoardScene(),false,false, true);
                        return true;
                    }
                    default: {
                        return true;
                        //return super.onAreaTouched(pSceneTouchEvent, pTouchAreaLocalX, pTouchAreaLocalY);
                    }
                }
            }
        };
        attachChild(rules);
        //rules.setZIndex(-1);
        //registerTouchArea(rules);


        Sprite info = new Sprite(pX, pY, icon_width, icon_width, mResourceManager.infoTextureRegion, mResourceManager.vbom ){
            boolean subMenuVisible = false;
            @Override
            public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
                switch (pSceneTouchEvent.getAction()) {
                    case TouchEvent.ACTION_UP: {
                        Utils.clickUpEffect(this);
                        mResourceManager.mActivity.playSound(mResourceManager.soundClick);
                        if (subMenuVisible){
                            Utils.fadeOutMenu(stats, finalPX, finalPY);
                            Utils.fadeOutMenu(rate, finalPX, finalPY);
                            Utils.fadeOutMenu(rules, finalPX, finalPY);
                            unregisterTouchArea(stats);
                            unregisterTouchArea(rate);
                            unregisterTouchArea(rules);
                            subMenuVisible = false;
                        }else{
                            Utils.fadeInMenu(stats, finalPX, finalPY + 55);
                            Utils.fadeInMenu(rate, finalPX + 55, finalPY);
                            Utils.fadeInMenu(rules, finalPX + 45, finalPY + 45);
                            registerTouchArea(stats);
                            registerTouchArea(rate);
                            registerTouchArea(rules);
                            subMenuVisible = true;
                        }
                        return true;
                    }
                    case TouchEvent.ACTION_DOWN: {
                        Utils.clickDownEffect(this);
                        return true;
                    }
                    default: {
                        return true;
                        //return super.onAreaTouched(pSceneTouchEvent, pTouchAreaLocalX, pTouchAreaLocalY);
                    }
                }
            }
        };
        attachChild(info);
        //info.setZIndex(2);
        registerTouchArea(info);
        info.
        sortChildren();

        fadeIn();

    }

    @Override
    public void onBackKeyPressed() {}

    @Override
    public SceneManager.SceneType getSceneType() {
        return SceneManager.SceneType.SCENE_MENU;
    }

    @Override
    public void disposeScene() {}
}
