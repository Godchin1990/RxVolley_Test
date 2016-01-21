package com.gq.rxvolley;

import android.app.Application;
import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.kymjs.rxvolley.client.HttpParams;

/**
 * @Author ：gaoqun on 2016/1/20 10:13
 * @Params :
 * @Describtion :
 * @return :
 */
public class App extends Application{

    private static RequestQueue requestQueue;
    private static Context context;

    public synchronized static RequestQueue getRequestQueue(Context context){
        if (requestQueue==null){
            requestQueue  = Volley.newRequestQueue(context,50);
        }
        return requestQueue;
    }
}