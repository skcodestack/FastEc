package com.skcodestack.stack.delegates.web.route;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.content.ContextCompat;
import android.text.style.URLSpan;
import android.webkit.URLUtil;
import android.webkit.WebView;

import com.skcodestack.stack.delegates.LemonDelegate;
import com.skcodestack.stack.delegates.web.WebDelegate;
import com.skcodestack.stack.delegates.web.WebDelegateImpl;

/**
 * Email  1562363326@qq.com
 * Github https://github.com/skcodestack
 * Created by sk on 2018/7/16
 * Version  1.0
 * Description:
 */

public class Router {

    private Router() {
    }

    public static Router getInstance() {
        return Holder.INSTANCE;
    }

    private static class Holder {
        public static final Router INSTANCE = new Router();
    }

    public final boolean handleWebUrl(WebDelegate delegate, String url) {
        //电话
        if (url.contains("tel:")) {
            callPhone(delegate.getContext(), url);
            return true;
        }
        LemonDelegate topDelegate = delegate.getTopDelegate();
        WebDelegateImpl webDelegate = WebDelegateImpl.create(url);
        topDelegate.start(webDelegate);
        return true;
    }

    private void callPhone(Context content, String url) {
        final Intent intent = new Intent(Intent.ACTION_DIAL);
        final Uri data = Uri.parse(url);
        intent.setData(data);
        ContextCompat.startActivity(content, intent, null);
    }

    private void loadWebPage(WebView webView, String url) {
        if (webView != null) {
            webView.loadUrl(url);
        } else {
            throw new NullPointerException("webview is null");
        }
    }

    private void loadLocalPage(WebView webView, String url) {
        loadWebPage(webView, "file:///android_asset/" + url);
    }

    public void loadPage(WebView webView, String url) {
        if (URLUtil.isNetworkUrl(url) || URLUtil.isAssetUrl(url)) {
            loadWebPage(webView, url);
        } else {
            loadLocalPage(webView, url);
        }
    }

    public void loadPage(WebDelegate delegate, String url) {
        loadPage(delegate.getWebView(), url);
    }
}
