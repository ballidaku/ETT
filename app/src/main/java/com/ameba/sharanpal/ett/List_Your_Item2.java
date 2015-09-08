package com.ameba.sharanpal.ett;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;


public
class List_Your_Item2 extends Activity implements View.OnClickListener
{

    TextView txtv_title,txtv_back;

    Context con;
    @Override
    protected
    void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        con=this;
        setContentView(R.layout.activity_list_your_item2);

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

                startActivity(new Intent(con,List_Your_Item3.class));
                break;

            case R.id.txtv_back:

                finish();
                break;

            default:
                break;

        }
    }

}
