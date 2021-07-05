package com.chw.pktest;

import ohos.aafwk.ability.Ability;
import ohos.aafwk.ability.LocalRemoteObject;
import ohos.aafwk.content.Intent;
import ohos.agp.components.DependentLayout;
import ohos.event.notification.NotificationRequest;
import ohos.rpc.IRemoteObject;
import ohos.hiviewdfx.HiLog;
import ohos.hiviewdfx.HiLogLabel;

public class ServiceAbility extends Ability {
    private static final HiLogLabel LABEL_LOG = new HiLogLabel(3, 0xD001100, "Demo");

    /**
     * 该方法在创建Service的时候调用，用于Service的初始化。在Service的整个生命周期只会调用一次，调用时传入的Intent应为空
     */
    @Override
    public void onStart(Intent intent) {
        HiLog.error(LABEL_LOG, "ServiceAbility::onStart,android onCreate");
        super.onStart(intent);

        // 创建通知，其中1005为notificationId
        NotificationRequest request = new NotificationRequest(1005);
        NotificationRequest.NotificationNormalContent content = new NotificationRequest.NotificationNormalContent();
        content.setTitle("title").setText("text");
        NotificationRequest.NotificationContent notificationContent = new NotificationRequest.NotificationContent(content);
        request.setContent(notificationContent);

        // 绑定通知，1005为创建通知时传入的notificationId
        keepBackgroundRunning(1005, request);
    }

    /**
     * 在Service创建完成之后调用，该方法在客户端每次启动该Service时都会调用，用户可以在该方法中做一些调用统计、初始化类的操作
     */
    @Override
    public void onCommand(Intent intent, boolean restart, int startId) {
        HiLog.info(LABEL_LOG,"android onStartCommand");

        //停止
//        terminateAbility();
    }

    @Override
    public void onBackground() {
        super.onBackground();
        HiLog.info(LABEL_LOG, "ServiceAbility::onBackground");
    }

    /**
     * 在Service销毁时调用。Service应通过实现此方法来清理任何资源，如关闭线程、注册的侦听器等
     */
    @Override
    public void onStop() {
        super.onStop();
        HiLog.info(LABEL_LOG, "ServiceAbility::onStop,android onDestroy");
    }

    /**
     * 在Ability和Service连接时调用，该方法返回IRemoteObject对象，
     * 用户可以在该回调函数中生成对应Service的IPC通信通道，以便Ability与Service交互。
     * Ability可以多次连接同一个Service，系统会缓存该Service的IPC通信对象，
     * 只有第一个客户端连接Service时，系统才会调用Service的onConnect方法来生成IRemoteObject对象，
     * 而后系统会将同一个RemoteObject对象传递至其他连接同一个Service的所有客户端，而无需再次调用onConnect方法
     *
     * connectAbility()
     */
    @Override
    public IRemoteObject onConnect(Intent intent) {
        HiLog.info(LABEL_LOG,"android onBind");
        return new MyRemoteObject();
    }

    /**
     * 在Ability与绑定的Service断开连接时调用,disconnectAbility()
     */
    @Override
    public void onDisconnect(Intent intent) {
        HiLog.info(LABEL_LOG,"android onUnbind");
    }

    private class MyRemoteObject extends LocalRemoteObject{

    }
}