package WebServices;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.util.Log;

import com.ameba.sharanpal.ett.Login;
import com.ameba.sharanpal.ett.R;
import com.ameba.sharanpal.ett.SignUp;

import org.json.JSONObject;

import java.util.HashMap;

import Fragments.Home;
import Utils.GlobalUtils;
import Utils.WebServiceHandler;

/**
 Created by sharan on 10/9/15. */
public class Super_AsyncTask extends AsyncTask<Void, Void, String>
{

    String                  URL;
    HashMap<String, String> inputData;
    Context                 con;
    ProgressDialog          dialog;
    String                  type;
    Fragment                fragment;
    String                  is_activity_fragment, method_name;

    public Super_AsyncTask(Context con, HashMap<String, String> inputData, String URL, String method_name) // for Activity , Post
    {
        this.con = con;
        this.inputData = inputData;
        this.URL = URL;
        this.type = "POST";
        this.is_activity_fragment = "a";
        this.method_name = method_name;
    }

    public Super_AsyncTask(Context con, String URL, String method_name) // for Activity , Get
    {
        this.con = con;
        this.URL = URL;
        this.type = "GET";
        this.is_activity_fragment = "a";
        this.method_name = method_name;
    }

    public Super_AsyncTask(Context con, Fragment fragment, HashMap<String, String> inputData, String URL, String method_name)// for Fragment , Post
    {
        this.con = con;
        this.fragment = fragment;
        this.inputData = inputData;
        this.URL = URL;
        this.type = "POST";
        this.is_activity_fragment = "f";
        this.method_name = method_name;
    }

    public Super_AsyncTask(Context con,Fragment fragment, String URL, String method_name) // for Fragment , Get
    {
        this.con = con;
        this.fragment = fragment;
        this.URL = URL;
        this.type = "GET";
        this.is_activity_fragment = "f";
        this.method_name = method_name;
    }

    @Override
    protected void onPreExecute()
    {
        super.onPreExecute();

        dialog = ProgressDialog.show(con, "", "");
        dialog.setContentView(R.layout.progress_dialog);
        dialog.show();

    }

    @Override
    protected String doInBackground(Void... params)
    {
        String response = "";

        Log.e("inputData",".."+inputData);
        try
        {
            if (type.equals("POST"))
            {
                response = new WebServiceHandler().performPostCall(URL, inputData);
            }
            else
            {
                response = new WebServiceHandler().performGetCall(URL);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return response;
    }

    @Override
    protected void onPostExecute(String ResponseString)
    {
        super.onPostExecute(ResponseString);

        if (dialog.isShowing())
        {
            dialog.dismiss();
        }

        Log.e("Response for " + con.getClass().getName(), " " + ResponseString);

        if (!ResponseString.equals("SLOW") && !ResponseString.equals("ERROR"))
        {

            if (is_activity_fragment.equals("a")) // activity
            {
                if (con instanceof Login && method_name.equals("set_data")) // Login
                {
                    ((Login) con).set_data(ResponseString);
                }
                else if (con instanceof Login && method_name.equals("set_forgot_data")) // Login
                {
                    ((Login) con).set_forgot_data(ResponseString);
                }
                if (con instanceof SignUp && method_name.equals("set_data")) // SignUp
                {
                    ((SignUp) con).set_data(ResponseString);
                }

            }
            else // fragment
            {
                if (fragment instanceof Home && method_name.equals("set_filter_data")) // Home Apply Filters
                {
                    ((Home) fragment).set_filter_data(ResponseString);
                }
                else if (fragment instanceof Home && method_name.equals("set_listing_data")) // Home Get Listings
                {
                    ((Home) fragment).set_listing_data(ResponseString);
                }

            }
        }
        else if (ResponseString.equals("SLOW"))
        {
            GlobalUtils.show_Toast("Please check your network.", con);

        }
        else if (ResponseString.equals("ERROR"))
        {
            GlobalUtils.show_Toast("Server side error.", con);
        }

    }
}
