package com.skcodestack.fastec.push;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;

import com.alibaba.fastjson.JSONObject;
import com.skcodestack.fastec.ExampleActivity;
import com.skcodestack.stack.ui.loader.LemonLoader;
import com.skcodestack.stack.util.log.LemonLogger;

import java.util.Set;
import java.util.logging.Logger;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.service.PushService;

/**
 * Email  1562363326@qq.com
 * Github https://github.com/skcodestack
 * Created by sk on 2018/7/20
 * Version  1.0
 * Description:
 */

public class PushReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        Bundle bundle = intent.getExtras();
        Set<String> keySet = bundle.keySet();
        JSONObject jsonObject = new JSONObject();
        for (String key : keySet) {
            Object value = bundle.get(key);
            jsonObject.put(key, value);
        }
        LemonLogger.e("jpush", "push:" + jsonObject.toString());

        String action = intent.getAction();
        if (action.equals(JPushInterface.ACTION_NOTIFICATION_RECEIVED)) {
            //处理接受信息
            onReceiveMessage(bundle);
        } else if (action.equals(JPushInterface.ACTION_NOTIFICATION_OPENED)) {
            //打开notification
            onOpenNotification(context, bundle);
        }
    }

    private void onReceiveMessage(Bundle bundle) {
        final String title = bundle.getString(JPushInterface.EXTRA_NOTIFICATION_TITLE);
        final String msgId = bundle.getString(JPushInterface.EXTRA_MSG_ID);
        final int notificationId = bundle.getInt(JPushInterface.EXTRA_NOTIFICATION_ID);
        final String message = bundle.getString(JPushInterface.EXTRA_MESSAGE);
        final String extra = bundle.getString(JPushInterface.EXTRA_EXTRA);
        final String alert = bundle.getString(JPushInterface.EXTRA_ALERT);
    }

    private void onOpenNotification(Context context, Bundle bundle) {
        final String extra = bundle.getString(JPushInterface.EXTRA_EXTRA);
        final Bundle openActivityBundle = new Bundle();
        openActivityBundle.putString("extra", extra);
        final Intent intent = new Intent(context, ExampleActivity.class);
        intent.putExtras(openActivityBundle);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        ContextCompat.startActivity(context, intent, null);
    }

}
