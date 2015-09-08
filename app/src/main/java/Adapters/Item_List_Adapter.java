package Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.ameba.sharanpal.ett.R;

/**
 * Created by Sharanpal on 8/6/2015.
 */
public
class Item_List_Adapter extends BaseAdapter
{
    Context con;

    public
    Item_List_Adapter(Context con)
    {
        this.con = con;
    }

    @Override
    public
    int getCount()
    {
        // TODO Auto-generated method stub
        return 15;
    }

    @Override
    public
    Object getItem(int arg0)
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public
    long getItemId(int arg0)
    {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public
    View getView(int arg0, View row, ViewGroup parent)
    {
        LayoutInflater inflater = (LayoutInflater) con.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if(arg0 == 0)
        {
            row = inflater.inflate(R.layout.custom_item_list1, parent, false);
        }
        else if(arg0 % 2 == 0)
        {
            row = inflater.inflate(R.layout.custom_item_list2, parent, false);
            ImageView image1 = (ImageView) row.findViewById(R.id.image1);
            ImageView image2 = (ImageView) row.findViewById(R.id.image2);
            ImageView image3 = (ImageView) row.findViewById(R.id.image3);

            final ImageView ic_check_uncheck1 = (ImageView) row.findViewById(R.id.ic_check_uncheck1);
            final ImageView ic_check_uncheck2 = (ImageView) row.findViewById(R.id.ic_check_uncheck2);
            final ImageView ic_check_uncheck3 = (ImageView) row.findViewById(R.id.ic_check_uncheck3);

            ic_check_uncheck1.setOnClickListener(new View.OnClickListener()
            {
                @Override public
                void onClick(View v)
                {
                    ic_check_uncheck1.setImageResource(R.mipmap.ic_check);
                    ic_check_uncheck2.setImageResource(R.mipmap.ic_uncheck);
                    ic_check_uncheck3.setImageResource(R.mipmap.ic_uncheck);
                }
            });
            ic_check_uncheck2.setOnClickListener(new View.OnClickListener()
            {
                @Override public
                void onClick(View v)
                {
                    ic_check_uncheck1.setImageResource(R.mipmap.ic_uncheck);
                    ic_check_uncheck2.setImageResource(R.mipmap.ic_check);
                    ic_check_uncheck3.setImageResource(R.mipmap.ic_uncheck);
                }
            });

            ic_check_uncheck3.setOnClickListener(new View.OnClickListener()
            {
                @Override public
                void onClick(View v)
                {
                    ic_check_uncheck1.setImageResource(R.mipmap.ic_uncheck);
                    ic_check_uncheck2.setImageResource(R.mipmap.ic_uncheck);
                    ic_check_uncheck3.setImageResource(R.mipmap.ic_check);
                }
            });
        }
        else
        {
            row = inflater.inflate(R.layout.custom_item_list3, parent, false);
        }


        return row;
    }
}