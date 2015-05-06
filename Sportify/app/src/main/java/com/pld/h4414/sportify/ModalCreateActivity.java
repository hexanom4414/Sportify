package com.pld.h4414.sportify;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.DialogFragment;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


public class ModalCreateActivity extends FragmentActivity implements AdapterView.OnItemSelectedListener {

   // private static SportifyEvent event ;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modal_create);

     //   event = new SportifyEvent();

        // Call webservices to populate the spinners

        this.getSportsWebServiceInvocation(new RequestParams(), "/fetch/sport");
        this.getLocationWebServiceInvocation(new RequestParams(), "/fetch/installation_sportive");

    }




    /**
     * Method that performs RESTful webservice invocations
     *
     * @param params
     * @param suffixe
     *
     */
    private void getSportsWebServiceInvocation(RequestParams params, String suffixe){
        // Show Progress Dialog

        SportifyRestClient client = new SportifyRestClient();
        JsonHttpResponseHandlerGetSports handler = new JsonHttpResponseHandlerGetSports();
        client.getWebServiceInvocation( params,suffixe, handler);



    }

    private void getLocationWebServiceInvocation(RequestParams params, String suffixe) {
        // Show Progress Dialog

        SportifyRestClient client = new SportifyRestClient();
        JsonHttpResponseHandlerGetLocation handler = new JsonHttpResponseHandlerGetLocation();
        client.getWebServiceInvocation( params,suffixe ,handler );
    }


    private void postEventWebServiceInvocation(RequestParams params, String suffixe){



        SportifyRestClient client = new SportifyRestClient();
        JsonHttpResponseHandlerPost handler = new JsonHttpResponseHandlerPost();
        handler.setMessage_erreur("Erreur lors de la création de l'évènement");
        handler.setMessage_succes("Evènement créé");
        client.postWebServiceInvocation( params,suffixe , handler);


    }

    private void postUserWebServiceInvocation(RequestParams params, String suffixe){

        SportifyRestClient client = new SportifyRestClient();

        JsonHttpResponseHandlerPost handler = new JsonHttpResponseHandlerPost();
        handler.setMessage_erreur("");
        handler.setMessage_succes("");
        client.postWebServiceInvocation( params, suffixe,handler);


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

                            sport_list.add(array.getJSONObject(i).getString("nom").toString());
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    ArrayAdapter<String> adapter_sport = new ArrayAdapter<String>(getApplicationContext(),R.layout.my_spinner_style,sport_list);


                    Spinner spinner_sport = (Spinner) findViewById(R.id.sportPicker);


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


    public class JsonHttpResponseHandlerGetLocation extends com.loopj.android.http.JsonHttpResponseHandler {


        @Override
        public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
            // If the response is JSONObject instead of expected JSONArray

            try {
                // JSON Object
                // When the JSON response has status boolean value assigned with true
                if (response.getString("success") == "true") {
                    JSONArray array = response.getJSONArray("data");
                    ArrayList<String> location_list = new ArrayList<String>();


                    try {


                        for (int i = 0; i < array.length(); i++) {

                            location_list.add(array.getJSONObject(i).getString("nom").toString());
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(),R.layout.my_spinner_style, location_list);


                    Spinner spinner = (Spinner) findViewById(R.id.locationPicker);


                    spinner.setAdapter(adapter);

                    System.out.println(location_list.toString());
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
            System.out.println("failure");

        }

        @Override
        public void onFinish() {


        }

        @Override
        public void onStart() {

        }
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



    //listener for spinner

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {



        ((TextView) parent.getChildAt(0)).setTextColor(Color.BLUE);

        if (parent.getId() == R.id.sportPicker){

            Toast.makeText(getApplicationContext(),"coucou",Toast.LENGTH_LONG).show();


           // event.setSport((String)parent.getItemAtPosition(position));

       }else if(parent.getId() == R.id.locationPicker){


            Toast.makeText(getApplicationContext(),"je suis là",Toast.LENGTH_LONG).show();

           // event.setLocation((String)parent.getItemAtPosition(position));

       }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }



    // onClickListener for the buttons


    public void showTimePickerDialog(View v) {
        DialogFragment newFragment = new TimePickerFragment();
        newFragment.show(getSupportFragmentManager(), "timePicker");
    }

    public void showDatePickerDialog(View v) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }





    // the dialog window that appears when clicking on the date and time buttons


    public static class TimePickerFragment extends DialogFragment
            implements TimePickerDialog.OnTimeSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current time as the default values for the picker
            final Calendar c = Calendar.getInstance();
            int hour = c.get(Calendar.HOUR_OF_DAY);
            int minute = c.get(Calendar.MINUTE);

            // Create a new instance of TimePickerDialog and return it
            return new TimePickerDialog(getActivity(), this, hour, minute,
                    DateFormat.is24HourFormat(getActivity()));
        }

        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            // Do something with the time chosen by the user
       // event.setTime(hourOfDay, minute);

        }
    }

    public static class DatePickerFragment extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            // Create a new instance of DatePickerDialog and return it
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            // Do something with the date chosen by the user

          //  event.setDate(new Date(year,month,day));


        }
    }



    // Listener for the validate button

    public void createEvent(View v){

        // Here we call the createEvent Method

        RequestParams params = new RequestParams();

       // params.put("",);
       // params.put("",);
       // params.put("",);

        this.postEventWebServiceInvocation(params, "/fetch/sport");
      //  Toast.makeText(getApplicationContext(), event+"",Toast.LENGTH_LONG).show();

    }






}
