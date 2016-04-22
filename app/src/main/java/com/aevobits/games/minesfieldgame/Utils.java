package com.aevobits.games.minesfieldgame;

/**
 * Created by vito on 08/03/16.
 */
public class Utils {

    public static String secondsToString(int count){
        int minute = count/60;
        int seconds = count%60;
        String mnStr = "" + minute;
        String secStr = (seconds<10 ? "0" : "")+seconds;

        return mnStr + ":" + secStr;
    }
}
