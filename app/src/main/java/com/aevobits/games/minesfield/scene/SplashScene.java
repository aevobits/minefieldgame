package com.aevobits.games.minesfield.scene;

import org.andengine.entity.sprite.Sprite;

/**
 * Created by vito on 22/01/16.
 */
public class SplashScene extends BaseScene {

    @Override
    public void createScene() {
        //getBackground().setColor(new Color(0.109803922f, 0.717647059f, 0.921568627f));

        Sprite logoSprite = new Sprite(SCREEN_WIDTH / 2, SCREEN_HEIGHT - (SCREEN_HEIGHT / 3), 300, 180, mResourceManager.logoTextureRegion, vbom);
        attachChild(logoSprite);

        //Text copyrightText = new Text(0, 0, mResourceManager.candy_shop, "AEVO BITS", vbom);
        //copyrightText.setPosition(SCREEN_WIDTH / 2 , (SCREEN_HEIGHT * 2) / 3);
        //attachChild(copyrightText);

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
