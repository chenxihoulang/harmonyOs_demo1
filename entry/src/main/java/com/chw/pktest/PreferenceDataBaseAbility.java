package com.chw.pktest;

import com.chw.pktest.slice.PreferenceDataBaseAbilitySlice;
import ohos.aafwk.ability.Ability;
import ohos.aafwk.content.Intent;

/**
 * 轻量级偏好数据库
 */
public class PreferenceDataBaseAbility extends Ability {
    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setMainRoute(PreferenceDataBaseAbilitySlice.class.getName());
    }
}
