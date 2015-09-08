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

import Adapters.Item_List_Adapter;


public
class Item_List extends Activity implements View.OnClickListener
{

    TextView txtv_title,txtv_back;

    ListView listv_item_list;

    Context con;

    @Override
    protected
    void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_list);

        con=this;

        txtv_title = (TextView) findViewById(R.id.txtv_title);
        txtv_title.setText("Item List");

        (txtv_back = (TextView) findViewById(R.id.txtv_back)).setOnClickListener(this);
        txtv_back.setVisibility(View.VISIBLE);


        ( findViewById(R.id.imgv_top_left)).setVisibility(View.GONE);

        TextView select=(TextView) findViewById(R.id.txtv_top_right);
        select.setVisibility(View.VISIBLE);
        select.setText("Done");


        listv_item_list=(ListView)findViewById(R.id.listv_item_list);
        listv_item_list.setAdapter(new Item_List_Adapter(con));
    }


    @Override public
    void onClick(View v)
    {
        switch (v.getId())
        {

           /* case R.id.txtv_next:

                startActivity(new Intent(con,List_Your_Item4.class));
                break;
*/
            case R.id.txtv_back:

                finish();
                break;

            default:
                break;

        }
    }

}
