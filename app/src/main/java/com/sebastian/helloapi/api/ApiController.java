package com.sebastian.helloapi.api;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by terranology on 25/6/18.
 */

public class ApiController {
    private RequestQueue mRequestQueue;
    private static ApiController mInstance;

    public static synchronized ApiController getInstance() {
        if (mInstance == null)
            mInstance = new ApiController();
        return mInstance;
    }

    public void initRequestQueue(Context context){
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(context);
        }
    }

    public RequestQueue getRequestQueue() {
        return mRequestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req, int tag) {
        Log.e("APICONTROLLER","addToRequestQueue" + tag);
        req.setTag(tag);
        getRequestQueue().add(req);

    }

    public void cancelPendingRequests(Object tag) {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(tag);
        }
    }

}
