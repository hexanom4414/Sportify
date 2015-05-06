    package com.pld.h4414.sportify;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.plus.Plus;
import com.google.android.gms.plus.model.people.Person;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;


/*
Tuto :
    1) https://developers.google.com/identity/sign-in/android/getting-started
    2) https://developers.google.com/identity/sign-in/android/sign-in
*/

public class ConnectionActivity extends Activity implements
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        View.OnClickListener {

    /* Request code used to invoke sign in user interactions. */
    private static final int RC_SIGN_IN = 0;

    /* Client used to interact with Google APIs. */
    private GoogleApiClient mGoogleApiClient;

    /**
     * True if the sign-in button was clicked.  When true, we know to resolve all
     * issues preventing sign-in without waiting.
     */
    private boolean mSignInClicked;

    /**
     * True if we are in the process of resolving a ConnectionResult
     */
    private boolean mIntentInProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.connection_activity);

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(Plus.API)
                .addScope(Plus.SCOPE_PLUS_PROFILE)
                .build();

        findViewById(R.id.sign_in_button).setOnClickListener(this);
        findViewById(R.id.sign_out_button).setOnClickListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mGoogleApiClient.disconnect();
    }

    @Override
    public void onConnectionFailed(ConnectionResult result) {
        if (!mIntentInProgress) {
            if (mSignInClicked && result.hasResolution()) {
                // The user has already clicked 'sign-in' so we attempt to resolve all
                // errors until the user is signed in, or they cancel.
                try {
                    result.startResolutionForResult(this, RC_SIGN_IN);
                    mIntentInProgress = true;
                } catch (IntentSender.SendIntentException e) {
                    // The intent was canceled before it was sent.  Return to the default
                    // state and attempt to connect to get an updated ConnectionResult.
                    mIntentInProgress = false;
                    mGoogleApiClient.connect();
                }
            }
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int responseCode, Intent intent) {
        if (requestCode == RC_SIGN_IN) {
            mIntentInProgress = false;

            if (!mGoogleApiClient.isConnected() && responseCode == RESULT_OK) {
                mGoogleApiClient.reconnect();
            }
        }
    }

    @Override
    public void onConnected(Bundle connectionHint) {
        mSignInClicked = false;
        Toast.makeText(this, "User is connected!", Toast.LENGTH_LONG).show();

        // create user on the server

        RequestParams params = new RequestParams();
        Person currentPerson = Plus.PeopleApi.getCurrentPerson(mGoogleApiClient);

        params.put("nom", currentPerson.getName().getFamilyName());
        params.put("prenom", currentPerson.getName().getGivenName());
        params.put("email", Plus.AccountApi.getAccountName(mGoogleApiClient));

        this.postUserWebServiceInvocation(params,"/utilisateur");

        if (Plus.PeopleApi.getCurrentPerson(mGoogleApiClient) == null) {
            System.err.println("Null person");
        }
    }

    @Override
    public void onConnectionSuspended(int cause) {
        mGoogleApiClient.connect();
    }


   /* @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }*/

    public void onClick(View view) {
        if (view.getId() == R.id.sign_in_button && !mGoogleApiClient.isConnecting()) {
            mSignInClicked = true;
            mGoogleApiClient.connect();
        }
        else if (view.getId() == R.id.sign_out_button) {
            if (mGoogleApiClient.isConnected()) {
                mGoogleApiClient.clearDefaultAccountAndReconnect();
                Toast.makeText(this, "Sign out", Toast.LENGTH_SHORT).show();
            }
        }
    }
    /** Called when the user clicks the Ignore button */
    public void nextAction(View view) {

        //transition to another activity here
        if(mGoogleApiClient.isConnected()) {
            Person currentPerson = Plus.PeopleApi.getCurrentPerson(mGoogleApiClient);

            Intent intent = new Intent(this, MainActivity.class);

            if (currentPerson != null) {
                intent.putExtra("validPerson", true);
                intent.putExtra("familyName", currentPerson.getName().getFamilyName());
                intent.putExtra("givenName", currentPerson.getName().getGivenName());
                intent.putExtra("email", Plus.AccountApi.getAccountName(mGoogleApiClient));
                intent.putExtra("urlImage", currentPerson.getImage().getUrl());


            } else {
                intent.putExtra("validPerson", false);
            }

            startActivity(intent);
        }
        else
        {
            Toast.makeText(this, "Please connect", Toast.LENGTH_LONG).show();
        }

    }

    private void postUserWebServiceInvocation(RequestParams params, String suffixe){

        SportifyRestClient client = new SportifyRestClient();

        JsonHttpResponseHandlerPost handler = new JsonHttpResponseHandlerPost();
        handler.setMessage_erreur("Erreur lors de la création de l'utilisateur");
        handler.setMessage_succes("Utilisateur créé");
        client.postWebServiceInvocation( params, suffixe,handler);


    }

    public class JsonHttpResponseHandlerPost extends com.loopj.android.http.JsonHttpResponseHandler{
        private String message_succes;
        private String message_erreur;


        @Override
        public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
            // If the response is JSONObject instead of expected JSONArray

            try {
                // JSON Object
                // When the JSON response has status boolean value assigned with true
                if (response.getString("success") == "true") {

                    Toast.makeText(getApplicationContext(), message_succes,Toast.LENGTH_LONG).show();

                }
                // Else display error message_succes
                else {
                    //gestion erreur

                    System.out.println("erreur");
                }
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();

            }


        }

        @Override
        public void onFailure(Throwable e, JSONObject errorResponse) {
            super.onFailure(e, errorResponse);
            Toast.makeText(getApplicationContext(), message_erreur,Toast.LENGTH_LONG).show();


        }

        public String getMessage_succes() {
            return message_succes;
        }

        public void setMessage_succes(String message_succes) {
            this.message_succes = message_succes;
        }

        public String getMessage_erreur() {
            return message_erreur;
        }

        public void setMessage_erreur(String message_erreur) {
            this.message_erreur = message_erreur;
        }
    }
}
