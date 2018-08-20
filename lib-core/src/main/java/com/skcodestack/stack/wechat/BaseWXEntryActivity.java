package com.skcodestack.stack.wechat;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.skcodestack.stack.net.RestClient;
import com.skcodestack.stack.net.callback.IFailure;
import com.skcodestack.stack.net.callback.ISuccess;
import com.skcodestack.stack.ui.loader.LemonLoader;
import com.skcodestack.stack.util.log.LemonLogger;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;

/**
 * Email  1562363326@qq.com
 * Github https://github.com/skcodestack
 * Created by sk on 2018/7/12
 * Version  1.0
 * Description:
 */

public abstract class BaseWXEntryActivity extends BaseWXActivity {
    /*
    *检验授权凭证（access_token）是否有效
     * http请求方式: GET
      https://api.weixin.qq.com/sns/auth?access_token=ACCESS_TOKEN&openid=OPENID
    * */


    //用户登陆成功后回调
    protected abstract void onSignInSuccess(String userInfo);

    //微信发送请求到第三方回调
    @Override
    public void onReq(BaseReq baseReq) {

    }

    //第三方发送请求到微信回调
    @Override
    public void onResp(BaseResp baseResp) {
        final String code = ((SendAuth.Resp) baseResp).code;
        StringBuilder authUrl = new StringBuilder();
        authUrl.append("https://api.weixin.qq.com/sns/oauth2/access_token?appid=")
                .append(LemonWeChat.APP_ID)
                .append("&secret=")
                .append(LemonWeChat.APP_SECRET)
                .append("&code=")
                .append(code)
                .append("&grant_type=authorization_code");

        LemonLogger.d("auth", authUrl);
        getAuth(authUrl.toString());
    }

    /**
     * 通过code获取access_token的接口。
     * http请求方式: GET
     * https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code
     *
     * @param url
     */
    protected void getAuth(String url) {

        RestClient.builder()
                .url(url)
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        /*
                        * {
                            "access_token":"ACCESS_TOKEN",
                            "expires_in":7200,
                            "refresh_token":"REFRESH_TOKEN",
                            "openid":"OPENID",
                            "scope":"SCOPE"
                            }
                        * */
                        final JSONObject authObj = JSON.parseObject(response);
                        final String accessToken = authObj.getString("access_token");
                        final String openId = authObj.getString("openid");

                        final StringBuilder userInfoUrl = new StringBuilder();
                        userInfoUrl
                                .append("https://api.weixin.qq.com/sns/userinfo?access_token=")
                                .append(accessToken)
                                .append("&openid=")
                                .append(openId)
                                .append("&lang=")
                                .append("zh_CN");

                        LemonLogger.d("userInfoUrl", userInfoUrl.toString());
                        getUserInfo(userInfoUrl.toString());
                    }


                })
                .build()
                .get();

    }

    /**
     * 获取用户个人信息（UnionID机制）
     * http请求方式: GET
     * https://api.weixin.qq.com/sns/userinfo?access_token=ACCESS_TOKEN&openid=OPENID
     *
     * @param url
     */
    private void getUserInfo(String url) {
        RestClient.builder()
                .url(url)
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        /*
                        * {
                            "errcode":0,"errmsg":"ok"
                           }
                        * */
                        onSignInSuccess(response);
                    }
                })
                .failure(new IFailure() {
                    @Override
                    public void onFailure() {

                    }
                })
                .build().get();

    }
}
