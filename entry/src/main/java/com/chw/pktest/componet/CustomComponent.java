package com.chw.pktest.componet;

import ohos.agp.components.AttrSet;
import ohos.agp.components.Component;
import ohos.agp.render.Canvas;
import ohos.agp.render.Paint;
import ohos.agp.utils.Color;
import ohos.app.Context;
import ohos.multimodalinput.event.TouchEvent;

public class CustomComponent extends Component implements Component.DrawTask,Component.EstimateSizeListener, Component.TouchEventListener {
    // 圆环宽度
    private static final float CIRCLE_STROKE_WIDTH = 100f;

    // 绘制圆环的画笔
    private Paint circlePaint;

    public CustomComponent(Context context) {
        super(context);

        // 初始化画笔
        initPaint();

        // 添加绘制任务
        addDrawTask(this);

        // 设置TouchEvent响应事件
        setTouchEventListener(this);
    }

    private void initPaint(){
        circlePaint = new Paint();
        circlePaint.setColor(Color.BLUE);
        circlePaint.setStrokeWidth(CIRCLE_STROKE_WIDTH);
        circlePaint.setStyle(Paint.Style.STROKE_STYLE);
    }

    @Override
    public void onDraw(Component component, Canvas canvas) {
        // 在界面中绘制一个圆心坐标为(500,500),半径为400的圆
        canvas.drawCircle(500,500,400,circlePaint);
    }

    @Override
    public boolean onEstimateSize(int i, int i1) {
        return false;
    }

    @Override
    public boolean onTouchEvent(Component component, TouchEvent touchEvent) {
        switch (touchEvent.getAction()) {
            case TouchEvent.PRIMARY_POINT_DOWN:
                circlePaint.setColor(Color.GREEN);
                invalidate();
                break;
            case TouchEvent.PRIMARY_POINT_UP:
                circlePaint.setColor(Color.YELLOW);
                invalidate();
                break;
        }
        return false;
    }
}