package com.pld.h4414.sportify;

import android.graphics.Bitmap;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

/**
 * Created by KEV on 04/05/15.
 */
public class SportifyRestClient {
    private static final String BASE_URL = "http://localhost:8080/events/sport";

    // Make RESTful webservice call using AsyncHttpClient object
    private static AsyncHttpClient client = new AsyncHttpClient();

    private static String getAbsoluteUrl(String relativeUrl) {
        return BASE_URL + relativeUrl;
    }
    public static void get(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.get(getAbsoluteUrl(url), params ,responseHandler);
    }


    public void registerUser(String email, String name){

        //Call webservice to known if user exist

        //If user doesn't exists create it

    }

    public String getUserName(String email){
       return null;

    }

    public Bitmap getUserPic(String email){

        return null;
    }


}