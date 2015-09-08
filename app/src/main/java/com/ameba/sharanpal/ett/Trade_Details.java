package com.ameba.sharanpal.ett;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import Adapters.Trade_Details_Adapter;


public
class Trade_Details extends Activity implements View.OnClickListener
{

    ImageView imgv_offerimage,imgv_offerimg2;
    ImageView imgv_swapitemimgperson,imgv_swapitemimgmail,imgv_swapitemimgeitherway;
    ImageView imgv_swapop1,imgv_swapop2,imgv_swapop3,imgv_swapop4;
    ImageView imgv_payment;

    TextView txtv_title,txtv_back;

    ListView lstv_trade_details;

    boolean imgv_payment_isselected=false;

    Context con;


    @Override
    protected
    void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        con=this;

        setContentView(R.layout.activity_trade__details);

        txtv_title = (TextView) findViewById(R.id.txtv_title);
        txtv_title.setText("Trade Details");

        (txtv_back = (TextView) findViewById(R.id.txtv_back)).setOnClickListener(this);
        txtv_back.setVisibility(View.VISIBLE);

        imgv_offerimage=(ImageView)findViewById(R.id.imgv_offerimage);
        imgv_offerimg2= (ImageView)findViewById(R.id.imgv_offerimg2);

        findViewById(R.id.lay_offerpermanent).setOnClickListener(this);
        findViewById(R.id.lay_offertemp).setOnClickListener(this);


        imgv_swapitemimgperson=(ImageView)findViewById(R.id.imgv_swapitemimgperson);
        imgv_swapitemimgmail=(ImageView)findViewById(R.id.imgv_swapitemimgmail);
        imgv_swapitemimgeitherway=(ImageView)findViewById(R.id.imgv_swapitemimgeitherway);


        findViewById(R.id.lay_swapperson).setOnClickListener(this);
        findViewById(R.id.lay_swapmail).setOnClickListener(this);
        findViewById(R.id.lay_swapeitherway).setOnClickListener(this);



        imgv_swapop1=(ImageView)findViewById(R.id.imgv_swapop1);
        imgv_swapop2=(ImageView)findViewById(R.id.imgv_swapop2);
        imgv_swapop3=(ImageView)findViewById(R.id.imgv_swapop3);
        imgv_swapop4=(ImageView)findViewById(R.id.imgv_swapop4);

        findViewById(R.id.lay_swapeopt1).setOnClickListener(this);
        findViewById(R.id.lay_swapeopt2).setOnClickListener(this);
        findViewById(R.id.lay_swapeopt3).setOnClickListener(this);
        findViewById(R.id.lay_swapeopt4).setOnClickListener(this);



        findViewById(R.id.txtv_next).setOnClickListener(this);

        (imgv_payment=(ImageView)findViewById(R.id.imgv_payment)).setOnClickListener(this);


        lstv_trade_details=(ListView)findViewById(R.id.lstv_trade_details);

        lstv_trade_details.setAdapter(new Trade_Details_Adapter(con));


        setListViewHeightBasedOnItems();
    }



    @Override public
    void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.lay_offerpermanent:

                offer(imgv_offerimage);

                break;
            case R.id.lay_offertemp:

                offer(imgv_offerimg2);

                break;

            //*********************************************
            case R.id.lay_swapperson:

                swap(imgv_swapitemimgperson);

                break;
            case R.id.lay_swapmail:

                swap(imgv_swapitemimgmail);

                break;
            case R.id.lay_swapeitherway:

                swap(imgv_swapitemimgeitherway);

                break;

            //*********************************************
            case R.id.lay_swapeopt1:

                swap_option(imgv_swapop1);

                break;
            case R.id.lay_swapeopt2:

                swap_option(imgv_swapop2);

                break;
            case R.id.lay_swapeopt3:

                swap_option(imgv_swapop3);

                break;



            case R.id.lay_swapeopt4:

                swap_option(imgv_swapop4);

                break;
            //***********************************

            case R.id.imgv_payment:

                if(imgv_payment_isselected = !imgv_payment_isselected)
                {
                    imgv_payment.setImageResource(R.mipmap.ic_check);
                }
                else
                {
                    imgv_payment.setImageResource(R.mipmap.ic_uncheck);
                }


                break;

            //***********************************

            case R.id.txtv_back:

                finish();
                break;


            case R.id.txtv_next:

                show();
                break;

            //*****************************


            case R.id.txtv_confirm:
                Intent i=new Intent(con,Main_Tabs.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);

            break;


            case R.id.txtv_cancel:
                dialog.cancel();

                break;

            default:
                break;

        }
    }

    public void offer(ImageView selected)
    {
        imgv_offerimage.setImageResource(R.mipmap.ic_uncheck);
        imgv_offerimg2.setImageResource(R.mipmap.ic_uncheck);

        selected.setImageResource(R.mipmap.ic_check);
    }


    public void swap(ImageView selected)
    {
        imgv_swapitemimgperson.setImageResource(R.mipmap.ic_uncheck);
        imgv_swapitemimgmail.setImageResource(R.mipmap.ic_uncheck);
        imgv_swapitemimgeitherway.setImageResource(R.mipmap.ic_uncheck);

        selected.setImageResource(R.mipmap.ic_check);
    }


    public void swap_option(ImageView selected)
    {
        imgv_swapop1.setImageResource(R.mipmap.ic_uncheck);
        imgv_swapop2.setImageResource(R.mipmap.ic_uncheck);
        imgv_swapop3.setImageResource(R.mipmap.ic_uncheck);
        imgv_swapop4.setImageResource(R.mipmap.ic_uncheck);

        selected.setImageResource(R.mipmap.ic_check);
    }


    public  void setListViewHeightBasedOnItems()
    {

        ListAdapter listAdapter = lstv_trade_details.getAdapter();
        if(listAdapter != null)
        {

            int numberOfItems = listAdapter.getCount();

            // Get total height of all items.
            int totalItemsHeight = 0;
            for(int itemPos = 0; itemPos < numberOfItems; itemPos++)
            {
                if(itemPos < 3)
                {
                    View item = listAdapter.getView(itemPos, null, lstv_trade_details);
                    item.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
                    Log.e("" + itemPos, "" + item.getMeasuredHeight());
                    totalItemsHeight += item.getMeasuredHeight();

                }
            }

            // Get total height of all item dividers.
            int totalDividersHeight = lstv_trade_details.getDividerHeight() * (numberOfItems - 1);

            // Set list height.
            ViewGroup.LayoutParams params = lstv_trade_details.getLayoutParams();
            params.height = totalItemsHeight + totalDividersHeight;
            lstv_trade_details.setLayoutParams(params);
            lstv_trade_details.requestLayout();

            //	return true;

        }
        else
        {
            //	return false;
        }

    }

     Dialog dialog;
    public void show()
    {
        dialog = new Dialog(con);

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); //before

        // Include dialog.xml file
        dialog.setContentView(R.layout.dialog_next);


        dialog.findViewById(R.id.txtv_confirm).setOnClickListener(this);
        dialog.findViewById(R.id.txtv_cancel).setOnClickListener(this);

        dialog.show();
    }


}
