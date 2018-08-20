package com.skcodestack.stack.delegates.web;

import android.webkit.JavascriptInterface;

import com.alibaba.fastjson.JSON;
import com.skcodestack.stack.delegates.web.event.Event;
import com.skcodestack.stack.delegates.web.event.EventManager;

/**
 * Email  1562363326@qq.com
 * Github https://github.com/skcodestack
 * Created by sk on 2018/7/16
 * Version  1.0
 * Description:
 */


public class LemonWebInterface {

    private final WebDelegate DELEGATE;

    private LemonWebInterface(WebDelegate delegate) {
        this.DELEGATE = delegate;
    }

    public static LemonWebInterface create(WebDelegate delegate) {
        return new LemonWebInterface(delegate);
    }

    /**
     * js 的返回值
     *
     * @param params
     * @return
     */
    @JavascriptInterface
    public String event(String params) {
        final String action = JSON.parseObject(params).getString("action");
        Event event = EventManager.getInstance().createEvent(action);
        if (event != null) {
            event.setAction(action);
            event.setDelegate(DELEGATE);
            event.setContext(DELEGATE.getContext());
            event.setUrl(DELEGATE.getUrl());
            event.setWebView(DELEGATE.getWebView());
            return event.execute(params);
        }
        return null;
    }
}
