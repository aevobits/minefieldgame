package com.aevobits.games.minesfieldgame.factory;

import com.aevobits.games.minesfieldgame.ResourceManager;
import com.aevobits.games.minesfieldgame.entity.Field;

import org.andengine.opengl.vbo.VertexBufferObjectManager;

/**
 * Created by vito on 01/03/16.
 */
public class FieldFactory {

    private static final FieldFactory INSTANCE = new FieldFactory();

    private VertexBufferObjectManager vbom;

    private FieldFactory(){};

    public static FieldFactory getInstance(){ return INSTANCE;}

    public void create(VertexBufferObjectManager vbom){
        this.vbom = vbom;

    }

    public Field createField(int rows, int cols){

        Field field = new Field(rows, cols, ResourceManager.getInstance().tileTextureRegion, vbom);
        field.createTiles();

        return field;
    }
}
