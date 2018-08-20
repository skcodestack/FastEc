package com.skcodestack.fastec.generator;

import com.example.annatations.EntryGenerator;
import com.example.annatations.PayEntryGenerator;
import com.skcodestack.stack.wechat.template.WXEntryTemplate;
import com.skcodestack.stack.wechat.template.WXPayEntryTemplate;

/**
 * Email  1562363326@qq.com
 * Github https://github.com/skcodestack
 * Created by sk on 2018/7/12
 * Version  1.0
 * Description:
 */
@EntryGenerator(
        packageName = "com.skcodestack.fastec",
        entryTemplete = WXEntryTemplate.class
)
public interface WeChatEntry {
}
