package com.skcodestack.stack.net.callback;

/**
 * Email  1562363326@qq.com
 * Github https://github.com/skcodestack
 * Created by sk on 2018/2/11
 * Version  1.0
 * Description: 请求开始和结束时调用（loading的显示和隐藏）
 */

public interface IRequest {

    void onReqestStart();

    void onRequestEnd();
}
