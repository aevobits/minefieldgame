package com.aevobits.games.minesfieldgame.scene;

import org.andengine.opengl.view.RenderSurfaceView;
import org.andengine.ui.activity.BaseGameActivity;

/**
 * Created by vito on 14/03/16.
 */
public abstract class LayoutGameActivity
        extends BaseGameActivity
{
    protected abstract int getLayoutID();

    protected abstract int getRenderSurfaceViewID();

    protected void onSetContentView()
    {
        super.setContentView(getLayoutID());
        this.mRenderSurfaceView = ((RenderSurfaceView)findViewById(getRenderSurfaceViewID()));
        this.mRenderSurfaceView.setRenderer(this.mEngine, this);
    }
}
