package com.skcodestack.fastec.ec.main.discover;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.skcodestack.fastec.ec.R;
import com.skcodestack.stack.delegates.bottom.BottomItemDelegate;
import com.skcodestack.stack.delegates.web.WebDelegateImpl;
import com.skcodestack.stack.delegates.web.event.EventManager;

/**
 * Email  1562363326@qq.com
 * Github https://github.com/skcodestack
 * Created by sk on 2018/7/16
 * Version  1.0
 * Description:
 */

public class DiscoverDelegate extends BottomItemDelegate {
    @Override
    public Object getLayout() {
        return R.layout.delegate_discover;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {

    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        initEvent();
        WebDelegateImpl webDelegate = WebDelegateImpl.create("index.html");
        webDelegate.setTopDelegate(this.getParentDelegate());
        loadRootFragment(R.id.web_discover_container, webDelegate);
    }

    private void initEvent() {

    }

}
