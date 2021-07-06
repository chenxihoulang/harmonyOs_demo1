package com.chw.pktest;

import com.chw.pktest.slice.NetworkAbilitySlice;
import ohos.aafwk.ability.Ability;
import ohos.aafwk.content.Intent;

/**
 * 网络管理
 */
public class NetworkAbility extends Ability {
    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setMainRoute(NetworkAbilitySlice.class.getName());


    }
}
