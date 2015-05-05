package com.pld.h4414.sportify;

import android.graphics.Bitmap;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpResponseHandler;

import com.loopj.android.http.RequestParams;
import com.loopj.android.http.ResponseHandlerInterface;
import com.loopj.android.http.SyncHttpClient;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.Semaphore;

/**
 * Created by KEV on 04/05/15.
 */
public class SportifyRestClient {


    private static final String BASE_URL = "http://10.0.2.2:8080";

    // Make RESTful webservice call using AsyncHttpClient object
    private static SyncHttpClient client = new SyncHttpClient();

    private static String getAbsoluteUrl(String relativeUrl) {
        return BASE_URL + relativeUrl;
    }
    private static void get(String url, RequestParams params, ResponseHandlerInterface responseHandler) {
        client.get(getAbsoluteUrl(url), params ,responseHandler);
    }

    private static void post(String url, RequestParams params, ResponseHandlerInterface responseHandler) {
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
            params.put("lastname", familyName);

            // Put Http parameter firstname

            params.put("firstname", firstName);

            // Invoke RESTful Web Service with Http parameters

            postWebServiceInvocation(params,"/user");
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


       JSONArray results =  getWebServiceInvocation(new RequestParams(),"/fetch/sports");
        System.out.println("arrivée "+results.length());


        try {

            for (int i = 0; i < results.length(); i++) {
               sport_list.add(results.getJSONObject(i).getString("name"));
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

        JsonHttpResponseHandler handler =  new JsonHttpResponseHandler();
        handler.setUseSynchronousMode(true);
        this.get(suffixe, params, handler);

        handler.s.acquireUninterruptibly();

        return handler.getResult();

    }


    public class JsonHttpResponseHandler extends com.loopj.android.http.JsonHttpResponseHandler {
        private JSONArray result ;
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