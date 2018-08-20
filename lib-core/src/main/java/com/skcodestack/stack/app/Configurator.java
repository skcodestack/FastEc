package com.skcodestack.stack.app;

import android.app.Activity;

import com.blankj.utilcode.util.Utils;
import com.joanzapata.iconify.IconFontDescriptor;
import com.joanzapata.iconify.Iconify;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;
import com.skcodestack.stack.delegates.web.event.Event;
import com.skcodestack.stack.delegates.web.event.EventManager;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.WeakHashMap;


import okhttp3.Interceptor;

/**
 * Email  1562363326@qq.com
 * Github https://github.com/skcodestack
 * Created by sk on 2018/7/10
 * Version  1.0
 * Description:
 */

public class Configurator {

    private static final WeakHashMap<Object, Object> LEMON_CONFIGS = new WeakHashMap<>();
    //字体图标
    private static final ArrayList<IconFontDescriptor> ICONS = new ArrayList<>();
    //okhttp 拦截器集合
    private static final ArrayList<Interceptor> INTERCEPTORS = new ArrayList<>();

    private Configurator() {
        LEMON_CONFIGS.put(ConfigType.CONFIG_READY, false);

    }

    public static Configurator getInstance() {
        return Holder.INSTANCE;
    }

    public WeakHashMap<Object, Object> getLemonConfigs() {
        return LEMON_CONFIGS;
    }

    public Configurator withEvent(String key, Event event) {
        EventManager.getInstance().addEvent(key,event);
        return this;
    }

    private static class Holder {
        private static final Configurator INSTANCE = new Configurator();
    }

        public final void configure() {
        initIcons();
        Logger.addLogAdapter(new AndroidLogAdapter());
        LEMON_CONFIGS.put(ConfigType.CONFIG_READY, true);
        Utils.init(Lemon.getApplicationContext());
    }

    private void initIcons() {
        if (ICONS.size() > 0) {
            final Iconify.IconifyInitializer initializer = Iconify.with(ICONS.get(0));
            for (int i = 1; i < ICONS.size(); i++) {
                initializer.with(ICONS.get(i));
            }
        }
    }


    public final Configurator withIcon(IconFontDescriptor icon) {
        ICONS.add(icon);
        return this;
    }

    public final Configurator withWXAppId(String appId) {
        LEMON_CONFIGS.put(ConfigType.WX_CHAT_APP_ID, appId);
        return this;
    }

    public final Configurator withWXAppSecret(String appSecret) {
        LEMON_CONFIGS.put(ConfigType.WX_CHAT_APP_SECRET, appSecret);
        return this;
    }

    public final Configurator withActivity(Activity activity) {
        LEMON_CONFIGS.put(ConfigType.ACTIVITY, activity);
        return this;
    }

    public final Configurator withApiHost(String host) {
        LEMON_CONFIGS.put(ConfigType.API_HOST, host);
        return this;
    }

    public final Configurator withRequestDelay(long delay) {
        LEMON_CONFIGS.put(ConfigType.REQUEST_DELAY, delay);
        return this;
    }

    public final Configurator withInterceptor(Interceptor interceptor) {
        INTERCEPTORS.add(interceptor);
        LEMON_CONFIGS.put(ConfigType.INTERCEPTORS, INTERCEPTORS);
        return this;
    }

    public final Configurator withInterceptors(ArrayList<Interceptor> interceptors) {
        INTERCEPTORS.addAll(interceptors);
        LEMON_CONFIGS.put(ConfigType.INTERCEPTORS, INTERCEPTORS);
        return this;
    }

    public void checkConfiguration() {
        boolean isReady = (boolean) LEMON_CONFIGS.get(ConfigType.CONFIG_READY);
        if (!isReady) {
            throw new RuntimeException("configuration is not ready!");
        }
    }

    @SuppressWarnings("unchecked")
    public <T> T getConfiguration(Enum<ConfigType> type) {
        checkConfiguration();
        return (T) LEMON_CONFIGS.get(type);
    }
}
