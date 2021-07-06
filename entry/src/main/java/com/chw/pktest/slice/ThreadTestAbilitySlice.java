package com.chw.pktest.slice;

import com.chw.pktest.ResourceTable;
import com.chw.pktest.utils.LogUtils;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.app.dispatcher.Group;
import ohos.app.dispatcher.TaskDispatcher;
import ohos.app.dispatcher.task.Revocable;
import ohos.app.dispatcher.task.TaskPriority;
import ohos.eventhandler.EventRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class ThreadTestAbilitySlice extends AbilitySlice {
    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_ability_thread_test);

        TaskDispatcher globalTaskDispatcher = getGlobalTaskDispatcher(TaskPriority.DEFAULT);
        globalTaskDispatcher.syncDispatch(() -> {
            LogUtils.error("tag", "同步执行,阻塞线程");
        });

        Revocable revocable = globalTaskDispatcher.asyncDispatch(() -> {
            LogUtils.error("tag", "异步执行,不阻塞线程");
        });
        boolean revokeStatus = revocable.revoke();

        Revocable revocable1 = globalTaskDispatcher.delayDispatch(() -> {
            LogUtils.error("tag", "异步执行,不阻塞线程,延迟1秒才将任务加到队列中");
        }, 1000L);

        TaskDispatcher parallelDispatcher = createParallelTaskDispatcher("parallelDispatcher", TaskPriority.DEFAULT);

        TaskDispatcher serialDispatcher = createSerialTaskDispatcher("serialDispatcher", TaskPriority.DEFAULT);

        TaskDispatcher uiTaskDispatcher = getUITaskDispatcher();

        final long callTime = System.currentTimeMillis();
        final long delayTime = 50L;
        Revocable revocable2 = globalTaskDispatcher.delayDispatch(new Runnable() {
            @Override
            public void run() {
                LogUtils.error("tag", "delayDispatch task1 run");
                final long actualDelay = System.currentTimeMillis() - callTime;
                LogUtils.error("tag", String.format("actualDelayTime >= delayTime: %{public}b", (actualDelay >= delayTime)));
            }
        }, delayTime);
        LogUtils.error("tag", "after delayDispatch task1");


        String dispatcherName = "parallelTaskDispatcher";
        TaskDispatcher dispatcher = createParallelTaskDispatcher(dispatcherName, TaskPriority.DEFAULT);
        // 创建任务组。
        Group group = dispatcher.createDispatchGroup();
        // 将任务1加入任务组，返回一个用于取消任务的接口。
        dispatcher.asyncGroupDispatch(group, new Runnable() {
            @Override
            public void run() {
                LogUtils.error("tag", "download task1 is running");
            }
        });
        // 将与任务1相关联的任务2加入任务组。
        dispatcher.asyncGroupDispatch(group, new Runnable() {
            @Override
            public void run() {
                LogUtils.error("tag", "download task2 is running");
            }
        });
        // 在任务组中的所有任务执行完成后执行指定任务。
        dispatcher.groupDispatchNotify(group, new Runnable() {
            @Override
            public void run() {
                LogUtils.error("tag", "the close task is running after all tasks in the group are completed");
            }
        });


        // 将任务加入任务组，返回一个用于取消任务的接口。
        dispatcher.asyncGroupDispatch(group, new Runnable() {
            @Override
            public void run() {
                LogUtils.error("tag", "task1 is running");  // 1
            }
        });
        dispatcher.asyncGroupDispatch(group, new Runnable() {
            @Override
            public void run() {
                LogUtils.error("tag", "task2 is running");  // 2
            }
        });

        //同步设置屏障任务：在任务组上设立任务执行屏障，同步等待任务组中的所有任务执行完成，再执行指定任务。
        dispatcher.syncDispatchBarrier(new Runnable() {
            @Override
            public void run() {
                LogUtils.error("tag", "barrier");  // 3
            }
        });
        LogUtils.error("tag", "after syncDispatchBarrier");  // 4

        //执行结果 1234 2134


        TaskDispatcher dispatcher1 = createParallelTaskDispatcher("dispatcherName", TaskPriority.DEFAULT);
// 创建任务组。
        Group group1 = dispatcher1.createDispatchGroup();
// 将任务加入任务组，返回一个用于取消任务的接口。
        dispatcher.asyncGroupDispatch(group1, new Runnable() {
            @Override
            public void run() {
                LogUtils.error("tag", "task1 is running");  // 1
            }
        });
        dispatcher.asyncGroupDispatch(group1, new Runnable() {
            @Override
            public void run() {
                LogUtils.error("tag", "task2 is running");  // 2
            }
        });

        dispatcher.asyncDispatchBarrier(new Runnable() {
            @Override
            public void run() {
                LogUtils.error("tag", "barrier");  // 3
            }
        });
        LogUtils.error("tag", "after asyncDispatchBarrier");  // 4

        // 1和2的执行顺序不定，但总在3之前执行；4不需要等待1、2、3执行完成。


        final int total = 10;
        final CountDownLatch latch = new CountDownLatch(total);
        final List<Long> indexList = new ArrayList<>(total);
        TaskDispatcher dispatcher2 = getGlobalTaskDispatcher(TaskPriority.DEFAULT);

        // 执行任务 total 次。
        dispatcher2.applyDispatch((index) -> {
            indexList.add(index);
            latch.countDown();
        }, total);
        // 设置任务超时。
        try {
            latch.await();
        } catch (InterruptedException exception) {
            LogUtils.error("tag", "latch exception");
        }
        LogUtils.error("tag", String.format("list size matches, %{public}b", (total == indexList.size())));
        // 执行结果：list size matches, true
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
