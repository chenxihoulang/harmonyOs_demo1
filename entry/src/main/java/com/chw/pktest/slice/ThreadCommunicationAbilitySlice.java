package com.chw.pktest.slice;

import com.chw.pktest.ResourceTable;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.eventhandler.EventHandler;
import ohos.eventhandler.EventRunner;
import ohos.eventhandler.InnerEvent;
import ohos.utils.PacMap;

public class ThreadCommunicationAbilitySlice extends AbilitySlice {

    private static final int EVENT_MESSAGE_NORMAL = 1;
    private static final int EVENT_MESSAGE_DELAY = 2;

    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_ability_thread_communication);

        EventRunner runner = EventRunner.create(false);
        EventHandler handler = new EventHandler(runner) {
            @Override
            protected void processEvent(InnerEvent event) {
                super.processEvent(event);

                if (event == null) {
                    return;
                }
                int eventId = event.eventId;
                switch (eventId) {
                    case EVENT_MESSAGE_NORMAL:
                        // 待执行的操作，由开发者定义
                        break;
                    case EVENT_MESSAGE_DELAY:
                        // 待执行的操作，由开发者定义
                        break;
                    default:
                        break;
                }
            }
        };

        InnerEvent event = InnerEvent.get(EVENT_MESSAGE_NORMAL, 0L, null);
        InnerEvent event1 = InnerEvent.get(EVENT_MESSAGE_DELAY, 1L, null);
        //可用于区分事件复用
        event.eventId = 1;
        event.object = "data";
        event.param = 1;

        PacMap pacMap = event.getPacMap();
        Runnable eventTask = event.getTask();

        handler.sendEvent(event, 100L, EventHandler.Priority.LOW);
        handler.sendEvent(event1, 2L, EventHandler.Priority.IMMEDIATE);
        handler.sendSyncEvent(event);
        handler.postSyncTask(() -> {

        }, EventHandler.Priority.LOW);

        handler.sendTimingEvent(event, System.currentTimeMillis() + 10 * 1000L);

        handler.removeAllEvent();
        handler.removeEvent(event.eventId);

        EventRunner eventRunner = handler.getEventRunner();

        boolean hasInnerEvent = handler.hasInnerEvent(event);
        boolean idle = handler.isIdle();

        runner.run();
        runner.stop();

        //将当前线程的EventRunner传递给EventHandler,然后在processEvent中根据传递过来的EventRunner对象创建EventHandler,
        //再使用新创建的EventHandler对象发送事件,新事件就会在原来线程执行
        InnerEvent event2 = InnerEvent.get(EVENT_MESSAGE_CROSS_THREAD, 0L, EventRunner.current());
        MyEventHandler myEventHandler = new MyEventHandler(runner);
        myEventHandler.sendEvent(event2);
    }

    private static final int EVENT_MESSAGE_CROSS_THREAD = 1;

    private class MyEventHandler extends EventHandler {
        private MyEventHandler(EventRunner runner) {
            super(runner);
        }

        // 重写实现processEvent方法
        @Override
        public void processEvent(InnerEvent event) {
            super.processEvent(event);
            if (event == null) {
                return;
            }
            int eventId = event.eventId;
            switch (eventId) {
                case EVENT_MESSAGE_CROSS_THREAD:
                    Object object = event.object;
                    if (object instanceof EventRunner) {
                        // 将原先线程的EventRunner实例投递给新创建的线程
                        EventRunner runner2 = (EventRunner) object;
                        // 将原先线程的EventRunner实例与新创建的线程的EventHandler绑定
                        EventHandler myHandler2 = new EventHandler(runner2) {
                            @Override
                            public void processEvent(InnerEvent event) {
                                // 需要在原先线程执行的操作

                            }
                        };
                        int eventId2 = 1;
                        long param2 = 0L;
                        Object object2 = null;
                        InnerEvent event2 = InnerEvent.get(eventId2, param2, object2);
                        myHandler2.sendEvent(event2); // 投递事件到原先的线程
                    }
                    break;
                default:
                    break;
            }
        }
    }
}
