package com.ameba.sharanpal.ett;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.google.android.gms.gcm.GoogleCloudMessaging;

import java.util.HashMap;

import Utils.GlobalConstant;
import Utils.GlobalUtils;
import WebServices.Login_web;


public
class SignUp extends Activity implements View.OnClickListener
{
    Context con;
    String device_id;
    EditText ed_confirm_password,ed_fullName,ed_email,ed_password,ed_zipCode;
    SharedPreferences sharedPref;
    private GoogleCloudMessaging gcm;

    public void findViewByIds()
    {
        ed_confirm_password = (EditText)findViewById(R.id.ed_confirm_password);
        ed_fullName = (EditText)findViewById(R.id.ed_fullName);
        ed_email = (EditText)findViewById(R.id.ed_email);
        ed_password = (EditText)findViewById(R.id.ed_password);
        ed_zipCode = (EditText)findViewById(R.id.ed_zipCode);
    }



    private boolean validation()
    {
        if(ed_fullName.getText().toString().trim().isEmpty())
        {

            ed_fullName.setError("Enter Full Name");

            return false;
        }
        else if(!GlobalUtils.is_valid_email(ed_email.getText().toString().trim()))
        {
            ed_email.setError("Enter a valid email");
            return false;
        }
        else if(ed_password.getText().toString().trim().isEmpty())
        {
            ed_password.setError("Enter password");
            return false;
        }
        else if(ed_password.getText().toString().length()<6)
        {
            ed_password.setError("minimum length should be 6 characters");
            return false;
        }
        else if(!ed_confirm_password.getText().toString().trim().equals(ed_password.getText().toString().trim()))
        {
            ed_confirm_password.setError("Password not matched");
            return false;
        }
        else if(ed_zipCode.getText().toString().trim().isEmpty())
        {
            ed_zipCode.setError("Enter ZipCode");
            return false;
        }
        else if(ed_zipCode.getText().toString().length()<3)
        {
            ed_zipCode.setError("minimum length should be 3 characters");
            return false;
        }
        return true;
    }


    public void getRegisterationID()
    {
        new AsyncTask<Object, Object, Object>()
        {
            @Override
            protected Object doInBackground(Object... params)
            {
                String msg = "";
                try
                {
                    if (gcm == null)
                    {
                        gcm = GoogleCloudMessaging.getInstance(getBaseContext());
                    }
                    device_id = gcm.register(GlobalConstant.SENDER_ID);

                    msg = "Registration ID=" + device_id;

                    Log.e(msg + "========== ", device_id);
                }
                catch (Exception ex)
                {
                    device_id = "";
                    msg = "Error :" + ex.getMessage();
                }
                return msg;
            }

            protected void onPostExecute(Object result)
            {
                if (!device_id.equals("")) {
                    SharedPreferences.Editor editPref = sharedPref.edit();
                    editPref.putString("device_id", device_id);
                    editPref.apply();
                } else
                {
                    getRegisterationID();
                }
            }
        }.execute();
    }

    @Override
    protected
    void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        sharedPref = getSharedPreferences("ETT",Activity.MODE_PRIVATE);

        findViewById(R.id.login_now).setOnClickListener(this);
        findViewById(R.id.btn_signup).setOnClickListener(this);
        getRegisterationID();
        findViewByIds();
        ed_fullName.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(ed_fullName.getText().toString().length() == 1 && ed_fullName.getText().toString().equals(" "))
                {
                    ed_fullName.setText("");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        con=this;

    }

    @Override public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.login_now:

                startActivity(new Intent(con,Login.class));
                break;

            case R.id.btn_signup:


                if(validation())
                {
                    HashMap<String,String> dataInput=new HashMap<>();
                    dataInput.put("full_name",ed_fullName.getText().toString());
                    dataInput.put("email",ed_email.getText().toString());
                    dataInput.put("zip",ed_zipCode.getText().toString());
                    dataInput.put("password",ed_password.getText().toString());
                    dataInput.put("device_id",device_id);

                    new Login_web(SignUp.this,dataInput, GlobalConstant.URL+"register").execute();
                }



                break;

            default:
                break;

        }
    }


}
