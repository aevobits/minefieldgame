package com.aevobits.games.minesfield.manager;

import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

import com.aevobits.games.minesfield.GameActivity;

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
    public ITextureRegion bombLoseTileTextureRegion;
    public ITextureRegion bombsTileTextureRegion;
    public ITextureRegion timerGameTileTextureRegion;
    public ITextureRegion homeTextureRegion;
    public ITextureRegion replayTextureRegion;
    public ITextureRegion flagTileTextureRegion;
    public ITextureRegion currentLevelTextureRegion;
    private BuildableBitmapTextureAtlas gameTextureAtlas;

    private BuildableBitmapTextureAtlas mainMenuTextureAtlas;
    private BitmapTextureAtlas mSubmenuTextureAtlas;
    public ITextureRegion titleITTextureRegion;
    public ITextureRegion titleENTextureRegion;
    public ITextureRegion playLevelTextureRegion;
    public TiledTextureRegion musicTextureRegion;
    public ITextureRegion rankingTextureRegion;
    public ITextureRegion rewardsTextureRegion;
    public ITextureRegion sharingTextureRegion;
    public ITextureRegion infoTextureRegion;
    public ITextureRegion rateTextureRegion;
    public ITextureRegion rulesTextureRegion;
    public ITextureRegion rulesBoardTextureRegion;
    public ITextureRegion statsBoardTextureRegion;
    public ITextureRegion levelsBoardTextureRegion;
    public ITextureRegion levelTextureRegion;
    public ITextureRegion padlockTextureRegion;
    public ITextureRegion pauseTextureRegion;
    public ITextureRegion playTextureRegion;

    private BuildableBitmapTextureAtlas mSubBitmapTextureAtlas;
    public ITextureRegion gameOverTextTextureRegion;
    public ITextureRegion gameOverFailedTextureRegion;
    public ITextureRegion gameOverQuestionTextureRegion;
    public ITextureRegion buttonTextureRegion;
    public ITextureRegion gameOverYesTextureRegion;
    public ITextureRegion gameOverNoTextureRegion;

    private ITexture fontTexture;
    private ITexture fontTexture2;
    private ITexture fontTexture3;
    public Font montserrat;
    public Font  bebasneue;
    public Font  bebasneueBold;

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
        BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
        SoundFactory.setAssetBasePath("sfx/");

        fontTexture = new BitmapTextureAtlas(mActivity.getTextureManager(), 256, 256, TextureOptions.REPEATING_BILINEAR_PREMULTIPLYALPHA);

        montserrat = FontFactory.createFromAsset(mActivity.getFontManager(), fontTexture,
                mActivity.getAssets(), "Montserrat-Regular.ttf", 36, true, Color.WHITE_ARGB_PACKED_INT);

        montserrat.load();

        fontTexture2 = new BitmapTextureAtlas(mActivity.getTextureManager(), 256, 256, TextureOptions.REPEATING_BILINEAR_PREMULTIPLYALPHA);

        bebasneue = FontFactory.createFromAsset(mActivity.getFontManager(), fontTexture2,
                mActivity.getAssets(), "BebasNeue.ttf", 36, true, Color.WHITE_ARGB_PACKED_INT);

        bebasneue.load();

        fontTexture3 = new BitmapTextureAtlas(mActivity.getTextureManager(), 256, 256, TextureOptions.REPEATING_BILINEAR_PREMULTIPLYALPHA);

        bebasneueBold = FontFactory.createFromAsset(mActivity.getFontManager(), fontTexture3,
                mActivity.getAssets(), "BebasNeueBold.ttf", 36, true, Color.WHITE_ARGB_PACKED_INT);

        bebasneueBold.load();

        try {
            soundClick = SoundFactory.createSoundFromAsset(mActivity.getSoundManager(), mActivity, "click.mp3");
        } catch (IOException e) {
            throw new RuntimeException("Error while loading audio", e);
        }
    }

    public void loadSplashResources(){
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

    }

    public void unloadSplashResources(){
        splashScreenTextureAtlas.unload();
        splashScreenTextureAtlas = null;
        logoTextureRegion = null;
    }

    public void loadMainMenuResources(){

        mainMenuTextureAtlas = new BuildableBitmapTextureAtlas(mActivity.getTextureManager(),
                1240, 1240, BitmapTextureFormat.RGBA_8888,
                TextureOptions.BILINEAR_PREMULTIPLYALPHA);

        titleITTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(
                mainMenuTextureAtlas, mActivity.getAssets(), "campoMinato.png");
        titleENTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(
                mainMenuTextureAtlas, mActivity.getAssets(), "mineSweeper.png");
        playLevelTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(
                mainMenuTextureAtlas, mActivity.getAssets(), "play_button.png");

        rankingTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(
                mainMenuTextureAtlas, mActivity.getAssets(), "ranking.png");
        rewardsTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(
                mainMenuTextureAtlas, mActivity.getAssets(), "rewards.png");
        sharingTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(
                mainMenuTextureAtlas, mActivity.getAssets(), "share.png");
        infoTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(
                mainMenuTextureAtlas, mActivity.getAssets(), "info_button.png");
        rateTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(
                mainMenuTextureAtlas, mActivity.getAssets(), "rate_button.png");
        rulesTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(
                mainMenuTextureAtlas, mActivity.getAssets(), "rules_button.png");
        rulesBoardTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(
                mainMenuTextureAtlas, mActivity.getAssets(), "rulesBoard.png");
        statsBoardTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(
                mainMenuTextureAtlas, mActivity.getAssets(), "statistics.png");
        homeTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(
                mainMenuTextureAtlas, mActivity.getAssets(), "home.png");
        levelsBoardTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(
                mainMenuTextureAtlas, mActivity.getAssets(), "levelsBoard.png");
        levelTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(
                mainMenuTextureAtlas, mActivity.getAssets(), "level.png");
        padlockTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(
                mainMenuTextureAtlas, mActivity.getAssets(), "padlock.png");


        mSubmenuTextureAtlas = new BitmapTextureAtlas(mActivity.getTextureManager(), 1024, 1024, TextureOptions.BILINEAR);
        musicTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mSubmenuTextureAtlas, mActivity, "sound.png", 0, 0, 2, 1);

        try {
            mainMenuTextureAtlas.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(2, 0, 2));
            mainMenuTextureAtlas.load();
            mSubmenuTextureAtlas.load();
        } catch (ITextureAtlasBuilder.TextureAtlasBuilderException e) {
            throw  new RuntimeException("Error while loading main menu textures", e);
        }
    }

    public void unloadMainMenuResources(){
        mainMenuTextureAtlas.unload();
        mainMenuTextureAtlas = null;

        titleITTextureRegion = null;
        titleENTextureRegion = null;

        rankingTextureRegion = null;
        rewardsTextureRegion = null;
        sharingTextureRegion = null;
        infoTextureRegion = null;
        rateTextureRegion = null;
        rulesTextureRegion = null;
        rulesBoardTextureRegion = null;
        statsBoardTextureRegion = null;
        homeTextureRegion = null;

        mSubmenuTextureAtlas.unload();
        mSubmenuTextureAtlas = null;
        musicTextureRegion = null;

    }

    public void loadGameResources(){

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
        bombLoseTileTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(
                gameTextureAtlas, mActivity.getAssets(), "bombLoseTile.png");
        flagTileTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(
                gameTextureAtlas, mActivity.getAssets(), "flag.png");
        bombsTileTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(
                gameTextureAtlas, mActivity.getAssets(), "bombs.png");
        timerGameTileTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(
                gameTextureAtlas, mActivity.getAssets(), "timer.png");
        homeTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(
                gameTextureAtlas, mActivity.getAssets(), "home.png");
        replayTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(
                gameTextureAtlas, mActivity.getAssets(), "replay.png");
        currentLevelTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(
                gameTextureAtlas, mActivity.getAssets(), "currentLevel.png");
        pauseTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(
                gameTextureAtlas, mActivity.getAssets(), "pause.png");
        playTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(
                gameTextureAtlas, mActivity.getAssets(), "play.png");

        mSubBitmapTextureAtlas = new BuildableBitmapTextureAtlas(mActivity.getTextureManager(), 1024, 1024, TextureOptions.BILINEAR);

        gameOverTextTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(
                mSubBitmapTextureAtlas, mActivity.getAssets(), "gameOver.png");
        gameOverFailedTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(
                mSubBitmapTextureAtlas, mActivity.getAssets(), "gameOverFailed.png");
        gameOverQuestionTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(
                mSubBitmapTextureAtlas, mActivity.getAssets(), "gameOverQuestion.png");
        /*
        gameOverYesTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(
                mSubBitmapTextureAtlas, mActivity.getAssets(), "gameOverButtonYes.png");
        gameOverNoTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(
                mSubBitmapTextureAtlas, mActivity.getAssets(), "gameOverButtonNo.png");
                */
        buttonTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(
                mSubBitmapTextureAtlas, mActivity.getAssets(), "button.png");

        try {
            gameTextureAtlas.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(2, 0, 2));
            gameTextureAtlas.load();
            mSubBitmapTextureAtlas.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(2, 0, 2));
            mSubBitmapTextureAtlas.load();
        } catch (ITextureAtlasBuilder.TextureAtlasBuilderException e) {
            throw  new RuntimeException("Error while loading game textures", e);
        }

        try {
            soundExplosion = SoundFactory.createSoundFromAsset(mActivity.getSoundManager(), mActivity, "explosion.mp3");
            soundFlip = SoundFactory.createSoundFromAsset(mActivity.getSoundManager(), mActivity, "flip.mp3");
            soundTada = SoundFactory.createSoundFromAsset(mActivity.getSoundManager(), mActivity, "tada.mp3");

        } catch (IOException e) {
            throw new RuntimeException("Error while loading audio", e);
        }
    }

    public void unloadGameResources(){
        gameTextureAtlas.unload();
        gameTextureAtlas = null;
        tileTextureRegion = null;
        emptyTileTextureRegion = null;
        oneTileTextureRegion = null;
        twoTileTextureRegion = null;
        threeTileTextureRegion = null;
        fourTileTextureRegion = null;
        fiveTileTextureRegion = null;
        sixTileTextureRegion = null;
        sevenTileTextureRegion = null;
        eightTileTextureRegion = null;
        bombTileTextureRegion = null;
        bombLoseTileTextureRegion = null;
        timerGameTileTextureRegion = null;
        //homeTextureRegion = null;
        replayTextureRegion = null;
        currentLevelTextureRegion = null;

        mSubBitmapTextureAtlas.unload();
        mSubBitmapTextureAtlas = null;
        gameOverTextTextureRegion = null;
        gameOverYesTextureRegion = null;
        gameOverNoTextureRegion = null;

        soundExplosion.release();
        soundExplosion = null;
        soundTada.release();
        soundTada = null;
        soundFlip.release();
        soundFlip = null;
    }
}
