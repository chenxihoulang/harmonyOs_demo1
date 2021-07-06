package com.chw.pktest.slice;

import com.chw.pktest.ResourceTable;
import com.chw.pktest.utils.ElementUtil;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.agp.utils.Color;
import ohos.event.intentagent.IntentAgent;
import ohos.event.intentagent.IntentAgentHelper;
import ohos.event.notification.*;
import ohos.event.notification.NotificationRequest.NotificationContent;
import ohos.event.notification.NotificationRequest.NotificationNormalContent;
import ohos.media.image.PixelMap;
import ohos.rpc.RemoteException;

import java.util.Set;
import java.util.concurrent.locks.LockSupport;

/**
 * 推送通知
 */
public class NotificationAbilitySlice extends AbilitySlice {
    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_ability_notification);

        NotificationSlot slot = new NotificationSlot("slot1", "name", NotificationSlot.LEVEL_HIGH);
        slot.setDescription("描述信息");
        //设置是否绕过系统的免打扰模式
        slot.enableBypassDnd(true);
        slot.setEnableVibration(true);
        slot.setEnableLight(true);
        slot.setLedLightColor(Color.GREEN.getValue());
        slot.setLockscreenVisibleness(NotificationRequest.VISIBLENESS_TYPE_PUBLIC);
        slot.setSlotGroup("groupId1");
        try {
            NotificationHelper.addNotificationSlot(slot);
        } catch (RemoteException e) {
            e.printStackTrace();
        }

        int notificationId = 1;
        NotificationRequest request = new NotificationRequest(notificationId);
        request.setAutoDeletedTime(System.currentTimeMillis() + 60 * 60 * 60);
        request.setDeliveryTime(System.currentTimeMillis());
        //构建NotificationRequest对象，应用发布通知前，通过NotificationRequest的setSlotId()方法与NotificationSlot绑定，使该通知在发布后都具备该对象的特征
        request.setSlotId("slot1");
        request.setGroupValue("groupId1");
        request.setTapDismissed(true);

        PixelMap pixelMap = null;
        IntentAgent intentAgent = IntentAgentHelper.getIntentAgent(this, null);
        NotificationActionButton button = new NotificationActionButton.Builder(pixelMap, "回复", intentAgent).build();
        request.addActionButton(button);

        request.setIntentAgent(intentAgent);

//        getResourceManager().getElement(ResourceTable.Media_icon)
//        request.setLittleIcon();

        NotificationRequest.NotificationNormalContent normalContent = new NotificationNormalContent();
        normalContent.setTitle("通知标题").setText("通知内容");
        NotificationRequest.NotificationContent content = new NotificationContent(normalContent);
        request.setContent(content);

        try {
            NotificationSlotGroup notificationSlotGroup = new NotificationSlotGroup("groupId", "groupName");
            notificationSlotGroup.setDescription("分组描述");
            NotificationHelper.addNotificationSlotGroup(notificationSlotGroup);

            NotificationHelper.publishNotification("tag1", request);
            NotificationHelper.cancelAllNotifications();
            NotificationHelper.cancelNotification(notificationId);

            //获取当前应用发的活跃通知
            Set<NotificationRequest> activeNotifications = NotificationHelper.getActiveNotifications();
            int activeNotificationNums = NotificationHelper.getActiveNotificationNums();
            NotificationHelper.setNotificationBadgeNum();
            NotificationHelper.setNotificationBadgeNum(99);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onActive() {
        super.onActive();
    }

    @Override
    public void onForeground(Intent intent) {
        super.onForeground(intent);
    }
}
