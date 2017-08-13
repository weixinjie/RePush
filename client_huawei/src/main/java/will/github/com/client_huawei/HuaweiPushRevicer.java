/*
 * Copyright (C) Huawei Technologies Co., Ltd. 2016. All rights reserved.
 * See LICENSE.txt for this sample's licensing information.
 */

package will.github.com.client_huawei;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.huawei.hms.support.api.push.PushReceiver;

import will.github.com.client_master.Logger;
import will.github.com.client_master.ReConstants;
import will.github.com.client_master.RePushMessage;


/**
 * 应用需要创建一个子类继承com.huawei.hms.support.api.push.PushReceiver，
 * 实现onToken，onPushState ，onPushMsg，onEvent，这几个抽象方法，用来接收token返回，push连接状态，透传消息和通知栏点击事件处理。
 * onToken 调用getToken方法后，获取服务端返回的token结果，返回token以及belongId
 * onPushState 调用getPushState方法后，获取push连接状态的查询结果
 * onPushMsg 推送消息下来时会自动回调onPushMsg方法实现应用透传消息处理。本接口必须被实现。 在开发者网站上发送push消息分为通知和透传消息
 * 通知为直接在通知栏收到通知，通过点击可以打开网页，应用 或者富媒体，不会收到onPushMsg消息
 * 透传消息不会展示在通知栏，应用会收到onPushMsg
 * onEvent 该方法会在设置标签、点击打开通知栏消息、点击通知栏上的按钮之后被调用。由业务决定是否调用该函数。
 */
public class HuaweiPushRevicer extends PushReceiver {

    @Override
    public void onToken(Context context, String token, Bundle extras) {
//        String belongId = extras.getString("belongId");
//        Log.i(TAG, "belongId为:" + belongId);
        RePushMessage message = new RePushMessage();
        message.setToken(token);
        if (HuaWeiClient.sReMessageListener == null) {
            throw new NullPointerException("you must set ReMessageListener in HuaweiClient");
        }
        HuaWeiClient.sReMessageListener.onToken(context, message);
    }

    @Override
    public boolean onPushMsg(Context context, byte[] msg, Bundle bundle) {
        try {
            //接收服务器推送的透传消息
            if (HuaWeiClient.sReMessageListener != null) {
                String content = new String(msg, "UTF-8");
                RePushMessage message = new RePushMessage();
                message.setContent(content);
                message.setPlatform(ReConstants.PUSHNAME_HUAWEI);
                HuaWeiClient.sReMessageListener.onReceivePassThroughMessage(context, message);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    //注意：在第三方app被系统杀死的时候，虽然推送能送到客户端，但是用户点击的时候并不会触发这个函数（开启自启动权限除外）。解决方案见PushhndlerActivity
    @Deprecated
    public void onEvent(Context context, Event event, Bundle extras) {
        super.onEvent(context, event, extras);
        //接收点击事件
        if (Event.NOTIFICATION_OPENED.equals(event) || Event.NOTIFICATION_CLICK_BTN.equals(event)) {
//            if (HuaWeiManager.sMixMessageProvider != null) {
//                MixPushMessage message = new MixPushMessage();
//                message.setPlatform(HuaWeiManager.NAME);
//                String hw_message = extras.getString(BOUND_KEY.pushMsgKey);
//                Logger.e("华为推送的点击事件  " + hw_message);
//                message.setTitle(hw_message);
//                message.setDescription(hw_message);
//                message.setContent(hw_message);
//                HuaWeiManager.sMixMessageProvider.onNotificationMessageClicked(context, message);
//            }
        }
        super.onEvent(context, event, extras);
    }

    @Override
    public void onPushState(Context context, boolean pushState) {
        Logger.e("Push连接状态为 " + pushState);
    }
}
