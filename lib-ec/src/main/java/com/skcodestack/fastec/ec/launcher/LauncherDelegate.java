package com.skcodestack.fastec.ec.launcher;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;

import com.skcodestack.fastec.ec.R;
import com.skcodestack.fastec.ec.R2;
import com.skcodestack.stack.app.AccountManager;
import com.skcodestack.stack.app.IUserChecker;
import com.skcodestack.stack.app.Lemon;
import com.skcodestack.stack.delegates.BaseDelegate;
import com.skcodestack.stack.delegates.LemonDelegate;
import com.skcodestack.stack.util.storage.LemonPreference;
import com.skcodestack.stack.util.timer.BaseTimerTask;
import com.skcodestack.stack.util.timer.ITimerListener;

import java.text.MessageFormat;
import java.util.Timer;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Email  1562363326@qq.com
 * Github https://github.com/skcodestack
 * Created by sk on 2018/7/11
 * Version  1.0
 * Description:
 */

public class LauncherDelegate extends LemonDelegate implements ITimerListener {
    @BindView(R2.id.tv_launcher_timer)
    AppCompatTextView mTvTimer = null;

    private int mCount = 5;
    private Timer mTimer = null;
    private ILauncherListener mILauncherListener;

    @OnClick(R2.id.tv_launcher_timer)
    void onClickTimerView() {
        if (mTimer != null) {
            mTimer.cancel();
            mTimer = null;
            checkShowScroll();
        }
    }

    private void initTimer() {
        mTimer = new Timer();
        BaseTimerTask timerTask = new BaseTimerTask(this);
        mTimer.schedule(timerTask, 0, 1000);
    }

    private void checkShowScroll(){
        boolean frist_launcher = LemonPreference.getAppFlag(LauncherTag.APP_FRIST_LAUNCHER_TAG.name());
        if(!frist_launcher){
            start(new LauncherScrollDelegate(),SINGLETASK);
        }else {
            //检查用户是否登陆
            AccountManager.checkAccout(new IUserChecker() {
                @Override
                public void onSignIn() {
                    if(mILauncherListener != null){
                        mILauncherListener.onLauncherFinish(LauncherFinishTag.SIGNED);
                    }
                }

                @Override
                public void onNotSignIn() {
                    if(mILauncherListener != null){
                        mILauncherListener.onLauncherFinish(LauncherFinishTag.NO_SIGNED);
                    }
                }
            });
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if(activity instanceof ILauncherListener){
            mILauncherListener = (ILauncherListener) activity;
        }
    }

    @Override
    public Object getLayout() {
        return R.layout.delegate_launcher;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        initTimer();
    }

    @Override
    public void onTimer() {
        getProxyActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (mTvTimer != null) {
                    mTvTimer.setText(MessageFormat.format("跳过\n{0}s", mCount));
                    mCount--;
                    if (mCount < 0) {
                        if (mTimer != null) {
                            mTimer.cancel();
                            mTimer = null;
                            checkShowScroll();
                        }
                    }
                }
            }
        });
    }
}
