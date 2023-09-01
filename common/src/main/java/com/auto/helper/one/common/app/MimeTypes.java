package com.auto.helper.one.common.app;

import android.webkit.MimeTypeMap;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class MimeTypes {


    @Nullable
    public static String fromFile(String path) {
        String ext = getExtension(path);
        return android.text.TextUtils.isEmpty(ext) ? "*/*" : MimeTypeMap.getSingleton().getMimeTypeFromExtension(ext);
    }

    private static String getExtension(String fileName) {
        int i = fileName.lastIndexOf('.');
        if (i < 0 || i + 1 >= fileName.length() - 1)
            return "";
        return fileName.substring(i + 1);
    }

    @NonNull
    public static String fromFileOr(String path, String defaultType) {
        String mimeType = fromFile(path);
        return mimeType == null ? defaultType : mimeType;
    }
}
