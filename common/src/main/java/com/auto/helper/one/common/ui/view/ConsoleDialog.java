package com.auto.helper.one.common.ui.view;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.auto.helper.one.common.R;
import com.auto.helper.one.common.app.AppModule;
import com.auto.helper.one.common.keep.AutoKeep;
import com.auto.helper.one.common.model.LogData;
import com.auto.helper.one.common.ui.adapter.BaseWrapperAdapter;
import com.auto.helper.one.common.ui.adapter.LogViewAdapter;


@AutoKeep
public class ConsoleDialog extends Dialog {
    private static final Handler mMainHandler = new Handler(Looper.getMainLooper());
    private BaseWrapperAdapter<LogData> mAdapter;
    private int X;
    private int Y;
    private int offsetX;
    private int offsetY;
    private WindowManager.LayoutParams lp;


    public ConsoleDialog(@NonNull Context context) {
        super(context);
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        initWindowAttr();
        init();
        setConsoleMode(false);
    }


    private void setConsoleMode(boolean consoleMode) {
        if (consoleMode) {
            consoleMode();
        } else {
            floatMode();
        }
    }

    private void init() {
        mAdapter = new LogViewAdapter(getContext(), null);
        setCanceledOnTouchOutside(false);
    }

    private void floatMode() {
        setContentView(R.layout.dialog_console_round_layout);
        setWindowStyle(false);

        ImageView imageView = findViewById(R.id.btn_plus);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setConsoleMode(true);
            }
        });
    }


    private void setWindowStyle(boolean consoleMode) {
        //背景没有模糊
        if (consoleMode) {
            getWindow().setLayout(dpToPx(300), WindowManager.LayoutParams.WRAP_CONTENT);
            getWindow().setBackgroundDrawable(new ColorDrawable(0X80000000));//透明背景
        } else {
            getWindow().setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
            getWindow().setBackgroundDrawable(new ColorDrawable(0X00000000));//透明背景
        }
        lp = getWindow().getAttributes();
        getWindow().setGravity(Gravity.TOP | Gravity.LEFT);
        offsetX = lp.x = (int) getContext().getResources().getDimension(R.dimen.console_dialog_left);
        offsetY = lp.y = (int) getContext().getResources().getDimension(R.dimen.console_dialog_top);
        lp.flags = WindowManager.LayoutParams.FLAG_FULLSCREEN |
                WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL |
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE |
                WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH |
                WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN |
                WindowManager.LayoutParams.FLAG_LAYOUT_INSET_DECOR;
        getWindow().setDimAmount(0f);//背景没有模糊
        getWindow().setAttributes(lp);
    }

    private void consoleMode() {
        setContentView(R.layout.dialog_console_expand_layout);

        setWindowStyle(true);

        MaxListView logView = findViewById(R.id.logView);
        logView.setListMaxViewHeight(dpToPx(300));
        ImageView clearButton = findViewById(R.id.clear);
        ImageView zoomButton = findViewById(R.id.zoom);
        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAdapter.clear();
            }
        });
        zoomButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setConsoleMode(false);
            }
        });

        logView.setAdapter(mAdapter);
    }

    @Override
    public void show() {
        if (AppModule.getApp().canDrawOverlays()) {
            super.show();
            return;
        }
        Toast.makeText(getContext(), "悬浮窗权限未开启", Toast.LENGTH_SHORT).show();
        AppModule.getApp().openOverlaySettingActivity();//打开悬浮窗
    }


    private void initWindowAttr() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            getWindow().setType(WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY);/*WindowManager.LayoutParams.TYPE_VOLUME_OVERLAY TYPE_SYSTEM_DIALOG*/
        }
    }

    @Override
    public void onWindowAttributesChanged(WindowManager.LayoutParams params) {
        super.onWindowAttributesChanged(params);
    }

    private int dpToPx(float dpValue) {
        // 获取屏幕密度
        final float scale = getContext().getResources().getDisplayMetrics().density;
        // 结果+0.5是为了int取整时更接近
        return (int) (dpValue * scale + 0.5f);
    }

    public void addData(LogData logData) {
        mMainHandler.post(new Runnable() {
            @Override
            public void run() {
                mAdapter.addData(logData);
            }
        });
    }

    @Override
    public boolean onTouchEvent(@NonNull MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                X = (int) event.getRawX();
                Y = (int) event.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:  //移动
                int nowX = (int) event.getRawX();
                int nowY = (int) event.getRawY();
                int movedX = nowX - X;
                int movedY = nowY - Y;
                X = nowX;
                Y = nowY;
                offsetX = offsetX + movedX;
                offsetY = offsetY + movedY;
                lp.x = offsetX;
                lp.y = offsetY;
                getWindow().setAttributes(lp);
                break;
            default:
                break;
        }
        return super.onTouchEvent(event);
    }
}
