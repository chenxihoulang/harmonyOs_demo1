package com.chw.pktest.slice;

import com.chw.pktest.utils.LogUtils;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.aafwk.content.Operation;
import ohos.event.commonevent.*;
import ohos.event.intentagent.IntentAgent;
import ohos.event.intentagent.IntentAgentHelper;
import ohos.event.intentagent.IntentAgentInfo;
import ohos.eventhandler.EventHandler;
import ohos.eventhandler.EventRunner;
import ohos.rpc.RemoteException;

/**
 * 公共事件通知
 */
public class QueryWeatherSlice extends AbilitySlice {
    @Override
    protected void onStart(Intent intent) {
        super.onStart(intent);

        IntentAgentInfo intentAgentInfo = null;
        IntentAgent intentAgent = IntentAgentHelper.getIntentAgent(this, intentAgentInfo);

        MatchingSkills matchingSkills=new MatchingSkills();
        //自定义事件
        matchingSkills.addEvent("com.my.test");
        matchingSkills.addEvent(CommonEventSupport.COMMON_EVENT_SCREEN_ON);

        CommonEventSubscribeInfo subscribeInfo = new CommonEventSubscribeInfo(matchingSkills);
        subscribeInfo.setPriority(100);
        subscribeInfo.setDeviceId("");
        subscribeInfo.setPermission("");
        subscribeInfo.setThreadMode(CommonEventSubscribeInfo.ThreadMode.HANDLER);

        CommonEventSubscriber subscriber = new CommonEventSubscriber(subscribeInfo) {
            /**
             * 此处不能执行耗时操作，否则会阻塞UI线程，产生用户点击没有反应等异常
             */
            @Override
            public void onReceiveEvent(CommonEventData commonEventData) {
                LogUtils.info("tag", commonEventData.getData());
                LogUtils.info("tag", commonEventData.getIntent().getStringParam("name"));
            }
        };

        //设置有序公共事件的异步结果
        subscriber.setCodeAndData(1, "");
        //取消当前的公共事件，仅对有序公共事件有效，取消后，公共事件不再向下一个订阅者传递
        subscriber.abortCommonEvent();

        Intent intent1 = new Intent();
        Operation operation = new Intent.OperationBuilder()
                .withAction("com.my.test")
                .build();
        intent.setOperation(operation);
        intent.setParam("name", "chw");

        CommonEventData eventData = new CommonEventData(intent1);
        eventData.setCode(1);
        eventData.setData("data");

        CommonEventPublishInfo publishInfo = new CommonEventPublishInfo();
        publishInfo.setSticky(true);
        publishInfo.setOrdered(true);

        String[] permissions = {"com.chw.pktest.permission"};
        //配置权限,config.json文件中也需要配置
        publishInfo.setSubscriberPermissions(permissions);

        // EventRunner创建新线程，将耗时的操作放到新的线程上执行
        EventRunner runner=EventRunner.create();
        EventHandler eventHandler= new EventHandler(runner);

        //最后一个事件的订阅者
        CommonEventSubscriber resultSubscriber = new CommonEventSubscriber(subscribeInfo) {
            @Override
            public void onReceiveEvent(CommonEventData commonEventData) {
                AsyncCommonEventResult result = goAsyncCommonEvent();

                eventHandler.postTask( new Runnable() {
                    @Override
                    public void run() {
                        // 待执行的操作，由开发者定义

                        // 调用finish结束异步操作
                        result.finishCommonEvent();
                    }
                });
            }
        };

        try {
            //发布携带权限的公共事件
            CommonEventManager.publishCommonEvent(eventData, publishInfo, resultSubscriber);

            CommonEventManager.subscribeCommonEvent(subscriber);
            CommonEventManager.unsubscribeCommonEvent(subscriber);
        } catch (RemoteException e) {
            e.printStackTrace();
        }


    }
}
