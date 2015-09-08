package com.ameba.sharanpal.ett;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import Adapters.Item_List_Adapter;
import Adapters.Past_Deals_Adapter;

/**
 * Created by Sharanpal on 8/24/2015.
 */
public
class Past_Deals extends Activity implements View.OnClickListener
{

    TextView txtv_title,txtv_back;

    ListView listv_past_deals;

    Context con;

    @Override
    protected
    void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_past_deals);

        con=this;

        txtv_title = (TextView) findViewById(R.id.txtv_title);
        txtv_title.setText("Past Deals");

        (txtv_back = (TextView) findViewById(R.id.txtv_back)).setOnClickListener(this);
        txtv_back.setVisibility(View.VISIBLE);


        listv_past_deals=(ListView)findViewById(R.id.listv_past_deals);
        listv_past_deals.setAdapter(new Past_Deals_Adapter(con));
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