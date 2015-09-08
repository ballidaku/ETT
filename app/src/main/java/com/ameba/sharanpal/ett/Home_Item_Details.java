package com.ameba.sharanpal.ett;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.ArrayList;

import Adapters.ImagePagerAdapter;
import Classes.AutoScrollViewPager;


public
class Home_Item_Details extends Activity implements View.OnClickListener
{

    RatingBar rating;

    ViewPager pager_item_details;

    TextView txtv_title,txtv_back;

    ImageView imgv_top_left;


    private ArrayList<Integer> imageIdList = new ArrayList<>();
    Context con;

    @Override
    protected
    void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home__item__details);

        con = this;
        rating = (RatingBar) findViewById(R.id.rating);

        txtv_title = (TextView) findViewById(R.id.txtv_title);
        (txtv_back = (TextView) findViewById(R.id.txtv_back)).setOnClickListener(this);
        imgv_top_left = (ImageView) findViewById(R.id.imgv_top_left);

        pager_item_details = (ViewPager) findViewById(R.id.pager_item_details);
        findViewById(R.id.btn_request_to_swap).setOnClickListener(this);


        txtv_back.setVisibility(View.VISIBLE);
        txtv_title.setText("Test Lala");
        imgv_top_left.setImageResource(R.mipmap.ic_share);


        LayerDrawable stars = (LayerDrawable) rating.getProgressDrawable();
        stars.getDrawable(0).setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_ATOP);
        stars.getDrawable(1).setColorFilter(Color.LTGRAY, PorterDuff.Mode.SRC_ATOP);
        stars.getDrawable(2).setColorFilter(Color.parseColor("#f4d334"), PorterDuff.Mode.SRC_ATOP);


        imageIdList.add(R.mipmap.item10);
        imageIdList.add(R.mipmap.item11);
        imageIdList.add(R.mipmap.item10);
        imageIdList.add(R.mipmap.item11);


        ImagePagerAdapter adapter = new ImagePagerAdapter(con, imageIdList);
        pager_item_details.setAdapter(adapter);
    }



    @Override public
    void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.txtv_back:

                finish();
                break;

            case R.id.btn_request_to_swap:

                startActivity(new Intent(con,Trade_Details.class));
                break;

            default:
                break;

        }
    }
}
