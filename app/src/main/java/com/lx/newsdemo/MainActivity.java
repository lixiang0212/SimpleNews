package com.lx.newsdemo;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private SwipeRefreshLayout refresh;
    private ListView listView;
    private MyAdapter adapter;
    private List<Data.NewslistBean> datas;
    private Gson gson;
    private int page=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();

    }

    private void initView() {
        refresh = (SwipeRefreshLayout) findViewById(R.id.refreshLayout);
        refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (page>0){
                    page++;initData();
                    refresh.setRefreshing(false);
                }
            }
        });
        listView = (ListView) findViewById(R.id.listView);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(MainActivity.this,LookActivity.class);
                intent.putExtra("url",datas.get(i).getUrl());
                startActivity(intent);
            }
        });
        datas = new ArrayList<>();
        gson = new Gson();
        adapter = new MyAdapter(this,datas);
        listView.setAdapter(adapter);
        initData();
    }
    private void initData(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                final Data data = gson.fromJson(DataManager.request(page),Data.class);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        datas = data.getNewslist();
                        adapter.addData(datas);
                    }
                });
            }
        }).start();
    }
}
