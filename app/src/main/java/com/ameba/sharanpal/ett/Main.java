package com.ameba.sharanpal.ett;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.viewpagerindicator.CirclePageIndicator;

import java.util.ArrayList;

import Adapters.ImagePagerAdapter;
import Classes.AutoScrollViewPager;

public
class Main extends Activity implements View.OnClickListener
{

    AutoScrollViewPager pager;
    CirclePageIndicator page_indicator;
    private ArrayList<Integer> imageIdList = new ArrayList<>();

    Context con;
    @Override protected
    void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        con=this;

        imageIdList.add(R.mipmap.a);
        imageIdList.add(R.mipmap.b);
        imageIdList.add(R.mipmap.c);
        imageIdList.add(R.mipmap.d);

        pager = (AutoScrollViewPager) findViewById(R.id.pager_splash);
        ImagePagerAdapter adapter= new ImagePagerAdapter(con,imageIdList);
        pager.setAdapter(adapter);


        page_indicator = (CirclePageIndicator) findViewById(R.id.indicator);
        page_indicator.setViewPager(pager);


        pager.startAutoScroll(2000);
        pager.setStopScrollWhenTouch(true);
        pager.setAutoScrollDurationFactor(17);



        findViewById(R.id.btn_login).setOnClickListener(this);
        findViewById(R.id.btn_signup).setOnClickListener(this);

    }

    @Override public
    void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.btn_login:

                startActivity(new Intent(con,Login.class));
                break;

            case R.id.btn_signup:

                startActivity(new Intent(con,SignUp.class));

                break;

             default:
                break;

        }
    }
}

