package com.skcodestack.fastec.ec.main.sort.list;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.skcodestack.fastec.ec.R;
import com.skcodestack.fastec.ec.R2;
import com.skcodestack.fastec.ec.main.sort.SortDelegate;
import com.skcodestack.stack.delegates.LemonDelegate;
import com.skcodestack.stack.net.RestClient;
import com.skcodestack.stack.net.callback.ISuccess;
import com.skcodestack.stack.ui.recycler.MutipleItemEntity;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * Email  1562363326@qq.com
 * Github https://github.com/skcodestack
 * Created by sk on 2018/7/15
 * Version  1.0
 * Description:
 */

public class VerticalListDelegate extends LemonDelegate {

    @BindView(R2.id.rv_vitical_list)
    RecyclerView mRecyclerView;

    private void initRecyclerView(){
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(layoutManager);

        mRecyclerView.setItemAnimator(null);
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        RestClient.builder()
                .url("sort_list")
                .loader(getContext())
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        ArrayList<MutipleItemEntity> itemEntities =
                                new VerticalListDataConverter().setJsonData(response).convert();
                        SortDelegate sortDelegate = getParentDelegate();
                        SortRecyclerAdapter mAdapter = new SortRecyclerAdapter(itemEntities,sortDelegate);
                        mRecyclerView.setAdapter(mAdapter);
                    }
                })
                .build()
                .get();
    }

    @Override
    public Object getLayout() {
        return R.layout.delegate_vertical_list;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        initRecyclerView();
    }
}
