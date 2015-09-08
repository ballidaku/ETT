package com.ameba.sharanpal.ett;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;


public
class Splash extends Activity
{

    protected int _splashTime = 2000;
    private Thread splashTread;


    @Override
    protected
    void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        ImageView imageView = (ImageView) findViewById(R.id.imageView);

        Animation animZoomin = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.zoom_in);

        imageView.startAnimation(animZoomin);


        splashTread = new Thread()
        {
            @Override
            public
            void run()
            {
                try
                {
                    synchronized (this)
                    {
                        wait(_splashTime);
                    }

                }
                catch (InterruptedException e)
                {
                }
                finally
                {

                    SharedPreferences shrdpref=getSharedPreferences("ETT", Context.MODE_PRIVATE);

                    if(shrdpref.contains("user_id"))
                    {
                        startActivity(new Intent(Splash.this, Main_Tabs.class));
                    }
                    else
                    {
                        startActivity(new Intent(Splash.this, Main.class));
                    }
                    finish();

                }
            }
        };

        splashTread.start();
    }


}
