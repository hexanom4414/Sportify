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

    private static SportifyEvent event ;



    /*

    useful for wabservice

    */

    private static final String BASE_URL = "http://91.229.95.108:80";


    // Make RESTful webservice call using AsyncHttpClient object
    private static AsyncHttpClient client = new AsyncHttpClient();

    private static String getAbsoluteUrl(String relativeUrl) {
        return BASE_URL + relativeUrl;
    }
    private static void get(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.get(getAbsoluteUrl(url), params ,responseHandler);
    }

    private static void post(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.post(getAbsoluteUrl(url), params, responseHandler);
    }





    /*

    useful for wabservice

    */


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modal_create);

        event = new SportifyEvent();

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



        this.get(suffixe, params, new JsonHttpResponseHandler(){


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
                    // Else display error message
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
        });



    }

    private void getLocationWebServiceInvocation(RequestParams params, String suffixe) {
        // Show Progress Dialog


        this.get(suffixe, params, new JsonHttpResponseHandler() {


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
                    // Else display error message
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
        });
    }


    private void postEventWebServiceInvocation(RequestParams params, String suffixe){


        this.post(suffixe, params, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                // If the response is JSONObject instead of expected JSONArray

                try {
                    // JSON Object
                    // When the JSON response has status boolean value assigned with true
                    if (response.getString("success") == "true") {

                    Toast.makeText(getApplicationContext(),"Evènement créé avec succès",Toast.LENGTH_LONG).show();

                    }
                    // Else display error message
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
                System.out.println("Erreur lors de la création de l'évènement");

            }
        });


    }

    private void postUserWebServiceInvocation(RequestParams params, String suffixe){


        this.post(suffixe, params, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                // If the response is JSONObject instead of expected JSONArray

                try {
                    // JSON Object
                    // When the JSON response has status boolean value assigned with true
                    if (response.getString("success") == "true") {

                        Toast.makeText(getApplicationContext(),"Utilisateur créé avec succès",Toast.LENGTH_LONG).show();

                    }
                    // Else display error message
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
                System.out.println("Erreur lors de la création de l'utilisateur");

            }
        });


    }

    //listener for spinner

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {



        ((TextView) parent.getChildAt(0)).setTextColor(Color.BLUE);

        if (parent.getId() == R.id.sportPicker){

            Toast.makeText(getApplicationContext(),"coucou",Toast.LENGTH_LONG).show();


            event.setSport((String)parent.getItemAtPosition(position));

       }else if(parent.getId() == R.id.locationPicker){


            Toast.makeText(getApplicationContext(),"je suis là",Toast.LENGTH_LONG).show();

            event.setLocation((String)parent.getItemAtPosition(position));

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
        event.setTime(hourOfDay, minute);

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

            event.setDate(new Date(year,month,day));


        }
    }



    // Listener for the validate button

    public void createEvent(View v){

        // Here we call the createEvent Method

        //SportifyRestClient client = new SportifyRestClient();
        RequestParams params = new RequestParams();

       // params.put("",);
       // params.put("",);
       // params.put("",);

        this.postEventWebServiceInvocation(params, "/fetch/sport");
        Toast.makeText(getApplicationContext(), event+"",Toast.LENGTH_LONG).show();

    }

    public class SportifyEvent{

        private String sport;
        private Date date;
        private Date time;
        private String location;
        private String userEmail;


        SportifyEvent(){

        }


        SportifyEvent(String s, Date d, String l,String u ){
            sport = s;
            date = d;
            location = l;
            userEmail = u;

        }

        @Override
        public String toString() {
            return "Username: "+ userEmail+" Sport : " +sport+ " le : "+date.toString()+" à "+ time.getHours()+":"+time.getMinutes()+" à "+location;
        }

        public String getSport() {
            return sport;
        }

        public void setSport(String sport) {
            this.sport = sport;
        }

        public Date getDate() {
            return date;
        }

        public void setDate(Date date) {
            this.date = date;
        }

        public String getLocation() {
            return location;
        }

        public void setLocation(String location) {
            this.location = location;
        }

        public String getUserEmail() {
            return userEmail;
        }

        public void setUserEmail(String userEmail) {
            this.userEmail = userEmail;
        }

        public Date getTime() {
            return time;
        }

        public void setTime(int hours, int minutes) {
            this.time = new Date(0,0,0,hours,minutes);
        }
    }




}
