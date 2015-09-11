package Fragments;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.ameba.sharanpal.ett.Main_Tabs;
import com.ameba.sharanpal.ett.R;
import com.ameba.sharanpal.ett.SignUp;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import Adapters.Apply_Filters_Adapter;
import Adapters.Home_Adapter;
import Adapters.News_Feeds_Adapter;
import Utils.GlobalConstant;
import Utils.GlobalUtils;
import WebServices.Super_AsyncTask;

public class Home extends Fragment implements View.OnClickListener
{

    Context  con;
    ListView listv_home;

    Apply_Filters_Adapter adapter;

    Fragment fragment;



    public Home()
    {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);

        con = getActivity();
        fragment = this;

        listv_home = (ListView) rootView.findViewById(R.id.listv_home);

        rootView.findViewById(R.id.txtv_applyfilters).setOnClickListener(this);



        HashMap<String, String> map = new HashMap<>();
        map.put("category","");
        map.put("condition","");
        map.put("connection","");
        map.put("worth_value","");
        map.put("nearby", "");

        refresh_listing(map);

        //apply filters
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
        {
            new Super_AsyncTask(con, fragment, GlobalConstant.URL + "get_filtered_option", "set_filter_data").executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        }
        else
        {
            new Super_AsyncTask(con, fragment, GlobalConstant.URL + "get_filtered_option", "set_filter_data").execute();
        }

        return rootView;
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.txtv_applyfilters:

                show();
                break;

            case R.id.txtv_applyfilters_dialog:

                filter_dialog.dismiss();

                HashMap<String,String> filteredListingInput = new HashMap<>();
                filteredListingInput.put("category",categoryFiltersIDS);
                filteredListingInput.put("condition",conditionFiltersIDS);
                filteredListingInput.put("connection",listByFiltersIDS);
                filteredListingInput.put("worth_value",worthValueFiltersIDS);
                filteredListingInput.put("nearby", nearByFiltersIDS);

                refresh_listing(filteredListingInput);

                refresh_apply_filters();

                break;

            case R.id.txtv_sp_category:

                showSubDialog(0);
                break;

            case R.id.txtv_sp_condition:

                showSubDialog(1);
                break;

            case R.id.txtv_sp_worthvalue:

                showSubDialog(2);
                break;

            case R.id.txtv_sp_listby:

                showSubDialog(3);
                break;

            case R.id.txtv_sp_nearby:

                showSubDialog(4);
                break;

            case R.id.btn_done:
                filterSelectionDone();
                break;

