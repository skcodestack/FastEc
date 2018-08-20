package com.skcodestack.fastec.generator;

import com.example.annatations.PayEntryGenerator;
import com.skcodestack.stack.wechat.template.WXPayEntryTemplate;

/**
 * Email  1562363326@qq.com
 * Github https://github.com/skcodestack
 * Created by sk on 2018/7/12
 * Version  1.0
 * Description:
 */

@PayEntryGenerator(
        packageName = "com.skcodestack.fastec",
        payEntryTemplete = WXPayEntryTemplate.class
)
public interface WeChatPayEntry {
}
