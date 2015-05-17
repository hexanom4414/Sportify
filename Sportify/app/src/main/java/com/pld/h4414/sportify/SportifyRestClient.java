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
//    private static final String BASE_URL = "http://91.229.95.108:80";
    //static public final Lock _mutex = new ReentrantLock(true);
    CountDownLatch latch = new CountDownLatch(1);

    // Make RESTful webservice call using AsyncHttpClient object
    private static AsyncHttpClient client = new AsyncHttpClient();

    private static String getAbsoluteUrl(String relativeUrl) {
        return BASE_URL + relativeUrl;
    }
    public static void get(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.get(getAbsoluteUrl(url), params ,responseHandler);
    }

    private static void post(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.post(getAbsoluteUrl(url), params, responseHandler);
    }



    /**
     * Method that performs RESTful webservice invocations
     *
     * @param params
     * @param suffixe
     *
     */
    public void postWebServiceInvocation(RequestParams params, String suffixe, JsonHttpResponseHandler handler){


        this.post(suffixe, params, handler );


    }


    /**
     * Method that performs RESTful webservice invocations
     *
     * @param params
     * @param suffixe
     *
     */
    public void getWebServiceInvocation(RequestParams params, String suffixe, JsonHttpResponseHandler handler){


        this.get(suffixe, params, handler);


    }


}