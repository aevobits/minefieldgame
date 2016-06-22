package com.aevobits.games.minesfieldgame;

import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

import org.andengine.audio.sound.Sound;
import org.andengine.audio.sound.SoundFactory;
import org.andengine.engine.Engine;
import org.andengine.engine.camera.Camera;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.font.FontFactory;
import org.andengine.opengl.texture.ITexture;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.atlas.bitmap.BuildableBitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.source.IBitmapTextureAtlasSource;
import org.andengine.opengl.texture.atlas.buildable.builder.BlackPawnTextureAtlasBuilder;
import org.andengine.opengl.texture.atlas.buildable.builder.ITextureAtlasBuilder;
import org.andengine.opengl.texture.bitmap.BitmapTextureFormat;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.TiledTextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.util.adt.color.Color;

import java.io.IOException;
import java.util.Locale;

/**
 * Created by vito on 01/03/16.
 */
public class ResourceManager {

    public GameActivity mActivity;
    public Engine engine;
    public Camera camera;
    public VertexBufferObjectManager vbom;
    public int density;

    public float startClick;
    public float endClick;

    private BuildableBitmapTextureAtlas splashScreenTextureAtlas;
    public ITextureRegion logoTextureRegion;

    public ITextureRegion tileTextureRegion;
    public ITextureRegion emptyTileTextureRegion;
    public ITextureRegion oneTileTextureRegion;
    public ITextureRegion twoTileTextureRegion;
    public ITextureRegion threeTileTextureRegion;
    public ITextureRegion fourTileTextureRegion;
    public ITextureRegion fiveTileTextureRegion;
    public ITextureRegion sixTileTextureRegion;
    public ITextureRegion sevenTileTextureRegion;
    public ITextureRegion eightTileTextureRegion;
    public ITextureRegion bombTileTextureRegion;
    public ITextureRegion bombsTileTextureRegion;
    public ITextureRegion timerGameTileTextureRegion;
    public ITextureRegion homeTextureRegion;
    public ITextureRegion bestScoreTextureRegion;
    public ITextureRegion replayTextureRegion;
    public ITextureRegion flagTileTextureRegion;
    private BuildableBitmapTextureAtlas gameTextureAtlas;

    private BuildableBitmapTextureAtlas mainMenuTextureAtlas;
    private BitmapTextureAtlas mSubmenuTextureAtlas;
    public ITextureRegion buttonLevelTextureRegion;
    public ITextureRegion buttonEasyLevelTextureRegion;
    public ITextureRegion buttonMediumLevelTextureRegion;
    public ITextureRegion buttonHardLevelTextureRegion;
    public ITextureRegion buttonProLevelTextureRegion;
    public TiledTextureRegion musicTextureRegion;
    public ITextureRegion rankingTextureRegion;
    public ITextureRegion rewardsTextureRegion;
    public ITextureRegion sharingTextureRegion;
    public ITextureRegion infoTextureRegion;
    public ITextureRegion statsTextureRegion;
    public ITextureRegion rateTextureRegion;
    public ITextureRegion rulesTextureRegion;
    public ITextureRegion rulesBoardTextureRegion;
    public ITextureRegion statsBoardTextureRegion;
    public ITexture fontStroke;

    private BuildableBitmapTextureAtlas mSubBitmapTextureAtlas;
    public ITextureRegion gameOverTextTextureRegion;
    public ITextureRegion gameOverYesTextureRegion;
    public ITextureRegion gameOverNoTextureRegion;
    public ITextureRegion mPlayTextureRegion;

    private ITexture fontTexture;

    public Font montserrat;
    public Font candy_shop;
    public Font candy_shop_min;

    //sounds
    public Sound soundExplosion;
    public Sound soundFlip;
    public Sound soundClick;
    public Sound soundTada;

    public Locale locale;


    private static final ResourceManager INSTANCE = new ResourceManager();

    private ResourceManager(){}

    public static ResourceManager getInstance(){
        return INSTANCE;
    }

