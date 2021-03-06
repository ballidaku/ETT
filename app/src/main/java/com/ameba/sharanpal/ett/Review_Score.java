package com.ameba.sharanpal.ett;

import android.app.Activity;
import android.content.Context;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import Adapters.Inbox_Adapter;
import Adapters.Review_Score_Adapter;


public
class Review_Score extends Activity implements View.OnClickListener
{

    Context con;
    ListView listv_review_score;
    TextView txtv_title, txtv_back;

    @Override
    protected
    void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review__score);

        con=this;
        txtv_title = (TextView) findViewById(R.id.txtv_title);
        txtv_title.setText("Review Score");

        (txtv_back = (TextView) findViewById(R.id.txtv_back)).setOnClickListener(this);
        txtv_back.setVisibility(View.VISIBLE);

        listv_review_score = (ListView) findViewById(R.id.listv_review_score);
        listv_review_score.setAdapter(new Review_Score_Adapter(con));
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
