package com.auto.helper.one.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityManager;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.auto.helper.one.R;
import com.auto.helper.one.automator.AutoServiceHelper;
import com.auto.helper.one.automator.AutoTask;
import com.auto.helper.one.automator.AutoTaskManager;
import com.auto.helper.one.common.app.AppModule;
import com.auto.helper.one.ui.adapter.AutoTaskAdapter;

import java.util.List;

public class FirstFragment extends Fragment {
    private AutoTaskAdapter mAutoTaskAdapter;
    private AccessibilityManager mAccessibilityManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_first, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();

        TextView firstTextView = getActivity().findViewById(R.id.textview_first);
        firstTextView.setText(AutoServiceHelper.isServiceEnable() ? "服务已开启" : "服务已关闭");

        if (mAutoTaskAdapter != null) {
            mAutoTaskAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ListView listView = view.findViewById(R.id.listView);

        List<? extends AutoTask> allCases = AutoTaskManager.getAllCases();

        mAutoTaskAdapter = new AutoTaskAdapter(getContext(), allCases);

        listView.setAdapter(mAutoTaskAdapter);

        view.findViewById(R.id.button_first).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppModule.getApp().openServiceActivity();
            }
        });
    }
}