package com.skcodestack.stack.ui.recycler;

import android.view.View;

import com.chad.library.adapter.base.BaseViewHolder;

/**
 * Email  1562363326@qq.com
 * Github https://github.com/skcodestack
 * Created by sk on 2018/7/14
 * Version  1.0
 * Description:
 */

public class MutipleViewHolder extends BaseViewHolder {

    private MutipleViewHolder(View view) {
        super(view);
    }

    public static MutipleViewHolder create(View view) {
        return new MutipleViewHolder(view);
    }
}
