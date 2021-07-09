package com.chw.pktest;

import com.chw.pktest.slice.AdaptiveBoxLayoutAbilitySlice;
import ohos.aafwk.ability.Ability;
import ohos.aafwk.content.Intent;

public class AdaptiveBoxLayoutAbility extends Ability {
    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setMainRoute(AdaptiveBoxLayoutAbilitySlice.class.getName());
    }
}
