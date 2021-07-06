package com.chw.pktest.slice;

import com.chw.pktest.ResourceTable;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.agp.colors.RgbColor;
import ohos.agp.components.*;
import ohos.agp.components.element.ShapeElement;
import ohos.agp.utils.LayoutAlignment;

/**
 * 组件与布局
 */
public class ComponentLayoutAbilitySlice extends AbilitySlice {
    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_ability_component_layout);

        // 声明布局
        DirectionalLayout directionalLayout = new DirectionalLayout(getContext());
        // 设置布局大小
        directionalLayout.setWidth(ComponentContainer.LayoutConfig.MATCH_PARENT);
        directionalLayout.setHeight(ComponentContainer.LayoutConfig.MATCH_PARENT);
        // 设置布局属性
        directionalLayout.setOrientation(Component.VERTICAL);
        directionalLayout.setPadding(32, 32, 32, 32);

        Text text = new Text(getContext());
        text.setText("My name is Text.");
        text.setTextSize(50);
        text.setId(100);
        // 为组件添加对应布局的布局属性
        DirectionalLayout.LayoutConfig layoutConfig = new DirectionalLayout.LayoutConfig(
                ComponentContainer.LayoutConfig.MATCH_CONTENT,
                ComponentContainer.LayoutConfig.MATCH_CONTENT);
        layoutConfig.alignment = LayoutAlignment.HORIZONTAL_CENTER;
        text.setLayoutConfig(layoutConfig);

        // 将Text添加到布局中
        directionalLayout.addComponent(text);

        // 类似的添加一个Button
        Button button = new Button(getContext());
        layoutConfig.setMargins(0, 50, 0, 0);
        button.setLayoutConfig(layoutConfig);
        button.setText("My name is Button.");
        button.setTextSize(50);

        ShapeElement background = new ShapeElement();
        background.setRgbColor(new RgbColor(0, 125, 255));
        background.setCornerRadius(25);
        button.setBackground(background);

        button.setPadding(10, 10, 10, 10);
        button.setClickedListener(new Component.ClickedListener() {
            @Override
            // 在组件中增加对点击事件的检测
            public void onClick(Component component) {
                // 此处添加按钮被点击需要执行的操作
            }
        });
        directionalLayout.addComponent(button);

        // 将布局作为根布局添加到视图树中
        super.setUIContent(directionalLayout);
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
