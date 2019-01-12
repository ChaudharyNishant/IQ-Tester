//Created by Nishant Chaudhary
//https://github.com/ChaudharyNishant

package com.nishant.iqtester;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

public class Game extends Activity
{
    boolean first;
    boolean selectSecond;
    int lastI;
    int lastJ;
    Drawable normal;
    Drawable green;
    Drawable yellow;
    TextView remaining;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON, WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.activity_game);
        super.onCreate(savedInstanceState);

        first = true;
        selectSecond = false;
        lastI = lastJ = -1;

        Button temp = new Button(this);
        temp.setBackgroundResource(R.drawable.btn_normal);
        normal = temp.getBackground();
        temp.setBackgroundResource(R.drawable.btn_green);
        green = temp.getBackground();
        temp.setBackgroundResource(R.drawable.btn_yellow);
        yellow = temp.getBackground();

        Button btn[][] = getBtn();
        for(int i = 0; i < 5; i++)
            for(int j = 0; j < i + 1; j++)
                btn[i][j].setBackground(normal);

        remaining = (TextView)findViewById(R.id.moreToGo);
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
        if(remaining.getText().equals("GOT IT!!") || remaining.getText().equals("15 remaining on board"))
            super.onBackPressed();
        else
            iQuit(new Button(this));
    }

    public void reset(View v)
    {
        Music.btnPressed.seekTo(0);
        Music.btnPressed.start();
        first = true;
        selectSecond = false;
        lastI = lastJ = -1;

        Button btn[][] = getBtn();
        for(int i = 0; i < 5; i++)
            for(int j = 0; j < i + 1; j++)
            {
                btn[i][j].setText("X");
                btn[i][j].setBackground(normal);
            }
        remaining.setText("15 remaining on board");
    }

    public void iQuit(View v)
    {
        if(remaining.getText().equals("GOT IT!!") || remaining.getText().equals("15 remaining on board"))
            onBackPressed();
        else
        {
            Music.mediaPlayer.pause();
            Music.btnPressed.seekTo(0);
            Music.btnPressed.start();
            Intent i = new Intent(getApplicationContext(), QuitConfirmation.class);
            i.putExtra("QUIT_FROM", "Game");
            startActivity(i);
        }
    }

    public void change(View v)
    {
        Button btn[][] = getBtn();
        int x = getX(v);
        int y = getY(v);

        if(first)
        {
            btn[x][y].setText("");
            btn[x][y].setBackground(yellow);
            btn[x][y].setBackground(normal);
            first = false;
            remaining.setText("14 remaining on board");
        }
        else
        {
            if(btn[x][y].getText().equals("X") && selectSecond == false && !remaining.getText().equals("GOT IT!!"))
            {
                btn[x][y].setBackground(yellow);
                selectSecond = true;
                lastI = x;
                lastJ = y;
                findPossiblePositions(btn, x, y);
            }
            else if(btn[x][y].getText().equals("X") && selectSecond == true)
            {
                for(int i1 = 0; i1 < 5; i1++)
                    for(int j1 = 0; j1 < btn[i1].length; j1++)
                        btn[i1][j1].setBackground(normal);
                btn[x][y].setBackground(yellow);
                selectSecond = true;
                lastI = x;
                lastJ = y;
                findPossiblePositions(btn, x, y);
            }
            else if(selectSecond == true && btn[x][y].getBackground().equals(green))
            {
                int a = (x + lastI) / 2;
                int b = (y + lastJ) / 2;
                if(btn[a][b].getText().equals("X"))
                {
                    for(int i1 = 0; i1 < 5; i1++)
                        for(int j1 = 0; j1 < btn[i1].length; j1++)
                            btn[i1][j1].setBackground(normal);
                    btn[lastI][lastJ].setText("");
                    btn[x][y].setText("X");
                    selectSecond = false;
                    btn[a][b].setText("");
                    String str = remaining.getText().toString();
                    remaining.setText(Integer.parseInt(str.substring(0, str.indexOf(" "))) - 1 + " remaining on board");
                }
            }
        }

        if(remaining.getText().equals("1 remaining on board"))
        {
            Music.btnPressed.seekTo(0);
            Music.btnPressed.start();
            btn[x][y].setBackground(green);
            remaining.setText("GOT IT!!");
            Button iQuit = (Button)findViewById(R.id.iQuit);
            iQuit.setText("MAIN MENU");
        }
    }

    public Button[][] getBtn()
    {
        Button btn[][] = new Button[5][];
        for(int i = 0; i < 5; i++)
            btn[i] = new Button[i + 1];
        btn[0][0] = (Button)findViewById(R.id.a00);
        btn[1][0] = (Button)findViewById(R.id.a10);
        btn[1][1] = (Button)findViewById(R.id.a11);
        btn[2][0] = (Button)findViewById(R.id.a20);
        btn[2][1] = (Button)findViewById(R.id.a21);
        btn[2][2] = (Button)findViewById(R.id.a22);
        btn[3][0] = (Button)findViewById(R.id.a30);
        btn[3][1] = (Button)findViewById(R.id.a31);
        btn[3][2] = (Button)findViewById(R.id.a32);
        btn[3][3] = (Button)findViewById(R.id.a33);
        btn[4][0] = (Button)findViewById(R.id.a40);
        btn[4][1] = (Button)findViewById(R.id.a41);
        btn[4][2] = (Button)findViewById(R.id.a42);
        btn[4][3] = (Button)findViewById(R.id.a43);
        btn[4][4] = (Button)findViewById(R.id.a44);
        return btn;
    }

    public int getX(View v)
    {
        switch(v.getId())
        {
            case R.id.a00:  return 0;
            case R.id.a10:
            case R.id.a11:  return 1;
            case R.id.a20:
            case R.id.a21:
            case R.id.a22:  return 2;
            case R.id.a30:
            case R.id.a31:
            case R.id.a32:
            case R.id.a33:  return 3;
            case R.id.a40:
            case R.id.a41:
            case R.id.a42:
            case R.id.a43:
            case R.id.a44:  return 4;
            default:        return -1;
        }
    }

    public int getY(View v)
    {
        switch(v.getId())
        {
            case R.id.a44:  return 4;
            case R.id.a33:
            case R.id.a43:  return 3;
            case R.id.a22:
            case R.id.a32:
            case R.id.a42:  return 2;
            case R.id.a11:
            case R.id.a21:
            case R.id.a31:
            case R.id.a41:  return 1;
            case R.id.a00:
            case R.id.a10:
            case R.id.a20:
            case R.id.a30:
            case R.id.a40:  return 0;
            default:        return -1;
        }
    }

    public void findPossiblePositions(Button btn[][], int x, int y)
    {
        if(y - 2 >= 0 && btn[x][y - 2].getText().equals("") && btn[x][y - 1].getText().equals("X"))
            btn[x][y - 2].setBackground(green);
        if(y + 2 < btn[x].length && btn[x][y + 2].getText().equals("") && btn[x][y + 1].getText().equals("X"))
            btn[x][y + 2].setBackground(green);
        if(x - 2 >= 0)
        {
            if(y < btn[x - 2].length && btn[x - 2][y].getText().equals("") && btn[x - 1][y].getText().equals("X"))
                btn[x - 2][y].setBackground(green);
            if(y - 2 >= 0 && btn[x - 2][y - 2].getText().equals("") && btn[x - 1][y - 1].getText().equals("X"))
                btn[x - 2][y - 2].setBackground(green);
        }
        if(x + 2 < 5)
        {
            if (btn[x + 2][y].getText().equals("") && btn[x + 1][y].getText().equals("X"))
                btn[x + 2][y].setBackground(green);
            if (y + 2 < btn[x + 2].length && btn[x + 2][y + 2].getText().equals("") && btn[x + 1][y + 1].getText().equals("X"))
                btn[x + 2][y + 2].setBackground(green);
        }
    }
}

//Created by Nishant Chaudhary
//https://github.com/ChaudharyNishant
