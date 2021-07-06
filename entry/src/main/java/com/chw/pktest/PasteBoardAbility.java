package com.chw.pktest;

import com.chw.pktest.slice.PasteBoardAbilitySlice;
import ohos.aafwk.ability.Ability;
import ohos.aafwk.content.Intent;

public class PasteBoardAbility extends Ability {
    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setMainRoute(PasteBoardAbilitySlice.class.getName());
    }
}
