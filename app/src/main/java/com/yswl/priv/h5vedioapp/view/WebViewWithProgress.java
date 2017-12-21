package com.yswl.priv.h5vedioapp.view;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.webkit.ClientCertRequest;
import android.webkit.ConsoleMessage;
import android.webkit.HttpAuthHandler;
import android.webkit.JsResult;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.RelativeLayout;

import com.yswl.priv.h5vedioapp.MainActivity;
import com.yswl.priv.h5vedioapp.RouteActivity;
import com.yswl.priv.h5vedioapp.tool.ADFilterTool;

/**
 * Created by kangpAdministrator on 2017/7/20 0020.
 * Emial kangpeng@yunhetong.net
 */

public class WebViewWithProgress extends RelativeLayout {
    private static final String TAG = WebViewWithProgress.class.getSimpleName();
    ObservableWebView mWebview;
    WebViewProgressBar mProgressBar;

    public WebView getmWebview() {
        return mWebview;
    }

    public void loadUrl(String url) {
        mWebview.loadUrl(url);
    }

    public WebViewWithProgress(Context context) {
        super(context);
        init(context);
    }

    public WebViewWithProgress(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public WebViewWithProgress(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    void init(Context context) {
        mWebview = new ObservableWebView(context);
        mProgressBar = new WebViewProgressBar(context);
        initWidget();
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, 6);
        RelativeLayout.LayoutParams lp2 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
        addView(mWebview, lp2);
        addView(mProgressBar, lp);
    }

    void initWidget() {
        setWebViewDebug();

        WebSettings settings = mWebview.getSettings();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            settings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        settings.setJavaScriptEnabled(true);
        settings.setLoadWithOverviewMode(true);
        settings.setSupportZoom(false);
        settings.setDomStorageEnabled(true);
        settings.setSupportMultipleWindows(true);
        settings.setUserAgentString("Mozilla/5.0(Macintosh;IntelMacOSX10_7_0)AppleWebKit/535.11(KHTML,likeGecko)Chrome/17.0.963.56Safari/535.11");
        settings.setPluginState(WebSettings.PluginState.ON);
        mWebview.setScrollContainer(false);
        mWebview.setScrollbarFadingEnabled(false);
        settings.setDefaultTextEncodingName("UTF-8");
        settings.setBuiltInZoomControls(false);
        mWebview.setVerticalScrollBarEnabled(false);
        Log.e(TAG, "UserAgent :" + settings.getUserAgentString());
        settings.setDisplayZoomControls(false);
        settings.setUseWideViewPort(true);

        settings.setJavaScriptCanOpenWindowsAutomatically(true);
//        settings.setCacheMode(WebSettings.LOAD_DEFAULT);
        setWebChromeClient();
        setWebViewClient();
    }

    public void setOnScrollChangedCallback(
            final ObservableWebView.OnScrollChangedCallback onScrollChangedCallback) {
        mWebview.setOnScrollChangedCallback(onScrollChangedCallback);
    }
    public static void setWebViewDebug() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WebView.setWebContentsDebuggingEnabled(true);
        }
    }
    private void setWebViewClient() {
        mWebview.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                Log.e(TAG, "onPageStarted url:" + url);
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                Log.e(TAG, "onPageFinished url:" + url);
                checkUrl(url);
                super.onPageFinished(view, url);
            }

            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                // TODO Auto-generated method stub
                // handler.cancel(); // Android默认的处理方式
                handler.proceed(); // 接受所有网站的证书
                // handleMessage(Message msg); // 进行其他处理
                // super.onReceivedSslError(view, handler, error);
            }

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String
                    failingUrl) {
                Log.e(TAG, " errorCode " + errorCode + " description " + description + " failingUrl" + failingUrl);
            }

            @Override
            public void onReceivedHttpError(WebView view, WebResourceRequest
                    request, WebResourceResponse errorResponse) {
                super.onReceivedHttpError(view, request, errorResponse);
            }

            @Override
            public void onReceivedHttpAuthRequest(WebView view, HttpAuthHandler handler, String
                    host, String realm) {
                Log.d(TAG, "onReceivedHttpAuthRequest host" + host + "realm " + realm);
                super.onReceivedHttpAuthRequest(view, handler, host, realm);
            }

            @Override
            public void onScaleChanged(WebView view, float oldScale, float newScale) {
                super.onScaleChanged(view, oldScale, newScale);
            }

            @Override
            public boolean shouldOverrideKeyEvent(WebView view, KeyEvent event) {
                Log.d(TAG, "shouldOverrideKeyEvent" + event.getKeyCode() + " " + event.getUnicodeChar());
                return super.shouldOverrideKeyEvent(view, event);
            }

            @Override
            public void onReceivedLoginRequest(WebView view, String realm, String account, String args) {
                super.onReceivedLoginRequest(view, realm, account, args);
            }

            @Override
            public WebResourceResponse shouldInterceptRequest(WebView view, String url) {
                url = url.toLowerCase();
                Log.e(TAG, "shouldInterceptRequesturl " + url);
                return super.shouldInterceptRequest(view, url);
//                if (!ADFilterTool.hasAd(view.getContext(), url)) {
//                    Log.e(TAG, "shouldInterceptRequesturl 加载" + url);
//
//                } else {
//                    Log.e(TAG, "shouldInterceptRequesturl 拦截");
//                    return new WebResourceResponse(null, null, null);
//                }
            }


            @Override
            public void onLoadResource(WebView view, String url) {
                Log.e(TAG, "onLoadResource " + url);
                checkUrl(url);
                super.onLoadResource(view, url);
//                if (!ADFilterTool.hasAd(view.getContext(), url)) {
//                    Log.e(TAG, "onLoadResource 加载..." + url);
//                }
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                Log.e(TAG, "shouldOverrideUrlLoading " + url);
                checkUrl(url);
//                view.loadUrl(url);

                return false;
            }

            @Override
            public void onReceivedClientCertRequest(WebView view, ClientCertRequest request) {
                super.onReceivedClientCertRequest(view, request);
            }

        });

    }
    private boolean checkUrl(String url) {
        if (url.startsWith("http://www.iqiyi.com/v_") && url.contains(".html")) {
            if (null != onParseCallBack)
                onParseCallBack.onJump(url);
            return true;
        }
        if (url.startsWith("http://v.youku.com/v_show/id_") && url.contains(".html?")) {
            if (null != onParseCallBack)
                onParseCallBack.onJump(url);
            return true;
        }

        if (url.startsWith("https://v.qq.com/x/cover/")) {
            if (null != onParseCallBack)
                onParseCallBack.onJump(url);
            return true;
        }

        return false;
    }
    private boolean checkUrl2(String url) {
        //TODO set rule https://m.v.qq.com/cover/d/dhzimk1qzznf301.html?vid=z00242gyhyw
        if (url.startsWith("http://m.iqiyi.com/v_") && url.contains(".html") && !url.contains("api")) {
            if (null != onParseCallBack)
                onParseCallBack.onJump(url);
            return true;
        }
        if (url.startsWith("http://m.youku.com/video") && url.contains(".html") && !url.contains("api")) {
            if (null != onParseCallBack)
                onParseCallBack.onJump(url);
            return true;
        }

        if (url.startsWith("https://m.v.qq.com/") && url.contains(".html") && !url.contains("univ_id")) {
            if (null != onParseCallBack)
                onParseCallBack.onJump(url);
            return true;
        }

        return false;
    }


    OnParseCallBack onParseCallBack;
    OnTitleCallBack onTitleCallBack;

    public void setOnTitleCallBack(OnTitleCallBack onJumpCallBack) {
        this.onTitleCallBack = onJumpCallBack;
    }

    public void setOnParseCallBack(OnParseCallBack onJumpCallBack) {
        this.onParseCallBack = onJumpCallBack;
    }

    public interface OnParseCallBack {
        void onJump(String url);
    }

    public interface OnTitleCallBack {
        void onTitleJump(String title);

    }

    private void setWebChromeClient() {
        mWebview.setWebChromeClient(new WebChromeClient() {
            @Override
            public boolean onCreateWindow(WebView view, boolean isDialog, boolean isUserGesture, Message resultMsg) {
                WebView newWebView = new WebView(view.getContext());
                newWebView.setWebViewClient(new WebViewClient() {
                    @Override
                    public boolean shouldOverrideUrlLoading(WebView view, String url) {
//                        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
//                        view.getContext().startActivity(browserIntent);
                        RouteActivity.JumpAct(view.getContext(),url);
                        return true;
                    }
                });
                WebView.WebViewTransport transport = (WebView.WebViewTransport) resultMsg.obj;
                transport.setWebView(newWebView);
                resultMsg.sendToTarget();
                Log.e(TAG,"onCreateWindow "+resultMsg);
                return true;
            }

            @Override
            public void onReceivedIcon(WebView view, Bitmap icon) {
                super.onReceivedIcon(view, icon);
            }

            @Override
            public void onReceivedTouchIconUrl(WebView view, String url, boolean precomposed) {
                Log.e(TAG, " onReceivedTouchIconUrl url " + url + " precomposed " + precomposed);
                super.onReceivedTouchIconUrl(view, url, precomposed);
            }

            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                Log.e(TAG, "onReceivedTitle title:" + title);
                CharSequence pnotfound = "The page cannot be found";
                if (title.contains(pnotfound)) {
                    view.stopLoading();
                } else if (null != onTitleCallBack) {
                    String tit;
                    if(title.contains("-")){
                         tit = title.split("-")[0];
                    }else if(title.contains("—")){
                        tit = title.split("-")[0];
                    }else {
                        tit = title;
                    }
                    onTitleCallBack.onTitleJump(tit);
                }
            }

            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                if (null != mProgressBar)
                    mProgressBar.setProgress(newProgress);
            }

            @Override
            public boolean onConsoleMessage(ConsoleMessage consoleMessage) {
                return super.onConsoleMessage(consoleMessage);
            }

            @Override
            public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
                return false;
            }

        });

    }
}
