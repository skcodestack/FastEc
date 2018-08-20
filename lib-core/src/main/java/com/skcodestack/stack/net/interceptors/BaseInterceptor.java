package com.skcodestack.stack.net.interceptors;

import java.io.IOException;
import java.util.LinkedHashMap;

import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Response;

/**
 * Email  1562363326@qq.com
 * Github https://github.com/skcodestack
 * Created by sk on 2018/7/10
 * Version  1.0
 * Description:
 */

public abstract class BaseInterceptor implements Interceptor {


    protected LinkedHashMap<String, String> getUrlParameters(Chain chain) {
        HttpUrl url = chain.request().url();
        int size = url.querySize();
        LinkedHashMap<String, String> map = new LinkedHashMap<>();
        for (int i = 0; i < size; i++) {
            map.put(url.queryParameterName(i), url.queryParameterValue(i));
        }
        return map;
    }

    protected String getUrlParameter(Chain chain, String key) {
        HttpUrl url = chain.request().url();
        return url.queryParameter(key);
    }

    protected LinkedHashMap<String, String> getBodyParameters(Chain chain) {
        FormBody formBody = (FormBody) chain.request().body();
        int size = formBody.size();
        LinkedHashMap<String, String> map = new LinkedHashMap<>();
        for (int i = 0; i < size; i++) {
            map.put(formBody.name(i),formBody.value(i));
        }
        return map;
    }

    protected String getBodyParameter(Chain chain,String key) {
        return getBodyParameters(chain).get(key);
    }
}
