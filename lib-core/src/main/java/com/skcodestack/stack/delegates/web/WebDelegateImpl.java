package com.skcodestack.stack.delegates.web;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.skcodestack.stack.delegates.IPageLoadListener;
import com.skcodestack.stack.delegates.web.chromeclient.WebChromeClientImpl;
import com.skcodestack.stack.delegates.web.client.WebViewClientImpl;
import com.skcodestack.stack.delegates.web.route.RouteKeys;
import com.skcodestack.stack.delegates.web.route.Router;

/**
 * Email  1562363326@qq.com
 * Github https://github.com/skcodestack
 * Created by sk on 2018/7/16
 * Version  1.0
 * Description:
 */

public class WebDelegateImpl extends WebDelegate implements IWebViewInitializer{

    private IPageLoadListener mIPageLoadListener = null;

    public static WebDelegateImpl create(String url) {
        Bundle bundle = new Bundle();
        bundle.putString(RouteKeys.URL.name(), url);
        WebDelegateImpl delegate = new WebDelegateImpl();
        delegate.setArguments(bundle);
        return delegate;
    }

    public void setPageLoadListener(IPageLoadListener listener) {
        this.mIPageLoadListener = listener;
    }

    @Override
    public IWebViewInitializer setInitializer() {
        return this;
    }

    @Override
    public Object getLayout() {
        return getWebView();
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        if (getUrl() != null) {
            synchronousWebCookies(getUrl());
            //使用原生的方式模拟web跳转,加载页面
            Router.getInstance().loadPage(this, getUrl());
        }
    }

    @Override
    public WebView initWebView(WebView webView) {
        return new WebViewInitializer().createWebView(webView);
    }

    @Override
    public WebViewClient initWebViewClient() {
        WebViewClientImpl client  =  new WebViewClientImpl(this);
        client.setPageLoadListener(mIPageLoadListener);
        return client;
    }

    @Override
    public WebChromeClient initWebChromeClient() {
        return new WebChromeClientImpl();
    }
}
