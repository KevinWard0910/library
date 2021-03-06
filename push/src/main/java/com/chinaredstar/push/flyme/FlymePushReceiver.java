package com.chinaredstar.push.flyme;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.chinaredstar.push.HxPushMessage;
import com.chinaredstar.push.HxPushReceiver;
import com.chinaredstar.push.utils.MsgProcessor;
import com.chinaredstar.push.utils.Platform;
import com.meizu.cloud.pushsdk.MzPushMessageReceiver;
import com.meizu.cloud.pushsdk.notification.PushNotificationBuilder;
import com.meizu.cloud.pushsdk.platform.message.PushSwitchStatus;
import com.meizu.cloud.pushsdk.platform.message.RegisterStatus;
import com.meizu.cloud.pushsdk.platform.message.SubAliasStatus;
import com.meizu.cloud.pushsdk.platform.message.SubTagsStatus;
import com.meizu.cloud.pushsdk.platform.message.UnRegisterStatus;

/**
 * Created by hairui.xiang on 2017/8/8.
 */

public class FlymePushReceiver extends MzPushMessageReceiver {
    @Override
    @Deprecated
    public void onRegister(Context context, String pushid) {
        //应用在接受返回的pushid
//        PushManager.getPushId(context);
//        TokenUtil.setToken(context, pushid);
    }

    @Override
    public void onHandleIntent(Context context, Intent intent) {
        super.onHandleIntent(context, intent);
    }

    @Override
    public void onMessage(Context context, String s) {
        //接收服务器推送的透传消息
        final HxPushMessage message = new HxPushMessage();
        message.setMessageID("");
        message.setPlatform(Platform.FLYME);
        message.setExtra(s);
        MsgProcessor.flymeMessage(context, message, HxPushReceiver.ACTION_MESSAGE_RECEIVED);
    }

    @Override
    @Deprecated
    public void onUnRegister(Context context, boolean b) {
        //调用PushManager.unRegister(context）方法后，会在此回调反注册状态
    }

    //设置通知栏小图标
    @Override
    public void onUpdateNotificationBuilder(PushNotificationBuilder pushNotificationBuilder) {
    }

    @Override
    public void onPushStatus(Context context, PushSwitchStatus pushSwitchStatus) {
        //检查通知栏和透传消息开关状态回调
    }

    @Override
    public void onRegisterStatus(Context context, RegisterStatus registerStatus) {
        Log.i(TAG, "onRegisterStatus " + registerStatus);
        //新版订阅回调
    }

    @Override
    public void onUnRegisterStatus(Context context, UnRegisterStatus unRegisterStatus) {
        Log.i(TAG, "onUnRegisterStatus " + unRegisterStatus);
        //新版反订阅回调
    }

    @Override
    public void onSubTagsStatus(Context context, SubTagsStatus subTagsStatus) {
        Log.i(TAG, "onSubTagsStatus " + subTagsStatus);
        //标签回调
    }

    @Override
    public void onSubAliasStatus(Context context, SubAliasStatus subAliasStatus) {
        Log.i(TAG, "onSubAliasStatus " + subAliasStatus);
        //别名回调
    }

    @Override
    public void onNotificationArrived(Context context, String title, String content, String selfDefineContentString) {
        //通知栏消息到达回调，flyme6基于android6.0以上不再回调
        HxPushMessage message = new HxPushMessage();
        message.setPlatform(Platform.FLYME);
        message.setTitle(title);
        message.setExtra(content);
        MsgProcessor.flymeMessage(context, message, HxPushReceiver.ACTION_NOTIFICATION_RECEIVED);
    }

    @Override
    public void onNotificationClicked(Context context, String title, String content, String selfDefineContentString) {
        //通知栏消息点击回调
        HxPushMessage message = new HxPushMessage();
        message.setPlatform(Platform.FLYME);
        message.setTitle(title);
        message.setExtra(content);
        MsgProcessor.flymeMessage(context, message, HxPushReceiver.ACTION_NOTIFICATION_CLICKED);
    }

    @Override
    public void onNotificationDeleted(Context context, String title, String content, String selfDefineContentString) {
        //通知栏消息删除回调；flyme6基于android6.0以上不再回调
    }
}
