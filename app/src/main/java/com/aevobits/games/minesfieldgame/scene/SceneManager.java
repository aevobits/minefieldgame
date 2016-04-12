package com.aevobits.games.minesfieldgame.scene;

import com.aevobits.games.minesfieldgame.ResourceManager;

import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.AlphaModifier;
import org.andengine.entity.modifier.FadeOutModifier;
import org.andengine.entity.modifier.IEntityModifier;
import org.andengine.entity.modifier.ScaleModifier;
import org.andengine.util.modifier.IModifier;

/**
 * Created by vito on 20/01/16.
 */
public class SceneManager {
    private static final SceneManager INSTANCE = new SceneManager();

    public enum SceneType {SCENE_SPLASH, SCENE_MENU, SCENE_GAME}

    private BaseScene mSplashScene;
    private BaseScene mMenuScene;
    private BaseScene mGameScene;

    private SceneType mCurrentSceneType;
    private BaseScene mCurrentScene;
    private BaseScene mPreviousScene;

    private SceneManager() {}

    public static SceneManager getInstance() {
        return INSTANCE;
    }

    public void setScene(SceneType sceneType) {
        switch (sceneType) {
            case SCENE_MENU:
                setScene(createMenuScene());
                break;
            case SCENE_GAME:
                setScene(createGameScene());
                break;
            case SCENE_SPLASH:
                setScene(createSplashScene());
                break;
        }
    }

    private void setScene(final BaseScene scene) {
        mPreviousScene = getCurrentScene();
        mCurrentScene = scene;
        mCurrentSceneType = scene.getSceneType();
        ResourceManager.getInstance().engine.setScene(scene);
        /*
        mPreviousScene.registerEntityModifier(new AlphaModifier(3f, 1f, 0f, new IEntityModifier.IEntityModifierListener() {

            @Override
            public void onModifierStarted(IModifier<IEntity> pModifier, IEntity pItem) {
            }

            @Override
            public void onModifierFinished(IModifier<IEntity> pModifier, IEntity pItem) {
                ResourceManager.getInstance().engine.setScene(scene);
            } }));
        */
    }

    public SceneType getCurrentSceneType() {
        return mCurrentSceneType;
    }

    public BaseScene getCurrentScene() {
        return mCurrentScene;
    }

    public void setCurrentScene(BaseScene scene){
        this.mCurrentScene = scene;
    }

    public BaseScene createSplashScene() {
        mSplashScene = new SplashScene();
        mSplashScene.createScene();
        return mSplashScene;
    }

    private BaseScene createMenuScene() {
        mMenuScene = new MainMenuScene();
        mMenuScene.createScene();
        return mMenuScene;
    }

    public BaseScene createGameScene() {
        mGameScene = new GameScene();
        mGameScene.createScene();
        return mGameScene;
    }
}
