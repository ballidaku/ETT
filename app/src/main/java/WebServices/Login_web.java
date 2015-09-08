package WebServices;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;

import com.ameba.sharanpal.ett.Login;
import com.ameba.sharanpal.ett.Main_Tabs;
import com.ameba.sharanpal.ett.R;
import com.ameba.sharanpal.ett.SignUp;

import org.json.JSONObject;

import java.util.HashMap;

import Utils.GlobalUtils;
import Utils.WebServiceHandler;

/**
 * Created by ameba on 7/9/15.
 */
public class Login_web extends AsyncTask<Void,Void,String>
{


    String URL;
    HashMap<String,String> inputData;
    Context context;
    SharedPreferences sp;
    SharedPreferences.Editor editor;
    ProgressDialog dialog;


    public Login_web(Context context, HashMap<String, String> inputData, String URL) {
        this.context = context;
        this.inputData = inputData;
        this.URL = URL;

        sp = context.getSharedPreferences("ETT",context.MODE_PRIVATE);
        editor = sp.edit();

    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        dialog = ProgressDialog.show(context, "", "");
        dialog.setContentView(R.layout.progress_dialog);
        dialog.show();

    }

    @Override
    protected String doInBackground(Void... params)
    {


        return new WebServiceHandler().performPostCall(URL,inputData);
    }


    @Override
    protected void onPostExecute(String ResponseString)
    {
        super.onPostExecute(ResponseString);

        dialog.dismiss();

        Log.e("==login/signup==", ResponseString);
        try
        {
            JSONObject mainobj = new JSONObject(ResponseString);
            if (mainobj.opt("status").equals("Success"))
            {
                try
                {
                    JSONObject data = mainobj.getJSONObject("data");
                    editor.putString("user_id",data.optString("user_id"));
                    editor.putString("full_name",data.optString("full_name"));
                    editor.putString("zip",data.optString("zip"));
                    editor.putString("email",data.optString("email"));
                    editor.putString("device_id",data.optString("device_id"));
                    editor.putString("type",data.optString("type"));

                    editor.putString("profile_image",data.optString("profile_image")); // IT IS GETTING ONLY IN THE CASE OF LOGIN (NOT SIGN UP)
                    editor.putString("flag",data.optString("flag")); // IT IS GETTING ONLY IN THE CASE OF LOGIN (NOT SIGN UP)

                    editor.commit();

                    GlobalUtils.show_Toast("Success", context);

                    context.startActivity(new Intent(context, Main_Tabs.class));
                    ((Activity)context).finish();
                }
                catch(Exception e)
                {
                e.printStackTrace();
                }
            }
            else
            {
                context.startActivity(new Intent(context, SignUp.class));
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }


    }

}
