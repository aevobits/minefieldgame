package com.aevobits.games.minesfield.scene;

import com.aevobits.games.minesfield.R;
import com.aevobits.games.minesfield.entity.LevelTile;
import com.aevobits.games.minesfield.manager.PlayerDataManager;

import org.andengine.entity.primitive.Gradient;
import org.andengine.entity.scene.background.EntityBackground;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.entity.text.TextOptions;
import org.andengine.util.adt.align.HorizontalAlign;
import org.andengine.util.adt.color.Color;

/**
 * Created by vito on 11/07/16.
 */
public class LevelMenuScene extends BaseScene{

    private final float TILE_DIMENSION_X = 74f;
    private final float TILE_DIMENSION_Y = 85f;
    private final float TILE_PADDING = 20f;

    private final int COLUMNS = 4;
    private final int ROWS = 4;

    private int mMaxLevel = 1;

    @Override
    public void createScene() {

        float pX = SCREEN_WIDTH / 2;
        float pY = SCREEN_HEIGHT / 2;

        Sprite levelBoard = new Sprite(pX, pY, 450, 540, mResourceManager.levelsBoardTextureRegion, mResourceManager.vbom );
        this.attachChild(levelBoard);

        String levelTitle = mResourceManager.mActivity.getString(R.string.level);
        Text textInfo = new Text(pX, pY + 225f, mResourceManager.bebasneueBold, levelTitle, new TextOptions(HorizontalAlign.CENTER), mResourceManager.vbom);
        textInfo.setScale(1.4f);
        this.attachChild(textInfo);

        String level = PlayerDataManager.getInstance().getData("MaxLevel");
        this.mMaxLevel = 16;//((level != null)?Integer.parseInt(level):Integer.parseInt("1"));
        createBackground();
        createTiles();
        fadeIn();
    }

    private void createBackground(){
        Gradient g = new Gradient(SCREEN_WIDTH / 2, SCREEN_HEIGHT / 2, SCREEN_WIDTH, SCREEN_HEIGHT, mResourceManager.vbom);
        g.setGradient(new Color(0.68627451f, 0.866666667f, 0.91372549f), new Color(0.109803922f, 0.717647059f, 0.921568627f), 0, 1);
        this.setBackground(new EntityBackground(g));
        g.setIgnoreUpdate(true);
    }

    public void createTiles() {

        float mInitialX = 100f;
        float mInitialY = 535f;
        float tempY = mInitialY;

        int currentTileLevel = 1;

        for (int i = 0; i < ROWS; i++) {
            float tempX = mInitialX;

            for (int o = 0; o < COLUMNS; o++) {

                final boolean locked;
                LevelTile levelTile;

                if (currentTileLevel <= mMaxLevel) {
                    locked = false;
                    levelTile = new LevelTile(tempX, tempY, TILE_DIMENSION_X, TILE_DIMENSION_Y, locked,
                            currentTileLevel, mResourceManager.levelTextureRegion, mResourceManager.vbom);
                } else {
                    locked = true;
                    levelTile = new LevelTile(tempX, tempY, TILE_DIMENSION_X, TILE_DIMENSION_Y, locked,
                            currentTileLevel, mResourceManager.levelTextureRegion, mResourceManager.padlockTextureRegion, mResourceManager.vbom);
                }
                levelTile.setScale(1.1f);
                this.registerTouchArea(levelTile);
                this.attachChild(levelTile);

                tempX = tempX + TILE_DIMENSION_X + TILE_PADDING;
                currentTileLevel++;
            }

            tempY = tempY - TILE_DIMENSION_Y - TILE_PADDING;
        }
    }

    @Override
    public void onBackKeyPressed() { mSceneManager.setScene(SceneManager.SceneType.SCENE_MENU);}

    @Override
    public SceneManager.SceneType getSceneType() {
        return SceneManager.SceneType.SCENE_LEVEL_MENU;
    }

    @Override
    public void disposeScene() {

    }
}
