package com.skcodestack.stack.ui.banner;

import android.content.Context;
import android.view.View;

import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.holder.Holder;
import com.skcodestack.stack.R;
import com.skcodestack.stack.delegates.LemonDelegate;

/**
 * Email  1562363326@qq.com
 * Github https://github.com/skcodestack
 * Created by sk on 2018/7/15
 * Version  1.0
 * Description:
 */

public class HolderCreator implements CBViewHolderCreator {
    Context mContext;

    public HolderCreator(Context context) {
        this.mContext = context;
    }

    @Override
    public Holder createHolder(View itemView) {
        return new ImageHolder(mContext,itemView);
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_load_image;
    }
}
