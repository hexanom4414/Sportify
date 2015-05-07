package com.pld.h4414.sportify;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class ModalFilterActivity extends Activity implements AdapterView.OnItemSelectedListener {


    private String sport;
    private static boolean isReallySelectedSport = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modal_filter);

        Spinner sport_spinner = (Spinner) findViewById(R.id.spinnerFilter);

        sport_spinner.setOnItemSelectedListener(this);

        this.getSportsWebServiceInvocation(new RequestParams(), "/fetch/sport");

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


        if (isReallySelectedSport){




           sport =  (String)parent.getItemAtPosition(position);

            Intent intent = new Intent( getApplicationContext(), MainActivity.class);
            intent.putExtra("sport",sport);
            startActivity(intent);


        }
        isReallySelectedSport = true;


    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private void getSportsWebServiceInvocation(RequestParams params, String suffixe){
        // Show Progress Dialog

        SportifyRestClient client = new SportifyRestClient();
        JsonHttpResponseHandlerGetSports handler = new JsonHttpResponseHandlerGetSports();
        client.getWebServiceInvocation( params,suffixe, handler);



    }


    public class JsonHttpResponseHandlerGetSports extends com.loopj.android.http.JsonHttpResponseHandler{


        @Override
        public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
            // If the response is JSONObject instead of expected JSONArray

            try {
                // JSON Object
                // When the JSON response has status boolean value assigned with true
                if(response.getString("success") == "true"){
                    JSONArray array =  response.getJSONArray("data");
                    ArrayList<String> sport_list = new  ArrayList<String>() ;


                    try {



                        for (int i = 0; i < array.length(); i++) {

                            sport_list.add(array.getJSONObject(i).getString("nom"));


                        }


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    ArrayAdapter<String> adapter_sport = new ArrayAdapter<String>(getApplicationContext(),R.layout.my_spinner_style,sport_list);


                    Spinner spinner_sport = (Spinner) findViewById(R.id.spinnerFilter);


                    spinner_sport.setAdapter(adapter_sport);

                    System.out.println(sport_list.toString());
                }
                // Else display error message_succes
                else{
                    //gestion erreur

                    System.out.println("erreur");
                }
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                System.out.println( "Error Occurred [Server's JSON response might be invalid]!");
                e.printStackTrace();

            }


        }

        @Override
        public void onFailure(Throwable e, JSONObject errorResponse) {
            super.onFailure(e, errorResponse);
            System.out.println("failure");

        }

        @Override
        public void onFinish() {


        }

        @Override
        public void onStart() {

        }
    }
}
