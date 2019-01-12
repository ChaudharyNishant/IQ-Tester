//Created by Nishant Chaudhary
//https://github.com/ChaudharyNishant

package com.nishant.iqtester;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

public class HomeScreen extends Activity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON, WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
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
        quit(new Button(this));
    }

    public void playGame(View v)
    {
        Music.mediaPlayer.pause();
        Music.btnPressed.seekTo(0);
        Music.btnPressed.start();
        Intent i = new Intent(getApplicationContext(), Game.class);
        HomeScreen.this.startActivity(i);
    }

    public void showInstructions(View v)
    {
        Music.mediaPlayer.pause();
        Music.btnPressed.seekTo(0);
        Music.btnPressed.start();
        Intent i = new Intent(getApplicationContext(), Instructions.class);
        HomeScreen.this.startActivity(i);
    }

    public void quit(View v)
    {
        Music.mediaPlayer.pause();
        Music.btnPressed.seekTo(0);
        Music.btnPressed.start();
        Intent i = new Intent(getApplicationContext(), QuitConfirmation.class);
        i.putExtra("QUIT_FROM", "HomeScreen");
        startActivity(i);
    }
}

//Created by Nishant Chaudhary
//https://github.com/ChaudharyNishant
