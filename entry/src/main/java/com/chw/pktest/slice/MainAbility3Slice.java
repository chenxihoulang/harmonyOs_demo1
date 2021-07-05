package com.chw.pktest.slice;

import com.chw.pktest.ResourceTable;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.global.i18n.text.DateFormatUtil;

public class MainAbility3Slice extends AbilitySlice {
    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_ability_main3);
    }

    @Override
    public void onActive() {
        super.onActive();


    }

    @Override
    public void onForeground(Intent intent) {
        super.onForeground(intent);
    }
}
