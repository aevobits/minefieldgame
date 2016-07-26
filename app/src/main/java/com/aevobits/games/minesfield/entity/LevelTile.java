package com.aevobits.games.minesfield.entity;

import android.widget.Toast;

import com.aevobits.games.minesfield.GameActivity;
import com.aevobits.games.minesfield.R;
import com.aevobits.games.minesfield.manager.ResourceManager;
import com.aevobits.games.minesfield.scene.MapManager;
import com.aevobits.games.minesfield.scene.SceneManager;
import com.aevobits.games.minesfield.util.Utils;

import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.IEntityModifier;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.entity.text.TextOptions;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.util.adt.align.HorizontalAlign;
import org.andengine.util.modifier.IModifier;

/**
 * Created by vito on 11/07/16.
 */
public class LevelTile extends Sprite {

    private final boolean mIsLocked;
    private final int mLevelNumber;
    private LevelTile levelTile;

    private ResourceManager mResourceManager;
    private GameActivity mActivity;
    private SceneManager mSceneManager;

    public LevelTile(float pX, float pY, float pWidth, float pHeight, boolean pIsLocked, int pLevelNumber, ITextureRegion iLevelTextureRegion, VertexBufferObjectManager vbom) {
        super(pX, pY, pWidth, pHeight, iLevelTextureRegion, vbom);

        this.mIsLocked = pIsLocked;
        this.mLevelNumber = pLevelNumber;

        this.mResourceManager = ResourceManager.getInstance();
        this.mActivity = this.mResourceManager.mActivity;
        this.mSceneManager = SceneManager.getInstance();
        this.attachText(pWidth, pHeight);
        this.levelTile = this;
    }

    public LevelTile(float pX, float pY, float pWidth, float pHeight, boolean pIsLocked, int pLevelNumber, ITextureRegion iLevelTextureRegion,
                     ITextureRegion iPadlockTextureRegion, VertexBufferObjectManager vbom) {
        super(pX, pY, pWidth, pHeight, iLevelTextureRegion, vbom);

        this.mIsLocked = pIsLocked;
        this.mLevelNumber = pLevelNumber;

        this.mResourceManager = ResourceManager.getInstance();
        this.mActivity = this.mResourceManager.mActivity;
        //this.attachText();

        Sprite padlock = new Sprite((pWidth /2), (pHeight / 2) - 7f, 35f, 35f, iPadlockTextureRegion, vbom);
        this.attachChild(padlock);
        this.levelTile = this;
    }

    public boolean isLocked() {
        return this.mIsLocked;
    }

    public int getLevelNumber() {
        return this.mLevelNumber;
    }

    private void attachText(float pWidth, float pHeight) {

        Text textInfo = new Text((pWidth / 2) - 2f, (pHeight / 2) - 10f, mResourceManager.bebasneueBold, String.valueOf(this.getLevelNumber()), new TextOptions(HorizontalAlign.CENTER), mResourceManager.vbom);
        if(this.getLevelNumber() == 1)
            textInfo.setPosition(textInfo.getX() - 2f, textInfo.getY());
        else {

        }
        this.attachChild(textInfo);
    }

    @Override
    public boolean onAreaTouched(TouchEvent pSceneTouchEvent,
                                 float pTouchAreaLocalX, float pTouchAreaLocalY) {

        switch (pSceneTouchEvent.getAction()) {
            case TouchEvent.ACTION_UP:{
                IEntityModifier iem = Utils.clickEffect(this);
                iem.addModifierListener(new IEntityModifier.IEntityModifierListener() {
                    @Override
                    public void onModifierStarted(IModifier<IEntity> pModifier, IEntity pItem) {}

                    @Override
                    public void onModifierFinished(IModifier<IEntity> pModifier, IEntity pItem) {
                        if (!levelTile.mIsLocked) {
                            MapManager mapManager = MapManager.getInstance();
                            mapManager.level = levelTile.getLevelNumber();
                            mResourceManager.unloadMainMenuResources();
                            mResourceManager.loadGameResources();
                            mSceneManager.setScene(SceneManager.SceneType.SCENE_GAME);
                        } else {
                            mActivity.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    CharSequence text = mActivity.getString(R.string.levelLocked);
                                    int duration = Toast.LENGTH_LONG;
                                    Toast.makeText(mActivity.getApplicationContext(), text, duration).show();
                                }
                            });
                        }
                    }
                });

                return true;
            }
            case TouchEvent.ACTION_DOWN: {
                mResourceManager.mActivity.playSound(mResourceManager.soundClick);
            }
            default: {
                return false;
            }
        }
    }

}
