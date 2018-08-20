package com.skcodestack.stack.net.callback;

/**
 * Email  1562363326@qq.com
 * Github https://github.com/skcodestack
 * Created by sk on 2018/2/11
 * Version  1.0
 * Description:错误
 */

public interface IError {
    void onError(int code, String message);
}
