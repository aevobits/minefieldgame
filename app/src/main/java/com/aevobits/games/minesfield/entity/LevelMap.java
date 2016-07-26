package com.aevobits.games.minesfield.entity;

/**
 * Created by vito on 12/07/16.
 */
public class LevelMap {
    private int rows;
    private int cols;
    private int bombs;

    public LevelMap(int rows, int cols, int bombs) {
        this.rows = rows;
        this.cols = cols;
        this.bombs = bombs;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public int getCols() {
        return cols;
    }

    public void setCols(int cols) {
        this.cols = cols;
    }

    public int getBombs() {
        return bombs;
    }

    public void setBombs(int bombs) {
        this.bombs = bombs;
    }
}
