package com.skcodestack.fastec;

import android.app.Application;
import android.support.multidex.MultiDex;

import com.facebook.stetho.Stetho;
import com.joanzapata.iconify.fonts.FontAwesomeModule;
import com.mob.MobSDK;
import com.skcodestack.fastec.ec.database.DatabaseManager;
import com.skcodestack.fastec.ec.icon.FontEcModule;
import com.skcodestack.fastec.event.ShareEvent;
import com.skcodestack.fastec.event.TestEvent;
import com.skcodestack.stack.app.Lemon;
import com.skcodestack.stack.net.interceptors.DebugInterceptor;
import com.skcodestack.stack.util.callback.CallbackManager;
import com.skcodestack.stack.util.callback.CallbackType;
import com.skcodestack.stack.util.callback.IGlobalCallback;
import com.umeng.analytics.MobclickAgent;

import cn.jpush.android.api.JPushInterface;
import cn.sharesdk.framework.ShareSDK;

/**
 * Email  1562363326@qq.com
 * Github https://github.com/skcodestack
 * Created by sk on 2018/7/10
 * Version  1.0
 * Description:
 */

public class ExampleApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Lemon.init(this)
                .withIcon(new FontAwesomeModule())
                .withIcon(new FontEcModule())
                .withRequestDelay(2000)
                .withApiHost("http://192.168.0.1")
                .withInterceptor(new DebugInterceptor.Builder()
                        .addItem("login.index", R.raw.login)
                        .addItem("index", R.raw.index)
                        .addItem("index_1",R.raw.index_1)
                        .addItem("sort_list", R.raw.sort_list)
                        .addItem("content_1", R.raw.content_1)
                        .addItem("content_2", R.raw.content_2)
                        .addItem("shop_cart", R.raw.shop_cart)
                        .addItem("order_list_all", R.raw.order_list_all)
                        .addItem("address", R.raw.address)
                        .addItem("goods_detail",R.raw.goods_detail)
                        .build())
                .withEvent("test", new TestEvent())
                .withEvent("share", new ShareEvent())
                .withWXAppId("")
                .withWXAppSecret("")
                .configure();
        initStetho();
        initJPush();
        initSharedSDK();
        DatabaseManager.getInstance().init(this);
        initMutilDex();
        initUMeng();
    }

    private void initSharedSDK() {
        MobSDK.init(this);
    }


    private void initMutilDex(){
        MultiDex.install(this);
    }

    private void initJPush() {
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);

        CallbackManager.getInstance().addCallback(CallbackType.OPEN_PUSH, new IGlobalCallback() {
            @Override
            public void callback(Object o) {
                if (JPushInterface.isPushStopped(getApplicationContext())) {
                    JPushInterface.resumePush(getApplicationContext());
                }
            }
        }).addCallback(CallbackType.CLOSE_PUSH, new IGlobalCallback() {
            @Override
            public void callback(Object o) {
                if (!JPushInterface.isPushStopped(getApplicationContext())) {
                    JPushInterface.stopPush(getApplicationContext());
                }
            }
        });
    }

    private void initStetho() {
        Stetho.initialize(Stetho.newInitializerBuilder(this)
                .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
                .enableWebKitInspector(Stetho.defaultInspectorModulesProvider(this))
                .build());
    }

    private void initUMeng(){
        MobclickAgent.setDebugMode(true);
        MobclickAgent.openActivityDurationTrack(false);
    }
}
