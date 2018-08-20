package com.skcodestack.stack.net.down;

import android.os.AsyncTask;

import com.skcodestack.stack.net.callback.IError;
import com.skcodestack.stack.net.callback.IFailure;
import com.skcodestack.stack.net.callback.IProgressListener;
import com.skcodestack.stack.net.callback.IRequest;
import com.skcodestack.stack.net.callback.ISuccess;

import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Email  1562363326@qq.com
 * Github https://github.com/skcodestack
 * Created by sk on 2018/7/10
 * Version  1.0
 * Description:
 */

public class DownloadHandler {

    private final String mUrl;
    private final Map<String, Object> mParams;
    private final String mDownload_dir;
    private final String mExtension;
    private final String mName;
    private final IRequest mRequest;
    private final ISuccess mSuccess;
    private final IError mError;
    private final IFailure mFailure;
    private final IProgressListener mProgressListener;

    public DownloadHandler(String mUrl, Map<String, Object> mParams, String mDownload_dir, String mExtension, String mName, IRequest mRequest, ISuccess mSuccess, IError mError, IFailure mFailure, IProgressListener mProgressListener) {
        this.mUrl = mUrl;
        this.mParams = mParams;
        this.mDownload_dir = mDownload_dir;
        this.mExtension = mExtension;
        this.mName = mName;
        this.mRequest = mRequest;
        this.mSuccess = mSuccess;
        this.mError = mError;
        this.mFailure = mFailure;
        this.mProgressListener = mProgressListener;
    }

    public final void handleDownloader() {
        if (mRequest != null) {
            mRequest.onReqestStart();
        }

        DownloadCreator.getDownLoadService(mProgressListener).download(mUrl, mParams)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()) {
                            ResponseBody body = response.body();
                            SaveFileTask task = new SaveFileTask(mRequest, mSuccess);
                            task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, mDownload_dir, mExtension, mName, body);

                            if (task.isCancelled()) {
                                if (mRequest != null) {
                                    mRequest.onRequestEnd();
                                }
                            }
                        } else {
                            if (mError != null) {
                                mError.onError(response.code(), response.message());
                            }
                        }

                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        if (mFailure != null) {
                            mFailure.onFailure();
                        }
                    }
                });
    }
}
