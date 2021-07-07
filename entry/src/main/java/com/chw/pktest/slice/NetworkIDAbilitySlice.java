package com.chw.pktest.slice;

import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.ability.continuation.IContinuationDeviceCallback;
import ohos.aafwk.ability.continuation.IContinuationRegisterManager;
import ohos.aafwk.ability.continuation.RequestCallback;
import ohos.aafwk.content.Intent;
import ohos.account.AccountAbility;
import ohos.agp.colors.RgbColor;
import ohos.agp.components.*;
import ohos.agp.components.element.ShapeElement;
import ohos.agp.utils.Color;

import java.util.UUID;

public class NetworkIDAbilitySlice extends AbilitySlice {
    private static final int OFFSET_X = 100;
    private static final int OFFSET_Y = 100;
    private static final int ADD_OFFSET_Y = 150;
    private static final int BUTTON_WIDTH = 800;
    private static final int BUTTON_HEIGHT = 100;
    private static final int TEXT_SIZE = 50;
    private int offsetY = 0;

    // 应用包名
    private String BUNDLE_NAME = "com.chw.pktest";
    // 注册控制中心服务后返回的Ability token
    private int abilityToken;
    // 用户在设备列表中选择设备后返回的NetworkID
    private String selectNetworkId;
    // 获取控制中心服务管理类
    private IContinuationRegisterManager continuationRegisterManager;

    // 设置控制中心设备状态变更的回调
    private IContinuationDeviceCallback callback = new IContinuationDeviceCallback() {
        @Override
        public void onDeviceConnectDone(String networkId, String deviceType) {
            // 在用户选择设备后回调获取NetworkID
            selectNetworkId = networkId;
        }

        @Override
        public void onDeviceDisconnectDone(String networkId) {
        }
    };

    // 设置注册控制中心服务回调
    private RequestCallback requsetCallback = new RequestCallback() {
        @Override
        public void onResult(int result) {
            abilityToken = result;
        }
    };
    // 弹出选择设备列表
    private Component.ClickedListener mShowDeviceListListener = new Component.ClickedListener() {
        @Override
        public void onClick(Component arg0) {
            // 显示选择设备列表
            continuationRegisterManager.showDeviceList(abilityToken, null, null);
        }
    };

    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        String distributedVirtualDeviceId = AccountAbility.getAccountAbility().getDistributedVirtualDeviceId();
        UUID uuid = UUID.randomUUID();

        continuationRegisterManager = getContinuationRegisterManager();

        // 开发者可以自行进行界面设计
        // 为按钮设置统一的背景色
        // 例如通过PositionLayout可以实现简单界面
        PositionLayout layout = new PositionLayout(this);
        ComponentContainer.LayoutConfig config = new ComponentContainer.LayoutConfig(
                ComponentContainer.LayoutConfig.MATCH_PARENT,
                ComponentContainer.LayoutConfig.MATCH_PARENT);
        layout.setLayoutConfig(config);
        ShapeElement buttonBg = new ShapeElement();
        buttonBg.setRgbColor(new RgbColor(0, 125, 255));
        Button btnShowDeviceList = createButton("ShowDeviceList", buttonBg);
        btnShowDeviceList.setClickedListener(mShowDeviceListListener);
        layout.addComponent(btnShowDeviceList);
        super.setUIContent(layout);

        // 注册控制中心
        continuationRegisterManager.register(BUNDLE_NAME, null, callback,requsetCallback);
    }

    private Button createButton(String text, ShapeElement buttonBg) {
        Button button = new Button(this);
        button.setContentPosition(OFFSET_X, OFFSET_Y + offsetY);
        offsetY += ADD_OFFSET_Y;
        button.setWidth(BUTTON_WIDTH);
        button.setHeight(BUTTON_HEIGHT);
        button.setTextSize(TEXT_SIZE);
        button.setTextColor(Color.YELLOW);
        button.setText(text);
        button.setBackground(buttonBg);
        return button;
    }

    @Override
    public void onStop() {
        super.onStop();
        // 解注册控制中心
        continuationRegisterManager.unregister(abilityToken, null);
    }
}
