package com.chw.pktest;

import com.chw.pktest.slice.ComponentLayoutAbilitySlice;
import ohos.aafwk.ability.Ability;
import ohos.aafwk.content.Intent;

public class ComponentLayoutAbility extends Ability {
    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setMainRoute(ComponentLayoutAbilitySlice.class.getName());
    }
}
