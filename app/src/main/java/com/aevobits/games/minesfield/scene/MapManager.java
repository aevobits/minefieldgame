package com.aevobits.games.minesfield.scene;

import com.aevobits.games.minesfield.manager.ResourceManager;
import com.aevobits.games.minesfield.entity.LevelMap;
import com.aevobits.games.minesfield.entity.Tile;

import org.andengine.entity.text.Text;
import org.andengine.opengl.texture.region.ITextureRegion;

import java.util.Arrays;

/**
 * Created by vito on 04/03/16.
 */
public class MapManager {

    public final static MapManager INSTANCE = new MapManager();

    private ResourceManager res = ResourceManager.getInstance();
    public LevelMap levels[][];
    public int map[][];
    public char mapMarks[][];
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
    public float newScore;
    private boolean gameOver = false;
    private boolean win;
    private GameScene gameScene;
    private int count = 0;

    private MapManager(){}

    public static MapManager getInstance(){return INSTANCE;}

    public void create(GameScene gameScene){
        generateLevels();
        this.bombs = levels[(this.level-1)/4][((this.level-1) % 4)].getBombs();
        this.rows = levels[(this.level-1)/4][((this.level-1) % 4)].getRows();
        this.cols = levels[(this.level-1)/4][((this.level-1) % 4)].getCols();
        this.map = new int[this.rows][this.cols];
        this.bombsMap = new boolean[this.rows][this.cols];
        this.shownMap = new boolean[this.rows][this.cols];
        this.flagMap = new boolean[this.rows][this.cols];
        this.flagsBombs = this.bombs;
        this.seconds = 0;
        this.newScore = 0;
        this.gameScene = gameScene;
        this.mapMarks = new char[this.rows][this.cols];

        for (char[] row:this.mapMarks){
            Arrays.fill(row, 'N');
        }

        generateMap();
    }

    private void generateLevels(){
        int bombs = 10;
        int rows = 10;
        int cols = 10;
        this.levels = new LevelMap[4][4];
        for (int i=0;i<4;i++){
            for (int j=0;j<4;j++){
                this.levels[i][j] = new LevelMap(rows, cols, bombs);
                if (i<2) {
                    bombs += 2;
                }else{
                    bombs+=4;
                }
                if (((i + j) % 2) == 1){
                    if (rows < 15){
                        if ((rows == 12) && (cols == 10)){
                            cols += 1;
                        }else if ((rows == 13) && (cols == 11)) {
                            cols += 1;
                        }else {
                            rows += 1;
                        }
                    }else {
                        cols += 1;
                    }
                }
            }
        }
    }

    private void generateMap(){

        int mines = this.bombs;

        while(mines > 0) {
            int randX = (int) (Math.random() * this.rows);
            int randY = (int) (Math.random() * this.cols);

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
        return (row >= 0 && row < this.rows && col >= 0 && col < this.cols);
    }

    public void freeTiles(int row, int col, float pX, float pY, float width, float height, Text mHudText){

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
                            freeTiles(row + r, col + c, pX + (c * width), pY - (r * height), width, height, mHudText);
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
    }

    public void switchBomb(int row, int col, float pX, float pY, final float width, final float height){
        Tile tile = new Tile(pX, pY, row, col, width, height, res.bombTileTextureRegion, res.mActivity.getVertexBufferObjectManager());
        this.gameScene.attachChild(tile);
    }

    public void switchBombs(final float width, final float height, int row, int col){
        float tmpY = this.pYInit;
        for (int i = 1; i <= this.rows; i++){
            float tmpX = this.pXInit;
            for (int j = 1; j <= this.cols; j++) {
                if(this.bombsMap[i-1][j-1]){
                    Tile tile;
                    if (((i - 1) == row) && ((j - 1) == col)){
                        tile = new Tile(tmpX, tmpY, i, j, width, height, res.bombLoseTileTextureRegion, res.mActivity.getVertexBufferObjectManager());
                    }else{
                        tile = new Tile(tmpX, tmpY, i, j, width, height, res.bombTileTextureRegion, res.mActivity.getVertexBufferObjectManager());
                    }
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

        for(int i = 0; i < this.rows; i++)
        {
            for(int j = 0; j < this.cols; j++)
            {
                if(shownMap[i][j])
                    nb++;
            }
        }

        int freeTile = (this.rows*this.cols - this.bombs);
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

    public int count3BV(){
        count = 0;
        for (int i=0; i < this.rows; i++){
            for (int j=0; j < this.cols; j++){
                if ((map[i][j]==0)&&(!bombsMap[i][j])){
                    if(mapMarks[i][j]=='C'){
                        continue;
                    }
                    count = count + 1;
                    mapMarks[i][j]='C';
                    floodFillMark(i, j);
                }
            }
        }


        for (int i=0; i < this.rows; i++) {
            for (int j = 0; j < this.cols; j++) {
                if ((mapMarks[i][j]=='N')&&(!bombsMap[i][j])){
                    count=count + 1;
                }
            }
        }
        return count;
    }

    private void floodFillMark(int row, int col){
        for(int r = -1; r <= 1; r++){
            for(int c = -1; c <= 1; c++){
                if(isIn(row + r, col + c)) {
                    if (mapMarks[row + r][col + c] != 'C') {
                        mapMarks[row + r][col + c] = 'C';
                        if (map[row + r][col + c] == 0) {
                            floodFillMark(row + r, col + c);
                        }
                    }
                }
            }
        }
    }

}
