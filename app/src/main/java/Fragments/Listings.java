package Fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.ameba.sharanpal.ett.List_Your_Item1;
import com.ameba.sharanpal.ett.R;

import Adapters.Listings_Adapter;

public
class Listings extends Fragment implements View.OnClickListener
{

    Context con;
    ListView listv_listings;

    public
    Listings()
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
        View rootView = inflater.inflate(R.layout.fragment_listings, container, false);

        con=getActivity();

        rootView.findViewById(R.id.add_listing).setOnClickListener(this);

        listv_listings=(ListView)rootView.findViewById(R.id.listv_listings);


        listv_listings.setAdapter(new Listings_Adapter(con));

        return rootView;
    }


    @Override public
    void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.add_listing:

                startActivity(new Intent(con, List_Your_Item1.class));
                break;


            default:
                break;

        }
    }



}
