package com.auto.helper.one.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.auto.helper.one.R;
import com.auto.helper.one.automator.AutoTask;
import com.auto.helper.one.automator.StateListener;

import java.util.List;

public class AutoTaskAdapter extends BaseAdapter implements StateListener {
    private final List<? extends AutoTask> mData;
    private final Context mContext;

    public AutoTaskAdapter(Context context, List<? extends AutoTask> data) {
        mData = data;
        if (mData != null) {

            for (AutoTask autoTask : mData) {
                autoTask.registerListener(this);
            }
        }
        mContext = context;
    }

    @Override
    public int getCount() {
        return mData != null ? mData.size() : 0;
    }

    @Override
    public AutoTask getItem(int i) {
        return mData != null ? mData.get(i) : null;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup parent) {

        ViewHolder holder;
        if (null == convertView) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.simple_list_item, parent, false);
            holder = new ViewHolder();
            holder.mUseCaseName = convertView.findViewById(R.id.name);
            holder.mStartButton = convertView.findViewById(R.id.startBtn);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        AutoTask autoTask = getItem(i);
        holder.mUseCaseName.setText(autoTask.name());

        if (autoTask.isStart()) {
            holder.mStartButton.setImageResource(R.drawable.stop);
        } else {
            holder.mStartButton.setImageResource(R.drawable.start);
        }


        holder.mStartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (autoTask.isStart() && autoTask.stop()) {
                    holder.mStartButton.setImageResource(R.drawable.start);
                    return;
                }

                if (!autoTask.isStart() && autoTask.start()) {
                    holder.mStartButton.setImageResource(R.drawable.stop);
                }
            }
        });

        return convertView;
    }

    @Override
    public void onStateChanged() {
        notifyDataSetChanged();
    }


    static class ViewHolder {
        TextView mUseCaseName;
        ImageView mStartButton;
    }
}
