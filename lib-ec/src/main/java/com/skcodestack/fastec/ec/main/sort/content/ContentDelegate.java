package com.skcodestack.fastec.ec.main.sort.content;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

import com.skcodestack.fastec.ec.R;
import com.skcodestack.fastec.ec.R2;
import com.skcodestack.stack.delegates.LemonDelegate;
import com.skcodestack.stack.net.RestClient;
import com.skcodestack.stack.net.callback.ISuccess;

import java.util.List;

import butterknife.BindView;

/**
 * Email  1562363326@qq.com
 * Github https://github.com/skcodestack
 * Created by sk on 2018/7/15
 * Version  1.0
 * Description:
 */

public class ContentDelegate extends LemonDelegate {

    private static final String CONTENT_ID = "CONTENT_ID";
    private int mContentId = -1;


    @BindView(R2.id.rv_list_content)
    RecyclerView mRecyclerView;

    public static ContentDelegate newInstance(int contentId) {
        Bundle args = new Bundle();
        args.putInt(CONTENT_ID, contentId);
        ContentDelegate delegate = new ContentDelegate();
        delegate.setArguments(args);
        return delegate;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        if (arguments != null) {
            mContentId = arguments.getInt(CONTENT_ID);
        }
    }

    @Override
    public Object getLayout() {
        return R.layout.delegate_list_content;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        final StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
        initData();
    }

    private void initData(){
        RestClient.builder()
                .url("content_1")
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        List<SectionItemEntity> datas = new SectionDataConverter().convert(response);
                        SectionRecyclerAdapter mAdapter = new SectionRecyclerAdapter(R.layout.item_section_content,R.layout.item_section_header,datas);
                        mRecyclerView.setAdapter(mAdapter);
                    }
                })
                .build()
                .get();
    }
}
