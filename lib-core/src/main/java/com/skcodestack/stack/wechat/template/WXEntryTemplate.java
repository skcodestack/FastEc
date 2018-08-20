package com.skcodestack.stack.wechat.template;

import com.skcodestack.stack.activities.ProxyActivity;
import com.skcodestack.stack.wechat.BaseWXEntryActivity;
import com.skcodestack.stack.wechat.LemonWeChat;

import me.yokeyword.fragmentation.SupportFragment;

/**
 * Email  1562363326@qq.com
 * Github https://github.com/skcodestack
 * Created by sk on 2018/7/12
 * Version  1.0
 * Description:
 */

public class WXEntryTemplate extends BaseWXEntryActivity {

    /**
     * 登陆回调会显示这个页面，所以要关闭，直接进入自己的应用界面
     */
    @Override
    protected void onResume() {
        super.onResume();
        finish();
        overridePendingTransition(0,0);
    }

    @Override
    protected void onSignInSuccess(String userInfo) {
        LemonWeChat.getInstance().getSignInCallback().onSignInSuccess(userInfo);
    }
}
