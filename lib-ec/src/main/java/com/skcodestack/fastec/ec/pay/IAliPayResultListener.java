package com.skcodestack.fastec.ec.pay;

/**
 * Email  1562363326@qq.com
 * Github https://github.com/skcodestack
 * Created by sk on 2018/7/18
 * Version  1.0
 * Description:
 */

public interface IAliPayResultListener {

    void onPayConnectionError();

    void onPaying();

    void onPaySuccess();

    void onPayFail();

    void onPayCancle();
}
