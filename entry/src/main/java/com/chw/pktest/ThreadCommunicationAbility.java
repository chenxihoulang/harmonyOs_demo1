package com.chw.pktest;

import com.chw.pktest.slice.ThreadCommunicationAbilitySlice;
import ohos.aafwk.ability.Ability;
import ohos.aafwk.content.Intent;

public class ThreadCommunicationAbility extends Ability {
    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setMainRoute(ThreadCommunicationAbilitySlice.class.getName());
    }
}
