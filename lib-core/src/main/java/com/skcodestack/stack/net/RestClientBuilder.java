package com.skcodestack.stack.net;

import android.content.Context;

import com.skcodestack.stack.net.callback.IError;
import com.skcodestack.stack.net.callback.IFailure;
import com.skcodestack.stack.net.callback.IProgressListener;
import com.skcodestack.stack.net.callback.IRequest;
import com.skcodestack.stack.net.callback.ISuccess;
import com.skcodestack.stack.net.rx.RxRestClient;
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
public class RestClientBuilder {

    private static final String JSON_MEDIATYPE = "application/json;charset=UTF-8";

    private String mUrl = null;
    private Map<String, Object> mParams = null;
    private IRequest mRequest = null;
    private ISuccess mSuccess = null;
    private IError mError = null;
    private IFailure mFailure = null;
    private RequestBody mBody = null;
    private File mFile = null;
    private Context mContext = null;
    private LoaderStyle mLoaderStyle = null;
    //download field
    private String mDownloadDir = null;
    private String mExtension = null;
    private String mName = null;
    IProgressListener mProgressListener;

    public RestClientBuilder() {
    }

    public final RestClientBuilder url(String url) {
        this.mUrl = url;
        return this;
    }

    public final RestClientBuilder params(Map<String, Object> params) {
        this.mParams = params;
        if (this.mParams == null) {
            this.mParams = new WeakHashMap<>();
        }
        return this;
    }

    public final RestClientBuilder params(String key, Object value) {
        if (this.mParams == null) {
            this.mParams = new WeakHashMap<>();
        }
        this.mParams.put(key, value);
        return this;
    }

    public final RestClientBuilder raw(String raw) {
        this.mBody = RequestBody.create(MediaType.parse(JSON_MEDIATYPE), raw);
        return this;
    }

    public final RestClientBuilder file(File file) {
        this.mFile = file;
        return this;
    }

    public final RestClientBuilder file(String filePath) {
        this.mFile = new File(filePath);
        return this;
    }

    public final RestClientBuilder success(ISuccess success) {
        this.mSuccess = success;
        return this;
    }

    public final RestClientBuilder error(IError error) {
        this.mError = error;
        return this;
    }

    public final RestClientBuilder failure(IFailure failure) {
        this.mFailure = failure;
        return this;
    }

    public final RestClientBuilder request(IRequest request) {
        this.mRequest = request;
        return this;
    }

    public final RestClientBuilder loader(Context context, LoaderStyle loaderStyle) {
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
    public final RestClientBuilder loader(Context context) {
        this.mContext = context;
        this.mLoaderStyle = LoaderStyle.BallSpinFadeLoaderIndicator;
        return this;
    }

    /**
     * 下载目录
     *
     * @param dir
     * @return
     */
    public final RestClientBuilder dir(String dir) {
        this.mDownloadDir = dir;
        return this;
    }

    public final RestClientBuilder name(String name) {
        this.mName = name;
        return this;
    }

    public final RestClientBuilder extension(String extension) {
        this.mExtension = extension;
        return this;
    }

    public final RestClientBuilder progressListener(IProgressListener listener) {
        this.mProgressListener = listener;
        return this;
    }

    private Map<String, Object> checkParams() {
        if (this.mParams == null) {
            this.mParams = new WeakHashMap<>();
        }
        return mParams;
    }

    public RestClient build() {
        return new RestClient(mUrl,
                checkParams(),
                mRequest,
                mSuccess,
                mError,
                mFailure,
                mBody,
                mFile,
                mDownloadDir,
                mExtension,
                mName,
                mProgressListener,
                mContext,
                mLoaderStyle);
    }
}
