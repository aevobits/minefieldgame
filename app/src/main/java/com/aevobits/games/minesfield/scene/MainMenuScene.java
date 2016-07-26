package com.aevobits.games.minesfield.scene;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;

import com.aevobits.games.minesfield.R;
import com.aevobits.games.minesfield.util.Utils;
import com.aevobits.games.minesfield.entity.ButtonLevel;
import com.google.android.gms.appinvite.AppInviteInvitation;
import com.google.android.gms.games.Games;
import com.google.android.gms.games.multiplayer.realtime.RoomConfig;

import org.andengine.engine.camera.ZoomCamera;
import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.IEntityModifier;
import org.andengine.entity.primitive.Gradient;
import org.andengine.entity.scene.background.EntityBackground;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.sprite.TiledSprite;
import org.andengine.entity.text.Text;
import org.andengine.entity.text.TextOptions;
import org.andengine.input.touch.TouchEvent;
import org.andengine.util.adt.align.HorizontalAlign;
import org.andengine.util.adt.color.Color;
import org.andengine.util.modifier.IModifier;

import java.util.Locale;

/**
 * Created by vito on 09/03/16.
 */
public class MainMenuScene extends BaseScene {

    private static final int REQUEST_THE_BEST_LEADERBOARD = 100;
    private static final int REQUEST_ALL_ACHIEVEMENT = 11;
    private static final int REQUEST_INVITE = 0;
    private RulesBoardScene mRulesBoardScene;

    @Override
    public void createScene() {

        //mActivity.getApiClient().disconnect();
        //mActivity.getApiClient().connect();
        //Person currentPerson = Plus.PeopleApi.getCurrentPerson(mActivity.getApiClient());
        //String personName = currentPerson.getDisplayName();
        //Log.i("MainMenuScene",personName);
        Gradient g = new Gradient(SCREEN_WIDTH / 2, SCREEN_HEIGHT / 2, SCREEN_WIDTH, SCREEN_HEIGHT, mResourceManager.vbom);
        g.setGradient(new Color(0.68627451f, 0.866666667f, 0.91372549f), new Color(0.109803922f, 0.717647059f, 0.921568627f), 0, 1);
        this.setBackground(new EntityBackground(g));
        g.setIgnoreUpdate(true);

        float pX = SCREEN_WIDTH / 2;
        float pY = (SCREEN_HEIGHT - (SCREEN_HEIGHT / 3));

        drawLogo(pX, pY);

        //pY+=30f;

        ButtonLevel playButton = new ButtonLevel(pX, pY, 1,394, 100, mResourceManager.playLevelTextureRegion, mResourceManager.vbom ){
            @Override
            public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
                switch (pSceneTouchEvent.getAction()) {
                    case TouchEvent.ACTION_UP: {
                        MapManager.getInstance().level = this.getLevel();
                        IEntityModifier iem = Utils.clickEffect(this, 0.9f);
                        iem.addModifierListener(new IEntityModifier.IEntityModifierListener() {
                            @Override
                            public void onModifierStarted(IModifier<IEntity> pModifier, IEntity pItem) {}

                            @Override
                            public void onModifierFinished(IModifier<IEntity> pModifier, IEntity pItem) {
                                mSceneManager.setScene(SceneManager.SceneType.SCENE_LEVEL_MENU);
                            }
                        });

                        return true;
                    }
                    case TouchEvent.ACTION_DOWN: {
                        mResourceManager.mActivity.playSound(mResourceManager.soundClick);
                    }
                    default:{
                        return false;
                    }
                }

            }
        };
        playButton.setScale(0.9f);
        attachChild(playButton);
        registerTouchArea(playButton);

        String playTitle = mResourceManager.mActivity.getString(R.string.play);
        Text textLevel1 = new Text(pX + 25, pY - 5f, mResourceManager.bebasneue, playTitle, new TextOptions(HorizontalAlign.CENTER), mResourceManager.vbom);
        textLevel1.setScale(1.4f);
        attachChild(textLevel1);

        pY = pY - 100;

