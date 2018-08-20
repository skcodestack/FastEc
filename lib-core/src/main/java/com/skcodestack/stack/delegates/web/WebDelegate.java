package com.skcodestack.stack.delegates.web;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebView;

import com.skcodestack.stack.app.ConfigType;
import com.skcodestack.stack.delegates.LemonDelegate;
import com.skcodestack.stack.delegates.web.route.RouteKeys;
import com.skcodestack.stack.util.log.LemonLogger;
import com.skcodestack.stack.util.storage.LemonPreference;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;

/**
 * Email  1562363326@qq.com
 * Github https://github.com/skcodestack
 * Created by sk on 2018/7/16
 * Version  1.0
 * Description:
 */

public abstract class WebDelegate extends LemonDelegate {
    private WebView mWebView;
    private final ReferenceQueue<WebView> WEB_VIEW_QUEUE = new ReferenceQueue<>();

    private String mUrl;
    private boolean mIsWebViewAbailable = false;
    private LemonDelegate mTopDelegate;

    public WebDelegate() {

    }

    public abstract IWebViewInitializer setInitializer();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Bundle args = getArguments();
        mUrl = args.getString(RouteKeys.URL.name());
        initWebView();
    }

    @SuppressLint("JavascriptInterface")
    private void initWebView() {
        if (mWebView != null) {
            mWebView.removeAllViews();
            mWebView.destroy();
        } else {
            IWebViewInitializer iWebViewInitializer = setInitializer();
            if (iWebViewInitializer != null) {
                final WeakReference<WebView> webViewWeakReference
                        = new WeakReference<WebView>(new WebView(getContext()), WEB_VIEW_QUEUE);
                mWebView = webViewWeakReference.get();
                mWebView = iWebViewInitializer.initWebView(mWebView);
                mWebView.setWebChromeClient(iWebViewInitializer.initWebChromeClient());
                mWebView.setWebViewClient(iWebViewInitializer.initWebViewClient());
                mWebView.addJavascriptInterface(LemonWebInterface.create(this), "lemon");
                mIsWebViewAbailable = true;
            } else {
                throw new NullPointerException("iWebViewInitializer is null");
            }
        }
    }

    public LemonDelegate getTopDelegate() {
        if (mTopDelegate == null) {
            mTopDelegate = this;
        }
        return mTopDelegate;
    }

    public void setTopDelegate(LemonDelegate mTopDelegate) {
        this.mTopDelegate = mTopDelegate;
    }

    public WebView getWebView() {
        if (mWebView == null) {
            throw new NullPointerException("webView is null");
        }
        return mIsWebViewAbailable ? mWebView : null;
    }

    public String getUrl() {
        if (mUrl == null) {
            throw new NullPointerException("url is null");
        }
        return mUrl;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mWebView != null) {
            mWebView.onResume();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mWebView != null) {
            mWebView.onPause();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mIsWebViewAbailable = false;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mWebView != null) {
            mWebView.removeAllViews();
            mWebView.destroy();
            mWebView = null;
        }
    }

    /**
     * 同步cookies
     * @param url
     */
    public void synchronousWebCookies( String url){
        return;
//        String cookies = LemonPreference.getCustomAppProfile(ConfigType.COOKIE.name());
//        if ( !TextUtils.isEmpty(url) )
//            if (!TextUtils.isEmpty(cookies) ) {
//                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP){
//                    CookieSyncManager.createInstance(getContext());
//                }
//                CookieManager cookieManager = CookieManager.getInstance();
//                cookieManager.setAcceptCookie(true);
//                cookieManager.removeSessionCookie();// 移除
//                cookieManager.removeAllCookie();
//                StringBuilder sbCookie = new StringBuilder();//创建一个拼接cookie的容器,为什么这么拼接，大家查阅一下http头Cookie的结构
//                sbCookie.append(cookies);//拼接sessionId
////			       sbCookie.append(String.format(";domain=%s", ""));
////			       sbCookie.append(String.format(";path=%s", ""));
//                String cookieValue = sbCookie.toString();
//                cookieManager.setCookie(url, cookieValue);//为url设置cookie
//                CookieSyncManager.getInstance().sync();//同步cookie
//                String newCookie = cookieManager.getCookie(url);
//                LemonLogger.i("同步后cookie", newCookie);
//            }
    }
}
