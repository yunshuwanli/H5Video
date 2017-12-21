package com.yswl.priv.h5vedioapp;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.TouchDelegate;
import android.view.View;
import android.view.Window;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.webkit.WebSettings;

import com.yswl.priv.h5vedioapp.view.WebViewWithProgress;

public class MainActivity extends AppCompatActivity {

    public static void JumpAct(Context context,String title, String url){
        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra("video_url", url);
        intent.putExtra("video_name",title);
        context. startActivity(intent);
    }
    private static final String TAG = MainActivity.class.getSimpleName();


    private  String URL ;
    WebViewWithProgress mWebview;
    Toolbar mToolBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mToolBar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolBar);

        URL = getIntent().getStringExtra("video_url");
        String name = getIntent().getStringExtra("video_name");
        setTitle(name);
        mToolBar.setTitleTextColor(getResources().getColor(android.R.color.white));

        mWebview = (WebViewWithProgress) findViewById(R.id.webview);
        mWebview.getmWebview().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showViews();
                mWebview.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        hideViews();
                    }
                },5000);
            }
        });
        mWebview.getmWebview().getSettings().setUserAgentString("Mozilla/5.0 (Linux; Android 4.4.4; SAMSUNG-SM-N900A Build/tt) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/33.0.0.0 Mobile Safari/537.36");
        mWebview.loadUrl(URL);
        Log.e(TAG, "video url ： " + URL);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mWebview.postDelayed(new Runnable() {
            @Override
            public void run() {
                hideViews();
            }
        },5000);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
       menu.add(0,0,0,"刷新")
               .setIcon(R.mipmap.ic_refresh_normal).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        return true;
    }

    private void hideViews() {
        mToolBar.animate().translationY(-mToolBar.getHeight()).setInterpolator(new AccelerateInterpolator(2));
    }

    private void showViews() {
        mToolBar.animate().translationY(0).setInterpolator(new DecelerateInterpolator(2));
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        mWebview.getmWebview().loadUrl(URL);
        return true;
    }

    @Override
    protected void onPause() {
        super.onPause();
        mWebview.getmWebview().onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mWebview.getmWebview().onPause();
        mWebview.getmWebview().destroy();
        mWebview = null;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && mWebview.getmWebview().canGoBack()) {
            Log.e(TAG, "jump count :" + mWebview.getmWebview().copyBackForwardList().getSize());
            mWebview.getmWebview().goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
