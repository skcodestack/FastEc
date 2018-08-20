package com.skcodestack.stack.delegates.web.client;

import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Handler;
import android.webkit.CookieManager;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.skcodestack.stack.app.ConfigType;
import com.skcodestack.stack.app.Lemon;
import com.skcodestack.stack.delegates.IPageLoadListener;
import com.skcodestack.stack.delegates.web.WebDelegate;
import com.skcodestack.stack.delegates.web.route.Router;
import com.skcodestack.stack.ui.loader.LemonLoader;
import com.skcodestack.stack.util.log.LemonLogger;
import com.skcodestack.stack.util.storage.LemonPreference;

/**
 * Email  1562363326@qq.com
 * Github https://github.com/skcodestack
 * Created by sk on 2018/7/16
 * Version  1.0
 * Description:
 */

public class WebViewClientImpl extends WebViewClient {

    private final WebDelegate DELEGATE;
    private IPageLoadListener mIPageLoadListener = null;
    private static final Handler HANDLER = Lemon.getHandler();

    public void setPageLoadListener(IPageLoadListener listener) {
        this.mIPageLoadListener = listener;
    }

    public WebViewClientImpl(WebDelegate delegate) {
        this.DELEGATE = delegate;
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
        return super.shouldOverrideUrlLoading(view, request);
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        LemonLogger.d(url);
        return Router.getInstance().handleWebUrl(DELEGATE, url);
    }

    /**
     * 5.0以下
     * @param view
     * @param url
     * @return
     */
    @Override
    public WebResourceResponse shouldInterceptRequest(WebView view, String url) {
        DELEGATE.synchronousWebCookies(url);
        return super.shouldInterceptRequest(view, url);
    }

    /**
     * 5.0 上
     * @param view
     * @param request
     * @return
     */
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
        String url = request.getUrl().toString();
        DELEGATE.synchronousWebCookies(url);
        return super.shouldInterceptRequest(view, request);
    }

    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon) {
        super.onPageStarted(view, url, favicon);
        if (mIPageLoadListener != null) {
            mIPageLoadListener.onLoadStart();
        }
        LemonLoader.showLoading(view.getContext());
    }

    @Override
    public void onPageFinished(WebView view, String url) {
        CookieManager cookieManager = CookieManager.getInstance();
        String cookieStr = cookieManager.getCookie(url);
        LemonLogger.d(cookieStr);
        LemonPreference.addCustomAppProfile(ConfigType.COOKIE.name(),cookieStr);

        super.onPageFinished(view, url);
        if (mIPageLoadListener != null) {
            mIPageLoadListener.onLoadEnd();
        }
        HANDLER.postDelayed(new Runnable() {
            @Override
            public void run() {
                LemonLoader.stopLoading();
            }
        }, 1000);
    }
}
