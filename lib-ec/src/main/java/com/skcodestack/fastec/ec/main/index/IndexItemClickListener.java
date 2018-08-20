package com.skcodestack.fastec.ec.main.index;

import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.SimpleClickListener;
import com.skcodestack.fastec.ec.main.detail.GoodsDetailDelegate;
import com.skcodestack.stack.delegates.LemonDelegate;
import com.skcodestack.stack.ui.recycler.MutipleFields;
import com.skcodestack.stack.ui.recycler.MutipleItemEntity;

/**
 * Email  1562363326@qq.com
 * Github https://github.com/skcodestack
 * Created by sk on 2018/7/15
 * Version  1.0
 * Description:
 */

public class IndexItemClickListener extends SimpleClickListener {

    private final LemonDelegate DELEGATE;

    private IndexItemClickListener(LemonDelegate delegate) {
        this.DELEGATE = delegate;
    }

    public static IndexItemClickListener create(LemonDelegate delegate) {
        return new IndexItemClickListener(delegate);
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        MutipleItemEntity entity = (MutipleItemEntity) baseQuickAdapter.getData().get(position);
        final GoodsDetailDelegate delegate = GoodsDetailDelegate.create((Integer) entity.getField(MutipleFields.ID));
        DELEGATE.start(delegate);
    }

    @Override
    public void onItemLongClick(BaseQuickAdapter adapter, View view, int position) {

    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {

    }

    @Override
    public void onItemChildLongClick(BaseQuickAdapter adapter, View view, int position) {

    }
}
