package com.skcodestack.fastec.ec.main.personal.address;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.skcodestack.fastec.ec.R;
import com.skcodestack.fastec.ec.R2;
import com.skcodestack.stack.delegates.LemonDelegate;
import com.skcodestack.stack.net.RestClient;
import com.skcodestack.stack.net.callback.ISuccess;
import com.skcodestack.stack.ui.recycler.MutipleItemEntity;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Email  1562363326@qq.com
 * Github https://github.com/skcodestack
 * Created by sk on 2018/7/20
 * Version  1.0
 * Description:
 */

public class AddressDelegate extends LemonDelegate {

    @BindView(R2.id.rv_address)
    RecyclerView mRecyclerView;

    @OnClick(R2.id.icon_address_add)
    void onClickAdd() {


    }

    public static final AddressDelegate create(){
        return new AddressDelegate();
    }

    @Override
    public Object getLayout() {
        return R.layout.delegate_address_layout;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {

    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        RestClient.builder()
                .url("address")
                .loader(getContext())
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        ArrayList<MutipleItemEntity> mData = new AddressDataConverter().setJsonData(response).convert();
                        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
                        mRecyclerView.setLayoutManager(layoutManager);

                        AddressAdapter mAdapter = new AddressAdapter(mData);
                        mRecyclerView.setAdapter(mAdapter);
                    }
                })
                .build()
                .get();
    }
}
