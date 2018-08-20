package com.skcodestack.fastec.ec.main.detail;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.widget.RelativeLayout;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.daimajia.androidanimations.library.YoYo;
import com.joanzapata.iconify.widget.IconTextView;
import com.skcodestack.fastec.ec.R;
import com.skcodestack.fastec.ec.R2;
import com.skcodestack.stack.delegates.LemonDelegate;
import com.skcodestack.stack.net.RestClient;
import com.skcodestack.stack.net.callback.ISuccess;
import com.skcodestack.stack.ui.animation.BezierAnimation;
import com.skcodestack.stack.ui.animation.BezierUtil;
import com.skcodestack.stack.ui.banner.HolderCreator;
import com.skcodestack.stack.ui.widget.CircleTextView;

import org.greenrobot.greendao.annotation.NotNull;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import me.yokeyword.fragmentation.anim.DefaultHorizontalAnimator;
import me.yokeyword.fragmentation.anim.FragmentAnimator;

/**
 * Email  1562363326@qq.com
 * Github https://github.com/skcodestack
 * Created by sk on 2018/7/15
 * Version  1.0
 * Description:
 */

public class GoodsDetailDelegate extends LemonDelegate implements AppBarLayout.OnOffsetChangedListener, BezierUtil.AnimationListener {


    @BindView(R2.id.goods_detail_toolbar)
    Toolbar mToolbar = null;
    @BindView(R2.id.tab_layout)
    TabLayout mTabLayout = null;
    @BindView(R2.id.view_pager)
    ViewPager mViewPager = null;
    @BindView(R2.id.detail_banner)
    ConvenientBanner<String> mBanner = null;
    @BindView(R2.id.collapsing_toolbar_detail)
    CollapsingToolbarLayout mCollapsingToolbarLayout = null;
    @BindView(R2.id.app_bar_detail)
    AppBarLayout mAppBar = null;

    //底部
    @BindView(R2.id.icon_favor)
    IconTextView mIconFavor = null;
    @BindView(R2.id.tv_shopping_cart_amount)
    CircleTextView mCircleTextView = null;
    @BindView(R2.id.rl_add_shop_cart)
    RelativeLayout mRlAddShopCart = null;
    @BindView(R2.id.icon_shop_cart)
    IconTextView mIconShopCart = null;

    private static final String GOOD_ID = "GOOD_ID";
    private int mGoodId;

    private String mGoodsThumbUrl = null;
    private int mGoodsCount  = 0;

    private static final RequestOptions REQUEST_OPTIONS =
            new RequestOptions()
            .dontAnimate()
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .centerCrop()
            .override(100,100);

    @OnClick(R2.id.rl_add_shop_cart)
    void onGoodsPlusClick(){
        CircleImageView imageView = new CircleImageView(getContext());
        Glide.with(this)
                .load(mGoodsThumbUrl)
                .apply(REQUEST_OPTIONS)
                .into(imageView);
        BezierAnimation.addCart(this, mRlAddShopCart, mIconShopCart, imageView, this);
    }

    private void setShopCount(JSONObject data){
        mGoodsThumbUrl =  data.getString("thumb");
        if(mGoodsCount == 0){
            mCircleTextView.setVisibility(View.GONE);
        }
    }

    public static GoodsDetailDelegate create(@NotNull int goodsId) {
        Bundle bundle = new Bundle();
        bundle.putInt(GOOD_ID, goodsId);
        GoodsDetailDelegate delegate = new GoodsDetailDelegate();
        delegate.setArguments(bundle);
        return delegate;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        if (arguments != null) {
            mGoodId = arguments.getInt(GOOD_ID);
        }
    }

    @Override
    public Object getLayout() {
        return R.layout.delegate_goods_detail;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        mCircleTextView.setCircleBackground(Color.RED);
        mCollapsingToolbarLayout.setContentScrimColor(Color.WHITE);
        mAppBar.addOnOffsetChangedListener(this);
        initTabLayout();
        initData();
    }


    private void initData() {
        RestClient.builder()
                .url("goods_detail")
                .params("goodId", mGoodId)
                .loader(getContext())
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        JSONObject data = JSON.parseObject(response).getJSONObject("data");
                        initBanner(data);
                        initGoodsInfo(data);
                        initViewPager(data);
                        setShopCount(data);
                    }
                })
                .build()
                .get();
    }

    private void initViewPager(JSONObject data) {
        TabPagerAdapter pagerAdapter = new TabPagerAdapter(getFragmentManager(), data);
        mViewPager.setAdapter(pagerAdapter);
    }

    private void initTabLayout() {
        //平分
        mTabLayout.setTabMode(TabLayout.MODE_FIXED);
        int color = ContextCompat.getColor(getContext(), R.color.orange_dark);

        mTabLayout.setSelectedTabIndicatorColor(color);
        mTabLayout.setTabTextColors(Color.BLACK, color);
        mTabLayout.setBackgroundColor(Color.WHITE);
        mTabLayout.setupWithViewPager(mViewPager);
    }

    private void initGoodsInfo(JSONObject data) {
        String jsonString = data.toJSONString();
        GoodsInfoDelegate delegate = GoodsInfoDelegate.create(jsonString);
        loadRootFragment(R.id.frame_goods_info, delegate);
    }

    private void initBanner(JSONObject data) {
        ArrayList<String> mBanners = new ArrayList<>();
        JSONArray banners =
                data.getJSONArray("banners");

        int size = banners.size();
        for (int i = 0; i < size; i++) {
            String url = banners.getString(i);
            mBanners.add(url);
        }

        mBanner.setPages(new HolderCreator(getContext()), mBanners)
                .setPageIndicator(new int[]{R.drawable.dot_normal, R.drawable.dot_focus})
                .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL)
                .setCanLoop(true)
                .startTurning(3000);
    }


    //水平动画
    @Override
    public FragmentAnimator onCreateFragmentAnimator() {
        return new DefaultHorizontalAnimator();
    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {

    }

    @Override
    public void onAnimationEnd() {
        YoYo.with(new ScaleUpAnimator())
                .duration(500)
                .playOn(mIconShopCart);
        mGoodsCount++;
        if(mGoodsCount > 0){
            mCircleTextView.setText(String.valueOf(mGoodsCount));
            mCircleTextView.setVisibility(View.VISIBLE);
        }
    }
}
