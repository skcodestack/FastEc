package com.skcodestack.fastec.ec.pay;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.skcodestack.fastec.ec.R;
import com.skcodestack.stack.app.ConfigType;
import com.skcodestack.stack.app.Lemon;
import com.skcodestack.stack.delegates.LemonDelegate;
import com.skcodestack.stack.net.RestClient;
import com.skcodestack.stack.net.callback.ISuccess;
import com.skcodestack.stack.ui.loader.LemonLoader;
import com.skcodestack.stack.wechat.LemonWeChat;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;

/**
 * Email  1562363326@qq.com
 * Github https://github.com/skcodestack
 * Created by sk on 2018/7/18
 * Version  1.0
 * Description:
 */

public class FastPay implements View.OnClickListener {
    private IAliPayResultListener mAliPayResultListener = null;

    private Activity mActivity;
    private AlertDialog mAlertDialog;
    private int mOrderId = -1;

    private FastPay(LemonDelegate delegate) {
        this.mActivity = delegate.getProxyActivity();
        this.mAlertDialog = new AlertDialog.Builder(delegate.getContext()).create();
    }

    public static FastPay create(LemonDelegate delegate) {
        return new FastPay(delegate);
    }


    public FastPay setAliPayResultListener(IAliPayResultListener listener) {
        mAliPayResultListener = listener;
        return this;
    }

    public FastPay setOrderId(int orderId) {
        mOrderId = orderId;
        return this;
    }

    public void beginPayDialog() {
        mAlertDialog.show();
        Window window =
                mAlertDialog.getWindow();
        if (window != null) {
            window.setContentView(R.layout.dialog_pay_panle);
            window.setGravity(Gravity.BOTTOM);
            window.setWindowAnimations(R.style.anim_up_from_bottom);
            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

            WindowManager.LayoutParams layoutParams =
                    window.getAttributes();
            layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
            layoutParams.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
            window.setAttributes(layoutParams);

            window.findViewById(R.id.icon_ali_pay).setOnClickListener(this);
            window.findViewById(R.id.icon_weixin_pay).setOnClickListener(this);
            window.findViewById(R.id.btn_dialog_pay_cancle).setOnClickListener(this);
        }

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.icon_ali_pay) {
            onAliPay(mOrderId);
            mAlertDialog.cancel();
        } else if (id == R.id.icon_weixin_pay) {
            onWeChatPay(mOrderId);
            mAlertDialog.cancel();
        } else if (id == R.id.btn_dialog_pay_cancle) {
            mAlertDialog.cancel();
        }
    }

    private void onWeChatPay(int orderId) {
        LemonLoader.stopLoading();
        String weChatUrl = "你的服务端微信预支付地址" + orderId;
        final IWXAPI wxapi = LemonWeChat.getInstance().getWXAPI();
        RestClient.builder()
                .url(weChatUrl)
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        JSONObject result = JSON.parseObject(response).getJSONObject("result");
                        String prepaid = result.getString("prepaid");
                        String partnerid = result.getString("partnerid");
                        String packageValue = result.getString("package");
                        String timestamp = result.getString("timestamp");
                        String noncestr = result.getString("noncestr");
                        String paySign = result.getString("sign");

                        PayReq payReq = new PayReq();
                        payReq.appId = Lemon.getConfigurator().getConfiguration(ConfigType.WX_CHAT_APP_ID);
                        payReq.prepayId = prepaid;
                        payReq.partnerId = partnerid;
                        payReq.packageValue = packageValue;
                        payReq.timeStamp = timestamp;
                        payReq.nonceStr = noncestr;
                        payReq.sign = paySign;

                        wxapi.sendReq(payReq);
                    }
                })
                .build()
                .post();
    }

    private void onAliPay(int orderId) {
        final String singUrl = "你的服务端支付宝预支付地址" + orderId;
        ;
        //获取签名字符串
        RestClient.builder()
                .url(singUrl)
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        String paySign = JSON.parseObject(response).getString("result");
                        //必须异步调用客户端支付
                        PayAsyncTask payAsyncTask = new PayAsyncTask(mActivity, mAliPayResultListener);
                        payAsyncTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, paySign);
                    }
                })
                .build()
                .get();
    }
}
