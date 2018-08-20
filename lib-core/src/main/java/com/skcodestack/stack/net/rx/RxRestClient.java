package com.skcodestack.stack.net.rx;

import android.content.Context;

import com.skcodestack.stack.net.HttpMethod;
import com.skcodestack.stack.net.RestCreator;
import com.skcodestack.stack.net.callback.IProgressListener;
import com.skcodestack.stack.net.down.DownloadCreator;
import com.skcodestack.stack.ui.loader.LemonLoader;
import com.skcodestack.stack.ui.loader.LoaderStyle;

import java.io.File;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;

/**
 * Email  1562363326@qq.com
 * Github https://github.com/skcodestack
 * Created by sk on 2018/2/11
 * Version  1.0
 * Description:
 */

@SuppressWarnings("ALL")
public class RxRestClient {

    private final String URL;
    private final Map<String, Object> PARAMS;
    private final RequestBody BODY;
    private final Context CONTEXT;
    private final LoaderStyle LOADERSTYLE;
    private final File FILE;
    private final IProgressListener PROGRESSLISTENER;

    public RxRestClient(String url,
                        Map<String, Object> params,
                        RequestBody body,
                        File file,
                        IProgressListener progresslistener,
                        Context context,
                        LoaderStyle loaderStyle) {
        this.URL = url;
        this.PARAMS = params;
        this.BODY = body;
        this.FILE = file;
        this.PROGRESSLISTENER = progresslistener;
        this.CONTEXT = context;
        this.LOADERSTYLE = loaderStyle;
    }

    public static RxRestClientBuilder builder() {
        return new RxRestClientBuilder();
    }

    public Observable<String> request(HttpMethod method) {

        RxRestService service = RestCreator.getRxRestService();
        Observable<String> observable = null;
        if (LOADERSTYLE != null) {
            LemonLoader.showLoading(CONTEXT, LOADERSTYLE.name());
        }
        switch (method) {
            case GET:
                observable = service.get(URL, PARAMS);
                break;
            case POST:
                observable = service.post(URL, PARAMS);
                break;
            case POST_RAW:
                observable = service.postRaw(URL, BODY);
                break;
            case PUT:
                observable = service.put(URL, PARAMS);
                break;
            case PUT_RAW:
                observable = service.putRaw(URL, BODY);
                break;
            case DELETE:
                observable = service.delete(URL, PARAMS);
                break;
            case UPLOAD:
                final RequestBody requestBody =
                        RequestBody.create(MediaType.parse(MultipartBody.FORM.toString()), FILE);
                final MultipartBody.Part part =
                        MultipartBody.Part.createFormData("file", FILE.getName(), requestBody);
                observable = service.upload(URL, part);
                break;
            default:
                break;
        }
        return observable;
    }


    public final Observable<String> get() {
        return request(HttpMethod.GET);
    }

    public final Observable<String> post() {
        if (BODY == null) {
            return request(HttpMethod.POST);
        } else {
            if (!PARAMS.isEmpty()) {
                throw new RuntimeException(" invoke post raw , params must be null ! ");
            }
            return request(HttpMethod.POST_RAW);
        }
    }

    public final Observable<String> put() {
        if (BODY == null) {
            return request(HttpMethod.PUT);
        } else {
            if (!PARAMS.isEmpty()) {
                throw new RuntimeException(" invoke put raw , params must be null ! ");
            }
            return request(HttpMethod.PUT_RAW);
        }
    }

    public final Observable<String> delete() {
        return request(HttpMethod.DELETE);
    }

    public final Observable<String> upload() {
        if (FILE == null || !FILE.exists()) {
            throw new RuntimeException("please make sure upload file is exist!");
        }
        return request(HttpMethod.UPLOAD);
    }

    public final Observable<ResponseBody> download() {
        return DownloadCreator.getRxDownLoadService(PROGRESSLISTENER).download(URL, PARAMS);
    }
}
