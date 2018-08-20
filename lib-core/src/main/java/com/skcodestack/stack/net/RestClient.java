package com.skcodestack.stack.net;

import android.content.Context;

import com.skcodestack.stack.net.callback.IError;
import com.skcodestack.stack.net.callback.IFailure;
import com.skcodestack.stack.net.callback.IProgressListener;
import com.skcodestack.stack.net.callback.IRequest;
import com.skcodestack.stack.net.callback.ISuccess;
import com.skcodestack.stack.net.callback.RequestCallBack;
import com.skcodestack.stack.net.down.DownloadHandler;
import com.skcodestack.stack.ui.loader.LemonLoader;
import com.skcodestack.stack.ui.loader.LoaderStyle;

import java.io.File;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;

/**
 * Email  1562363326@qq.com
 * Github https://github.com/skcodestack
 * Created by sk on 2018/2/11
 * Version  1.0
 * Description:
 */

@SuppressWarnings("ALL")
public class RestClient {

    private final String URL;
    private final Map<String, Object> PARAMS;
    private final IRequest REQUEST;
    private final ISuccess SUCCESS;
    private final IError ERROR;
    private final IFailure FAILURE;
    private final RequestBody BODY;
    private final Context CONTEXT;
    private final LoaderStyle LOADERSTYLE;
    private final File FILE;
    //download field
    private final String DOWNLOAD_DIR;
    private final String EXTENSION;
    private final String NAME;
    private final IProgressListener PROGRESSLISTENER;

    public RestClient(String url,
                      Map<String, Object> params,
                      IRequest request,
                      ISuccess success,
                      IError error,
                      IFailure failure,
                      RequestBody body,
                      File file,
                      String downloadDir,
                      String extension,
                      String name,
                      IProgressListener progresslistener,
                      Context context,
                      LoaderStyle loaderStyle) {
        this.URL = url;
        this.PARAMS = params;
        this.REQUEST = request;
        this.SUCCESS = success;
        this.ERROR = error;
        this.FAILURE = failure;
        this.BODY = body;
        this.FILE = file;
        this.DOWNLOAD_DIR = downloadDir;
        this.EXTENSION = extension;
        this.NAME = name;
        this.PROGRESSLISTENER = progresslistener;
        this.CONTEXT = context;
        this.LOADERSTYLE = loaderStyle;
    }


    public static RestClientBuilder builder() {
        return new RestClientBuilder();
    }

    public void request(HttpMethod method) {

        RestService service = RestCreator.getRestService();
        Call<String> call = null;
        if (REQUEST != null) {
            REQUEST.onReqestStart();
        }
        if (LOADERSTYLE != null) {
            LemonLoader.showLoading(CONTEXT, LOADERSTYLE.name());
        }

        switch (method) {
            case GET:
                call = service.get(URL, PARAMS);
                break;
            case POST:
                call = service.post(URL, PARAMS);
                break;
            case POST_RAW:
                call = service.postRaw(URL, BODY);
                break;
            case PUT:
                call = service.put(URL, PARAMS);
                break;
            case PUT_RAW:
                call = service.putRaw(URL, BODY);
                break;
            case DELETE:
                call = service.delete(URL, PARAMS);
                break;
            case UPLOAD:
                final RequestBody requestBody =
                        RequestBody.create(MediaType.parse(MultipartBody.FORM.toString()), FILE);

                final MultipartBody.Part part =
                        MultipartBody.Part.createFormData("file", FILE.getName(), requestBody);
                call = service.upload(URL, part);
                break;
            default:
                break;
        }

        if (call != null) {
            call.enqueue(getRequestCallBack());
        }
    }

    private Callback<String> getRequestCallBack() {
        return new RequestCallBack(
                REQUEST,
                SUCCESS,
                ERROR,
                FAILURE,
                LOADERSTYLE);
    }

    public final void get() {
        request(HttpMethod.GET);
    }

    public final void post() {
        if (BODY == null) {
            request(HttpMethod.POST);
        } else {
            if (!PARAMS.isEmpty()) {
                throw new RuntimeException(" invoke post raw , params must be null ! ");
            }
            request(HttpMethod.POST_RAW);
        }

    }

    public final void put() {
        if (BODY == null) {
            request(HttpMethod.PUT);
        } else {
            if (!PARAMS.isEmpty()) {
                throw new RuntimeException(" invoke put raw , params must be null ! ");
            }
            request(HttpMethod.PUT_RAW);
        }
    }

    public final void delete() {
        request(HttpMethod.DELETE);
    }

    public final void upload() {
        if (FILE == null || !FILE.exists()) {
            throw new RuntimeException("please make sure upload file is exist!");
        }
        request(HttpMethod.UPLOAD);
    }

    public final void download(){
        new DownloadHandler(URL,PARAMS,DOWNLOAD_DIR,EXTENSION,NAME,REQUEST,SUCCESS,ERROR,FAILURE,PROGRESSLISTENER)
                .handleDownloader();
    }
}
