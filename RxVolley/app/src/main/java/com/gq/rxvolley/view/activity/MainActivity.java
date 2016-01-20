package com.gq.rxvolley.view.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.gq.rxvolley.R;
import com.kymjs.rxvolley.RxVolley;
import com.kymjs.rxvolley.client.HttpParams;
import com.kymjs.rxvolley.rx.Result;

import org.json.JSONException;
import org.json.JSONObject;

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    private Observable<Result> observable;
    private Subscription subscription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        HttpParams params = new HttpParams();

//同之前的设计，传递 http 请求头可以使用 putHeaders()
        params.putHeaders("cookie", "your cookie");
        params.putHeaders("User-Agent", "rxvolley");

//传递 http 请求参数可以使用 put()
        params.put("name", "kymjs");
        params.put("age", "18");

      /*  HttpCallback callback = new HttpCallback() {
            @Override
            public void onSuccessInAsync(byte[] t) {
            }

            @Override
            public void onSuccess(String t) {
                Toast.makeText(MainActivity.this,t,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(int errorNo, String strMsg) {
            }
        };

        new RxVolley.Builder()
                .url("http://www.kymjs.com/rss.xml") //接口地址
                //请求类型，如果不加，默认为 GET 可选项：
                //POST/PUT/DELETE/HEAD/OPTIONS/TRACE/PATCH
                .httpMethod(RxVolley.Method.GET)
                //设置缓存时间: 默认是 get 请求 5 分钟, post 请求不缓存
                .cacheTime(6)
                //内容参数传递形式，如果不加，默认为 FORM 表单提交，可选项 JSON 内容
                .contentType(RxVolley.ContentType.FORM)
                .params(params) //上文创建的HttpParams请求参数集
                //是否缓存，默认是 get 请求 5 缓存分钟, post 请求不缓存
                .shouldCache(true)
                .callback(callback) //响应回调
                .encoding("UTF-8") //编码格式，默认为utf-8
                .doTask();  //执行请求操作*/
        observable = new RxVolley.Builder().cacheTime(6)
                .url("http://kymjs.com/feed.xml")
                .contentType(RxVolley.ContentType.FORM)
                .getResult();
        Observable<Result> observable1 = new RxVolley.Builder().url(
                "http://115.29.144.109:8080/pom/app/app-server!findReportInfo.action?start=0&size=20&userId=149&orderby=publishtime&order=desc"
        ).contentType(RxVolley.ContentType.JSON).getResult();
        subscription = observable1.map(new Func1<Result, JSONObject>() {
            @Override
            public JSONObject call(Result result) {
                try {
                    return new JSONObject(new String(result.data));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return null;
            }
        }).filter(new Func1<JSONObject, Boolean>() {
            @Override
            public Boolean call(JSONObject jsonObject) {
                return jsonObject!=null;
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<JSONObject>() {
                    @Override
                    public void call(JSONObject s) {
                        Toast.makeText(MainActivity.this, s.toString(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (!subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
    }
}
