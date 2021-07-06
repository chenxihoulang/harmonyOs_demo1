package com.chw.pktest.slice;

import com.chw.pktest.ResourceTable;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.aafwk.content.IntentParams;
import ohos.aafwk.content.Operation;
import ohos.event.intentagent.*;
import ohos.event.notification.NotificationHelper;
import ohos.eventhandler.EventHandler;
import ohos.eventhandler.EventRunner;

import java.util.ArrayList;
import java.util.List;

public class IntentAgentAbilitySlice extends AbilitySlice {
    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_ability_intent_agent);

        // 指定要启动的Ability的BundleName和AbilityName字段
        // 将Operation对象设置到Intent中
        Operation operation = new Intent.OperationBuilder()
                .withDeviceId("")
                .withBundleName("com.chw.pktest")
                .withAbilityName("com.chw.pktest.IntentAgentAbility")
                .build();
        intent.setOperation(operation);

        List<Intent> intents = new ArrayList<>();
        intents.add(intent);

        int requestCode = 1;
        IntentParams intentParams = new IntentParams();
        // 指定启动一个有页面的Ability
        IntentAgentInfo intentAgentInfo = new IntentAgentInfo(requestCode,
                IntentAgentConstant.OperationType.START_ABILITY,
                IntentAgentConstant.Flags.UPDATE_PRESENT_FLAG,
                intents, intentParams
        );
        // 获取IntentAgent实例
        IntentAgent intentAgent = IntentAgentHelper.getIntentAgent(this, intentAgentInfo);

        String permission="SEND_COMMON_EVENT时的权限";
        TriggerInfo triggerInfo = new TriggerInfo(permission,intentParams,intent,1);
        IntentAgentHelper.triggerIntentAgent(this, intentAgent,
                null,
                null,
                triggerInfo);
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
