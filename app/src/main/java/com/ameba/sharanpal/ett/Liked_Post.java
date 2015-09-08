package com.ameba.sharanpal.ett;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import Adapters.Liked_Post_Adapter;
import Adapters.Listings_Adapter;


public
class Liked_Post extends Activity implements View.OnClickListener
{

    Context con;
    ListView listv_liked_post;
    TextView txtv_title, txtv_back;

    @Override
    protected
    void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liked__post);

        con=this;
        txtv_title = (TextView) findViewById(R.id.txtv_title);
        txtv_title.setText("Liked Post");

        (txtv_back = (TextView) findViewById(R.id.txtv_back)).setOnClickListener(this);
        txtv_back.setVisibility(View.VISIBLE);

        listv_liked_post = (ListView) findViewById(R.id.listv_liked_post);
        listv_liked_post.setAdapter(new Liked_Post_Adapter(con));
    }


    @Override public
    void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.txtv_back:

                finish();
                break;

            default:
                break;

        }
    }


}
