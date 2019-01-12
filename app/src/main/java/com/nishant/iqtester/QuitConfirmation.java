package com.nishant.iqtester;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

public class QuitConfirmation extends Activity
{
    String parentActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON, WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quit_confirmation);

        parentActivity = getIntent().getExtras().getString("QUIT_FROM");
        if(parentActivity.equals("HomeScreen"))
        {
            TextView allProgress = (TextView)findViewById(R.id.allProgress);
            TextView sure = (TextView)findViewById(R.id.sure);
            allProgress.setText("");
            sure.setText("Are you sure to exit?");
        }
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
        Music.mediaPlayer.pause();
        Music.btnPressed.seekTo(0);
        Music.btnPressed.start();
        super.onBackPressed();
    }

    public void onClickNo(View v)
    {
        onBackPressed();
    }

    public void onClickYes(View v)
    {
        Music.btnPressed.seekTo(0);
        Music.btnPressed.start();
        if(parentActivity.equals("Game"))
        {
            Music.mediaPlayer.pause();
            Intent i = new Intent(getApplicationContext(), HomeScreen.class);
            startActivity(i);
        }
        else if(parentActivity.equals("HomeScreen"))
        {
            Music.mediaPlayer.stop();
            Music.mediaPlayer.release();
            finishAffinity();
            System.exit(0);
        }
    }
}
