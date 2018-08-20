package com.skcodestack.stack.app;

import com.skcodestack.stack.util.storage.LemonPreference;

/**
 * Email  1562363326@qq.com
 * Github https://github.com/skcodestack
 * Created by sk on 2018/7/12
 * Version  1.0
 * Description:
 */

public class AccountManager {

    enum SignTag{
        SIGN_TAG
    }

    /**
     * 设置用户登陆状态
     * @param isSign
     */
    public static void setSignState(boolean isSign){
        LemonPreference.setAppFlag(SignTag.SIGN_TAG.name(),isSign);
    }

    public static boolean isSignIn(){
        return LemonPreference.getAppFlag(SignTag.SIGN_TAG.name());
    }

    public static void checkAccout(IUserChecker checker){
        if(checker == null){
            return;
        }
        if(isSignIn()){
            checker.onSignIn();
        }else {
            checker.onNotSignIn();
        }
    }
}
