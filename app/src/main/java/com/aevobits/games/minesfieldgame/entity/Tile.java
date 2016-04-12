package com.aevobits.games.minesfieldgame.entity;

import android.os.Message;
import android.os.SystemClock;
import android.util.Log;

import com.aevobits.games.minesfieldgame.BuildConfig;
import com.aevobits.games.minesfieldgame.GameActivity;
import com.aevobits.games.minesfieldgame.ResourceManager;
import com.aevobits.games.minesfieldgame.scene.MapManager;
import com.google.android.gms.games.Games;

import org.andengine.entity.sprite.Sprite;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

/**
 * Created by vito on 01/03/16.
 */
public class Tile extends Sprite{

    private int row;
    private int col;
    private float pX;
    private float pY;
    private float pWidth;
    private float pHeight;

    private MapManager mapManager;
    private ResourceManager mResourceManager;
    private GameActivity mActivity;

    private static float timeActionDown = 0;
    private static float timeActionUp = 0;

    //private ITextureRegion tileTextureRegion;


    public Tile(float pX, float pY, int row, int col, float pWidth, float pHeight, ITextureRegion tileTextureRegion, VertexBufferObjectManager vbom){
        super(pX, pY, pWidth, pHeight, tileTextureRegion, vbom);

        mapManager = MapManager.getInstance();
        mResourceManager = ResourceManager.getInstance();
        mActivity = mResourceManager.mActivity;

        setRow(row);
        setCol(col);

        this.pX = pX;
        this.pY = pY;
        this.pWidth = pWidth;
        this.pHeight = pHeight;

    }

    @Override
    public synchronized boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {

        switch (pSceneTouchEvent.getAction()){
            case TouchEvent.ACTION_DOWN:{
                ResourceManager.getInstance().startClick = SystemClock.elapsedRealtime();
                return true;
            }
            case TouchEvent.ACTION_UP:{
                int row = this.getRow() - 1;
                int col = this.getCol() - 1;
                float x = this.getX();
                float y = this.getY();
                ResourceManager.getInstance().endClick = SystemClock.elapsedRealtime();
                float elapsedTime = ResourceManager.getInstance().endClick - ResourceManager.getInstance().startClick;
                if(BuildConfig.DEBUG) Log.v("ElapsedTime:", elapsedTime + "");
                boolean click = (Float.compare(elapsedTime,450.0f)<=0);
                if(click) {
                    if (!mapManager.bombsMap[row][col]) {
                        if (mapManager.map[row][col] == 0) {
                            mapManager.freeTiles(row, col, x, y, this.pWidth, this.pHeight, mapManager.flagsBombs, mapManager.mBombsHudText);
                        } else {
                            mapManager.switchTile(row, col, x, y, this.pWidth, this.pHeight);
                            mapManager.shownMap[row][col] = true;

                        }
                        mapManager.flagMap[row][col] = false;
                        if(mapManager.hasWon()){
                            mapManager.setGameOver(true);
                            mapManager.setWin(true);
                            int newScore = secondsToScore(mapManager.seconds);
                            if(newScore>mResourceManager.mActivity.getHiscore()){
                                mResourceManager.mActivity.setHiScore(newScore);
                                if(mActivity.getGameHelper().isSignedIn())
                                    Games.Leaderboards.submitScoreImmediate(mActivity.getApiClient(), "CgkIoq7_rKsbEAIQAQ", newScore);
                            }
                        }
                    } else {
                        //mapManager.switchBomb(row, col, x, y, this.pWidth, this.pHeight);
                        mapManager.switchBombs(this.pWidth, this.pHeight);
                        mResourceManager.mActivity.playSound(mResourceManager.soundExplosion);
                        mapManager.setGameOver(true);
                        mapManager.setWin(false);
                    }
                    return true;
                }else{

                    if (mapManager.flagMap[row][col]) {

                        mapManager.switchFlag(row, col, x, y, this.pWidth, this.pHeight, false);
                        mapManager.flagMap[row][col] = false;
                        mapManager.flagsBombs++;
                        mapManager.mBombsHudText.setText(String.valueOf(mapManager.flagsBombs));

                    } else {
                        if(mapManager.flagsBombs>0) {
                            mapManager.switchFlag(row, col, x, y, this.pWidth, this.pHeight, true);
                            mapManager.flagMap[row][col] = true;
                            mapManager.flagsBombs--;
                            mapManager.mBombsHudText.setText(String.valueOf(mapManager.flagsBombs));
                        }else{
                            Message msg = mResourceManager.mActivity.handler.obtainMessage();
                            msg.arg1 = 1;
                            msg.arg2 = mapManager.bombs;
                            mResourceManager.mActivity.handler.sendMessage(msg);
                        }
                    }
                    if (mapManager.hasWon()) {
                        mapManager.setGameOver(true);
                        mapManager.setWin(true);
                        int newScore = secondsToScore(mapManager.seconds);
                        if (newScore > mResourceManager.mActivity.getHiscore()) {
                            mResourceManager.mActivity.setHiScore(newScore);
                            if(mActivity.getGameHelper().isSignedIn())
                                Games.Leaderboards.submitScoreImmediate(mActivity.getApiClient(), "CgkIoq7_rKsbEAIQAQ", newScore);
                        }
                    }

                    return true;
                }
            }
            default:{
                return false;
            }
        }
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }

    private int secondsToScore(int seconds){
        return (150 - (seconds / 4)) * MapManager.getInstance().level;
    }

}
