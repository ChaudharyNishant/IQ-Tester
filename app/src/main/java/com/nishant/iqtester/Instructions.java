//Created by Nishant Chaudhary
//https://github.com/ChaudharyNishant

package com.nishant.iqtester;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

public class Instructions extends Activity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON, WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructions);
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        Music.mediaPlayer.start();
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        Music.mediaPlayer.pause();
    }

    @Override
    public void onBackPressed()
    {
        Music.btnPressed.seekTo(0);
        Music.btnPressed.start();
        super.onBackPressed();
    }

    public void gotoMainMenu(View v)
    {
        Music.mediaPlayer.pause();
        onBackPressed();
    }
}

//Created by Nishant Chaudhary
//https://github.com/ChaudharyNishant
