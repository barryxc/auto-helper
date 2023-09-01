package com.auto.helper.one.common.ui.adapter;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import androidx.annotation.IdRes;
import androidx.annotation.LayoutRes;

import com.auto.helper.one.common.keep.AutoKeep;

import java.util.ArrayList;
import java.util.List;

@AutoKeep
public abstract class BaseWrapperAdapter<T> extends BaseAdapter {
    private List<T> mData;
    protected final Context mContext;
    private final int mLayoutResID;

    public BaseWrapperAdapter(Context context, List<T> data, @LayoutRes int layoutResID) {
        mData = data;
        mContext = context;
        mLayoutResID = layoutResID;
    }

    public void addData(T data) {
        if (mData == null) {
            mData = new ArrayList<>();
        }
        mData.add(0, data);
        notifyDataSetChanged();
    }

    public void remove(T data) {
        if (mData != null) {
            mData.remove(data);
            notifyDataSetChanged();
        }
    }

    @Override
    public int getCount() {
        return mData != null ? mData.size() : 0;
    }

    @Override
    public T getItem(int i) {
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
            convertView = LayoutInflater.from(mContext).inflate(mLayoutResID, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        onBindView(holder, i);

        return convertView;
    }

    public abstract void onBindView(ViewHolder viewHolder, int position);

    public void clear() {
        if (mData != null) {
            mData.clear();
            notifyDataSetChanged();
        }
    }

    public static class ViewHolder {
        private final View parentView;
        private final SparseArray<View> mViewSparseArray;


        public ViewHolder(View parentView) {
            this.parentView = parentView;
            mViewSparseArray = new SparseArray<>();
        }

        @SuppressWarnings("unChecked")
        public <V extends View> V findView(@IdRes int viewID) {
            View view = mViewSparseArray.get(viewID);
            if (view != null) {
                return (V) view;
            }
            View child = parentView.findViewById(viewID);
            if (child != null) {
                mViewSparseArray.put(viewID, child);
            }
            return (V) child;
        }
    }
}
