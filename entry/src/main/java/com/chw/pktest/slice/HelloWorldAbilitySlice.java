package com.chw.pktest.slice;

import com.chw.pktest.HelloWorldAbility;
import com.chw.pktest.MainAbility;
import com.chw.pktest.ResourceTable;
import com.chw.pktest.ServiceAbility;
import com.chw.pktest.utils.LogUtils;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.ability.IAbilityConnection;
import ohos.aafwk.ability.IAbilityContinuation;
import ohos.aafwk.ability.startsetting.AbilityStartSetting;
import ohos.aafwk.content.Intent;
import ohos.aafwk.content.IntentParams;
import ohos.aafwk.content.Operation;
import ohos.agp.components.Button;
import ohos.agp.components.Component;
import ohos.bundle.ElementName;
import ohos.rpc.IRemoteObject;
import ohos.security.NetworkSecurityPolicy;
import ohos.utils.IntentConstants;
import ohos.utils.net.Uri;

import java.util.HashSet;
import java.util.Set;

public class HelloWorldAbilitySlice extends AbilitySlice implements IAbilityContinuation {
    private static final String TAG = HelloWorldAbilitySlice.class.getSimpleName();

    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_ability_hello_world);

        Button btnNavSlice = (Button) findComponentById(ResourceTable.Id_btnNavSlice);
        btnNavSlice.setClickedListener(listener -> {
//            present(new SecondAbilitySlice(), new Intent());
            presentForResult(new SecondAbilitySlice(), new Intent(), 1);
        });

        Button btnNavAbility = (Button) findComponentById(ResourceTable.Id_btnNavAbility);
        btnNavAbility.setClickedListener(listener -> {
            Intent intent1 = new Intent();
            ElementName elementName = new ElementName();
            elementName.setAbilityName(MainAbility.class.getName());
            intent1.setElement(elementName);

//            startAbility(intent1);
            startAbilityForResult(intent1, 1, AbilityStartSetting.getEmptySetting());
        });

        Button btnStartService = (Button) findComponentById(ResourceTable.Id_btnStartService);
        btnStartService.setClickedListener(listener -> {
            Intent intent1 = new Intent();
            Operation operation = new Intent.OperationBuilder()
                    .withDeviceId("")
                    .withBundleName(ServiceAbility.class.getPackage().getName())
                    .withAbilityName(ServiceAbility.class)
                    .build();
            intent1.setOperation(operation);

//            startAbility(intent1);

            // ??????Service
            connectAbility(intent1, connection);
            //????????????
            disconnectAbility(connection);
        });

        Button btnDataProvider = (Button) findComponentById(ResourceTable.Id_btnDataProvider);
        btnDataProvider.setClickedListener(listener -> {
            //??????com.chw.pktest.DataAbility????????????????????????
        });

        Button btnIntent = (Button) findComponentById(ResourceTable.Id_btnIntent);
        btnIntent.setClickedListener(listener -> {
            Set<String> entities = new HashSet<>();
            entities.add(IntentConstants.ENTITY_HOME);

            Intent intent1 = new Intent();
            Operation operation = new Intent.OperationBuilder()
                    .withAction(IntentConstants.ACTION_HOME)
                    .withEntities(entities)
                    .withUri(Uri.parse("pk://www.chw.com"))
                    .withFlags(Intent.FLAG_ABILITY_CONTINUATION)
                    //????????????
                    .withDeviceId("")
                    .withBundleName(ServiceAbility.class.getPackage().getName())
                    .withAbilityName(ServiceAbility.class)
                    .build();
            intent1.setOperation(operation);
            intent1.setParam("name","chw");

//            startAbility(intent1);
            startAbilityForResult(intent1,1);

            Intent intent2 = new Intent();
            Operation operation2 = new Intent.OperationBuilder()
                    .withAction(Intent.ACTION_QUERY_WEATHER)
                    .build();
            intent2.setOperation(operation2);
            startAbilityForResult(intent2, 2);
        });
    }

    // ????????????Service????????????
    private IAbilityConnection connection = new IAbilityConnection() {
        // ?????????Service?????????
        @Override
        public void onAbilityConnectDone(ElementName elementName, IRemoteObject iRemoteObject, int resultCode) {
            // Client??????????????????Service????????????IRemoteObject?????????????????????????????????????????????IRemoteObject?????????????????????????????????????????????????????????
        }

        // Service?????????????????????
        @Override
        public void onAbilityDisconnectDone(ElementName elementName, int resultCode) {
        }
    };

    /**
     * presentForResult?????????????????????
     */
    @Override
    protected void onResult(int requestCode, Intent resultIntent) {
        super.onResult(requestCode, resultIntent);
        if (requestCode == 1) {
            LogUtils.info(TAG, "android onActivityResult");
        }
    }

    /**
     * startAbilityForResult?????????????????????
     */
    @Override
    protected void onAbilityResult(int requestCode, int resultCode, Intent resultData) {
        super.onAbilityResult(requestCode, resultCode, resultData);
    }

    @Override
    protected void onInactive() {
        super.onInactive();
    }

    @Override
    public void onActive() {
        super.onActive();
    }

    @Override
    protected void onBackground() {
        super.onBackground();
    }

    @Override
    public boolean onStartContinuation() {
        return false;
    }

    @Override
    public boolean onSaveData(IntentParams intentParams) {
        return false;
    }

    @Override
    public boolean onRestoreData(IntentParams intentParams) {
        return false;
    }

    @Override
    public void onCompleteContinuation(int i) {

    }

    @Override
    public void onRemoteTerminated() {

    }
}
