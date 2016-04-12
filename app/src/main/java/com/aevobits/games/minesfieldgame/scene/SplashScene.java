package com.aevobits.games.minesfieldgame.scene;

import org.andengine.entity.text.Text;
import org.andengine.util.adt.color.Color;

/**
 * Created by vito on 22/01/16.
 */
public class SplashScene extends BaseScene {

    @Override
    public void createScene() {
        getBackground().setColor(new Color(0.109803922f, 0.717647059f, 0.921568627f));
        Text copyrightText = new Text(0, 0, mResourceManager.candy_shop, "AEVO BITS", vbom);
        copyrightText.setPosition(SCREEN_WIDTH / 2 , (SCREEN_HEIGHT * 2) / 3);
        attachChild(copyrightText);

    }

    @Override
    public void onBackKeyPressed() {
        mActivity.finish();
    }

    @Override
    public SceneManager.SceneType getSceneType() {
        return SceneManager.SceneType.SCENE_SPLASH;
    }

    @Override
    public void disposeScene() {
    }
}
