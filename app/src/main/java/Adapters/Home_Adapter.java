package Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.ameba.sharanpal.ett.Home_Item_Details;
import com.ameba.sharanpal.ett.Main_Tabs;
import com.ameba.sharanpal.ett.Other_Profile;
import com.ameba.sharanpal.ett.R;

import Classes.RoundedCornersImageView;

/**
 * Created by Sharanpal on 8/5/2015.
 */
public
class Home_Adapter extends BaseAdapter
{
    Context con;

    int[] pics={R.mipmap.item12,R.mipmap.item13,R.mipmap.item12,R.mipmap.item13};
    int[] images={R.mipmap.item10,R.mipmap.item11,R.mipmap.item10,R.mipmap.item11};

    RoundedCornersImageView imgv_item,imgv_other_profile;

    public Home_Adapter(Context con)
    {
        this.con = con;
    }

    @Override
    public int getCount()
    {
        // TODO Auto-generated method stub
        return 4;
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
    public
    View getView(int position, View row, ViewGroup parent)
    {
        LayoutInflater inflater = (LayoutInflater) con.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        row = inflater.inflate(R.layout.custom_home_item, parent, false);

        imgv_item=(RoundedCornersImageView)row.findViewById(R.id.imgv_item);
        imgv_other_profile=(RoundedCornersImageView)row.findViewById(R.id.imgv_other_profile);

        imgv_item.setImageResource(images[position]);
        imgv_other_profile.setImageResource(pics[position]);


        row.setOnClickListener(new View.OnClickListener()
        {
            @Override public
            void onClick(View v)
            {
                con.startActivity(new Intent(con, Home_Item_Details.class));
            }
        });


        imgv_other_profile.setOnClickListener(new View.OnClickListener() {
            @Override public
            void onClick(View v)
            {
                con.startActivity(new Intent(con, Other_Profile.class));
            }
        });

        return row;
    }

}
