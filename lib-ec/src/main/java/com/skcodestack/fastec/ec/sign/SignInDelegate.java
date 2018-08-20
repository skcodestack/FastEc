package com.skcodestack.fastec.ec.sign;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.util.Patterns;
import android.view.View;

import com.skcodestack.fastec.ec.R;
import com.skcodestack.fastec.ec.R2;
import com.skcodestack.stack.activities.ProxyActivity;
import com.skcodestack.stack.delegates.LemonDelegate;
import com.skcodestack.stack.net.RestClient;
import com.skcodestack.stack.net.callback.ISuccess;
import com.skcodestack.stack.wechat.LemonWeChat;
import com.skcodestack.stack.wechat.callbacks.IWeChatSignInCallback;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Email  1562363326@qq.com
 * Github https://github.com/skcodestack
 * Created by sk on 2018/7/11
 * Version  1.0
 * Description:
 */

public class SignInDelegate extends LemonDelegate {

    @BindView(R2.id.edit_sign_in_email)
    TextInputEditText mEmailEdit;
    @BindView(R2.id.edit_sign_in_password)
    TextInputEditText mPasswordEdit;
    private ISignListener mISignListener;
    private ISMSVertification mISMSVertifListener;

    @OnClick(R2.id.btn_sign_in)
    void onClickSignIn() {
        if (checkFrom()) {
            RestClient.builder()
                    .url("login.index")
                    .success(new ISuccess() {
                        @Override
                        public void onSuccess(String response) {
                            SignHandler.onSignIn(response, mISignListener);
                        }
                    })
                    .build().get();

        }
    }

    @OnClick(R2.id.icon_sign_in_phone)
    void onSMSSignIn(){
        if(mISMSVertifListener != null){
            mISMSVertifListener.vertif(new ISMSCallback() {
                @Override
                public void onSuccess(String code, String phone) {
                    RestClient.builder()
                            .url("login.index")
                            .success(new ISuccess() {
                                @Override
                                public void onSuccess(String response) {
                                    SignHandler.onSignIn(response, mISignListener);
                                }
                            })
                            .build().get();
                }

                @Override
                public void onError() {

                }
            });
        }
    }

    @OnClick(R2.id.tv_link_sign_in)
    void onClickLink() {
        start(new SignUpDelegate());
    }

    @OnClick(R2.id.icon_sign_in_wechat)
    void onClickWeChat() {
        LemonWeChat.getInstance().onSignInSuccess(new IWeChatSignInCallback() {
            @Override
            public void onSignInSuccess(String userInfo) {
                //根据微信信息注册，登陆成功
//              SignHandler.onSignIn(userInfo,mISignListener);
            }
        }).signIn();
    }

    private boolean checkFrom() {
        boolean isPass = true;
        String email = mEmailEdit.getText().toString();
        String password = mPasswordEdit.getText().toString();


        if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            mEmailEdit.setError("错误的邮箱格式");
            isPass = false;
        } else
            mEmailEdit.setError(null);


        if (password.isEmpty() || password.length() < 6) {
            mPasswordEdit.setError("错误的密码格式");
            isPass = false;
        } else
            mPasswordEdit.setError(null);

        return isPass;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof ISignListener) {
            mISignListener = (ISignListener) activity;
        }
        if(activity instanceof ISMSVertification){
            mISMSVertifListener = (ISMSVertification) activity;
        }
    }

    @Override
    public Object getLayout() {
        return R.layout.delegate_sign_in;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {

    }
}
