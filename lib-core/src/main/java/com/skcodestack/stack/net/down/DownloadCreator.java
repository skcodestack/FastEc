package com.skcodestack.stack.net.down;

import com.skcodestack.stack.app.ConfigType;
import com.skcodestack.stack.app.Lemon;
import com.skcodestack.stack.net.callback.IProgressListener;
import com.skcodestack.stack.net.down.rx.RxDownloadService;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Email  1562363326@qq.com
 * Github https://github.com/skcodestack
 * Created by sk on 2018/2/12
 * Version  1.0
 * Description:
 */

public class DownloadCreator {

    public static DownloadService getDownLoadService(IProgressListener listener) {
        return RetrofitHolder.createRetrofit(listener).create(DownloadService.class);
    }

    public static RxDownloadService getRxDownLoadService(IProgressListener listener) {
        return RetrofitHolder.createRetrofit(listener).create(RxDownloadService.class);
    }

    public static final class RetrofitHolder {
        public static final String BASE_URL = Lemon.getConfigurator().getConfiguration(ConfigType.API_HOST);;
        private static final int TIME_OUT = 60;

        /**
         * 工厂模式
         * @return
         */
        public static Retrofit createRetrofit(final IProgressListener listener){

            OkHttpClient OK_HTTP_CLIENT = new OkHttpClient.Builder()
                    .connectTimeout(TIME_OUT, TimeUnit.SECONDS)
                    .addInterceptor(new Interceptor() {
                        @Override
                        public Response intercept(Chain chain) throws IOException {
                            Response response = chain.proceed(chain.request());
                            return response.newBuilder()
                                    .body(new ProgressResponseBody(response.body(),listener))
                                    .build();
                        }
                    })
                    .build();

            Retrofit RETROFIT_CLIENT = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(OK_HTTP_CLIENT)
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .build();


            return RETROFIT_CLIENT;
        }

    }
}
