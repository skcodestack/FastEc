package com.skcodestack.stack.ui.loader;

import android.content.Context;

import com.wang.avi.AVLoadingIndicatorView;
import com.wang.avi.Indicator;

import java.util.WeakHashMap;

/**
 * Email  1562363326@qq.com
 * Github https://github.com/skcodestack
 * Created by sk on 2018/2/11
 * Version  1.0
 * Description:
 */

@SuppressWarnings("ALL")
public class LoaderCreator {
    private static final WeakHashMap<String, Indicator> LOADING_MAP = new WeakHashMap<>();

    static AVLoadingIndicatorView create(Context context, String type) {
        final AVLoadingIndicatorView loadingIndicatorView = new AVLoadingIndicatorView(context);
        if (LOADING_MAP.get(type) == null) {
            Indicator indicator = getIndicator(type);
            LOADING_MAP.put(type, indicator);
        }
        loadingIndicatorView.setIndicator(LOADING_MAP.get(type));
        return loadingIndicatorView;
    }

    static Indicator getIndicator(String type) {
        if (type == null || type.isEmpty()) {
            return null;
        }
        StringBuffer sb = new StringBuffer();
        if (!type.contains(".")) {
            String packagename = AVLoadingIndicatorView.class.getPackage().getName();
            sb.append(packagename)
                    .append(".indicators")
                    .append(".");
        }
        sb.append(type);
        try {
            Class<?> aClass = Class.forName(sb.toString());
            return (Indicator) aClass.newInstance();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }
}
