package com.pld.h4414.sportify;

import android.graphics.Bitmap;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestHandle;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by KEV on 04/05/15.
 */
public class SportifyRestClient {


    private static final String BASE_URL = "http://localhost:8080";

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
       ArrayList <String> sport_list =new  ArrayList<String>() ;


       JSONObject results =  getWebServiceInvocation(null,"/sports");

        try {


            results.getString("nom");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return sport_list;
    }


    public ArrayList<String> listLocations(){
        ArrayList <String> location_list = new  ArrayList<String>();

        JSONObject results =  getWebServiceInvocation(null,"/");

        return location_list;
    }

    public ArrayList<String> listLocations(String sport){
        ArrayList <String> location_list  = new  ArrayList<String>();

        JSONObject results  =  getWebServiceInvocation(null,"/");

        return location_list;
    }

    /**
     * Method that performs RESTful webservice invocations
     *
     * @param params
     * @param suffixe
     *
     */
    private JSONObject getWebServiceInvocation(RequestParams params, String suffixe){
        // Show Progress Dialog

        JsonHttpResponseHandler handler =  new JsonHttpResponseHandler();
        this.get(suffixe, params, handler);
        return handler.getResult();

    }


    public class JsonHttpResponseHandler extends com.loopj.android.http.JsonHttpResponseHandler {
        private JSONObject result;

        @Override
        public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
            // If the response is JSONObject instead of expected JSONArray

            try {
                // JSON Object
                // When the JSON response has status boolean value assigned with true
                if(response.getString("result")== "success"){
                     setResult(response.getJSONObject("json"));


                }
                // Else display error message
                else{
                    //gestion erreur

                    response.getString("error");
                }
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                // message = "Error Occured [Server's JSON response might be invalid]!";
                e.printStackTrace();

            }


        }
        public JSONObject getResult(){
            return result;
        }

        private void setResult(JSONObject object){

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