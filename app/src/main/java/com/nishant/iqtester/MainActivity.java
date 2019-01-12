package com.nishant.iqtester;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.VideoView;

public class MainActivity extends Activity
{
    VideoView video;
    MediaPlayer mediaPlayer;
    MediaPlayer btnPressed;
    MediaPlayer mediaPlayerTemp;
    Music m;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON, WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                try
                {
                    mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.bg_music);
                    mediaPlayer.setLooping(true);
                    btnPressed = MediaPlayer.create(getApplicationContext(), R.raw.btn_press);
                    btnPressed.setLooping(false);
                    Thread.sleep(500);
                    m = new Music(mediaPlayer, btnPressed);
                }
                catch(InterruptedException e)
                {
                    e.printStackTrace();
                }
            }
        }).start();

        video = (VideoView)findViewById(R.id.videoView);
        Uri videoUri = Uri.parse("android.resource://" + getPackageName() + "/raw/" + "intro");
        video.setVideoURI(videoUri);
        video.start();

        mediaPlayerTemp = MediaPlayer.create(getApplicationContext(), R.raw.bg_music);
        mediaPlayerTemp.setLooping(true);
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        video.start();
        mediaPlayerTemp.start();
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        video.pause();
        mediaPlayerTemp.pause();
    }

    @Override
    public void onBackPressed()
    {
    }

    public void goToMainMenu(View v)
    {
        Music.btnPressed.start();
        Intent i = new Intent(getApplicationContext(), HomeScreen.class);
        startActivity(i);
    }
}
