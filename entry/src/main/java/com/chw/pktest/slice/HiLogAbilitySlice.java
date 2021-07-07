package com.chw.pktest.slice;

import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.hiviewdfx.HiLog;
import ohos.hiviewdfx.HiLogLabel;

/**
 * HiLog日志系统
 */
public class HiLogAbilitySlice extends AbilitySlice {
    //参数type：用于指定输出日志的类型。HiLog中当前只提供了一种日志类型，即应用日志类型LOG_APP
    //参数domain：用于指定输出日志所对应的业务领域，取值范围为0x0~0xFFFFF，开发者可以根据需要进行自定义
    //参数tag：用于指定日志标识，可以为任意字符串，建议标识调用所在的类或者业务行为。
    static final HiLogLabel LABEL = new HiLogLabel(HiLog.LOG_APP, 0x00201, "MY_TAG");

    @Override
    protected void onStart(Intent intent) {
        super.onStart(intent);

        //每个参数需添加隐私标识，分为{public}或{private}，默认为{private}。
        // {public}表示日志打印结果可见；{private}表示日志打印结果不可见，输出结果为<private>
        HiLog.warn(LABEL, "Failed to visit %{private}s, reason:%{public}d.", "private", "public");
    }
}
