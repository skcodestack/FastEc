package com.skcodestack.stack.delegates.web.event;

import android.content.Context;
import android.webkit.WebView;

import com.skcodestack.stack.delegates.LemonDelegate;

/**
 * Email  1562363326@qq.com
 * Github https://github.com/skcodestack
 * Created by sk on 2018/7/17
 * Version  1.0
 * Description:
 */

public abstract class Event implements IEvent {

    private Context mContext = null;
    private String mAction = null;
    private String mUrl = null;
    private LemonDelegate mDelegate = null;
    private WebView mWebView = null;

    public WebView getWebView() {
        return mWebView;
    }

    public void setWebView(WebView mWebView) {
        this.mWebView = mWebView;
    }

    public Context getContext() {
        return mContext;
    }

    public void setContext(Context mContext) {
        this.mContext = mContext;
    }

    public String getAction() {
        return mAction;
    }

    public void setAction(String mAction) {
        this.mAction = mAction;
    }

    public String getUrl() {
        return mUrl;
    }

    public void setUrl(String mUrl) {
        this.mUrl = mUrl;
    }

    public LemonDelegate getDelegate() {
        return mDelegate;
    }

    public void setDelegate(LemonDelegate mDelegate) {
        this.mDelegate = mDelegate;
    }
}
