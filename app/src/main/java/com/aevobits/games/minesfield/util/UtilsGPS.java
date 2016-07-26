package com.aevobits.games.minesfield.util;

import android.widget.Toast;

import com.aevobits.games.minesfield.GameActivity;
import com.aevobits.games.minesfield.R;
import com.aevobits.games.minesfield.manager.PlayerDataManager;
import com.aevobits.games.minesfield.manager.ResourceManager;
import com.google.android.gms.games.Games;

/**
 * Created by vito on 17/06/16.
 */
public class UtilsGPS {

    public static void unlockAchievement(final GameActivity mActivity, int level){

        if(mActivity.getGameHelper().isSignedIn()) {

            int achievement_id = mActivity.getResources().getIdentifier("achievement2_" + level,"string",
                    mActivity.getPackageName());
            String achievement_string = mActivity.getString(achievement_id);
            Games.Achievements.unlock(mActivity.getApiClient(), achievement_string);

        }else {
            mActivity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    CharSequence text = mActivity.getString(R.string.gamehelper_sign_in_failed);
                    int duration = Toast.LENGTH_LONG;
                    Toast.makeText(mActivity.getApplicationContext(), text, duration).show();
                }
            });
        }

    }

    public static void submitScoreToLeaderboard(final GameActivity mActivity, float score){

        if(mActivity.getGameHelper().isSignedIn()) {
            int leaderboard_id = mActivity.getResources().getIdentifier("leaderboard2","string",
                    mActivity.getPackageName());
            String leaderboard_string = mActivity.getString(leaderboard_id);
            Games.Leaderboards.submitScoreImmediate(mActivity.getApiClient(), leaderboard_string, (long) (score * 100));
        }else {
            mActivity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    CharSequence text = mActivity.getString(R.string.gamehelper_sign_in_failed);
                    int duration = Toast.LENGTH_LONG;
                    Toast.makeText(mActivity.getApplicationContext(), text, duration).show();
                }
            });
        }

    }
}
