package com.skcodestack.fastec.ec.main.personal;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.skcodestack.fastec.ec.R;
import com.skcodestack.fastec.ec.R2;
import com.skcodestack.fastec.ec.main.personal.adapter.ListAdapter;
import com.skcodestack.fastec.ec.main.personal.adapter.ListBean;
import com.skcodestack.fastec.ec.main.personal.adapter.ListItemType;
import com.skcodestack.fastec.ec.main.personal.address.AddressDelegate;
import com.skcodestack.fastec.ec.main.personal.order.OrderListDelegate;
import com.skcodestack.fastec.ec.main.personal.profile.ProfileDelegate;
import com.skcodestack.fastec.ec.main.personal.profile.UserPofileOnClickListener;
import com.skcodestack.fastec.ec.main.personal.setting.SettingDelegate;
import com.skcodestack.stack.delegates.bottom.BottomItemDelegate;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Email  1562363326@qq.com
 * Github https://github.com/skcodestack
 * Created by sk on 2018/7/19
 * Version  1.0
 * Description:
 */

public class PersonalDelegate extends BottomItemDelegate {

    @BindView(R2.id.rv_personal_setting)
    RecyclerView mRecyclerView;
    private List<ListBean> mData;

    @OnClick(R2.id.tv_all_order)
    void onClickAllOrder() {
        startOrderListDelegate("all");
    }

    @OnClick(R2.id.iv_user_avatar)
    void onClickAvatar(){
        ProfileDelegate delegate = new ProfileDelegate();
        getParentDelegate().start(delegate);
    }
    /**
     * 跳转
     *
     * @param orderType
     */
    private void startOrderListDelegate(String orderType) {
        OrderListDelegate delegate = OrderListDelegate.create(orderType);
        getParentDelegate().start(delegate);
    }

    @Override
    public Object getLayout() {
        return R.layout.delegate_personal;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        ListBean address = new ListBean.Builder()
                .setItemType(ListItemType.ITEM_TYPE_ARROW)
                .setId(1)
                .setText("收货地址")
                .setDelegate(AddressDelegate.create())
                .build();

        ListBean system = new ListBean.Builder()
                .setItemType(ListItemType.ITEM_TYPE_ARROW)
                .setId(2)
                .setText("系统设置")
                .setDelegate(new SettingDelegate())
                .build();

        mData = new ArrayList<>();
        mData.add(address);
        mData.add(system);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(layoutManager);
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        ListAdapter mAdapter = new ListAdapter(mData);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addOnItemTouchListener(new PersonOnClickListener(this));
    }
}
