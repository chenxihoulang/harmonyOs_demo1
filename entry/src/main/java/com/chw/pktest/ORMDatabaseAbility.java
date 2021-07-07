package com.chw.pktest;

import com.chw.pktest.slice.DatabaseAbilitySlice;
import ohos.aafwk.ability.Ability;
import ohos.aafwk.content.Intent;


public class ORMDatabaseAbility extends Ability {
    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setMainRoute(DatabaseAbilitySlice.class.getName());
    }
}
