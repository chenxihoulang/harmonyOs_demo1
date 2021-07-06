package com.chw.pktest.slice;

import com.chw.pktest.ResourceTable;
import com.chw.pktest.utils.LogUtils;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.bundle.IBundleManager;
import ohos.security.SystemPermission;
import ohos.security.permission.PermissionDef;
import ohos.security.permission.PermissionFlags;

public class PermissionAbilitySlice extends AbilitySlice {
    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_ability_permission);
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
