package com.chw.pktest;

import com.chw.pktest.slice.IntentAgentAbilitySlice;
import ohos.aafwk.ability.Ability;
import ohos.aafwk.content.Intent;

public class IntentAgentAbility extends Ability {
    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setMainRoute(IntentAgentAbilitySlice.class.getName());
    }
}
