package com.ameba.sharanpal.ett;

import android.app.Activity;
import android.content.Context;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;


public
class Your_Interests extends Activity implements View.OnClickListener
{

    TextView txtv_title, txtv_back;
    Context con;
    @Override
    protected
    void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_your__interests);


        con=this;
        txtv_title = (TextView) findViewById(R.id.txtv_title);
        txtv_title.setText("Your Interests");

        (txtv_back = (TextView) findViewById(R.id.txtv_back)).setOnClickListener(this);
        txtv_back.setVisibility(View.VISIBLE);
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