            default:
                break;

        }
    }




    public void refresh_listing(HashMap<String, String> map)
    {


        // Get Listing
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
        {
            new Super_AsyncTask(con, fragment,map, GlobalConstant.URL + "get_filtered_listing", "set_listing_data").executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        }
        else
        {
            new Super_AsyncTask(con, fragment,map, GlobalConstant.URL + "get_filtered_listing", "set_listing_data").execute();
        }

    }

    ArrayList<ArrayList<HashMap<String, String>>> list = new ArrayList<>();

    public void set_filter_data(String response)
    {
        try
        {
            Log.e("response", response);

            JSONObject mainobj = new JSONObject(response);
            if (mainobj.opt("status").equals("Success"))
            {
                list.clear();

                JSONObject data = mainobj.getJSONObject("listing");

                JSONArray category_listing_array = data.getJSONArray("category_listing");

                ArrayList<HashMap<String, String>> list_category = new ArrayList<>();
                for (int i = 0; i < category_listing_array.length(); i++)
                {
                    HashMap<String, String> map = new HashMap<>();
                    map.put("cid", category_listing_array.getJSONObject(i).getString("cid"));
                    map.put("cname", category_listing_array.getJSONObject(i).getString("cname"));
                    map.put("is_checked", "no");

                    list_category.add(map);
                }
                list.add(list_category);

                JSONArray condition_array = data.getJSONArray("condition");

                ArrayList<HashMap<String, String>> list_condition = new ArrayList<>();
                for (int i = 0; i < condition_array.length(); i++)
                {
                    HashMap<String, String> map = new HashMap<>();
                    map.put("cid", condition_array.getJSONObject(i).getString("cid"));
                    map.put("cname", condition_array.getJSONObject(i).getString("cname"));
                    map.put("is_checked", "no");

                    list_condition.add(map);
                }
                list.add(list_condition);

                JSONArray worth_value_array = data.getJSONArray("worth_value");

                ArrayList<HashMap<String, String>> list_worth_value = new ArrayList<>();
                for (int i = 0; i < worth_value_array.length(); i++)
                {
                    HashMap<String, String> map = new HashMap<>();
                    map.put("cid", worth_value_array.getJSONObject(i).getString("cid"));
                    map.put("cname", worth_value_array.getJSONObject(i).getString("cname"));
                    map.put("is_checked", "no");

                    list_worth_value.add(map);
                }
                list.add(list_worth_value);

                JSONArray list_by_array = data.getJSONArray("list_by");

                ArrayList<HashMap<String, String>> list_list_by = new ArrayList<>();
                for (int i = 0; i < list_by_array.length(); i++)
                {
                    HashMap<String, String> map = new HashMap<>();
                    map.put("cid", list_by_array.getJSONObject(i).getString("cid"));
                    map.put("cname", list_by_array.getJSONObject(i).getString("cname"));
                    map.put("is_checked", "no");

                    list_list_by.add(map);
                }
                list.add(list_list_by);

                JSONArray nearby_array = data.getJSONArray("nearby");

                ArrayList<HashMap<String, String>> list_nearby = new ArrayList<>();
                for (int i = 0; i < nearby_array.length(); i++)
                {
                    HashMap<String, String> map = new HashMap<>();
                    map.put("cid", nearby_array.getJSONObject(i).getString("cid"));
                    map.put("cname", nearby_array.getJSONObject(i).getString("cname"));
                    map.put("is_checked", "no");

                    list_nearby.add(map);
                }
                list.add(list_nearby);

            }
            else
            {
                GlobalUtils.show_Toast(mainobj.optString("message"), con);

            }

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }

    ArrayList<HashMap<String, String>> listing = new ArrayList<>();
    Home_Adapter home_adapter;

    public void set_listing_data(String response)
    {
        try
        {
            Log.e("response", response);

            JSONObject mainobj = new JSONObject(response);
            if (mainobj.opt("status").equals("Success"))
            {
                listing.clear();

                JSONArray listingData = mainobj.getJSONArray("listing");
                for (int i = 0; i < listingData.length(); i++)
                {
                    HashMap innerData = new HashMap<>();
                    JSONObject data = listingData.getJSONObject(i);
                    innerData.put("Pid", data.optString("Pid"));
                    innerData.put("Pname", data.optString("Pname"));
                    innerData.put("Pprice", data.optString("Pprice"));
                    innerData.put("Pdesc", data.optString("Pdesc"));
                    innerData.put("Pcity", data.optString("Pcity"));
                    innerData.put("Pstate", data.optString("Pstate"));
                    innerData.put("Pcountry", data.optString("Pcountry"));
                    innerData.put("Pimage", data.optString("Pimage"));
                    innerData.put("owner_image", data.optString("owner_image"));

                    listing.add(innerData);
                }

                home_adapter = new Home_Adapter(con, listing);

                listv_home.setAdapter(home_adapter);

            }
            else
            {
                GlobalUtils.show_Toast(mainobj.optString("message"), con);

            }

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }

    Dialog filter_dialog, sub_filter_dialog;

    public void show()
    {
        filter_dialog = new Dialog(con);

        filter_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); //before

        // Include dialog.xml file
        filter_dialog.setContentView(R.layout.dialog_apply_filters);

        // Set dialog title
        filter_dialog.getWindow().setGravity(Gravity.BOTTOM);

        filter_dialog.findViewById(R.id.txtv_applyfilters_dialog).setOnClickListener(this);

        txtv_sp_category = (TextView) filter_dialog.findViewById(R.id.txtv_sp_category);
        txtv_sp_category.setText(categoryFilters = categoryFilters.isEmpty() ? "Please Select" : categoryFilters);
        txtv_sp_category.setOnClickListener(this);

        txtv_sp_condition = (TextView) filter_dialog.findViewById(R.id.txtv_sp_condition);
        txtv_sp_condition.setText(conditionFilters = conditionFilters.isEmpty() ? "Please Select" : conditionFilters);
        txtv_sp_condition.setOnClickListener(this);

        txtv_sp_worthvalue = (TextView) filter_dialog.findViewById(R.id.txtv_sp_worthvalue);
        txtv_sp_worthvalue.setText(worthValueFilters = worthValueFilters.isEmpty() ? "Please Select" : worthValueFilters);
        txtv_sp_worthvalue.setOnClickListener(this);

        txtv_sp_listby = (TextView) filter_dialog.findViewById(R.id.txtv_sp_listby);
        txtv_sp_listby.setText(listByFilters = listByFilters.isEmpty() ? "Please Select" : listByFilters);
        txtv_sp_listby.setOnClickListener(this);

        txtv_sp_nearby = (TextView) filter_dialog.findViewById(R.id.txtv_sp_nearby);
        txtv_sp_nearby.setText(nearByFilters = nearByFilters.isEmpty() ? "Please Select" : nearByFilters);
        txtv_sp_nearby.setOnClickListener(this);

        filter_dialog.show();

    }

    TextView txtv_sp_category, txtv_sp_condition, txtv_sp_worthvalue, txtv_sp_listby, txtv_sp_nearby;
    String categoryFilters = "", conditionFilters = "", worthValueFilters = "", listByFilters = "", nearByFilters = "";
    String categoryFiltersIDS="",conditionFiltersIDS="",worthValueFiltersIDS="",listByFiltersIDS="",nearByFiltersIDS="";

    public void showSubDialog(int pos)
    {
        sub_filter_dialog = new Dialog(con);

        sub_filter_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); //before
        // Include dialog.xml file
        sub_filter_dialog.setContentView(R.layout.dialog_sub_apply_filter);
        // Set dialog title
        sub_filter_dialog.getWindow().setGravity(Gravity.BOTTOM);

        ListView listv_subFilters = (ListView) sub_filter_dialog.findViewById(R.id.listv_subFilters);
        (sub_filter_dialog.findViewById(R.id.btn_done)).setOnClickListener(this);

        adapter = new Apply_Filters_Adapter(con,fragment, list, pos);
        listv_subFilters.setAdapter(adapter);

        sub_filter_dialog.show();
    }


    public void refresh_sub_apply_filters()
    {
        adapter.notifyDataSetChanged();
    }

    public void refresh_apply_filters()
    {

        for (int i = 0; i < list.size(); i++)
        {

            for (int j = 0; j < list.get(i).size(); j++)
            {
                list.get(i).get(j).put("is_checked","no");
            }

        }

       filterSelectionDone();


    }

    public void filterSelectionDone() // Show Selected filters on textviews after pressing done button.
    {

        categoryFilters = "";
        categoryFiltersIDS="";
        for (int i = 0; i < list.get(0).size(); i++)
        {
            if (list.get(0).get(i).get("is_checked").equals("yes"))
            {
                categoryFilters = categoryFilters.isEmpty() ? list.get(0).get(i).get("cname") : categoryFilters + ", " + list.get(0).get(i).get("cname");
                categoryFiltersIDS=categoryFiltersIDS.isEmpty()?list.get(0).get(i).get("cid"):categoryFiltersIDS+","+list.get(0).get(i).get("cid");
            }
        }
        txtv_sp_category.setText(categoryFilters = categoryFilters.isEmpty() ? "Please Select" : categoryFilters);

        conditionFilters = "";
        conditionFiltersIDS = "";
        for (int i = 0; i < list.get(1).size(); i++)
        {
            if (list.get(1).get(i).get("is_checked").equals("yes"))
            {
                conditionFilters = conditionFilters.isEmpty() ? list.get(1).get(i).get("cname") : conditionFilters + ", " + list.get(1).get(i).get("cname");
                conditionFiltersIDS=conditionFiltersIDS.isEmpty()?list.get(1).get(i).get("cid"):conditionFiltersIDS+","+list.get(1).get(i).get("cid");
            }
        }
        txtv_sp_condition.setText(conditionFilters = conditionFilters.isEmpty() ? "Please Select" : conditionFilters);

        worthValueFilters = "";
        worthValueFiltersIDS = "";
        for (int i = 0; i < list.get(2).size(); i++)
        {
            if (list.get(2).get(i).get("is_checked").equals("yes"))
            {
                worthValueFilters = worthValueFilters.isEmpty() ? list.get(2).get(i).get("cname") : worthValueFilters + ", " + list.get(2).get(i).get("cname");
                worthValueFiltersIDS=worthValueFiltersIDS.isEmpty()?list.get(2).get(i).get("cid"):worthValueFiltersIDS+","+list.get(2).get(i).get("cid");
            }
        }
        txtv_sp_worthvalue.setText(worthValueFilters = worthValueFilters.isEmpty() ? "Please Select" : worthValueFilters);

        listByFilters = "";
        listByFiltersIDS = "";
        for (int i = 0; i < list.get(3).size(); i++)
        {
            if (list.get(3).get(i).get("is_checked").equals("yes"))
            {
                listByFilters = listByFilters.isEmpty() ? list.get(3).get(i).get("cname") : listByFilters + ", " + list.get(3).get(i).get("cname");
                listByFiltersIDS=listByFiltersIDS.isEmpty()?list.get(3).get(i).get("cid"):listByFiltersIDS+","+list.get(3).get(i).get("cid");
            }
        }
        txtv_sp_listby.setText(listByFilters = listByFilters.isEmpty() ? "Please Select" : listByFilters);

        nearByFilters = "";
        nearByFiltersIDS = "";
        for (int i = 0; i < list.get(4).size(); i++)
        {
            if (list.get(4).get(i).get("is_checked").equals("yes"))
            {
                nearByFilters = nearByFilters.isEmpty() ? list.get(4).get(i).get("cname") : nearByFilters + ", " + list.get(4).get(i).get("cname");
                nearByFiltersIDS=nearByFiltersIDS.isEmpty()?list.get(4).get(i).get("cid"):nearByFiltersIDS+","+list.get(4).get(i).get("cid");
            }
        }
        txtv_sp_nearby.setText(nearByFilters = nearByFilters.isEmpty() ? "Please Select" : nearByFilters);


        if(sub_filter_dialog.isShowing())
        {
            sub_filter_dialog.dismiss();
        }
    }

}
