package com.skcodestack.stack.ui.loader;

import android.content.Context;
import android.support.v7.app.AppCompatDialog;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

import com.skcodestack.stack.R;
import com.skcodestack.stack.util.dimen.DimenUtil;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.ArrayList;

/**
 * Email  1562363326@qq.com
 * Github https://github.com/skcodestack
 * Created by sk on 2018/2/11
 * Version  1.0
 * Description:
 */

public class LemonLoader {

    private static final int LOADER_SIZE_SCALE = 8;
    private static final int LOADER_OFFSET_SCALE = 10;

    private static final String DEFAULT_LOADER_STYLE = LoaderStyle.BallSpinFadeLoaderIndicator.name();

    private static ArrayList<AppCompatDialog> LOADERS = new ArrayList<>();

    public static void showLoading(Context context, String type) {

        final AppCompatDialog dialog = new AppCompatDialog(context, R.style.dialog);
        AVLoadingIndicatorView loadingIndicatorView = LoaderCreator.create(context, type);
        dialog.setContentView(loadingIndicatorView);

        int screenHeight = DimenUtil.getScreenHeight();
        int screenWidth = DimenUtil.getScreenWidth();

        Window window = dialog.getWindow();

        if (window != null) {
            WindowManager.LayoutParams lp = window.getAttributes();
            lp.height = screenHeight / LOADER_SIZE_SCALE;
            lp.width = screenWidth / LOADER_SIZE_SCALE;
            lp.height = lp.height + screenHeight / LOADER_OFFSET_SCALE;
//            lp.width = lp.width + screenWidth / LOADER_OFFSET_SCALE;
            lp.gravity = Gravity.CENTER;
        }
        LOADERS.add(dialog);
        dialog.show();
    }

    @SuppressWarnings("unused")
    public static void showLoading(Context context) {
        showLoading(context, DEFAULT_LOADER_STYLE);
    }

    public static void stopLoading() {
        for (AppCompatDialog dialog : LOADERS) {
            if (dialog != null) {
                dialog.cancel();
            }
        }
    }
}