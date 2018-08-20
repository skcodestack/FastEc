package com.skcodestack.fastec.ec.main.index;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.URLUtil;
import android.widget.Toast;

import com.joanzapata.iconify.widget.IconTextView;
import com.skcodestack.fastec.ec.R;
import com.skcodestack.fastec.ec.R2;
import com.skcodestack.fastec.ec.main.EcBottomDelegate;
import com.skcodestack.fastec.ec.main.index.search.SearchDelegate;
import com.skcodestack.stack.delegates.LemonDelegate;
import com.skcodestack.stack.delegates.bottom.BottomItemDelegate;
import com.skcodestack.stack.delegates.web.WebDelegateImpl;
import com.skcodestack.stack.ui.recycler.BaseDecoration;
import com.skcodestack.stack.ui.refresh.RefreshHandler;
import com.skcodestack.stack.util.callback.CallbackManager;
import com.skcodestack.stack.util.callback.CallbackType;
import com.skcodestack.stack.util.callback.IGlobalCallback;

import butterknife.BindView;
import butterknife.OnClick;
import me.yokeyword.fragmentation.SupportFragmentDelegate;

/**
 * Email  1562363326@qq.com
 * Github https://github.com/skcodestack
 * Created by sk on 2018/7/13
 * Version  1.0
 * Description:
 */

public class IndexDelegate extends BottomItemDelegate implements View.OnFocusChangeListener {

    @BindView(R2.id.rv_index)
    RecyclerView mRecyclerView;
    @BindView(R2.id.sr_index)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @BindView(R2.id.tb_index)
    Toolbar mToolbar;
    @BindView(R2.id.icon_index_scan)
    IconTextView mIconScan;
    @BindView(R2.id.et_search_view)
    AppCompatEditText mSearchView;

    @OnClick(R2.id.icon_index_scan)
    void onScanClick() {
        getParentDelegate().starScan();
    }


    private RefreshHandler mRefreshHandler;

    private void initRefreshLayout() {
        //设置颜色
        mSwipeRefreshLayout.setColorSchemeResources(android.R.color.holo_orange_light, android.R.color.holo_red_light, android.R.color.holo_blue_light);
        //图标
        mSwipeRefreshLayout.setProgressViewOffset(true, 120, 300);
    }

    private void initRecyclerView() {
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 4);
        mRecyclerView.setLayoutManager(layoutManager);

        mRecyclerView.addItemDecoration(BaseDecoration.create(ContextCompat.getColor(getContext(), R.color.app_background), 5));

        EcBottomDelegate ecBottomDelegate = getParentDelegate();
        mRecyclerView.addOnItemTouchListener(IndexItemClickListener.create(ecBottomDelegate));
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        initRefreshLayout();
        initRecyclerView();
        mRefreshHandler.firstPage("index.php");
    }

    @Override
    public Object getLayout() {
        return R.layout.delegate_index;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        mRefreshHandler = RefreshHandler.create(mSwipeRefreshLayout, mRecyclerView, new IndexDataConvert());

        CallbackManager.getInstance().addCallback(CallbackType.ON_SCAN, new IGlobalCallback<String>() {
            @Override
            public void callback(@Nullable String result) {
                if (URLUtil.isNetworkUrl(result)) {
                    //跳转
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(result));
                    startActivity(intent);
                } else {
                    Toast.makeText(getContext(), "扫描内容：" + result, Toast.LENGTH_SHORT).show();
                }
            }
        });
        mSearchView.setOnFocusChangeListener(this);
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if(hasFocus){
            getParentDelegate().start(new SearchDelegate());
        }

    }
}
