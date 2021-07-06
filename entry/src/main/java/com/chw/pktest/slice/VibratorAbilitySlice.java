package com.chw.pktest.slice;

import com.chw.pktest.ResourceTable;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.vibrator.agent.VibratorAgent;
import ohos.vibrator.bean.VibrationPattern;

import java.util.List;

public class VibratorAbilitySlice extends AbilitySlice {
    private VibratorAgent vibratorAgent = new VibratorAgent();

    private int[] timing = {1000, 1000, 2000, 5000};
    private int[] intensity = {50, 100, 200, 255};

    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_ability_vibrator);

        // 查询硬件设备上的振动器列表
        List<Integer> vibratorList = vibratorAgent.getVibratorIdList();
        if (vibratorList.isEmpty()) {
            return;
        }
        int vibratorId = vibratorList.get(0);

        // 查询指定的振动器是否支持指定的振动效果
        boolean isSupport = vibratorAgent.isEffectSupport(vibratorId,
                VibrationPattern.VIBRATOR_TYPE_CAMERA_CLICK);

        // 创建指定效果的一次性振动
        boolean vibrateEffectResult = vibratorAgent.startOnce(vibratorId,
                VibrationPattern.VIBRATOR_TYPE_CAMERA_CLICK);

        // 创建指定振动时长的一次性振动
        int vibratorTiming = 1000;
        boolean vibrateResult = vibratorAgent.startOnce(vibratorId, vibratorTiming);

        // 以预设的某种振动效果进行循环振动
        boolean vibratorRepeatEffect = vibratorAgent.start(VibrationPattern.VIBRATOR_TYPE_RINGTONE_BOUNCE, true);
        // 控制振动器停止循环振动
        vibratorAgent.stop();

        // 创建自定义效果的周期性波形振动
        int count = 5;
        VibrationPattern vibrationPeriodEffect = VibrationPattern.createPeriod(timing, intensity, count);
        boolean vibratePeriodResult = vibratorAgent.start(vibratorId, vibrationPeriodEffect);

        // 创建自定义效果的一次性振动
        VibrationPattern vibrationOnceEffect = VibrationPattern.createSingle(3000, 50);
        boolean vibrateSingleResult = vibratorAgent.start(vibratorId, vibrationOnceEffect);

        // 关闭指定的振动器自定义模式的振动
        boolean stopResult = vibratorAgent.stop(vibratorId,
                VibratorAgent.VIBRATOR_STOP_MODE_CUSTOMIZED);
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
