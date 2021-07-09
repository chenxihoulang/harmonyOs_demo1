package com.chw.pktest.slice;

import com.chw.pktest.ResourceTable;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.agp.animation.Animator;
import ohos.agp.animation.AnimatorProperty;
import ohos.agp.animation.AnimatorScatter;
import ohos.agp.animation.AnimatorValue;
import ohos.agp.components.DirectionalLayout;
import ohos.agp.components.element.FrameAnimationElement;

public class AnimationAbilitySlice extends AbilitySlice {
    @Override
    protected void onStart(Intent intent) {
        super.onStart(intent);
        DirectionalLayout layout = new DirectionalLayout(this);

        FrameAnimationElement frameAnimationElement = new FrameAnimationElement(getContext(),
                ResourceTable.Animation_animation_element);
        layout.setBackground(frameAnimationElement);
        frameAnimationElement.start();

        setUIContent(layout);

        AnimatorValue animatorValue = new AnimatorValue();
        animatorValue.setDelay(1000);
        animatorValue.setDuration(2000);
        animatorValue.setLoopedCount(2);
        animatorValue.setCurveType(Animator.CurveType.BOUNCE);

        AnimatorScatter scatter = AnimatorScatter.getInstance(this);
        Animator animator = scatter.parse(ResourceTable.Animation_animator_value);
        if (animator instanceof AnimatorValue) {
            AnimatorValue animatorValue1 = (AnimatorValue) animator;
            animatorValue1.setLoopedCount(2);
            animatorValue1.setCurveType(Animator.CurveType.BOUNCE);
        }

        animatorValue.setValueUpdateListener(new AnimatorValue.ValueUpdateListener() {
            @Override
            public void onUpdate(AnimatorValue animatorValue, float value) {

            }
        });
        animatorValue.start();

        AnimatorProperty animatorProperty = layout.createAnimatorProperty();
    }
}
