package com.chw.pktest;

import com.chw.pktest.slice.PermissionAbilitySlice;
import ohos.aafwk.ability.Ability;
import ohos.aafwk.content.Intent;

public class PermissionAbility extends Ability {
    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setMainRoute(PermissionAbilitySlice.class.getName());
    }
}
