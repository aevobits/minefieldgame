package com.aevobits.games.minesfieldgame.scene;

import com.aevobits.games.minesfieldgame.GameActivity;
import com.aevobits.games.minesfieldgame.ResourceManager;

import org.andengine.engine.Engine;
import org.andengine.engine.camera.Camera;
import org.andengine.entity.modifier.FadeOutModifier;
import org.andengine.entity.modifier.IEntityModifier;
import org.andengine.entity.modifier.SequenceEntityModifier;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.scene.Scene;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.util.adt.color.Color;

/**
 * Created by vito on 20/01/16.
 */
public abstract class BaseScene extends Scene {
    protected final int SCREEN_WIDTH = GameActivity.CAMERA_WIDTH;
    protected final int SCREEN_HEIGHT = GameActivity.CAMERA_HEIGHT;

    protected GameActivity mActivity;
    protected Engine mEngine;
    protected Camera mCamera;
    protected VertexBufferObjectManager vbom;
    protected ResourceManager mResourceManager;
    protected SceneManager mSceneManager;

    public BaseScene() {
        super();
        mResourceManager = ResourceManager.getInstance();
        mActivity = mResourceManager.mActivity;
        //vbom = mResourceManager.vbom;
        //mEngine = mResourceManager.engine;
        //mCamera = mResourceManager.camera;
        mSceneManager = SceneManager.getInstance();
        //createScene();
    }

    protected void fadeIn(){
        Rectangle rectangle = new Rectangle(SCREEN_WIDTH / 2, SCREEN_HEIGHT / 2, SCREEN_WIDTH, SCREEN_HEIGHT, mResourceManager.vbom);
        rectangle.setColor(Color.BLACK);
        IEntityModifier iem = new SequenceEntityModifier(new FadeOutModifier(0.7f));
        iem.setAutoUnregisterWhenFinished(true);
        rectangle.registerEntityModifier(iem);
        attachChild(rectangle);
    }

    public abstract void createScene();
    public abstract void onBackKeyPressed();
    public abstract SceneManager.SceneType getSceneType();
    public abstract void disposeScene();
}
