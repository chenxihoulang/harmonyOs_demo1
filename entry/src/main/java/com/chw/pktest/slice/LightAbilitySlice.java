package com.chw.pktest.slice;

import com.chw.pktest.ResourceTable;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.light.agent.LightAgent;
import ohos.light.bean.LightBrightness;
import ohos.light.bean.LightEffect;

import java.util.List;
import java.util.function.Consumer;

public class LightAbilitySlice extends AbilitySlice {
    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_ability_light);

        LightAgent lightAgent = new LightAgent();
        List<Integer> lightIdList = lightAgent.getLightIdList();
        if (lightIdList.isEmpty()) {
            return;
        }
        int lightId = lightIdList.get(0);
        boolean lightAgentSupport = lightAgent.isSupport(lightId);
        if (lightAgentSupport) {
            boolean effectSupport = lightAgent.isEffectSupport(lightId, LightEffect.LIGHT_ID_KEYBOARD);
            if (effectSupport) {
                lightAgent.turnOn(lightId, LightEffect.LIGHT_ID_KEYBOARD);
            }
        }

        // 创建自定义效果的一次性闪烁
        LightBrightness lightBrightness = new LightBrightness(255, 255, 255);
        LightEffect lightEffect = new LightEffect(lightBrightness, 1000, 1000);
        boolean turnOnEffectResult = lightAgent.turnOn(lightId, lightEffect);

        // 关闭指定的灯
        boolean turnOffResult = lightAgent.turnOff(lightId);
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
