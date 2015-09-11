package Adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ameba.sharanpal.ett.R;

import java.util.ArrayList;
import java.util.HashMap;

import Fragments.Home;

/**
 Created by sharan on 11/9/15. */
public class Apply_Filters_Adapter extends BaseAdapter
{
    Context                                       con;
    ArrayList<ArrayList<HashMap<String, String>>> list;
    int                                           pos;
    Fragment fragment;

    public Apply_Filters_Adapter(Context con,Fragment fragment, ArrayList<ArrayList<HashMap<String, String>>> list, int pos)
    {
        this.con = con;
        this.list = list;
        this.pos = pos;
        this.fragment=fragment;
    }

    @Override
    public int getCount()
    {
        // TODO Auto-generated method stub
        return list.get(pos).size();
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
    public View getView(final int position, View row, ViewGroup parent)
    {
        LayoutInflater inflater = (LayoutInflater) con.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        row = inflater.inflate(R.layout.custom_dialog_apply_filters, parent, false);
        TextView        txtv_filterText = (TextView) row.findViewById(R.id.txtv_filterText);
        final ImageView imgv_chkFilter  = (ImageView) row.findViewById(R.id.imgv_chkFilter);

        txtv_filterText.setText(list.get(pos).get(position).get("cname"));


        if(pos==3 || pos==4)
        {
            if (list.get(pos).get(position).get("is_checked").equals("yes"))
            {
                imgv_chkFilter.setBackgroundResource(R.mipmap.ic_check);
            }
            else
            {
                imgv_chkFilter.setBackgroundResource(R.mipmap.ic_uncheck);
            }

            row.setOnClickListener(new View.OnClickListener()
            {

                @Override
                public void onClick(View v)
                {


                    if (list.get(pos).get(position).get("is_checked").equals("yes"))
                    {
                        imgv_chkFilter.setBackgroundResource(R.mipmap.ic_uncheck);
                        list.get(pos).get(position).put("is_checked", "no");
                    }
                    else
                    {
                        for (int i=0;i<list.get(pos).size();i++)
                        {
                            list.get(pos).get(i).put("is_checked", "no");
                        }
                        imgv_chkFilter.setBackgroundResource(R.mipmap.ic_check);
                        list.get(pos).get(position).put("is_checked", "yes");
                    }

                    ((Home)fragment).refresh_sub_apply_filters();


                }
            });

        }
        else
        {
            if (list.get(pos).get(position).get("is_checked").equals("yes"))
            {
                imgv_chkFilter.setBackgroundResource(R.mipmap.ic_check);
            }
            else
            {
                imgv_chkFilter.setBackgroundResource(R.mipmap.ic_uncheck);
            }

            row.setOnClickListener(new View.OnClickListener()
            {

                @Override
                public void onClick(View v)
                {
                    if (list.get(pos).get(position).get("is_checked").equals("yes"))
                    {
                        imgv_chkFilter.setBackgroundResource(R.mipmap.ic_uncheck);
                        list.get(pos).get(position).put("is_checked", "no");
                    }
                    else
                    {
                        imgv_chkFilter.setBackgroundResource(R.mipmap.ic_check);
                        list.get(pos).get(position).put("is_checked", "yes");
                    }
                }
            });
        }





        return row;
    }

}
