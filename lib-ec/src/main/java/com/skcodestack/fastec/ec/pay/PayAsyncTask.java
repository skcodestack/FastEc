package com.skcodestack.fastec.ec.pay;

import android.app.Activity;
import android.os.AsyncTask;

import com.alipay.sdk.app.PayTask;
import com.skcodestack.stack.ui.loader.LemonLoader;
import com.skcodestack.stack.util.log.LemonLogger;

/**
 * Email  1562363326@qq.com
 * Github https://github.com/skcodestack
 * Created by sk on 2018/7/18
 * Version  1.0
 * Description:
 */

public class PayAsyncTask extends AsyncTask<String, Void, String> {
    private final Activity mActivity;
    private final IAliPayResultListener ALIPAY_LISTENER;


    public PayAsyncTask(Activity mActivity, IAliPayResultListener alipay_listener) {
        this.mActivity = mActivity;
        this.ALIPAY_LISTENER = alipay_listener;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        LemonLoader.showLoading(mActivity);
    }

    @Override
    protected String doInBackground(String... params) {
        String paySign = params[0];
        PayTask mPayTask = new PayTask(mActivity);
        return mPayTask.pay(paySign, true);
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        LemonLoader.stopLoading();

        PayResult payResult = new PayResult(s);
        //支付宝返回此次支付结果以及加签，建议从支付宝签名信息中拿签约时支付宝提供的公钥做验证
        String resultInfo = payResult.getResult();
        String resultStatus = payResult.getResultStatus();
        LemonLogger.e("ali pay", "ali pay result info:" + resultInfo + " -reslut status:" + resultStatus);
        switch (resultStatus) {
            case PayResult.ALIPAY_SUCCESS_STATUS:
                if (ALIPAY_LISTENER != null) {
                    ALIPAY_LISTENER.onPaySuccess();
                }
                break;
            case PayResult.ALIPAY_CANCLE_STATUS:
                if (ALIPAY_LISTENER != null) {
                    ALIPAY_LISTENER.onPayCancle();
                }
                break;
            case PayResult.ALIPAY_CONNECTION_ERROR_STATUS:
                if (ALIPAY_LISTENER != null) {
                    ALIPAY_LISTENER.onPayConnectionError();
                }
                break;
            case PayResult.ALIPAY_PAYING_STATUS:
                if (ALIPAY_LISTENER != null) {
                    ALIPAY_LISTENER.onPaying();
                }
                break;
            case PayResult.ALIPAY_FAIL_STATUS:
                if (ALIPAY_LISTENER != null) {
                    ALIPAY_LISTENER.onPayFail();
                }
                break;
            default:
                break;
        }
    }

}
