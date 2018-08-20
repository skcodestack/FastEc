package com.skcodestack.stack.net.rx;

import android.content.Context;

import com.skcodestack.stack.net.RestClient;
import com.skcodestack.stack.net.callback.IError;
import com.skcodestack.stack.net.callback.IFailure;
import com.skcodestack.stack.net.callback.IProgressListener;
import com.skcodestack.stack.net.callback.IRequest;
import com.skcodestack.stack.net.callback.ISuccess;
import com.skcodestack.stack.ui.loader.LoaderStyle;

import java.io.File;
import java.util.Map;
import java.util.WeakHashMap;

import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * Email  1562363326@qq.com
 * Github https://github.com/skcodestack
 * Created by sk on 2018/2/11
 * Version  1.0
 * Description:
 */

@SuppressWarnings("ALL")
public class RxRestClientBuilder {

    private static final String JSON_MEDIATYPE = "application/json;charset=UTF-8";

    private String mUrl = null;
    private Map<String, Object> mParams = null;
    private RequestBody mBody = null;
    private File mFile = null;
    private Context mContext = null;
    private LoaderStyle mLoaderStyle = null;
    IProgressListener mProgressListener;

    public RxRestClientBuilder() {
    }

    public final RxRestClientBuilder url(String url) {
        this.mUrl = url;
        return this;
    }

    public final RxRestClientBuilder params(Map<String, Object> params) {
        this.mParams = params;
        if (this.mParams == null) {
            this.mParams = new WeakHashMap<>();
        }
        return this;
    }

    public final RxRestClientBuilder params(String key, Object value) {
        if (this.mParams == null) {
            this.mParams = new WeakHashMap<>();
        }
        this.mParams.put(key, value);
        return this;
    }

    public final RxRestClientBuilder raw(String raw) {
        this.mBody = RequestBody.create(MediaType.parse(JSON_MEDIATYPE), raw);
        return this;
    }

    public final RxRestClientBuilder file(File file) {
        this.mFile = file;
        return this;
    }

    public final RxRestClientBuilder file(String filePath) {
        this.mFile = new File(filePath);
        return this;
    }

    public final RxRestClientBuilder loader(Context context, LoaderStyle loaderStyle) {
        this.mContext = context;
        this.mLoaderStyle = loaderStyle;
        return this;
    }

    /**
     * 加载样式
     *
     * @param context
     * @return
     */
    public final RxRestClientBuilder loader(Context context) {
        this.mContext = context;
        this.mLoaderStyle = LoaderStyle.BallSpinFadeLoaderIndicator;
        return this;
    }

    public final RxRestClientBuilder progressListener(IProgressListener listener) {
        this.mProgressListener = listener;
        return this;
    }

    private Map<String, Object> checkParams() {
        if (this.mParams == null) {
            this.mParams = new WeakHashMap<>();
        }
        return mParams;
    }

    public RxRestClient build() {
        return new RxRestClient(mUrl,
                checkParams(),
                mBody,
                mFile,
                mProgressListener,
                mContext,
                mLoaderStyle);
    }
}
