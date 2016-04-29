package com.aevobits.games.minesfieldgame;

import org.andengine.entity.Entity;
import org.andengine.entity.modifier.AlphaModifier;
import org.andengine.entity.modifier.FadeInModifier;
import org.andengine.entity.modifier.IEntityModifier;
import org.andengine.entity.modifier.ParallelEntityModifier;
import org.andengine.entity.modifier.ScaleModifier;
import org.andengine.entity.scene.Scene;

import java.util.List;

import javax.microedition.khronos.opengles.GL10;

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

    public static <T extends Entity> void fadeIn(T entity){
        IEntityModifier iem = new FadeInModifier(5f);
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
}
