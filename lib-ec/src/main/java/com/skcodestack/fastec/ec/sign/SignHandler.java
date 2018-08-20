package com.skcodestack.fastec.ec.sign;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.skcodestack.fastec.ec.database.DatabaseManager;
import com.skcodestack.fastec.ec.database.UserProfile;
import com.skcodestack.stack.app.AccountManager;


/**
 * Email  1562363326@qq.com
 * Github https://github.com/skcodestack
 * Created by sk on 2018/7/12
 * Version  1.0
 * Description:
 */

public class SignHandler {
    //{'data':{'userId':'12','name':'xxx','avatar':'aa','gender':'man','address':'xdfdfdfdfd'}}
    public  static void onSignIn(String response, ISignListener mISignListener){
        JSONObject  user_profile = (JSONObject) JSON.parseObject(response).get("data");
        final long userId = user_profile.getLong("userId");
        String name = user_profile.getString("name");
        String avatar = user_profile.getString("avatar");
        String gender = user_profile.getString("gender");
        String address = user_profile.getString("address");

        UserProfile userProfile  = new UserProfile(userId,name,avatar,gender,address);
        DatabaseManager.getInstance().getDao().insert(userProfile);

        //注册成功并登陆
        AccountManager.setSignState(true);
        mISignListener.onSignInSuccess();
    }

    public  static void onSignUp(String response, ISignListener mISignListener){
        JSONObject  user_profile = (JSONObject) JSON.parseObject(response).get("data");
        final long userId = user_profile.getLong("userId");
        String name = user_profile.getString("name");
        String avatar = user_profile.getString("avatar");
        String gender = user_profile.getString("gender");
        String address = user_profile.getString("address");

        UserProfile userProfile  = new UserProfile(userId,name,avatar,gender,address);
        DatabaseManager.getInstance().getDao().insert(userProfile);

        //注册成功并登陆
        AccountManager.setSignState(true);
        mISignListener.onSignUpSuccess();
    }
}
