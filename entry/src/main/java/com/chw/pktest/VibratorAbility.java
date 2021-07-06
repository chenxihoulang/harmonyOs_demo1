package com.chw.pktest;

import com.chw.pktest.slice.VibratorAbilitySlice;
import ohos.aafwk.ability.Ability;
import ohos.aafwk.content.Intent;

public class VibratorAbility extends Ability {
    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setMainRoute(VibratorAbilitySlice.class.getName());
    }
}
