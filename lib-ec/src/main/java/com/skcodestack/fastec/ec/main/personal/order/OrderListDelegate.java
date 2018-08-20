package com.skcodestack.fastec.ec.main.personal.order;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.skcodestack.fastec.ec.R;
import com.skcodestack.fastec.ec.R2;
import com.skcodestack.fastec.ec.main.personal.PersonalDelegate;
import com.skcodestack.stack.delegates.LemonDelegate;
import com.skcodestack.stack.net.RestClient;
import com.skcodestack.stack.net.callback.ISuccess;
import com.skcodestack.stack.ui.recycler.MutipleItemEntity;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * Email  1562363326@qq.com
 * Github https://github.com/skcodestack
 * Created by sk on 2018/7/19
 * Version  1.0
 * Description:
 */

public class OrderListDelegate extends LemonDelegate {
    private String mOrderType;
    @BindView(R2.id.rv_order_list)
    RecyclerView mRecyclerView;
    public static final String ORDER_TYPE = "ORDER_TYPE";

    @Override
    public Object getLayout() {
        return R.layout.delegate_order_list;
    }


    public static OrderListDelegate create(String order_type) {
        Bundle bundle = new Bundle();
        bundle.putString(ORDER_TYPE, order_type);
        OrderListDelegate delegate = new OrderListDelegate();
        delegate.setArguments(bundle);
        return delegate;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        mOrderType = arguments.getString(ORDER_TYPE);
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {

    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        RestClient.builder()
                .url("order_list_" + mOrderType)
                .loader(getContext())
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
                        mRecyclerView.setLayoutManager(layoutManager);
                        ArrayList<MutipleItemEntity> data =
                                new OrderListDataConverter().setJsonData(response).convert();
                        OrderListAdapter mAdapter = new OrderListAdapter(data);
                        mRecyclerView.setAdapter(mAdapter);
                        mRecyclerView.addOnItemTouchListener(new OrderListOnClickListener(OrderListDelegate.this));
                    }
                })
                .build()
                .get();
    }
}
