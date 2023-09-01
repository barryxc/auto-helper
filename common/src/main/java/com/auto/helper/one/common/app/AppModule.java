package com.auto.helper.one.common.app;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.Application;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;

import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import com.auto.helper.one.common.keep.AutoKeep;

import java.io.File;
import java.lang.ref.WeakReference;
import java.util.List;

@AutoKeep
public class AppModule {
    private Application mContext;
    private WeakReference<Activity> mCurrentActivity = new WeakReference<Activity>(null);
    private static final AppModule APP = new AppModule();

    private AppModule() {
    }

    public synchronized static AppModule getApp() {
        return APP;
    }

    public void init(Application application) {
        mContext = application;
        mContext.registerActivityLifecycleCallbacks(new SimpleActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
                setCurrentActivity(activity);
            }

            @Override
            public void onActivityPaused(Activity activity) {
                setCurrentActivity(null);
            }

            @Override
            public void onActivityResumed(Activity activity) {
                setCurrentActivity(activity);
            }
        });
    }

    private void setCurrentActivity(Activity activity) {
        mCurrentActivity = new WeakReference<>(activity);
    }

    public Activity getCurrentActivity() {
        return mCurrentActivity.get();
    }

    public void launchPackage(String packageName) {
        Intent launchIntentForPackage = mContext.getPackageManager().getLaunchIntentForPackage(packageName);
        launchIntentForPackage.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        mContext.startActivity(launchIntentForPackage);
    }

    public void launchApp(String appName) {
        String packageName = getPackageName(appName);
        if (!TextUtils.isEmpty(packageName)) {
            launchPackage(packageName);
        }
    }


    public boolean checkPermission(String permission) {
        return ContextCompat.checkSelfPermission(mContext, permission) == PackageManager.PERMISSION_GRANTED;
    }

    @SuppressLint("MissingPermission")
    public boolean killApp(String packageName) {
        if (checkPermission(Manifest.permission.KILL_BACKGROUND_PROCESSES)) {
            ActivityManager manager = (ActivityManager) mContext.getSystemService(Context.ACTIVITY_SERVICE);
            manager.killBackgroundProcesses(packageName);
            return true;
        }
        return false;
    }

    public String getPackageName(String appName) {
        PackageManager packageManager = mContext.getPackageManager();
        List<ApplicationInfo> installedApplications = packageManager.getInstalledApplications(PackageManager.GET_META_DATA);
        for (ApplicationInfo applicationInfo : installedApplications) {
            if (packageManager.getApplicationLabel(applicationInfo).toString().equals(appName)) {
                return applicationInfo.packageName;
            }
        }
        return null;
    }

    public String getAppName(String packageName) {
        PackageManager packageManager = mContext.getPackageManager();
        try {
            ApplicationInfo applicationInfo = packageManager.getApplicationInfo(packageName, 0);
            CharSequence appName = packageManager.getApplicationLabel(applicationInfo);
            return appName == null ? null : appName.toString();
        } catch (PackageManager.NameNotFoundException e) {
            return null;
        }
    }

    public void openUrl(String url) {
        if (!url.startsWith("http://") && !url.startsWith("https://")) {
            url = "http://" + url;
        }
        mContext.startActivity(new Intent(Intent.ACTION_VIEW)
                .setData(Uri.parse(url))
                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
    }

    public void uninstall(String packageName) {
        mContext.startActivity(new Intent(Intent.ACTION_DELETE, Uri.parse("package:" + packageName))
                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
    }

    public void viewFile(String path) {
        if (path == null)
            throw new NullPointerException("path == null");
        String mimeType = MimeTypes.fromFileOr(path, "*/*");
        try {
            Uri uri = getUriOfFile(path, null);
            mContext.startActivity(new Intent(Intent.ACTION_VIEW)
                    .setDataAndType(uri, mimeType)
                    .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    .addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                    .addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION));
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
        }
    }

    private Uri getUriOfFile(String path, String fileProvider) {
        Uri uri;
        if (fileProvider == null) {
            uri = Uri.parse("file://" + path);
        } else {
            uri = FileProvider.getUriForFile(mContext, fileProvider, new File(path));
        }
        return uri;
    }

    public boolean canDrawOverlays() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return Settings.canDrawOverlays(mContext);
        }
        return true;
    }

    public void openOverlaySettingActivity() {
        try {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(intent);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean isAccessibilityServiceEnabled(String serviceName) {
        TextUtils.SimpleStringSplitter mStringColonSplitter = new TextUtils.SimpleStringSplitter(':');
        String settingValue = Settings.Secure.getString(mContext.getContentResolver(), Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES);
        if (settingValue != null) {
            mStringColonSplitter.setString(settingValue);
            while (mStringColonSplitter.hasNext()) {
                String accessibilityService = mStringColonSplitter.next();
                if (accessibilityService.equalsIgnoreCase(serviceName)) {
                    return true;
                }
            }
        }
        return false;
    }

    public void openServiceActivity() {
        //开启辅助功能
        try {
            Intent intent = new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            mContext.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

