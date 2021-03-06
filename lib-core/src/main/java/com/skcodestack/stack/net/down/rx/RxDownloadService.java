package com.skcodestack.stack.net.down.rx;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

/**
 * Email  1562363326@qq.com
 * Github https://github.com/skcodestack
 * Created by sk on 2018/2/12
 * Version  1.0
 * Description:下载
 */

public interface RxDownloadService {

    @Streaming
    @GET
    Observable<ResponseBody> download(@Url String url, @QueryMap Map<String, Object> map);

}
