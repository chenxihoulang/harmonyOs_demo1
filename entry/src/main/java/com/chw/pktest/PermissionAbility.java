package com.chw.pktest;

import com.chw.pktest.slice.PermissionAbilitySlice;
import com.chw.pktest.utils.LogUtils;
import ohos.aafwk.ability.Ability;
import ohos.aafwk.content.Intent;
import ohos.bundle.IBundleManager;
import ohos.security.SystemPermission;

public class PermissionAbility extends Ability {
    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setMainRoute(PermissionAbilitySlice.class.getName());

        //查询IPC跨进程调用方的进程是否已被授予某权限
        if (verifyCallingPermission(SystemPermission.CAMERA) != IBundleManager.PERMISSION_GRANTED) {
            LogUtils.error("tag", "调用者没有相机权限");
        }
        //查询自身进程是否已被授予某权限
        if (verifySelfPermission(SystemPermission.CAMERA) != IBundleManager.PERMISSION_GRANTED) {
            LogUtils.error("tag", "我们没有相机权限");
            //向系统权限管理模块查询某权限是否不再弹框授权了
            if (canRequestPermission(SystemPermission.CAMERA)) {
                //弹框申请权限

                //向系统权限管理模块申请权限（接口可支持一次申请多个。
                //若下一步操作涉及到多个敏感权限，可以这么用，其他情况建议不要这么用。
                //因为弹框还是按权限组一个个去弹框，耗时比较长。用到哪个权限就去申请哪个）
                requestPermissionsFromUser(new String[]{SystemPermission.CAMERA}, 1);
            } else {
                // 显示应用需要权限的理由，提示用户进入设置授权
            }
        } else {
            // 权限已被授予
        }
    }

    @Override
    public void onRequestPermissionsFromUserResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsFromUserResult(requestCode, permissions, grantResults);
        //权限申请回调
        switch (requestCode) {
            case 1:
                if (grantResults.length>0&&grantResults[0]==IBundleManager.PERMISSION_GRANTED){
                    // 权限被授予
                    // 注意：因时间差导致接口权限检查时有无权限，所以对那些因无权限而抛异常的接口进行异常捕获处理
                }else {
                    // 权限被拒绝
                }
                break;
            default:
                break;
        }
    }
}
