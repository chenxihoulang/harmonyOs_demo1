package com.chw.pktest;

import com.chw.pktest.slice.MainAbility3Slice;
import ohos.aafwk.ability.Ability;
import ohos.aafwk.content.Intent;
import ohos.sysappcomponents.calendar.entity.Reminders;

public class MainAbility3 extends Ability {
    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setMainRoute(MainAbility3Slice.class.getName());
    }
}
