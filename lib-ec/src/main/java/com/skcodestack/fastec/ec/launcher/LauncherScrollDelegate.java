package com.skcodestack.fastec.ec.launcher;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;
import com.skcodestack.fastec.ec.R;
import com.skcodestack.fastec.ec.launcher.holder.LauncherHolderCreator;
import com.skcodestack.stack.app.AccountManager;
import com.skcodestack.stack.app.IUserChecker;
import com.skcodestack.stack.delegates.LemonDelegate;
import com.skcodestack.stack.util.storage.LemonPreference;

import java.util.ArrayList;

/**
 * Email  1562363326@qq.com
 * Github https://github.com/skcodestack
 * Created by sk on 2018/7/11
 * Version  1.0
 * Description:
 */

public class LauncherScrollDelegate extends LemonDelegate implements OnItemClickListener {

    private ConvenientBanner<Integer> mConvenientBanner;
    private static final ArrayList<Integer> INTEGERS = new ArrayList<>();
    private ILauncherListener mILauncherListener;


    private void initBanners() {
        INTEGERS.clear();
        INTEGERS.add(R.mipmap.launcher_01);
        INTEGERS.add(R.mipmap.launcher_02);
        INTEGERS.add(R.mipmap.launcher_03);
        INTEGERS.add(R.mipmap.launcher_04);
        INTEGERS.add(R.mipmap.launcher_05);

        mConvenientBanner.setPages(new LauncherHolderCreator(), INTEGERS)
                .setPageIndicator(new int[]{R.drawable.dot_normal, R.drawable.dot_focus})
                .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL)
                .setOnItemClickListener(this)
                .setCanLoop(false);
    }

    @Override
    public Object getLayout() {
        mConvenientBanner = new ConvenientBanner<Integer>(getContext());
        initBanners();
        return mConvenientBanner;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {

    }

    @Override
    public void onItemClick(int position) {
        if (position == INTEGERS.size() - 1) {
            //点击的是最后一页
            LemonPreference.setAppFlag(LauncherTag.APP_FRIST_LAUNCHER_TAG.name(), true);
            //用户是否登陆
            checkAccount();
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof ILauncherListener) {
            mILauncherListener = (ILauncherListener) activity;
        }
    }

    private void checkAccount() {
        //检查用户是否登陆
        AccountManager.checkAccout(new IUserChecker() {
            @Override
            public void onSignIn() {
                if (mILauncherListener != null) {
                    mILauncherListener.onLauncherFinish(LauncherFinishTag.SIGNED);
                }
            }

            @Override
            public void onNotSignIn() {
                if (mILauncherListener != null) {
                    mILauncherListener.onLauncherFinish(LauncherFinishTag.NO_SIGNED);
                }
            }
        });

    }
}
