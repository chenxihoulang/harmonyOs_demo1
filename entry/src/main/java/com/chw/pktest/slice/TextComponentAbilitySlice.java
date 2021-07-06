package com.chw.pktest.slice;

import com.chw.pktest.ResourceTable;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.agp.components.Text;

public class TextComponentAbilitySlice extends AbilitySlice {
    @Override
    protected void onStart(Intent intent) {
        super.onStart(intent);
        setUIContent(ResourceTable.Layout_ability_component_layout);

        Text text= (Text) findComponentById(ResourceTable.Id_text);

        // 跑马灯效果
        text.setTruncationMode(Text.TruncationMode.AUTO_SCROLLING);
        // 始终处于自动滚动状态
        text.setAutoScrollingCount(Text.AUTO_SCROLLING_FOREVER);
        // 启动跑马灯效果
        text.startAutoScrolling();
    }
}
