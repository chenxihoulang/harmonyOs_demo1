package com.chw.pktest;

import com.chw.pktest.slice.HelloWorldAbilitySlice;
import ohos.aafwk.ability.Ability;
import ohos.aafwk.content.Intent;

public class HelloWorldAbility extends Ability {
    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setMainRoute(HelloWorldAbilitySlice.class.getName());
    }
}
