package com.yswl.priv.h5vedioapp;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Display;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.yswl.priv.h5vedioapp.view.WebViewWithProgress;

import static com.yswl.priv.h5vedioapp.view.WebViewWithProgress.MOBULE_USERAGENT;
import static com.yswl.priv.h5vedioapp.view.WebViewWithProgress.PC_USERAGENT;

public class MainActivity extends AppCompatActivity {
/*    http://jx.aeidu.cn/7.php?url=
    http://jx.aeidu.cn/6.php?url=
    http://jx.aeidu.cn/5.php?url=
    http://jx.aeidu.cn/4.php?url=*/


//    http://jx.aeidu.cn/index.php?url= //万能1
//"http://api.svip.baiyug.cn/svip/index.php?url=" //万能2
    //http://www.82190555.com/index/qqvod.php?url= //万能 3
    //http://api.91exp.com/svip/?url= //万能 4
//    http://jx.vgoodapi.com/jx.php?url= //万能 5
    //https://api.flvsp.com/?url= //爱奇艺


    public static void JumpAct(Context context, String title, String url) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra("video_url", url);
        intent.putExtra("video_name", title);
        context.startActivity(intent);
    }

    private static final String TAG = MainActivity.class.getSimpleName();
    private String ORIGINAL_URL;
    private String URL;
    WebViewWithProgress mWebview;
    LinearLayout layout;
    Toolbar mToolBar;
    private boolean isLandscape = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mToolBar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolBar);

        URL = getIntent().getStringExtra("video_url");
        String name = getIntent().getStringExtra("video_name");
        setTitle(name);
        isLandscape = isLandscape();
        layout = (LinearLayout) findViewById(R.id.layout);
        mWebview = new WebViewWithProgress(this, true);
        layout.addView(mWebview);
        mWebview.getWebview().getSettings().setUserAgentString(PC_USERAGENT);
        mWebview.loadUrl(MENU[5] + URL);
        Log.e(TAG, "detailUrl ： " + MENU[5] + URL);
        mWebview.setLoadFullScreenCallBack(new WebViewWithProgress.LoadFullScreenCallBack() {
            @Override
            public void onFullScreenJump(String url) {
                Log.e(TAG, "onFullScreenJump : " + url);
                ORIGINAL_URL = url;
            }
        });
    }


    private boolean isLandscape() {
        Display display = getWindowManager().getDefaultDisplay();//计算此时手机的宽度和高度，来进行判断，是否需要显示标题
        int width = display.getWidth();
        int height = display.getHeight();
        if (width > height) {
            return true;
        } else {
            return false;
        }
    }

    public final static String[] MENU = {
            "http://jx.aeidu.cn/index.php?url=", //通用
            "http://api.baiyug.cn/vip/index.php?url=",
            "http://api.svip.baiyug.cn/svip/index.php?url=",
            "http://www.82190555.com/index/qqvod.php?url=",
            "http://jx.vgoodapi.com/jx.php?url=",
            "http://api.91exp.com/svip/?url=",
            "http://player.jidiaose.com/supapi/iframe.php?v="//优酷
    };
    public final static String[] NAME = {
            "通用线路一",
            "通用线路二",
            "通用线路三",
            "通用线路四",
            "通用线路五",
            "通用线路六",
            "优酷线路"
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.clear();
        String tit = isLandscape ? "竖屏" : "横屏";
        SubMenu sub2 = menu.addSubMenu(0, 12, 0, tit);
        SubMenu sub = menu.addSubMenu(1, 10, 0, "播放卡顿");
        sub.add(1, 11, 0, "刷新");
        sub.add(1, 0, 0, NAME[0]);
        sub.add(1, 1, 0, NAME[1]);
        sub.add(1, 2, 0, NAME[2]);
        sub.add(1, 3, 0, NAME[3]);
        sub.add(1, 4, 0, NAME[4]);
        sub.add(1, 5, 0, NAME[5]);
        sub.add(1, 6, 0, NAME[6]);
        sub.getItem().setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS | MenuItem.SHOW_AS_ACTION_WITH_TEXT);
        sub2.getItem().setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS | MenuItem.SHOW_AS_ACTION_WITH_TEXT);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == 10) {
            return false;
        } else if (id == 11) {
            mWebview.getWebview().reload();
            return true;
        } else if (id == 12) {
            isLandscape = !isLandscape;
            int tag = isLandscape ? ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE : ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
            Toast.makeText(this, ORIGINAL_URL, Toast.LENGTH_SHORT).show();
            if (!TextUtils.isEmpty(ORIGINAL_URL)) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                String url = ORIGINAL_URL;
//                Uri uri = Uri.parse("http://116.31.98.13/videos/v0/20171218/d7/60/ed7942c3f99bcc43934107bb48bb943c.mp4?key=050949059a54447d7d70138e44c8281a6&dis_k=2f8ee3c34532566afb5ef45e4e0f83992&dis_t=1514448596&dis_dz=CT-GuangDong&dis_st=44&src=iqiyi.com&uuid=a795aeb-5a44a6d4-ba&m=v&qd_ip=db87e141&qd_p=db87e141&qd_k=5ced0c4d9d0a206686fa26b8e7847acc&qd_src=02020031010000000000&ssl=&ip=&qd_vip=0&dis_src=vrs&qd_uid=0&qdv=1&qd_tm=1514448596072");
                Uri uri = Uri.parse(url);
                intent.setData(uri);
                startActivityForResult(intent, 2);
            }
