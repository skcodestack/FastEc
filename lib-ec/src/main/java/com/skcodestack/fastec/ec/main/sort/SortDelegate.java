package com.skcodestack.fastec.ec.main.sort;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.skcodestack.fastec.ec.R;
import com.skcodestack.fastec.ec.main.sort.content.ContentDelegate;
import com.skcodestack.fastec.ec.main.sort.list.VerticalListDelegate;
import com.skcodestack.stack.delegates.bottom.BottomItemDelegate;

/**
 * Email  1562363326@qq.com
 * Github https://github.com/skcodestack
 * Created by sk on 2018/7/13
 * Version  1.0
 * Description:
 */

public class SortDelegate extends BottomItemDelegate {
    @Override
    public Object getLayout() {
        return R.layout.delegate_sort;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {

    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        VerticalListDelegate verticalListDelegate = new VerticalListDelegate();
        loadRootFragment(R.id.vertical_list_container, verticalListDelegate);
        loadRootFragment(R.id.sort_content_container, ContentDelegate.newInstance(1), false, true);
    }
}
