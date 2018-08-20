package com.skcodestack.stack.net.interceptors;

import android.support.annotation.RawRes;

import com.skcodestack.stack.R;
import com.skcodestack.stack.app.ConfigType;
import com.skcodestack.stack.app.Lemon;
import com.skcodestack.stack.delegates.BaseDelegate;
import com.skcodestack.stack.util.file.FileUtil;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Protocol;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * Email  1562363326@qq.com
 * Github https://github.com/skcodestack
 * Created by sk on 2018/7/10
 * Version  1.0
 * Description:
 */

public class DebugInterceptor extends BaseInterceptor {

    private final ArrayList<String> DEBUG_URL;
    private final ArrayList<Integer> DEBUG_RAW_ID;


    private DebugInterceptor(ArrayList<String> urls, ArrayList<Integer> raws) {
        this.DEBUG_URL = urls;
        this.DEBUG_RAW_ID = raws;
    }


    @Override
    public Response intercept(Chain chain) throws IOException {
        String requestUrl = chain.request().url().toString();
        int length = DEBUG_URL.size();
        for (int i = 0; i < length; i++) {
            String debug_url = DEBUG_URL.get(i);
            int raw_id = DEBUG_RAW_ID.get(i);
            if (requestUrl.contains(debug_url)) {
                return debugResponse(chain, raw_id);
            }
        }


//        if(requestUrl.contains("index")){
//            return debugResponse(chain, R.raw.index);
//        }else if(requestUrl.contains("sort_list")){
//            return debugResponse(chain,R.raw.sort_list);
//        }


        return chain.proceed(chain.request());
    }

    private Response debugResponse(Chain chain, @RawRes int rawId) {
        return getResponse(chain, FileUtil.getRawFile(rawId));
    }

    private Response getResponse(Chain chain, String json) {
        return new Response.Builder()
                .protocol(Protocol.HTTP_1_1)
                .code(200)
                .message("OK")
                .request(chain.request())
                .addHeader("Content-type", "application/json")
                .body(ResponseBody.create(MediaType.parse("application/json"), json))
                .build();
    }


    public static class Builder {
        private ArrayList<String> debug_url;
        private ArrayList<Integer> debug_raw_id;


        public Builder() {
            debug_url = new ArrayList<>();
            debug_raw_id = new ArrayList<>();
        }

        public Builder addItem(String url, int rawId) {
            debug_url.add(url);
            debug_raw_id.add(rawId);
            return this;
        }

        public Builder addList(ArrayList<String> urls, ArrayList<Integer> rawIds) {
            debug_url.addAll(urls);
            debug_raw_id.addAll(rawIds);
            return this;
        }


        public DebugInterceptor build() {
            return new DebugInterceptor(debug_url, debug_raw_id);
        }


    }

}
