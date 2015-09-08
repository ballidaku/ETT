package Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.ameba.sharanpal.ett.R;

import Adapters.News_Feeds_Adapter;
import Adapters.Your_Inquiry_Liked_Listing_Adapter;

public
class News_Feeds extends Fragment
{

    Context con;
    ListView listv_news_feed;

    public
    News_Feeds()
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
        View rootView = inflater.inflate(R.layout.fragment_news__feeds, container, false);

        con=getActivity();

        listv_news_feed=(ListView)rootView.findViewById(R.id.listv_news_feed);


        listv_news_feed.setAdapter(new News_Feeds_Adapter(con));

        return rootView;
    }


}
