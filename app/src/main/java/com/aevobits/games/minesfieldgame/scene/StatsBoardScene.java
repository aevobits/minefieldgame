package com.aevobits.games.minesfieldgame.scene;

import com.aevobits.games.minesfieldgame.GameActivity;
import com.aevobits.games.minesfieldgame.R;
import com.aevobits.games.minesfieldgame.ResourceManager;

import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.entity.text.TextOptions;
import org.andengine.input.touch.TouchEvent;
import org.andengine.util.adt.align.HorizontalAlign;
import org.andengine.util.adt.color.Color;

/**
 * Created by vito on 01/06/16.
 */
public class StatsBoardScene extends BaseScene{

    private ResourceManager mResourceManager;
    private SceneManager mSceneManager;

    public StatsBoardScene(){
        this.mResourceManager = ResourceManager.getInstance();
        this.mSceneManager = SceneManager.getInstance();
        createScene();
    }

    public void createScene(){
        final float overX = GameActivity.CAMERA_WIDTH / 2;
        final float overY = GameActivity.CAMERA_HEIGHT / 2;

        this.setBackgroundEnabled(false);
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
        this.registerTouchArea(backgroundGameOver);
        this.attachChild(backgroundGameOver);

        String statistics = mResourceManager.mActivity.getString(R.string.statistics);
        Text statisticsText = new Text(GameActivity.CAMERA_WIDTH / 2,GameActivity.CAMERA_HEIGHT - 50f, mResourceManager.montserrat, statistics,
                new TextOptions(HorizontalAlign.CENTER), mResourceManager.vbom);
        statisticsText.setScale(1.5f);
        statisticsText.setColor(Color.WHITE);
        this.attachChild(statisticsText);

        final Sprite statsBoardSprite = new Sprite(overX, overY,430, 560, mResourceManager.statsBoardTextureRegion, mResourceManager.vbom);
        this.attachChild(statsBoardSprite);

        // Easy Stats

        String easyTitle = mResourceManager.mActivity.getString(R.string.easy);
        Text easyTitleText = new Text(130f,640f, mResourceManager.montserrat, easyTitle, new TextOptions(HorizontalAlign.CENTER), mResourceManager.vbom);
        this.attachChild(easyTitleText);

        String easyGamesPlayedCount = String.valueOf(mActivity.getGamesPlayed(1));
        String easyGamesPlayedTitle = mActivity.getString(R.string.gamePlayed);
        Text easyGamesPlayedText = new Text(130f,580f, mResourceManager.montserrat, easyGamesPlayedTitle + " " + easyGamesPlayedCount, new TextOptions(HorizontalAlign.CENTER), mResourceManager.vbom);
        easyGamesPlayedText.setScale(0.6f);
        this.attachChild(easyGamesPlayedText);

        String easyGamesWonCount = String.valueOf(mActivity.getGamesWon(1));
        String easyGamesWonTitle = mActivity.getString(R.string.gameWon);
        Text easyGamesWonText = new Text(130f,550f, mResourceManager.montserrat, easyGamesWonTitle + " " + easyGamesWonCount, new TextOptions(HorizontalAlign.CENTER), mResourceManager.vbom);
        easyGamesWonText.setScale(0.6f);
        this.attachChild(easyGamesWonText);

        String easyPercentageWonCount = "0";
        if (mActivity.getGamesWon(1) != 0)
            easyPercentageWonCount = String.format(mResourceManager.locale, "%.2f",(((float)mActivity.getGamesWon(1) * 100)/ (float)mActivity.getGamesPlayed(1)));
        String easyPercentageWonTitle = mActivity.getString(R.string.percentageWon);
        Text easyPercentageWonText = new Text(130f,520f, mResourceManager.montserrat, easyPercentageWonTitle + " " + easyPercentageWonCount, new TextOptions(HorizontalAlign.CENTER), mResourceManager.vbom);
        easyPercentageWonText.setScale(0.6f);
        this.attachChild(easyPercentageWonText);

        String easyHiScore = "0";
        if (mActivity.getHiscore(1) != 0)
            easyHiScore = String.format(mResourceManager.locale, "%.2f",mActivity.getHiscore(1));
        String easyHiScoreTitle = mActivity.getString(R.string.hiScore);
        Text easyHiScoreText = new Text(130f,490f, mResourceManager.montserrat, easyHiScoreTitle + " " + easyHiScore, new TextOptions(HorizontalAlign.CENTER), mResourceManager.vbom);
        easyHiScoreText.setScale(0.6f);
        this.attachChild(easyHiScoreText);


        // Intermediate Stats

        String intermediateTitle = mResourceManager.mActivity.getString(R.string.intermediate);
        Text intermediateTitleText = new Text(345f,640f, mResourceManager.montserrat, intermediateTitle, new TextOptions(HorizontalAlign.CENTER), mResourceManager.vbom);
        intermediateTitleText.setScale(0.9f);
        this.attachChild(intermediateTitleText);

        String intermediateGamesPlayedCount = String.valueOf(mActivity.getGamesPlayed(2));
        String intermediateGamesPlayedTitle = mActivity.getString(R.string.gamePlayed);
        Text intermediateGamesPlayedText = new Text(345f,580f, mResourceManager.montserrat, intermediateGamesPlayedTitle + " " + intermediateGamesPlayedCount, new TextOptions(HorizontalAlign.CENTER), mResourceManager.vbom);
        intermediateGamesPlayedText.setScale(0.6f);
        this.attachChild(intermediateGamesPlayedText);

        String intermediateGamesWonCount = String.valueOf(mActivity.getGamesWon(2));
        String intermediateGamesWonTitle = mActivity.getString(R.string.gameWon);
        Text intermediateGamesWonText = new Text(345f,550f, mResourceManager.montserrat, intermediateGamesWonTitle + " " + intermediateGamesWonCount, new TextOptions(HorizontalAlign.CENTER), mResourceManager.vbom);
        intermediateGamesWonText.setScale(0.6f);
        this.attachChild(intermediateGamesWonText);

        String intermediatePercentageWonCount = "0";
        if (mActivity.getGamesWon(2) != 0)
            intermediatePercentageWonCount = String.format(mResourceManager.locale, "%.2f",(((float)mActivity.getGamesWon(2) * 100)/ (float)mActivity.getGamesPlayed(2)));
        String intermediatePercentageWonTitle = mActivity.getString(R.string.percentageWon);
        Text intermediatePercentageWonText = new Text(345f,520f, mResourceManager.montserrat, intermediatePercentageWonTitle + " " + intermediatePercentageWonCount, new TextOptions(HorizontalAlign.CENTER), mResourceManager.vbom);
        intermediatePercentageWonText.setScale(0.6f);
        this.attachChild(intermediatePercentageWonText);

        String intermediateHiScore = "0";
        if (mActivity.getHiscore(2) != 0)
            intermediateHiScore = String.format(mResourceManager.locale, "%.2f",mActivity.getHiscore(2));
        String intermediateHiScoreTitle = mActivity.getString(R.string.hiScore);
        Text intermediateHiScoreText = new Text(345f,490f, mResourceManager.montserrat, intermediateHiScoreTitle + " " + intermediateHiScore, new TextOptions(HorizontalAlign.CENTER), mResourceManager.vbom);
        intermediateHiScoreText.setScale(0.6f);
        this.attachChild(intermediateHiScoreText);


        // Hard Stats

        String hardTitle = mResourceManager.mActivity.getString(R.string.hard);
        Text hardTitleText = new Text(130f,355f, mResourceManager.montserrat, hardTitle, new TextOptions(HorizontalAlign.CENTER), mResourceManager.vbom);
        hardTitleText.setColor(Color.WHITE);
        this.attachChild(hardTitleText);

        String hardGamesPlayedCount = String.valueOf(mActivity.getGamesPlayed(3));
        String hardGamesPlayedTitle = mActivity.getString(R.string.gamePlayed);
        Text hardGamesPlayedText = new Text(130f,295f, mResourceManager.montserrat, hardGamesPlayedTitle + " " + hardGamesPlayedCount, new TextOptions(HorizontalAlign.CENTER), mResourceManager.vbom);
        hardGamesPlayedText.setScale(0.6f);
        this.attachChild(hardGamesPlayedText);

        String hardGamesWonCount = String.valueOf(mActivity.getGamesWon(3));
        String hardGamesWonTitle = mActivity.getString(R.string.gameWon);
        Text hardGamesWonText = new Text(130f,265f, mResourceManager.montserrat, hardGamesWonTitle + " " + hardGamesWonCount, new TextOptions(HorizontalAlign.CENTER), mResourceManager.vbom);
        hardGamesWonText.setScale(0.6f);
        this.attachChild(hardGamesWonText);

        String hardPercentageWonCount = "0";
        if (mActivity.getGamesWon(3) != 0)
            hardPercentageWonCount = String.format(mResourceManager.locale, "%.2f",(((float)mActivity.getGamesWon(3) * 100)/ (float)mActivity.getGamesPlayed(3)));
        String hardPercentageWonTitle = mActivity.getString(R.string.percentageWon);
        Text hardPercentageWonText = new Text(130f,235f, mResourceManager.montserrat, hardPercentageWonTitle + " " + hardPercentageWonCount, new TextOptions(HorizontalAlign.CENTER), mResourceManager.vbom);
        hardPercentageWonText.setScale(0.6f);
        this.attachChild(hardPercentageWonText);

        String hardHiScore = "0";
        if (mActivity.getHiscore(3) != 0)
            hardHiScore = String.format(mResourceManager.locale, "%.2f",mActivity.getHiscore(3));
        String hardHiScoreTitle = mActivity.getString(R.string.hiScore);
        Text hardHiScoreText = new Text(130f,205f, mResourceManager.montserrat, hardHiScoreTitle + " " + hardHiScore, new TextOptions(HorizontalAlign.CENTER), mResourceManager.vbom);
        hardHiScoreText.setScale(0.6f);
        this.attachChild(hardHiScoreText);


        // Pro Stats

        String proTitle = mResourceManager.mActivity.getString(R.string.pro);
        Text proTitleText = new Text(345f,355f, mResourceManager.montserrat, proTitle, new TextOptions(HorizontalAlign.CENTER), mResourceManager.vbom);
        proTitleText.setColor(Color.WHITE);
        this.attachChild(proTitleText);

        String proGamesPlayedCount = String.valueOf(mActivity.getGamesPlayed(4));
        String proGamesPlayedTitle = mActivity.getString(R.string.gamePlayed);
        Text proGamesPlayedText = new Text(345f,295f, mResourceManager.montserrat, proGamesPlayedTitle + " " + proGamesPlayedCount, new TextOptions(HorizontalAlign.CENTER), mResourceManager.vbom);
        proGamesPlayedText.setScale(0.6f);
        this.attachChild(proGamesPlayedText);

        String proGamesWonCount = String.valueOf(mActivity.getGamesWon(4));
        String proGamesWonTitle = mActivity.getString(R.string.gameWon);
        Text proGamesWonText = new Text(345f,265f, mResourceManager.montserrat, proGamesWonTitle + " " + proGamesWonCount, new TextOptions(HorizontalAlign.CENTER), mResourceManager.vbom);
        proGamesWonText.setScale(0.6f);
        this.attachChild(proGamesWonText);

        String proPercentageWonCount = "0";
        if (mActivity.getGamesWon(4) != 0)
            proPercentageWonCount = String.format(mResourceManager.locale, "%.2f",(((float)mActivity.getGamesWon(4) * 100)/ (float)mActivity.getGamesPlayed(4)));
        String proPercentageWonTitle = mActivity.getString(R.string.percentageWon);
        Text proPercentageWonText = new Text(345f,235f, mResourceManager.montserrat, proPercentageWonTitle + " " + proPercentageWonCount, new TextOptions(HorizontalAlign.CENTER), mResourceManager.vbom);
        proPercentageWonText.setScale(0.6f);
        this.attachChild(proPercentageWonText);

        String proHiScore = "0";
        if (mActivity.getHiscore(4) != 0)
            proHiScore = String.format(mResourceManager.locale, "%.2f",mActivity.getHiscore(4));
        String proHiScoreTitle = mActivity.getString(R.string.hiScore);
        Text proHiScoreText = new Text(345f,205f, mResourceManager.montserrat, proHiScoreTitle + " " + proHiScore, new TextOptions(HorizontalAlign.CENTER), mResourceManager.vbom);
        proHiScoreText.setScale(0.6f);
        this.attachChild(proHiScoreText);

    }

    public BaseScene getmStatsBoardScene() {
        return this;
    }

    @Override
    public void onBackKeyPressed() {

    }

    @Override
    public SceneManager.SceneType getSceneType() {
        return SceneManager.SceneType.SCENE_STATS_BOARD;
    }

    @Override
    public void disposeScene() {

    }
}
