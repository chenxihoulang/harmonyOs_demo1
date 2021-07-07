package com.chw.pktest.slice;

import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.data.usage.DataUsage;
import ohos.data.usage.MountState;
import ohos.data.usage.Volume;

import java.io.File;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

/**
 * 数据存储管理
 */
public class DataStoreManageAbilitySlice extends AbilitySlice {
    @Override
    protected void onStart(Intent intent) {
        super.onStart(intent);


        MountState state = DataUsage.getDiskMountedStatus();
        if (state == MountState.DISK_MOUNTED) {
            //设备挂载了
            Optional<List<Volume>> volumes = DataUsage.getVolumes();
            boolean diskPluggable = DataUsage.isDiskPluggable();


            File file = new File("/sdcard/example.txt");
            Optional<Volume> volume = DataUsage.getVolume(file);

            volume.ifPresent(new Consumer<Volume>() {
                @Override
                public void accept(Volume volume) {
                    boolean pluggable = volume.isPluggable();
                    boolean emulated = volume.isEmulated();
                    MountState state = volume.getState();
                    String volUuid = volume.getVolUuid();
                }
            });
        }
    }
}
