package com.skcodestack.fastec.ec.main.index.search;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.alibaba.fastjson.JSON;
import com.blankj.utilcode.util.StringUtils;
import com.choices.divider.Divider;
import com.choices.divider.DividerItemDecoration;
import com.skcodestack.fastec.ec.R;
import com.skcodestack.fastec.ec.R2;
import com.skcodestack.stack.delegates.LemonDelegate;
import com.skcodestack.stack.ui.recycler.DividerLookupImpl;
import com.skcodestack.stack.ui.recycler.MutipleItemEntity;
import com.skcodestack.stack.util.log.LemonLogger;
import com.skcodestack.stack.util.storage.LemonPreference;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Email  1562363326@qq.com
 * Github https://github.com/skcodestack
 * Created by sk on 2018/7/24
 * Version  1.0
 * Description:
 */

public class SearchDelegate extends LemonDelegate {

    @BindView(R2.id.et_search_view)
    AppCompatEditText mSearchEdit;

    @BindView(R2.id.rv_search)
    RecyclerView mRecyclerView;

    @OnClick(R2.id.tv_top_search)
    void onClickSearch() {
        String searchText = mSearchEdit.getText().toString();
        saveItem(searchText);
    }

    @OnClick(R2.id.icon_top_search_back)
    void onClickBack() {
        getSupportDelegate().pop();
    }


    private void saveItem(String searchText) {
        if (!StringUtils.isEmpty(searchText) && !StringUtils.isSpace(searchText)) {
            List<String> list;
            String history = LemonPreference.getCustomAppProfile(SearchDataConverter.SEARCH_FLAG);
            LemonLogger.e("search delegate",history);
            if (StringUtils.isEmpty(history)) {
                list = new ArrayList<>();
            } else {
                //noinspection unchecked
                list = JSON.parseObject(history, ArrayList.class);
            }
            if(list.contains(searchText)) {
                list.remove(searchText);
            }
            list.add(0,searchText);
            String reJson = JSON.toJSONString(list);
            LemonPreference.addCustomAppProfile(SearchDataConverter.SEARCH_FLAG, reJson);
        }
    }


    @Override
    public Object getLayout() {
        return R.layout.delegate_search;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(layoutManager);
        ArrayList<MutipleItemEntity> entities = new SearchDataConverter().convert();
        SearchAdapter mAdapter = new SearchAdapter(entities);
        mRecyclerView.setAdapter(mAdapter);

        DividerItemDecoration decoration = new DividerItemDecoration();
        decoration.setDividerLookup(new DividerItemDecoration.DividerLookup() {
            @Override
            public Divider getVerticalDivider(int position) {
                return null;
            }

            @Override
            public Divider getHorizontalDivider(int position) {
                return new Divider.Builder()
                        .color(Color.GRAY)
                        .size(2)
                        .build();
            }
        });

        mRecyclerView.addItemDecoration(decoration);
    }
}
