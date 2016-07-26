package com.aevobits.games.minesfield.util;

import android.view.WindowManager;

import com.aevobits.games.minesfield.GameActivity;
import com.aevobits.games.minesfield.manager.ResourceManager;

import org.andengine.entity.Entity;
import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.DelayModifier;
import org.andengine.entity.modifier.FadeInModifier;
import org.andengine.entity.modifier.IEntityModifier;
import org.andengine.entity.modifier.MoveModifier;
import org.andengine.entity.modifier.MoveYModifier;
import org.andengine.entity.modifier.ParallelEntityModifier;
import org.andengine.entity.modifier.ScaleModifier;
import org.andengine.entity.modifier.SequenceEntityModifier;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.util.modifier.ease.EaseBackInOut;

import java.util.List;

/**
 * Created by vito on 08/03/16.
 */
public class Utils {

    public static String secondsToString(int count){
        int minute = count/60;
        int seconds = count%60;
        String mnStr = "" + minute;
        String secStr = (seconds<10 ? "0" : "")+seconds;

        return mnStr + ":" + secStr;
    }

    public static <T extends Entity> void fadeInMenu(T entity, float newX, float newY){
        float pX = entity.getX();
        float pY = entity.getY();
        IEntityModifier iem = new ParallelEntityModifier(new ScaleModifier(0.2f,0f,1f, 0f, 1f),
                                                        new MoveModifier(0.2f, pX, pY, newX, newY));
        iem.setAutoUnregisterWhenFinished(true);
        entity.registerEntityModifier(iem);
    }

    public static <T extends Entity> void fadeOutMenu(T entity, float newX, float newY){
        float pX = entity.getX();
        float pY = entity.getY();
        IEntityModifier iem = new ParallelEntityModifier(new ScaleModifier(0.2f, 1f, 0f, 1f, 0f),
                                                        new MoveModifier(0.2f, pX, pY, newX, newY));
        iem.setAutoUnregisterWhenFinished(true);
        entity.registerEntityModifier(iem);
    }

    public static <T extends Scene> void fadeInChildren(T scene){
        for(int i=0; i<scene.getChildCount();i++){
            scene.getChildByIndex(i).setAlpha(0f);
            IEntityModifier iem = new ParallelEntityModifier(new FadeInModifier(0.2f));
            iem.setAutoUnregisterWhenFinished(true);
            scene.getChildByIndex(i).registerEntityModifier(iem);
        }

    }

    public static <T extends Entity> void fadeInEntityList(List<T> entityList){
        for(Entity entity:entityList){
            entity.setAlpha(0f);
            float scaleX = entity.getScaleX();
            float scaleY = entity.getScaleY();
            IEntityModifier iem = new ParallelEntityModifier(new FadeInModifier(0.2f),
                                                            new ScaleModifier(0.2f,0f,scaleX, 0f, scaleY));
            iem.setAutoUnregisterWhenFinished(true);
            entity.registerEntityModifier(iem);
        }
    }

    public static <T extends Entity> IEntityModifier clickDownEffect(T entity){
        IEntityModifier iem = new ScaleModifier(0.2f, 1f, 1.1f);
        iem.setAutoUnregisterWhenFinished(true);
        entity.registerEntityModifier(iem);
        return iem;
    }

    public static <T extends Entity> IEntityModifier clickEffect(T entity){
        IEntityModifier iem = new SequenceEntityModifier(
                                new ScaleModifier(0.1f, 1f, 1.1f),
                                new ScaleModifier(0.1f, 1.1f, 1f));
        iem.setAutoUnregisterWhenFinished(true);
        entity.registerEntityModifier(iem);
        return iem;
    }

    public static <T extends Entity> IEntityModifier clickEffect(T entity, float scale){
        IEntityModifier iem = new SequenceEntityModifier(
                new ScaleModifier(0.1f, 1f, 1.1f),
                new ScaleModifier(0.1f, 1f, scale));
        iem.setAutoUnregisterWhenFinished(true);
        entity.registerEntityModifier(iem);
        return iem;
    }

