package com.skcodestack.fastec.ec.main.personal;

import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.SimpleClickListener;
import com.skcodestack.fastec.ec.main.personal.adapter.ListBean;
import com.skcodestack.fastec.ec.main.personal.address.AddressDelegate;
import com.skcodestack.stack.delegates.LemonDelegate;

/**
 * Email  1562363326@qq.com
 * Github https://github.com/skcodestack
 * Created by sk on 2018/7/20
 * Version  1.0
 * Description:
 */

public class PersonOnClickListener extends SimpleClickListener {

    private final LemonDelegate DELEGATE;

    public PersonOnClickListener(LemonDelegate delegate) {
        this.DELEGATE = delegate;
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        ListBean bean = (ListBean) baseQuickAdapter.getData().get(position);
        switch (bean.getId()) {
            case 1:
                //收货地址
                DELEGATE.getParentDelegate().start(bean.getDelegate());
                break;
            case 2:
                //系统设置
                DELEGATE.getParentDelegate().start(bean.getDelegate());
                break;
            default:
                break;
        }
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
