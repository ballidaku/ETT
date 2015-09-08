package com.ameba.sharanpal.ett;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.plus.People;
import com.google.android.gms.plus.Plus;
import com.google.android.gms.plus.model.people.Person;
import com.google.android.gms.plus.model.people.PersonBuffer;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import Utils.GlobalConstant;
import Utils.GlobalUtils;
import WebServices.Login_web;


public
class Login extends Activity implements View.OnClickListener,GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener,
        ResultCallback<People.LoadPeopleResult>,CheckBox.OnCheckedChangeListener, GoogleApiClient.ServerAuthCodeCallbacks
{
    Context con;
//    Button btngooglePlus,btn_loginFb;
    EditText edUsername,edPswd;

    // FACEBOOK INTEGRATION VARIABLES
    CallbackManager callbackManager;
    String FirstName;
    ProfileTracker profileTracker;
    LoginButton loginButton;// facebook login button

    // GOOGLE PLUS VARIABLES

    private static final String WEB_CLIENT_ID = "245280530525-s3rss19dq5i1eod706ig1agrk1up8a1g.apps.googleusercontent.com";
    private static final int STATE_DEFAULT = 0;
    private static final int STATE_SIGN_IN = 1;
    private static final int STATE_IN_PROGRESS = 2;
    private static final int RC_SIGN_IN = 0;

    private static final String SAVED_PROGRESS = "sign_in_progress";
    private static final String SERVER_BASE_URL = "SERVER_BASE_URL";
    private static final String EXCHANGE_TOKEN_URL = SERVER_BASE_URL + "/exchangetoken";
    private static final String SELECT_SCOPES_URL = SERVER_BASE_URL + "/selectscopes";
    private GoogleApiClient mGoogleApiClient;
    private int mSignInProgress;
    private PendingIntent mSignInIntent;
    private int mSignInError;
    private boolean mServerHasToken = true;

    private SignInButton mSignInButton;

    @Override
    protected
    void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();

        setContentView(R.layout.activity_login);

        con=this;
        findViewById(R.id.sign_up_now).setOnClickListener(this);
        findViewById(R.id.btn_login).setOnClickListener(this);

        loginButton = (LoginButton) findViewById(R.id.login_button); // facebook login button
        loginButton.setBackgroundResource(R.mipmap.fb);
        loginButton.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
        loginButton.setReadPermissions(Arrays.asList("email"));

        edPswd=(EditText) findViewById(R.id.edpswd);
        edUsername=(EditText) findViewById(R.id.edUsername);
        mSignInButton = (SignInButton) findViewById(R.id.sign_in_button);
//        btngooglePlus = (Button)findViewById(R.id.btn_googlePlus);
//        btn_loginFb = (Button)findViewById(R.id.btn_loginFb);
        mSignInButton.setOnClickListener(this);




        findViewById(R.id.btn_loginFb).setOnClickListener(this);
        findViewById(R.id.btn_googlePlus).setOnClickListener(this);
//
//        btngooglePlus.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v)
//            {
//                Log.e("google","clicked");
//                mSignInButton.performClick();
//            }
//        });
//        btn_loginFb.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v)
//            {
//                 loginButton.performClick();
//
//            }
//        });
























        // Callback registration
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>()
        {
            @Override
            public
            void onSuccess(LoginResult loginResult)
            {
                Log.e("onSuccess", "Hello" + loginResult.toString());
                profileTracker.startTracking();
            }
            @Override
            public
            void onCancel()
            {
                // App code
            }

            @Override
            public
            void onError(FacebookException exception)
            {
                // App code
            }
        });
        profileTracker = new ProfileTracker()
        {
            @Override
            protected
            void onCurrentProfileChanged(Profile oldProfile, Profile currentProfile)
            {
                if( Profile.getCurrentProfile() != null)
                {
                    FirstName = currentProfile.getName();
                }

            }
        };

        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>()
                {
                    @Override
                    public
                    void onSuccess(LoginResult loginResult)
                    {
                        GraphRequest request = GraphRequest.newMeRequest(
                                loginResult.getAccessToken(),
                                new GraphRequest.GraphJSONObjectCallback() {
                                    @Override
                                    public void onCompleted(
                                            JSONObject object,
                                            GraphResponse response) {
                                            // Application code
                                        try
                                        {

                                            HashMap<String,String> loginData = new HashMap<String, String>();
//
                                            String fb_emailID = object.getString("email");

                                            loginData.put("full_name",FirstName);
                                            loginData.put("email", fb_emailID);
                                            loginData.put("type", "F");

                                            new Login_web(Login.this, loginData,GlobalConstant.URL+"login").execute();

                                            try
                                            {
                                                FacebookSdk.sdkInitialize(Login.this);

                                                LoginManager loginManager = null;

                                                loginManager = LoginManager.getInstance();
                                                loginManager.logOut();
                                            }
                                            catch (Exception e)
                                            {
                                                e.printStackTrace();
                                            }



                                        }
                                        catch(Exception e)
                                        {
                                            startActivity(new Intent(Login.this,SignUp.class));
                                            finish();
                                            e.printStackTrace();
                                        }

                                    }
                                });
                        Bundle parameters = new Bundle();
                        parameters.putString("fields", "email");
                        request.setParameters(parameters);
                        request.executeAsync();

                        profileTracker.startTracking();
                    }

                    @Override
                    public
                    void onCancel()
                    {
                        Log.e("onCancel", "Hello");
                    }

                    @Override
                    public
                    void onError(FacebookException exception)
                    {
                        Log.e("onError", "Hello");
                    }
                });

        AccessTokenTracker accessTokenTracker = new AccessTokenTracker()
        {
            @Override
            protected
            void onCurrentAccessTokenChanged(AccessToken oldAccessToken,AccessToken currentAccessToken)
            {

            }
        };



        AccessToken accessToken = AccessToken.getCurrentAccessToken();



























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


    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        profileTracker.stopTracking();
    }

    @Override
    protected void onStart()
    {
        super.onStart();
//        mGoogleApiClient.connect();
    }

    @Override
    protected void onStop()
    {
        super.onStop();

        if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState)
    {
        super.onSaveInstanceState(outState);
        outState.putInt(SAVED_PROGRESS, mSignInProgress);
    }
    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.btn_loginFb:
                loginButton.performClick();
                break;
            case R.id.btn_googlePlus:
                if (!mGoogleApiClient.isConnecting())
                {
                    mSignInProgress = STATE_SIGN_IN;
                    mGoogleApiClient.connect();
                }
                break;
            case R.id.sign_in_button:
                if (!mGoogleApiClient.isConnecting())
                {
                    mSignInProgress = STATE_SIGN_IN;
                    mGoogleApiClient.connect();
                }
                break;
            case R.id.sign_up_now:

                startActivity(new Intent(con,SignUp.class));
                break;
            case R.id.btn_login:



                if(validation())
                {
                    HashMap<String,String> dataInput=new HashMap<>();
                    dataInput.put("email",edUsername.getText().toString());
                    dataInput.put("password",edPswd.getText().toString());

                    new Login_web(Login.this,dataInput, GlobalConstant.URL+"login").execute();
                }

                break;
            default:
                break;

        }
    }





    private boolean validation()
    {


        if(!GlobalUtils.is_valid_email(edUsername.getText().toString().trim()))
        {

            edUsername.setError("Enter a valid email");

            return false;
        }
        else if(edPswd.getText().toString().trim().isEmpty())
        {
            edPswd.setError("Enter Password");
            return false;
        }
        else if(edPswd.getText().toString().length()<6)
        {
            edPswd.setError("minimum length should be 6 characters");
            return false;
        }
        return true;
    }




















    @Override
    protected void onActivityResult(int requestCode, int resultCode,Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);

        switch (requestCode)
        {
            case RC_SIGN_IN:
                if (resultCode == RESULT_OK) {
                    // If the error resolution was successful we should continue
                    // processing errors.
                    mSignInProgress = STATE_SIGN_IN;
                } else {
                    // If the error resolution was not successful or the user canceled,
                    // we should stop processing errors.
                    mSignInProgress = STATE_DEFAULT;
                }

                if (!mGoogleApiClient.isConnecting())
                {
                    // If Google Play services resolved the issue with a dialog then
                    // onStart is not called so we need to re-attempt connection here.
                    mGoogleApiClient.connect();
                }
                break;
        }
    }

    private void onSignedOut()
    {
        // Update the UI to reflect that the user is signed out.
        mSignInButton.setEnabled(true);

    }

    private Dialog createErrorDialog()
    {
        if (GooglePlayServicesUtil.isUserRecoverableError(mSignInError)) {
            return GooglePlayServicesUtil.getErrorDialog(
                    mSignInError,
                    this,
                    RC_SIGN_IN,
                    new DialogInterface.OnCancelListener() {
                        @Override
                        public void onCancel(DialogInterface dialog) {
                            Log.e("google", "Google Play services resolution cancelled");
                            mSignInProgress = STATE_DEFAULT;
                            Log.e("==google==","google signed out");
                        }
                    });
        } else {
            return new AlertDialog.Builder(this)
                    .setMessage("play store error")
                    .setPositiveButton("close",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Log.e("google", "Google Play services error could not be "
                                            + "resolved: " + mSignInError);
                                    mSignInProgress = STATE_DEFAULT;
                                    Log.e("google","signed out");
                                }
                            }).create();
        }
    }

    private void resolveSignInError()
    {
        if (mSignInIntent != null) {
            // We have an intent which will allow our user to sign in or
            // resolve an error.  For example if the user needs to
            // select an account to sign in with, or if they need to consent
            // to the permissions your app is requesting.

            try {
                // Send the pending intent that we stored on the most recent
                // OnConnectionFailed callback.  This will allow the user to
                // resolve the error currently preventing our connection to
                // Google Play services.
                mSignInProgress = STATE_IN_PROGRESS;
                startIntentSenderForResult(mSignInIntent.getIntentSender(),
                        RC_SIGN_IN, null, 0, 0, 0);
            } catch (IntentSender.SendIntentException e) {
                Log.e("====gPlus signIn Ex===", "Sign in intent could not be sent: "
                        + e.getLocalizedMessage());
                // The intent was canceled before it was sent.  Attempt to connect to
                // get an updated ConnectionResult.
                mSignInProgress = STATE_SIGN_IN;
                mGoogleApiClient.connect();
            }
        } else {
            // Google Play services wasn't able to provide an intent for some
            // error types, so we show the default Google Play services error
            // dialog which may still start an intent on our behalf if the
            // user can resolve the issue.
            createErrorDialog().show();
        }
    }

    private void checkServerAuthConfiguration()
    {
        // Check that the server URL is configured before allowing this box to
        // be unchecked
        if ("WEB_CLIENT_ID".equals(WEB_CLIENT_ID) ||
                "SERVER_BASE_URL".equals(SERVER_BASE_URL))
        {
            Log.w("==google==", "WEB_CLIENT_ID or SERVER_BASE_URL configured incorrectly.");
            Dialog dialog = new AlertDialog.Builder(this)
                    .setMessage("WEB_CLIENT_ID or SERVER_BASE_URL configured incorrectly.")
                    .setNeutralButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .create();

            dialog.show();
        }
    }

    private GoogleApiClient buildGoogleApiClient()
    {
        // When we build the GoogleApiClient we specify where connected and
        // connection failed callbacks should be returned, which Google APIs our
        // app uses and which OAuth 2.0 scopes our app requests.
        GoogleApiClient.Builder builder = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(Plus.API, Plus.PlusOptions.builder().build())
                .addScope(Plus.SCOPE_PLUS_LOGIN);

        return builder.build();
    }

    @Override
    public void onConnected(Bundle bundle)
    {
            // Reaching onConnected means we consider the user signed in.
            Log.e("google integration====", "onConnected");

            // Update the user interface to reflect that the user is signed in.
//            mSignInButton.setEnabled(false);

//            mSignOutButton.setEnabled(true);
//            mRevokeButton.setEnabled(true);

            // Hide the sign-in options, they no longer apply
//            findViewById(R.id.layout_server_auth).setVisibility(View.GONE);

            // Retrieve some profile information to personalize our app for the user.
            final Person currentUser = Plus.PeopleApi.getCurrentPerson(mGoogleApiClient);

            Log.e("==Success name==", currentUser.getDisplayName());

            Log.e("google plus ===", "ETT connected to " + Plus.AccountApi.getAccountName(mGoogleApiClient));
            Log.e("==Success name==", Plus.AccountApi.getAccountName(mGoogleApiClient));


        // calling login service using google plus account

        AlertDialog.Builder adb = new AlertDialog.Builder(
                Login.this);
        adb.setMessage("Account Detected '"+Plus.AccountApi.getAccountName(mGoogleApiClient)+"'"+". Do you want to login with this account?");
        adb.setTitle("Google Plus Login");
        adb.setNeutralButton("yes",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog,
                                        int which)
                    {
                        HashMap<String, String> loginData = new HashMap<>();
                        loginData.put("full_name", currentUser.getDisplayName());
                        loginData.put("email", Plus.AccountApi.getAccountName(mGoogleApiClient));
                        loginData.put("type", "G");

                        new Login_web(Login.this, loginData, GlobalConstant.URL + "login").execute();


                    }
                });
        adb.setPositiveButton("no",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog,
                                        int which)
                    {
                        if (mGoogleApiClient.isConnected())
                        {
                            Plus.AccountApi.clearDefaultAccount(mGoogleApiClient);
                            mGoogleApiClient.disconnect();

                        }
                    }
                });
        AlertDialog ad = adb.create();
        ad.show();




            Plus.PeopleApi.loadVisible(mGoogleApiClient, null)
                    .setResultCallback(this);
            // Indicate that the sign in process is complete.
            mSignInProgress = STATE_DEFAULT;

    }

    @Override
    public void onConnectionSuspended(int i)
    {
        Log.e("==gPlus conn ===","connection suspended");
        // The connection to Google Play services was lost for some reason.
        // We call connect() to attempt to re-establish the connection or get a
        // ConnectionResult that we can attempt to resolve.
        mGoogleApiClient.connect();
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
    {

    }

    @Override
    public void onConnectionFailed(ConnectionResult result)
    {
        // Refer to the javadoc for ConnectionResult to see what error codes might
        // be returned in onConnectionFailed.
        Log.e("====gPlus FAILED===", "onConnectionFailed: ConnectionResult.getErrorCode() = "
                + result.getErrorCode());

        if (result.getErrorCode() == ConnectionResult.API_UNAVAILABLE) {
            // An API requested for GoogleApiClient is not available. The device's current
            // configuration might not be supported with the requested API or a required component
            // may not be installed, such as the Android Wear application. You may need to use a
            // second GoogleApiClient to manage the application's optional APIs.
            Log.w("google", "API Unavailable.");
        } else if (mSignInProgress != STATE_IN_PROGRESS) {
            // We do not have an intent in progress so we should store the latest
            // error resolution intent for use when the sign in button is clicked.
            mSignInIntent = result.getResolution();
            mSignInError = result.getErrorCode();

            if (mSignInProgress == STATE_SIGN_IN) {
                // STATE_SIGN_IN indicates the user already clicked the sign in button
                // so we should continue processing errors until the user is signed in
                // or they click cancel.
                resolveSignInError();
            }
        }

        // In this sample we consider the user signed out whenever they do not have
        // a connection to Google Play services.
        onSignedOut();
    }

    @Override
    public void onResult(People.LoadPeopleResult peopleData)
    {
        if (peopleData.getStatus().getStatusCode() == CommonStatusCodes.SUCCESS) {

            PersonBuffer personBuffer = peopleData.getPersonBuffer();
            try {
                int count = personBuffer.getCount();
                for (int i = 0; i < count; i++) {

                }
            } finally {
                personBuffer.close();
            }


        } else {
            Log.e("====gPlus error===", "Error requesting visible circles: " + peopleData.getStatus());
        }
    }

    @Override
    public CheckResult onCheckServerAuthorization(String s, Set<Scope> set)
    {
        Log.e("google", "Checking if server is authorized.");
        Log.e("google", "Mocking server has refresh token: " + String.valueOf(mServerHasToken));

//        if (!mServerHasToken) {
        if (false) {
            // Server does not have a valid refresh token, so request a new
            // auth code which can be exchanged for one.  This will cause the user to see the
            // consent dialog and be prompted to grant offline access. This callback occurs on a
            // background thread so it is OK to do synchronous network access.

            // Ask the server which scopes it would like to have for offline access.  This
            // can be distinct from the scopes granted to the client.  By getting these values
            // from the server, you can change your server's permissions without needing to
            // recompile the client application.
            HttpClient httpClient = new DefaultHttpClient();
            HttpGet httpGet = new HttpGet(SELECT_SCOPES_URL);
            HashSet<Scope> serverScopeSet = new HashSet<Scope>();

            try {
                HttpResponse httpResponse = httpClient.execute(httpGet);
                int responseCode = httpResponse.getStatusLine().getStatusCode();
                String responseBody = EntityUtils.toString(httpResponse.getEntity());

                if (responseCode == 200) {
                    String[] scopeStrings = responseBody.split(" ");
                    for (String scope : scopeStrings) {
                        Log.e("google", "Server Scope: " + scope);
                        serverScopeSet.add(new Scope(scope));
                    }
                } else {
                    Log.e("google", "Error in getting server scopes: " + responseCode);
                }

            } catch (ClientProtocolException e) {
                Log.e("google", "Error in getting server scopes.", e);
            } catch (IOException e) {
                Log.e("gpogle", "Error in getting server scopes.", e);
            }

            // This tells GoogleApiClient that the server needs a new serverAuthCode with
            // access to the scopes in serverScopeSet.  Note that we are not asking the server
            // if it already has such a token because this is a sample application.  In reality,
            // you should only do this on the first user sign-in or if the server loses or deletes
            // the refresh token.
            return CheckResult.newAuthRequiredResult(serverScopeSet);
        } else {
            // Server already has a valid refresh token with the correct scopes, no need to
            // ask the user for offline access again.
            return CheckResult.newAuthNotRequiredResult();
        }
    }

    @Override
    public boolean onUploadServerAuthCode(String s, String serverAuthCode)
    {
        // Upload the serverAuthCode to the server, which will attempt to exchange it for
        // a refresh token.  This callback occurs on a background thread, so it is OK
        // to perform synchronous network access.  Returning 'false' will fail the
        // GoogleApiClient.connect() call so if you would like the client to ignore
        // server failures, always return true.
        HttpClient httpClient = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(EXCHANGE_TOKEN_URL);

        try {
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
            nameValuePairs.add(new BasicNameValuePair("serverAuthCode", serverAuthCode));
            httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

            HttpResponse response = httpClient.execute(httpPost);
            int statusCode = response.getStatusLine().getStatusCode();
            final String responseBody = EntityUtils.toString(response.getEntity());
            Log.e("google", "Code: " + statusCode);
            Log.e("google", "Resp: " + responseBody);

            // Show Toast on UI Thread
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(Login.this, responseBody, Toast.LENGTH_LONG).show();
                }
            });
            return (statusCode == 200);
        } catch (ClientProtocolException e) {
            Log.e("google", "Error in auth code exchange.", e);
            return false;
        } catch (IOException e) {
            Log.e("google", "Error in auth code exchange.", e);
            return false;
        }
    }



}
