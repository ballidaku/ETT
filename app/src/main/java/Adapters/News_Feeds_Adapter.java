package Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.ameba.sharanpal.ett.R;

/**
 * Created by Sharanpal on 8/4/2015.
 */
public
class News_Feeds_Adapter extends BaseAdapter
{
    Context con;

    public News_Feeds_Adapter(Context con)
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

        row = inflater.inflate(R.layout.custom_your_inquiry_liked_listing, parent, false);

        return row;
    }

}
