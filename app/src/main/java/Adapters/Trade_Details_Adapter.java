package Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.ameba.sharanpal.ett.R;

/**
 * Created by Sharanpal on 8/5/2015.
 */
public
class Trade_Details_Adapter extends BaseAdapter
{
    Context con;


    public
    Trade_Details_Adapter(Context con)
    {
        this.con = con;
    }

    @Override
    public int getCount()
    {
        // TODO Auto-generated method stub
        return 3;
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

        row = inflater.inflate(R.layout.custom_trade_details_item, parent, false);

        final ImageView imgv_check_uncheck=(ImageView)row.findViewById(R.id.imgv_check_uncheck);



        row.setOnClickListener(new View.OnClickListener() {
            @Override public
            void onClick(View v)
            {

                if(imgv_check_uncheck.getDrawable().getConstantState() == con.getResources().getDrawable( R.mipmap.ic_uncheck).getConstantState())
                {
                    imgv_check_uncheck.setImageResource(R.mipmap.ic_check);
                }
                else
                {
                    imgv_check_uncheck.setImageResource(R.mipmap.ic_uncheck);
                }
            }
        });

        return row;
    }

}
