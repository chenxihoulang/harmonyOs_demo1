package com.chw.pktest.slice;

import com.chw.pktest.HelloWorldAbility;
import com.chw.pktest.MainAbility;
import com.chw.pktest.ResourceTable;
import com.chw.pktest.utils.LogUtils;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.ability.IAbilityContinuation;
import ohos.aafwk.ability.startsetting.AbilityStartSetting;
import ohos.aafwk.content.Intent;
import ohos.aafwk.content.IntentParams;
import ohos.agp.components.Button;
import ohos.agp.components.Component;
import ohos.bundle.ElementName;
import ohos.security.NetworkSecurityPolicy;

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
            Intent intent1=new Intent();
            ElementName elementName = new ElementName();
            elementName.setAbilityName(MainAbility.class.getName());
            intent1.setElement(elementName);

//            startAbility(intent1);
            startAbilityForResult(intent1,1, AbilityStartSetting.getEmptySetting());
        });
    }

    /**
     * presentForResult请求的返回结果
     */
    @Override
    protected void onResult(int requestCode, Intent resultIntent) {
        super.onResult(requestCode, resultIntent);
        if (requestCode == 1) {
            LogUtils.info(TAG,"android onActivityResult");
        }
    }

    /**
     * startAbilityForResult请求的返回结果
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
