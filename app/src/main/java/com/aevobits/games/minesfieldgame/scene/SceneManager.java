package com.aevobits.games.minesfieldgame.scene;

import com.aevobits.games.minesfieldgame.ResourceManager;

import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.AlphaModifier;
import org.andengine.entity.modifier.FadeInModifier;
import org.andengine.entity.modifier.FadeOutModifier;
import org.andengine.entity.modifier.IEntityModifier;
import org.andengine.entity.modifier.ScaleModifier;
import org.andengine.entity.modifier.SequenceEntityModifier;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.util.adt.color.Color;
import org.andengine.util.modifier.IModifier;

import javax.microedition.khronos.opengles.GL10;

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
        Rectangle backgroundScene = new Rectangle(240, 400,480, 800, ResourceManager.getInstance().vbom);
        backgroundScene.setColor(new Color(0.109803922f, 0.717647059f, 0.921568627f));
        mPreviousScene.attachChild(backgroundScene);
        //backgroundScene.registerEntityModifier(new SequenceEntityModifier(new FadeOutModifier(5), new FadeInModifier(5)));

        backgroundScene.setBlendFunction(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
        IEntityModifier iem = new AlphaModifier(5, 0, 1);
        iem.setAutoUnregisterWhenFinished(true);
        backgroundScene.registerEntityModifier(iem);
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
