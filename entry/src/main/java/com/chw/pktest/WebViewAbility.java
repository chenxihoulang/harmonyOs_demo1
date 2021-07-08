package com.chw.pktest;

import com.chw.pktest.slice.WebViewAbilitySlice;
import ohos.aafwk.ability.Ability;
import ohos.aafwk.content.Intent;

public class WebViewAbility extends Ability {
    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setMainRoute(WebViewAbilitySlice.class.getName());
    }
}
