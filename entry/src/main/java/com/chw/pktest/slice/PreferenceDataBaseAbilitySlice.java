package com.chw.pktest.slice;

import com.chw.pktest.ResourceTable;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.data.DatabaseHelper;
import ohos.data.preferences.Preferences;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 轻量级偏好数据库
 */
public class PreferenceDataBaseAbilitySlice extends AbilitySlice {
    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_ability_preference_data_base);

        DatabaseHelper helper= new DatabaseHelper(this);
        // fileName表示文件名，其取值不能为空，也不能包含路径，默认存储目录可以通过context.getPreferencesDir()获取。
        String fileName = "user_config";
        Preferences preferences = helper.getPreferences(fileName);

        DatabaseHelper databaseHelper = new DatabaseHelper(this); // context入参类型为ohos.app.Context。

        Preferences preferences1 = databaseHelper.getPreferences(fileName);

        preferences.getString("userId","");

        preferences.registerObserver(new Preferences.PreferencesObserver() {
            @Override
            public void onChange(Preferences preferences, String key) {
                //key对应的数据变化了

            }
        });

        helper.deletePreferences(fileName);
        helper.removePreferencesFromCache(fileName);


        // 向preferences实例注册观察者
        PreferencesChangeCounter counter = new PreferencesChangeCounter();
        preferences.registerObserver(counter);
        // 修改数据
        preferences.putInt("intKey", 3);
        boolean result = preferences.flushSync();
        // 修改数据后，onChange方法会被回调，notifyTimes == 1
        int notifyTimes = counter.notifyTimes.intValue();
        // 向preferences实例注销观察者
        preferences.unregisterObserver(counter);

        //targetContext和srcContext需通过在对应AbilitySlice或Ability中，使用“getApplicationContext()”方法获取。
        // srcFile表示源文件名或者源文件的绝对路径，不能为相对路径，其取值不能为空。当srcFile只传入文件名时，srcContext不能为空。
        String srcFile = "srcFile";
        // targetFile表示目标文件名，其取值不能为空，也不能包含路径。
        String targetFile = "targetFile";
        boolean result1 = databaseHelper.movePreferences(getApplicationContext(), srcFile, targetFile);
    }

    private class PreferencesChangeCounter implements Preferences.PreferencesObserver {
        final AtomicInteger notifyTimes = new AtomicInteger(0);
        @Override
        public void onChange(Preferences preferences, String key) {
            if ("intKey".equals(key)) {
                notifyTimes.incrementAndGet();
            }
        }
    }
}
