package com.skcodestack.stack.wechat;

import android.app.Activity;

import com.skcodestack.stack.app.ConfigType;
import com.skcodestack.stack.app.Lemon;
import com.skcodestack.stack.wechat.callbacks.IWeChatSignInCallback;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import retrofit2.http.PUT;

/**
 * Email  1562363326@qq.com
 * Github https://github.com/skcodestack
 * Created by sk on 2018/7/12
 * Version  1.0
 * Description:
 */

public class LemonWeChat {
    static final String APP_ID = Lemon.getConfigurator().getConfiguration(ConfigType.WX_CHAT_APP_ID);
    static final String APP_SECRET = Lemon.getConfigurator().getConfiguration(ConfigType.WX_CHAT_APP_SECRET);
    private final IWXAPI WXAPI;
    private IWeChatSignInCallback mCallback;


    private LemonWeChat(){
        Activity activity = Lemon.getConfigurator().getConfiguration(ConfigType.ACTIVITY);
        WXAPI = WXAPIFactory.createWXAPI(activity,APP_ID,true);
        WXAPI.registerApp(APP_ID);
    }

    public static final class Holder{
        private static final LemonWeChat WE_CHAT = new LemonWeChat();
    }


    public LemonWeChat onSignInSuccess(IWeChatSignInCallback callback){
        this.mCallback = callback;
        return this;
    }

    public IWeChatSignInCallback getSignInCallback(){
        return mCallback;
    }

    public static LemonWeChat getInstance(){
        return Holder.WE_CHAT;
    }

    public final IWXAPI getWXAPI(){
        return WXAPI;
    }

    public final void signIn(){
        SendAuth.Req req = new SendAuth.Req();
        req.state = "random_state";
        req.scope="snsapi_userinfo";
        WXAPI.sendReq(req);
    }

}
