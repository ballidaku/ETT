package com.ameba.sharanpal.ett;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.plus.Plus;
import com.google.android.gms.plus.model.people.Person;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;

import Utils.GlobalConstant;
import Utils.GlobalUtils;
import WebServices.Super_AsyncTask;

public class Login extends Activity implements View.OnClickListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener
{
    Context  con;
    EditText edUsername, edPswd;
    CallbackManager          callbackManager;
    String                   FirstName;
    LoginButton              loginButton;
    GoogleCloudMessaging     gcm;
    SharedPreferences        sp;
    SharedPreferences.Editor editor;
    private static final int    STATE_DEFAULT     = 0;
    private static final int    STATE_SIGN_IN     = 1;
    private static final int    STATE_IN_PROGRESS = 2;
    private static final int    RC_SIGN_IN        = 0;
    private static final String SAVED_PROGRESS    = "sign_in_progress";
    private GoogleApiClient mGoogleApiClient;
    private int             mSignInProgress;
    private PendingIntent   mSignInIntent;
    private int             mSignInError;
    private boolean mServerHasToken = true;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
        setContentView(R.layout.activity_login);
        con = this;

        sp = con.getSharedPreferences("ETT", con.MODE_PRIVATE);
        editor = sp.edit();

        registerInBackground();
        findViewById(R.id.sign_up_now).setOnClickListener(this);
        findViewById(R.id.btn_login).setOnClickListener(this);
        findViewById(R.id.lay_forgot_password).setOnClickListener(this);
        loginButton = (LoginButton) findViewById(R.id.login_button); //
        // facebook login
        // button
        loginButton.setBackgroundResource(R.mipmap.fb);
        loginButton.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
        edPswd = (EditText) findViewById(R.id.edpswd);
        edUsername = (EditText) findViewById(R.id.edUsername);
        edPswd.setOnClickListener(this);
        findViewById(R.id.imgv_loginFb).setOnClickListener(this);
        findViewById(R.id.imgv_googlePlus).setOnClickListener(this);

