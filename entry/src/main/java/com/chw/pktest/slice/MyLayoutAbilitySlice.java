package com.chw.pktest.slice;

import com.chw.pktest.componet.CustomLayout;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.agp.colors.RgbColor;
import ohos.agp.components.Button;
import ohos.agp.components.Component;
import ohos.agp.components.DependentLayout;
import ohos.agp.components.DirectionalLayout;
import ohos.agp.components.element.ShapeElement;
import ohos.agp.utils.Color;

public class MyLayoutAbilitySlice extends AbilitySlice {
    @Override
    protected void onStart(Intent intent) {
        super.onStart(intent);
        DirectionalLayout  myLayout=new DirectionalLayout(this);
        DirectionalLayout.LayoutConfig config = new DirectionalLayout.LayoutConfig(
                DirectionalLayout.LayoutConfig.MATCH_PARENT, DirectionalLayout.LayoutConfig.MATCH_PARENT);
        myLayout.setLayoutConfig(config);
        CustomLayout customLayout = new CustomLayout(this);
        for (int idx = 0; idx < 15; idx++) {
            customLayout.addComponent(getComponent(idx + 1));
        }

        ShapeElement shapeElement = new ShapeElement();
        shapeElement.setRgbColor(new RgbColor());
        customLayout.setBackground(shapeElement);
        DependentLayout.LayoutConfig layoutConfig = new DependentLayout.LayoutConfig(
                DependentLayout.LayoutConfig.MATCH_PARENT,
                DependentLayout.LayoutConfig.MATCH_CONTENT);
        customLayout.setLayoutConfig(layoutConfig);
        myLayout.addComponent(customLayout);

        super.setUIContent(myLayout);
    }

    //创建子组件
    private Component getComponent(int idx) {
        Button button = new Button(getContext());
        ShapeElement shapeElement = new ShapeElement();
        shapeElement.setRgbColor(new RgbColor());
        button.setBackground(shapeElement);
        button.setTextColor(Color.WHITE);
        DependentLayout.LayoutConfig layoutConfig = new DependentLayout.LayoutConfig(300, 100);
        if (idx == 1) {
            layoutConfig = new DependentLayout.LayoutConfig(1080, 200);
            button.setText("1080 * 200");
        } else if (idx == 6) {
            layoutConfig = new DependentLayout.LayoutConfig(500, 100);
            button.setText("500 * 100");
        } else if (idx == 8) {
            layoutConfig = new DependentLayout.LayoutConfig(600, 600);
            button.setText("600 * 600");
        } else {
            button.setText("Item" + idx);
        }
        layoutConfig.setMargins(10, 10, 10, 10);
        button.setLayoutConfig(layoutConfig);
        return button;
    }
}
