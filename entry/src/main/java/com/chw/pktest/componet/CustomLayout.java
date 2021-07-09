package com.chw.pktest.componet;

import ohos.agp.components.AttrSet;
import ohos.agp.components.Component;
import ohos.agp.components.ComponentContainer;
import ohos.app.Context;

import java.util.HashMap;
import java.util.Map;

public class CustomLayout extends ComponentContainer
    implements ComponentContainer.EstimateSizeListener, ComponentContainer.ArrangeListener {
    private final Map<Integer, Position> axis = new HashMap<>();

    private int locationX;
    private int locationY;

    private int maxWidth;
    private int maxHeight;

    private int lastHeight;

    public CustomLayout(Context context) {
        super(context);
        setEstimateSizeListener(this);
        setArrangeListener(this);
    }

    @Override
    public boolean onEstimateSize(int widthEstimatedConfig, int heightEstimatedConfig) {
        //测量各子组件宽高
        measureChildren(widthEstimatedConfig, heightEstimatedConfig);
        //容器组件宽度
        int width = EstimateSpec.getSize(widthEstimatedConfig);

        for (int idx = 0; idx < getChildCount(); idx++) {
            Component childView = getComponentAt(idx);
            addChild(childView, idx, width);
        }

        setEstimatedSize(EstimateSpec.getChildSizeWithMode(maxWidth, widthEstimatedConfig, 0),
            EstimateSpec.getChildSizeWithMode(maxHeight, heightEstimatedConfig, 0));

        return true;
    }

    private void measureChildren(int widthEstimatedConfig, int heightEstimatedConfig) {
        for (int idx = 0; idx < getChildCount(); idx++) {
            Component childView = getComponentAt(idx);
            if (childView != null) {
                measureChild(childView, widthEstimatedConfig, heightEstimatedConfig);
            }
        }
    }

    private void measureChild(Component child, int parentWidthMeasureSpec, int parentHeightMeasureSpec) {
        LayoutConfig lc = child.getLayoutConfig();
        int childWidthMeasureSpec =
            EstimateSpec.getChildSizeWithMode(lc.width, parentWidthMeasureSpec, EstimateSpec.UNCONSTRAINT);
        int childHeightMeasureSpec =
            EstimateSpec.getChildSizeWithMode(lc.height, parentHeightMeasureSpec, EstimateSpec.UNCONSTRAINT);
        child.estimateSize(childWidthMeasureSpec, childHeightMeasureSpec);
    }

    private void addChild(Component component, int id, int layoutWidth) {
        Position position = new Position();
        position.positionX = locationX + component.getMarginLeft();
        position.positionY = locationY + component.getMarginTop();
        position.width = component.getEstimatedWidth();
        position.height = component.getEstimatedHeight();
        if ((locationX + position.width) > layoutWidth) {
            locationX = 0;
            locationY += lastHeight;
            lastHeight = 0;
            position.positionX = locationX + component.getMarginLeft();
            position.positionY = locationY + component.getMarginTop();
        }
        axis.put(id, position);

        lastHeight = Math.max(lastHeight, position.height + component.getMarginBottom());
        locationX += position.width + component.getMarginRight();
        maxWidth = Math.max(maxWidth, position.positionX + position.width);
        maxHeight = Math.max(maxHeight, position.positionY + position.height);
    }

    @Override
    public boolean onArrange(int left, int top, int width, int height) {
        for (int idx = 0; idx < getChildCount(); idx++) {
            Component childView = getComponentAt(idx);
            Position position = axis.get(idx);
            if (position != null) {
                childView.arrange(position.positionX, position.positionY, position.width, position.height);
            }
        }
        return true;
    }

    /**
     * Layout
     *
     * @since 2021-05-08
     */
    private static class Position {
        int positionX = 0;

        int positionY = 0;

        int width = 0;

        int height = 0;
    }
}