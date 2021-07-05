package com.chw.pktest;

import com.chw.pktest.slice.HelloWorldAbilitySlice;
import com.chw.pktest.slice.SecondAbilitySlice;
import com.chw.pktest.utils.LogUtils;
import ohos.aafwk.ability.Ability;
import ohos.aafwk.ability.IAbilityContinuation;
import ohos.aafwk.content.Intent;
import ohos.aafwk.content.IntentParams;
import ohos.bundle.AbilityInfo;
import ohos.utils.PacMap;
/**
 * 跨设备迁移数据
 */
public class HelloWorldAbility extends Ability implements IAbilityContinuation {
    private static final String TAG = HelloWorldAbility.class.getSimpleName();

    @Override
    protected void onInitialized(AbilityInfo abilityInfo) {
        super.onInitialized(abilityInfo);
    }

    /**
     * 当系统首次创建Page实例时，触发该回调。对于一个Page实例，该回调在其生命周期过程中仅触发一次，Page在该逻辑后将进入INACTIVE状态。
     * 开发者必须重写该方法，并在此配置默认展示的AbilitySlice。
     */
    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setMainRoute(HelloWorldAbilitySlice.class.getName());

        LogUtils.info(TAG, "android onCreate");

        addActionRoute("action.hello", HelloWorldAbilitySlice.class.getName());
        addActionRoute("action.second", SecondAbilitySlice.class.getName());
    }

    /**
     * 处于BACKGROUND状态的Page仍然驻留在内存中，当重新回到前台时（比如用户重新导航到此Page），
     * 系统将先调用onForeground()回调通知开发者，而后Page的生命周期状态回到INACTIVE状态。
     * 开发者应当在此回调中重新申请在onBackground()中释放的资源，最后Page的生命周期状态进一步回到ACTIVE状态，系统将通过onActive()回调通知开发者用户。
     */
    @Override
    protected void onForeground(Intent intent) {
        super.onForeground(intent);
        LogUtils.info(TAG, "android onRestart");
    }

    /**
     * Page会在进入INACTIVE状态后来到前台，然后系统调用此回调。Page在此之后进入ACTIVE状态，该状态是应用与用户交互的状态。
     * Page将保持在此状态，除非某类事件发生导致Page失去焦点，比如用户点击返回键或导航到其他Page。
     * 当此类事件发生时，会触发Page回到INACTIVE状态，系统将调用onInactive()回调。
     * 此后，Page可能重新回到ACTIVE状态，系统将再次调用onActive()回调。
     * 因此，开发者通常需要成对实现onActive()和onInactive()，并在onActive()中获取在onInactive()中被释放的资源。
     */
    @Override
    protected void onActive() {
        super.onActive();
        LogUtils.info(TAG, "android onResume");
    }

    /**
     * 当Page失去焦点时，系统将调用此回调，此后Page进入INACTIVE状态。开发者可以在此回调中实现Page失去焦点时应表现的恰当行为。
     */
    @Override
    protected void onInactive() {
        super.onInactive();
        LogUtils.info(TAG, "android onPause");
    }

    /**
     * 如果Page不再对用户可见，系统将调用此回调通知开发者用户进行相应的资源释放，此后Page进入BACKGROUND状态。
     * 开发者应该在此回调中释放Page不可见时无用的资源，或在此回调中执行较为耗时的状态保存操作
     */
    @Override
    protected void onBackground() {
        super.onBackground();
        LogUtils.info(TAG, "android onStop");
    }

    /**
     * 系统将要销毁Page时，将会触发此回调函数，通知用户进行系统资源的释放。销毁Page的可能原因包括以下几个方面：
     * 用户通过系统管理能力关闭指定Page，例如使用任务管理器关闭Page。
     * 用户行为触发Page的terminateAbility()方法调用，例如使用应用的退出功能。
     * 配置变更导致系统暂时销毁Page并重建。
     * 系统出于资源管理目的，自动触发对处于BACKGROUND状态Page的销毁。
     */
    @Override
    protected void onStop() {
        super.onStop();
        LogUtils.info(TAG, "android onDestroy");
    }

    /**
     * Page请求迁移后，系统首先回调此方法，开发者可以在此回调中决策当前是否可以执行迁移，比如，弹框让用户确认是否开始迁移
     */
    @Override
    public boolean onStartContinuation() {
        return false;
    }

    /**
     * 如果onStartContinuation()返回true，则系统回调此方法，开发者在此回调中保存必须传递到另外设备上以便恢复Page状态的数据
     */
    @Override
    public boolean onSaveData(IntentParams intentParams) {
        return false;
    }

    /**
     * 源侧设备上Page完成保存数据后，系统在目标侧设备上回调此方法，开发者在此回调中接受用于恢复Page状态的数据。
     * 注意，在目标侧设备上的Page会重新启动其生命周期，无论其启动模式如何配置。且系统回调此方法的时机在onStart()之前
     */
    @Override
    public boolean onRestoreData(IntentParams intentParams) {
        return false;
    }

    /**
     * 目标侧设备上恢复数据一旦完成，系统就会在源侧设备上回调Page的此方法，以便通知应用迁移流程已结束。
     * 开发者可以在此检查迁移结果是否成功，并在此处理迁移结束的动作，例如，应用可以在迁移完成后终止自身生命周期。
     */
    @Override
    public void onCompleteContinuation(int status) {

    }

    /**
     * 如果开发者使用continueAbilityReversibly()而不是continueAbility()，则此后可以在源侧设备上使用reverseContinueAbility()进行回迁。
     * 这种场景下，相当于同一个Page（的两个实例）同时在两个设备上运行，迁移完成后，如果目标侧设备上Page因任何原因终止，则源侧Page通过此回调接收终止通知
     */
    @Override
    public void onRemoteTerminated() {

    }
}
