package com.chw.pktest;

import com.chw.pktest.slice.LightAbilitySlice;
import ohos.aafwk.ability.Ability;
import ohos.aafwk.content.Intent;

public class LightAbility extends Ability {
    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setMainRoute(LightAbilitySlice.class.getName());
    }
}