        //**************************************Facebook******************************************
        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>()
        {
            @Override
            public void onSuccess(LoginResult loginResult)
            {
                GraphRequest request = GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback()
                {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response)
                    {
                        // Application code
                        try
                        {
                            Log.e("object", "" + object);
                            if (sp.contains("GCM_Reg_id"))
                            {
                                HashMap<String, String> loginData = new HashMap<String, String>();
                                loginData.put("full_name", object.getString("name"));
                                loginData.put("email", object.getString("email"));
                                loginData.put("type", "F");
                                loginData.put("device_id", sp.getString("GCM_Reg_id", ""));
                               // Log.e("FirstName", "" + FirstName);
                                login(loginData);
                            }
                            else
                            {
                                stop_fb();
                                registerInBackground();
                                GlobalUtils.show_Toast("Please check network", con);
                            }
                        }
                        catch (Exception e)
                        {
                            stop_fb();
                            e.printStackTrace();
                        }
                    }
                });
                Bundle parameters = new Bundle();
                parameters.putString("fields", "email,name");
                request.setParameters(parameters);
                request.executeAsync();
            }

            @Override
            public void onCancel()
            {
                Log.e("onCancel", "Hello");
            }

            @Override
            public void onError(FacebookException exception)
            {

                Log.e("onError", "Hello");
            }
        });
        if (savedInstanceState != null)
        {
            mSignInProgress = savedInstanceState.getInt(SAVED_PROGRESS, STATE_DEFAULT);
        }
        mGoogleApiClient = buildGoogleApiClient();
        if (mGoogleApiClient.isConnected())
        {
            Plus.AccountApi.clearDefaultAccount(mGoogleApiClient);
            mGoogleApiClient.disconnect();
            Plus.AccountApi.clearDefaultAccount(mGoogleApiClient);
            Plus.AccountApi.revokeAccessAndDisconnect(mGoogleApiClient);
        }
    }

    public void stop_fb()
    {
        Log.e("Logout", "Logout");
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

    @Override
    protected void onStop()
    {
        super.onStop();
        if (mGoogleApiClient.isConnected())
        {
            mGoogleApiClient.disconnect();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState)
    {
        super.onSaveInstanceState(outState);
        outState.putInt(SAVED_PROGRESS, mSignInProgress);
    }

    View.OnClickListener oc;
    EditText             forgot_password_email;

    @Override
    public void onClick(View v)
    {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(con.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        switch (v.getId())
        {
            case R.id.imgv_loginFb:
                loginButton.performClick();
                break;
            case R.id.imgv_googlePlus:
                if (!mGoogleApiClient.isConnecting())
                {
                    mSignInProgress = STATE_SIGN_IN;
                    mGoogleApiClient.connect();
                }
                break;
            case R.id.sign_up_now:
                startActivity(new Intent(con, SignUp.class));
                break;
            case R.id.btn_login:
                if (validation())
                {
                    if (sp.contains("GCM_Reg_id"))
                    {
                        HashMap<String, String> dataInput = new HashMap<>();
                        dataInput.put("email", edUsername.getText().toString());
                        dataInput.put("password", edPswd.getText().toString());
                        dataInput.put("device_id", sp.getString("GCM_Reg_id", ""));
                        login(dataInput);
                    }
                    else
                    {
                        registerInBackground();
                        GlobalUtils.show_Toast("Please check " + "network", con);
                    }
                }
                break;
            case R.id.lay_forgot_password:
                oc = new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        String email_s = forgot_password_email.getText().toString().trim();
                        if (email_s.length() == 0)
                        {
                            GlobalUtils.show_Toast("Please enter email.", con);
                        }
                        else if (GlobalUtils.is_valid_email(email_s))
                        {
                            Log.e("forgot", email_s);
                            GlobalUtils.forgot_dialog.dismiss();
                            // new
                            // Forgot_Password_ProgressTask(con, email_s).execute();

                            HashMap<String, String> map = new HashMap<>();
                            map.put("email", email_s);

                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
                            {
                                new Super_AsyncTask(con, map, GlobalConstant.URL + "forgot_password", "set_forgot_data").executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                            }
                            else
                            {
                                new Super_AsyncTask(con, map, GlobalConstant.URL + "forgot_password", "set_forgot_data").execute();
                            }

                        }
                        else
                        {
                            GlobalUtils.show_Toast("Please enter valid email.", con);
                        }
                    }
                };
                forgot_password_email = GlobalUtils.show_forgot_dialog(con, oc);
                break;
            case R.id.edpswd:
                edPswd.setError(null);
                break;
            default:
                break;
        }
    }

    public void login(HashMap<String, String> map)
    {

        Log.e("map",""+map);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
        {
            new Super_AsyncTask(con, map, GlobalConstant.URL + "login", "set_data").executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        }
        else
        {
            new Super_AsyncTask(con, map, GlobalConstant.URL + "login", "set_data").execute();
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

    public void set_forgot_data(String response)
    {
        try
        {
            if (response != null)
            {

                JSONObject msg_json = new JSONObject(response);

                if (msg_json.getString("status").equals("Success"))
                {
                    GlobalUtils.show_Toast(msg_json.getString("message"), con);

                }
                else if (msg_json.equals("Failure"))
                {
                    GlobalUtils.show_Toast(msg_json.getString("message"), con);
                }

            }

        }
        catch (JSONException e)
        {
            e.printStackTrace();

        }
    }

    private boolean validation()
    {
        if (!GlobalUtils.is_valid_email(edUsername.getText().toString().trim()))
        {
            edUsername.setError("Enter a valid " + "email");
            return false;
        }
        else if (edPswd.getText().toString().trim().isEmpty())
        {
            edPswd.setError("Enter Password");
            return false;
        }
        else if (edPswd.getText().toString().length() < 6)
        {
            edPswd.setError("minimum length " + "should be 6 characters");
            return false;
        }
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
        switch (requestCode)
        {
            case RC_SIGN_IN:
                if (resultCode == RESULT_OK)
                {
                    // If the error resolution was
                    // successful we should continue
                    // processing errors.
                    mSignInProgress = STATE_SIGN_IN;
                }
                else
                {
                    // If the error resolution was
                    // not successful or the user
                    // canceled,
                    // we should stop processing
                    // errors.
                    mSignInProgress = STATE_DEFAULT;
                }
                if (!mGoogleApiClient.isConnecting())
                {
                    // If Google Play services
                    // resolved the issue with a
                    // dialog then
                    // onStart is not called so we
                    // need to re-attempt
                    // connection here.
                    mGoogleApiClient.connect();
                }
                break;
        }
    }

    private void resolveSignInError()
    {
        if (mSignInIntent != null)
        {
            // We have an intent which will
            // allow our user to sign in or
            // resolve an error.  For example
            // if the user needs to
            // select an account to sign in
            // with, or if they need to consent
            // to the permissions your app is
            // requesting.
            try
            {
                // Send the pending intent that
                // we stored on the most recent
                // OnConnectionFailed callback.
                //  This will allow the user to
                // resolve the error currently
                // preventing our connection to
                // Google Play services.
                mSignInProgress = STATE_IN_PROGRESS;
                startIntentSenderForResult(mSignInIntent.getIntentSender(), RC_SIGN_IN, null, 0, 0, 0);
            }
            catch (IntentSender.SendIntentException e)
            {
                Log.e("====gPlus signIn Ex===", "Sign in intent could" +
                          " not be " +
                          "sent: " +
                          e.getLocalizedMessage());
                // The intent was canceled
                // before it was sent.  Attempt
                // to connect to
                // get an updated
                // ConnectionResult.
                mSignInProgress = STATE_SIGN_IN;
                mGoogleApiClient.connect();
            }
        }
        else
        {
            // Google Play services wasn't able
            // to provide an intent for some
            // error types, so we show the
            // default Google Play services error
            // dialog which may still start an
            // intent on our behalf if the
            // user can resolve the issue.
            //createErrorDialog().show();
        }
    }

    private GoogleApiClient buildGoogleApiClient()
    {
        // When we build the GoogleApiClient we
        // specify where connected and
        // connection failed callbacks should
        // be returned, which Google APIs our
        // app uses and which OAuth 2.0 scopes
        // our app requests.
        GoogleApiClient.Builder builder = new GoogleApiClient.Builder(this).addConnectionCallbacks(this).addOnConnectionFailedListener(this).addApi(Plus.API, Plus.PlusOptions.builder().build()).addScope(Plus.SCOPE_PLUS_LOGIN);
        return builder.build();
    }

    @Override
    public void onConnected(Bundle bundle)
    {
        // Reaching onConnected means we
        // consider the user signed in.
        Log.e("google integration====", "onConnected");
        // Retrieve some profile information to
        // personalize our app for the user.
        final Person currentUser = Plus.PeopleApi.getCurrentPerson(mGoogleApiClient);
        Log.e("==Success name==", currentUser.getDisplayName());
        Log.e("google plus ===", "ETT connected" +
                  " to " + Plus.AccountApi.getAccountName(mGoogleApiClient));
        Log.e("==Success name==", Plus.AccountApi.getAccountName(mGoogleApiClient));
        if (sp.contains("GCM_Reg_id"))
        {
            HashMap<String, String> loginData = new HashMap<>();
            loginData.put("full_name", currentUser.getDisplayName());
            loginData.put("email", Plus.AccountApi.getAccountName(mGoogleApiClient));
            loginData.put("type", "G");
            loginData.put("device_id", sp.getString("GCM_Reg_id", ""));
            login(loginData);
        }
        else
        {
            registerInBackground();
            GlobalUtils.show_Toast("Please " + "check network", con);
        }
        mSignInProgress = STATE_DEFAULT;
    }

    @Override
    public void onConnectionSuspended(int i)
    {
        Log.e("==gPlus conn ===", "connection " + "suspended");
        // The connection to Google Play
        // services was lost for some reason.
        // We call connect() to attempt to
        // re-establish the connection or get a
        // ConnectionResult that we can attempt
        // to resolve.
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnectionFailed(ConnectionResult result)
    {
        // Refer to the javadoc for
        // ConnectionResult to see what error
        // codes might
        // be returned in onConnectionFailed.
        Log.e("====gPlus FAILED===", "onConnectionFailed: " +
                  "ConnectionResult" +
                  ".getErrorCode() = " +
                  "" + result.getErrorCode());
        if (result.getErrorCode() == ConnectionResult.API_UNAVAILABLE)
        {
            // An API requested for
            // GoogleApiClient is not available
            // . The device's current
            // configuration might not be
            // supported with the requested API
            // or a required component
            // may not be installed, such as
            // the Android Wear application.
            // You may need to use a
            // second GoogleApiClient to manage
            // the application's optional APIs.
            Log.w("google", "API Unavailable.");
        }
        else if (mSignInProgress != STATE_IN_PROGRESS)
        {
            // We do not have an intent in
            // progress so we should store the
            // latest
            // error resolution intent for use
            // when the sign in button is clicked.
            mSignInIntent = result.getResolution();
            mSignInError = result.getErrorCode();
            if (mSignInProgress == STATE_SIGN_IN)
            {
                // STATE_SIGN_IN indicates the
                // user already clicked the
                // sign in button
                // so we should continue
                // processing errors until the
                // user is signed in
                // or they click cancel.
                resolveSignInError();
            }
        }
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
                    sp.edit().putString("GCM_Reg_id", GCM_Reg_id).apply();
                    //					System.out
                    // .println("GCM_Reg_id===" +
                    // GCM_Reg_id);

                    Log.e("GCM_Reg_id",".."+GCM_Reg_id);
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
