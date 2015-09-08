package com.ameba.sharanpal.ett;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;


public
class List_Your_Item4 extends Activity implements View.OnClickListener
{

    TextView txtv_title,txtv_back;

    Context con;


    @Override
    protected
    void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_your_item4);

        con=this;

        txtv_title = (TextView) findViewById(R.id.txtv_title);
        txtv_title.setText("Test Lala");

        (txtv_back = (TextView) findViewById(R.id.txtv_back)).setOnClickListener(this);
        txtv_back.setVisibility(View.VISIBLE);


        findViewById(R.id.txtv_publish).setOnClickListener(this);


    }


    @Override public
    void onClick(View v)
    {
        switch (v.getId())
        {

            case R.id.txtv_publish:

               Intent i =new Intent(con,Main_Tabs.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
                break;

            case R.id.txtv_back:

                finish();
                break;

            default:
                break;

        }
    }

}
