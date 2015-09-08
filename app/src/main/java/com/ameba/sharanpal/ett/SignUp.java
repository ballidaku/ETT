package com.ameba.sharanpal.ett;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import java.util.HashMap;

import Utils.GlobalConstant;
import Utils.GlobalUtils;
import WebServices.Login_web;


public
class SignUp extends Activity implements View.OnClickListener
{

    Context con;
    String DeviceSerialNo;
    EditText ed_confirm_password,ed_fullName,ed_email,ed_password,ed_zipCode;

    public void getMacAddress()
    {
        WifiManager manager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
        WifiInfo info = manager.getConnectionInfo();
        DeviceSerialNo = info.getMacAddress();

    }

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

            ed_fullName.setError("Enter Name");

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

        return true;
    }


    @Override
    protected
    void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        findViewById(R.id.login_now).setOnClickListener(this);
        findViewById(R.id.btn_signup).setOnClickListener(this);

        getMacAddress();
        findViewByIds();

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
                    dataInput.put("device_id","hegi ni");


                    new Login_web(SignUp.this,dataInput, GlobalConstant.URL+"register").execute();

                }



                break;

            default:
                break;

        }
    }


}
