package com.skcodestack.fastec.ec.sign;

/**
 * Email  1562363326@qq.com
 * Github https://github.com/skcodestack
 * Created by sk on 2018/7/23
 * Version  1.0
 * Description:
 */

public interface ISMSCallback {

    void onSuccess(String code,String phone);
    void onError();

}
