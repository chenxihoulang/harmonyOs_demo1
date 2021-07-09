package com.chw.pktest.slice;

import com.chw.pktest.componet.CustomComponent;
import com.chw.pktest.componet.CustomControlBar;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.agp.colors.RgbColor;
import ohos.agp.components.DirectionalLayout;
import ohos.agp.components.element.ShapeElement;

public class MyComponentAbilitySlice extends AbilitySlice {
    @Override
    protected void onStart(Intent intent) {
        super.onStart(intent);
        DirectionalLayout myLayout = new DirectionalLayout(this);
        DirectionalLayout.LayoutConfig config = new DirectionalLayout.LayoutConfig(
                DirectionalLayout.LayoutConfig.MATCH_PARENT, DirectionalLayout.LayoutConfig.MATCH_PARENT);
        myLayout.setLayoutConfig(config);

        CustomComponent customComponent = new CustomComponent(this);
        DirectionalLayout.LayoutConfig layoutConfig = new DirectionalLayout.LayoutConfig(1080, 1000);
        customComponent.setLayoutConfig(layoutConfig);

        myLayout.addComponent(customComponent);


        DirectionalLayout.LayoutConfig config1 = new DirectionalLayout.LayoutConfig(
                DirectionalLayout.LayoutConfig.MATCH_PARENT, DirectionalLayout.LayoutConfig.MATCH_PARENT);
        myLayout.setLayoutConfig(config1);

        // 在此创建自定义组件，并可设置其属性
        CustomControlBar controlBar = new CustomControlBar(this);
        controlBar.setClickable(true);
        DirectionalLayout.LayoutConfig layoutConfig1 = new DirectionalLayout.LayoutConfig(
                600, 600);
        controlBar.setLayoutConfig(layoutConfig1);
        ShapeElement element = new ShapeElement();
        element.setRgbColor(new RgbColor(0, 0, 0));
        controlBar.setBackground(element);

        // 将此组件添加至布局，并在界面中显示
        myLayout.addComponent(controlBar);

        super.setUIContent(myLayout);
    }
}
