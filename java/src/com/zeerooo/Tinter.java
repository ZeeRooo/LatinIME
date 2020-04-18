package com.zeerooo;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.util.TypedValue;
import android.view.inputmethod.EditorInfo;

import androidx.core.content.ContextCompat;

import static android.content.Context.CONTEXT_IGNORE_SECURITY;

public class Tinter {
    public static int colorAccentDark, colorAccent, colorPrimary;
    private Context internalContext, externalContext;
    private Intent intent;
    private ActivityInfo activityInfo;
    private float[] hsv = new float[3];

    public Tinter(Context internalContext) {
        this.internalContext = internalContext;
    }

    public void doMagic(EditorInfo editorInfo) {
        try {
            intent = internalContext.getPackageManager().getLaunchIntentForPackage(editorInfo.packageName);
            if (intent != null) {
                activityInfo = internalContext.getPackageManager().getActivityInfo(intent.getComponent(), PackageManager.GET_META_DATA);
                externalContext = internalContext.createPackageContext(editorInfo.packageName, CONTEXT_IGNORE_SECURITY);
                externalContext.setTheme(activityInfo.getThemeResource());

                colorAccent = getColorFromContext(android.R.attr.colorAccent);
                colorPrimary = getColorFromContext(android.R.attr.colorPrimary);
                colorAccentDark = colorDarker(colorAccent);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int getColorFromContext(int attr) {
        final TypedValue typedValue = new TypedValue();
        externalContext.getTheme().resolveAttribute(attr, typedValue, true);
        if (typedValue.type == TypedValue.TYPE_REFERENCE) {
            return ContextCompat.getColor(externalContext, typedValue.resourceId);
        } else {
            return typedValue.data;
        }
    }

    private int colorDarker(int color) {
        Color.colorToHSV(color, hsv);
        hsv[2] *= 0.8f; // smaller = darker
        return Color.HSVToColor(hsv);
    }
}
