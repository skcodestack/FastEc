package com.skcodestack.fastec;

import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.widget.Toast;

import com.skcodestack.fastec.ec.launcher.ILauncherListener;
import com.skcodestack.fastec.ec.launcher.LauncherDelegate;
import com.skcodestack.fastec.ec.launcher.LauncherFinishTag;
import com.skcodestack.fastec.ec.main.EcBottomDelegate;
import com.skcodestack.fastec.ec.sign.ISMSCallback;
import com.skcodestack.fastec.ec.sign.ISMSVertification;
import com.skcodestack.fastec.ec.sign.ISignListener;
import com.skcodestack.fastec.ec.sign.SignInDelegate;
import com.skcodestack.stack.activities.ProxyActivity;
import com.skcodestack.stack.app.Lemon;

import java.util.HashMap;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import cn.smssdk.gui.RegisterPage;
import me.yokeyword.fragmentation.SupportFragment;
import qiu.niorgai.StatusBarCompat;

public class ExampleActivity extends ProxyActivity implements
        ISignListener, ILauncherListener ,ISMSVertification {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        Lemon.getConfigurator().withActivity(this);
        StatusBarCompat.translucentStatusBar(this, true);
    }

    @Override
    public SupportFragment getRootDelegate() {
        return new LauncherDelegate();
    }

    @Override
    public void onSignInSuccess() {
        Toast.makeText(this, "登陆成功", Toast.LENGTH_SHORT).show();
        startWithPop(new EcBottomDelegate());
    }

    @Override
    public void onSignUpSuccess() {
        Toast.makeText(this, "注册成功", Toast.LENGTH_SHORT).show();
        startWithPop(new SignInDelegate());
    }

    @Override
    public void onLauncherFinish(LauncherFinishTag tag) {
        switch (tag) {
            case SIGNED:
                Toast.makeText(this, "登录", Toast.LENGTH_SHORT).show();
                startWithPop(new EcBottomDelegate());
                break;
            case NO_SIGNED:
                Toast.makeText(this, "没登陆", Toast.LENGTH_SHORT).show();
                startWithPop(new SignInDelegate());
                break;
            default:
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Lemon.getConfigurator().withActivity(null);
    }

    public void sendCode(final ISMSCallback callback) {
        RegisterPage page = new RegisterPage();
        //如果使用我们的ui，没有申请模板编号的情况下需传null
        page.setTempCode(null);
        page.setRegisterCallback(new EventHandler() {
            public void afterEvent(int event, int result, Object data) {
                if (result == SMSSDK.RESULT_COMPLETE) {
                    // 处理成功的结果
                    HashMap<String, Object> phoneMap = (HashMap<String, Object>) data;
                    String country = (String) phoneMap.get("country"); // 国家代码，如“86”
                    String phone = (String) phoneMap.get("phone"); // 手机号码，如“13800138000”
                    // TODO 利用国家代码和手机号码进行后续的操作
                    Toast.makeText(ExampleActivity.this, "短信登陆成功", Toast.LENGTH_SHORT).show();
                    if (callback != null) {
                        callback.onSuccess(country, phone);
                    }
                } else {
                    // TODO 处理错误的结果
                    Toast.makeText(ExampleActivity.this, "短信登陆失败", Toast.LENGTH_SHORT).show();
                    if (callback != null) {
                        callback.onError();
                    }
                }
            }
        });
        page.show(this);
    }

    @Override
    public void vertif(ISMSCallback callback) {
        sendCode(callback);
    }
}
