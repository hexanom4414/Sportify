package com.pld.h4414.sportify;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
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
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by KEV on 04/05/15.
 */



public class SportifyRestClient {
    public static JSONArray result  = new JSONArray();
    private static final String BASE_URL = "http://91.229.95.108:80";
    //static public final Lock _mutex = new ReentrantLock(true);
    CountDownLatch latch = new CountDownLatch(1);

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


    public boolean registerUser(String email, String familyName, String firstName){

        boolean result = false;

        //If user doesn't exists create it
        RequestParams params = new RequestParams();


        if (familyName != null && firstName != null && email !=null) {


            // Put Http parameter username
            // params.put("username", email);
            // Put Http parameter lastname
            //params.put("lastname", familyName);

            // Put Http parameter firstname

            //params.put("firstname", firstName);

            // Invoke RESTful Web Service with Http parameters

            //postWebServiceInvocation(params,"/user");



        }





        return result;

    }

    public String getUserName(String email){


        //If user doesn't exists create it
        RequestParams params = new RequestParams();

        // Put Http parameter username
         params.put("username", email);


        // Invoke RESTful Web Service with Http parameters

        getWebServiceInvocation(params, "/user");

        return null;

    }

    public Bitmap getUserPic(String email){

        return null;
    }


    public ArrayList<String> listSports(){
       ArrayList <String> sport_list = new  ArrayList<String>() ;



        do{
            getWebServiceInvocation(new RequestParams(), "/fetch/sport");

        }while (result.length()==0);



        System.out.println("arrivée "+result.length());


        try {

            for (int i = 0; i < result.length(); i++) {
               sport_list.add(result.getJSONObject(i).getString("name"));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return sport_list;
    }


    public ArrayList<String> listLocations(){
        ArrayList <String> location_list = new  ArrayList<String>();

        JSONArray results =  getWebServiceInvocation(null,"/fetch/fields");

        return location_list;
    }

    public ArrayList<String> listLocations(String sport){
        ArrayList <String> location_list  = new  ArrayList<String>();

        JSONArray results  =  getWebServiceInvocation(null,"/fetch/fields");

        return location_list;
    }

    /**
     * Method that performs RESTful webservice invocations
     *
     * @param params
     * @param suffixe
     *
     */
    private JSONArray getWebServiceInvocation(RequestParams params, String suffixe){
        // Show Progress Dialog



        this.get(suffixe, params, new JsonHttpResponseHandler(){


            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                // If the response is JSONObject instead of expected JSONArray

                try {
                    // JSON Object
                    // When the JSON response has status boolean value assigned with true
                    if(response.getString("success") == "true"){
                        result = response.getJSONArray("data");
                        System.out.println("départ " + result.length());


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


        return result;

    }



    /*
    public class JsonHttpResponseHandler extends com.loopj.android.http.JsonHttpResponseHandler {
        private JSONArray result = new JSONArray() ;
        private boolean finExec = false;
        public Semaphore s = new Semaphore(1);

        @Override
        public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
            // If the response is JSONObject instead of expected JSONArray
            result = new JSONArray();

            try {
                // JSON Object
                // When the JSON response has status boolean value assigned with true
                if(response.getString("success")== "true"){
                     setResult(response.getJSONArray("data"));
                    System.out.println("départ " + response.getJSONArray("data").length());


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

            s.release();
        }

        public JSONArray getResult(){
            return result;
        }

        private void setResult(JSONArray object){

        result = object;

        }





    }
    */
    /**
     * Method that performs RESTful webservice invocations
     *
     * @param params
     * @param suffixe
     *
     */
    private void postWebServiceInvocation(RequestParams params, String suffixe){


        this.post(suffixe, params, new JsonHttpResponseHandler() );


    }
}