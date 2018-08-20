package com.skcodestack.stack.wechat.template;

import android.widget.Toast;

import com.skcodestack.stack.activities.ProxyActivity;
import com.skcodestack.stack.wechat.BaseWXEntryActivity;
import com.skcodestack.stack.wechat.BaseWXPayEntryActivity;

import me.yokeyword.fragmentation.SupportFragment;

/**
 * Email  1562363326@qq.com
 * Github https://github.com/skcodestack
 * Created by sk on 2018/7/12
 * Version  1.0
 * Description:
 */

public class WXPayEntryTemplate extends BaseWXPayEntryActivity{

    @Override
    public void onPaySuccess() {
        Toast.makeText(this,"支付成功",Toast.LENGTH_SHORT).show();
        finish();
        overridePendingTransition(0,0);
    }

    @Override
    public void onPayFail() {
        Toast.makeText(this,"支付失败",Toast.LENGTH_SHORT).show();
        finish();
        overridePendingTransition(0,0);

    }

    @Override
    public void onPayCancle() {
        Toast.makeText(this,"取消支付",Toast.LENGTH_SHORT).show();
        finish();
        overridePendingTransition(0,0);
    }
}
