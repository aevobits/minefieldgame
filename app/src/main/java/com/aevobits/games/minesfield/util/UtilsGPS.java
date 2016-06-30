package com.aevobits.games.minesfield.util;

import android.widget.Toast;

import com.aevobits.games.minesfield.GameActivity;
import com.aevobits.games.minesfield.R;
import com.google.android.gms.games.Games;

/**
 * Created by vito on 17/06/16.
 */
public class UtilsGPS {

    public static void unlockAchievement(final GameActivity mActivity, int level){

        if(mActivity.getGameHelper().isSignedIn()) {
            if (mActivity.getGamesWon(level) >= 1){
                int achievement_id = mActivity.getResources().getIdentifier("achievement_" + level,"string",
                        mActivity.getPackageName());
                String achievement_string = mActivity.getString(achievement_id);
                Games.Achievements.unlock(mActivity.getApiClient(), achievement_string);
            }

            if (mActivity.getGamesWon(level) >= 10){
                int achievement_id = mActivity.getResources().getIdentifier("achievement_" + level + 4,"string",
                        mActivity.getPackageName());
                String achievement_string = mActivity.getString(achievement_id);
                Games.Achievements.unlock(mActivity.getApiClient(), achievement_string);
            }
        }else {
            mActivity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    CharSequence text = mActivity.getString(R.string.gamehelper_sign_in_failed);
                    int duration = Toast.LENGTH_SHORT;
                    Toast.makeText(mActivity.getApplicationContext(), text, duration).show();
                }
            });
        }
    }

    public static void submitScoreToLeaderboard(final GameActivity mActivity, int level, float score){

        if(mActivity.getGameHelper().isSignedIn()) {
            int leaderboard_id = mActivity.getResources().getIdentifier("leaderboard_" + level,"string",
                    mActivity.getPackageName());
            String leaderboard_string = mActivity.getString(leaderboard_id);
            Games.Leaderboards.submitScoreImmediate(mActivity.getApiClient(), leaderboard_string, (long) (score * 100));
        }else {
            mActivity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    CharSequence text = mActivity.getString(R.string.gamehelper_sign_in_failed);
                    int duration = Toast.LENGTH_SHORT;
                    Toast.makeText(mActivity.getApplicationContext(), text, duration).show();
                }
            });
        }
    }
}
