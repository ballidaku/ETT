package com.ameba.sharanpal.ett;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.google.android.gms.gcm.GoogleCloudMessaging;

import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;

import Utils.GlobalConstant;
import Utils.GlobalUtils;
import WebServices.Super_AsyncTask;

public class SignUp extends Activity implements View.OnClickListener
{
    Context con;

    EditText ed_confirm_password, ed_fullName, ed_email, ed_password, ed_zipCode;
    SharedPreferences        sp;
    SharedPreferences.Editor editor;
    private GoogleCloudMessaging gcm;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        con = this;

        sp = getSharedPreferences("ETT", Activity.MODE_PRIVATE);
        editor = sp.edit();


        registerInBackground();
        findViewByIds();

        ed_fullName.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
                if (ed_fullName.getText().toString().length() == 1 && ed_fullName.getText().toString().equals(" "))
                {
                    ed_fullName.setText("");
                }
            }

            @Override
            public void afterTextChanged(Editable s)
            {

            }
        });



    }

    @Override
    public void onClick(View v)
    {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(con.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        switch (v.getId())
        {
            case R.id.login_now:

                startActivity(new Intent(con, Login.class));
                break;

            case R.id.btn_signup:

                if (validation())
                {
                    HashMap<String, String> dataInput = new HashMap<>();
                    dataInput.put("full_name", ed_fullName.getText().toString());
                    dataInput.put("email", ed_email.getText().toString());
                    dataInput.put("zip", ed_zipCode.getText().toString());
                    dataInput.put("password", ed_password.getText().toString());
                    dataInput.put("device_id", sp.getString("GCM_Reg_id", ""));

                    //new Login_web(SignUp.this, dataInput, GlobalConstant.URL + "register").execute();


                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
                    {
                        new Super_AsyncTask(con, dataInput, GlobalConstant.URL + "register", "set_data").executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                    }
                    else
                    {
                        new Super_AsyncTask(con, dataInput, GlobalConstant.URL + "register","set_data").execute();
                    }
                }

                break;

            default:
                break;

        }
    }


    public void set_data(String response)
    {
        try
        {

            JSONObject mainobj = new JSONObject(response);
            if (mainobj.opt("status").equals("Success"))
            {

                JSONObject data = mainobj.getJSONObject("data");
                editor.putString("user_id", data.optString("user_id"));
                editor.putString("full_name", data.optString("full_name"));
                editor.putString("zip", data.optString("zip"));
                editor.putString("email", data.optString("email"));
                editor.putString("device_id", data.optString("device_id"));
                editor.putString("type", data.optString("type"));

                editor.putString("profile_image", data.optString("profile_image")); // IT IS
                // GETTING ONLY IN THE CASE
                // OF LOGIN (NOT SIGN UP)
                editor.putString("flag", data.optString("flag")); // IT IS GETTING ONLY IN
                // THE CASE OF LOGIN (NOT
                // SIGN UP)

                editor.commit();

                GlobalUtils.show_Toast(mainobj.optString("message"), con);

                Intent i = new Intent(con, Main_Tabs.class);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                con.startActivity(i);

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

    public void findViewByIds()
    {

        findViewById(R.id.login_now).setOnClickListener(this);
        findViewById(R.id.btn_signup).setOnClickListener(this);
        ed_confirm_password = (EditText) findViewById(R.id.ed_confirm_password);
        ed_fullName = (EditText) findViewById(R.id.ed_fullName);
        ed_email = (EditText) findViewById(R.id.ed_email);
        ed_password = (EditText) findViewById(R.id.ed_password);
        ed_zipCode = (EditText) findViewById(R.id.ed_zipCode);
    }

    private boolean validation()
    {
        if (ed_fullName.getText().toString().trim().isEmpty())
        {

            ed_fullName.setError("Enter Full Name");

            return false;
        }
        else if (!GlobalUtils.is_valid_email(ed_email.getText().toString().trim()))
        {
            ed_email.setError("Enter a valid email");
            return false;
        }
        else if (ed_password.getText().toString().trim().isEmpty())
        {
            ed_password.setError("Enter password");
            return false;
        }
        else if (ed_password.getText().toString().length() < 6)
        {
            ed_password.setError("minimum length should be 6 characters");
            return false;
        }
        else if (!ed_confirm_password.getText().toString().trim().equals(ed_password.getText().toString().trim()))
        {
            ed_confirm_password.setError("Password not matched");
            return false;
        }
        else if (ed_zipCode.getText().toString().trim().isEmpty())
        {
            ed_zipCode.setError("Enter ZipCode");
            return false;
        }
        else if (ed_zipCode.getText().toString().length() < 3)
        {
            ed_zipCode.setError("minimum length should be 3 characters");
            return false;
        }
        else if (!sp.contains("GCM_Reg_id"))
        {
            GlobalUtils.show_Toast("Please check network.", con);
            return false;
        }
        return true;
    }

    private void registerInBackground()
    {
        new AsyncTask()
        {
            @Override
            protected Void doInBackground(Object... params)
            {
                String msg = "";
                try
                {
                    if (gcm == null)
                    {
                        gcm = GoogleCloudMessaging.getInstance(con);
                    }
                    String GCM_Reg_id = gcm.register(GlobalConstant.SENDER_ID);

                    sp.edit().putString("GCM_Reg_id", GCM_Reg_id).commit();

                    //					System.out.println("GCM_Reg_id===" + GCM_Reg_id);
                }
                catch (IOException ex)
                {
                    registerInBackground();
                    msg = "Error :" + ex.getMessage();
                }
                return null;
            }

        }.execute(null, null, null);
    }

}
