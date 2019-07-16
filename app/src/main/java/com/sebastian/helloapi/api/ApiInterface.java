package com.sebastian.helloapi.api;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;
import com.sebastian.helloapi.model.Films;

import org.json.JSONObject;

/**
 * Created by terranology on 15/7/19.
 */

public class ApiInterface {

    private static ApiInterface mInstance = null;
    private static ApiListener mApiListener;
    private Context mContext;

    public static ApiInterface getInstance() {
        if (mInstance == null)
            mInstance = new ApiInterface();
        return mInstance;
    }

    public void setApiListener(ApiListener listener, Context context) {
        mApiListener = listener;
        mContext = context;
    }

    public void getPaises() {
        int tag = 1;
        String endpoint = "https://swapi.co/api/films/";
        callApiGet(endpoint, mApiListener, tag, false);
    }


    // **************   API CALLS   **************** //


    private void callApiGet(String url, final ApiListener listener, final int tag, final boolean withAuth) {

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject responseJson) {

                        listener.onResponse(tag, responseJson.toString());

                        //  Creo un Objeto de la clase Films
                        Gson gson = new Gson();
                        Films films = gson.fromJson(responseJson.toString(), Films.class);

                        Log.i("OK", films.toString());


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("KO", "Error" + error.getMessage());
                listener.onErrorResponse(tag, error.getMessage());
            }
        });

        // HACE LA PETICIÓN
        ApiController.getInstance().addToRequestQueue(jsonObjReq, tag);

    }

    public interface ApiListener {
        void onResponse(int tag, String response);

        void onErrorResponse(Object tag, String error);
    }
}