//            setRequestedOrientation(tag);
//            supportInvalidateOptionsMenu();
            return true;
        } else if (id == 0 || id == 3 || id == 4 || id == 5 || id == 6) {
            mWebview.getWebview().getSettings().setUserAgentString(MOBULE_USERAGENT);
            Log.e(TAG, MENU[id] + URL);
        } else if (id == 1 || id == 2) {//百域阁 需要UserAgent
            mWebview.getWebview().getSettings().setUserAgentString(MOBULE_USERAGENT);
            Log.e(TAG, MENU[id] + URL);
        }
            mWebview.getWebview().clearHistory();
            mWebview.getWebview().loadUrl(MENU[id] + URL);
//        Intent intent = new Intent(Intent.ACTION_VIEW);
//        Uri uri = Uri.parse(MENU[id] + URL);
//        intent.setData(uri);
//        startActivity(intent);
        return true;
    }

        @Override
        public void onConfigurationChanged (Configuration newConfig){
            super.onConfigurationChanged(newConfig);
            String message = newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE ? "屏幕设置为：横屏" : "屏幕设置为：竖屏";
        }

        @Override
        protected void onPause () {
            super.onPause();
//        mWebview.getWebview().onPause();
        }

        @Override
        protected void onResume () {
            super.onResume();
//        mWebview.getWebview().onResume();
        }

        @Override
        protected void onRestart () {
            super.onRestart();
//        mWebview.getWebview().reload();
            mWebview.getWebview().onResume();
        }

        @Override
        protected void onStop () {
            super.onStop();
//        mWebview.getWebview().stopLoading();
            mWebview.getWebview().onPause();
        }

        @Override
        protected void onDestroy () {
            super.onDestroy();
            ViewParent parent = mWebview.getParent();
            if (parent != null) {
                ((ViewGroup) parent).removeView(mWebview);
            }

            mWebview.getWebview().stopLoading();
            // 退出时调用此方法，移除绑定的服务，否则某些特定系统会报错
            mWebview.getWebview().clearHistory();
            mWebview.getWebview().clearView();
            mWebview.removeAllViews();
            mWebview.getWebview().destroy();
            mWebview = null;
        }

        @Override
        public boolean onKeyDown ( int keyCode, KeyEvent event){
            if (keyCode == KeyEvent.KEYCODE_BACK && mWebview.getWebview().canGoBack()) {
                Log.e(TAG, "jump count :" + mWebview.getWebview().copyBackForwardList().getSize());
                mWebview.getWebview().goBack();
                return true;
            }
            return super.onKeyDown(keyCode, event);
        }


    }
