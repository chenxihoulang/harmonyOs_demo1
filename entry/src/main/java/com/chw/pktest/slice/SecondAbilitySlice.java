package com.chw.pktest.slice;

import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.agp.colors.RgbColor;
import ohos.agp.components.DependentLayout;
import ohos.agp.components.Text;
import ohos.agp.components.element.ShapeElement;

public class SecondAbilitySlice extends AbilitySlice {
    @Override
    protected void onStart(Intent intent) {
        super.onStart(intent);

        DependentLayout layout = new DependentLayout(this);
        layout.setWidth(DependentLayout.LayoutConfig.MATCH_PARENT);
        layout.setHeight(DependentLayout.LayoutConfig.MATCH_PARENT);

        ShapeElement background = new ShapeElement();
        background.setRgbColor(new RgbColor(255, 255, 255));
        layout.setBackground(background);

        Text text = new Text(this);
        text.setText("hi there");
        text.setWidth(DependentLayout.LayoutConfig.MATCH_PARENT);
        text.setTextSize(100);

        DependentLayout.LayoutConfig layoutConfig = new DependentLayout.LayoutConfig(DependentLayout.LayoutConfig.MATCH_CONTENT,
                DependentLayout.LayoutConfig.MATCH_CONTENT);
        layoutConfig.addRule(DependentLayout.LayoutConfig.CENTER_IN_PARENT);
        text.setLayoutConfig(layoutConfig);

        layout.addComponent(text);

        super.setUIContent(layout);
    }
}
