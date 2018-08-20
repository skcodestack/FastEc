package com.skcodestack.stack.ui.refresh;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.skcodestack.stack.app.ConfigType;
import com.skcodestack.stack.app.Lemon;
import com.skcodestack.stack.delegates.LemonDelegate;
import com.skcodestack.stack.net.RestClient;
import com.skcodestack.stack.net.callback.ISuccess;
import com.skcodestack.stack.ui.recycler.DataConverter;
import com.skcodestack.stack.ui.recycler.MutipleItemEntity;
import com.skcodestack.stack.ui.recycler.MutipleRecyclerAdapter;

import java.util.List;

/**
 * Email  1562363326@qq.com
 * Github https://github.com/skcodestack
 * Created by sk on 2018/7/13
 * Version  1.0
 * Description:
 */

public class RefreshHandler implements SwipeRefreshLayout.OnRefreshListener,
        BaseQuickAdapter.RequestLoadMoreListener {

    private static final long DELAY_TIMES = 2000;
    private final SwipeRefreshLayout mSwipeRefreshLayout;
    private final PageBean PAGEBEAN;
    private final RecyclerView RECYCLERVIEW;
    private MutipleRecyclerAdapter mAdapter;
    private final DataConverter CONVERTER;


    private RefreshHandler(SwipeRefreshLayout mSwipeRefreshLayout, RecyclerView recyclerView, DataConverter converter, PageBean pageBean) {
        this.mSwipeRefreshLayout = mSwipeRefreshLayout;
        this.CONVERTER = converter;
        this.PAGEBEAN = pageBean;
        this.RECYCLERVIEW = recyclerView;
        mSwipeRefreshLayout.setOnRefreshListener(this);
    }

    public static RefreshHandler create(SwipeRefreshLayout mSwipeRefreshLayout, RecyclerView recyclerView, DataConverter converter) {
        return new RefreshHandler(mSwipeRefreshLayout, recyclerView, converter, new PageBean());
    }

    public void refresh() {
        mSwipeRefreshLayout.setRefreshing(true);
        Lemon.getHandler().postDelayed(new Runnable() {
            @Override
            public void run() {
                firstPage("index");
            }
        }, DELAY_TIMES);
    }

    public void firstPage(String url) {
        PAGEBEAN.setDelayed(1000);
        PAGEBEAN.setCurrentPage(0);
        RestClient.builder()
                .url(url)
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        mSwipeRefreshLayout.setRefreshing(false);
                        JSONObject jsonObject = JSON.parseObject(response);

                        mAdapter = MutipleRecyclerAdapter.create(CONVERTER.setJsonData(response));
                        mAdapter.setOnLoadMoreListener(RefreshHandler.this, RECYCLERVIEW);
                        RECYCLERVIEW.setAdapter(mAdapter);
                        PAGEBEAN.addIndex();
                        PAGEBEAN.setPageSize(jsonObject.getInteger("page_size"))
                                .setTotal(jsonObject.getInteger("total"))
                                .setCurrentCount(mAdapter.getData().size());
                    }
                })
                .build()
                .get();
    }

    @Override
    public void onRefresh() {
        refresh();
    }


    private void paging(final String url) {
        int pageSize = PAGEBEAN.getPageSize();
        int total = PAGEBEAN.getTotal();
        int currentCount = PAGEBEAN.getCurrentCount();
        final int currentPage = PAGEBEAN.getCurrentPage();
        List<MutipleItemEntity> data = mAdapter.getData();
        if (data.size() < pageSize || currentCount >= total) {
            mAdapter.loadMoreEnd(true);
        } else {
            Lemon.getHandler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    RestClient.builder()
                            .url(url + currentPage)
                            .success(new ISuccess() {
                                @Override
                                public void onSuccess(String response) {
                                    mAdapter.addData(CONVERTER.setJsonData(response).convert());
                                    PAGEBEAN.setCurrentCount(mAdapter.getData().size());
                                    PAGEBEAN.addIndex();
                                    mAdapter.loadMoreComplete();
                                }
                            })
                            .build()
                            .get();

                }
            }, DELAY_TIMES);
        }
    }


    @Override
    public void onLoadMoreRequested() {
        paging("index_1");
    }
}