    public static <T extends Entity> void gameOverEffect(Sprite yes, Text yesText, Sprite no, Text noText, Sprite question,
                                                         Text goTextQuestion, Sprite questionText, Text goTextWonLose, Text goTextScore,
                                                         Text goTextTitleScore, Text goLoseTextScore){

        float fromYYes = yes.getY();
        float fromXYes = yes.getX();
        float toYYes = (yes.getHeight() / 2) + 70f;
        float toXYes = yes.getWidth() / 2;
        IEntityModifier iemYes = new SequenceEntityModifier(
                new DelayModifier(5f),
                new MoveModifier(1f,fromXYes, fromYYes, toXYes + 10f, toYYes, EaseBackInOut.getInstance()));
        iemYes.setAutoUnregisterWhenFinished(true);
        yes.registerEntityModifier(iemYes);

        IEntityModifier iemYesText = new SequenceEntityModifier(
                new DelayModifier(5f),
                new MoveModifier(1f,fromXYes, fromYYes, toXYes + 10f, toYYes, EaseBackInOut.getInstance()));
        iemYesText.setAutoUnregisterWhenFinished(true);
        yesText.registerEntityModifier(iemYesText);


        float fromYNo = no.getY();
        float fromXNo = no.getX();
        float toYNo = (no.getHeight() / 2) + 70f;
        float toXNo = GameActivity.SCREEN_WIDTH - (no.getWidth() / 2);
        IEntityModifier iemNo = new SequenceEntityModifier(
                new DelayModifier(5f),
                new MoveModifier(1f,fromXNo, fromYNo, toXNo - 10f, toYNo, EaseBackInOut.getInstance()));
        iemNo.setAutoUnregisterWhenFinished(true);
        no.registerEntityModifier(iemNo);

        IEntityModifier iemNoText = new SequenceEntityModifier(
                new DelayModifier(5f),
                new MoveModifier(1f,fromXNo, fromYNo, toXNo - 10f, toYNo, EaseBackInOut.getInstance()));
        iemNoText.setAutoUnregisterWhenFinished(true);
        noText.registerEntityModifier(iemNoText);

        float scaleXQuestion = question.getScaleX();
        float scaleYQuestion = question.getScaleY();
        float toScaleXQuestion = (GameActivity.SCREEN_WIDTH / question.getWidth()) + 0.5f;
        float fromYQuestion = question.getY();
        float fromXQuestion = question.getX();
        float toYQuestion = -50f;
        float toXQuestion = GameActivity.SCREEN_WIDTH / 2;
        IEntityModifier iemQuestion = new SequenceEntityModifier(
                new ParallelEntityModifier(new FadeInModifier(0.3f),
                new ScaleModifier(0.3f,0f,scaleXQuestion, 0f, scaleYQuestion)),
                new DelayModifier(4f),
                new ParallelEntityModifier(
                new MoveModifier(1f,fromXQuestion, fromYQuestion, toXQuestion, toYQuestion, EaseBackInOut.getInstance()),
                new ScaleModifier(1f,scaleXQuestion,toScaleXQuestion, scaleYQuestion, 0.32f)));
        iemQuestion.setAutoUnregisterWhenFinished(true);
        question.registerEntityModifier(iemQuestion);

        IEntityModifier iemGoSpriteTextQuestion = new DelayModifier(5.6f){
            @Override
            protected void onModifierFinished(IEntity pItem) {
                super.onModifierFinished(pItem);
                pItem.setVisible(true);
            }
        };
        iemGoSpriteTextQuestion.setAutoUnregisterWhenFinished(true);
        questionText.registerEntityModifier(iemGoSpriteTextQuestion);

        IEntityModifier iemGoTextQuestion = new DelayModifier(5.6f){
            @Override
            protected void onModifierFinished(IEntity pItem) {
                super.onModifierFinished(pItem);
                pItem.setVisible(true);
            }
        };
        iemGoTextQuestion.setAutoUnregisterWhenFinished(true);
        goTextQuestion.registerEntityModifier(iemGoTextQuestion);

        float fromGoTextWonLoseY = goTextWonLose.getY();
        float toGoTextWonLoseY = (GameActivity.SCREEN_HEIGHT / 2) + 128f;
        IEntityModifier iemGoTextWonLose = new SequenceEntityModifier(
                        new MoveYModifier(1.3f,fromGoTextWonLoseY, toGoTextWonLoseY, EaseBackInOut.getInstance()){
                            @Override
                            protected void onModifierStarted(IEntity pItem) {
                                super.onModifierStarted(pItem);
                                pItem.setVisible(true);
                            }
                        },
                        new DelayModifier(3f){
                            @Override
                            protected void onModifierFinished(IEntity pItem) {
                                super.onModifierFinished(pItem);
                                pItem.setVisible(false);
                            }
                        });
        iemGoTextWonLose.setAutoUnregisterWhenFinished(true);
        goTextWonLose.registerEntityModifier(iemGoTextWonLose);

        if (goTextScore != null) {
            float scaleXGoTextScore = goTextScore.getScaleX();
            float scaleYGoTextScore = goTextScore.getScaleY();
            IEntityModifier iemGoTextScore = new SequenceEntityModifier(
                    new ParallelEntityModifier(new FadeInModifier(0.3f),
                            new ScaleModifier(0.3f, 0f, scaleXGoTextScore, 0f, scaleYGoTextScore)),
                    new DelayModifier(4f) {
                        @Override
                        protected void onModifierFinished(IEntity pItem) {
                            super.onModifierFinished(pItem);
                            pItem.setVisible(false);
                        }
                    });
            iemGoTextScore.setAutoUnregisterWhenFinished(true);
            goTextScore.registerEntityModifier(iemGoTextScore);
        }
        if (goTextTitleScore != null) {
            float scaleXGoTextTitleScore = goTextTitleScore.getScaleX();
            float scaleYGoTextTitleScore = goTextTitleScore.getScaleY();
            IEntityModifier iemGoTextTitleScore = new SequenceEntityModifier(
                    new ParallelEntityModifier(new FadeInModifier(0.3f),
                            new ScaleModifier(0.3f, 0f, scaleXGoTextTitleScore, 0f, scaleYGoTextTitleScore)),
                    new DelayModifier(4f) {
                        @Override
                        protected void onModifierFinished(IEntity pItem) {
                            super.onModifierFinished(pItem);
                            pItem.setVisible(false);
                        }
                    });
            iemGoTextTitleScore.setAutoUnregisterWhenFinished(true);
            goTextTitleScore.registerEntityModifier(iemGoTextTitleScore);
        }
        if (goLoseTextScore != null){
            float scaleXGoLoseTextScore = goLoseTextScore.getScaleX();
            float scaleYGoLoseTextScore = goLoseTextScore.getScaleY();
            IEntityModifier iemGoLoseTextScore = new SequenceEntityModifier(
                    new ParallelEntityModifier(new FadeInModifier(0.3f),
                            new ScaleModifier(0.3f, 0f, scaleXGoLoseTextScore, 0f, scaleYGoLoseTextScore)),
                    new DelayModifier(4f) {
                        @Override
                        protected void onModifierFinished(IEntity pItem) {
                            super.onModifierFinished(pItem);
                            pItem.setVisible(false);
                        }
                    });
            iemGoLoseTextScore.setAutoUnregisterWhenFinished(true);
            goLoseTextScore.registerEntityModifier(iemGoLoseTextScore);
        }
    }

    public static void keepScreenOn() {
        ResourceManager.getInstance().mActivity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }

    // Clears the flag that keeps the screen on.
    public static void stopKeepingScreenOn() {
        ResourceManager.getInstance().mActivity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }
}
