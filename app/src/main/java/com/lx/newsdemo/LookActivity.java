package com.lx.newsdemo;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

public class LookActivity extends AppCompatActivity {
    private ProgressBar progressBar;
    private WebView webView;
    private String url;
    private Handler handler = new Handler(){
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            progressBar.setProgress(msg.arg1);
            if(msg.arg1==100){
                progressBar.setVisibility(View.GONE);
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_look);
        url = getIntent().getStringExtra("url");
        initView();
    }
    @SuppressLint("SetJavaScriptEnabled")
    private void initView() {
        progressBar = (ProgressBar) findViewById(R.id.look_progressBar);
        webView = (WebView) findViewById(R.id.look_webView);
        webView.loadUrl(url);
        webView.getSettings().setLoadWithOverviewMode(true);//back键返回
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebChromeClient(new MyWebChromeClient());
        webView.setWebViewClient(new MyWebViewClient());
        webView.getSettings().setLoadsImagesAutomatically(true);
    }
    class MyWebChromeClient extends WebChromeClient {
        public void onProgressChanged(WebView view, int newProgress) {
            //获取进度更新
            Message message = Message.obtain();
            message.arg1=newProgress;
            handler.sendMessage(message);
            super.onProgressChanged(view, newProgress);
        }
    }
    class MyWebViewClient extends WebViewClient {
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            //拦截系统加载
            view.loadUrl(url);
            return true;
        }
    }
}
