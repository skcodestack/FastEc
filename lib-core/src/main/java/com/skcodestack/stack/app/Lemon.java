package com.skcodestack.stack.app;

import android.app.Application;
import android.content.Context;
import android.content.res.Resources;
import android.os.Handler;
import android.os.Looper;

import java.util.WeakHashMap;

/**
 * Email  1562363326@qq.com
 * Github https://github.com/skcodestack
 * Created by sk on 2018/2/10
 * Version  1.0
 * Description:   工具类
 */

public final class Lemon {

    public static Configurator init(Context mContext){
        getConfigurations().put(ConfigType.APPLICATION_CONTEXT.name(),mContext.getApplicationContext());
        return Configurator.getInstance();
    }

    public static WeakHashMap<Object,Object> getConfigurations(){
        return Configurator.getInstance().getLemonConfigs();
    }
    public static Configurator getConfigurator(){
        return Configurator.getInstance();
    }

    public static Context getApplicationContext() {
        return (Context) getConfigurations().get(ConfigType.APPLICATION_CONTEXT.name());
    }

    public static Handler getHandler() {
        return new Handler(Looper.getMainLooper());
    }
}
