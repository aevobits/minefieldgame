package com.aevobits.games.minesfield.scene;

import org.andengine.entity.sprite.Sprite;

/**
 * Created by vito on 22/01/16.
 */
public class SplashScene extends BaseScene {

    @Override
    public void createScene() {
        Sprite logoSprite = new Sprite(SCREEN_WIDTH / 2, SCREEN_HEIGHT - (SCREEN_HEIGHT / 3), 300, 180, mResourceManager.logoTextureRegion, vbom);
        attachChild(logoSprite);
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
