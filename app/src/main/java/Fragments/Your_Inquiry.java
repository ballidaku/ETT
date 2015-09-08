package Fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.ameba.sharanpal.ett.R;
import com.ameba.sharanpal.ett.Your_Interests;

import Adapters.Your_Inquiry_Liked_Listing_Adapter;


public
class Your_Inquiry extends Fragment implements View.OnClickListener
{
    ListView listv_liked_listing;
    Context con;

    public
    Your_Inquiry()
    {
        // Required empty public constructor
    }

    @Override
    public
    void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);


    }

    @Override
    public
    View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.fragment_your__inquiry, container, false);

        con=getActivity();


        rootView.findViewById(R.id.lay_show_more).setOnClickListener(this);


        listv_liked_listing=(ListView)rootView.findViewById(R.id.listv_liked_listing);


        listv_liked_listing.setAdapter(new Your_Inquiry_Liked_Listing_Adapter(con));

        return rootView;
    }


    @Override public
    void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.lay_show_more:

               startActivity(new Intent(con, Your_Interests.class));
                break;

            default:
                break;

        }
    }

}
