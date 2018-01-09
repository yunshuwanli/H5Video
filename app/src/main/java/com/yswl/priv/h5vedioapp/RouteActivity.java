package com.yswl.priv.h5vedioapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.ImageView;

import com.yswl.priv.h5vedioapp.view.WebViewWithProgress;

public class RouteActivity extends AppCompatActivity implements WebViewWithProgress.LoadDetailH5CallBack, WebViewWithProgress.LoadTitleCallBack, View.OnClickListener {
    private static final String TAG = RouteActivity.class.getSimpleName();
    WebViewWithProgress mWebview;
    ImageView mPlayer;
    String mJumpURL;
    Toolbar mToolbar;
    NestedScrollView layout;

    public static void JumpAct(Context context, String url) {
        Intent intent = new Intent(context, RouteActivity.class);
        intent.putExtra("url", url);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(mToolbar);
        String url = getIntent().getStringExtra("url");
        layout = (NestedScrollView) findViewById(R.id.layout);
        mWebview = new WebViewWithProgress(this);
        layout.addView(mWebview);
        mWebview.setLoadDetailH5CallBack(this);
        mWebview.setLoadTitleCallBack(this);
        mPlayer = (ImageView) findViewById(R.id.player);
        mPlayer.setOnClickListener(this);
        mWebview.loadUrl(url);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(0, 0, 0, "刷新")
                .setIcon(R.mipmap.ic_refresh_normal).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        mWebview.getWebview().clearCache(true);
        mWebview.getWebview().reload();
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        mWebview.getWebview().onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mWebview.getWebview().onPause();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }

//    @Override
//    protected void onRestart() {
//        super.onRestart();
//        mWebview.getWebview().reload();
//    }
//
//    @Override
//    protected void onStop() {
//        super.onStop();
//        mWebview.getWebview().stopLoading();
//    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ViewParent parent = mWebview.getParent();
        if (parent != null) {
            ((ViewGroup) parent).removeView(mWebview);
        }

        mWebview.getWebview().stopLoading();
        // 退出时调用此方法，移除绑定的服务，否则某些特定系统会报错
        mWebview.getWebview().getSettings().setJavaScriptEnabled(false);
        mWebview.getWebview().clearHistory();
        mWebview.getWebview().clearView();
        mWebview.removeAllViews();
        mWebview.getWebview().destroy();
        mWebview = null;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && mWebview.getWebview().canGoBack()) {
            mWebview.getWebview().goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onDetailJump(String url) {
        Log.e(TAG, "onJump url:" + url);
        mJumpURL = url;
        if (!TextUtils.isEmpty(mJumpURL))
            mPlayer.setVisibility(View.VISIBLE);
    }


    @Override
    public void onTitle(String title) {
        setTitle(title);
    }

    @Override
    public void onClick(View v) {

//                 String fileUrl = "http://baiyug.cn/?m=vod-play-id-86458-src-1-num-7.html";
            MainActivity.JumpAct(RouteActivity.this, getTitle().toString(), mJumpURL);
        Log.e(TAG, "onJump jumpUrl:"  +mJumpURL);
    }
}
