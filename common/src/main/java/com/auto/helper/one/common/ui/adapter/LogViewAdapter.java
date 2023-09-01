package com.auto.helper.one.common.ui.adapter;

import android.content.Context;
import android.widget.TextView;

import com.auto.helper.one.common.R;
import com.auto.helper.one.common.model.LogData;
import com.auto.helper.one.common.model.LogLevel;

import java.util.List;

public class LogViewAdapter extends BaseWrapperAdapter<LogData> {
    public LogViewAdapter(Context context, List<LogData> data) {
        super(context, data, R.layout.log_view_item);
    }

    @Override
    public void onBindView(ViewHolder viewHolder, int position) {
        TextView logView = viewHolder.findView(R.id.logItem);

        LogData logData = getItem(position);

        if (logData != null) {
            logView.setText(logData.getMessage());

            int level = logData.getLevel();
            switch (level) {
                case LogLevel.D:
                    logView.setTextColor(0XFF00FFFF);//青色
                    break;
                case LogLevel.W:
                    logView.setTextColor(0XFFFF6100);//橙色
                    break;
                case LogLevel.E:
                    logView.setTextColor(0XFFFF0000);//红色
                    break;
            }
        }


    }
}
