package Fragments;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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

import java.util.ArrayList;
import java.util.List;

import Adapters.Home_Adapter;
import Adapters.News_Feeds_Adapter;


public
class Home extends Fragment implements View.OnClickListener
{

    Context con;
    ListView listv_home;

    ArrayAdapter<String> adapter;
    List<String> list;

    public
    Home()
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
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);

        con=getActivity();

        listv_home=(ListView)rootView.findViewById(R.id.listv_home);

        rootView.findViewById(R.id.txtv_applyfilters).setOnClickListener(this);


        listv_home.setAdapter(new Home_Adapter(con));

        list = new ArrayList<String>();
        list.add("Appliances");
        list.add("Brand New");
        list.add("Under 15 dollars");
        list.add("Public");
        list.add("10 miles");

        return rootView;
    }

    @Override public
    void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.txtv_applyfilters:

                show();
                break;

            case R.id.txtv_applyfilters_dialog:

                dialog.dismiss();
                break;

            default:
                break;

        }
    }

     Dialog dialog;
    public void show()
    {
         dialog = new Dialog(con);

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); //before

        // Include dialog.xml file
        dialog.setContentView(R.layout.dialog_apply_filters);

        // Set dialog title
        dialog.getWindow().setGravity(Gravity.BOTTOM);


        dialog.findViewById(R.id.txtv_applyfilters_dialog).setOnClickListener(this);

        txtv_categoery = (Spinner)dialog. findViewById(R.id.txtv_categoery);
        txtv_condition = (Spinner)dialog. findViewById(R.id.txtv_condition);
        txtv_worthvalue = (Spinner)dialog. findViewById(R.id.txtv_worthvalue);
        txtv_listby = (Spinner)dialog. findViewById(R.id.txtv_listby);
        txtv_nearby = (Spinner)dialog.findViewById(R.id.txtv_nearby);


        adapter = new ArrayAdapter<String>(con, android.R.layout.simple_spinner_item, list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        txtv_categoery.setAdapter(adapter);
        txtv_categoery.setSelection(0); // jfor designing only
        txtv_condition.setAdapter(adapter);
        txtv_condition.setSelection(1); // jfor designing only
        txtv_worthvalue.setAdapter(adapter);
        txtv_worthvalue.setSelection(2); // jfor designing only
        txtv_listby.setAdapter(adapter);
        txtv_listby.setSelection(3); // jfor designing only
        txtv_nearby.setAdapter(adapter);
        txtv_nearby.setSelection(4); // for de

        dialog.show();

    }

    Spinner txtv_categoery;
    Spinner txtv_condition;
    Spinner txtv_worthvalue;
    Spinner txtv_listby;
    Spinner txtv_nearby;




}
