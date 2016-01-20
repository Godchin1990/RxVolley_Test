package com.gq.rxvolley;

import android.app.Application;

import com.kymjs.rxvolley.client.HttpParams;

/**
 * @Author ：gaoqun on 2016/1/20 10:13
 * @Params :
 * @Describtion :
 * @return :
 */
public class App extends Application{

    private void setHttpParams(){
        HttpParams httpParams = new HttpParams();
        httpParams.putHeaders("","");
    }
}