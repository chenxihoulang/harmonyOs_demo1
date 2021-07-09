package com.chw.pktest.slice;

import com.chw.pktest.ResourceTable;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.agp.components.AttrHelper;
import ohos.agp.components.Component;
import ohos.agp.components.TableLayout;

public class TabLayoutAbilitySlice extends AbilitySlice {
    @Override
    protected void onStart(Intent intent) {
        super.onStart(intent);
        setUIContent(ResourceTable.Layout_ability_tab_layout);

        Component component = findComponentById(ResourceTable.Id_text);
        TableLayout.LayoutConfig tlc = new TableLayout.LayoutConfig(vp2px(72), vp2px(72));
        tlc.columnSpec = TableLayout.specification(TableLayout.DEFAULT, 2);
        tlc.rowSpec = TableLayout.specification(TableLayout.DEFAULT, 2);

        tlc.columnSpec = TableLayout.specification(TableLayout.DEFAULT, 2, TableLayout.Alignment.ALIGNMENT_FILL);
        tlc.rowSpec = TableLayout.specification(TableLayout.DEFAULT, 2, TableLayout.Alignment.ALIGNMENT_FILL);

        component.setLayoutConfig(tlc);
    }

    private int vp2px(float vp) {
        return AttrHelper.vp2px(vp, this);
    }

    private int fp2px(float fp) {
        return AttrHelper.fp2px(fp, this);
    }
}
