package com.aevobits.games.minesfield.entity;

import com.aevobits.games.minesfield.GameActivity;

import org.andengine.entity.Entity;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

/**
 * Created by vito on 01/03/16.
 */
public class Field extends Entity {

    private final float TILE_DIMENSION = 60f;

    public int rows;
    public int cols;
    private VertexBufferObjectManager vbom;
    private ITextureRegion tileTextureRegion;

    private float mInitialX;
    private float mInitialY;

    public Field(int rows, int cols, ITextureRegion tileTextureRegion, VertexBufferObjectManager vbom){

        this.rows = rows;
        this.cols = cols;
        this.tileTextureRegion = tileTextureRegion;
        this.vbom = vbom;

        final float halfFieldWidth = (TILE_DIMENSION * cols);
        this.mInitialX = ((GameActivity.SCREEN_WIDTH - halfFieldWidth) * 0.5f);

        final float halfFieldHeight = (TILE_DIMENSION * rows);
        this.mInitialY = GameActivity.SCREEN_HEIGHT - ((GameActivity.SCREEN_HEIGHT - halfFieldHeight) * 0.5f);

    }

    public void createTiles() {

        float tmpY = this.mInitialY;

        for (int i = 1; i <= this.rows; i++){
            float tmpX = this.mInitialX;
            for (int j = 1; j <= this.cols; j++) {
                Tile tile = new Tile(tmpX, tmpY, i, j, TILE_DIMENSION, TILE_DIMENSION, tileTextureRegion, vbom);
                attachChild(tile);
                tmpX = tmpX + TILE_DIMENSION;
            }
            tmpY = tmpY - TILE_DIMENSION;
        }

    }

}