    public void create(GameActivity mActivity, Engine engine,
                       Camera camera, VertexBufferObjectManager vbom){

        this.mActivity = mActivity;
        this.engine = engine;
        this.camera = camera;
        this.vbom = vbom;

        WindowManager windowManager = (WindowManager) mActivity.getSystemService(mActivity.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        display.getMetrics(displayMetrics);
        density = (int)(displayMetrics.density);

        this.locale = this.mActivity.getResources().getConfiguration().locale;

        FontFactory.setAssetBasePath("font/");
    }

    public void loadSplashResources(){
        BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
        splashScreenTextureAtlas = new BuildableBitmapTextureAtlas(mActivity.getTextureManager(),
                1024, 1024, BitmapTextureFormat.RGBA_8888,
                TextureOptions.BILINEAR_PREMULTIPLYALPHA);
        logoTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(
                splashScreenTextureAtlas, mActivity.getAssets(), "aevobits_logo.png");

        try {
            splashScreenTextureAtlas.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(2, 0, 2));
            splashScreenTextureAtlas.load();
        } catch (ITextureAtlasBuilder.TextureAtlasBuilderException e) {
            throw  new RuntimeException("Error while loading splash textures", e);
        }


        candy_shop = FontFactory.createFromAsset(mActivity.getFontManager(), mActivity.getTextureManager(), 256, 256,
                mActivity.getAssets(), "candy_shop.ttf", 40, true, Color.WHITE_ARGB_PACKED_INT);
        candy_shop.load();

        int fontSize = (10 * density);
        fontTexture = new BitmapTextureAtlas(mActivity.getTextureManager(), 256, 256, TextureOptions.REPEATING_BILINEAR_PREMULTIPLYALPHA);

        candy_shop_min = FontFactory.createFromAsset(mActivity.getFontManager(), fontTexture,
                mActivity.getAssets(), "candy_shop.ttf", 20, true, Color.WHITE_ARGB_PACKED_INT);
        candy_shop_min.load();
    }

    public void unloadSplashResources(){
        //edmunds.unload();
        //edmunds = null;
    }

    public void loadMainManuResources(){
        BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
        mainMenuTextureAtlas = new BuildableBitmapTextureAtlas(mActivity.getTextureManager(),
                1024, 1024, BitmapTextureFormat.RGBA_8888,
                TextureOptions.BILINEAR_PREMULTIPLYALPHA);
        buttonLevelTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(
                mainMenuTextureAtlas, mActivity.getAssets(), "level.png");
        buttonEasyLevelTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(
                mainMenuTextureAtlas, mActivity.getAssets(), "level1.png");
        buttonMediumLevelTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(
                mainMenuTextureAtlas, mActivity.getAssets(), "level2.png");
        buttonHardLevelTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(
                mainMenuTextureAtlas, mActivity.getAssets(), "level3.png");
        buttonProLevelTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(
                mainMenuTextureAtlas, mActivity.getAssets(), "level4.png");

        mSubmenuTextureAtlas = new BitmapTextureAtlas(mActivity.getTextureManager(), 1024, 1024, TextureOptions.BILINEAR);
        musicTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mSubmenuTextureAtlas, mActivity, "sound.png", 0, 0, 2, 1);
        rankingTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(
                mainMenuTextureAtlas, mActivity.getAssets(), "ranking.png");
        rewardsTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(
                mainMenuTextureAtlas, mActivity.getAssets(), "rewards.png");
        sharingTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(
                mainMenuTextureAtlas, mActivity.getAssets(), "sharing.png");
        infoTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(
                mainMenuTextureAtlas, mActivity.getAssets(), "info.png");
        statsTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(
                mainMenuTextureAtlas, mActivity.getAssets(), "stats.png");
        rateTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(
                mainMenuTextureAtlas, mActivity.getAssets(), "rate.png");
        rulesTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(
                mainMenuTextureAtlas, mActivity.getAssets(), "rules.png");
        rulesBoardTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(
                mainMenuTextureAtlas, mActivity.getAssets(), "rulesBoard.png");
        statsBoardTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(
                mainMenuTextureAtlas, mActivity.getAssets(), "statistics.png");
        mSubmenuTextureAtlas.load();

