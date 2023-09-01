package com.auto.helper.one.common.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

public class MaxListView extends ListView {
    /**
     * listview高度
     */
    private int listMaxViewHeight;

    public int getListMaxViewHeight() {
        return listMaxViewHeight;
    }

    public void setListMaxViewHeight(int listMaxViewHeight) {
        this.listMaxViewHeight = listMaxViewHeight;
    }

    public MaxListView(Context context) {
        super(context);
    }

    public MaxListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public MaxListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (listMaxViewHeight > -1) {
            heightMeasureSpec = MeasureSpec.makeMeasureSpec(listMaxViewHeight,
                    MeasureSpec.AT_MOST);
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

}