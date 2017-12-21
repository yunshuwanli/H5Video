package com.yswl.priv.h5vedioapp;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageButton;

import com.yswl.priv.h5vedioapp.view.ObservableWebView;
import com.yswl.priv.h5vedioapp.view.WebViewWithProgress;

public class RouteActivity extends AppCompatActivity{
    private static final String TAG = RouteActivity.class.getSimpleName();
    WebViewWithProgress mWebview;
    ImageButton mPlayer;
    String mJumpURL;
    Toolbar mToolbar;
    public static void JumpAct(Context context, String url){
        Intent intent = new Intent(context, RouteActivity.class);
        intent.putExtra("url", url);
        context. startActivity(intent);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        String url = getIntent().getStringExtra("url");

        mWebview = (WebViewWithProgress) findViewById(R.id.royte_wv);
        mWebview.setOnParseCallBack(new WebViewWithProgress.OnParseCallBack() {
            @Override
            public void onJump(String url) {
                Log.e(TAG, "onJump url:" + url);
                mJumpURL = url;
                if (!TextUtils.isEmpty(mJumpURL))
                    mPlayer.setVisibility(View.VISIBLE);
            }
        });
        mWebview.setOnScrollChangedCallback(new ObservableWebView.OnScrollChangedCallback() {
            @Override
            public void onScroll(int dx, int dy) {
                if(dx>2){
                    mPlayer.setVisibility(View.GONE);
                }
            }
        });
        mWebview.setOnTitleCallBack(new WebViewWithProgress.OnTitleCallBack() {
            @TargetApi(Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onTitleJump(String title) {
                getWindow().setNavigationBarColor(getResources().getColor(R.color.colorAccent));
                setTitle(title);
            }
        });
        mPlayer = (ImageButton) findViewById(R.id.player);
        mPlayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //                 final String LOCAL = "file:///android_asset/index.htm?url=";
//                 String fileUrl = "http://baiyug.cn/?m=vod-play-id-86458-src-1-num-7.html";
                final String ONLINE = "http://api.svip.baiyug.cn/svip/index.php?url=";
                MainActivity.JumpAct(RouteActivity.this,getTitle().toString(),ONLINE+mJumpURL);
                Log.e(TAG, "onJump jumpUrl:" + ONLINE+mJumpURL);
            }
        });
        mWebview.loadUrl(url);
    }




    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && mWebview.getmWebview().canGoBack()) {
            mWebview.getmWebview().goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
