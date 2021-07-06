package com.chw.pktest;

import com.chw.pktest.slice.NotificationAbilitySlice;
import ohos.aafwk.ability.Ability;
import ohos.aafwk.content.Intent;

public class NotificationAbility extends Ability {
    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setMainRoute(NotificationAbilitySlice.class.getName());
    }
}
