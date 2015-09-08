package com.ameba.sharanpal.ett;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


public
class List_Your_Item1 extends Activity implements View.OnClickListener
{

    ImageView imgv_permanent_tick,imgv_temporary_tick,imgv_eitherway_tick;

    TextView txtv_title,txtv_back;


    Context con;
    @Override
    protected
    void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        con=this;
        setContentView(R.layout.activity_list_your_item1);

        txtv_title = (TextView) findViewById(R.id.txtv_title);
        txtv_title.setText("List Your Item");

        (txtv_back = (TextView) findViewById(R.id.txtv_back)).setOnClickListener(this);
        txtv_back.setVisibility(View.VISIBLE);

        imgv_permanent_tick=(ImageView)findViewById(R.id.imgv_permanent_tick);
        imgv_temporary_tick=(ImageView)findViewById(R.id.imgv_temporary_tick);
        imgv_eitherway_tick=(ImageView)findViewById(R.id.imgv_eitherway_tick);

        findViewById(R.id.lay_permanent).setOnClickListener(this);
        findViewById(R.id.lay_temporarily).setOnClickListener(this);
        findViewById(R.id.lay_either).setOnClickListener(this);

        findViewById(R.id.txtv_next).setOnClickListener(this);

    }

    @Override public
    void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.lay_permanent:

                select_item(imgv_permanent_tick);
                break;
            case R.id.lay_temporarily:

                select_item(imgv_temporary_tick);
                break;

            case R.id.lay_either:

                select_item(imgv_eitherway_tick);
                break;


            case R.id.txtv_next:

                startActivity(new Intent(con,List_Your_Item2.class));
                break;

            case R.id.txtv_back:

                finish();
                break;

            default:
                break;

        }
    }

   public void  select_item(ImageView selected)
    {
        imgv_permanent_tick.setVisibility(View.INVISIBLE);
        imgv_temporary_tick.setVisibility(View.INVISIBLE);
        imgv_eitherway_tick.setVisibility(View.INVISIBLE);

        selected.setVisibility(View.VISIBLE);
    }
}
