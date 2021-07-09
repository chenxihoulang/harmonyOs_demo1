package com.chw.pktest.slice;

import com.chw.pktest.ResourceTable;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.agp.components.ComponentContainer;
import ohos.agp.components.Text;

public class StackLayoutAbilitySlice extends AbilitySlice {
    @Override
    protected void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_ability_stack_layout);

        ComponentContainer stackLayout = (ComponentContainer) findComponentById(ResourceTable.Id_slContainer);
        Text text = (Text) findComponentById(ResourceTable.Id_text0);
        stackLayout.moveChildToFront(text);
    }
}
