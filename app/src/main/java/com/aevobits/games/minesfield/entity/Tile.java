package com.aevobits.games.minesfield.entity;

import android.os.Message;
import android.os.SystemClock;
import android.util.Log;

import com.aevobits.games.minesfield.BuildConfig;
import com.aevobits.games.minesfield.GameActivity;
import com.aevobits.games.minesfield.manager.ResourceManager;
import com.aevobits.games.minesfield.scene.MapManager;
import com.aevobits.games.minesfield.manager.PlayerDataManager;
import com.aevobits.games.minesfield.util.UtilsGPS;

import org.andengine.entity.sprite.Sprite;
import org.andengine.input.touch.TouchEvent;
import org.andengine.input.touch.detector.HoldDetector;
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
    public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {

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
                boolean click = (Float.compare(elapsedTime,400.0f)<=0);
                if(click) {
                    if (mapManager.flagMap[row][col]) {

                        mapManager.switchFlag(row, col, x, y, this.pWidth, this.pHeight, false);
                        mapManager.flagMap[row][col] = false;
                        mapManager.flagsBombs++;
                        mapManager.mBombsHudText.setText(String.valueOf(mapManager.flagsBombs));
                        return true;
                    }
                    if (!mapManager.bombsMap[row][col]) {
                        if (mapManager.map[row][col] == 0) {
                            if (!mapManager.shownMap[row][col]) {
                                mActivity.playSound(mResourceManager.soundFlip);
                            }
                            mapManager.freeTiles(row, col, x, y, this.pWidth, this.pHeight, mapManager.mBombsHudText);
                        } else {
                            if (!mapManager.shownMap[row][col]) {
                                mActivity.playSound(mResourceManager.soundFlip);
                            }
                            mapManager.switchTile(row, col, x, y, this.pWidth, this.pHeight);
                            mapManager.shownMap[row][col] = true;

                        }
                        if (mapManager.flagMap[row][col]==true){
                            mapManager.flagsBombs++;
                            mapManager.mBombsHudText.setText(String.valueOf(mapManager.flagsBombs));
                        }
                        mapManager.flagMap[row][col] = false;

                        if(mapManager.hasWon()){
                            mActivity.playSound(mResourceManager.soundTada);
                            mapManager.setGameOver(true);
                            mapManager.setWin(true);
                            int level = mapManager.level;
                            Integer won = Integer.parseInt(PlayerDataManager.getInstance().getData("GamesWon" + level)) + 1;
                            PlayerDataManager.getInstance().updateData("GamesWon" + level, won.toString());
                            UtilsGPS.unlockAchievement(mActivity, mapManager.level);
                            mapManager.newScore = secondsToScore(mapManager.seconds);
                            float oldScore = Float.parseFloat(PlayerDataManager.getInstance().getData("HiScore"));
                            if(mapManager.newScore>oldScore){
                                PlayerDataManager.getInstance().updateData("HiScore",new Float(mapManager.newScore).toString());
                                UtilsGPS.submitScoreToLeaderboard(mActivity, mapManager.newScore);
                            }
                            if (mapManager.level<16){
                                mapManager.level = level + 1;
                                PlayerDataManager.getInstance().updateData("MaxLevel", new Integer(level + 1).toString());
                            }
                        }
                    } else {
                        mapManager.switchBombs(this.pWidth, this.pHeight, row, col);
                        mActivity.playSound(mResourceManager.soundExplosion);
                        mapManager.newScore = secondsToScore(mapManager.seconds);
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

                    } else if (!mapManager.shownMap[row][col]){
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
                        mActivity.playSound(mResourceManager.soundTada);
                        mapManager.setGameOver(true);
                        mapManager.setWin(true);
                        int level = mapManager.level;
                        Integer won = Integer.parseInt(PlayerDataManager.getInstance().getData("GamesWon" + level)) + 1;
                        PlayerDataManager.getInstance().updateData("GamesWon" + level, won.toString());
                        UtilsGPS.unlockAchievement(mActivity, mapManager.level);
                        mapManager.newScore = secondsToScore(mapManager.seconds);
                        float oldScore = Float.parseFloat(PlayerDataManager.getInstance().getData("HiScore"));
                        if(mapManager.newScore>oldScore){
                            PlayerDataManager.getInstance().updateData("HiScore",new Float(mapManager.newScore).toString());
                            UtilsGPS.submitScoreToLeaderboard(mActivity, mapManager.newScore);
                        }
                        if (mapManager.level<16){
                            mapManager.level = level + 1;
                            PlayerDataManager.getInstance().updateData("MaxLevel", new Integer(level + 1).toString());
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

    private float secondsToScore(int seconds){
        float count3bv = mapManager.count3BV();
        float score = (count3bv / seconds)*1000 * MapManager.getInstance().level;
        //return (150 - (seconds / 4)) * MapManager.getInstance().level;
        return score;
    }
}
