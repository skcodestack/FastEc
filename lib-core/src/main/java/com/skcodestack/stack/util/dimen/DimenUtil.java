package com.skcodestack.stack.util.dimen;

import android.content.res.Resources;
import android.util.DisplayMetrics;

import com.skcodestack.stack.app.Lemon;

/**
 * Email  1562363326@qq.com
 * Github https://github.com/skcodestack
 * Created by sk on 2018/2/11
 * Version  1.0
 * Description:
 */

public class DimenUtil {

    public static int getScreenWidth() {
        Resources resources =
                Lemon.getApplicationContext().getResources();
        DisplayMetrics displayMetrics = resources.getDisplayMetrics();
        return displayMetrics.widthPixels;
    }

    public static int getScreenHeight() {
        Resources resources =
                Lemon.getApplicationContext().getResources();
        DisplayMetrics displayMetrics = resources.getDisplayMetrics();
        return displayMetrics.heightPixels;
    }
}