        try {
            mainMenuTextureAtlas.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(2, 0, 2));
            mainMenuTextureAtlas.load();
        } catch (ITextureAtlasBuilder.TextureAtlasBuilderException e) {
            throw  new RuntimeException("Error while loading main menu textures", e);
        }

        //fontStroke = new BitmapTextureAtlas(mActivity.getTextureManager(), 256, 256, TextureOptions.BILINEAR);

        fontTexture = new BitmapTextureAtlas(mActivity.getTextureManager(), 256, 256, TextureOptions.REPEATING_BILINEAR_PREMULTIPLYALPHA);

        montserrat = FontFactory.createFromAsset(mActivity.getFontManager(), fontTexture,
                mActivity.getAssets(), "Montserrat-Regular.ttf", 36, true, Color.WHITE_ARGB_PACKED_INT);

        //montserrat = FontFactory.createStrokeFromAsset(mActivity.getFontManager(), fontStroke, mActivity.getAssets(), "Montserrat-Regular.ttf", 36, true, Color.WHITE_ARGB_PACKED_INT, 1, Color.BLACK_ARGB_PACKED_INT);
        montserrat.load();

        try {
            SoundFactory.setAssetBasePath("sfx/");
            soundClick = SoundFactory.createSoundFromAsset(mActivity.getSoundManager(), mActivity, "click.mp3");

        } catch (IOException e) {
            throw new RuntimeException("Error while loading audio", e);
        }
    }

    public void unloadMainManuResources(){
        mainMenuTextureAtlas.unload();
        buttonEasyLevelTextureRegion = null;
        buttonMediumLevelTextureRegion = null;
        buttonHardLevelTextureRegion = null;
        buttonProLevelTextureRegion = null;
        musicTextureRegion = null;
        rankingTextureRegion = null;
        rewardsTextureRegion = null;
        //montserrat.unload();
        //montserrat = null;
    }

    public void loadGameResources(){
        BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
        gameTextureAtlas = new BuildableBitmapTextureAtlas(mActivity.getTextureManager(),
                1024, 1024, BitmapTextureFormat.RGBA_8888,
                TextureOptions.BILINEAR_PREMULTIPLYALPHA);
        tileTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(
                gameTextureAtlas, mActivity.getAssets(), "tile.png");
        emptyTileTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(
                gameTextureAtlas, mActivity.getAssets(), "emptyTile.png");
        oneTileTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(
                gameTextureAtlas, mActivity.getAssets(), "oneTile.png");
        twoTileTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(
                gameTextureAtlas, mActivity.getAssets(), "twoTile.png");
        threeTileTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(
                gameTextureAtlas, mActivity.getAssets(), "threeTile.png");
        fourTileTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(
                gameTextureAtlas, mActivity.getAssets(), "fourTile.png");
        fiveTileTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(
                gameTextureAtlas, mActivity.getAssets(), "fiveTile.png");
        sixTileTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(
                gameTextureAtlas, mActivity.getAssets(), "sixTile.png");
        sevenTileTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(
                gameTextureAtlas, mActivity.getAssets(), "sevenTile.png");
        eightTileTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(
                gameTextureAtlas, mActivity.getAssets(), "eightTile.png");
        bombTileTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(
                gameTextureAtlas, mActivity.getAssets(), "bombTile.png");
        flagTileTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(
                gameTextureAtlas, mActivity.getAssets(), "flag.png");
        bombsTileTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(
                gameTextureAtlas, mActivity.getAssets(), "bombs.png");
        timerGameTileTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(
                gameTextureAtlas, mActivity.getAssets(), "timerGame.png");
        homeTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(
                gameTextureAtlas, mActivity.getAssets(), "home.png");
        bestScoreTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(
                gameTextureAtlas, mActivity.getAssets(), "bestScore.png");
        replayTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(
                gameTextureAtlas, mActivity.getAssets(), "replay.png");

        mSubBitmapTextureAtlas = new BuildableBitmapTextureAtlas(mActivity.getTextureManager(), 1024, 1024, TextureOptions.BILINEAR);

        gameOverTextTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(
                mSubBitmapTextureAtlas, mActivity.getAssets(), "gameOverText.png");
        gameOverYesTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(
                mSubBitmapTextureAtlas, mActivity.getAssets(), "gameOverYes.png");
        gameOverNoTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(
                mSubBitmapTextureAtlas, mActivity.getAssets(), "gameOverNo.png");

/*
        calculator = FontFactory.createFromAsset(mActivity.getFontManager(), mActivity.getTextureManager(), 256, 256,
                TextureOptions.DEFAULT, mActivity.getAssets(), "calculator.ttf", 36, true, Color.BLACK_ABGR_PACKED_INT);
        calculator.load();
*/
/*
        coolvetica = FontFactory.createFromAsset(mActivity.getFontManager(), mActivity.getTextureManager(), 256, 256,
                TextureOptions.DEFAULT, mActivity.getAssets(), "coolvetica.ttf", 36, true, Color.BLACK_ABGR_PACKED_INT);
        coolvetica.load();
*/
        try {
            gameTextureAtlas.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(2, 0, 2));
            gameTextureAtlas.load();
            mSubBitmapTextureAtlas.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(2, 0, 2));
            mSubBitmapTextureAtlas.load();
        } catch (ITextureAtlasBuilder.TextureAtlasBuilderException e) {
            throw  new RuntimeException("Error while loading game textures", e);
        }

        try {
            SoundFactory.setAssetBasePath("sfx/");
            soundExplosion = SoundFactory.createSoundFromAsset(mActivity.getSoundManager(), mActivity, "explosion.mp3");
            soundFlip = SoundFactory.createSoundFromAsset(mActivity.getSoundManager(), mActivity, "flip.mp3");
            soundTada = SoundFactory.createSoundFromAsset(mActivity.getSoundManager(), mActivity, "tada.mp3");

        } catch (IOException e) {
            throw new RuntimeException("Error while loading audio", e);
        }
    }

    public void unloadGameResources(){

    }
}
