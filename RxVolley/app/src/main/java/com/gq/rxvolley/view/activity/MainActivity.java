package com.gq.rxvolley.view.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.bumptech.glide.Glide;
import com.gq.rxvolley.App;
import com.gq.rxvolley.R;
import com.gq.rxvolley.api.JsonObjectPostRequest;
import com.kymjs.rxvolley.client.HttpParams;
import com.kymjs.rxvolley.rx.Result;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import pl.droidsonroids.gif.GifImageView;
import rx.Observable;
import rx.Subscription;

public class MainActivity extends AppCompatActivity {

    private Observable<Result> observable;
    private Subscription subscription;
    private ListView listView;

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
        List<String> list = new ArrayList<>();
        for (int i=0;i<10;i++){
            list.add("http://file6.mafengwo.net/M00/40/87/wKgBt090GMDRpI0TAAca-eb8FtY667.weng.w460.gif");
            list.add("http://pic.962.net/up/2012-11/2012112609575882766.gif");
            list.add("http://img4.imgtn.bdimg.com/it/u=2500344646,3349691812&fm=21&gp=0.jpg");
            list.add("http://img0.imgtn.bdimg.com/it/u=2747206147,3437759991&fm=21&gp=0.jpg");
            list.add("http://img3.imgtn.bdimg.com/it/u=1827109971,3367997446&fm=21&gp=0.jpg");
            list.add("http://img2.imgtn.bdimg.com/it/u=443220657,3542202131&fm=21&gp=0.jpg");
            list.add("http://img3.imgtn.bdimg.com/it/u=1835959688,949753021&fm=21&gp=0.jpg");
            list.add("http://img4.goumin.com/attachments/photo/0/0/7/1852/474215.gif");
        }
        listView = (ListView) findViewById(R.id.listview);
MyAdapter myAdapter = new MyAdapter(this,list);
        listView.setAdapter(myAdapter);
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
        /*observable = new RxVolley.Builder().cacheTime(6)
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
                });*/

        setData();
    }


    private void setData(){
        Map<String,String> map = new HashMap<>();
        map.put("start","0");
        map.put("size","20");
        map.put("userId","149");
        map.put("orderby","publishtime");
        map.put("order","desc");
        JsonObjectPostRequest jsonObjectPostRequest = new JsonObjectPostRequest("http://115.29.144.109:8080/pom/app/app-server!findReportInfo.action",
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Toast.makeText(MainActivity.this,response.getString("Cookie"),Toast.LENGTH_SHORT).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this,error.getMessage(),Toast.LENGTH_SHORT).show();
            }
        },map);
        jsonObjectPostRequest.setShouldCache(true);
        App.getRequestQueue(this).add(jsonObjectPostRequest);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        /*if (!subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }*/
    }

    private class MyAdapter extends BaseAdapter{
        private LayoutInflater layoutInflater;
        private Context context;
        private List<String> list;
        private ViewHolder viewHolder;

        public MyAdapter(Context context,List<String> list){
            this.context = context;
            this.list = list;
            layoutInflater = LayoutInflater.from(MainActivity.this);
        }
        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView==null){
                viewHolder = new ViewHolder();
                convertView = layoutInflater.inflate(R.layout.layout_aaa,null);
                viewHolder.gifImageView = (GifImageView) convertView.findViewById(R.id.gifview);
                convertView.setTag(viewHolder);
            }else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            Glide.with(context).load(list.get(position)).centerCrop().placeholder(R.mipmap.ic_launcher)
                    .error(R.mipmap.ic_launcher).into(viewHolder.gifImageView);
            return convertView;
        }

         class ViewHolder {
            public GifImageView gifImageView;
        }
    }
}
