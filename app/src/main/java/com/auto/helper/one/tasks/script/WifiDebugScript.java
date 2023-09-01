package com.auto.helper.one.tasks.script;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;

import com.auto.helper.one.automator.Script;
import com.auto.helper.one.common.console.Console;

import java.io.DataOutputStream;

public class WifiDebugScript extends Script {

    private static final String TAG = WifiDebugScript.class.getSimpleName();

    @Override
    public String name() {
        return "wifi调试(需要root权限)";
    }

    @Override
    public boolean onExecute() {
        try {
            WifiManager wifiManager = (WifiManager) mContext.getSystemService(Context.WIFI_SERVICE);
            //判断wifi是否开启
            WifiInfo wifiInfo = wifiManager.getConnectionInfo();
            int ipAddress = wifiInfo.getIpAddress();
            Console.d(TAG, "ip地址：" + ipAddress);
            Process localProcess = Runtime.getRuntime().exec("su\n");
            DataOutputStream os = new DataOutputStream(localProcess.getOutputStream());
            os.writeBytes("setprop service.adb.tcp.port 5555\n");
            os.writeBytes("stop adbd\n");
            os.writeBytes("start adbd\n");
            os.flush();
        } catch (Throwable throwable) {
            Console.e(TAG, throwable.getMessage(), throwable);
        }
        return true;
    }
}