        Sprite rulesButton = new Sprite(pX, pY, 394, 100, mResourceManager.rulesTextureRegion, mResourceManager.vbom ){
            @Override
            public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
                switch (pSceneTouchEvent.getAction()) {
                    case TouchEvent.ACTION_UP: {
                        IEntityModifier iem = Utils.clickEffect(this, 0.9f);
                        iem.addModifierListener(new IEntityModifier.IEntityModifierListener() {
                            @Override
                            public void onModifierStarted(IModifier<IEntity> pModifier, IEntity pItem) {}

                            @Override
                            public void onModifierFinished(IModifier<IEntity> pModifier, IEntity pItem) {
                                mRulesBoardScene = new RulesBoardScene();
                                setChildScene(mRulesBoardScene.getmRulesBoardScene(),false,false, true);
                            }
                        });

                        return true;
                    }
                    case TouchEvent.ACTION_DOWN: {
                        mResourceManager.mActivity.playSound(mResourceManager.soundClick);
                    }
                    default:{
                        return false;
                    }
                }
            }
        };
        rulesButton.setScale(0.9f);
        attachChild(rulesButton);
        registerTouchArea(rulesButton);

        String rulesTitle = mResourceManager.mActivity.getString(R.string.rules);
        Text rulesText = new Text(pX + 25f, pY - 5f, mResourceManager.bebasneue, rulesTitle, new TextOptions(HorizontalAlign.CENTER), mResourceManager.vbom);
        if(Locale.getDefault().getLanguage().equals("it")) {
            rulesText.setScale(1.2f);
        }else{
            rulesText.setScale(1.3f);
        }
        attachChild(rulesText);

        pY = pY - 100;

        Sprite rateButton = new Sprite(pX, pY, 394, 100, mResourceManager.rateTextureRegion, mResourceManager.vbom ){
            @Override
            public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
                switch (pSceneTouchEvent.getAction()) {
                    case TouchEvent.ACTION_UP: {
                        IEntityModifier iem = Utils.clickEffect(this, 0.9f);
                        iem.addModifierListener(new IEntityModifier.IEntityModifierListener() {
                            @Override
                            public void onModifierStarted(IModifier<IEntity> pModifier, IEntity pItem) {}

                            @Override
                            public void onModifierFinished(IModifier<IEntity> pModifier, IEntity pItem) {
                                final String appPackageName = mActivity.getPackageName();
                                try {
                                    mActivity.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
                                } catch (android.content.ActivityNotFoundException anfe) {
                                    mActivity.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
                                }
                            }
                        });

                        return true;
                    }
                    case TouchEvent.ACTION_DOWN: {
                        mResourceManager.mActivity.playSound(mResourceManager.soundClick);
                    }
                    default:{
                        return false;
                    }
                }
            }
        };
        rateButton.setScale(0.9f);
        attachChild(rateButton);
        registerTouchArea(rateButton);

        String rateTitle = mResourceManager.mActivity.getString(R.string.rate);
        Text rateText = new Text(pX + 37f, pY - 5f, mResourceManager.bebasneue, rateTitle, new TextOptions(HorizontalAlign.CENTER), mResourceManager.vbom);
        if(Locale.getDefault().getLanguage().equals("it")) {
            rateText.setScale(1.1f);
        }else{
            rateText.setScale(1.2f);
        }
        attachChild(rateText);

        pY = 110;
        float icon_width = 80;
        float padding = 10;
        pX = ((SCREEN_WIDTH - ((icon_width + padding) * 4)) / 2) + (icon_width / 2);

        TiledSprite music = new TiledSprite(pX, pY, icon_width, icon_width, mResourceManager.musicTextureRegion, mResourceManager.vbom) {

            @Override
            public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
                switch (pSceneTouchEvent.getAction()) {
                    case TouchEvent.ACTION_UP: {
                        IEntityModifier iem = Utils.clickEffect(this);
                        iem.addModifierListener(new IEntityModifier.IEntityModifierListener() {
                            @Override
                            public void onModifierStarted(IModifier<IEntity> pModifier, IEntity pItem) {}

                            @Override
                            public void onModifierFinished(IModifier<IEntity> pModifier, IEntity pItem) {
                                if(mActivity.isSound()) {
                                    setCurrentTileIndex(1);
                                    mActivity.setSound(false);

                                }else {
                                    setCurrentTileIndex(0);
                                    mActivity.setSound(true);
                                    mResourceManager.mActivity.playSound(mResourceManager.soundClick);
                                }
                            }
                        });

                        return true;
                    }
                    case TouchEvent.ACTION_DOWN: {

                    }
                    default:{
                        return false;
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
                        IEntityModifier iem = Utils.clickEffect(this);
                        iem.addModifierListener(new IEntityModifier.IEntityModifierListener() {
                            @Override
                            public void onModifierStarted(IModifier<IEntity> pModifier, IEntity pItem) {}

                            @Override
                            public void onModifierFinished(IModifier<IEntity> pModifier, IEntity pItem) {
                                if(mActivity.getGameHelper().isSignedIn()) {
                                    int leaderboard_id = mActivity.getResources().getIdentifier("leaderboard2","string",
                                            mActivity.getPackageName());
                                    String leaderboard_string = mActivity.getString(leaderboard_id);
                                    Intent showTheBestIntent = Games.Leaderboards.getLeaderboardIntent(mActivity.getApiClient(), leaderboard_string);
                                    mActivity.startActivityForResult(showTheBestIntent, REQUEST_THE_BEST_LEADERBOARD);
                                }else {
                                    mActivity.runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            CharSequence text = mActivity.getString(R.string.gamehelper_sign_in_failed);
                                            int duration = Toast.LENGTH_LONG;
                                            Toast.makeText(mActivity.getApplicationContext(), text, duration).show();
                                        }
                                    });
                                }

                            }
                        });

                        return true;
                    }
                    case TouchEvent.ACTION_DOWN: {
                        mResourceManager.mActivity.playSound(mResourceManager.soundClick);
                    }
                    default:{
                        return false;
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
                        IEntityModifier iem = Utils.clickEffect(this);
                        iem.addModifierListener(new IEntityModifier.IEntityModifierListener() {
                            @Override
                            public void onModifierStarted(IModifier<IEntity> pModifier, IEntity pItem) {}

                            @Override
                            public void onModifierFinished(IModifier<IEntity> pModifier, IEntity pItem) {
                                if(mActivity.getGameHelper().isSignedIn()) {
                                    final Intent showAchievementIntent = Games.Achievements.getAchievementsIntent(mActivity.getApiClient());
                                    mActivity.startActivityForResult(showAchievementIntent, REQUEST_ALL_ACHIEVEMENT);
                                }else {
                                    mActivity.runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            CharSequence text = mActivity.getString(R.string.gamehelper_sign_in_failed);
                                            int duration = Toast.LENGTH_LONG;
                                            Toast.makeText(mActivity.getApplicationContext(), text, duration).show();
                                        }
                                    });
                                }
                            }
                        });

                        return true;
                    }
                    case TouchEvent.ACTION_DOWN: {
                        mResourceManager.mActivity.playSound(mResourceManager.soundClick);
                    }
                    default:{
                        return false;
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
                        IEntityModifier iem = Utils.clickEffect(this);
                        iem.addModifierListener(new IEntityModifier.IEntityModifierListener() {
                            @Override
                            public void onModifierStarted(IModifier<IEntity> pModifier, IEntity pItem) {}

                            @Override
                            public void onModifierFinished(IModifier<IEntity> pModifier, IEntity pItem) {
                                /*
                                Intent sendIntent = new Intent();
                                sendIntent.setAction(Intent.ACTION_SEND);
                                sendIntent.putExtra(Intent.EXTRA_SUBJECT,mActivity.getString(R.string.share_extra_subject));
                                sendIntent.putExtra(Intent.EXTRA_TEXT, mActivity.getString(R.string.shate_extra_text));
                                sendIntent.setType("text/plain");
                                mActivity.startActivity(Intent.createChooser(sendIntent, mActivity.getString(R.string.share_chooser_text)));
                                */
                                Intent intent = new AppInviteInvitation.IntentBuilder(mActivity.getString(R.string.invitation_title))
                                        .setMessage(mActivity.getString(R.string.invitation_message))
                                        .setDeepLink(Uri.parse(mActivity.getString(R.string.invitation_deep_link)))
                                        .setCustomImage(Uri.parse(mActivity.getString(R.string.invitation_custom_image)))
                                        .setCallToActionText(mActivity.getString(R.string.invitation_cta))
                                        .build();
                                mActivity.startActivityForResult(intent, REQUEST_INVITE);
                            }
                        });

                        return true;
                    }
                    case TouchEvent.ACTION_DOWN: {
                        mResourceManager.mActivity.playSound(mResourceManager.soundClick);
                    }
                    default:{
                        return false;
                    }
                }
            }
        };
        attachChild(sharing);
        registerTouchArea(sharing);

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

    private void startQuickGame() {
        // quick-start a game with 1 randomly selected opponent
        final int MIN_OPPONENTS = 1, MAX_OPPONENTS = 1;
        Bundle autoMatchCriteria = RoomConfig.createAutoMatchCriteria(MIN_OPPONENTS,
                MAX_OPPONENTS, 0);
        /*
        RoomConfig.Builder rtmConfigBuilder = RoomConfig.builder(mActivity);
        rtmConfigBuilder.setMessageReceivedListener(mActivity);
        rtmConfigBuilder.setRoomStatusUpdateListener(mActivity);
        rtmConfigBuilder.setAutoMatchCriteria(autoMatchCriteria);
        //switchToScreen(R.id.screen_wait);
        Utils.keepScreenOn();
        //resetGameVars();
        Games.RealTimeMultiplayer.create(mActivity.getApiClient(), rtmConfigBuilder.build());
       */
    }
}
