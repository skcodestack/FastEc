package com.skcodestack.stack.ui.banner;

import android.content.Context;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;
import com.skcodestack.stack.R;
import com.skcodestack.stack.delegates.LemonDelegate;


import java.util.ArrayList;

/**
 * Email  1562363326@qq.com
 * Github https://github.com/skcodestack
 * Created by sk on 2018/7/15
 * Version  1.0
 * Description:
 */

public class BannerCreator {

    public static void setDeafult(Context context, ConvenientBanner<String> banner, ArrayList<String> data, OnItemClickListener listener) {
        banner.setPages(new HolderCreator(context),data)
                .setOnItemClickListener(listener)
                .setPageIndicator(new int[]{R.drawable.dot_normal, R.drawable.dot_focus})
                .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL)
                .setOnItemClickListener(listener)
                .startTurning(3000)
                .setCanLoop(true);

    }
}
