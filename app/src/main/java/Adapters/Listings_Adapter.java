package Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;

import com.ameba.sharanpal.ett.Item_List;
import com.ameba.sharanpal.ett.R;

/**
 * Created by Sharanpal on 8/5/2015.
 */
public
class Listings_Adapter extends BaseAdapter
{
    Context con;

    public
    Listings_Adapter(Context con)
    {
        this.con = con;
    }

    @Override
    public int getCount()
    {
        // TODO Auto-generated method stub
        return 15;
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
    View getView(int arg0, View row, ViewGroup parent)
    {
        LayoutInflater inflater = (LayoutInflater) con.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        row = inflater.inflate(R.layout.custom_listings_item, parent, false);

        LinearLayout lay_inquire=(LinearLayout)row.findViewById(R.id.lay_inquire);

        lay_inquire.setOnClickListener(new View.OnClickListener() {
            @Override public
            void onClick(View v)
            {
                con.startActivity(new Intent(con, Item_List.class));
            }
        });

        return row;
    }

}
