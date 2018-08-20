package com.skcodestack.stack.wechat;

import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;

/**
 * Email  1562363326@qq.com
 * Github https://github.com/skcodestack
 * Created by sk on 2018/7/18
 * Version  1.0
 * Description:
 */

public abstract class BaseWXPayEntryActivity extends BaseWXActivity {

    private static final int WX_PAY_SUCCESS = 0;
    private static final int WX_PAY_FAIL = -1;
    private static final int WX_PAY_CANCLE = -2;


    @Override
    public void onReq(BaseReq baseReq) {

    }

    public abstract void onPaySuccess();

    public abstract void onPayFail();

    public abstract void onPayCancle();


    @Override
    public void onResp(BaseResp baseResp) {
        if (baseResp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
            switch (baseResp.errCode) {
                case WX_PAY_SUCCESS:
                    break;
                case WX_PAY_FAIL:
                    break;
                case WX_PAY_CANCLE:
                    break;
            }
        }
    }
}
