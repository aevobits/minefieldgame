package com.aevobits.games.minesfield.scene;

import com.aevobits.games.minesfield.GameActivity;
import com.aevobits.games.minesfield.manager.ResourceManager;

import org.andengine.engine.Engine;
import org.andengine.engine.camera.Camera;
import org.andengine.entity.modifier.FadeOutModifier;
import org.andengine.entity.modifier.IEntityModifier;
import org.andengine.entity.modifier.SequenceEntityModifier;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.util.adt.color.Color;

import java.util.Locale;

/**
 * Created by vito on 20/01/16.
 */
public abstract class BaseScene extends Scene {
    protected final int SCREEN_WIDTH = GameActivity.SCREEN_WIDTH;
    protected final int SCREEN_HEIGHT = GameActivity.SCREEN_HEIGHT;

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
        mEngine = mResourceManager.engine;
        mCamera = mResourceManager.camera;
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

    protected void drawLogo(float pX, float pY){
        Sprite titleSprite;
        if(Locale.getDefault().getLanguage().equals("it")) {
            titleSprite = new Sprite(pX, pY + 150, 250f, 140f, mResourceManager.titleITTextureRegion, mResourceManager.vbom);
        } else {
            titleSprite = new Sprite(pX, pY + 150, 350f, 168f, mResourceManager.titleENTextureRegion, mResourceManager.vbom);
        }
        attachChild(titleSprite);
    }

    public abstract void createScene();
    public abstract void onBackKeyPressed();
    public abstract SceneManager.SceneType getSceneType();
    public abstract void disposeScene();
}
