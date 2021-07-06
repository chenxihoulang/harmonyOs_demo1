package com.chw.pktest.slice;

import com.chw.pktest.ResourceTable;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.event.notification.*;
import ohos.rpc.RemoteException;

import java.time.LocalDateTime;

public class ReminderAbilitySlice extends AbilitySlice {
    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_ability_reminder);


        // 1. 设置渠道信息
        NotificationSlot slot = new NotificationSlot("slot_id", "slot_name", NotificationSlot.LEVEL_HIGH);
        slot.setEnableLight(false);
        slot.setEnableVibration(true);
        // 2. 向代理服务添加渠道对象
        try {
            ReminderHelper.addNotificationSlot(slot);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        // 3. 创建提醒类通知对象
        int[] repeatDay = {};
        ReminderRequest reminder = new ReminderRequestAlarm(10, 30, repeatDay);
        // 4. 设置提醒内容
        reminder.setTitle("set title here").setContent("set content here");
        // 5. 设置提醒时长等属性
        reminder.setSnoozeTimes(1).setTimeInterval(5 * 60).setRingDuration(10);
        // 6. 设置IntentAgent, （假设包名为：com.ohos.aaa，Ability类名为FirstAbility）
        reminder.setIntentAgent("com.ohos.aaa", ReminderAbilitySlice.class.getName());
        // 7. 设置提醒信息框中的“延迟提醒”和“关闭”按钮（可选）（ActionButton）
        reminder.setActionButton("snooze", ReminderRequest.ACTION_BUTTON_TYPE_SNOOZE)
                .setActionButton("close", ReminderRequest.ACTION_BUTTON_TYPE_CLOSE);
        // 8. 发布提醒类通知
        try {
            ReminderHelper.publishReminder(reminder);
        } catch (ReminderManager.AppLimitExceedsException e) {
            e.printStackTrace();
        } catch (ReminderManager.SysLimitExceedsException e) {
            e.printStackTrace();
        } catch (RemoteException e) {
            e.printStackTrace();
        }


        // 经过1分钟后提醒
        ReminderRequestTimer reminderRequestTimer = new ReminderRequestTimer(60);

        // 2021年3月2日14点30分提醒
        int[] repeatMonths = {};
        int[] repeatDays = {};
        ReminderRequestCalendar reminderRequestCalendar = new ReminderRequestCalendar(
                LocalDateTime.of(2021, 3, 2, 14, 30),
                repeatMonths, repeatDays);

        // 3月份，5月份的9号和15号 14点30分提醒，延迟10分钟后再次提醒，默认延迟次数为3次
        int[] repeatMonths1 = {3, 5};
        int[] repeatDaysOfMonth1 = {9, 15};
        ReminderRequestCalendar reminderRequestCalender = new ReminderRequestCalendar(
                LocalDateTime.of(2021, 3, 2, 14, 30),
                repeatMonths1, repeatDaysOfMonth1);
        reminderRequestCalender.setTimeInterval(10 * 60);

        //创建一个一次性闹钟提醒 13点59分提醒，如果当前时间大于13点59分，则取后一天的13点59分
        int[] repeatDay1 = {};
        ReminderRequest reminderRequestAlarm = new ReminderRequestAlarm(13, 59, repeatDay1);

        // 每周1，2，3，4的13点59分提醒
        int[] repeatDay2 = {1, 2, 3, 4};
        ReminderRequest reminderRequestAlarm2 = new ReminderRequestAlarm(13, 59, repeatDay2);
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
