package com.chw.pktest;

import com.chw.pktest.slice.ThreadTestAbilitySlice;
import ohos.aafwk.ability.Ability;
import ohos.aafwk.content.Intent;

public class ThreadTestAbility extends Ability {
    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setMainRoute(ThreadTestAbilitySlice.class.getName());
    }
}
