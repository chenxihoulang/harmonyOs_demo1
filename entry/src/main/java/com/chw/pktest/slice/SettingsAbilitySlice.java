package com.chw.pktest.slice;

import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.ability.DataAbilityHelper;
import ohos.aafwk.ability.IDataAbilityObserver;
import ohos.aafwk.content.Intent;
import ohos.sysappcomponents.settings.SystemSettings;
import ohos.utils.net.Uri;

public class SettingsAbilitySlice extends AbilitySlice {
    DataAbilityHelper dataAbilityHelper = DataAbilityHelper.creator(this);
    private Uri timeFormatUri = SystemSettings.getUri(SystemSettings.Date.TIME_FORMAT);
    private IDataAbilityObserver mObserver;

    @Override
    protected void onStart(Intent intent) {
        super.onStart(intent);
        mObserver = new IDataAbilityObserver() {
            @Override
            public void onChange() {
                String timeFormat = SystemSettings.getValue(dataAbilityHelper, SystemSettings.Date.TIME_FORMAT);
                setTimeFormat(timeFormat);
            }
        };
        dataAbilityHelper.registerObserver(timeFormatUri, mObserver);
    }

    void setTimeFormat(String timeFormat) {
        String timeStr = "12";
        if (timeStr.equals(timeFormat)) {
            // Display in 12-hour format
        } else {
            // Display in 24-hour format
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        dataAbilityHelper.unregisterObserver(timeFormatUri, mObserver);
    }
}
