package com.yk.base.utils;


import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

public class StatusBarUtil {

    //设置状态栏颜色,不全屏
    public static void setStatusBarColor(Activity activity, int colorId) {
        Window window = activity.getWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            //5.0以上移动
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.setStatusBarColor(colorId);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //支持4.4到5.0
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);//设置状态栏透明，并且系统view全屏
            View statusBarView = createStatusBarView(activity, colorId, 1);
            ViewGroup decorView = (ViewGroup) window.getDecorView();
            decorView.addView(statusBarView);
            ViewGroup content = decorView.findViewById(android.R.id.content);
            content.getChildAt(0).setFitsSystemWindows(true);
        }
    }

    /**
     * 设置状态栏全透明并且，系统ui全屏
     *
     * @param activity
     */
    public static void setStatusBarTransparent(Activity activity) {
        Window window = activity.getWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            //5.0以上移动
            View decor = window.getDecorView();
            decor.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
            window.setStatusBarColor(Color.TRANSPARENT);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //支持4.4到5.0
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);//设置状态栏透明，并且系统view全屏

            ViewGroup decorView = (ViewGroup) window.getDecorView();
            ViewGroup content = decorView.findViewById(android.R.id.content);
            content.getChildAt(0).setFitsSystemWindows(true);
        }
    }

    /**
     * 设置状态栏透明度并且，系统ui全屏
     *
     * @param activity
     */

    public static void setStatusBarTransparent(Activity activity, int color, float statusBarAlpha) {
        Window window = activity.getWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            //5.0以上移动
            View decor = window.getDecorView();
            decor.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                window.setStatusBarColor(Color.argb(255f * statusBarAlpha, (color & 0xff0000) >> 16, (color & 0x00ff00) >> 8, color & 0x0000ff));
            } else {
                int alphaRate = (int) (255 * statusBarAlpha);
                int value = (color & 0x00ffffff) | (alphaRate << 24);
                window.setStatusBarColor(value);
            }

        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //支持4.4到5.0
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);//设置状态栏透明，并且系统view全屏
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);//设置状态栏透明，并且系统view全屏
            int alphaRate = (int) (255 * statusBarAlpha);
            int value = (color & 0x00ffffff) | (alphaRate << 24);
            View statusBarView = createStatusBarView(activity, value, 1);
            ViewGroup decorView = (ViewGroup) window.getDecorView();
            decorView.addView(statusBarView);
            ViewGroup content = decorView.findViewById(android.R.id.content);
            content.getChildAt(0).setFitsSystemWindows(true);
        }
    }


    /**
     * @param activity
     * @param rate
     */
    public static void slideStatusBarTransparent(Activity activity, int rate) {
        Window window = activity.getWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            //5.0以上移动
            int color = window.getStatusBarColor();
            int red = (color & 0xff0000) >> 16;
            int green = (color & 0x00ff00) >> 8;
            int blue = color & 0x0000ff;
            window.setStatusBarColor(Color.argb(1, red, green, blue));
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //支持4.4到5.0
            ViewGroup decorView = (ViewGroup) window.getDecorView();
            View view = decorView.getChildAt(decorView.getChildCount() - 1);
            if (view == null) return;
            Drawable drawable = view.getBackground();
            if (drawable instanceof ColorDrawable) {
                ColorDrawable cd = (ColorDrawable) drawable;
                int color = cd.getColor();
                int red = (color & 0xff0000) >> 16;
                int green = (color & 0x00ff00) >> 8;
                int blue = color & 0x0000ff;
                view.setBackgroundColor(Color.argb(1, red, green, blue));
            }
        }
    }

    public static View createStatusBarView(Activity activity, int color, float startusBarAlpha) {
        TextView view = new TextView(activity);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, getStatusBarHeight(activity));
        view.setLayoutParams(params);
        view.setBackgroundColor(color);
        view.setAlpha(startusBarAlpha);
        return view;
    }

    public static int getStatusBarHeight(Activity activity) {
        Resources resources = activity.getResources();
        int id = resources.getIdentifier("status_bar_height", "dimen", "android");
        int height = resources.getDimensionPixelOffset(id);
        return height;
    }

    public static void setAndroidNativeLightStatusBar(Activity activity, boolean dark) {
        View decor = activity.getWindow().getDecorView();
        if (dark) {
            decor.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        } else {
            decor.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
        }
    }

}
