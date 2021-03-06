package Fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.ameba.sharanpal.ett.Inbox;
import com.ameba.sharanpal.ett.Liked_Post;
import com.ameba.sharanpal.ett.Login;
import com.ameba.sharanpal.ett.Main_Tabs;
import com.ameba.sharanpal.ett.Past_Deals;
import com.ameba.sharanpal.ett.R;
import com.ameba.sharanpal.ett.Review_Score;
import com.ameba.sharanpal.ett.Your_Interests;
import com.facebook.AccessToken;
import com.facebook.Profile;

import Adapters.News_Feeds_Adapter;

public class My_Profile extends Fragment implements View.OnClickListener
{

    Context           con;
    SharedPreferences sp;

    public My_Profile()
    {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.fragment_my_profile, container, false);

        con = getActivity();

        sp = con.getSharedPreferences("ETT", Context.MODE_PRIVATE);

        rootView.findViewById(R.id.lay_wishlist).setOnClickListener(this);
        rootView.findViewById(R.id.lay_inbox).setOnClickListener(this);
        rootView.findViewById(R.id.lay_your_review_score).setOnClickListener(this);
        rootView.findViewById(R.id.lay_interests).setOnClickListener(this);
        rootView.findViewById(R.id.lay_past_deals).setOnClickListener(this);
        rootView.findViewById(R.id.btn_sign_out).setOnClickListener(this);

        return rootView;
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.lay_wishlist:

                startActivity(new Intent(con, Liked_Post.class));
                break;

            case R.id.lay_interests:

                startActivity(new Intent(con, Your_Interests.class));
                break;

            case R.id.lay_inbox:

                startActivity(new Intent(con, Inbox.class));
                break;

            case R.id.lay_your_review_score:

                startActivity(new Intent(con, Review_Score.class));
                break;

            case R.id.lay_past_deals:

                startActivity(new Intent(con, Past_Deals.class));
                break;

            case R.id.btn_sign_out:
                sp.edit().remove("user_id").apply();
                stop_fb();

                Intent i = new Intent(con, Login.class);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                con.startActivity(i);

                break;

            default:
                break;

        }
    }

    public void stop_fb()
    {

        try
        {
            AccessToken.setCurrentAccessToken(null);
            Profile.setCurrentProfile(null);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }

}
