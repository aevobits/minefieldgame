package com.aevobits.games.minesfieldgame.scene;

import android.util.Log;

import com.aevobits.games.minesfieldgame.BuildConfig;
import com.aevobits.games.minesfieldgame.ResourceManager;
import com.aevobits.games.minesfieldgame.entity.Tile;

import org.andengine.entity.text.Text;
import org.andengine.opengl.texture.region.ITextureRegion;

/**
 * Created by vito on 04/03/16.
 */
public class MapManager {

    public final static MapManager INSTANCE = new MapManager();

    private ResourceManager res = ResourceManager.getInstance();
    public int map[][];
    public boolean bombsMap[][];
    public boolean shownMap[][];
    public boolean flagMap[][];
    public int bombs;
    public int rows;
    public int cols;
    public float pXInit;
    public float pYInit;
    public int level;
    public int flagsBombs;
    public Text mBombsHudText;
    public int seconds;
    public int newScore;
    private boolean gameOver = false;
    private boolean win;
    private GameScene gameScene;

    private MapManager(){}

    public static MapManager getInstance(){return INSTANCE;}

    public void create(int rows, int cols, int bombs, float pXInit, float pYInit, GameScene gameScene){

        this.map = new int[rows][cols];
        this.bombsMap = new boolean[rows][cols];
        this.shownMap = new boolean[rows][cols];
        this.flagMap = new boolean[rows][cols];
        this.bombs = bombs;
        this.rows = rows;
        this.cols = cols;
        this.pXInit = pXInit;
        this.pYInit = pYInit;
        this.flagsBombs = bombs;
        this.seconds = 0;
        this.newScore = 0;
        this.gameScene = gameScene;

        generateMap();
    }

    private void generateMap(){

        int mines = bombs;

        while(mines > 0) {
            int randX = (int) (Math.random() * rows);
            int randY = (int) (Math.random() * cols);

            if (!bombsMap[randX][randY]) {
                if (isIn(randX - 1, randY - 1))
                    map[randX - 1][randY - 1]++;

                if (isIn(randX - 1, randY))
                    map[randX - 1][randY]++;

                if (isIn(randX - 1, randY + 1))
                    map[randX - 1][randY + 1]++;

                if (isIn(randX, randY - 1))
                    map[randX][randY - 1]++;

                if (isIn(randX, randY + 1))
                    map[randX][randY + 1]++;

                if (isIn(randX + 1, randY - 1))
                    map[randX + 1][randY - 1]++;

                if (isIn(randX + 1, randY))
                    map[randX + 1][randY]++;

                if (isIn(randX + 1, randY + 1))
                    map[randX + 1][randY + 1]++;

                bombsMap[randX][randY] = true;
                mines--;
            }

        }
    }

    private boolean isIn(int row, int col)
    {
        return (row >= 0 && row < rows && col >= 0 && col < cols);
    }

    public void freeTiles(int row, int col, float pX, float pY, float width, float height, int flagsBombs, Text mHudText){

        if(isIn(row, col))
        {
            if(!shownMap[row][col] && !bombsMap[row][col])
            {
                shownMap[row][col] = true;

                if(flagMap[row][col]){
                    flagMap[row][col] = false;
                    flagsBombs++;
                    mHudText.setText(String.valueOf(flagsBombs));
                }

                if(map[row][col] >=0)
                    switchTile(row, col, pX, pY, width, height);

                if(map[row][col] == 0)
                {
                    for(int r = -1; r <= 1; r++)
                    {
                        for(int c = -1; c <= 1; c++)
                        {
                            freeTiles(row + r, col + c, pX + (c * width), pY - (r * height), width, height, flagsBombs, mHudText);
                        }
                    }
                }
            }
        }
    }

    public void switchTile(int row, int col, float pX, float pY, float width, float height){
        int caseTile = map[row][col];
        ITextureRegion textureRegion;
        switch (caseTile){
            case 0:{
                textureRegion = res.emptyTileTextureRegion;
                break;
            }
            case 1:{
                textureRegion = res.oneTileTextureRegion;
                break;
            }
            case 2:{
                textureRegion = res.twoTileTextureRegion;
                break;
            }
            case 3:{
                textureRegion = res.threeTileTextureRegion;
                break;
            }
            case 4:{
                textureRegion = res.fourTileTextureRegion;
                break;
            }
            case 5:{
                textureRegion = res.fiveTileTextureRegion;
                break;
            }
            case 6:{
                textureRegion = res.sixTileTextureRegion;
                break;
            }
            case 7:{
                textureRegion = res.sevenTileTextureRegion;
                break;
            }
            case 8:{
                textureRegion = res.eightTileTextureRegion;
                break;
            }
            default:{
                textureRegion = res.emptyTileTextureRegion;
            }
        }
        Tile tile = new Tile(pX, pY, row, col, width, height, textureRegion, res.mActivity.getVertexBufferObjectManager());
        this.gameScene.attachChild(tile);
        res.mActivity.playSound(res.soundFlip);

    }

    public void switchBomb(int row, int col, float pX, float pY, final float width, final float height){
        Tile tile = new Tile(pX, pY, row, col, width, height, res.bombTileTextureRegion, res.mActivity.getVertexBufferObjectManager());
        this.gameScene.attachChild(tile);
    }

    public void switchBombs(final float width, final float height){
        float tmpY = this.pYInit;
        for (int i = 1; i <= this.rows; i++){
            float tmpX = this.pXInit;
            for (int j = 1; j <= this.cols; j++) {
                if(this.bombsMap[i-1][j-1]){
                    Tile tile = new Tile(tmpX, tmpY, i, j, width, height, res.bombTileTextureRegion, res.mActivity.getVertexBufferObjectManager());
                    this.gameScene.attachChild(tile);
                }

                tmpX = tmpX + width;
            }
            tmpY = tmpY - height;
        }


    }

    public void switchFlag(int row, int col, float pX, float pY, final float width, final float height, boolean flag){
        Tile tile;
        if(flag) {
            tile = new Tile(pX, pY, row, col, width, height, res.flagTileTextureRegion, res.mActivity.getVertexBufferObjectManager());
        }else{
            tile = new Tile(pX, pY, row, col, width, height, res.tileTextureRegion, res.mActivity.getVertexBufferObjectManager());
        }
        this.gameScene.attachChild(tile);
    }

    public boolean hasWon()
    {
        int nb = 0;

        for(int i = 0; i < rows; i++)
        {
            for(int j = 0; j < cols; j++)
            {
                if(shownMap[i][j])
                    nb++;
            }
        }

        int freeTile = (rows*cols - bombs);
        boolean won = false;

        if(nb == freeTile){
            won = true;
        }
        return won;
    }

    public void setGameOver(boolean gameOver){
        this.gameOver = gameOver;
    }

    public boolean getGameOver(){
        return this.gameOver;
    }

    public void setWin(boolean win){
        this.win = win;
    }

    public boolean isWin(){
        return this.win;
    }

}
