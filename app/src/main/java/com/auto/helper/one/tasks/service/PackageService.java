package com.auto.helper.one.tasks.service;

import android.widget.Button;
import android.widget.Toast;

import com.auto.helper.one.automator.Service;
import com.auto.helper.one.automator.constanst.Package;
import com.auto.helper.one.automator.node.UiNode;


/**
 * 自动安装和授权
 **/
public class PackageService extends Service {

    @Override
    public String name() {
        return "自动安装和授权";
    }

    @Override
    public String[] packages() {
        return new String[]{Package.SYSTEM_APP_INSTALLER, Package.SYSTEM_APP_PERMISSION};
    }

    @Override
    public boolean onCall() {
        //安装
        UiNode uiNode = selector().text("继续安装").className(Button.class).findOne(1);
        if (uiNode != null) {
            Toast.makeText(mContext, "服务执行自动安装", Toast.LENGTH_SHORT).show();
            uiNode.clickDeeply();
        }


        UiNode installNode = selector().text("允许").id("com.android.permissioncontroller:id/permission_allow_button").findOne(1);
        //权限允许
        if (installNode != null) {
            Toast.makeText(mContext, "服务执行自动允许", Toast.LENGTH_SHORT).show();
            installNode.clickDeeply();
        }
        return true;
    }
}
