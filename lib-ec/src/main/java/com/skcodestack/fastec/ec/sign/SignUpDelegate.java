package com.skcodestack.fastec.ec.sign;

import android.app.Activity;
import android.os.Bundle;
import android.os.PatternMatcher;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;

import com.skcodestack.fastec.ec.R;
import com.skcodestack.fastec.ec.R2;
import com.skcodestack.stack.activities.ProxyActivity;
import com.skcodestack.stack.delegates.LemonDelegate;
import com.skcodestack.stack.net.RestClient;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Email  1562363326@qq.com
 * Github https://github.com/skcodestack
 * Created by sk on 2018/7/11
 * Version  1.0
 * Description:
 */

public class SignUpDelegate extends LemonDelegate {
    @BindView(R2.id.edit_sign_up_name)
    TextInputEditText mNameEdit;
    @BindView(R2.id.edit_sign_up_email)
    TextInputEditText mEmailEdit;
    @BindView(R2.id.edit_sign_up_phone)
    TextInputEditText mPhoneEdit;
    @BindView(R2.id.edit_sign_up_password)
    TextInputEditText mPasswordEdit;
    @BindView(R2.id.edit_sign_up_re_password)
    TextInputEditText mRePasswordEdit;
    private ISignListener mISignListener;

    @OnClick(R2.id.btn_sign_up)
    void onClickSignUp() {
        if (checkFrom()) {
//            RestClient.builder().build().post();
            SignHandler.onSignUp("",mISignListener);

        }
    }

    @OnClick(R2.id.tv_link_sign_up)
    void onClickLink() {
        start(new SignInDelegate());
    }

    private boolean checkFrom() {
        boolean isPass = true;
        String name = mNameEdit.getText().toString();
        String email = mEmailEdit.getText().toString();
        String phone = mPhoneEdit.getText().toString();
        String password = mPasswordEdit.getText().toString();
        String re_password = mRePasswordEdit.getText().toString();

        if (name.isEmpty()) {
            mNameEdit.setError("请输入姓名");
            isPass = false;
        } else
            mNameEdit.setError(null);

        if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            mEmailEdit.setError("错误的邮箱格式");
            isPass = false;
        } else
            mEmailEdit.setError(null);

        if (phone.isEmpty() || !Patterns.PHONE.matcher(phone).matches()) {
            mPhoneEdit.setError("错误的电话格式");
            isPass = false;
        } else
            mPhoneEdit.setError(null);

        if (password.isEmpty() || password.length() < 6) {
            mPasswordEdit.setError("错误的密码格式");
            isPass = false;
        } else
            mPasswordEdit.setError(null);

        if (re_password.isEmpty() || re_password.length() < 6 || !re_password.equals(password)) {
            mRePasswordEdit.setError("错误的密码格式");
            isPass = false;
        } else
            mRePasswordEdit.setError(null);

        return isPass;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if(activity instanceof ISignListener){
            mISignListener = (ISignListener) activity;
        }
    }

    @Override
    public Object getLayout() {
        return R.layout.delegate_sign_up;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {

    }
}
