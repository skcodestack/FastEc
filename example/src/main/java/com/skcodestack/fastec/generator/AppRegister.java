package com.skcodestack.fastec.generator;

import com.example.annatations.AppRegisterGenerator;
import com.skcodestack.stack.wechat.template.AppRegisterTemplate;

/**
 * Email  1562363326@qq.com
 * Github https://github.com/skcodestack
 * Created by sk on 2018/7/12
 * Version  1.0
 * Description:
 */
@AppRegisterGenerator(
        packageName = "com.skcodestack.fastec",
        registerTemplete = AppRegisterTemplate.class
)
public interface AppRegister {
}
