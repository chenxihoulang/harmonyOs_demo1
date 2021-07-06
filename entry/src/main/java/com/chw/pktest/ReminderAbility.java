package com.chw.pktest;

import com.chw.pktest.slice.ReminderAbilitySlice;
import ohos.aafwk.ability.Ability;
import ohos.aafwk.content.Intent;

public class ReminderAbility extends Ability {
    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setMainRoute(ReminderAbilitySlice.class.getName());
    }
}
