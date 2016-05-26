package com.aevobits.games.minesfieldgame;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import java.io.IOException;
import java.io.InputStream;

public class RulesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rules);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        // Get a support ActionBar corresponding to this toolbar
        ActionBar ab = getSupportActionBar();

        // Enable the Up button
        ab.setDisplayHomeAsUpEnabled(true);
        //ab.setIcon(R.drawable.home);

        ab.setHomeActionContentDescription(R.string.howToPlay);

        /*
        setContentView(R.layout.activity_rules);
        Typeface typeface = Typeface.createFromAsset(getAssets(), "font/Montserrat-Regular.ttf");
        ((TextView)findViewById(R.id.rulesText)).setTypeface(typeface);
        ((TextView)findViewById(R.id.rulesText)).setTextSize(16f);

        ((TextView)findViewById(R.id.howToPlay)).setTypeface(typeface);
        ((TextView)findViewById(R.id.howToPlay)).setTextSize(40f);

        ImageButton backToMenu = (ImageButton) findViewById(R.id.backToMenuButton);
        backToMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });*/
/*
        try {

            Bitmap bm = getBitmapFromAsset("gfx/home.png");
            backToMenu.setImageBitmap(bm);

        } catch (IOException e) {
            e.printStackTrace();
        }
*/
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }

    private Bitmap getBitmapFromAsset(String strName) throws IOException
    {
        AssetManager assetManager = getAssets();
        InputStream istr = assetManager.open(strName);
        Bitmap bitmap = BitmapFactory.decodeStream(istr);
        return bitmap;
    }
}
