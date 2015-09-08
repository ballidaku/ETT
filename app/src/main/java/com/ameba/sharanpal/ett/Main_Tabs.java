package com.ameba.sharanpal.ett;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.astuetz.PagerSlidingTabStrip;
import com.readystatesoftware.systembartint.SystemBarTintManager;


import java.util.ArrayList;

import Fragments.Home;
import Fragments.Listings;
import Fragments.My_Profile;
import Fragments.News_Feeds;
import Fragments.Your_Inquiry;
import butterknife.ButterKnife;
import butterknife.InjectView;


public
class Main_Tabs extends ActionBarActivity implements ViewPager.OnPageChangeListener
{

    @InjectView(R.id.tabs)
    PagerSlidingTabStrip tabs;
    @InjectView(R.id.pager)
    ViewPager pager;

    private MyPagerAdapter adapter;
    private SystemBarTintManager mTintManager;

    TextView txtv_title;
    ImageView imgv_top_left;
    private
    ArrayList<Fragment> list;


    @Override
    protected
    void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main__tabs);


        list=new ArrayList<>();
        list.add(new Home());
        list.add(new Your_Inquiry());
        list.add(new Listings());
        list.add(new News_Feeds());
        list.add(new My_Profile());




        txtv_title=(TextView)findViewById(R.id.txtv_title);
        imgv_top_left=(ImageView)findViewById(R.id.imgv_top_left);

        ButterKnife.inject(this);
        // create our manager instance after the content view is set
        mTintManager = new SystemBarTintManager(this);
        // enable status bar tint
        mTintManager.setStatusBarTintEnabled(true);
        adapter = new MyPagerAdapter(getSupportFragmentManager());
        adapter.selectTabIcon(0);





        pager.setAdapter(adapter);
        tabs.setViewPager(pager);
        tabs.setIndicatorColor(getResources().getColor(R.color.Indicator));
        tabs.setBackgroundColor(getResources().getColor(R.color.Tabs_Background));
        tabs.setIndicatorHeight(5);
        pager.setOnPageChangeListener(this);
    }

    @Override public
    void onPageScrolled(int position, float positionOffset, int positionOffsetPixels)
    {
    }

    @Override public
    void onPageSelected(int position)
    {

        adapter.selectTabIcon(position);
        adapter.notifyDataSetChanged();

    }

    @Override public
    void onPageScrollStateChanged(int state)
    {
    }


    public class MyPagerAdapter extends FragmentPagerAdapter implements PagerSlidingTabStrip.CustomTabProvider
    {



        private final String[] TITLES = {"Home", "Your Inquiry", "Listings", "News Feeds", "My Profile"};

        int pos;

        int[] picture_unselected={R.mipmap.home_unselected,
                                  R.mipmap.your_inquiry_unselected,
                                  R.mipmap.listing_unselected,
                                  R.mipmap.news_feed_unselected,
                                  R.mipmap.my_profile_unselected};

        int[] picture_selected={R.mipmap.home_selected,
                                R.mipmap.your_inquiry_selected,
                                R.mipmap.listing_selected,
                                R.mipmap.news_feed_selected,
                                R.mipmap.my_profile_selected};



        public MyPagerAdapter(FragmentManager fm)
        {
            super(fm);
        }



        @Override
        public View getCustomTabView(ViewGroup parent, int position) {
            View v = getLayoutInflater().inflate(R.layout.custom_viewpager_item, null);

            ImageView image = (ImageView) v.findViewById(R.id.pager_images);

            if(pos==position)
            {
                image.setImageResource(picture_selected[position]);
            }
            else
            {
                image.setImageResource(picture_unselected[position]);
            }
            return v;
        }

        @Override public
        void tabSelected(View tab)
        {
            ImageView image = (ImageView) tab.findViewById(R.id.pager_images);
            image.setImageResource(picture_selected[pos]);
        }

        @Override public
        void tabUnselected(View tab)
        {
        }


        private void selectTabIcon(int position)
        {
            pos=position;
            txtv_title.setText(TITLES[position]);

            if(position==4)
            {
                imgv_top_left.setImageResource(R.mipmap.ic_settings);
            }
            else
            {
                imgv_top_left.setImageResource(R.mipmap.ic_search);
            }
        }

        @Override
        public int getCount() {
            return TITLES.length;
        }



        @Override
        public
        Fragment getItem(int position)
        {
            return list.get(position);
//            return SuperAwesomeCardFragment.newInstance(position);
        }



    }
}
