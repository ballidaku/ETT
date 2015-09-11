package Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ameba.sharanpal.ett.Home_Item_Details;
import com.ameba.sharanpal.ett.Main_Tabs;
import com.ameba.sharanpal.ett.Other_Profile;
import com.ameba.sharanpal.ett.R;

import java.util.ArrayList;
import java.util.HashMap;

import Classes.RoundedCornersImageView;

/**
 Created by Sharanpal on 8/5/2015. */
public class Home_Adapter extends BaseAdapter
{
    Context con;

    int[] pics   = {R.mipmap.item12, R.mipmap.item13, R.mipmap.item12, R.mipmap.item13};
    int[] images = {R.mipmap.item10, R.mipmap.item11, R.mipmap.item10, R.mipmap.item11};



    ArrayList<HashMap<String, String>> listing;

    public Home_Adapter(Context con,ArrayList<HashMap<String, String>> listing)
    {
        this.con = con;
        this.listing=listing;
    }

    @Override
    public int getCount()
    {
        // TODO Auto-generated method stub
        return listing.size();
    }

    @Override
    public Object getItem(int arg0)
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public long getItemId(int arg0)
    {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public View getView(int position, View row, ViewGroup parent)
    {
        LayoutInflater inflater = (LayoutInflater) con.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        row = inflater.inflate(R.layout.custom_home_item, parent, false);

        RoundedCornersImageView imgv_other_profile = (RoundedCornersImageView) row.findViewById(R.id.imgv_other_profile);


        // SET DATA ON OBJECTS
        ((TextView) row.findViewById(R.id.txtv_location)).setText(listing.get(position).get("Pstate") + ", " + listing.get(position).get("Pcountry"));
        ((TextView) row.findViewById(R.id.txtv_itemName)).setText(listing.get(position).get("Pname"));
        ((TextView)row.findViewById(R.id.txtv_price)).setText(listing.get(position).get("Pprice"));


        ((RoundedCornersImageView) row.findViewById(R.id.imgv_item)).setImageUrl(con, listing.get(position).get("Pimage"));
        imgv_other_profile.setRadius(90);
        imgv_other_profile.setImageUrl(con, listing.get(position).get("owner_image"));

        row.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                con.startActivity(new Intent(con, Home_Item_Details.class));
            }
        });

        imgv_other_profile.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                con.startActivity(new Intent(con, Other_Profile.class));
            }
        });

        return row;

    }

}
