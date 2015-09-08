package com.ameba.sharanpal.ett;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;


public
class List_Your_Item3 extends FragmentActivity implements View.OnClickListener
{

    TextView txtv_title,txtv_back;

    Context con;
    @Override
    protected
    void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_your_item3);

        con=this;

        txtv_title = (TextView) findViewById(R.id.txtv_title);
        txtv_title.setText("List Your Item");

        (txtv_back = (TextView) findViewById(R.id.txtv_back)).setOnClickListener(this);
        txtv_back.setVisibility(View.VISIBLE);


        findViewById(R.id.txtv_next).setOnClickListener(this);
    }


    @Override public
    void onClick(View v)
    {
        switch (v.getId())
        {

            case R.id.txtv_next:

                startActivity(new Intent(con,List_Your_Item4.class));
                break;

            case R.id.txtv_back:

                finish();
                break;

            default:
                break;

        }
    }

}
